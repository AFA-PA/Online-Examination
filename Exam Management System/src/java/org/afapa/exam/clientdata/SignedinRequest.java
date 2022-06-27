/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.clientdata;

import java.io.Serializable;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.afapa.exam.identity.Token;

/**
 *
 * @author AFAPA-group
 * @param <Data>
 */
@Setter
@Getter(AccessLevel.PUBLIC)
@XmlRootElement
public class SignedinRequest<Data> implements Serializable {

    @Pattern(regexp = "^(\\d+):(?:[a-zA-Z0-9+/]{4})+(?:[a-zA-Z0-9+/]{2}==|[a-zA-Z0-9+/]{3}=)?")
    private String tokenString;
    private Data entity;

    public SignedinRequest() {
    }

    public static SignedinRequest fromToken(Token token) {
        return new SignedinRequest(token.getString());
    }

    public SignedinRequest(String tokenString) {
        this.entity = null;
        this.tokenString = tokenString;
    }

    @Override
    public String toString() {
        return "request from user with id: " + tokenString.split(":")[0];
    }

    @XmlTransient
    public String getUserToken() {
        return tokenString.split(":")[1];
    }

    @XmlTransient
    public long getUserId() {
        return Long.parseLong(tokenString.split(":")[0]);
    }
}
