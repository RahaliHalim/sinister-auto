package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Tiers.
 */
@Entity
@Table(name = "tiers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tiers")
public class Tiers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Size(max = 100)
    @Column(name = "prenom", length = 100)
    private String prenom;

    
    @Size(max = 100)
    @Column(name = "nom", length = 100)
    private String nom;

    
    @Column(name = "debut_validite")
    private LocalDate debutValidite;

    
    @Column(name = "fin_validite")
    private LocalDate finValidite;
    
   
    @ManyToOne
    private Partner compagnie;
    
    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agency agence;

    @ManyToOne
    @JoinColumn(name = "sinister_pec_id")
    @JsonBackReference
    private SinisterPec sinisterPec;

  
    //@Pattern(regexp = "([0-9]{4})TU([0-9]{3})")
    @Column(name = "immatriculation")
    private String immatriculation;
    
    @Column(name = "numero_contrat")
    private String numeroContrat;
    @Column(name = "agence_tier")
    private String agenceTier;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "responsible")
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

    public Tiers prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public SinisterPec getSinisterPec() {
		return sinisterPec;
	}

	public void setSinisterPec(SinisterPec sinisterPec) {
		this.sinisterPec = sinisterPec;
	}

	public Partner getCompagnie() {
		return compagnie;
	}

	public String getNom() {
        return nom;
    }

    public Tiers nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDebutValidite() {
        return debutValidite;
    }

    public Tiers debutValidite(LocalDate debutValidite) {
        this.debutValidite = debutValidite;
        return this;
    }

    public void setDebutValidite(LocalDate debutValidite) {
        this.debutValidite = debutValidite;
    }

    public LocalDate getFinValidite() {
        return finValidite;
    }

    public Tiers finValidite(LocalDate finValidite) {
        this.finValidite = finValidite;
        return this;
    }

    public void setFinValidite(LocalDate finValidite) {
        this.finValidite = finValidite;
    }

    public Partner getRefCompagnie() {
        return compagnie;
    }

    public Tiers refCompagnie(Partner compagnie) {
        this.compagnie = compagnie;
        return this;
    }

    public void setCompagnie(Partner compagnie) {
        this.compagnie = compagnie;
    }
    
    public Agency getAgence() {
        return agence;
    }

    public Tiers agence(Agency refAgence) {
        this.agence = refAgence;
        return this;
    }

    public void setAgence(Agency refAgence) {
        this.agence = refAgence;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public Tiers immatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
        return this;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getNumeroContrat() {
		return numeroContrat;
	}

	public void setNumeroContrat(String numeroContrat) {
		this.numeroContrat = numeroContrat;
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
        Tiers tiers = (Tiers) o;
        if (tiers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tiers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tiers{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", debutValidite='" + getDebutValidite() + "'" +
            ", finValidite='" + getFinValidite() + "'" +
            ", immatriculation='" + getImmatriculation() + "'" +
            "}";
    }
}
