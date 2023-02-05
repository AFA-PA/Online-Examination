/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import org.afapa.exam.entity.ExamTaken;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.User;

/**
 *
 * @author ketij
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getTakenExams() == null) {
            user.setTakenExams(new HashSet<ExamTaken>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<ExamTaken> attachedTakenExams = new HashSet<ExamTaken>();
            for (ExamTaken takenExamsExamTakenToAttach : user.getTakenExams()) {
                takenExamsExamTakenToAttach = em.getReference(takenExamsExamTakenToAttach.getClass(), takenExamsExamTakenToAttach.getId());
                attachedTakenExams.add(takenExamsExamTakenToAttach);
            }
            user.setTakenExams(attachedTakenExams);
            em.persist(user);
            for (ExamTaken takenExamsExamTaken : user.getTakenExams()) {
                User oldTakerOfTakenExamsExamTaken = takenExamsExamTaken.getTaker();
                takenExamsExamTaken.setTaker(user);
                takenExamsExamTaken = em.merge(takenExamsExamTaken);
                if (oldTakerOfTakenExamsExamTaken != null) {
                    oldTakerOfTakenExamsExamTaken.getTakenExams().remove(takenExamsExamTaken);
                    oldTakerOfTakenExamsExamTaken = em.merge(oldTakerOfTakenExamsExamTaken);
                }
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

    public void edit(User user) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            Set<ExamTaken> takenExamsOld = persistentUser.getTakenExams();
            Set<ExamTaken> takenExamsNew = user.getTakenExams();
            Set<ExamTaken> attachedTakenExamsNew = new HashSet<ExamTaken>();
            for (ExamTaken takenExamsNewExamTakenToAttach : takenExamsNew) {
                takenExamsNewExamTakenToAttach = em.getReference(takenExamsNewExamTakenToAttach.getClass(), takenExamsNewExamTakenToAttach.getId());
                attachedTakenExamsNew.add(takenExamsNewExamTakenToAttach);
            }
            takenExamsNew = attachedTakenExamsNew;
            user.setTakenExams(takenExamsNew);
            user = em.merge(user);
            for (ExamTaken takenExamsOldExamTaken : takenExamsOld) {
                if (!takenExamsNew.contains(takenExamsOldExamTaken)) {
                    takenExamsOldExamTaken.setTaker(null);
                    takenExamsOldExamTaken = em.merge(takenExamsOldExamTaken);
                }
            }
            for (ExamTaken takenExamsNewExamTaken : takenExamsNew) {
                if (!takenExamsOld.contains(takenExamsNewExamTaken)) {
                    User oldTakerOfTakenExamsNewExamTaken = takenExamsNewExamTaken.getTaker();
                    takenExamsNewExamTaken.setTaker(user);
                    takenExamsNewExamTaken = em.merge(takenExamsNewExamTaken);
                    if (oldTakerOfTakenExamsNewExamTaken != null && !oldTakerOfTakenExamsNewExamTaken.equals(user)) {
                        oldTakerOfTakenExamsNewExamTaken.getTakenExams().remove(takenExamsNewExamTaken);
                        oldTakerOfTakenExamsNewExamTaken = em.merge(oldTakerOfTakenExamsNewExamTaken);
                    }
                }
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
                Long id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            Set<ExamTaken> takenExams = user.getTakenExams();
            for (ExamTaken takenExamsExamTaken : takenExams) {
                takenExamsExamTaken.setTaker(null);
                takenExamsExamTaken = em.merge(takenExamsExamTaken);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from User as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from User as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
