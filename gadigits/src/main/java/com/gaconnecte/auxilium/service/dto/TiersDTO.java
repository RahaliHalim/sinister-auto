package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tiers entity.
 */
public class TiersDTO implements Serializable {

    private Long id;

    
    @Size(max = 100)
    private String prenom;

   
    @Size(max = 100)
    private String nom;

   
    private LocalDate debutValidite;

    
    private LocalDate finValidite;


    private Long compagnieId;

    private String compagnieNom;
    
    private Long agenceId;

    private String agenceNom;
    
    private String fullName;
    
    private String agenceTier;

    
    //@Pattern(regexp = "([0-9]{4})TU([0-9]{3})")
    private String immatriculation;

    private Long sinisterPecId;
    
    private String numeroContrat;
    
    private Boolean responsible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDebutValidite() {
        return debutValidite;
    }

    public void setDebutValidite(LocalDate debutValidite) {
        this.debutValidite = debutValidite;
    }

    public LocalDate getFinValidite() {
        return finValidite;
    }

    public void setFinValidite(LocalDate finValidite) {
        this.finValidite = finValidite;
    }

    public Long getCompagnieId() {
        return compagnieId;
    }

    public void setCompagnieId(Long compagnieId) {
        this.compagnieId = compagnieId;
    }
    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getCompagnieNom() {
        return compagnieNom;
    }

    public void setCompagnieNom(String compagnieNom) {
        this.compagnieNom = compagnieNom;
    }

   

    public Long getSinisterPecId() {
		return sinisterPecId;
	}

	public void setSinisterPecId(Long sinisterPecId) {
		this.sinisterPecId = sinisterPecId;
	}

	public String getNumeroContrat() {
		return numeroContrat;
	}

	public void setNumeroContrat(String numeroContrat) {
		this.numeroContrat = numeroContrat;
	}

	public String getAgenceNom() {
		return agenceNom;
	}

	public void setAgenceNom(String agenceNom) {
		this.agenceNom = agenceNom;
	}

	public Long getAgenceId() {
		return agenceId;
	}

	public void setAgenceId(Long agenceId) {
		this.agenceId = agenceId;
	}

	public Boolean getResponsible() {
		return responsible;
	}

	public void setResponsible(Boolean responsible) {
		this.responsible = responsible;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAgenceTier() {
		return agenceTier;
	}

	public void setAgenceTier(String agenceTier) {
		this.agenceTier = agenceTier;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TiersDTO tiersDTO = (TiersDTO) o;
        if(tiersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tiersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TiersDTO{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", debutValidite='" + getDebutValidite() + "'" +
            ", finValidite='" + getFinValidite() + "'" +
            ", immatriculation='" + getImmatriculation() + "'" +
            "}";
    }
}
