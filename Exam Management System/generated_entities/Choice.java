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
    @NamedQuery(name = "Choice.findAll", query = "SELECT c FROM Choice c"),
    @NamedQuery(name = "Choice.findById", query = "SELECT c FROM Choice c WHERE c.id = :id"),
    @NamedQuery(name = "Choice.findByChoise", query = "SELECT c FROM Choice c WHERE c.choise = :choise"),
    @NamedQuery(name = "Choice.findByCorrect", query = "SELECT c FROM Choice c WHERE c.correct = :correct")})
@SuppressWarnings("UniqueEntityName")
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 255)
    private String choise;
    private Boolean correct;
    @OneToMany(mappedBy = "choosedId")
    private List<Answer> answerList;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Question questionId;

    /**
     *
     */
    public Choice() {
    }

    /**
     *
     * @param id
     */
    public Choice(Long id) {
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
    public String getChoise() {
        return choise;
    }

    /**
     *
     * @param choise
     */
    public void setChoise(String choise) {
        this.choise = choise;
    }

    /**
     *
     * @return
     */
    public Boolean getCorrect() {
        return correct;
    }

    /**
     *
     * @param correct
     */
    public void setCorrect(Boolean correct) {
        this.correct = correct;
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
    public Question getQuestionId() {
        return questionId;
    }

    /**
     *
     * @param questionId
     */
    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
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
        if (!(object instanceof Choice)) {
            return false;
        }
        Choice other = (Choice) object;
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
        return "org.afapa.exam.generated_entities.Choice[ id=" + id + " ]";
    }

}
