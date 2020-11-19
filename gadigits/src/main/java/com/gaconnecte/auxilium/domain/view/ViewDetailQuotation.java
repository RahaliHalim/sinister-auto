package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "view_detail_quotation")
public class ViewDetailQuotation implements Serializable{
private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "receptionvehicule")
    private Boolean receptionVehicule;

    @Column(name = "vehiclereceiptdate")
    private LocalDateTime vehicleReceiptDate;

    @Column(name = "lightshock")
    private Boolean lightShock;

    @Column(name = "disassemblyrequest")
    private Boolean disassemblyRequest;

    @Column(name = "marketvalue")
    private Double marketValue;

    @Column(name = "newvaluevehicle")
    private Double newValueVehicle;

    @Column(name = "numerochassis")
    private String numeroChassis;

    @Column(name = "puissance")
    private Integer puissance;

    @Column(name = "immatriculationvehicule")
    private String immatriculationVehicule;

    @Column(name = "usageid")
    private Long usageId;

    @Column(name = "marquelibelle")
    private String marqueLibelle;

    @Column(name = "modelelibelle")
    private String modeleLibelle;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "raisonsociale")
    private String raisonSociale; 

    @Column(name = "declarationdate")
    private LocalDateTime declarationDate;

    @Column(name = "primaryquotationid")
    private Long primaryQuotationId;

    @Column(name = "modeid")
    private Long modeId;

    @Column(name = "packid")
    private Long packid;

    @Column(name = "companyreference")
    private String companyReference;

    @Column(name = "vehiclenumber")
    private Integer vehicleNumber;

    @Column(name = "posgaid")
    private Long posGaId;

    @Column(name = "governorateid")
    private Long governorateId;

    @Column(name = "delegationid")
    private Long delegationId;

    @Column(name = "marqueid")
    private Long marqueId;

    @Column(name = "isgaestimate")
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

    public LocalDateTime getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(LocalDateTime declarationDate) {
        this.declarationDate = declarationDate;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
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

    public void setGovernorateId(Long posGaId) {
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


}
