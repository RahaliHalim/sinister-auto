package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TodoPrestationAvt entity.
 */
public class TodoPrestationAvtDTO implements Serializable, Comparable<TodoPrestationAvtDTO> {

    private Long id;
    private Long distance;
    
    private Long prestationId;
    private String prestationReference;
    
    private Long destinationId;
    private String destinationName;
    private Long remorqueurId;
    
    private Long insuredId;
    private String insuredName;
    
    private Long vehiculeId;
    private String vehiculeRegistrationCode;

    private Long statusId;
    private String statusName;

    private String incidentNature;
    private String incidentLocation;



    /**
     * @return Long return the id
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
     * @return Long return the distance
     */
    public Long getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(Long distance) {
        this.distance = distance;
    }

    /**
     * @return Long return the prestationId
     */
    public Long getPrestationId() {
        return prestationId;
    }

    /**
     * @param prestationId the prestationId to set
     */
    public void setPrestationId(Long prestationId) {
        this.prestationId = prestationId;
    }

    /**
     * @return String return the prestationReference
     */
    public String getPrestationReference() {
        return prestationReference;
    }

    /**
     * @param prestationReference the prestationReference to set
     */
    public void setPrestationReference(String prestationReference) {
        this.prestationReference = prestationReference;
    }

    /**
     * @return Long return the destinationId
     */
    public Long getDestinationId() {
        return destinationId;
    }

    /**
     * @param destinationId the destinationId to set
     */
    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    /**
     * @return String return the destinationName
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * @param destinationName the destinationName to set
     */
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * @return Long return the remorqueurId
     */
    public Long getRemorqueurId() {
        return remorqueurId;
    }

    /**
     * @param remorqueurId the remorqueurId to set
     */
    public void setRemorqueurId(Long remorqueurId) {
        this.remorqueurId = remorqueurId;
    }

    /**
     * @return Long return the insuredId
     */
    public Long getInsuredId() {
        return insuredId;
    }

    /**
     * @param insuredId the insuredId to set
     */
    public void setInsuredId(Long insuredId) {
        this.insuredId = insuredId;
    }

    /**
     * @return String return the insuredName
     */
    public String getInsuredName() {
        return insuredName;
    }

    /**
     * @param insuredName the insuredName to set
     */
    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

        /**
     * @return Long return the vehiculeId
     */
    public Long getVehiculeId() {
        return vehiculeId;
    }

    /**
     * @param vehiculeId the vehiculeId to set
     */
    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    /**
     * @return String return the vehiculeRegistrationCode
     */
    public String getVehiculeRegistrationCode() {
        return vehiculeRegistrationCode;
    }

    /**
     * @param vehiculeRegistrationCode the vehiculeRegistrationCode to set
     */
    public void setVehiculeRegistrationCode(String vehiculeRegistrationCode) {
        this.vehiculeRegistrationCode = vehiculeRegistrationCode;
    }

    /**
     * @return Long return the statusId
     */
    public Long getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    /**
     * @return String return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    /**
     * @return String return the incidentNature
     */
    public String getIncidentNature() {
        return incidentNature;
    }

    /**
     * @param incidentNature the incidentNature to set
     */
    public void setIncidentNature(String incidentNature) {
        this.incidentNature = incidentNature;
    }

    /**
     * @return String return the incidentLocation
     */
    public String getIncidentLocation() {
        return incidentLocation;
    }

    /**
     * @param incidentLocation the incidentLocation to set
     */
    public void setIncidentLocation(String incidentLocation) {
        this.incidentLocation = incidentLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TodoPrestationAvtDTO serviceRmqDTO = (TodoPrestationAvtDTO) o;
        if(serviceRmqDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRmqDTO.getId());
    }

    @Override
    public int compareTo(TodoPrestationAvtDTO o) {
        return this.getId().compareTo(o.getId());
    }
    @Override
    public String toString() {
        return "TodoPrestationAvtDTO {" +
            "id=" + getId() +
            ", distance='" + getDistance() + "'" +
            "}";
    }
}
