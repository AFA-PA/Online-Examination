/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package org.afapa.exam.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.afapa.exam.clientdata.*;
import org.afapa.exam.entity.*;
import org.afapa.exam.identity.*;

/**
 * REST Web Service
 *
 * @author AFAPA-group
 */
@Stateless
@Path("auth")
//@RolesAllowed({"guest", "loggedin"})
public class AuthRest {

    private static final Logger logger = Logger.getLogger("Auth Rest");

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;

    @Inject
    UserIdentityStore idStr;

    private final SecureRandom random = new SecureRandom();

    /**
     * Creates a new instance of Auth
     */
    public AuthRest() {
    }
//
//    @GET
//    public String getAuth() {
//        return "Hello";
//    }

    @POST
    @Path("signup")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response signup(SignupCred cred) {

        logger.log(Level.INFO, "signup: {0}:{1}", new Object[]{cred.getUser().getEmail(), cred.getPassword()});
        if (cred.getPassword() == null) {
            throw new BadRequestException("Password cannot be null");
        }

        if (em.createNamedQuery("getUserByEmail").setParameter("email", cred.getUser().getEmail())
                .setMaxResults(1).getResultList().isEmpty()) {
            em.persist(cred.getUser());
            UserCredential uc = new UserCredential(cred.getUser(), cred.getPassword());
            logger.log(Level.INFO, "Password: ******");
            em.persist(uc);
            return Response.status(Response.Status.CREATED).entity(cred.getUser()).build();
        } else {
            throw new BadRequestException("a user already exists with that email");
        }
    }

    @POST
    @Path("signin")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response signin(SigninCred cred) {
        logger.log(Level.INFO, "login: {0}:{1}", new Object[]{cred.getEmail(), cred.getPassword()});
        try {
            User user = (User) em.createNamedQuery("getUserByEmail").setParameter("email", cred.getEmail()).getSingleResult();
            UserCredential uc = new UserCredential(user, cred.getPassword());
            CredentialValidationResult cvr = idStr.validate(uc);
            if (cvr.getStatus() == CredentialValidationResult.Status.VALID) {
                byte bytes[] = new byte[64];
                random.nextBytes(bytes);
                String tokenString = Base64.getEncoder().encodeToString(bytes);
                Token token = new Token(user, tokenString);
                em.persist(token);
                logger.log(Level.INFO, "Signed in a user with email: {0}", cred.getEmail());
                return Response.accepted(SignedinRequest.fromToken(token)).build();
            }

        } catch (NoResultException e) {
            logger.log(Level.INFO, "user with {0} does not exist", cred.getEmail());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Path("signout")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response signout(SignedinRequest cred) {
        String[] tokens = cred.getTokenString().split(":");
        int result = em.createNamedQuery("deleteToken")
                .setParameter("userId", Long.parseLong(tokens[0]))
                .setParameter("token", tokens[1]).executeUpdate();
        if (result == 0) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok().build();
    }
}
