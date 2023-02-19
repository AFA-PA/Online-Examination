/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t"),
    @NamedQuery(name = "Token.findByExpiretime", query = "SELECT t FROM Token t WHERE t.expiretime = :expiretime"),
    @NamedQuery(name = "Token.findByModifiedat", query = "SELECT t FROM Token t WHERE t.modifiedat = :modifiedat"),
    @NamedQuery(name = "Token.findByUserId", query = "SELECT t FROM Token t WHERE t.tokenPK.userId = :userId"),
    @NamedQuery(name = "Token.findByToken", query = "SELECT t FROM Token t WHERE t.tokenPK.token = :token")})
@SuppressWarnings("UniqueEntityName")
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @EmbeddedId
    protected TokenPK tokenPK;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiretime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
    @JoinTable(name = "USERCREDENTIAL_TOKEN", joinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false),
        @JoinColumn(name = "TOKEN", referencedColumnName = "TOKEN", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "UserCredential_USER_ID", referencedColumnName = "USER_ID", nullable = false)})
    @ManyToMany
    private List<Usercredential> usercredentialList;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    /**
     *
     */
    public Token() {
    }

    /**
     *
     * @param tokenPK
     */
    public Token(TokenPK tokenPK) {
        this.tokenPK = tokenPK;
    }

    /**
     *
     * @param userId
     * @param token
     */
    public Token(long userId, String token) {
        this.tokenPK = new TokenPK(userId, token);
    }

    /**
     *
     * @return
     */
    public TokenPK getTokenPK() {
        return tokenPK;
    }

    /**
     *
     * @param tokenPK
     */
    public void setTokenPK(TokenPK tokenPK) {
        this.tokenPK = tokenPK;
    }

    /**
     *
     * @return
     */
    public Date getExpiretime() {
        return expiretime;
    }

    /**
     *
     * @param expiretime
     */
    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }

    /**
     *
     * @return
     */
    public Date getModifiedat() {
        return modifiedat;
    }

    /**
     *
     * @param modifiedat
     */
    public void setModifiedat(Date modifiedat) {
        this.modifiedat = modifiedat;
    }

    /**
     *
     * @return
     */
    public List<Usercredential> getUsercredentialList() {
        return usercredentialList;
    }

    /**
     *
     * @param usercredentialList
     */
    public void setUsercredentialList(List<Usercredential> usercredentialList) {
        this.usercredentialList = usercredentialList;
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
        hash += (tokenPK != null ? tokenPK.hashCode() : 0);
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
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.tokenPK == null && other.tokenPK != null) || (this.tokenPK != null && !this.tokenPK.equals(other.tokenPK))) {
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
        return "org.afapa.exam.generated_entities.Token[ tokenPK=" + tokenPK + " ]";
    }

}
