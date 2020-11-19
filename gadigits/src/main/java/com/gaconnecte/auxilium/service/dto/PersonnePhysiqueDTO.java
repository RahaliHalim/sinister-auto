package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.Civilite;

/**
 * A DTO for the PersonnePhysique entity.
 */
public class PersonnePhysiqueDTO implements Serializable {

    private Long id;

   
    @Size(max = 100)
    private String prenom;

  
    @Size(max = 100)
    private String nom;

   
    private String premTelephone;

 
    private String deuxTelephone;

  
    private String fax;

    @Pattern(regexp = "^(.+)@(.+)$")
    private String premMail;

    @Pattern(regexp = "^(.+)@(.+)$")
    private String deuxMail;

    @Size(max = 256)
    private String adresse;

    private Double latitude;

    private Double longitude;

    private Boolean isUtilisateur;

    @Max(value = 99999999)
    private Integer cin;

    private Civilite civilite;

    private Long villeId;

    private String villeLibelle;

    private Long userId;
    
    private String userLogin;

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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Long getVilleId() {
        return villeId;
    }

    public void setVilleId(Long sysVilleId) {
        this.villeId = sysVilleId;
    }

    public String getVilleLibelle() {
        return villeLibelle;
    }

    public void setVilleLibelle(String sysVilleLibelle) {
        this.villeLibelle = sysVilleLibelle;
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

    public Boolean isIsUtilisateur() {
        return isUtilisateur;
    }

    public void setIsUtilisateur(Boolean isUtilisateur) {
        this.isUtilisateur = isUtilisateur;
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

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonnePhysiqueDTO personnePhysiqueDTO = (PersonnePhysiqueDTO) o;
        if(personnePhysiqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personnePhysiqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonnePhysiqueDTO{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", premTelephone='" + getPremTelephone() + "'" +
            ", deuxTelephone='" + getDeuxTelephone() + "'" +
            ", fax='" + getFax() + "'" +
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

