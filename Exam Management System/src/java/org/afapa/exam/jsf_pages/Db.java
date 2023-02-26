/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.jsf_pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.afapa.exam.entity.Choice;
import org.afapa.exam.entity.Course;
import org.afapa.exam.entity.CourseRegistration;
import org.afapa.exam.entity.Department;
import org.afapa.exam.entity.Exam;
import org.afapa.exam.entity.Organization;
import org.afapa.exam.entity.Question;
import org.afapa.exam.entity.User;

/**
 *
 * @author ketij
 */
public class Db {

    private static final Logger logger = Logger.getLogger("JSP_Auth");

    Connection conn;
    @PersistenceContext
    EntityManager em;

    public Db() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ExamManDb", "afapa", "AFA-PA");
    }

    public List<Exam> getAllExams() throws SQLException {

        PreparedStatement stm = conn.prepareStatement("Select * from EXAM");
        ResultSet rs = stm.executeQuery();
        List<Exam> exams = new ArrayList<>();
        int size = 0;
        while (rs.next()) {
            Exam e = new Exam();
            e.setId(rs.getLong("ID"));
            e.setName(rs.getString("NAME"));
            e.setMinutesAllowed(rs.getInt("MINUTESALLOWED"));
            e.setStartTime(rs.getTimestamp("STARTTIME"));
            e.setRegistration(getRegistration(rs.getLong("REGISTRATION_id")));
            e.setQuestions(getQuestions(e));
            exams.add(e);
            logger.log(Level.INFO, "adding exam {0}", size++);
        }

        return exams;
    }

    public List<Exam> getExams(long reg_d) throws SQLException {

        PreparedStatement stm = conn.prepareStatement("Select * from EXAM where REGISTRATION_id=?");
        stm.setLong(1, reg_d);
        ResultSet rs = stm.executeQuery();
        List<Exam> exams = new ArrayList<>();
        int size = 0;
        while (rs.next()) {
            Exam e = new Exam();
            e.setId(rs.getLong("ID"));
            e.setName(rs.getString("NAME"));
            e.setMinutesAllowed(rs.getInt("MINUTESALLOWED"));
            e.setStartTime(rs.getTimestamp("STARTTIME"));
            e.setRegistration(getRegistration(rs.getLong("REGISTRATION_id")));
            e.setQuestions(getQuestions(e));
            exams.add(e);
            logger.log(Level.INFO, "adding exam {0}", size++);
        }

        return exams;
    }

    public List<Question> getQuestions(Exam e) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from QUESTION where EXAM_ID=?");
        stm.setLong(1, e.getId());
        ResultSet rs = stm.executeQuery();
        List<Question> qns = new ArrayList<>();
        int size = 0;
        while (rs.next()) {
            Question q = new Question();
            q.setExam(e);
            q.setId(rs.getLong("ID"));
            q.setQuestion(rs.getString("QUESTION"));
            q.setChoices(getChoices(q));
            qns.add(q);
            logger.log(Level.INFO, "adding question {0}", size++);
        }
        return qns;
    }

    public List<Choice> getChoices(Question q) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from CHOICE where QUESTION_ID=?");
        stm.setLong(1, q.getId());
        ResultSet rs = stm.executeQuery();
        List<Choice> choices = new ArrayList<>();
        while (rs.next()) {
            Choice ch = new Choice();
            ch.setQuestion(q);
            ch.setId(rs.getLong("ID"));
            ch.setChoice(rs.getString("CHOICE"));
            ch.setCorrect(rs.getBoolean("CORRECT"));
            choices.add(ch);
        }
        return choices;
    }

    public CourseRegistration getRegistration(long id) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from CourseRegistration where ID=?");
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();
        CourseRegistration reg = new CourseRegistration(rs.getLong("ID"));
        reg.setSemester(rs.getString("semester"));
        reg.setYear(rs.getInt("year"));
        reg.setCourse(getCourse(rs.getLong("course_id")));
        return reg;
    }

    public Course getCourse(long id) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from COURSE where ID=?");
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();
        Course c = new Course();
        c.setId(rs.getLong("ID"));
        c.setCode(rs.getString("CODE"));
        c.setName(rs.getString("NAME"));
        c.setDepartment(getDepartment(rs.getLong("DEPARTMENT_ID")));
        return c;
    }

    public Department getDepartment(long id) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from DEPARTMENT where ID=?");
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();
        Department dep = new Department();
        dep.setId(rs.getLong("ID"));
        dep.setName(rs.getString("NAME"));
        dep.setUnitType("dep");
        dep.setCreatedODate(rs.getDate("CREATEDODATE"));
        dep.setOrganization(getOrganization(rs.getLong("ORGANIZATION_ID")));

        return dep;
    }

    public Organization getOrganization(long id) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from ORGANIZATION where ID=?");
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();
        Organization org = new Organization();
        org.setId(rs.getLong("ID"));
        org.setName(rs.getString("NAME"));
        org.setUnitType("org");
        org.setCreatedOnDate(rs.getDate("CREATEDONDATE"));
        org.setCreatedBy(getUser(rs.getLong("CREATEDBY_ID")));
        return org;
    }

    public User getUser(long id) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("Select * from USER where ID=?");
        stm.setLong(1, id);
        ResultSet rs = stm.executeQuery();
        rs.next();
        User u = new User(rs.getString("EMAIL"), rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"), rs.getString("PHONENUMBER"));
        u.setId(rs.getLong("ID"));
        return u;
    }
}

//ID              | bigint(20)   | NO   | PRI | NULL    |       |
//| MINUTESALLOWED  | int(11)      | YES  |     | NULL    |       |
//| NAME            | varchar(255) | YES  |     | NULL    |       |
//| STARTTIME       | datetime     | YES  |     | NULL    |       |
//| REGISTRATION_id
//}
