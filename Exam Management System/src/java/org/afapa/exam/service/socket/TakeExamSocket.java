/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.service.socket;

import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.afapa.exam.entity.Answer;
import org.afapa.exam.entity.Question;

/**
 *
 * @author ketij
 */
@ServerEndpoint("/takeExam")
public class TakeExamSocket {

    @OnMessage
    public Question answer(Answer answer, Session session) {

        return null;
    }

    @OnMessage
    public boolean ping(PongMessage pong) {
//        pong.getApplicationData()
        return false;
    }

}
