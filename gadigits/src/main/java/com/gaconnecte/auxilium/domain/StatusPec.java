package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StatusPec.
 */
@Entity
@Table(name = "ref_status_pec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refstatuspec")
public class StatusPec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    @Column(name = "has_reason")
    private Boolean hasReason;

    @Column(name = "active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public StatusPec code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public StatusPec label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isHasReason() {
        return hasReason;
    }

    public StatusPec hasReason(Boolean hasReason) {
        this.hasReason = hasReason;
        return this;
    }

    public void setHasReason(Boolean hasReason) {
        this.hasReason = hasReason;
    }

    public Boolean isActive() {
        return active;
    }

    public StatusPec active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatusPec statusPec = (StatusPec) o;
        if (statusPec.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statusPec.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatusPec{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", hasReason='" + isHasReason() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
