package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TodoPrestationAvt entity.
 */
public class TodoPrestationPecDTO implements Serializable, Comparable<TodoPrestationPecDTO> {

    private Long id;
    private Long prestationId;
    
    private String descPtsChoc;
    private Integer nbrVehicules;
    private String prestationReference;
    
    private String modeLibelle;
    private String posGaLibelle;

    private String userFirstName;  
    private String userLastName;
    
    private Long quotationId;
    private String quotationStatus;

    private Long statusId;
    private String statusName;




    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
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
     * @return String return the descPtsChoc
     */
    public String getDescPtsChoc() {
        return descPtsChoc;
    }

    /**
     * @param descPtsChoc the descPtsChoc to set
     */
    public void setDescPtsChoc(String descPtsChoc) {
        this.descPtsChoc = descPtsChoc;
    }

    /**
     * @return Integer return the nbrVehicules
     */
    public Integer getNbrVehicules() {
        return nbrVehicules;
    }

    /**
     * @param nbrVehicules the nbrVehicules to set
     */
    public void setNbrVehicules(Integer nbrVehicules) {
        this.nbrVehicules = nbrVehicules;
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
     * @return String return the modeLibelle
     */
    public String getModeLibelle() {
        return modeLibelle;
    }

    /**
     * @param modeLibelle the modeLibelle to set
     */
    public void setModeLibelle(String modeLibelle) {
        this.modeLibelle = modeLibelle;
    }

    /**
     * @return String return the posGaLibelle
     */
    public String getPosGaLibelle() {
        return posGaLibelle;
    }

    /**
     * @param posGaLibelle the posGaLibelle to set
     */
    public void setPosGaLibelle(String posGaLibelle) {
        this.posGaLibelle = posGaLibelle;
    }

    /**
     * @return String return the userFirstName
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * @param userFirstName the userFirstName to set
     */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * @return String return the userLastName
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * @param userLastName the userLastName to set
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * @return Long return the quotationId
     */
    public Long getQuotationId() {
        return quotationId;
    }

    /**
     * @param quotationId the quotationId to set
     */
    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    /**
     * @return String return the quotationStatus
     */
    public String getQuotationStatus() {
        return quotationStatus;
    }

    /**
     * @param quotationStatus the quotationStatus to set
     */
    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
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
    public int compareTo(TodoPrestationPecDTO o) {
        return this.getId().compareTo(o.getId());
    }
    @Override
    public String toString() {
        return "TodoPrestationAvtDTO {" +
            "id=" + getId() +
            ", distance='" + getPrestationId() + "'" +
            "}";
    }
}
