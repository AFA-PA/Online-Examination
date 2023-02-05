/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.security.enterprise.credential.AbstractClearableCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import org.afapa.exam.entity.User;

/**
 *
 * @author ketij
 */
@Entity
public class UserCredential extends AbstractClearableCredential implements Credential, Serializable {

    @Id
    @OneToOne
    private User user;
    private String hashedPassword;
    @Transient
    private final Password password;

    UserCredential(User caller, Password password) {
        this.user = caller;
        this.password = password;
    }

    UserCredential() {
        this.password = null;
    }

    protected Password getPassword() {
        return this.password;
    }

    protected User getUser() {
        return this.user;
    }

    @Override
    public void clearCredential() {
        password.clear();
    }

    protected String getHashedPassword() {
        return this.hashedPassword;
    }
}
