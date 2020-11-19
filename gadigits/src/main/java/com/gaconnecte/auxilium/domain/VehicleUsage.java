package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A VehicleUsage.
 */
@Entity
@Table(name = "ref_vehicle_usage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_vehicle_usage")
public class VehicleUsage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    @Column(name = "vat_rate_id")
    private Long vatRateId;

    @Column(name = "vat_rate")
    private BigDecimal vatRate;
    
    @Column(name = "compagnie")
    private String compagnie;
    
    
    
    public Long getCompagnieId() {
		return compagnieId;
	}

	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}

	public Long getVatRateId() {
		return vatRateId;
	}

	public void setVatRateId(Long vatRateId) {
		this.vatRateId = vatRateId;
	}

	public BigDecimal getVatRate() {
		return vatRate;
	}

	public void setVatRate(BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	public String getCompagnie() {
		return compagnie;
	}

	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public VehicleUsage code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public VehicleUsage label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isActive() {
        return active;
    }

    public VehicleUsage active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehicleUsage vehicleUsage = (VehicleUsage) o;
        if (vehicleUsage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleUsage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "VehicleUsage [id=" + id + ", code=" + code + ", label=" + label + ", active=" + active
				+ ", compagnieId=" + compagnieId + ", vatRateId=" + vatRateId + ", vatRate=" + vatRate + ", compagnie="
				+ compagnie + "]";
	}
}
