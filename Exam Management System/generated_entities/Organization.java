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
    @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o"),
    @NamedQuery(name = "Organization.findById", query = "SELECT o FROM Organization o WHERE o.id = :id"),
    @NamedQuery(name = "Organization.findByName", query = "SELECT o FROM Organization o WHERE o.name = :name"),
    @NamedQuery(name = "Organization.findByCreatedondate", query = "SELECT o FROM Organization o WHERE o.createdondate = :createdondate")})
@SuppressWarnings("UniqueEntityName")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 255)
    private String name;
    @Temporal(TemporalType.DATE)
    private Date createdondate;
    @JoinColumn(name = "CREATEDBY_ID", referencedColumnName = "ID")
    @ManyToOne
    private User createdbyId;
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Organizationalunit organizationalunit;

    /**
     *
     */
    public Organization() {
    }

    /**
     *
     * @param id
     */
    public Organization(Long id) {
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
    public Date getCreatedondate() {
        return createdondate;
    }

    /**
     *
     * @param createdondate
     */
    public void setCreatedondate(Date createdondate) {
        this.createdondate = createdondate;
    }

    /**
     *
     * @return
     */
    public User getCreatedbyId() {
        return createdbyId;
    }

    /**
     *
     * @param createdbyId
     */
    public void setCreatedbyId(User createdbyId) {
        this.createdbyId = createdbyId;
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
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
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
        return "org.afapa.exam.generated_entities.Organization[ id=" + id + " ]";
    }

}
