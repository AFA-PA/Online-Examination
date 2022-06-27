/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.afapa.exam.identity.UserCredential;

/**
 *
 * @author ketij
 */
@Stateless
public class UserCredentialFacade extends AbstractFacade<UserCredential> {

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserCredentialFacade() {
        super(UserCredential.class);
    }

}
