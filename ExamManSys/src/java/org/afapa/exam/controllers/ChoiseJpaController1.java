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
import org.afapa.exam.entity.Choise;
import org.afapa.exam.entity.Question;

/**
 *
 * @author ketij
 */
public class ChoiseJpaController1 implements Serializable {

    public ChoiseJpaController1(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Choise choise) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Question question = choise.getQuestion();
            if (question != null) {
                question = em.getReference(question.getClass(), question.getId());
                choise.setQuestion(question);
            }
            em.persist(choise);
            if (question != null) {
                question.getChoises().add(choise);
                question = em.merge(question);
            }
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

    public void edit(Choise choise) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choise persistentChoise = em.find(Choise.class, choise.getId());
            Question questionOld = persistentChoise.getQuestion();
            Question questionNew = choise.getQuestion();
            if (questionNew != null) {
                questionNew = em.getReference(questionNew.getClass(), questionNew.getId());
                choise.setQuestion(questionNew);
            }
            choise = em.merge(choise);
            if (questionOld != null && !questionOld.equals(questionNew)) {
                questionOld.getChoises().remove(choise);
                questionOld = em.merge(questionOld);
            }
            if (questionNew != null && !questionNew.equals(questionOld)) {
                questionNew.getChoises().add(choise);
                questionNew = em.merge(questionNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = choise.getId();
                if (findChoise(id) == null) {
                    throw new NonexistentEntityException("The choise with id " + id + " no longer exists.");
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
            Choise choise;
            try {
                choise = em.getReference(Choise.class, id);
                choise.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The choise with id " + id + " no longer exists.", enfe);
            }
            Question question = choise.getQuestion();
            if (question != null) {
                question.getChoises().remove(choise);
                question = em.merge(question);
            }
            em.remove(choise);
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

    public List<Choise> findChoiseEntities() {
        return findChoiseEntities(true, -1, -1);
    }

    public List<Choise> findChoiseEntities(int maxResults, int firstResult) {
        return findChoiseEntities(false, maxResults, firstResult);
    }

    private List<Choise> findChoiseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Choise as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Choise findChoise(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Choise.class, id);
        } finally {
            em.close();
        }
    }

    public int getChoiseCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Choise as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
