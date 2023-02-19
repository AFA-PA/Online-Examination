/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByQuestion", query = "SELECT q FROM Question q WHERE q.question = :question")})
@SuppressWarnings("UniqueEntityName")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 255)
    private String question;
    @OneToMany(mappedBy = "forquestionId")
    private List<Answer> answerList;
    @JoinColumn(name = "EXAM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Organizationalunit examId;
    @OneToMany(mappedBy = "questionId")
    private List<Choice> choiceList;

    /**
     *
     */
    public Question() {
    }

    /**
     *
     * @param id
     */
    public Question(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * @return
     */
    public List<Answer> getAnswerList() {
        return answerList;
    }

    /**
     *
     * @param answerList
     */
    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    /**
     *
     * @return
     */
    public Organizationalunit getExamId() {
        return examId;
    }

    /**
     *
     * @param examId
     */
    public void setExamId(Organizationalunit examId) {
        this.examId = examId;
    }

    /**
     *
     * @return
     */
    public List<Choice> getChoiceList() {
        return choiceList;
    }

    /**
     *
     * @param choiceList
     */
    public void setChoiceList(List<Choice> choiceList) {
        this.choiceList = choiceList;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "org.afapa.exam.generated_entities.Question[ id=" + id + " ]";
    }

}
