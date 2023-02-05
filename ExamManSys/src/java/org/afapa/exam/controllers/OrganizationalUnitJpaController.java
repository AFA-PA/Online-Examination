/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.OrganizationalUnit;

/**
 *
 * @author ketij
 */
public class OrganizationalUnitJpaController implements Serializable {

    public OrganizationalUnitJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrganizationalUnit organizationalUnit) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(organizationalUnit);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrganizationalUnit organizationalUnit) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            organizationalUnit = em.merge(organizationalUnit);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = organizationalUnit.getId();
                if (findOrganizationalUnit(id) == null) {
                    throw new NonexistentEntityException("The organizationalUnit with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OrganizationalUnit organizationalUnit;
            try {
                organizationalUnit = em.getReference(OrganizationalUnit.class, id);
                organizationalUnit.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizationalUnit with id " + id + " no longer exists.", enfe);
            }
            em.remove(organizationalUnit);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrganizationalUnit> findOrganizationalUnitEntities() {
        return findOrganizationalUnitEntities(true, -1, -1);
    }

    public List<OrganizationalUnit> findOrganizationalUnitEntities(int maxResults, int firstResult) {
        return findOrganizationalUnitEntities(false, maxResults, firstResult);
    }

    private List<OrganizationalUnit> findOrganizationalUnitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from OrganizationalUnit as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public OrganizationalUnit findOrganizationalUnit(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrganizationalUnit.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizationalUnitCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from OrganizationalUnit as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
