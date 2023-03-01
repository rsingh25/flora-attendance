package com.rps.flora.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.rps.flora.domain.AttendanceEntry} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttendanceEntryDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private String yearMonth;

    private Instant attStart;

    private Instant attEnd;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Instant getAttStart() {
        return attStart;
    }

    public void setAttStart(Instant attStart) {
        this.attStart = attStart;
    }

    public Instant getAttEnd() {
        return attEnd;
    }

    public void setAttEnd(Instant attEnd) {
        this.attEnd = attEnd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceEntryDTO)) {
            return false;
        }

        AttendanceEntryDTO attendanceEntryDTO = (AttendanceEntryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attendanceEntryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceEntryDTO{" +
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
