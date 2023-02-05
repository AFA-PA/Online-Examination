/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.PreexistingEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.User;
import org.afapa.exam.identity.UserCredential;

/**
 *
 * @author ketij
 */
public class UserCredentialJpaController implements Serializable {

    public UserCredentialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserCredential userCredential) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(userCredential);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUserCredential(userCredential.getUser()) != null) {
                throw new PreexistingEntityException("UserCredential " + userCredential + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserCredential userCredential) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            userCredential = em.merge(userCredential);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                User id = userCredential.getUser();
                if (findUserCredential(id) == null) {
                    throw new NonexistentEntityException("The userCredential with id " + id + " no longer exists.");
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
            UserCredential userCredential;
            try {
                userCredential = em.getReference(UserCredential.class, id);
                userCredential.getUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userCredential with id " + id + " no longer exists.", enfe);
            }
            em.remove(userCredential);
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

    public void destroy(User id) throws NonexistentEntityException, RollbackFailureException, Exception {
        destroy(id.getId());
    }

    public List<UserCredential> findUserCredentialEntities() {
        return findUserCredentialEntities(true, -1, -1);
    }

    public List<UserCredential> findUserCredentialEntities(int maxResults, int firstResult) {
        return findUserCredentialEntities(false, maxResults, firstResult);
    }

    private List<UserCredential> findUserCredentialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from UserCredential as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UserCredential findUserCredential(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserCredential.class, id);
        } finally {
            em.close();
        }
    }

    public UserCredential findUserCredential(User id) {
        return findUserCredential(id.getId());
    }

    public int getUserCredentialCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from UserCredential as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
