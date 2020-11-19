package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.*;

import java.util.Set;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String name;

     @Column(name = "is_interne")
    private Boolean isInterne;

     @Column(name = "is_active")
    private Boolean isActive;

     @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lien> liens = new HashSet<>();

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserCellule> usersCellules = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsInterne() {
        return isInterne;
    }

    public Authority isInterne(Boolean isInterne) {
        this.isInterne = isInterne;
        return this;
    }

    public void setIsInterne(Boolean isInterne) {
        this.isInterne = isInterne;
    }
    
    
    public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsInterne() {
		return isInterne;
	}

	public Set<Lien> getLiens() {
        return liens;
    }


    public Authority(String name) {
        this.name = name;
    }

    public Authority() {
    }

    public Authority liens(Set<Lien> liens) {
        this.liens = liens;
        return this;
    }

    public Authority addLiens(Lien lien) {
        this.liens.add(lien);
        lien.getAuthorities().add(this);
        return this;
    }

    public Authority removeLiens(Lien lien) {
        this.liens.remove(lien);
        lien.getAuthorities().remove(this);
        return this;
    }

    public void setLiens(Set<Lien> liens) {
        this.liens = liens;
    }


    public Set<UserCellule> getUsersCellules() {
        return usersCellules;
    }

    public Authority usersCellules(Set<UserCellule> usersCellules) {
        this.usersCellules = usersCellules;
        return this;
    }

    public Authority addUsersCellules(UserCellule userCellule) {
        this.usersCellules.add(userCellule);
        userCellule.getAuthorities().add(this);
        return this;
    }

    public Authority removeUsersCellules(UserCellule userCellule) {
        this.usersCellules.remove(userCellule);
        userCellule.getAuthorities().remove(this);
        return this;
    }

    public void setUsersCellules(Set<UserCellule> usersCellules) {
        this.usersCellules = usersCellules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Authority authority = (Authority) o;

        return !(name != null ? !name.equals(authority.name) : authority.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Authority{" +
            "name='" + name + '\'' +
            "}";
    }
}
