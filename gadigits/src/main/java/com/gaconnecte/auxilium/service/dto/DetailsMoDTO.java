package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;

/**
 * A DTO for the DetailsMo entity.
 */
public class DetailsMoDTO implements Serializable {

    private Long id;

    private float nombreHeures;

    private Long designationId;

    private String designationReference;

    private Long devisId;

    private Long typeInterventionId;

    private String typeInterventionLibelle;

    private float th;

    private float vat;

    private float discount;

    private float totalHt;

    private float totalTtc;

    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(float nombreHeures) {
        this.nombreHeures = nombreHeures;
    }
    
    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getDesignationReference() {
        return designationReference;
    }

    public void setDesignationReference(String designationReference) {
        this.designationReference = designationReference;
    }

    public Long getDevisId() {
        return devisId;
    }

    public void setDevisId(Long devisId) {
        this.devisId = devisId;
    }

    public Long getTypeInterventionId() {
        return typeInterventionId;
    }

    public void setTypeInterventionId(Long refTypeInterventionId) {
        this.typeInterventionId = refTypeInterventionId;
    }

    public String getTypeInterventionLibelle() {
        return typeInterventionLibelle;
    }

    public void setTypeInterventionLibelle(String refTypeInterventionLibelle) {
        this.typeInterventionLibelle = refTypeInterventionLibelle;
    }

    /**
     * @return the th
     */
    public float getTh() {
        return th;
    }

    /**
     * @param th the th to set
     */
    public void setTh(float th) {
        this.th = th;
    }

    /**
     * @return the discount
     */
    public float getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    /**
     * @return the vat
     */
    public float getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(float vat) {
        this.vat = vat;
    }

    /**
     * @return the totalHt
     */
    public float getTotalHt() {
        return totalHt;
    }

    /**
     * @param totalHt the totalHt to set
     */
    public void setTotalHt(float totalHt) {
        this.totalHt = totalHt;
    }

    /**
     * @return the totalTtc
     */
    public float getTotalTtc() {
        return totalTtc;
    }

    /**
     * @param totalTtc the totalTtc to set
     */
    public void setTotalTtc(float totalTtc) {
        this.totalTtc = totalTtc;
    }

    /**
     * @param observation the observation to set
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

    /**
     * @return the observation
     */
    public String getObservation() {
        return observation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailsMoDTO detailsMoDTO = (DetailsMoDTO) o;
        if(detailsMoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailsMoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailsMoDTO{" +
            "id=" + getId() +
            ", nombreHeures='" + getNombreHeures() + "'" +
            "}";
    }


}
