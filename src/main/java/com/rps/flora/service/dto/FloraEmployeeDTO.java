package com.rps.flora.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.rps.flora.domain.FloraEmployee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloraEmployeeDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Instant attStartTime1;

    private Instant attEndTime1;

    private Instant attStartTime2;

    private UserDTO internalUser;

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

    public Instant getAttStartTime1() {
        return attStartTime1;
    }

    public void setAttStartTime1(Instant attStartTime1) {
        this.attStartTime1 = attStartTime1;
    }

    public Instant getAttEndTime1() {
        return attEndTime1;
    }

    public void setAttEndTime1(Instant attEndTime1) {
        this.attEndTime1 = attEndTime1;
    }

    public Instant getAttStartTime2() {
        return attStartTime2;
    }

    public void setAttStartTime2(Instant attStartTime2) {
        this.attStartTime2 = attStartTime2;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloraEmployeeDTO)) {
            return false;
        }

        FloraEmployeeDTO floraEmployeeDTO = (FloraEmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, floraEmployeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloraEmployeeDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", attStartTime1='" + getAttStartTime1() + "'" +
            ", attEndTime1='" + getAttEndTime1() + "'" +
            ", attStartTime2='" + getAttStartTime2() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
