package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gaconnecte.auxilium.Utils.CustomJsonDateDeserializer;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaconnecte.auxilium.Utils.CustomJsonDateSerializer;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A DTO for the PrestationAvt entity.
 */
public class PrestationAvtDTO implements Serializable {

    private Long id;

    private Long dossierId;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateDerniereMaj;

    private Boolean isDelete;

    private ZonedDateTime dateCloture;

    private Boolean isFerier;

    private Boolean isNuit;

    private Boolean isPoidsLourd;

    private Boolean isDemiMajore;

    @Size(max = 2000)
    private String commentaire;

    private String dossierReference;

    private Long typeServiceId;

    private String typeServiceNom;

    private Long userId;

    private EtatDossierRmq etat;
    
    private ZonedDateTime tugArrivalDate;

    private ZonedDateTime insuredArrivalDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public ZonedDateTime getDateCloture() {
        return dateCloture;
    }
    
    public void setDateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public Boolean isIsFerier() {
        return isFerier;
    }

    public void setIsFerier(Boolean isFerier) {
        this.isFerier = isFerier;
    }

    public Boolean isIsNuit() {
        return isNuit;
    }

    public void setIsNuit(Boolean isNuit) {
        this.isNuit = isNuit;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public Boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public void setDateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public Long getTypeServiceId() {
        return typeServiceId;
    }

    public void setTypeServiceId(Long refTypeServiceId) {
        this.typeServiceId = refTypeServiceId;
    }

    public String getTypeServiceNom() {
        return typeServiceNom;
    }

    public void setTypeServiceNom(String refTypeServiceNom) {
        this.typeServiceNom = refTypeServiceNom;
    }

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }

    public String getDossierReference() {
        return dossierReference;
    }

    public void setDossierReference(String dossierReference) {
        this.dossierReference = dossierReference;
    }
        
    public Boolean getIsPoidsLourd() {
		return isPoidsLourd;
	}

	public void setIsPoidsLourd(Boolean isPoidsLourd) {
		this.isPoidsLourd = isPoidsLourd;
	}
	
	public Boolean getIsDemiMajore() {
		return isDemiMajore;
	}

	public void setIsDemiMajore(Boolean isDemiMajore) {
		this.isDemiMajore = isDemiMajore;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public Boolean getIsFerier() {
		return isFerier;
	}

	public Boolean getIsNuit() {
		return isNuit;
	}
    	
	public EtatDossierRmq getEtat() {
		return etat;
	}

	public void setEtat(EtatDossierRmq etat) {
		this.etat = etat;
	}

	public ZonedDateTime getTugArrivalDate() {
		return tugArrivalDate;
	}
	
	public void setTugArrivalDate(ZonedDateTime tugArrivalDate) {
		this.tugArrivalDate = tugArrivalDate;
	}

	public ZonedDateTime getInsuredArrivalDate() {
		return insuredArrivalDate;
	}
	
	public void setInsuredArrivalDate(ZonedDateTime insuredArrivalDate) {
		this.insuredArrivalDate = insuredArrivalDate;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrestationAvtDTO prestationAvtDTO = (PrestationAvtDTO) o;
        if(prestationAvtDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestationAvtDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestationAvtDTO{" +
            "id=" + getId() +
            "}";
    }
}
