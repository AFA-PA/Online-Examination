package org.afapa.exam.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UNIQUE_EXAM_TAKER", columnNames = {"EXAM_ID", "TAKER_ID"}))
@NamedQuery(name = "getUserTakenExam", query = "SELECT DISTINCT et FROM ExamTaken et WHERE et.exam.id=:exam_id AND et.taker.id=:taker_id")
@Setter
@Getter
@XmlRootElement
public class ExamTaken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private User taker;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Exam exam;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "EXAMTAKEN_ANSWER", joinColumns = {
        @JoinColumn(name = "ExamTaken_ID", table = "EXAMTAKEN")})
    private List<Choice> answers;

    public float getResult() {
        float got = 0;
        for (Choice answer : answers) {
            if (answer.isCorrect()) {
                got += 1;
            }
        }
        return got / exam.getQuestions().size();
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
        if (!(object instanceof ExamTaken)) {
            return false;
        }
        ExamTaken other = (ExamTaken) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return exam.getName() + " by " + taker.getEmail();
    }

}
