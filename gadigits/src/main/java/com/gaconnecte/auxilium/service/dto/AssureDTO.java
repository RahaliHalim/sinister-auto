package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.enumeration.Civilite;
import java.util.Date;

/**
 * A DTO for the Assure entity.
 */
public class AssureDTO implements Serializable {

    private Long id;

    private Boolean company;

    private String raisonSociale;

    private String subscriber;

    private String prenom;

    private String nom;

    private String fullName;

    private String premTelephone;

    private String deuxTelephone;

    private String fax;

    private String premMail;

    private String deuxMail;

    private String adresse;

    private Double latitude;

    private Double longitude;

    private String registreCommerce;

    private Boolean isUtilisateur;

    private String cin;

    private Civilite civilite;

    private Long delegationId;

    private String delegationLabel;

    private Long userId;

    private String userLogin;

    private Boolean isAssujettieTva;

    private String type;

    private Long contratAssuranceId;

    private String immatriculation;

    private String numContrat;

    private LocalDate DateFinDeContrat;

    private Double nombreConventionne;

    private Integer nombreDePlace;

    private String marqueVehicule;

    private Long vehiculeId;

    private Long agencyId;

    private Long typeService;

    private String mdp;

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    private String usageVehicule;

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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public String getDelegationLabel() {
        return delegationLabel;
    }

    public void setDelegationLabel(String delegationLabel) {
        this.delegationLabel = delegationLabel;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the userLogin
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

        AssureDTO assureDTO = (AssureDTO) o;
        if (assureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assureDTO.getId());
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getContratAssuranceId() {
        return contratAssuranceId;
    }

    public void setContratAssuranceId(Long contratAssuranceId) {
        this.contratAssuranceId = contratAssuranceId;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getNumContrat() {
        return numContrat;
    }

    public void setNumContrat(String numContrat) {
        this.numContrat = numContrat;
    }

    public LocalDate getDateFinDeContrat() {
        return DateFinDeContrat;
    }

    public void setDateFinDeContrat(LocalDate DateFinDeContrat) {
        this.DateFinDeContrat = DateFinDeContrat;
    }

    public Double getNombreConventionne() {
        return nombreConventionne;
    }

    public void setNombreConventionne(Double nombreConventionne) {
        this.nombreConventionne = nombreConventionne;
    }

    public Integer getNombreDePlace() {
        return nombreDePlace;
    }

    public void setNombreDePlace(Integer nombreDePlace) {
        this.nombreDePlace = nombreDePlace;
    }

    public String getMarqueVehicule() {
        return marqueVehicule;
    }

    public void setMarqueVehicule(String marqueVehicule) {
        this.marqueVehicule = marqueVehicule;
    }

    public String getUsageVehicule() {
        return usageVehicule;
    }

    public void setUsageVehicule(String usageVehicule) {
        this.usageVehicule = usageVehicule;
    }

    public Long getTypeService() {
        return typeService;
    }

    public void setTypeService(Long typeService) {
        this.typeService = typeService;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssureDTO{"
                + "id=" + getId()
                + "}";
    }
}
