/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
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
@DiscriminatorValue(value = "org")
@XmlRootElement
public class Organization extends OrganizationalUnit implements Serializable {

    private static final long serialVersionUID = 11L;

    private String name;

    @ManyToOne
    private User createdBy;

    private Date createdOnDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
    private Set<Department> departments;

    public Organization() {
    }
    public Organization(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "org.afapa.exam.entity.Organization[ id=" + id + " ]";
    }

}
