package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VehicleBrandModel.
 */
@Entity
@Table(name = "ref_vehicle_brand_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_vehicle_brand_model")
public class VehicleBrandModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private VehicleBrand brand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public VehicleBrandModel label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isActive() {
        return active;
    }

    public VehicleBrandModel active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public VehicleBrand getBrand() {
        return brand;
    }

    public VehicleBrandModel brand(VehicleBrand vehicleBrand) {
        this.brand = vehicleBrand;
        return this;
    }

    public void setBrand(VehicleBrand vehicleBrand) {
        this.brand = vehicleBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehicleBrandModel vehicleBrandModel = (VehicleBrandModel) o;
        if (vehicleBrandModel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleBrandModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleBrandModel{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
