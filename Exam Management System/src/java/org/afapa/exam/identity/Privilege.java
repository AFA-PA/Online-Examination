/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;
import org.afapa.exam.entity.Course;
import org.afapa.exam.entity.Department;
import org.afapa.exam.entity.OrganizationalUnit;
import org.afapa.exam.entity.User;

/**
 *
 * @author AFAPA-group
 */
@NamedQuery(name = "getUserPrivileges",
        query = "SELECT priv FROM Privilege priv WHERE priv.grantedTo.email = :email AND priv.grantedON = :unit")
@Setter
@Getter
@Entity
public class Privilege implements Serializable {

    public static final int VIEW = 1;
    public static final int PREVIEW = 2;
    public static final int EDIT = 4;
    public static final int CREATE = 8;
    public static final int DELETE = 16;
    public static final int PERMIT = 32;
    public static final int PRESIDENCY = 64;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User grantedTo;
    @ManyToOne(fetch = FetchType.EAGER)
    private OrganizationalUnit grantedON;
    @Basic(fetch = FetchType.EAGER)
    private int permission;

    public Privilege() {
    }

    public Privilege(User grantedTo, OrganizationalUnit grantedON, int permission) {
        this.grantedTo = grantedTo;
        this.grantedON = grantedON;
        this.permission = permission;
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
        if (!(object instanceof Privilege)) {
            return false;
        }
        Privilege other = (Privilege) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.afapa.exam.identity.Privilege[ id=" + id + " ]";
    }

    public static boolean isPermittedTo(List<Privilege> privileges, OrganizationalUnit unit, int permission, boolean exact) {
        int x;
        for (Privilege privilege : privileges) {
            x = privilege.permission & permission;
            if (exact ? x == permission : x > 0) {
                return getHeirarchichalPermission(privilege, unit);
            }
        }
        return false;
    }

    private static boolean getHeirarchichalPermission(Privilege privilege, OrganizationalUnit unit) {
        if (privilege.grantedON.equals(unit)) {
            return true;
        }
        switch (unit.getUnitType()) {
            case "crs":
                unit = ((Course) unit).getDepartment();
                if (privilege.grantedON.equals(unit)) {
                    return true;
                }
            case "dep":
                unit = ((Department) unit).getOrganization();
                if (privilege.grantedON.equals(unit)) {
                    return true;
                }
            case "org":
                return privilege.grantedON.equals(unit);
            default:
                throw new AssertionError();
        }
    }
}
