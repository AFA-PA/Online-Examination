/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author ketij
 */
public abstract class UserIdentityStore implements IdentityStore {

    @PersistenceContext(unitName = "ExamManSysPU")
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public CredentialValidationResult validate(UserCredential credential) {
        boolean isVerified = passwordHash.verify(credential.getPassword().getValue(), credential.getHashedPassword());
        if (!isVerified) {
            return CredentialValidationResult.INVALID_RESULT;
        }

        CredentialValidationResult cvr = new CredentialValidationResult(credential.getUser().getEmail());

        return cvr;
    }
}
