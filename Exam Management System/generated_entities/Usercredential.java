/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Usercredential.findAll", query = "SELECT u FROM Usercredential u"),
    @NamedQuery(name = "Usercredential.findByHashedpassword", query = "SELECT u FROM Usercredential u WHERE u.hashedpassword = :hashedpassword"),
    @NamedQuery(name = "Usercredential.findByUserId", query = "SELECT u FROM Usercredential u WHERE u.userId = :userId")})
@SuppressWarnings("UniqueEntityName")
public class Usercredential implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(length = 255)
    private String hashedpassword;
    @Id
    @Basic(optional = false)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;
    @ManyToMany(mappedBy = "usercredentialList")
    private List<Token> tokenList;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    /**
     *
     */
    public Usercredential() {
    }

    /**
     *
     * @param userId
     */
    public Usercredential(Long userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getHashedpassword() {
        return hashedpassword;
    }

    /**
     *
     * @param hashedpassword
     */
    public void setHashedpassword(String hashedpassword) {
        this.hashedpassword = hashedpassword;
    }

    /**
     *
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
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
        if (!(object instanceof Usercredential)) {
            return false;
        }
        Usercredential other = (Usercredential) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
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
        return "org.afapa.exam.generated_entities.Usercredential[ userId=" + userId + " ]";
    }

}
