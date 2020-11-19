package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lien.
 */
@Entity
@Table(name = "lien")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lien_cellules",
               joinColumns = @JoinColumn(name="liens_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="cellules_id", referencedColumnName="id"))
    private Set<Cellule> cellules = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lien_authorities",
               joinColumns = @JoinColumn(name="liens_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="authorities_name", referencedColumnName="name"))
    private Set<Authority> authorities = new HashSet<>();

    @ManyToOne(optional = false)
    private Lien parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Lien name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public Lien url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Cellule> getCellules() {
        return cellules;
    }

    public Lien cellules(Set<Cellule> cellules) {
        this.cellules = cellules;
        return this;
    }

    public Lien addCellules(Cellule cellule) {
        this.cellules.add(cellule);
        cellule.getLiens().add(this);
        return this;
    }

    public Lien removeCellules(Cellule cellule) {
        this.cellules.remove(cellule);
        cellule.getLiens().remove(this);
        return this;
    }

    public void setCellules(Set<Cellule> cellules) {
        this.cellules = cellules;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public Lien authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public Lien addAuthorities(Authority authority) {
        this.authorities.add(authority);
        authority.getLiens().add(this);
        return this;
    }

    public Lien removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
        authority.getLiens().remove(this);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Lien getParent() {
        return parent;
    }

    public Lien parent(Lien parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(Lien parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lien lien = (Lien) o;
        if (lien.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lien.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lien{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}