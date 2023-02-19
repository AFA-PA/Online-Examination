/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByFirstname", query = "SELECT u FROM User u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "User.findByPhonenumber", query = "SELECT u FROM User u WHERE u.phonenumber = :phonenumber")})
@SuppressWarnings("UniqueEntityName")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 255)
    private String email;
    @Column(length = 255)
    private String firstname;
    @Column(length = 255)
    private String lastname;
    @Column(length = 255)
    private String phonenumber;
    @OneToMany(mappedBy = "grantedtoId")
    private List<Privilege> privilegeList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Usercredential usercredential;
    @OneToMany(mappedBy = "createdbyId")
    private List<Organization> organizationList;
    @OneToMany(mappedBy = "takerId")
    private List<Examtaken> examtakenList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Admin admin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Token> tokenList;

    /**
     *
     */
    public User() {
    }

    /**
     *
     * @param id
     */
    public User(Long id) {
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
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     *
     * @return
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     *
     * @param phonenumber
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     *
     * @return
     */
    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    /**
     *
     * @param privilegeList
     */
    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    /**
     *
     * @return
     */
    public Usercredential getUsercredential() {
        return usercredential;
    }

    /**
     *
     * @param usercredential
     */
    public void setUsercredential(Usercredential usercredential) {
        this.usercredential = usercredential;
    }

    /**
     *
     * @return
     */
    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    /**
     *
     * @param organizationList
     */
    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    /**
     *
     * @return
     */
    public List<Examtaken> getExamtakenList() {
        return examtakenList;
    }

    /**
     *
     * @param examtakenList
     */
    public void setExamtakenList(List<Examtaken> examtakenList) {
        this.examtakenList = examtakenList;
    }

    /**
     *
     * @return
     */
    public Admin getAdmin() {
        return admin;
    }

    /**
     *
     * @param admin
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     *
     * @return
     */
    public List<Token> getTokenList() {
        return tokenList;
    }

    /**
     *
     * @param tokenList
     */
    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
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
        return "org.afapa.exam.generated_entities.User[ id=" + id + " ]";
    }

}
