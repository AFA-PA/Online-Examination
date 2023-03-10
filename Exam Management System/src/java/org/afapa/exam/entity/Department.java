/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author AFAPA-group
 */
@Setter
@Getter
@Entity
@DiscriminatorValue(value = "dep")
@XmlRootElement
public class Department extends OrganizationalUnit implements Serializable {

    private static final long serialVersionUID = 11L;

    private String name;

    private Date createdODate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Organization organization;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "department")
    private List<Course> courses;

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
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
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
        return name;
    }

    @PrePersist
    private void prePersist() {
        createdODate = new Date(Instant.now().toEpochMilli());
    }
}
