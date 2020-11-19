package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BusinessEntity.
 */
@Entity
@Table(name = "auth_business_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "businessentity")
public class BusinessEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "code")
    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_business_entity_functionality", joinColumns = {
        @JoinColumn(name = "business_entity_id")}, inverseJoinColumns = {
        @JoinColumn(name = "functionality_id")})
    private Set<Functionality> functionalities = new HashSet<>();

    public BusinessEntity() {
    }

    public BusinessEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public BusinessEntity label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public BusinessEntity code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Functionality> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(Set<Functionality> functionalities) {
        this.functionalities = functionalities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessEntity businessEntity = (BusinessEntity) o;
        if (businessEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessEntity{"
                + "id=" + getId()
                + ", label='" + getLabel() + "'"
                + ", code='" + getCode() + "'"
                + "}";
    }
}
