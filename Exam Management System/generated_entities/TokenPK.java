/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author AFAPA-group
 */
@Embeddable
@SuppressWarnings("UniqueEntityName")
public class TokenPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "USER_ID", nullable = false)
    private long userId;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String token;

    /**
     *
     */
    public TokenPK() {
    }

    /**
     *
     * @param userId
     * @param token
     */
    public TokenPK(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    /**
     *
     * @return
     */
    public long getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (token != null ? token.hashCode() : 0);
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
        if (!(object instanceof TokenPK)) {
            return false;
        }
        TokenPK other = (TokenPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if ((this.token == null && other.token != null) || (this.token != null && !this.token.equals(other.token))) {
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
        return "org.afapa.exam.generated_entities.TokenPK[ userId=" + userId + ", token=" + token + " ]";
    }

}
