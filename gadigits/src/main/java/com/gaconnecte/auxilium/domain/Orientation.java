package com.gaconnecte.auxilium.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A RefModeGestion.
 */
@Entity
@Table(name = "reparateur_orientation")
public class Orientation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")

    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reparateur_id", nullable = false, updatable = true, insertable = true)
    @JsonBackReference

    private Reparateur reparateur;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reparateur_orientation_marque", joinColumns = {
        @JoinColumn(name = "orientation_id")}, inverseJoinColumns = {
        @JoinColumn(name = "marque_id")})
    private Set<VehicleBrand> refMarques = new HashSet<>();

    @Column(name = "remise_marque")
    private Double remiseMarque;
    @ManyToOne
    @JoinColumn(name = "ref_age_vehicule_id")
    private RefAgeVehicule refAgeVehicule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reparateur getReparateur() {
        return reparateur;
    }

    public void setReparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
    }

    public RefAgeVehicule getRefAgeVehicule() {
        return refAgeVehicule;
    }

    public Set<VehicleBrand> getRefMarques() {
        return refMarques;
    }

    public void setRefMarques(Set<VehicleBrand> refMarques) {
        this.refMarques = refMarques;
    }

    public void setRefAgeVehicule(RefAgeVehicule refAgeVehicule) {
        this.refAgeVehicule = refAgeVehicule;
    }

    public Double getRemiseMarque() {
        return remiseMarque;
    }

    public void setRemiseMarque(Double remiseMarque) {
        this.remiseMarque = remiseMarque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orientation orientation = (Orientation) o;
        if (orientation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orientation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
