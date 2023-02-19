/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Privilege.findAll", query = "SELECT p FROM Privilege p"),
    @NamedQuery(name = "Privilege.findById", query = "SELECT p FROM Privilege p WHERE p.id = :id"),
    @NamedQuery(name = "Privilege.findByPermission", query = "SELECT p FROM Privilege p WHERE p.permission = :permission")})
@SuppressWarnings("UniqueEntityName")
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    private Short permission;
    @JoinColumn(name = "GRANTEDON_ID", referencedColumnName = "ID")
    @ManyToOne
    private Organizationalunit grantedonId;
    @JoinColumn(name = "GRANTEDTO_ID", referencedColumnName = "ID")
    @ManyToOne
    private User grantedtoId;

    /**
     *
     */
    public Privilege() {
    }

    /**
     *
     * @param id
     */
    public Privilege(Long id) {
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
    public Short getPermission() {
        return permission;
    }

    /**
     *
     * @param permission
     */
    public void setPermission(Short permission) {
        this.permission = permission;
    }

    /**
     *
     * @return
     */
    public Organizationalunit getGrantedonId() {
        return grantedonId;
    }

    /**
     *
     * @param grantedonId
     */
    public void setGrantedonId(Organizationalunit grantedonId) {
        this.grantedonId = grantedonId;
    }

    /**
     *
     * @return
     */
    public User getGrantedtoId() {
        return grantedtoId;
    }

    /**
     *
     * @param grantedtoId
     */
    public void setGrantedtoId(User grantedtoId) {
        this.grantedtoId = grantedtoId;
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
        if (!(object instanceof Privilege)) {
            return false;
        }
        Privilege other = (Privilege) object;
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
        return "org.afapa.exam.generated_entities.Privilege[ id=" + id + " ]";
    }

}
