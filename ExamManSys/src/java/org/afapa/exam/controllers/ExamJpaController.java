/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import org.afapa.exam.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import org.afapa.exam.controllers.exceptions.NonexistentEntityException;
import org.afapa.exam.controllers.exceptions.RollbackFailureException;
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.ExamTaken;

/**
 *
 * @author ketij
 */
public class ExamJpaController implements Serializable {

    public ExamJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exam exam) throws RollbackFailureException, Exception {
        if (exam.getQuestions() == null) {
            exam.setQuestions(new ArrayList<Question>());
        }
        if (exam.getExamsTaken() == null) {
            exam.setExamsTaken(new ArrayList<ExamTaken>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Question> attachedQuestions = new ArrayList<Question>();
            for (Question questionsQuestionToAttach : exam.getQuestions()) {
                questionsQuestionToAttach = em.getReference(questionsQuestionToAttach.getClass(), questionsQuestionToAttach.getId());
                attachedQuestions.add(questionsQuestionToAttach);
            }
            exam.setQuestions(attachedQuestions);
            List<ExamTaken> attachedExamsTaken = new ArrayList<ExamTaken>();
            for (ExamTaken examsTakenExamTakenToAttach : exam.getExamsTaken()) {
                examsTakenExamTakenToAttach = em.getReference(examsTakenExamTakenToAttach.getClass(), examsTakenExamTakenToAttach.getId());
                attachedExamsTaken.add(examsTakenExamTakenToAttach);
            }
            exam.setExamsTaken(attachedExamsTaken);
            em.persist(exam);
            for (Question questionsQuestion : exam.getQuestions()) {
                Exam oldExamOfQuestionsQuestion = questionsQuestion.getExam();
                questionsQuestion.setExam(exam);
                questionsQuestion = em.merge(questionsQuestion);
                if (oldExamOfQuestionsQuestion != null) {
                    oldExamOfQuestionsQuestion.getQuestions().remove(questionsQuestion);
                    oldExamOfQuestionsQuestion = em.merge(oldExamOfQuestionsQuestion);
                }
            }
            for (ExamTaken examsTakenExamTaken : exam.getExamsTaken()) {
                Exam oldExamOfExamsTakenExamTaken = examsTakenExamTaken.getExam();
                examsTakenExamTaken.setExam(exam);
                examsTakenExamTaken = em.merge(examsTakenExamTaken);
                if (oldExamOfExamsTakenExamTaken != null) {
                    oldExamOfExamsTakenExamTaken.getExamsTaken().remove(examsTakenExamTaken);
                    oldExamOfExamsTakenExamTaken = em.merge(oldExamOfExamsTakenExamTaken);
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

    public void edit(Exam exam) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Exam persistentExam = em.find(Exam.class, exam.getId());
            List<Question> questionsOld = persistentExam.getQuestions();
            List<Question> questionsNew = exam.getQuestions();
            List<ExamTaken> examsTakenOld = persistentExam.getExamsTaken();
            List<ExamTaken> examsTakenNew = exam.getExamsTaken();
            List<Question> attachedQuestionsNew = new ArrayList<Question>();
            for (Question questionsNewQuestionToAttach : questionsNew) {
                questionsNewQuestionToAttach = em.getReference(questionsNewQuestionToAttach.getClass(), questionsNewQuestionToAttach.getId());
                attachedQuestionsNew.add(questionsNewQuestionToAttach);
            }
            questionsNew = attachedQuestionsNew;
            exam.setQuestions(questionsNew);
            List<ExamTaken> attachedExamsTakenNew = new ArrayList<ExamTaken>();
            for (ExamTaken examsTakenNewExamTakenToAttach : examsTakenNew) {
                examsTakenNewExamTakenToAttach = em.getReference(examsTakenNewExamTakenToAttach.getClass(), examsTakenNewExamTakenToAttach.getId());
                attachedExamsTakenNew.add(examsTakenNewExamTakenToAttach);
            }
            examsTakenNew = attachedExamsTakenNew;
            exam.setExamsTaken(examsTakenNew);
            exam = em.merge(exam);
            for (Question questionsOldQuestion : questionsOld) {
                if (!questionsNew.contains(questionsOldQuestion)) {
                    questionsOldQuestion.setExam(null);
                    questionsOldQuestion = em.merge(questionsOldQuestion);
                }
            }
            for (Question questionsNewQuestion : questionsNew) {
                if (!questionsOld.contains(questionsNewQuestion)) {
                    Exam oldExamOfQuestionsNewQuestion = questionsNewQuestion.getExam();
                    questionsNewQuestion.setExam(exam);
                    questionsNewQuestion = em.merge(questionsNewQuestion);
                    if (oldExamOfQuestionsNewQuestion != null && !oldExamOfQuestionsNewQuestion.equals(exam)) {
                        oldExamOfQuestionsNewQuestion.getQuestions().remove(questionsNewQuestion);
                        oldExamOfQuestionsNewQuestion = em.merge(oldExamOfQuestionsNewQuestion);
                    }
                }
            }
            for (ExamTaken examsTakenOldExamTaken : examsTakenOld) {
                if (!examsTakenNew.contains(examsTakenOldExamTaken)) {
                    examsTakenOldExamTaken.setExam(null);
                    examsTakenOldExamTaken = em.merge(examsTakenOldExamTaken);
                }
            }
            for (ExamTaken examsTakenNewExamTaken : examsTakenNew) {
                if (!examsTakenOld.contains(examsTakenNewExamTaken)) {
                    Exam oldExamOfExamsTakenNewExamTaken = examsTakenNewExamTaken.getExam();
                    examsTakenNewExamTaken.setExam(exam);
                    examsTakenNewExamTaken = em.merge(examsTakenNewExamTaken);
                    if (oldExamOfExamsTakenNewExamTaken != null && !oldExamOfExamsTakenNewExamTaken.equals(exam)) {
                        oldExamOfExamsTakenNewExamTaken.getExamsTaken().remove(examsTakenNewExamTaken);
                        oldExamOfExamsTakenNewExamTaken = em.merge(oldExamOfExamsTakenNewExamTaken);
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
                Long id = exam.getId();
                if (findExam(id) == null) {
                    throw new NonexistentEntityException("The exam with id " + id + " no longer exists.");
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
            Exam exam;
            try {
                exam = em.getReference(Exam.class, id);
                exam.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exam with id " + id + " no longer exists.", enfe);
            }
            List<Question> questions = exam.getQuestions();
            for (Question questionsQuestion : questions) {
                questionsQuestion.setExam(null);
                questionsQuestion = em.merge(questionsQuestion);
            }
            List<ExamTaken> examsTaken = exam.getExamsTaken();
            for (ExamTaken examsTakenExamTaken : examsTaken) {
                examsTakenExamTaken.setExam(null);
                examsTakenExamTaken = em.merge(examsTakenExamTaken);
            }
            em.remove(exam);
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

    public List<Exam> findExamEntities() {
        return findExamEntities(true, -1, -1);
    }

    public List<Exam> findExamEntities(int maxResults, int firstResult) {
        return findExamEntities(false, maxResults, firstResult);
    }

    private List<Exam> findExamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Exam as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Exam findExam(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exam.class, id);
        } finally {
            em.close();
        }
    }

    public int getExamCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Exam as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
