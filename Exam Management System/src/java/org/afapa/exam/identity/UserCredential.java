/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.security.enterprise.credential.Credential;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import org.afapa.exam.entity.User;

/**
 *
 * @author AFAPA-group
 */
@NamedQueries(value = {
    @NamedQuery(name = "getUserCredential", query = "SELECT cred FROM UserCredential cred WHERE cred.user.id = :userId")})
@Getter
@Entity
public class UserCredential implements Credential, Serializable {

    @Id
    @OneToOne
    private User user;
    private String hashedPassword;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Token> tokens;

    @XmlTransient
    @Transient
    Token givenToken;
    @XmlTransient
    @Transient
    private String password;
    @Transient
    @XmlTransient
    private final CredType credType;

    protected UserCredential() {
        this.credType = CredType.NULL;
    }

    public UserCredential(User caller, String pass) {
        this(caller, pass, null);
    }

    public UserCredential(User caller, String pass, String hashedPassword) {
        this.credType = CredType.PASS_CRED;
        this.user = caller;
        this.password = pass;
        this.hashedPassword = hashedPassword;
    }

    public UserCredential(User caller, Token token) {
        this.credType = CredType.TOKEN_CRED;
        this.user = caller;
        this.givenToken = token;
    }

    public void clearCredential() {
        password.replaceAll(".", "\0");
    }

    public long getId() {
        return this.user.getId();
    }

    public enum CredType {
        PASS_CRED,
        TOKEN_CRED,
        NULL;
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (hashedPassword == null && password == null) {
            throw new NullPointerException("could not persist without hashedPassword");
        }
        if (hashedPassword == null && password != null) {

            Pbkdf2PasswordHasher hasher = new Pbkdf2PasswordHasher();
            hashedPassword = hasher.generate(password.toCharArray());
        }
    }
}
