package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonneMorale.
 */
@Entity
@Table(name = "personne_morale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "personnemorale")
public class PersonneMorale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "raison_sociale", length = 256, nullable = false)
    private String raisonSociale;

    @NotNull
    @Size(max = 100)
    @Column(name = "registre_commerce", length = 100, nullable = false)
    private String registreCommerce;

    @Size(min = 3, max = 3)
    @Column(name = "num_etablissement", length = 3)
    private String numEtablissement;

    
    @Column(name = "code_categorie")
    private Integer codeCategorie;

    @Size(max = 100)
    @Column(name = "matricule_fiscale", length = 100)
    private String matriculeFiscale;

    @Size(max = 256)
    @Column(name = "adresse", length = 256)
    private String adresse;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
    
    @Size(max = 100)
    @Column(name = "banque", length = 100)
    private String banque;

    @Size(max = 100)
    @Column(name = "agence", length = 100)
    private String agence;
  
    @Column(name = "rib")
    private String rib;
    @ManyToOne
    private  RefModeReglement reglement;
    @ManyToOne
    private  VatRate tva;
    @ManyToOne
    private Delegation ville;
    @Column(name = "is_assujettie_tva")
    private Boolean isAssujettieTva;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public PersonneMorale raisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getRegistreCommerce() {
        return registreCommerce;
    }

    public PersonneMorale registreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
        return this;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public String getNumEtablissement() {
        return numEtablissement;
    }

    public PersonneMorale numEtablissement(String numEtablissement) {
        this.numEtablissement = numEtablissement;
        return this;
    }

    public void setNumEtablissement(String numEtablissement) {
        this.numEtablissement = numEtablissement;
    }

    public Integer getCodeCategorie() {
        return codeCategorie;
    }

    public PersonneMorale codeCategorie(Integer codeCategorie) {
        this.codeCategorie = codeCategorie;
        return this;
    }

    public void setCodeCategorie(Integer codeCategorie) {
        this.codeCategorie = codeCategorie;
    }

   

    public VatRate getTva() {
		return tva;
	}
    public PersonneMorale tva(VatRate refTva) {
        this.tva = refTva;
        return this;
    }
	public void setTva(VatRate tva) {
		this.tva = tva;
	}

	public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public PersonneMorale matriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
        return this;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getAdresse() {
        return adresse;
    }

    public PersonneMorale adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
   
    public Double getLatitude() {
        return latitude;
    }

    public PersonneMorale latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public PersonneMorale longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBanque() {
        return banque;
    }

    public PersonneMorale banque(String banque) {
        this.banque = banque;
        return this;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getAgence() {
        return agence;
    }

    public PersonneMorale agence(String agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getRib() {
        return rib;
    }

    public PersonneMorale rib(String rib) {
        this.rib = rib;
        return this;
    }
    public Delegation getVille() {
        return ville;
    }

    public PersonneMorale ville(Delegation sysVille) {
        this.ville = sysVille;
        return this;
    }

    public void setVille(Delegation sysVille) {
        this.ville = sysVille;
    }

    public PersonneMorale reglement(RefModeReglement refModeReglement) {
        this.reglement = refModeReglement;
        return this;
    }
    public RefModeReglement getReglement() {
		return reglement;
	}

	public void setReglement(RefModeReglement reglement) {
		this.reglement = reglement;
	}


    public void setRib(String rib) {
        this.rib = rib;
    }

    public Boolean isIsAssujettieTva() {
        return isAssujettieTva;
    }

    public PersonneMorale isAssujettieTva(Boolean isAssujettieTva) {
        this.isAssujettieTva = isAssujettieTva;
        return this;
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
        PersonneMorale personneMorale = (PersonneMorale) o;
        if (personneMorale.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personneMorale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonneMorale{" +
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

