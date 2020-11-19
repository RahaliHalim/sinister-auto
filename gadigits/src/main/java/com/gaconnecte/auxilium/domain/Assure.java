package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.enumeration.Civilite;


/**
 * A Assure.
 */
@Entity
@Table(name = "assure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assure")
public class Assure implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_company")
    private Boolean company;

    @Size(max = 100)
    @Column(name = "prenom", length = 100)
    private String prenom;

    @Size(max = 100)
    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "raison_sociale", length = 256)
    private String raisonSociale;

    @Column(name = "subscriber")
    private String subscriber;

    @Size(max = 256)
    @Column(name = "full_name", length = 256)
    private String fullName;

    @Column(name = "prem_telephone")
    private String premTelephone;

    @Column(name = "deux_telephone")
    private String deuxTelephone;

    @Pattern(regexp = "([0-9]{8})")
    @Column(name = "fax")
    private String fax;

    @Column(name = "prem_mail")
    private String premMail;

    @Column(name = "deux_mail")
    private String deuxMail;

    @Size(max = 256)
    @Column(name = "adresse", length = 256)
    private String adresse;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "registre_commerce")
    private String registreCommerce;

    @Column(name = "is_utilisateur")
    private Boolean isUtilisateur;

    @Column(name = "cin")
    private String cin;

    @Column(name = "is_assujettie_tva")
    private Boolean isAssujettieTva;

    @Column(name = "type")
    private String Type;
    
    @Column(name = "nombre_conventionne")
    private Integer nombreConventionne;
    
    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "civilite")
    private Civilite civilite;

    @ManyToOne
    @JoinColumn(name = "ville_id")
    private Delegation delegation;
    
    @OneToOne
    @JoinColumn(unique = true)
    private User user;
    
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }


	public Integer getNombreConventionne() {
		return nombreConventionne;
	}

	public void setNombreConventionne(Integer nombreConventionne) {
		this.nombreConventionne = nombreConventionne;
	}
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the company
     */
    public Boolean getCompany() {
        return company;
    }
    /**
     * @param company the company to set
     */
    public void setCompany(Boolean company) {
        this.company = company;
    }
    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }
    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the raisonSociale
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * @param raisonSociale the raisonSociale to set
     */
    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the premTelephone
     */
    public String getPremTelephone() {
        return premTelephone;
    }

    /**
     * @param premTelephone the premTelephone to set
     */
    public void setPremTelephone(String premTelephone) {
        this.premTelephone = premTelephone;
    }

    /**
     * @return the deuxTelephone
     */
    public String getDeuxTelephone() {
        return deuxTelephone;
    }

    /**
     * @param deuxTelephone the deuxTelephone to set
     */
    public void setDeuxTelephone(String deuxTelephone) {
        this.deuxTelephone = deuxTelephone;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the premMail
     */
    public String getPremMail() {
        return premMail;
    }

    /**
     * @param premMail the premMail to set
     */
    public void setPremMail(String premMail) {
        this.premMail = premMail;
    }

    /**
     * @return the deuxMail
     */
    public String getDeuxMail() {
        return deuxMail;
    }

    /**
     * @param deuxMail the deuxMail to set
     */
    public void setDeuxMail(String deuxMail) {
        this.deuxMail = deuxMail;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return the number of R.C
     */
    public String getRegistreCommerce() {
        return registreCommerce;
    }

    /**
     *
     * @param registreCommerce to set
     */
    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    /**
     * @return the isUtilisateur
     */
    public Boolean getIsUtilisateur() {
        return isUtilisateur;
    }

    /**
     * @param isUtilisateur the isUtilisateur to set
     */
    public void setIsUtilisateur(Boolean isUtilisateur) {
        this.isUtilisateur = isUtilisateur;
    }

    /**
     * @return the cin
     */
    public String getCin() {
        return cin;
    }

    /**
     * @param cin the cin to set
     */
    public void setCin(String cin) {
        this.cin = cin;
    }

    /**
     * @return the isAssujettieTva
     */
    public Boolean getIsAssujettieTva() {
        return isAssujettieTva;
    }

    /**
     * @param isAssujettieTva the isAssujettieTva to set
     */
    public void setIsAssujettieTva(Boolean isAssujettieTva) {
        this.isAssujettieTva = isAssujettieTva;
    }

    /**
     * @return the civilite
     */
    public Civilite getCivilite() {
        return civilite;
    }

    /**
     * @param civilite the civilite to set
     */
    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the dateCreation
     */
    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assure assure = (Assure) o;
        if (assure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assure{"
                + "id=" + getId()
                + "}";
    }
}