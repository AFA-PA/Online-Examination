
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ketij
 */
@NamedQuery(name = "getCourseExams", query = "SELECT e FROM Exam e WHERE e.registration=:reg")
@Setter
@Getter
@Entity
@Table(name = "CourseRegistration")
//@SecondaryTable(name = "UserRegistration")
@XmlRootElement
public class CourseRegistration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;
    @Size(max = 15)
    @Column(name = "semester")
    private String semester;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "UserRegistration", joinColumns = {
        @JoinColumn(name = "reg_id", table = "CourseRegistration")})
    private List<User> registeredUsers;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "registration")
    private List<Exam> exams;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseRegistration)) {
            return false;
        }
        CourseRegistration other = (CourseRegistration) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return this.course + " " + this.year + "/" + this.semester;
    }

    public CourseRegistration() {
    }

    public CourseRegistration(Long id) {
        this.id = id;
    }

    public CourseRegistration(Long id, int year) {
        this.id = id;
        this.year = year;
    }
}
