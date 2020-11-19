package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.NaturePiece;
import com.gaconnecte.auxilium.domain.enumeration.ObservationRepairer;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DetailsPieces.
 */
@Entity
@Table(name = "details_pieces")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "detailspieces")


public class DetailsPieces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    //@NotNull
    @Max(value = 99999999)
    @Column(name = "quantite")
    private Float quantite;

    @Column(name = "nombre_heures")
    private Float nombreHeures;

    @Column(name = "reference")
    private Integer reference;

 
    @Column(name = "vetuste")
    private Float vetuste;
    

    @Column(name = "insured_participation")
    private Float insuredParticipation;
    

    @Column(name = "ht_vetuste")
    private Float HTVetuste;
    

    @Column(name = "ttc_vetuste")
    private  Float TTCVetuste;
    

    @Column(name = "observation_expert")
    private  Long observationExpert; 
    
    @Column(name = "type_pa")
    private String  typePA;

	@Column(name = "nombre_mo_estime")
    private  Float nombreMOEstime;

    @ManyToOne(optional = false)
    @JsonBackReference(value="designation")
    private VehiclePiece designation;

    @Column(name = "observation")
    private String observation;

   // @NotNull
    @DecimalMax(value = "100000000000000000")
    @Column(name = "prix_unit")
    private Double prixUnit;

    @DecimalMax(value = "99")
    @Column(name = "tva")
    private Float tva;
 
    @OneToOne
    @JoinColumn(name = "vat_rate_id")
    private VatRate vatRate;

    @Column(name = "amount_vat")
    private Float amountVat;
  
    @Column(name = "total_ht")
    @JsonBackReference(value="totalHt")
    private Float totalHt;

    @DecimalMax(value = "99")
    @Column(name = "discount")
    private Float discount;

    @Column(name = "amount_discount")
    private Float amountDiscount;

    @Column(name = "amount_after_discount")
    private Float amountAfterDiscount;

    @Column(name = "total_ttc")
    private Float totalTtc;

    @OneToMany(mappedBy = "detailsPieces")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacturePieces> facturePieces = new HashSet<>();

    @ManyToOne
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "quotation_id", referencedColumnName = "id")
    private Quotation quotation;
    
    @ManyToOne
    @JoinColumn(name = "quotation_mp_id", referencedColumnName = "id")
    private QuotationMP quotationMP;

    @ManyToOne(optional = false)
    //@NotNull
    private RefTypeIntervention typeIntervention;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature_piece")
    private NaturePiece naturePiece;

    @Enumerated(EnumType.STRING)
    @Column(name = "observation_repairer")
    private ObservationRepairer observationRepairer;
    
    @Column(name = "is_mo")
    private Boolean isMo;
    
    @Column(name = "modified_line")
    private Long modifiedLine;
    
    @Column(name = "is_modified")
    private Boolean isModified;
    
    @Column(name = "is_complementary")
    private Boolean isComplementary;

    @Column(name = "is_new")
    private Boolean isNew;

    
    public String getTypePA() {
		return typePA;
	}

	public void setTypePA(String typePA) {
		this.typePA = typePA;
	}

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
    public NaturePiece getNaturePiece() {
        return naturePiece;
    }

    public DetailsPieces naturePiece(NaturePiece naturePiece) {
        this.naturePiece = naturePiece;
        return this;
    }

    public void setNaturePiece(NaturePiece naturePiece) {
        this.naturePiece = naturePiece;
    }

    public ObservationRepairer getObservationRepairer() {
        return observationRepairer;
    }

    public DetailsPieces observationRepairer(ObservationRepairer observationRepairer) {
        this.observationRepairer = observationRepairer;
        return this;
    }

    public void setObservationRepairer(ObservationRepairer observationRepairer) {
        this.observationRepairer = observationRepairer;
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

    public Float getQuantite() {
        return quantite;
    }

    public DetailsPieces quantite(Float quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public Integer getReference() {
        return reference;
    }

    public DetailsPieces reference(Integer reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    public VehiclePiece getDesignation() {
        return designation;
    }

    public DetailsPieces designation(VehiclePiece designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(VehiclePiece designation) {
        this.designation = designation;
    }

    public Double getPrixUnit() {
        return prixUnit;
    }

    public DetailsPieces prixUnit(Double prixUnit) {
        this.prixUnit = prixUnit;
        return this;
    }

    public void setPrixUnit(Double prixUnit) {
        this.prixUnit = prixUnit;
    }

    public Float getTva() {
        return tva;
    }

    public DetailsPieces tva(Float tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

     public VatRate getVatRate() {
        return vatRate;
    }

    public void setVatRate(VatRate vatRate) {
        this.vatRate = vatRate;
    }

    /**
     * @return the totalHt
     */
    public Float getTotalHt() {
        return totalHt;
    }

    /**
     * @param totalHt the totalHt to set
     */
    public void setTotalHt(Float totalHt) {
        this.totalHt = totalHt;
    }

     public Float getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(Float amountVat) {
        this.amountVat = amountVat;
    }

    /**
     * @return the totalTtc
     */
    public Float getTotalTtc() {
        return totalTtc;
    }

    /**
     * @param totalTtc the totalTtc to set
     */
    public void setTotalTtc(Float totalTtc) {
        this.totalTtc = totalTtc;
    }

    public Set<FacturePieces> getFacturePieces() {
        return facturePieces;
    }

    public DetailsPieces facturePieces(Set<FacturePieces> facturePieces) {
        this.facturePieces = facturePieces;
        return this;
    }

    public DetailsPieces addFacturePiece(FacturePieces facturePieces) {
        this.facturePieces.add(facturePieces);
        facturePieces.setDetailsPieces(this);
        return this;
    }

    public DetailsPieces removeFacturePiece(FacturePieces facturePieces) {
        this.facturePieces.remove(facturePieces);
        facturePieces.setDetailsPieces(null);
        return this;
    }

    public void setFacturePieces(Set<FacturePieces> facturePieces) {
        this.facturePieces = facturePieces;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public DetailsPieces fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public DetailsPieces Quotation(Quotation quotation) {
        this.quotation = quotation;
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
    
    public QuotationMP getQuotationMP() {
        return quotationMP;
    }

    public DetailsPieces QuotationMP(QuotationMP quotationMP) {
        this.quotationMP = quotationMP;
        return this;
    }

    public void setQuotationMP(QuotationMP quotationMP) {
        this.quotationMP = quotationMP;
    }

    public Float getVetuste() {
        return vetuste;
    }

    public DetailsPieces vetuste(Float vetuste) {
        this.vetuste = vetuste;
        return this;
    }

    public void setVetuste(Float vetuste) {
        this.vetuste = vetuste;
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

    public RefTypeIntervention getTypeIntervention() {
        return typeIntervention;
    }

    public void setTypeIntervention(RefTypeIntervention typeIntervention) {
        this.typeIntervention = typeIntervention;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetailsPieces detailsPieces = (DetailsPieces) o;
        if (detailsPieces.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailsPieces.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailsPieces{"
                + "id=" + getId()
                + ", quantite='" + getQuantite() + "'"
                + ", reference='" + getReference() + "'"
                + ", designation='" + getDesignation() + "'"
                + ", vetuste='" + getVetuste() + "'"
                + ", prixUnit='" + getPrixUnit() + "'"
                + ", tva='" + getTva() + "'"
                + "}";
    }
}
