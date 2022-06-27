/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.identity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.afapa.exam.entity.User;

/**
 *
 * @author AFAPA-group
 */
@NamedQueries(value = {
    @NamedQuery(name = "deleteToken", query = "DELETE FROM Token t WHERE t.userToken.userId = :userId AND t.userToken.token = :token"),
    @NamedQuery(name = "getTokensByUserId", query = "SELECT token FROM Token token WHERE token.userToken.userId = :userId")
})
@Getter
@Entity
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserToken userToken;
    @Column(name = "MODIFIEDAT")
//    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp modifiedAt;
    @Column(name = "EXPIRETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp expireTime;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    protected Token() {

    }

    public Token(User user, String token) {
        this(user, token, 86400L);
    }

    public Token(User user, String token, long lifeSpanSeconds) {
        this.user = user;
        this.userToken = new UserToken(user.getId(), token);
        this.expireTime = Timestamp.from(Instant.now().plusSeconds(lifeSpanSeconds));
    }

    @PreUpdate
    @PrePersist
    private void preUpdate() {
        this.modifiedAt = Timestamp.from(Instant.now());
        if (this.expireTime == null) {
            this.expireTime = Timestamp.from(Instant.now().plusSeconds(86400));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(userToken, modifiedAt);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Token)) {
            return false;
        }
        return this.userToken.equals(((Token) other).userToken);
    }

    public String getString() {
        return userToken.getUserId() + ":" + userToken.getToken();
    }

    @Setter(AccessLevel.PROTECTED)
    @Getter
    @Embeddable
    public static class UserToken implements Serializable {

        @NotNull
        @Column(name = "USER_ID")
        long userId;
        @NotNull
        @Column(name = "TOKEN")
        String token;

        public UserToken() {
        }

        private UserToken(long userId, String token) {
            this.userId = userId;
            this.token = token;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, token);
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof UserToken)) {
                return false;
            }
            return this.userId == ((UserToken) other).userId && this.token.equals(((UserToken) other).token);
        }
    }
}
