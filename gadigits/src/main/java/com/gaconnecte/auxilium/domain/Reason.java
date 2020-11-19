package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;

/**
 * A Reason.
 */
@Entity
@Table(name = "ref_reason")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reason")
public class Reason implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsible")
    private ResponsibleEnum responsible;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    private Step step;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Reason code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public Reason label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public Reason description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponsibleEnum getResponsible() {
        return responsible;
    }

    public Reason responsible(ResponsibleEnum responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(ResponsibleEnum responsible) {
        this.responsible = responsible;
    }

    public Boolean isActive() {
        return active;
    }

    public Reason active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Step getStep() {
        return step;
    }

    public Reason step(Step step) {
        this.step = step;
        return this;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reason reason = (Reason) o;
        if (reason.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reason.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reason{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
