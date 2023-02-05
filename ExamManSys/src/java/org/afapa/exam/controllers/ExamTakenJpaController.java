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
import org.afapa.exam.entity.User;
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.ExamTaken;

/**
 *
 * @author ketij
 */
public class ExamTakenJpaController implements Serializable {

    public ExamTakenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ExamTaken examTaken) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User taker = examTaken.getTaker();
            if (taker != null) {
                taker = em.getReference(taker.getClass(), taker.getId());
                examTaken.setTaker(taker);
            }
            Exam exam = examTaken.getExam();
            if (exam != null) {
                exam = em.getReference(exam.getClass(), exam.getId());
                examTaken.setExam(exam);
            }
            em.persist(examTaken);
            if (taker != null) {
                taker.getTakenExams().add(examTaken);
                taker = em.merge(taker);
            }
            if (exam != null) {
                exam.getExamsTaken().add(examTaken);
                exam = em.merge(exam);
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

    public void edit(ExamTaken examTaken) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ExamTaken persistentExamTaken = em.find(ExamTaken.class, examTaken.getId());
            User takerOld = persistentExamTaken.getTaker();
            User takerNew = examTaken.getTaker();
            Exam examOld = persistentExamTaken.getExam();
            Exam examNew = examTaken.getExam();
            if (takerNew != null) {
                takerNew = em.getReference(takerNew.getClass(), takerNew.getId());
                examTaken.setTaker(takerNew);
            }
            if (examNew != null) {
                examNew = em.getReference(examNew.getClass(), examNew.getId());
                examTaken.setExam(examNew);
            }
            examTaken = em.merge(examTaken);
            if (takerOld != null && !takerOld.equals(takerNew)) {
                takerOld.getTakenExams().remove(examTaken);
                takerOld = em.merge(takerOld);
            }
            if (takerNew != null && !takerNew.equals(takerOld)) {
                takerNew.getTakenExams().add(examTaken);
                takerNew = em.merge(takerNew);
            }
            if (examOld != null && !examOld.equals(examNew)) {
                examOld.getExamsTaken().remove(examTaken);
                examOld = em.merge(examOld);
            }
            if (examNew != null && !examNew.equals(examOld)) {
                examNew.getExamsTaken().add(examTaken);
                examNew = em.merge(examNew);
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
                Long id = examTaken.getId();
                if (findExamTaken(id) == null) {
                    throw new NonexistentEntityException("The examTaken with id " + id + " no longer exists.");
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
            ExamTaken examTaken;
            try {
                examTaken = em.getReference(ExamTaken.class, id);
                examTaken.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The examTaken with id " + id + " no longer exists.", enfe);
            }
            User taker = examTaken.getTaker();
            if (taker != null) {
                taker.getTakenExams().remove(examTaken);
                taker = em.merge(taker);
            }
            Exam exam = examTaken.getExam();
            if (exam != null) {
                exam.getExamsTaken().remove(examTaken);
                exam = em.merge(exam);
            }
            em.remove(examTaken);
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

    public List<ExamTaken> findExamTakenEntities() {
        return findExamTakenEntities(true, -1, -1);
    }

    public List<ExamTaken> findExamTakenEntities(int maxResults, int firstResult) {
        return findExamTakenEntities(false, maxResults, firstResult);
    }

    private List<ExamTaken> findExamTakenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ExamTaken as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ExamTaken findExamTaken(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ExamTaken.class, id);
        } finally {
            em.close();
        }
    }

    public int getExamTakenCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from ExamTaken as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
