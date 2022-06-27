/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author AFAPA-group
 */
@NamedQueries(value = {
    @NamedQuery(name = "getAdminUser",
            query = "SELECT admin FROM Admin admin WHERE admin.user.email = :userEmial"),
    @NamedQuery(name = "isUserAdmin", query = "SELECT COUNT(a) FROM Admin a WHERE a.user.email = :email")
})
@Getter
@Setter
@Entity
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user != null ? user.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the user fields are not set
        if (!(object instanceof Admin)) {
            return false;
        }
        Admin other = (Admin) object;
        return !((this.user == null && other.user != null) || (this.user != null && !this.user.equals(other.user)));
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "org.afapa.exam.entity.Admin[ id=" + user + " ]";
    }

}
