///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package org.afapa.exam.entity;
//
//import java.io.Serializable;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import javax.xml.bind.annotation.XmlRootElement;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// *
// * @author AFAPA-group
// */
//@NamedQuery(name = "Answer.getChoosed", query = "SELECT c FROM Answer a, Question q, Choice c WHERE c.id=a.choosed.id AND a.id=:id")
//@Setter
//@Getter
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"FORQUESTION_ID", "CHOOSED_ID"}))
//@XmlRootElement
//public class Answer implements Serializable {
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private Question forQuestion;
//
//    @ManyToOne
//    private Choice choosed;
//
//    /**
//     *
//     * @return
//     */
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    /**
//     *
//     * @param object
//     * @return
//     */
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Answer)) {
//            return false;
//        }
//        Answer other = (Answer) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     *
//     * @return
//     */
//    @Override
//    public String toString() {
//        return "org.afapa.exam.entity.Answer[ id=" + id + " ]";
//    }
//
//}
