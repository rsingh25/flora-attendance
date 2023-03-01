package com.rps.flora.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AttendanceEntry.
 */
@Entity
@Table(name = "attendance_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttendanceEntry extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "year_month")
    private String yearMonth;

    @Column(name = "att_start")
    private Instant attStart;

    @Column(name = "att_end")
    private Instant attEnd;

    @Column(name = "comment")
    private String comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AttendanceEntry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendanceEntry createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public AttendanceEntry createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public AttendanceEntry lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public AttendanceEntry lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public String getYearMonth() {
        return this.yearMonth;
    }

    public AttendanceEntry yearMonth(String yearMonth) {
        this.setYearMonth(yearMonth);
        return this;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Instant getAttStart() {
        return this.attStart;
    }

    public AttendanceEntry attStart(Instant attStart) {
        this.setAttStart(attStart);
        return this;
    }

    public void setAttStart(Instant attStart) {
        this.attStart = attStart;
    }

    public Instant getAttEnd() {
        return this.attEnd;
    }

    public AttendanceEntry attEnd(Instant attEnd) {
        this.setAttEnd(attEnd);
        return this;
    }

    public void setAttEnd(Instant attEnd) {
        this.attEnd = attEnd;
    }

    public String getComment() {
        return this.comment;
    }

    public AttendanceEntry comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceEntry)) {
            return false;
        }
        return id != null && id.equals(((AttendanceEntry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceEntry{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", yearMonth='" + getYearMonth() + "'" +
            ", attStart='" + getAttStart() + "'" +
            ", attEnd='" + getAttEnd() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
