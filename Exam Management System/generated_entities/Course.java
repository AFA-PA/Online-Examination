/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
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

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
    @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.id = :id"),
    @NamedQuery(name = "Course.findByCode", query = "SELECT c FROM Course c WHERE c.code = :code"),
    @NamedQuery(name = "Course.findByName", query = "SELECT c FROM Course c WHERE c.name = :name")})
@SuppressWarnings("UniqueEntityName")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 255)
    private String code;
    @Column(length = 255)
    private String name;
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Organizationalunit departmentId;
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Organizationalunit organizationalunit;

    /**
     *
     */
    public Course() {
    }

    /**
     *
     * @param id
     */
    public Course(Long id) {
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
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
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
    public Organizationalunit getDepartmentId() {
        return departmentId;
    }

    /**
     *
     * @param departmentId
     */
    public void setDepartmentId(Organizationalunit departmentId) {
        this.departmentId = departmentId;
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
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
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
        return "org.afapa.exam.generated_entities.Course[ id=" + id + " ]";
    }

}
