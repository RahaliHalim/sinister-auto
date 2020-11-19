package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PersonneMorale entity.
 */
public class PersonneMoraleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String raisonSociale;

    @NotNull
    @Size(max = 100)
    private String registreCommerce;

    @Size(min = 3, max = 3)
    private String numEtablissement;


    private  Integer codeCategorie;


    @Size(max = 100)
    private String matriculeFiscale;

    @Size(max = 256)
    private String adresse;

    private Long villeId;
    
    private Long reglementId;
    private Long tvaId;
    private Double latitude;
    
	private Double longitude;

    @Size(max = 100)
    private String banque;

    @Size(max = 100)
    private String agence;

   
    private String rib;

    private Boolean isAssujettieTva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTvaId() {
		return tvaId;
	}

	public void setTvaId(Long tvaId) {
		this.tvaId = tvaId;
	}

	public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getRegistreCommerce() {
        return registreCommerce;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public String getNumEtablissement() {
        return numEtablissement;
    }

    public void setNumEtablissement(String numEtablissement) {
        this.numEtablissement = numEtablissement;
    }

    public Integer getCodeCategorie() {
        return codeCategorie;
    }

    public void setCodeCategorie(Integer codeCategorie) {
        this.codeCategorie = codeCategorie;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Long getReglementId() {
		return reglementId;
	}

	public void setReglementId(Long reglementId) {
		this.reglementId = reglementId;
	}

	public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }
    public Long getVilleId() {
        return villeId;
    }

    public void setVilleId(Long sysVilleId) {
        this.villeId = sysVilleId;
    }

    public Boolean isIsAssujettieTva() {
        return isAssujettieTva;
    }

    public void setIsAssujettieTva(Boolean isAssujettieTva) {
        this.isAssujettieTva = isAssujettieTva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonneMoraleDTO personneMoraleDTO = (PersonneMoraleDTO) o;
        if(personneMoraleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personneMoraleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonneMoraleDTO{" +
            "id=" + getId() +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", registreCommerce='" + getRegistreCommerce() + "'" +
            ", numEtablissement='" + getNumEtablissement() + "'" +
            ", codeCategorie='" + getCodeCategorie() + "'" +
            ", matriculeFiscale='" + getMatriculeFiscale() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", banque='" + getBanque() + "'" +
            ", agence='" + getAgence() + "'" +
            ", rib='" + getRib() + "'" +
            ", isAssujettieTva='" + isIsAssujettieTva() + "'" +
            "}";
    }
}

