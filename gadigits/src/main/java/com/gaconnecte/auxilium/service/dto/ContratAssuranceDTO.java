package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;

import org.javers.core.metamodel.annotation.DiffIgnore;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ContratAssurance entity.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContratAssuranceDTO implements Serializable {

    private Long id;

 
    
    


    @DiffIgnore
    private Boolean isSuspendu;
    
    @DiffIgnore
    private Boolean isDelete;
    
    @DiffIgnore
    private Double newValueVehicle;
    
    @DiffIgnore
    private Double newValueVehicleFarnchise;
    
    @DiffIgnore
    private Double dcCapitalFranchise;
    @DiffIgnore
    private Double bgCapitalFranchise;
    @DiffIgnore
    private Double marketValueFranchise;
    @DiffIgnore
    private Double dcCapital;
    @DiffIgnore
    private Double bgCapital;
    @DiffIgnore
    private Double marketValue;
    @DiffIgnore
    private Double primeAmount;
  
    @DiffIgnore
    private String franchiseTypeMarketValue;
   
    @DiffIgnore
    private String franchiseTypeBgCapital;
    @DiffIgnore
    private String CIN;
    @DiffIgnore
    private String numMobile;
    @DiffIgnore
    private Long fractionnementId;
    @Size(max = 100)
    private String souscripteur;

    @Size(max = 2000)
    private String commentaire;
    private String numeroContrat;

    private LocalDate debutValidite;
    
    private LocalDate deadlineDate;
    private LocalDate receiptValidityDate;
    
    private LocalDate finValidite;
    private LocalDate amendmentEffectiveDate;


    private String typeLabel;
  
    private String natureLabel;

    private String statusLabel;
    private String receiptStatusLabel;
    
    private String franchiseTypeNewValue;
    private String franchiseTypeDcCapital;
    
    
    private String agenceNom;

    private Long usageId;

    private String usageLibelle;

    private Long vehiculeId;

    private String vehiculeImmatriculationVehicule;
    

    private String fractionnementLibelle;
    
    private String nomCompagnie;
    
    @DiffIgnore
    private Long typeId;
    @DiffIgnore
    private Long natureId;
    @DiffIgnore
    private Long agenceId;
   
    private Long packId;
    @DiffIgnore
    private String packNom;
    @DiffIgnore
    private String modeleLibelle;
    @DiffIgnore
    private String marqueLibelle;
    @DiffIgnore
    private Long clientId;



    @DiffIgnore
    private Boolean isAssujettieTva;
    @DiffIgnore
    private Boolean isRachat;
    @DiffIgnore
    private String typeFranchise;
    
    @DiffIgnore
    private Set<VehiculeAssureDTO> vehicules = new HashSet<>();
    @DiffIgnore
    private Boolean isExtract;
    @DiffIgnore
    private Integer interventionNumber;
    @DiffIgnore
    private Integer doneInterventionNumber;
    @DiffIgnore
    private Integer statusId;

    @DiffIgnore
    private Integer receiptStatusId;

    @DiffIgnore
    private Integer amendmentTypeId;
    @DiffIgnore
    private String amendmentTypeLabel;
    @DiffIgnore
    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie = nomCompagnie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public LocalDate getDebutValidite() {
        return debutValidite;
    }

    public void setDebutValidite(LocalDate debutValidite) {
        this.debutValidite = debutValidite;
    }

    public LocalDate getFinValidite() {
        return finValidite;
    }

    public void setFinValidite(LocalDate finValidite) {
        this.finValidite = finValidite;
    }

    public Boolean isIsSuspendu() {
        return isSuspendu;
    }

    public void setIsSuspendu(Boolean isSuspendu) {
        this.isSuspendu = isSuspendu;
    }

    public Boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Double getNewValueVehicle() {
        return newValueVehicle;
    }

    public void setNewValueVehicle(Double newValueVehicle) {
        this.newValueVehicle = newValueVehicle;
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

    public String getSouscripteur() {
        return souscripteur;
    }

    public void setSouscripteur(String souscripteur) {
        this.souscripteur = souscripteur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long refTypeContratId) {
        this.typeId = refTypeContratId;
    }

    public Long getNatureId() {
        return natureId;
    }

    public void setNatureId(Long refNatureContratId) {
        this.natureId = refNatureContratId;
    }

    public Long getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(Long refAgenceId) {
        this.agenceId = refAgenceId;
    }

    public String getAgenceNom() {
        return agenceNom;
    }

    public void setAgenceNom(String refAgenceNom) {
        this.agenceNom = refAgenceNom;
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

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeAssureId) {
        this.vehiculeId = vehiculeAssureId;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String cIN) {
        this.CIN = cIN;
    }

    public String getNumMobile() {
        return numMobile;
    }

    public void setNumMobile(String numMobile) {
        this.numMobile = numMobile;
    }

    public String getVehiculeImmatriculationVehicule() {
        return vehiculeImmatriculationVehicule;
    }

    public void setVehiculeImmatriculationVehicule(String vehiculeAssureImmatriculationVehicule) {
        this.vehiculeImmatriculationVehicule = vehiculeAssureImmatriculationVehicule;
    }

    public Long getFractionnementId() {
        return fractionnementId;
    }

    public void setFractionnementId(Long refFractionnementId) {
        this.fractionnementId = refFractionnementId;
    }

    public String getFractionnementLibelle() {
        return fractionnementLibelle;
    }

    public void setFractionnementLibelle(String refFractionnementLibelle) {
        this.fractionnementLibelle = refFractionnementLibelle;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long refPackId) {
        this.packId = refPackId;
    }

    public String getPackNom() {
        return packNom;
    }

    public void setPackNom(String refPackNom) {
        this.packNom = refPackNom;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getTypeFranchise() {
        return typeFranchise;
    }

    public void setTypeFranchise(String typeFranchise) {
        this.typeFranchise = typeFranchise;
    }

    public Boolean getIsRachat() {
        return isRachat;
    }

    public void setIsRachat(Boolean isRachat) {
        this.isRachat = isRachat;
    }

    public Set<VehiculeAssureDTO> getVehicules() {
        return vehicules;
    }

    public void setVehicules(Set<VehiculeAssureDTO> vehicules) {
        this.vehicules = vehicules;
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

    public Boolean getIsExtract() {
        return isExtract;
    }

    public void setIsExtract(Boolean isExtract) {
        this.isExtract = isExtract;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContratAssuranceDTO contratAssuranceDTO = (ContratAssuranceDTO) o;
        if (contratAssuranceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contratAssuranceDTO.getId());
    }

    public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	public String getNatureLabel() {
		return natureLabel;
	}

	public void setNatureLabel(String natureLabel) {
		this.natureLabel = natureLabel;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContratAssuranceDTO{"
                + "id=" + getId()
                + ", numeroContrat='" + getNumeroContrat() + "'"
                + ", debutValidite='" + getDebutValidite() + "'"
                + ", finValidite='" + getFinValidite() + "'"
                + ", isSuspendu='" + isIsSuspendu() + "'"
                + ", IsAssujettieTva='" + isIsAssujettieTva() + "'"
                + ", newValueVehicle='" + getNewValueVehicle() + "'"
                + ", dcCapital='" + getDcCapital() + "'"
                + ", marketValue='" + getMarketValue() + "'"
                + ", souscripteur='" + getSouscripteur() + "'"
                + ", commentaire='" + getCommentaire() + "'"
                + "}";
    }

    public Boolean isIsAssujettieTva() {
        return isAssujettieTva;
    }

    public void setIsAssujettieTva(Boolean isAssujettieTva) {
        this.isAssujettieTva = isAssujettieTva;
    }

    public Boolean getIsAssujettieTva() {
        return isAssujettieTva;
    }

    public Integer getInterventionNumber() {
        return interventionNumber;
    }

    public void setInterventionNumber(Integer interventionNumber) {
        this.interventionNumber = interventionNumber;
    }

    public Integer getDoneInterventionNumber() {
        return doneInterventionNumber;
    }

    public void setDoneInterventionNumber(Integer doneInterventionNumber) {
        this.doneInterventionNumber = doneInterventionNumber;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDate getAmendmentEffectiveDate() {
        return amendmentEffectiveDate;
    }

    public void setAmendmentEffectiveDate(LocalDate amendmentEffectiveDate) {
        this.amendmentEffectiveDate = amendmentEffectiveDate;
    }

    public LocalDate getReceiptValidityDate() {
        return receiptValidityDate;
    }

    public void setReceiptValidityDate(LocalDate receiptValidityDate) {
        this.receiptValidityDate = receiptValidityDate;
    }

    public Double getPrimeAmount() {
        return primeAmount;
    }

    public void setPrimeAmount(Double primeAmount) {
        this.primeAmount = primeAmount;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Integer getReceiptStatusId() {
        return receiptStatusId;
    }

    public void setReceiptStatusId(Integer receiptStatusId) {
        this.receiptStatusId = receiptStatusId;
    }

    public String getReceiptStatusLabel() {
        return receiptStatusLabel;
    }

    public void setReceiptStatusLabel(String receiptStatusLabel) {
        this.receiptStatusLabel = receiptStatusLabel;
    }

    public Integer getAmendmentTypeId() {
        return amendmentTypeId;
    }

    public void setAmendmentTypeId(Integer amendmentTypeId) {
        this.amendmentTypeId = amendmentTypeId;
    }

    public String getAmendmentTypeLabel() {
        return amendmentTypeLabel;
    }

    public void setAmendmentTypeLabel(String amendmentTypeLabel) {
        this.amendmentTypeLabel = amendmentTypeLabel;
    }

}
