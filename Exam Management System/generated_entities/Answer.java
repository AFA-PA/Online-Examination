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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a"),
    @NamedQuery(name = "Answer.findById", query = "SELECT a FROM Answer a WHERE a.id = :id")})
@SuppressWarnings("UniqueEntityName")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @ManyToMany(mappedBy = "answerList")
    private List<Examtaken> examtakenList;
    @JoinColumn(name = "CHOOSED_ID", referencedColumnName = "ID")
    @ManyToOne
    private Choice choosedId;
    @JoinColumn(name = "FORQUESTION_ID", referencedColumnName = "ID")
    @ManyToOne
    private Question forquestionId;

    /**
     *
     */
    public Answer() {
    }

    /**
     *
     * @param id
     */
    public Answer(Long id) {
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
    public List<Examtaken> getExamtakenList() {
        return examtakenList;
    }

    /**
     *
     * @param examtakenList
     */
    public void setExamtakenList(List<Examtaken> examtakenList) {
        this.examtakenList = examtakenList;
    }

    /**
     *
     * @return
     */
    public Choice getChoosedId() {
        return choosedId;
    }

    /**
     *
     * @param choosedId
     */
    public void setChoosedId(Choice choosedId) {
        this.choosedId = choosedId;
    }

    /**
     *
     * @return
     */
    public Question getForquestionId() {
        return forquestionId;
    }

    /**
     *
     * @param forquestionId
     */
    public void setForquestionId(Question forquestionId) {
        this.forquestionId = forquestionId;
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
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
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
        return "org.afapa.exam.generated_entities.Answer[ id=" + id + " ]";
    }

}
