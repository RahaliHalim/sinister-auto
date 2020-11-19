package com.gaconnecte.auxilium.service.dto;

import com.gaconnecte.auxilium.domain.VehicleBrandModel;
import java.time.LocalDate;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehiculeAssure entity.
 */
public class VehiculeAssureDTO implements Serializable {

    private Long id;

    @NotNull
    private String immatriculationVehicule;

    private Integer puissance;

    private Long usageId;

    private String usageLibelle;

    private Long marqueId;

    private String marqueLibelle;

    private Long contratId;

    private Long insuredId;

    private AssureDTO insured;

    private String numeroChassis;

    private LocalDate datePCirculation;

    private Long modeleId;

    private VehicleBrandModel refmodel;

    private String modeleLibelle;

    private Long energieId;

    private String energieLibelle;

    private Integer nombreDePlace;

    private String mdp;

    private Long packId;

    private String packLabel;

    private Long partnerId;

    private Double newValueVehicle;

    private Double newValueVehicleFarnchise;

    private Double dcCapitalFranchise;

    private Double bgCapitalFranchise;

    private Double marketValueFranchise;

    private Double dcCapital;

    private Double bgCapital;

    private Double marketValue;

    private String franchiseTypeNewValue;

    private String franchiseTypeMarketValue;

    private String franchiseTypeDcCapital;

    private String franchiseTypeBgCapital;
    
    private String compagnyName;

	private Boolean assujettieTVA;
	
    public Integer getNombreDePlace() {
        return nombreDePlace;
    }

    public void setNombreDePlace(Integer nombreDePlace) {
        this.nombreDePlace = nombreDePlace;
    }

    public Long getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Long marqueId) {
        this.marqueId = marqueId;
    }

    public String getMarqueLibelle() {
        return marqueLibelle;
    }

    public void setMarqueLibelle(String marqueLibelle) {
        this.marqueLibelle = marqueLibelle;
    }

    public Long getContratId() {
        return contratId;
    }

    public void setContratId(Long contratId) {
        this.contratId = contratId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculationVehicule() {
        return immatriculationVehicule;
    }

    public void setImmatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
    }

    public Integer getPuissance() {
        return puissance;
    }

    public void setPuissance(Integer puissance) {
        this.puissance = puissance;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long refUsageContratId) {
        this.usageId = refUsageContratId;
    }

    public String getUsageLibelle() {
        return usageLibelle;
    }

    public void setUsageLibelle(String refUsageContratLibelle) {
        this.usageLibelle = refUsageContratLibelle;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public LocalDate getDatePCirculation() {
        return datePCirculation;
    }

    public void setDatePCirculation(LocalDate datePCirculation) {
        this.datePCirculation = datePCirculation;
    }

    public Long getModeleId() {
        return modeleId;
    }

    public void setModeleId(Long refModelVoitureId) {
        this.modeleId = refModelVoitureId;
    }

    public String getModeleLibelle() {
        return modeleLibelle;
    }

    public void setModeleLibelle(String refModelVoitureLibelle) {
        this.modeleLibelle = refModelVoitureLibelle;
    }

    public Long getEnergieId() {
        return energieId;
    }

    public void setEnergieId(Long refEnergieId) {
        this.energieId = refEnergieId;
    }

    public String getEnergieLibelle() {
        return energieLibelle;
    }

    public void setEnergieLibelle(String refEnergieLibelle) {
        this.energieLibelle = refEnergieLibelle;
    }

    public VehicleBrandModel getRefmodel() {
        return refmodel;
    }

    public void setRefmodel(VehicleBrandModel refmodel) {
        this.refmodel = refmodel;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPackLabel() {
        return packLabel;
    }

    public void setPackLabel(String packLabel) {
        this.packLabel = packLabel;
    }

    public Double getNewValueVehicle() {
        return newValueVehicle;
    }

    public void setNewValueVehicle(Double newValueVehicle) {
        this.newValueVehicle = newValueVehicle;
    }

    public Double getNewValueVehicleFarnchise() {
        return newValueVehicleFarnchise;
    }

    public void setNewValueVehicleFarnchise(Double newValueVehicleFarnchise) {
        this.newValueVehicleFarnchise = newValueVehicleFarnchise;
    }

    public Double getDcCapitalFranchise() {
        return dcCapitalFranchise;
    }

    public void setDcCapitalFranchise(Double dcCapitalFranchise) {
        this.dcCapitalFranchise = dcCapitalFranchise;
    }

    public Double getBgCapitalFranchise() {
        return bgCapitalFranchise;
    }

    public void setBgCapitalFranchise(Double bgCapitalFranchise) {
        this.bgCapitalFranchise = bgCapitalFranchise;
    }

    public Double getMarketValueFranchise() {
        return marketValueFranchise;
    }

    public void setMarketValueFranchise(Double marketValueFranchise) {
        this.marketValueFranchise = marketValueFranchise;
    }

    public Double getDcCapital() {
        return dcCapital;
    }

    public void setDcCapital(Double dcCapital) {
        this.dcCapital = dcCapital;
    }

    public Double getBgCapital() {
        return bgCapital;
    }

    public void setBgCapital(Double bgCapital) {
        this.bgCapital = bgCapital;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public String getFranchiseTypeNewValue() {
        return franchiseTypeNewValue;
    }

    public void setFranchiseTypeNewValue(String franchiseTypeNewValue) {
        this.franchiseTypeNewValue = franchiseTypeNewValue;
    }

    public String getFranchiseTypeMarketValue() {
        return franchiseTypeMarketValue;
    }

    public void setFranchiseTypeMarketValue(String franchiseTypeMarketValue) {
        this.franchiseTypeMarketValue = franchiseTypeMarketValue;
    }

    public String getFranchiseTypeDcCapital() {
        return franchiseTypeDcCapital;
    }

    public void setFranchiseTypeDcCapital(String franchiseTypeDcCapital) {
        this.franchiseTypeDcCapital = franchiseTypeDcCapital;
    }

    public String getFranchiseTypeBgCapital() {
        return franchiseTypeBgCapital;
    }

    public void setFranchiseTypeBgCapital(String franchiseTypeBgCapital) {
        this.franchiseTypeBgCapital = franchiseTypeBgCapital;
    }

    public Long getInsuredId() {
        return insuredId;
    }

    public void setInsuredId(Long insuredId) {
        this.insuredId = insuredId;
    }

    public AssureDTO getInsured() {
        return insured;
    }

    public void setInsured(AssureDTO insured) {
        this.insured = insured;
    }

    public String getCompagnyName() {
		return compagnyName;
	}

	public void setCompagnyName(String compagnyName) {
		this.compagnyName = compagnyName;
	}

	public Boolean getAssujettieTVA() {
		return assujettieTVA;
	}

	public void setAssujettieTVA(Boolean assujettieTVA) {
		this.assujettieTVA = assujettieTVA;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehiculeAssureDTO vehiculeAssureDTO = (VehiculeAssureDTO) o;
        if (vehiculeAssureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculeAssureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiculeAssureDTO{" + "id=" + getId() + ", immatriculationVehicule='" + getImmatriculationVehicule()
                + "'" + ", puissance='" + getPuissance() + "'" + ", numeroChassis='" + getNumeroChassis() + "'"
                + ", datePCirculation='" + getDatePCirculation() + "'" + "}";
    }
}
