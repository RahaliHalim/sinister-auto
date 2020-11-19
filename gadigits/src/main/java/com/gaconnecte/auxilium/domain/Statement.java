package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Statement.
 */
@Entity
@Table(name = "app_statement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appstatement")
public class Statement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "tug_id")
    private Long tugId;
    
    @Column(name = "tug_label")
    private String tugLabel;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "step")
    private Integer step;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Statement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public Statement path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTugId() {
        return tugId;
    }

    public Statement tugId(Long tugId) {
        this.tugId = tugId;
        return this;
    }

    public void setTugId(Long tugId) {
        this.tugId = tugId;
    }

    public String getTugLabel() {
        return tugLabel;
    }

    public void setTugLabel(String tugLabel) {
        this.tugLabel = tugLabel;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Statement creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getNotes() {
        return notes;
    }

    public Statement notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStep() {
        return step;
    }

    public Statement step(Integer step) {
        this.step = step;
        return this;
    }

    public void setStep(Integer step) {
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
        Statement statement = (Statement) o;
        if (statement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Statement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            ", tugId='" + getTugId() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
