/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.Choise;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.Question;

/**
 *
 * @author ketij
 */
public class QuestionJpaController implements Serializable {

    public QuestionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Question question) throws RollbackFailureException, Exception {
        if (question.getChoises() == null) {
            question.setChoises(new HashSet<Choise>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Exam exam = question.getExam();
            if (exam != null) {
                exam = em.getReference(exam.getClass(), exam.getId());
                question.setExam(exam);
            }
            Set<Choise> attachedChoises = new HashSet<Choise>();
            for (Choise choisesChoiseToAttach : question.getChoises()) {
                choisesChoiseToAttach = em.getReference(choisesChoiseToAttach.getClass(), choisesChoiseToAttach.getId());
                attachedChoises.add(choisesChoiseToAttach);
            }
            question.setChoises(attachedChoises);
            em.persist(question);
            if (exam != null) {
                exam.getQuestions().add(question);
                exam = em.merge(exam);
            }
            for (Choise choisesChoise : question.getChoises()) {
                Question oldQuestionOfChoisesChoise = choisesChoise.getQuestion();
                choisesChoise.setQuestion(question);
                choisesChoise = em.merge(choisesChoise);
                if (oldQuestionOfChoisesChoise != null) {
                    oldQuestionOfChoisesChoise.getChoises().remove(choisesChoise);
                    oldQuestionOfChoisesChoise = em.merge(oldQuestionOfChoisesChoise);
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

    public void edit(Question question) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Question persistentQuestion = em.find(Question.class, question.getId());
            Exam examOld = persistentQuestion.getExam();
            Exam examNew = question.getExam();
            Set<Choise> choisesOld = persistentQuestion.getChoises();
            Set<Choise> choisesNew = question.getChoises();
            if (examNew != null) {
                examNew = em.getReference(examNew.getClass(), examNew.getId());
                question.setExam(examNew);
            }
            Set<Choise> attachedChoisesNew = new HashSet<Choise>();
            for (Choise choisesNewChoiseToAttach : choisesNew) {
                choisesNewChoiseToAttach = em.getReference(choisesNewChoiseToAttach.getClass(), choisesNewChoiseToAttach.getId());
                attachedChoisesNew.add(choisesNewChoiseToAttach);
            }
            choisesNew = attachedChoisesNew;
            question.setChoises(choisesNew);
            question = em.merge(question);
            if (examOld != null && !examOld.equals(examNew)) {
                examOld.getQuestions().remove(question);
                examOld = em.merge(examOld);
            }
            if (examNew != null && !examNew.equals(examOld)) {
                examNew.getQuestions().add(question);
                examNew = em.merge(examNew);
            }
            for (Choise choisesOldChoise : choisesOld) {
                if (!choisesNew.contains(choisesOldChoise)) {
                    choisesOldChoise.setQuestion(null);
                    choisesOldChoise = em.merge(choisesOldChoise);
                }
            }
            for (Choise choisesNewChoise : choisesNew) {
                if (!choisesOld.contains(choisesNewChoise)) {
                    Question oldQuestionOfChoisesNewChoise = choisesNewChoise.getQuestion();
                    choisesNewChoise.setQuestion(question);
                    choisesNewChoise = em.merge(choisesNewChoise);
                    if (oldQuestionOfChoisesNewChoise != null && !oldQuestionOfChoisesNewChoise.equals(question)) {
                        oldQuestionOfChoisesNewChoise.getChoises().remove(choisesNewChoise);
                        oldQuestionOfChoisesNewChoise = em.merge(oldQuestionOfChoisesNewChoise);
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
                Long id = question.getId();
                if (findQuestion(id) == null) {
                    throw new NonexistentEntityException("The question with id " + id + " no longer exists.");
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
            Question question;
            try {
                question = em.getReference(Question.class, id);
                question.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The question with id " + id + " no longer exists.", enfe);
            }
            Exam exam = question.getExam();
            if (exam != null) {
                exam.getQuestions().remove(question);
                exam = em.merge(exam);
            }
            Set<Choise> choises = question.getChoises();
            for (Choise choisesChoise : choises) {
                choisesChoise.setQuestion(null);
                choisesChoise = em.merge(choisesChoise);
            }
            em.remove(question);
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

    public List<Question> findQuestionEntities() {
        return findQuestionEntities(true, -1, -1);
    }

    public List<Question> findQuestionEntities(int maxResults, int firstResult) {
        return findQuestionEntities(false, maxResults, firstResult);
    }

    private List<Question> findQuestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Question as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Question findQuestion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Question.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Question as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
