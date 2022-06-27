/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Organizationalunit.findAll", query = "SELECT o FROM Organizationalunit o"),
    @NamedQuery(name = "Organizationalunit.findById", query = "SELECT o FROM Organizationalunit o WHERE o.id = :id"),
    @NamedQuery(name = "Organizationalunit.findByUnitType", query = "SELECT o FROM Organizationalunit o WHERE o.unitType = :unitType")})
@SuppressWarnings("UniqueEntityName")
public class Organizationalunit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 3)
    private String unitType;
    @OneToMany(mappedBy = "grantedonId")
    private List<Privilege> privilegeList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "organizationalunit")
    private Department department;
    @OneToMany(mappedBy = "organizationId")
    private List<Department> departmentList;
    @OneToMany(mappedBy = "departmentId")
    private List<Course> courseList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "organizationalunit")
    private Course course;
    @OneToMany(mappedBy = "examId")
    private List<Question> questionList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "organizationalunit")
    private Organization organization;
    @OneToMany(mappedBy = "examId")
    private List<Examtaken> examtakenList;
    @OneToMany(mappedBy = "courseId")
    private List<Exam> examList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "organizationalunit")
    private Exam exam;

    /**
     *
     */
    public Organizationalunit() {
    }

    /**
     *
     * @param id
     */
    public Organizationalunit(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     *
     * @param unitType
     */
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    /**
     *
     * @return
     */
    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    /**
     *
     * @param privilegeList
     */
    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    /**
     *
     * @return
     */
    public Department getDepartment() {
        return department;
    }

    /**
     *
     * @param department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     *
     * @return
     */
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    /**
     *
     * @param departmentList
     */
    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    /**
     *
     * @return
     */
    public List<Course> getCourseList() {
        return courseList;
    }

    /**
     *
     * @param courseList
     */
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    /**
     *
     * @return
     */
    public Course getCourse() {
        return course;
    }

    /**
     *
     * @param course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     *
     * @return
     */
    public List<Question> getQuestionList() {
        return questionList;
    }

    /**
     *
     * @param questionList
     */
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    /**
     *
     * @return
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     */
    public List<Examtaken> getExamtakenList() {
        return examtakenList;
    }

    /**
     *
     * @param examtakenList
     */
    public void setExamtakenList(List<Examtaken> examtakenList) {
        this.examtakenList = examtakenList;
    }

    /**
     *
     * @return
     */
    public List<Exam> getExamList() {
        return examList;
    }

    /**
     *
     * @param examList
     */
    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    /**
     *
     * @return
     */
    public Exam getExam() {
        return exam;
    }

    /**
     *
     * @param exam
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        if (!(object instanceof Organizationalunit)) {
            return false;
        }
        Organizationalunit other = (Organizationalunit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
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
        return "org.afapa.exam.generated_entities.Organizationalunit[ id=" + id + " ]";
    }

}
