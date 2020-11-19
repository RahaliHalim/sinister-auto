package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class ViewDetailQuotationDTO implements Serializable{



    private Long id;
    private Boolean receptionVehicule;
    private LocalDateTime vehicleReceiptDate;
    private Boolean lightShock;
    private Boolean disassemblyRequest;
    private Double marketValue;
    private Double newValueVehicle;
    private String numeroChassis;
    private Integer puissance;
    private String immatriculationVehicule;
    private Long idVehicule;
    private Long usageId;
    private String marqueLibelle;
    private String modeleLibelle;
    private String fullName;
    private String raisonSociale;
    private LocalDateTime declarationDate;
    private Long primaryQuotationId;
    private Long modeId;
    private Long packid;
    private String companyReference;
    private Integer vehicleNumber;
    private Long posGaId;
    private Long governorateId;
    private Long delegationId;
    private Long marqueId;
    private Boolean isGaEstimate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public String getImmatriculationVehicule() {
        return immatriculationVehicule;
    }

    public void setImmatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
    }

    public String getMarqueLibelle() {
        return marqueLibelle;
    }

    public void setMarqueLibelle(String marqueLibelle) {
        this.marqueLibelle = marqueLibelle;
    }

    public String getModeleLibelle() {
        return modeleLibelle;
    }

    public void setModeleLibelle(String modeleLibelle) {
        this.modeleLibelle = modeleLibelle;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public Boolean getReceptionVehicule() {
        return receptionVehicule;
    }

    public void setReceptionVehicule(Boolean receptionVehicule) {
        this.receptionVehicule = receptionVehicule;
    }

    public Boolean getLightShock() {
        return lightShock;
    }

    public void setLightShock(Boolean lightShock) {
        this.lightShock = lightShock;
    }

    public Boolean getDisassemblyRequest() {
        return disassemblyRequest;
    }

    public void setDisassemblyRequest(Boolean disassemblyRequest) {
        this.disassemblyRequest = disassemblyRequest;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getNewValueVehicle() {
        return newValueVehicle;
    }

    public void setNewValueVehicle(Double newValueVehicle) {
        this.newValueVehicle = newValueVehicle;
    }

    public Integer getPuissance() {
        return puissance;
    }

    public void setPuissance(Integer puissance) {
        this.puissance = puissance;
    }

    public LocalDateTime getVehicleReceiptDate() {
        return vehicleReceiptDate;
    }

    public void setVehicleReceiptDate(LocalDateTime vehicleReceiptDate) {
        this.vehicleReceiptDate = vehicleReceiptDate;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

    public LocalDateTime getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(LocalDateTime declarationDate) {
        this.declarationDate = declarationDate;
    }

    public Long getPrimaryQuotationId() {
        return primaryQuotationId;
    }

    public void setPrimaryQuotationId(Long primaryQuotationId) {
        this.primaryQuotationId = primaryQuotationId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public Long getPackid() {
        return packid;
    }

    public void setPackid(Long packid) {
        this.packid = packid;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public void setCompanyReference(String companyReference) {
        this.companyReference = companyReference;
    }

    public Integer getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(Integer vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Long getPosGaId() {
        return posGaId;
    }

    public void setPosGaId(Long posGaId) {
        this.posGaId = posGaId;
    }

    public Long getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(Long governorateId) {
        this.governorateId = governorateId;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public Long getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Long marqueId) {
        this.marqueId = marqueId;
    }

    public Boolean getIsGaEstimate() {
        return isGaEstimate;
    }

    public void setIsGaEstimate(Boolean isGaEstimate) {
        this.isGaEstimate = isGaEstimate;
    }

	public Long getIdVehicule() {
		return idVehicule;
	}

	public void setIdVehicule(Long idVehicule) {
		this.idVehicule = idVehicule;
	}


}
