package com.gaconnecte.auxilium.domain;

import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RaisonAssistance.
 */
@Entity
@Table(name = "ref_raison_assistance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "raisonassistance")
public class RaisonAssistance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_user_id")
    private Long creationUserId;

    @ManyToOne
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RefStatusSinister status;

    public RaisonAssistance() {
    }

    public RaisonAssistance(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public RaisonAssistance code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public RaisonAssistance label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isActive() {
        return active;
    }

    public RaisonAssistance active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public RaisonAssistance creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreationUserId() {
        return creationUserId;
    }

    public RaisonAssistance creationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
        return this;
    }

    public void setCreationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
    }

    public Operation getOperation() {
        return operation;
    }

    public RaisonAssistance operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public RefStatusSinister getStatus() {
        return status;
    }

    public void setStatus(RefStatusSinister status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RaisonAssistance raisonAssistance = (RaisonAssistance) o;
        if (raisonAssistance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), raisonAssistance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RaisonAssistance{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", creationUserId='" + getCreationUserId() + "'" +
            "}";
    }
}
