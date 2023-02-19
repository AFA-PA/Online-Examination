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
import javax.persistence.JoinTable;
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
    @NamedQuery(name = "Examtaken.findAll", query = "SELECT e FROM Examtaken e"),
    @NamedQuery(name = "Examtaken.findById", query = "SELECT e FROM Examtaken e WHERE e.id = :id")})
@SuppressWarnings("UniqueEntityName")
public class Examtaken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinTable(name = "EXAMTAKEN_ANSWER", joinColumns = {
        @JoinColumn(name = "ExamTaken_ID", referencedColumnName = "ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "answers_ID", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    private List<Answer> answerList;
    @JoinColumn(name = "EXAM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Organizationalunit examId;
    @JoinColumn(name = "TAKER_ID", referencedColumnName = "ID")
    @ManyToOne
    private User takerId;

    /**
     *
     */
    public Examtaken() {
    }

    /**
     *
     * @param id
     */
    public Examtaken(Long id) {
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
    public User getTakerId() {
        return takerId;
    }

    /**
     *
     * @param takerId
     */
    public void setTakerId(User takerId) {
        this.takerId = takerId;
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
        if (!(object instanceof Examtaken)) {
            return false;
        }
        Examtaken other = (Examtaken) object;
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
        return "org.afapa.exam.generated_entities.Examtaken[ id=" + id + " ]";
    }

}
