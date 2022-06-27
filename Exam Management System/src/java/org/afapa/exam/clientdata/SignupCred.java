/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.clientdata;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.afapa.exam.entity.User;

/**
 *
 * @author AFAPA-group
 */
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@XmlRootElement
public class SignupCred implements Serializable {

    private User user;
    private String password;

    public SignupCred() {
    }

    @Override
    public String toString() {
        return "credential for user with id: " + user.getId();
    }
}
