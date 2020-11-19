package com.gaconnecte.auxilium.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaconnecte.auxilium.domain.referential.RefPack;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A VehiculeAssure.
 */
@Entity
@Table(name = "vehicule_assure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vehiculeassure")
public class VehiculeAssure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "immatriculation_vehicule", nullable = false)
    private String immatriculationVehicule;
    
    @Column(name = "puissance")
    private Integer puissance;

    @Column(name = "numero_chassis")
    private String numeroChassis;

    @Column(name = "date_p_circulation")
    private LocalDate datePCirculation;

    @Column(name = "nombre_de_place")
    private Integer nombreDePlace;

    @Column(name = "mdp")
    private String mdp;

    @Column(name = "new_value_vehicle")
    private Double newValueVehicle;

    @Column(name = "new_value_vehicle_franchise")
    private Double newValueVehicleFarnchise;

    @Column(name = "dc_capital")
    private Double dcCapital;

    @Column(name = "dc_capital_franchise")
    private Double dcCapitalFranchise;

    @Column(name = "bg_capital")
    private Double bgCapital;

    @Column(name = "bg_capital_franchise")
    private Double bgCapitalFranchise;

    @Column(name = "market_value")
    private Double marketValue;

    @Column(name = "market_value_franchise")
    private Double marketValueFranchise;
    
    @Column(name = "franchise_type_new_value")
    private String franchiseTypeNewValue;

    @Column(name = "franchise_type_market_value")
    private String franchiseTypeMarketValue;

    @Column(name = "franchise_type_dc_capital")
    private String franchiseTypeDcCapital;

    @Column(name = "franchise_type_bg_capital")
    private String franchiseTypeBgCapital;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    @JsonIgnore
    private ContratAssurance contrat;

    @ManyToOne
    @JoinColumn(name = "insured_id")
    @JsonIgnore
    private Assure insured;
    
    @ApiModelProperty(value = "Relation concernant les vehicules assures")
    @ManyToOne(optional = false)
    @JoinColumn(name = "modele_id")
    @NotNull
    private VehicleBrandModel modele;

    @ApiModelProperty(value = "Relation concernant les vehicules assures")
    @ManyToOne
    @JoinColumn(name = "energie_id")
    private VehicleEnergy energie;

    @ManyToOne
    @JoinColumn(name = "usage_id")
    private VehicleUsage usage;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private VehicleBrand marque;

    @ManyToOne
    @JoinColumn(name = "pack_id")    
    private RefPack pack;
    
    @Column(name = "assujettie_tva")
	private Boolean assujettieTVA;
    
    public VehicleBrand getMarque() {
        return marque;
    }

    public VehiculeAssure marque(VehicleBrand refMarque) {
        this.marque = refMarque;
        return this;
    }

    public void setMarque(VehicleBrand marque) {
        this.marque = marque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNombreDePlace() {
        return nombreDePlace;
    }

    public void setNombreDePlace(Integer nombreDePlace) {
        this.nombreDePlace = nombreDePlace;
    }

    public String getImmatriculationVehicule() {
        return immatriculationVehicule;
    }

    public VehiculeAssure immatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
        return this;
    }

    public void setImmatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
    }

    public Integer getPuissance() {
        return puissance;
    }

    public VehiculeAssure puissance(Integer puissance) {
        this.puissance = puissance;
        return this;
    }

    public void setPuissance(Integer puissance) {
        this.puissance = puissance;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public VehiculeAssure numeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
        return this;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public LocalDate getDatePCirculation() {
        return datePCirculation;
    }

    public VehiculeAssure datePCirculation(LocalDate datePCirculation) {
        this.datePCirculation = datePCirculation;
        return this;
    }

    public void setDatePCirculation(LocalDate datePCirculation) {
        this.datePCirculation = datePCirculation;
    }

    public ContratAssurance getContrat() {
        return contrat;
    }

    public void setContrat(ContratAssurance contrat) {
        this.contrat = contrat;
    }

    public VehicleBrandModel getModele() {
        return modele;
    }

    public VehiculeAssure modele(VehicleBrandModel refModelVoiture) {
        this.modele = refModelVoiture;
        return this;
    }

    public void setModele(VehicleBrandModel refModelVoiture) {
        this.modele = refModelVoiture;
    }

    public VehicleEnergy getEnergie() {
        return energie;
    }

    public VehiculeAssure energie(VehicleEnergy refEnergie) {
        this.energie = refEnergie;
        return this;
    }

    public void setEnergie(VehicleEnergy refEnergie) {
        this.energie = refEnergie;
    }

    public VehicleUsage getUsage() {
        return usage;
    }

    public VehiculeAssure usage(VehicleUsage refUsageContrat) {
        this.usage = refUsageContrat;
        return this;
    }

    public void setUsage(VehicleUsage refUsageContrat) {
        this.usage = refUsageContrat;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public RefPack getPack() {
        return pack;
    }

    public void setPack(RefPack pack) {
        this.pack = pack;
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

    public Double getDcCapital() {
        return dcCapital;
    }

    public void setDcCapital(Double dcCapital) {
        this.dcCapital = dcCapital;
    }

    public Double getDcCapitalFranchise() {
        return dcCapitalFranchise;
    }

    public void setDcCapitalFranchise(Double dcCapitalFranchise) {
        this.dcCapitalFranchise = dcCapitalFranchise;
    }

    public Double getBgCapital() {
        return bgCapital;
    }

    public void setBgCapital(Double bgCapital) {
        this.bgCapital = bgCapital;
    }

    public Double getBgCapitalFranchise() {
        return bgCapitalFranchise;
    }

    public void setBgCapitalFranchise(Double bgCapitalFranchise) {
        this.bgCapitalFranchise = bgCapitalFranchise;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getMarketValueFranchise() {
        return marketValueFranchise;
    }

    public void setMarketValueFranchise(Double marketValueFranchise) {
        this.marketValueFranchise = marketValueFranchise;
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

    public Assure getInsured() {
        return insured;
    }

    public void setInsured(Assure insured) {
        this.insured = insured;
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
        VehiculeAssure vehiculeAssure = (VehiculeAssure) o;
        if (vehiculeAssure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculeAssure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiculeAssure{"
                + "id=" + getId()
                + ", immatriculationVehicule='" + getImmatriculationVehicule() + "'"
                + ", puissance='" + getPuissance() + "'"
                + ", numeroChassis='" + getNumeroChassis() + "'"
                + ", datePCirculation='" + getDatePCirculation() + "'"
                + "}";
    }
}
