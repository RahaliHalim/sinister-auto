package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Journal.
 */
@Entity
@Table(name = "history")

@Document(indexName = "history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")

    private Long id;
    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "operation_name")
    private String operationName;

    @Column(name = "type_devis")
    private String typeDevis;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    @Column(name = "type_accord")
    private String typeAccord;

    @Column(name = "quotation_id")
    private Long quotationId;

    @ManyToOne
    private User user;

    @ManyToOne
    private RefAction action;

    @Column(name = "description_of_historization")
    private String descriptionOfHistorization;

    public String getTypeAccord() {
        return typeAccord;
    }

    public void setTypeAccord(String typeAccord) {
        this.typeAccord = typeAccord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public History entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public History entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;

    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;

    }

    public String getOperationName() {
        return operationName;
    }

    public History operationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public History operationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public History user(User user) {
        this.user = user;
        return this;
    }

    public RefAction getAction() {
        return action;
    }

    public void setAction(RefAction action) {
        this.action = action;
    }

    public String getDescriptionOfHistorization() {
        return descriptionOfHistorization;
    }

    public void setDescriptionOfHistorization(String descriptionOfHistorization) {
        this.descriptionOfHistorization = descriptionOfHistorization;
    }

    public String getTypeDevis() {
        return typeDevis;
    }

    public void setTypeDevis(String typeDevis) {
        this.typeDevis = typeDevis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        History journal = (History) o;
        if (journal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "History [id=" + id + ", entityName=" + entityName + ", entityId=" + entityId + ", operationName="
                + operationName + ", typeDevis=" + typeDevis + ", operationDate=" + operationDate + ", typeAccord="
                + typeAccord + ", user=" + user + ", action=" + action + ", descriptionOfHistorization="
                + descriptionOfHistorization + "]";
    }
}
