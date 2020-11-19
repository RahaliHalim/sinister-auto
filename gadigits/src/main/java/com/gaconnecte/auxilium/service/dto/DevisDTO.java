package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.domain.enumeration.EtatDevis;
import java.time.ZonedDateTime;

/**
 * A DTO for the Devis entity.
 */
public class DevisDTO implements Serializable {

    private Long id;

    @DecimalMax(value = "100000000000000000")
    private Double totalTtc;

    @DecimalMax(value = "100000000000000000")
    private Double totHt;

    private Boolean isComplementaire;

    private Boolean isSupprime;

    private ZonedDateTime dateGeneration;

    private EtatDevis etatDevis;

    @Max(value = 99999999)
    private Integer version;

    @DecimalMax(value = "99")
    private Float timbre;

    @Size(max = 2000)
    private String commentaire;

    @DecimalMax(value = "99999999")
    private Float droitTimbre;

    private Long prestationId;

    private Long reparateurId;

    private Long expertId;

    private Integer parentId;
    
    private Expert expert;

    private LocalDate dateExpertise;

    @DecimalMax(value = "100000000000000000")
    private Double valeurNeuf;

    @DecimalMax(value = "100000000000000000")
    private Double valeurVenale;

    @DecimalMax(value = "100000000000000000")
    private Double klm;

    @Size(max = 2000)
    private String etatVehicule;

    private Boolean isVehiculeReparable;

    private Boolean isCia;

    private Boolean isConstatPre;

    private Long quotationStatusId;

    public Double getValeurNeuf() {
        return valeurNeuf;
    }

    public void setValeurNeuf(Double valeurNeuf) {
        this.valeurNeuf = valeurNeuf;
    }

    public Double getValeurVenale() {
        return valeurVenale;
    }

    public void setValeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
    }

    public Double getKlm() {
        return klm;
    }

    public void setKlm(Double klm) {
        this.klm = klm;
    }

    public String getEtatVehicule() {
        return etatVehicule;
    }

    public void setEtatVehicule(String etatVehicule) {
        this.etatVehicule = etatVehicule;
    }

    public Boolean isIsConstatPre() {
        return isConstatPre;
    }

    public void setIsConstatPre(Boolean isConstatPre) {
        this.isConstatPre = isConstatPre;
    }

    public Boolean isIsCia() {
        return isCia;
    }

    public void setIsCia(Boolean isCia) {
        this.isCia = isCia;
    }

    public Boolean isIsVehiculeReparable() {
        return isVehiculeReparable;
    }

    public void setIsVehiculeReparable(Boolean isVehiculeReparable) {
        this.isVehiculeReparable = isVehiculeReparable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Double getTotalTtc() {
        return totalTtc;
    }

    public void setTotalTtc(Double totalTtc) {
        this.totalTtc = totalTtc;
    }

    public Double getTotHt() {
        return totHt;
    }

    public void setTotHt(Double totHt) {
        this.totHt = totHt;
    }

    public Boolean isIsComplementaire() {
        return isComplementaire;
    }

    public void setIsComplementaire(Boolean isComplementaire) {
        this.isComplementaire = isComplementaire;
    }

    public Boolean isIsSupprime() {
        return isSupprime;
    }

    public void setIsSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
    }

    public LocalDate getDateExpertise() {
        return dateExpertise;
    }

    public void setDateExpertise(LocalDate dateExpertise) {
        this.dateExpertise = dateExpertise;
    }

    public EtatDevis getEtatDevis() {
        return etatDevis;
    }

    public void setEtatDevis(EtatDevis etatDevis) {
        this.etatDevis = etatDevis;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Float getTimbre() {
        return timbre;
    }

    public void setTimbre(Float timbre) {
        this.timbre = timbre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Float getDroitTimbre() {
        return droitTimbre;
    }

    public void setDroitTimbre(Float droitTimbre) {
        this.droitTimbre = droitTimbre;
    }

    public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationPECId) {
        this.prestationId = prestationPECId;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }
    
    public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
    }
    
    public ZonedDateTime getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(ZonedDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
    }
    
	/**
	 * @return the isComplementaire
	 */
	public Boolean getIsComplementaire() {
		return isComplementaire;
	}

	/**
	 * @return the isSupprime
	 */
	public Boolean getIsSupprime() {
		return isSupprime;
	}

	/**
	 * @return the isVehiculeReparable
	 */
	public Boolean getIsVehiculeReparable() {
		return isVehiculeReparable;
	}

	/**
	 * @return the isCia
	 */
	public Boolean getIsCia() {
		return isCia;
	}

	/**
	 * @return the isConstatPre
	 */
	public Boolean getIsConstatPre() {
		return isConstatPre;
	}

	public Long getQuotationStatusId() {
		return quotationStatusId;
	}

	public void setQuotationStatusId(Long quotationStatusId) {
		this.quotationStatusId = quotationStatusId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DevisDTO devisDTO = (DevisDTO) o;
        if(devisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DevisDTO{" +
            "id=" + getId() +
            ", totalTtc='" + getTotalTtc() + "'" +
            ", totHt='" + getTotHt() + "'" +
            ", isComplementaire='" + isIsComplementaire() + "'" +
            ", isSupprime='" + isIsSupprime() + "'" +
            ", dateGeneration='" + getDateGeneration() + "'" +
            ", timbre='" + getTimbre() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", droitTimbre='" + getDroitTimbre() + "'" +
            "}";
    }
}
