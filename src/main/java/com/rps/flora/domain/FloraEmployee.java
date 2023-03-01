package com.rps.flora.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FloraEmployee.
 */
@Entity
@Table(name = "flora_employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloraEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "att_start_time_1")
    private Instant attStartTime1;

    @Column(name = "att_end_time_1")
    private Instant attEndTime1;

    @Column(name = "att_start_time_2")
    private Instant attStartTime2;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FloraEmployee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public FloraEmployee createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public FloraEmployee createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public FloraEmployee lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public FloraEmployee lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getAttStartTime1() {
        return this.attStartTime1;
    }

    public FloraEmployee attStartTime1(Instant attStartTime1) {
        this.setAttStartTime1(attStartTime1);
        return this;
    }

    public void setAttStartTime1(Instant attStartTime1) {
        this.attStartTime1 = attStartTime1;
    }

    public Instant getAttEndTime1() {
        return this.attEndTime1;
    }

    public FloraEmployee attEndTime1(Instant attEndTime1) {
        this.setAttEndTime1(attEndTime1);
        return this;
    }

    public void setAttEndTime1(Instant attEndTime1) {
        this.attEndTime1 = attEndTime1;
    }

    public Instant getAttStartTime2() {
        return this.attStartTime2;
    }

    public FloraEmployee attStartTime2(Instant attStartTime2) {
        this.setAttStartTime2(attStartTime2);
        return this;
    }

    public void setAttStartTime2(Instant attStartTime2) {
        this.attStartTime2 = attStartTime2;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public FloraEmployee internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloraEmployee)) {
            return false;
        }
        return id != null && id.equals(((FloraEmployee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloraEmployee{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", attStartTime1='" + getAttStartTime1() + "'" +
            ", attEndTime1='" + getAttEndTime1() + "'" +
            ", attStartTime2='" + getAttStartTime2() + "'" +
            "}";
    }
}
