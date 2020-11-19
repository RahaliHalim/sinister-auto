package com.gaconnecte.auxilium.domain;

import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A RaisonPec.
 */
@Entity
@Table(name = "ref_raison_pec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "raisonpec")
public class RaisonPec implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "responsible")
    private ResponsibleEnum responsible;

    @ManyToOne
    private Operation operation;

    @ManyToOne
    private PecStatusChangeMatrix pecStatusChangeMatrix;
    
    @ManyToOne
    private StatusPec statusPec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public RaisonPec code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public RaisonPec label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isActive() {
        return active;
    }

    public RaisonPec active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public RaisonPec creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreationUserId() {
        return creationUserId;
    }

    public RaisonPec creationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
        return this;
    }

    public void setCreationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
    }

    public ResponsibleEnum getResponsible() {
        return responsible;
    }

    public RaisonPec responsible(ResponsibleEnum responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(ResponsibleEnum responsible) {
        this.responsible = responsible;
    }

    public Operation getOperation() {
        return operation;
    }

    public RaisonPec operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public PecStatusChangeMatrix getPecStatusChangeMatrix() {
        return pecStatusChangeMatrix;
    }

    public RaisonPec pecStatusChangeMatrix(PecStatusChangeMatrix pecStatusChangeMatrix) {
        this.pecStatusChangeMatrix = pecStatusChangeMatrix;
        return this;
    }

    public void setPecStatusChangeMatrix(PecStatusChangeMatrix pecStatusChangeMatrix) {
        this.pecStatusChangeMatrix = pecStatusChangeMatrix;
    }

    public RaisonPec statusPec(StatusPec statusPec) {
        this.statusPec = statusPec;
        return this;
    }

    public StatusPec getStatusPec() {
        return statusPec;
    }



    public void setStatusPec(StatusPec statusPec) {
        this.statusPec = statusPec;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RaisonPec raisonPec = (RaisonPec) o;
        if (raisonPec.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), raisonPec.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RaisonPec{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", creationUserId='" + getCreationUserId() + "'" +
            ", responsible='" + getResponsible() + "'" +
            "}";
    }
}
