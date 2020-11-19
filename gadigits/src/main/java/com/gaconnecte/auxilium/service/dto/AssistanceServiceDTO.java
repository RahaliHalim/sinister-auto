package com.gaconnecte.auxilium.service.dto;

import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

public class AssistanceServiceDTO {
    private Long id;
    private Long distance;
    private Long prestationId;
    private Long destinationId;
    private String destinationNom;
    private Long gouvernoratId;
    private String gouvernoratName;
    private Long remorqueurId;
    private String remorqueurName;
    private Long vehiculeId;
    private String immatriculation;
    private String insuredName;
    private EtatDossierRmq etat;
    private ZonedDateTime tugArrivalDate;
    private ZonedDateTime insuredArrivalDate;
    private LocalDate incidentDate;
    private String incidentNature;
    private String incidentLocation;
    private String reference;
    private String typeService;
    private Double montant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationId) {
        this.prestationId = prestationId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationNom() {
        return destinationNom;
    }

    public void setDestinationNom(String destinationNom) {
        this.destinationNom = destinationNom;
    }

    public Long getGouvernoratId() {
        return gouvernoratId;
    }

    public void setGouvernoratId(Long gouvernoratId) {
        this.gouvernoratId = gouvernoratId;
    }

    public String getGouvernoratName() {
        return gouvernoratName;
    }

    public void setGouvernoratName(String gouvernoratName) {
        this.gouvernoratName = gouvernoratName;
    }

    public Long getRemorqueurId() {
        return remorqueurId;
    }

    public void setRemorqueurId(Long remorqueurId) {
        this.remorqueurId = remorqueurId;
    }

    public String getRemorqueurName() {
        return remorqueurName;
    }

    public void setRemorqueurName(String remorqueurName) {
        this.remorqueurName = remorqueurName;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public EtatDossierRmq getEtat() {
        return etat;
    }

    public void setEtat(EtatDossierRmq etat) {
        this.etat = etat;
    }

    public ZonedDateTime getTugArrivalDate() {
        return tugArrivalDate;
    }

    public void setTugArrivalDate(ZonedDateTime tugArrivalDate) {
        this.tugArrivalDate = tugArrivalDate;
    }

    public ZonedDateTime getInsuredArrivalDate() {
        return insuredArrivalDate;
    }

    public void setInsuredArrivalDate(ZonedDateTime insuredArrivalDate) {
        this.insuredArrivalDate = insuredArrivalDate;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getIncidentNature() {
        return incidentNature;
    }

    public void setIncidentNature(String incidentNature) {
        this.incidentNature = incidentNature;
    }

    public String getIncidentLocation() {
        return incidentLocation;
    }

    public void setIncidentLocation(String incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssistanceServiceDTO that = (AssistanceServiceDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AssistanceServiceDTO{" +
            "id=" + id +
            ", distance=" + distance +
            ", prestationId=" + prestationId +
            ", destinationId=" + destinationId +
            ", destinationNom='" + destinationNom + '\'' +
            ", gouvernoratId=" + gouvernoratId +
            ", gouvernoratName='" + gouvernoratName + '\'' +
            ", remorqueurId=" + remorqueurId +
            ", remorqueurName='" + remorqueurName + '\'' +
            ", vehiculeId=" + vehiculeId +
            ", immatriculation='" + immatriculation + '\'' +
            ", insuredName='" + insuredName + '\'' +
            ", etat=" + etat +
            ", tugArrivalDate=" + tugArrivalDate +
            ", insuredArrivalDate=" + insuredArrivalDate +
            '}';
    }
}
