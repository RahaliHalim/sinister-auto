package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.enumeration.Civilite;

/**
 * A PersonnePhysique.
 */
@Entity
@Table(name = "personne_physique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "personnephysique")
public class PersonnePhysique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "prenom", length = 100, nullable = false)
    private String prenom;

    @NotNull
    @Size(max = 100)
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;


   
    @Column(name = "prem_telephone")
    private String premTelephone;

 
    @Column(name = "deux_telephone")
    private String deuxTelephone;

   
    @Column(name = "fax")
    private String fax;

    @Pattern(regexp = "^(.+)@(.+)$")
    @Column(name = "prem_mail")
    private String premMail;

    @Pattern(regexp = "^(.+)@(.+)$")
    @Column(name = "deux_mail")
    private String deuxMail;

    @Size(max = 256)
    @Column(name = "adresse", length = 256)
    private String adresse;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    private Delegation ville;

    @Column(name = "is_utilisateur")
    private Boolean isUtilisateur;

    @Max(value = 99999999)
    @Column(name = "cin")
    private Integer cin;

    @Enumerated(EnumType.STRING)
    @Column(name = "civilite")
    private Civilite civilite;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public PersonnePhysique prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public PersonnePhysique nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPremTelephone() {
        return premTelephone;
    }

    public PersonnePhysique premTelephone(String premTelephone) {
        this.premTelephone = premTelephone;
        return this;
    }

    public void setPremTelephone(String premTelephone) {
        this.premTelephone = premTelephone;
    }

    public String getDeuxTelephone() {
        return deuxTelephone;
    }

    public PersonnePhysique deuxTelephone(String deuxTelephone) {
        this.deuxTelephone = deuxTelephone;
        return this;
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

    public PersonnePhysique premMail(String premMail) {
        this.premMail = premMail;
        return this;
    }

    public void setPremMail(String premMail) {
        this.premMail = premMail;
    }

    public String getDeuxMail() {
        return deuxMail;
    }

    public PersonnePhysique deuxMail(String deuxMail) {
        this.deuxMail = deuxMail;
        return this;
    }

    public void setDeuxMail(String deuxMail) {
        this.deuxMail = deuxMail;
    }

    public String getAdresse() {
        return adresse;
    }

    public PersonnePhysique adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getLatitude() {
        return latitude;
    }

    public PersonnePhysique latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public PersonnePhysique longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isIsUtilisateur() {
        return isUtilisateur;
    }

    public PersonnePhysique isUtilisateur(Boolean isUtilisateur) {
        this.isUtilisateur = isUtilisateur;
        return this;
    }

    public void setIsUtilisateur(Boolean isUtilisateur) {
        this.isUtilisateur = isUtilisateur;
    }
	public PersonnePhysique cin(Integer cin) {
        this.cin = cin;
        return this;
    }
	public Integer getCin() {
        return cin;
    }
  	public void setCin(Integer cin) {
        this.cin = cin;
    }
 public Civilite getCivilite() {
        return civilite;
    }

    public PersonnePhysique civilite(Civilite civilite) {
        this.civilite = civilite;
        return this;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }


    public User getUser() {
        return user;
    }

    public PersonnePhysique user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Delegation getVille() {
        return ville;
    }

    public PersonnePhysique ville(Delegation sysVille) {
        this.ville = sysVille;
        return this;
    }

    public void setVille(Delegation sysVille) {
        this.ville = sysVille;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonnePhysique personnePhysique = (PersonnePhysique) o;
        if (personnePhysique.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personnePhysique.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonnePhysique{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", premTelephone='" + getPremTelephone() + "'" +
            ", deuxTelephone='" + getDeuxTelephone() + "'" +
            ", premMail='" + getPremMail() + "'" +
            ", deuxMail='" + getDeuxMail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", isUtilisateur='" + isIsUtilisateur() + "'" +
            ", cin='" + getCin() + "'" +
            ", civilite='" + getCivilite() + "'" +
            "}";
    }
}

