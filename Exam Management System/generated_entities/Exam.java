/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Exam.findAll", query = "SELECT e FROM Exam e"),
    @NamedQuery(name = "Exam.findById", query = "SELECT e FROM Exam e WHERE e.id = :id"),
    @NamedQuery(name = "Exam.findByMinutesallowed", query = "SELECT e FROM Exam e WHERE e.minutesallowed = :minutesallowed"),
    @NamedQuery(name = "Exam.findByName", query = "SELECT e FROM Exam e WHERE e.name = :name"),
    @NamedQuery(name = "Exam.findByStarttime", query = "SELECT e FROM Exam e WHERE e.starttime = :starttime")})
@SuppressWarnings("UniqueEntityName")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    private Integer minutesallowed;
    @Column(length = 255)
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Organizationalunit courseId;
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Organizationalunit organizationalunit;

    /**
     *
     */
    public Exam() {
    }

    /**
     *
     * @param id
     */
    public Exam(Long id) {
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
    public Integer getMinutesallowed() {
        return minutesallowed;
    }

    /**
     *
     * @param minutesallowed
     */
    public void setMinutesallowed(Integer minutesallowed) {
        this.minutesallowed = minutesallowed;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     *
     * @param starttime
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     *
     * @return
     */
    public Organizationalunit getCourseId() {
        return courseId;
    }

    /**
     *
     * @param courseId
     */
    public void setCourseId(Organizationalunit courseId) {
        this.courseId = courseId;
    }

    /**
     *
     * @return
     */
    public Organizationalunit getOrganizationalunit() {
        return organizationalunit;
    }

    /**
     *
     * @param organizationalunit
     */
    public void setOrganizationalunit(Organizationalunit organizationalunit) {
        this.organizationalunit = organizationalunit;
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
        return "org.afapa.exam.generated_entities.Exam[ id=" + id + " ]";
    }

}
