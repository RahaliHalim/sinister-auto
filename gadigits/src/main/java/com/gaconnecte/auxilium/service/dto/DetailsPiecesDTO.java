package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.NaturePiece;
import com.gaconnecte.auxilium.domain.enumeration.ObservationRepairer;
import com.gaconnecte.auxilium.domain.Quotation;

/**
 * A DTO for the DetailsPieces entity.
 */


import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.ArrayList;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailsPiecesDTO")
public class DetailsPiecesDTO implements Serializable {
	
	   @XmlAttribute(name = "id")
    private Long id;
    //@NotNull
    @Max(value = 99999999)
    @XmlAttribute(name = "quantite")
    private Float quantite;
    @XmlAttribute(name = "nombreHeures")
    private Float nombreHeures;
    @XmlAttribute(name = "reference")
    private Integer reference;

    private Long designationId;


	private String designationReference;
	private String designationSource;


    private String designation;

    private String designationCode;

    @NotNull
    @DecimalMax(value = "100000000000000000")
    private Double prixUnit;

    @DecimalMax(value = "99")
    private Float tva;

    @DecimalMax(value = "99")
    private Float vetuste;

    private Long fournisseurId;
    private String  typePA;
    public String getTypePA() {
		return typePA;
	}

	public void setTypePA(String typePA) {
		this.typePA = typePA;
	}

	private Long quotationId;
    private Long quotationMPId;
   
    private Float totalHt;
    private Float discount;
    private Float amountDiscount;
    private Float amountAfterDiscount;
    private Float insuredParticipation;
    private Float HTVetuste;
    private  Float TTCVetuste;
    private  Long observationExpert;
    private  Float nombreMOEstime;
    private Float totalTtc;
    private String observation;
    private NaturePiece naturePiece;

    private ObservationRepairer observationRepairer;
    
    private Long typeInterventionId;
    

    private String typeInterventionLibelle;
    
    private Boolean isMo;
    
    private Long modifiedLine;

    private Boolean isModified;

    private Long vatRateId;

    private Float amountVat;
    
    private Long typeDesignationId;

    //private QuotationDTO quotation;
    
    private Boolean isComplementary;
    private Boolean isNew;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public Float getInsuredParticipation() {
		return insuredParticipation;
	}

	public void setInsuredParticipation(Float insuredParticipation) {
		this.insuredParticipation = insuredParticipation;
	}

	public Float getHTVetuste() {
		return HTVetuste;
	}

	public void setHTVetuste(Float hTVetuste) {
		HTVetuste = hTVetuste;
	}

	public Float getTTCVetuste() {
		return TTCVetuste;
	}

	public void setTTCVetuste(Float tTCVetuste) {
		TTCVetuste = tTCVetuste;
	}

	public Long getObservationExpert() {
		return observationExpert;
	}

	public void setObservationExpert(Long observationExpert) {
		this.observationExpert = observationExpert;
	}

	public Float getNombreMOEstime() {
		return nombreMOEstime;
	}

	public void setNombreMOEstime(Float nombreMOEstime) {
		this.nombreMOEstime = nombreMOEstime;
	}

	public void setTotalHt(Float totalHt) {
		this.totalHt = totalHt;
    }
    
    public Float getTotalHtF() {
        return totalHt;
    }

	public void setTotalTtc(Float totalTtc) {
		this.totalTtc = totalTtc;
	}

	public NaturePiece getNaturePiece() {
        return naturePiece;
    }

    public void setNaturePiece(NaturePiece naturePiece) {
        this.naturePiece = naturePiece;
    }

     public ObservationRepairer getObservationRepairer() {
        return observationRepairer;
    }

    public void setObservationRepairer(ObservationRepairer observationRepairer) {
        this.observationRepairer = observationRepairer;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignationCode() {
        return designationCode;
    }

    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    public Double getPrixUnit() {
        return prixUnit;
    }

    public void setPrixUnit(Double prixUnit) {
        this.prixUnit = prixUnit;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }
    
    /**
     * @return float return the totalHt
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
     * @return float return the totalTtc
     */
    public float getTotalTtc() {
        return totalTtc;
    }

    public Float getTotalTtcF() {
        return totalTtc;
    }

    /**
     * @param totalTtc the totalTtc to set
     */
    public void setTotalTtc(float totalTtc) {
        this.totalTtc = totalTtc;
    }

    public Float getVetuste() {
        return vetuste;
    }

    public void setVetuste(Float vetuste) {
        this.vetuste = vetuste;
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

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

    public Float getAmountDiscount() {
		return amountDiscount;
	}

	public void setAmountDiscount(Float amountDiscount) {
		this.amountDiscount = amountDiscount;
	}

    public Float getAmountAfterDiscount() {
		return amountAfterDiscount;
	}

	public void setAmountAfterDiscount(Float amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}

	public Float getNombreHeures() {
		return nombreHeures;
	}

	public void setNombreHeures(Float nombreHeures) {
		this.nombreHeures = nombreHeures;
	}

	public Long getTypeInterventionId() {
		return typeInterventionId;
	}

	public void setTypeInterventionId(Long typeInterventionId) {
		this.typeInterventionId = typeInterventionId;
	}

	public String getTypeInterventionLibelle() {
		return typeInterventionLibelle;
	}

	public void setTypeInterventionLibelle(String typeInterventionLibelle) {
		this.typeInterventionLibelle = typeInterventionLibelle;
	}
    
	public Boolean getIsMo() {
		return isMo;
	}

	public void setIsMo(Boolean isMo) {
		this.isMo = isMo;
	}

	public Long getModifiedLine() {
		return modifiedLine;
	}

	public void setModifiedLine(Long modifiedLine) {
		this.modifiedLine = modifiedLine;
	}

	public Boolean getIsModified() {
		return isModified;
	}

	public void setIsModified(Boolean isModified) {
		this.isModified = isModified;
	}

    public Long getVatRateId() {
		return vatRateId;
	}

	public void setVatRateId(Long vatRateId) {
		this.vatRateId = vatRateId;
	}

    public Float getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(Float amountVat) {
        this.amountVat = amountVat;
    }

    /*public QuotationDTO getQuotation() {
        return quotation;
    }

    public void setQuotation(QuotationDTO quotation) {
        this.quotation = quotation;
    }*/
    

	public Long getTypeDesignationId() {
		return typeDesignationId;
	}

	public void setTypeDesignationId(Long typeDesignationId) {
		this.typeDesignationId = typeDesignationId;
	}

	public Boolean getIsComplementary() {
		return isComplementary;
	}

	public void setIsComplementary(Boolean isComplementary) {
		this.isComplementary = isComplementary;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getDesignationSource() {
		return designationSource;
	}

	public void setDesignationSource(String designationSource) {
		this.designationSource = designationSource;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailsPiecesDTO detailsPiecesDTO = (DetailsPiecesDTO) o;
        if(detailsPiecesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailsPiecesDTO.getId());
    }

    public Long getQuotationMPId() {
		return quotationMPId;
	}

	public void setQuotationMPId(Long quotationMPId) {
		this.quotationMPId = quotationMPId;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailsPiecesDTO{" +
            "id=" + getId() +
            ", quantite='" + getQuantite() + "'" +
            ", prixUnit='" + getPrixUnit() + "'" +
            ", vetuste='" + getVetuste() + "'" +
            ", tva='" + getTva() + "'" +
            ", discount='" + getDiscount() + "'"+
            "}";
    }
}
