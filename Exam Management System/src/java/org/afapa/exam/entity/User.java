/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author AFAPA-group
 */
@NamedQueries(value = {
    @NamedQuery(name = "getUserByEmail", query = "SELECT user FROM User user WHERE user.email = :email"),
    @NamedQuery(name = "getUserById", query = "SELECT user FROM User user WHERE user.id = :id")
})
@Setter
@Getter
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @Basic(fetch = FetchType.EAGER)
    protected String firstName;

    @NotNull
    @Basic(fetch = FetchType.EAGER)
    protected String lastName;

    @NotNull
    protected String phoneNumber;

    @NotNull
    @Email
    @Basic(fetch = FetchType.EAGER)
    protected String email;

//    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taker")
    private List<ExamTaken> takenExams;

    @ManyToMany(mappedBy = "registeredUsers")
    private List<CourseRegistration> courseRegistrations;

    public User() {
        email = "";
        firstName = "";
        lastName = "";
        phoneNumber = "";
    }

    public User(String email, String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
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
        return "org.afapa.exam.entity.User[ id=" + id + " ]";
    }

}
