package com.rps.flora.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.rps.flora.domain.FloraEmployee} entity. This class is used
 * in {@link com.rps.flora.web.rest.FloraEmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /flora-employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloraEmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private InstantFilter attStartTime1;

    private InstantFilter attEndTime1;

    private InstantFilter attStartTime2;

    private LongFilter internalUserId;

    private Boolean distinct;

    public FloraEmployeeCriteria() {}

    public FloraEmployeeCriteria(FloraEmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.attStartTime1 = other.attStartTime1 == null ? null : other.attStartTime1.copy();
        this.attEndTime1 = other.attEndTime1 == null ? null : other.attEndTime1.copy();
        this.attStartTime2 = other.attStartTime2 == null ? null : other.attStartTime2.copy();
        this.internalUserId = other.internalUserId == null ? null : other.internalUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FloraEmployeeCriteria copy() {
        return new FloraEmployeeCriteria(this);
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

    public InstantFilter getAttStartTime1() {
        return attStartTime1;
    }

    public InstantFilter attStartTime1() {
        if (attStartTime1 == null) {
            attStartTime1 = new InstantFilter();
        }
        return attStartTime1;
    }

    public void setAttStartTime1(InstantFilter attStartTime1) {
        this.attStartTime1 = attStartTime1;
    }

    public InstantFilter getAttEndTime1() {
        return attEndTime1;
    }

    public InstantFilter attEndTime1() {
        if (attEndTime1 == null) {
            attEndTime1 = new InstantFilter();
        }
        return attEndTime1;
    }

    public void setAttEndTime1(InstantFilter attEndTime1) {
        this.attEndTime1 = attEndTime1;
    }

    public InstantFilter getAttStartTime2() {
        return attStartTime2;
    }

    public InstantFilter attStartTime2() {
        if (attStartTime2 == null) {
            attStartTime2 = new InstantFilter();
        }
        return attStartTime2;
    }

    public void setAttStartTime2(InstantFilter attStartTime2) {
        this.attStartTime2 = attStartTime2;
    }

    public LongFilter getInternalUserId() {
        return internalUserId;
    }

    public LongFilter internalUserId() {
        if (internalUserId == null) {
            internalUserId = new LongFilter();
        }
        return internalUserId;
    }

    public void setInternalUserId(LongFilter internalUserId) {
        this.internalUserId = internalUserId;
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
        final FloraEmployeeCriteria that = (FloraEmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(attStartTime1, that.attStartTime1) &&
            Objects.equals(attEndTime1, that.attEndTime1) &&
            Objects.equals(attStartTime2, that.attStartTime2) &&
            Objects.equals(internalUserId, that.internalUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            attStartTime1,
            attEndTime1,
            attStartTime2,
            internalUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloraEmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (attStartTime1 != null ? "attStartTime1=" + attStartTime1 + ", " : "") +
            (attEndTime1 != null ? "attEndTime1=" + attEndTime1 + ", " : "") +
            (attStartTime2 != null ? "attStartTime2=" + attStartTime2 + ", " : "") +
            (internalUserId != null ? "internalUserId=" + internalUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
