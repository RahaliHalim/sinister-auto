package com.gaconnecte.auxilium.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

import java.util.Objects;

/**
 * A RefModeGestion.
 */
@Entity
@Table(name = "ref_exclusion")

public class RefExclusion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "libelle", length = 500, nullable = false)
    private String libelle;

    @NotNull
    @Size(max = 20)
    @Column(name = "state", length = 20, nullable = false)
    private String state;
  

  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public RefExclusion libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

   

    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefExclusion refModeGestion = (RefExclusion) o;
        if (refModeGestion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refModeGestion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefModeGestion{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
        
            "}";
    }
}
