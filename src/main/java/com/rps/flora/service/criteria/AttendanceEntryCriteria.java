package com.rps.flora.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.rps.flora.domain.AttendanceEntry} entity. This class is used
 * in {@link com.rps.flora.web.rest.AttendanceEntryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attendance-entries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttendanceEntryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private StringFilter yearMonth;

    private InstantFilter attStart;

    private InstantFilter attEnd;

    private StringFilter comment;

    private Boolean distinct;

    public AttendanceEntryCriteria() {}

    public AttendanceEntryCriteria(AttendanceEntryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.yearMonth = other.yearMonth == null ? null : other.yearMonth.copy();
        this.attStart = other.attStart == null ? null : other.attStart.copy();
        this.attEnd = other.attEnd == null ? null : other.attEnd.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AttendanceEntryCriteria copy() {
        return new AttendanceEntryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getYearMonth() {
        return yearMonth;
    }

    public StringFilter yearMonth() {
        if (yearMonth == null) {
            yearMonth = new StringFilter();
        }
        return yearMonth;
    }

    public void setYearMonth(StringFilter yearMonth) {
        this.yearMonth = yearMonth;
    }

    public InstantFilter getAttStart() {
        return attStart;
    }

    public InstantFilter attStart() {
        if (attStart == null) {
            attStart = new InstantFilter();
        }
        return attStart;
    }

    public void setAttStart(InstantFilter attStart) {
        this.attStart = attStart;
    }

    public InstantFilter getAttEnd() {
        return attEnd;
    }

    public InstantFilter attEnd() {
        if (attEnd == null) {
            attEnd = new InstantFilter();
        }
        return attEnd;
    }

    public void setAttEnd(InstantFilter attEnd) {
        this.attEnd = attEnd;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceEntryCriteria that = (AttendanceEntryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(yearMonth, that.yearMonth) &&
            Objects.equals(attStart, that.attStart) &&
            Objects.equals(attEnd, that.attEnd) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, createdDate, lastModifiedBy, lastModifiedDate, yearMonth, attStart, attEnd, comment, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceEntryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (yearMonth != null ? "yearMonth=" + yearMonth + ", " : "") +
            (attStart != null ? "attStart=" + attStart + ", " : "") +
            (attEnd != null ? "attEnd=" + attEnd + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
