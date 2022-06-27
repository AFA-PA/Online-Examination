/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.security.enterprise.identitystore.IdentityStore;
import org.afapa.exam.entity.OrganizationalUnit;
import org.afapa.exam.entity.User;

/**
 *
 * @author AFAPA-group
 */
@ApplicationScoped
@Named
public class UserIdentityStore implements IdentityStore {

    private static final Logger logger = Logger.getLogger("IdentityStore");

    public UserIdentityStore() {
    }

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHasher passwordHasher;

    public CredentialValidationResult validate(UserCredential credential) {
        logger.log(Level.INFO, "starting validation");
        switch (credential.getCredType()) {
            case PASS_CRED:
                logger.log(Level.INFO, "validating using email and password, email: {0}", credential.getUser().getEmail());
                return validatePassword(credential);
            case TOKEN_CRED:
                logger.log(Level.INFO, "validating using tokens token: {0}", credential.getGivenToken().getString());
                return validateToken(credential);
            default:
                return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }

    public CredentialValidationResult validate(User user, char[] password) {
        try {
            UserCredential dbUc = getDbCred(user);
            boolean isVerified = passwordHasher.verify(password, dbUc.getHashedPassword());
            if (isVerified) {
                return new CredentialValidationResult(user.getEmail());
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Exception validating UserCredential: {0}", e.getMessage());
            logger.log(Level.FINE, "{0}", e.getStackTrace());
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
        return CredentialValidationResult.INVALID_RESULT;
    }

    private CredentialValidationResult validatePassword(UserCredential credential) {
        return validate(credential.getUser(), credential.getPassword().toCharArray());
    }

    private CredentialValidationResult validateToken(UserCredential credential) {
        try {
            List<Token> dbTokens = em.createNamedQuery("getTokensByUserId", Token.class)
                    .setParameter("userId", credential.getUser().getId()).getResultList();
            Token givenToken = credential.getGivenToken();
            logger.log(Level.INFO, "iterating over existing {0} tokens for user: {1} givenToken: {2}",
                    new Object[]{dbTokens.size(), credential.getUser(), givenToken.getString()});
            for (Token token : dbTokens) {
                logger.log(Level.INFO, "validating token: {0}", token.getString());
                if (token.equals(givenToken)) {
                    logger.log(Level.INFO, "got a match, chacking expiration...");
                    if (token.getExpireTime().after(Timestamp.from(Instant.now()))) {
                        logger.log(Level.INFO, "validated: {0} using token: {1}",
                                new Object[]{credential.getId(), givenToken.getString()});
                        return new CredentialValidationResult(credential.getUser().getEmail());
                    } else {
                        logger.log(Level.INFO, "expired token: {0}", token.getString());
                        return CredentialValidationResult.INVALID_RESULT;
                    }
                }
            }

        } catch (Exception e) {
            logger.log(Level.INFO, "Exeption during token validation: {0}", e.getMessage());
            logger.log(Level.FINE, "{0}", e.getStackTrace());
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
        return CredentialValidationResult.INVALID_RESULT;
    }

    private UserCredential getDbCred(User user) {
        return em.createNamedQuery("getUserCredential", UserCredential.class)
                .setParameter("userId", user.getId()).getSingleResult();
    }

    public String doHash(char[] password) {
        return passwordHasher.generate(password);
    }

    public Set<Privilege> getPrivilegesOn(UserCredential credential, OrganizationalUnit unit) {
        Set<Privilege> privileges = new HashSet<>();
        if (validate(credential).getStatus() == CredentialValidationResult.Status.VALID) {
            privileges = em.createNamedQuery("getUserPrivileges", privileges.getClass())
                    .setParameter("user", credential.getUser())
                    .setParameter("unit", unit).getSingleResult();
        }
        return privileges;
    }

    public List<Privilege> getPrivilegesOn(CredentialValidationResult cvr, OrganizationalUnit unit) {
        List<Privilege> privileges = null;
        if (cvr.getStatus().equals(Status.VALID)) {
            privileges = em.createNamedQuery("getUserPrivileges", Privilege.class)
                    .setParameter("email", cvr.getCallerPrincipal().getName())
                    .setParameter("unit", unit).getResultList();
        }
        return privileges;
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult cvr) {
        HashSet<String> groups = new HashSet<>(Arrays.asList("guest"));
        if (cvr.getStatus() == CredentialValidationResult.Status.VALID) {
            groups.add("signed-in");
            Long result = 0L;
            try {
                result = em.createNamedQuery("isUserAdmin", Long.class)
                        .setParameter("email", cvr.getCallerPrincipal().getName())
                        .getSingleResult();
            } finally {
                if (result > 0) {
                    groups.add("admin");
                }
            }
        }
        logger.log(Level.INFO, "User {0} is in the following group: {1}", new Object[]{cvr.getCallerPrincipal(), groups});
        return groups;
    }
}
