package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cellule.
 */
@Entity
@Table(name = "cellule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cellule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "cellules")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lien> liens = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Cellule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Lien> getLiens() {
        return liens;
    }

    public Cellule liens(Set<Lien> liens) {
        this.liens = liens;
        return this;
    }

    public Cellule addLiens(Lien lien) {
        this.liens.add(lien);
        lien.getCellules().add(this);
        return this;
    }

    public Cellule removeLiens(Lien lien) {
        this.liens.remove(lien);
        lien.getCellules().remove(this);
        return this;
    }

    public void setLiens(Set<Lien> liens) {
        this.liens = liens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cellule cellule = (Cellule) o;
        if (cellule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cellule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cellule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}