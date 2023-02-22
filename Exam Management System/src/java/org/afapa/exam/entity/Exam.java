/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author AFAPA-group
 */
@NamedQuery(name = "getExamById", query = "SELECT exam FROM Exam exam WHERE exam.id=:id")
@Setter
@Getter
@Entity
@XmlRootElement
public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int minutesAllowed;

    private Timestamp startTime;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    protected CourseRegistration registration;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "exam")
    private List<Question> questions;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "exam")
    private List<ExamTaken> examsTaken;

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> qns) {
        this.questions = qns;
    }

    public Date getDate() {
        return this.startTime;
    }

    public void setDate(Date date) {
        this.startTime = new Timestamp(date.getTime());
    }

    /**
     *
     */
    public Exam() {
    }

    /**
     *
     * @param name
     * @param minutesAllowed
     * @param startTime
     * @param course
     */
    public Exam(String name, int minutesAllowed, Timestamp startTime, CourseRegistration course) {
        this.name = name;
        this.minutesAllowed = minutesAllowed;
        this.startTime = startTime;
        this.registration = course;
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
        if (!(object instanceof Exam)) {
            return false;
        }
        Exam other = (Exam) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

    @PrePersist
    public void PrePersist() {
        if (startTime == null) {
//            startTime = Timestamp.from(Instant.now().plusSeconds(3600));
        }
    }

}
