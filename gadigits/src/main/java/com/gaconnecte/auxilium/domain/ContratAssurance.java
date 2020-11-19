package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import javax.persistence.FetchType;

/**
 * A ContratAssurance.
 */
@Entity
@Table(name = "contrat_assurance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contratassurance")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ContratAssurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_contrat")
    private String numeroContrat;

    @Column(name = "debut_validite")
    private LocalDate debutValidite;

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @Column(name = "amendment_effective_date")
    private LocalDate amendmentEffectiveDate;

    @Column(name = "receipt_validity_date")
    private LocalDate receiptValidityDate;

    @Column(name = "fin_validite")
    private LocalDate finValidite;

    @Column(name = "is_suspendu")
    private Boolean isSuspendu;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Size(max = 100)
    @Column(name = "souscripteur", length = 100)
    private String souscripteur;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @Column(name = "is_assujettie_tva")
    private Boolean isAssujettieTva;

    @Column(name = "is_rachat")
    private Boolean isRachat;

    @Column(name = "is_extract")
    private Boolean isExtract;

    @Column(name = "prime_amount")
    private Double primeAmount;

    @ManyToOne
    private RefTypeContrat type;

    @ManyToOne
    private RefNatureContrat nature;

    @ManyToOne
    private Agency agence;

    @ManyToOne
    private VehicleUsage usage;

    @OneToMany(mappedBy = "contrat", fetch = FetchType.EAGER)
    private Set<VehiculeAssure> vehicules;

    @ManyToOne
    private RefFractionnement fractionnement;

    @ManyToOne
    private RefPack pack;

    @ManyToOne(optional = false)
    @NotNull
    private Partner client;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private PolicyStatus status;

    @ManyToOne
    @JoinColumn(name = "receipt_status_id")
    private PolicyReceiptStatus receiptStatus;

    @ManyToOne
    @JoinColumn(name = "amendment_type_id")
    private AmendmentType amendmentType;

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

    public LocalDate getFinValidite() {
        return finValidite;
    }

    public void setFinValidite(LocalDate finValidite) {
        this.finValidite = finValidite;
    }

    public Boolean getIsSuspendu() {
        return isSuspendu;
    }

    public void setIsSuspendu(Boolean isSuspendu) {
        this.isSuspendu = isSuspendu;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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

    public Boolean getIsAssujettieTva() {
        return isAssujettieTva;
    }

    public void setIsAssujettieTva(Boolean isAssujettieTva) {
        this.isAssujettieTva = isAssujettieTva;
    }

    public Boolean getIsRachat() {
        return isRachat;
    }

    public void setIsRachat(Boolean isRachat) {
        this.isRachat = isRachat;
    }

    public Boolean getIsExtract() {
        return isExtract;
    }

    public void setIsExtract(Boolean isExtract) {
        this.isExtract = isExtract;
    }

    public Double getPrimeAmount() {
        return primeAmount;
    }

    public void setPrimeAmount(Double primeAmount) {
        this.primeAmount = primeAmount;
    }

    public RefTypeContrat getType() {
        return type;
    }

    public void setType(RefTypeContrat type) {
        this.type = type;
    }

    public RefNatureContrat getNature() {
        return nature;
    }

    public void setNature(RefNatureContrat nature) {
        this.nature = nature;
    }

    public Agency getAgence() {
        return agence;
    }

    public void setAgence(Agency agence) {
        this.agence = agence;
    }

    public VehicleUsage getUsage() {
        return usage;
    }

    public void setUsage(VehicleUsage usage) {
        this.usage = usage;
    }

    public Set<VehiculeAssure> getVehicules() {
        return vehicules;
    }

    public void setVehicules(Set<VehiculeAssure> vehicules) {
        this.vehicules = vehicules;
    }

    public RefFractionnement getFractionnement() {
        return fractionnement;
    }

    public void setFractionnement(RefFractionnement fractionnement) {
        this.fractionnement = fractionnement;
    }

    public RefPack getPack() {
        return pack;
    }

    public void setPack(RefPack pack) {
        this.pack = pack;
    }

    public Partner getClient() {
        return client;
    }

    public void setClient(Partner client) {
        this.client = client;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public PolicyReceiptStatus getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(PolicyReceiptStatus receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public AmendmentType getAmendmentType() {
        return amendmentType;
    }

    public void setAmendmentType(AmendmentType amendmentType) {
        this.amendmentType = amendmentType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
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
        final ContratAssurance other = (ContratAssurance) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ContratAssurance{" + "id=" + id + ", numeroContrat=" + numeroContrat + ", debutValidite=" + debutValidite + ", deadlineDate=" + deadlineDate + ", amendmentEffectiveDate=" + amendmentEffectiveDate + ", finValidite=" + finValidite + '}';
    }

    
}
