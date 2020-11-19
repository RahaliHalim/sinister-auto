package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RefModeGestion entity.
 */
public class RefExclusionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String libelle;
    @NotNull
    @Size(max = 20)
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

        RefExclusionDTO refExclusionDTO = (RefExclusionDTO) o;
        if(refExclusionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refExclusionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "RefExclusionDTO [id=" + id + ", libelle=" + libelle + "]";
	}

  
}
