package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Contact entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    private Boolean isGerant;

    private Long personnePhysiqueId;

    private String personnePhysiqueNom;
    private String personnePhysiquePrenom;
    private String personnePhysiqueTel;
    private String personnePhysiqueFax;
    private String personnePhysiqueEmail;
    

    private Long personneMoraleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsGerant() {
        return isGerant;
    }
    

    public String getPersonnePhysiqueFax() {
		return personnePhysiqueFax;
	}

	public void setPersonnePhysiqueFax(String personnePhysiqueFax) {
		this.personnePhysiqueFax = personnePhysiqueFax;
	}

	public void setIsGerant(Boolean isGerant) {
        this.isGerant = isGerant;
    }

    public Long getPersonnePhysiqueId() {
        return personnePhysiqueId;
    }

    public void setPersonnePhysiqueId(Long personnePhysiqueId) {
        this.personnePhysiqueId = personnePhysiqueId;
    }

    public String getPersonnePhysiqueNom() {
        return personnePhysiqueNom;
    }

    public void setPersonnePhysiqueNom(String personnePhysiqueNom) {
        this.personnePhysiqueNom = personnePhysiqueNom;
    }

     public String getPersonnePhysiquePrenom() {
        return personnePhysiquePrenom;
    }

    public void setPersonnePhysiquePrenom(String personnePhysiquePrenom) {
        this.personnePhysiquePrenom = personnePhysiquePrenom;
    }

     public String getPersonnePhysiqueTel() {
        return personnePhysiqueTel;
    }

    public void setPersonnePhysiqueTel(String personnePhysiqueTel) {
        this.personnePhysiqueTel = personnePhysiqueTel;
    }

     public String getPersonnePhysiqueEmail() {
        return personnePhysiqueEmail;
    }

    public void setPersonnePhysiqueEmail(String personnePhysiqueEmail) {
        this.personnePhysiqueEmail = personnePhysiqueEmail;
    }


    public Long getPersonneMoraleId() {
        return personneMoraleId;
    }

    public void setPersonneMoraleId(Long personneMoraleId) {
        this.personneMoraleId = personneMoraleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactDTO contactDTO = (ContactDTO) o;
        if(contactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + getId() +
            ", isGerant='" + isIsGerant() + "'" +
            "}";
    }
}
