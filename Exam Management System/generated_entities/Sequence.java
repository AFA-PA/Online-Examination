/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.afapa.exam.generated_entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author AFAPA-group
 */
@Entity
@Table(catalog = "ExamManDb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Sequence.findAll", query = "SELECT s FROM Sequence s"),
    @NamedQuery(name = "Sequence.findBySeqName", query = "SELECT s FROM Sequence s WHERE s.seqName = :seqName"),
    @NamedQuery(name = "Sequence.findBySeqCount", query = "SELECT s FROM Sequence s WHERE s.seqCount = :seqCount")})
@SuppressWarnings("UniqueEntityName")
public class Sequence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SEQ_NAME", nullable = false, length = 50)
    private String seqName;
    @Column(name = "SEQ_COUNT")
    private BigInteger seqCount;

    /**
     *
     */
    public Sequence() {
    }

    /**
     *
     * @param seqName
     */
    public Sequence(String seqName) {
        this.seqName = seqName;
    }

    /**
     *
     * @return
     */
    public String getSeqName() {
        return seqName;
    }

    /**
     *
     * @param seqName
     */
    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    /**
     *
     * @return
     */
    public BigInteger getSeqCount() {
        return seqCount;
    }

    /**
     *
     * @param seqCount
     */
    public void setSeqCount(BigInteger seqCount) {
        this.seqCount = seqCount;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seqName != null ? seqName.hashCode() : 0);
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
        if (!(object instanceof Sequence)) {
            return false;
        }
        Sequence other = (Sequence) object;
        if ((this.seqName == null && other.seqName != null) || (this.seqName != null && !this.seqName.equals(other.seqName))) {
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
        return "org.afapa.exam.generated_entities.Sequence[ seqName=" + seqName + " ]";
    }

}
