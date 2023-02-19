/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.afapa.exam.entity.OrganizationalUnit;

/**
 *
 * @author ketij
 */
@Stateless
public class OrganizationalUnitFacade extends AbstractFacade<OrganizationalUnit> {

    @PersistenceContext(unitName = "WebApplicationTest3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizationalUnitFacade() {
        super(OrganizationalUnit.class);
    }

}
