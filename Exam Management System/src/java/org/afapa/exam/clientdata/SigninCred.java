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

/**
 *
 * @author AFAPA-group
 */
@Setter
@Getter(AccessLevel.PUBLIC)
@XmlRootElement
public class SigninCred implements Serializable {

    private String email;
    private String password;

    @Override
    public String toString() {
        return "credential for user with email: " + email;
    }

}
