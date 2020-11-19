package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DetailsMo.
 */
@Entity
@Table(name = "details_mo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "detailsmo")
public class DetailsMo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre_heures")
    private Float nombreHeures;

    @DecimalMax(value = "99999999")
    @Column(name = "th", nullable = false)
    private Float th;

    @DecimalMax(value = "99")
    @Column(name = "discount")
    private Float discount;

     @Column(name = "observation")
    private String observation;

    @DecimalMax(value = "99")
    @Column(name = "vat")
    private Float vat;

    @Column(name = "total_ht")
    private Float totalHt;

    @Column(name = "total_ttc")
    private Float totalTtc;

    @OneToMany(mappedBy = "detailsMo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FactureDetailsMo> factureMos = new HashSet<>();

    /**
     * Relation concernant le devis main d oeuvre
     */
    @ApiModelProperty(value = "Relation concernant le devis main d oeuvre")
    @ManyToOne(optional = false)
    @NotNull
    private Devis devis;

    @ManyToOne(optional = false)
    @NotNull
    private RefTypeIntervention typeIntervention;

    @ManyToOne(optional = false)
    @NotNull
    private Piece designation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNombreHeures() {
        return nombreHeures;
    }

    public DetailsMo nombreHeures(Float nombreHeures) {
        this.nombreHeures = nombreHeures;
        return this;
    }

    public void setNombreHeures(Float nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    /**
     * @param th the th to set
     */
    public void setTh(Float th) {
        this.th = th;
    }

    /**
     * @return the th
     */
    public Float getTh() {
        return th;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(Float vat) {
        this.vat = vat;
    }

    /**
     * @return the vat
     */
    public Float getVat() {
        return vat;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    /**
     * @return the discount
     */
    public Float getDiscount() {
        return discount;
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

    /**
     * @param totalHt the totalHt to set
     */
    public void setTotalHt(Float totalHt) {
        this.totalHt = totalHt;
    }

    /**
     * @return the totalHt
     */
    public Float getTotalHt() {
        return totalHt;
    }

    /**
     * @param totalTtc the totalTtc to set
     */
    public void setTotalTtc(Float totalTtc) {
        this.totalTtc = totalTtc;
    }

    /**
     * @return the totalTtc
     */
    public Float getTotalTtc() {
        return totalTtc;
    }

    public Piece getDesignation() {
        return designation;
    }

    public DetailsMo designation(Piece designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Piece designation) {
        this.designation = designation;
    }

    public Set<FactureDetailsMo> getFactureMos() {
        return factureMos;
    }

    public DetailsMo factureMos(Set<FactureDetailsMo> factureDetailsMos) {
        this.factureMos = factureDetailsMos;
        return this;
    }

    public DetailsMo addFactureMo(FactureDetailsMo factureDetailsMo) {
        this.factureMos.add(factureDetailsMo);
        factureDetailsMo.setDetailsMo(this);
        return this;
    }

    public DetailsMo removeFactureMo(FactureDetailsMo factureDetailsMo) {
        this.factureMos.remove(factureDetailsMo);
        factureDetailsMo.setDetailsMo(null);
        return this;
    }

    public void setFactureMos(Set<FactureDetailsMo> factureDetailsMos) {
        this.factureMos = factureDetailsMos;
    }

    public Devis getDevis() {
        return devis;
    }

    public DetailsMo devis(Devis devis) {
        this.devis = devis;
        return this;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public RefTypeIntervention getTypeIntervention() {
        return typeIntervention;
    }

    public DetailsMo typeIntervention(RefTypeIntervention refTypeIntervention) {
        this.typeIntervention = refTypeIntervention;
        return this;
    }

    public void setTypeIntervention(RefTypeIntervention refTypeIntervention) {
        this.typeIntervention = refTypeIntervention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetailsMo detailsMo = (DetailsMo) o;
        if (detailsMo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailsMo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailsMo{" +
            "id=" + getId() +
            ", nombreHeures='" + getNombreHeures() + "'" +
            "}";
    }
}
