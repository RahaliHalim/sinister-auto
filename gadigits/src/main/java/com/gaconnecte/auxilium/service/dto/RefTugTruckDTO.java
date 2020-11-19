package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Camion entity.
 */
public class RefTugTruckDTO implements Serializable {

    private Long id;

    private String immatriculation;
   
    private Boolean hasHabillage;

    private Long refTugId;
    
    private RefRemorqueurDTO refTug;
    
    private Set<RefTypeServiceDTO> serviceTypes;

    private String statusLabel;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Boolean getHasHabillage() {
        return hasHabillage;
    }

    public void setHasHabillage(Boolean hasHabillage) {
        this.hasHabillage = hasHabillage;
    }

    public Long getRefTugId() {
        return refTugId;
    }

    public void setRefTugId(Long refTugId) {
        this.refTugId = refTugId;
    }

    public RefRemorqueurDTO getRefTug() {
        return refTug;
    }

    public void setRefTug(RefRemorqueurDTO refTug) {
        this.refTug = refTug;
    }

    public Set<RefTypeServiceDTO> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<RefTypeServiceDTO> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RefTugTruckDTO other = (RefTugTruckDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RefTugTruckDTO{" + "id=" + id + ", immatriculation=" + immatriculation + ", hasHabillage=" + hasHabillage + ", refTugId=" + refTugId + ", serviceTypes=" + serviceTypes + '}';
    }
       

}
