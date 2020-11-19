package com.gaconnecte.auxilium.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.EtatDevis;
import java.time.ZonedDateTime;

/**
 * A Devis.
 */
@Entity
@Table(name = "devis")
/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    discriminatorType = DiscriminatorType.INTEGER,
    name = "devis_compl_id",
    columnDefinition = "TINYINT(1)")*/
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "devis")
public class Devis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMax(value = "100000000000000000")
    @Column(name = "total_ttc", nullable = false)
    private Double totalTtc;

    @DecimalMax(value = "100000000000000000")
    @Column(name = "tot_ht")
    private Double totHt;

    @Column(name = "is_complementaire")
    private Boolean isComplementaire;

    @Column(name = "is_supprime")
    private Boolean isSupprime;

   
    @Column(name = "date_generation", nullable = false)
    private ZonedDateTime dateGeneration;

    @DecimalMax(value = "99")
    @Column(name = "timbre")
    private Float timbre;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

   
    @DecimalMax(value = "99999999")
    @Column(name = "droit_timbre", nullable = true)
    private Float droitTimbre;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_devis", nullable = false)
    private EtatDevis etatDevis;
    
    //@NotNull
    @Max(value = 99999999)
    @Column(name = "version")
    private Integer version;



     /**
     * Relation concernant le Devis
     */
    @ApiModelProperty(value = "Relation concernant le Devis")
    @ManyToOne(optional = false)
    @NotNull
    private PrestationPEC prestation;

    @ManyToOne(optional = false)
    @NotNull
    private Reparateur reparateur;

    @ManyToOne
    private Expert expert;

    
    @Column(name = "date_expertise", nullable = true)
    private LocalDate dateExpertise;

    @DecimalMax(value = "100000000000000000")
    @Column(name = "valeur_neuf")
    private Double valeurNeuf;

    @DecimalMax(value = "100000000000000000")
    @Column(name = "valeur_venale")
    private Double valeurVenale;
    
    @DecimalMax(value = "100000000000000000")
    @Column(name = "klm")
    private Double klm;

    @Size(max = 100)
    @Column(name = "etat_vehicule", length = 100)
    private String etatVehicule;

    @Column(name = "is_vehicule_reparable")
    private Boolean isVehiculeReparable;

    @Column(name = "is_cia")
    private Boolean isCia;

    @Column(name = "is_constat_pre")
    private Boolean isConstatPre;

    @ManyToOne(optional = false)
    
    private QuotationStatus quotationStatus;


    public Boolean isIsCia() {
        return isCia;
    }



    public Devis isCia(Boolean isCia) {
        this.isCia = isCia;
        return this;
    }

    public void setIsCia(Boolean isCia) {
        this.isCia = isCia;
    }

    public Boolean isIsConstatPre() {
        return isConstatPre;
    }

    public Devis isConstatPre(Boolean isConstatPre) {
        this.isConstatPre = isConstatPre;
        return this;
    }

    public void setIsConstatPre(Boolean isConstatPre) {
        this.isConstatPre = isConstatPre;
    }


    public String getEtatVehicule() {
        return etatVehicule;
    }

    public Devis etatVehicule(String etatVehicule) {
        this.etatVehicule = etatVehicule;
        return this;
    }

    public void setEtatVehicule(String etatVehicule) {
        this.etatVehicule = etatVehicule;
    }
    
    public QuotationStatus getQuotationStatus() {
		return quotationStatus;
	}



	public void setQuotationStatus(QuotationStatus quotationStatus) {
		this.quotationStatus = quotationStatus;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getKlm() {
        return klm;
    }

    public Devis klm(Double klm) {
        this.klm = klm;
        return this;
    }

    public void setKlm(Double klm) {
        this.klm = klm;
    }

    public Double getTotalTtc() {
        return totalTtc;
    }

    public Devis totalTtc(Double totalTtc) {
        this.totalTtc = totalTtc;
        return this;
    }

    public void setTotalTtc(Double totalTtc) {
        this.totalTtc = totalTtc;
    }

    public Double getTotHt() {
        return totHt;
    }

    public Devis totHt(Double totHt) {
        this.totHt = totHt;
        return this;
    }

    public void setTotHt(Double totHt) {
        this.totHt = totHt;
    }

    public Boolean isIsComplementaire() {
        return isComplementaire;
    }

    public Devis isComplementaire(Boolean isComplementaire) {
        this.isComplementaire = isComplementaire;
        return this;
    }

    public void setIsComplementaire(Boolean isComplementaire) {
        this.isComplementaire = isComplementaire;
    }

    public Boolean isIsVehiculeReparable() {
        return isVehiculeReparable;
    }

    public Devis isVehiculeReparable(Boolean isVehiculeReparable) {
        this.isVehiculeReparable = isVehiculeReparable;
        return this;
    }

    public void setIsVehiculeReparable(Boolean isVehiculeReparable) {
        this.isVehiculeReparable = isVehiculeReparable;
    }

    public Boolean isIsSupprime() {
        return isSupprime;
    }

    public Devis isSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
        return this;
    }

    public void setIsSupprime(Boolean isSupprime) {
        this.isSupprime = isSupprime;
    }

    public ZonedDateTime getDateGeneration() {
        return dateGeneration;
    }

    public Devis dateGeneration(ZonedDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
        return this;
    }

    public void setDateGeneration(ZonedDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public LocalDate getDateExpertise() {
        return dateExpertise;
    }

    public Devis dateExpertise(LocalDate dateExpertise) {
        this.dateExpertise = dateExpertise;
        return this;
    }

    public void setDateExpertise(LocalDate dateExpertise) {
        this.dateExpertise = dateExpertise;
    }


    public Float getTimbre() {
        return timbre;
    }

    public Devis timbre(Float timbre) {
        this.timbre = timbre;
        return this;
    }

    public void setTimbre(Float timbre) {
        this.timbre = timbre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Devis commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Float getDroitTimbre() {
        return droitTimbre;
    }

    public Devis droitTimbre(Float droitTimbre) {
        this.droitTimbre = droitTimbre;
        return this;
    }

    public void setDroitTimbre(Float droitTimbre) {
        this.droitTimbre = droitTimbre;
    }

    public PrestationPEC getPrestation() {
        return prestation;
    }

    public Devis prestation(PrestationPEC prestationPEC) {
        this.prestation = prestationPEC;
        return this;
    }

    public void setPrestation(PrestationPEC prestationPEC) {
        this.prestation = prestationPEC;
    }

    public Reparateur getReparateur() {
        return reparateur;
    }

    public Devis reparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
        return this;
    }
    public void setReparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
    }

    public Expert getExpert() {
        return expert;
    }

    public Devis expert(Expert expert) {
        this.expert = expert;
        return this;
    }
    public void setExpert(Expert expert) {
        this.expert = expert;
    }

     public EtatDevis getEtatDevis() {
        return etatDevis;
    }

    public Devis etatDevis(EtatDevis etatDevis) {
        this.etatDevis = etatDevis;
        return this;
    }

    public void setEtatDevis(EtatDevis etatDevis) {
        this.etatDevis = etatDevis;
    }

    public Integer getVersion() {
        return version;
    }

    public Devis version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    public Double getValeurNeuf() {
        return valeurNeuf;
    }

    public Devis valeurNeuf(Double valeurNeuf) {
        this.valeurNeuf = valeurNeuf;
        return this;
    }

    public void setValeurNeuf(Double valeurNeuf) {
        this.valeurNeuf = valeurNeuf;
    }

    public Double getValeurVenale() {
        return valeurVenale;
    }

    public Devis valeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
        return this;
    }

    public void setValeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
	}



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Devis devis = (Devis) o;
        if (devis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), devis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Devis{" +
            "id=" + getId() +
            ", totalTtc='" + getTotalTtc() + "'" +
            ", totHt='" + getTotHt() + "'" +
            ", isComplementaire='" + isIsComplementaire() + "'" +
            ", isSupprime='" + isIsSupprime() + "'" +
            ", dateGeneration='" + getDateGeneration() + "'" +
            ", timbre='" + getTimbre() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", droitTimbre='" + getDroitTimbre() + "'" +
            "}";
    }
}
