/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ketij
 */
@Setter
@Getter
@Entity
@XmlRootElement
public class Exam extends OrganizationalUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private Duration duration;

    private Time startTime;

    @ManyToOne
    protected Course course;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exam")
    private List<ExamTaken> examsTaken;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exam)) {
            return false;
        }
        Exam other = (Exam) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "org.afapa.exam.entity.Exam[ id=" + id + " ]";
    }

//    @java.lang.SuppressWarnings(value = "all")
//    @XmlTransient
//    public List<Question> getQuestions() {
//    }
//
//    @java.lang.SuppressWarnings(value = "all")
//    @XmlTransient
//    public List<ExamTaken> getExamsTaken() {
//    }

}
