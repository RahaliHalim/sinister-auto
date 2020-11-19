package com.gaconnecte.auxilium.service.dto;


import javax.persistence.Column;
import javax.validation.constraints.*;

import org.javers.core.metamodel.annotation.DiffIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Contact entity.
 */
public class VisAVisDTO implements Serializable {
	@DiffIgnore
    private Long id;
	@DiffIgnore
    private String entityName;
    @DiffIgnore
    private Long reparateurId;
    @DiffIgnore
    private Long PartnerId;
    @DiffIgnore
    private Long remorqueurId;
    @DiffIgnore
    private Long loueurId;
    @DiffIgnore
    private Boolean isGerant;
    private String nom;
    private String prenom;
    private String premTelephone;
    private String deuxTelephone;
    private String fax;
    private String premMail;
    private String deuxMail;
  
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    

	
	public Long getRemorqueurId() {
		return remorqueurId;
	}
	public void setRemorqueurId(Long remorqueurId) {
		this.remorqueurId = remorqueurId;
	}
	public Boolean getIsGerant() {
		return isGerant;
	}
	public void setIsGerant(Boolean isGerant) {
		this.isGerant = isGerant;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Long getReparateurId() {
		return reparateurId;
	}
	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}
	
	public Long getPartnerId() {
		return PartnerId;
	}
	public void setPartnerId(Long partnerId) {
		PartnerId = partnerId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPremTelephone() {
		return premTelephone;
	}
	public void setPremTelephone(String premTelephone) {
		this.premTelephone = premTelephone;
	}
	public String getDeuxTelephone() {
		return deuxTelephone;
	}
	public void setDeuxTelephone(String deuxTelephone) {
		this.deuxTelephone = deuxTelephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPremMail() {
		return premMail;
	}
	public void setPremMail(String premMail) {
		this.premMail = premMail;
	}
	public String getDeuxMail() {
		return deuxMail;
	}
	public void setDeuxMail(String deuxMail) {
		this.deuxMail = deuxMail;
	}
	
    public Long getLoueurId() {
		return loueurId;
	}
	public void setLoueurId(Long loueurId) {
		this.loueurId = loueurId;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VisAVisDTO contactDTO = (VisAVisDTO) o;
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
		return "VisAVisDTO [entityName=" + entityName + ", gerant=" + isGerant + ", nom=" + nom
				+ ", prenom=" + prenom + ", premTelephone=" + premTelephone + ", deuxTelephone=" + deuxTelephone
				+ ", fax=" + fax + ", premMail=" + premMail + ", deuxMail=" + deuxMail + "]";
	}

}
