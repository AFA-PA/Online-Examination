/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Basic(fetch = FetchType.EAGER)
    private String firstName;

    @NotNull
    @Basic(fetch = FetchType.EAGER)
    private String lastName;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    @Basic(fetch = FetchType.EAGER)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taker")
    private Set<ExamTaken> takenExams;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.afapa.exam.entity.User[ id=" + id + " ]";
    }

//    @java.lang.SuppressWarnings(value = "all")
//    @XmlTransient
//    public Set<ExamTaken> getTakenExams() {
//    }

}
