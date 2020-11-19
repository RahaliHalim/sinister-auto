package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the VehicleUsage entity.
 */
public class VehicleUsageDTO implements Serializable {

    private Long id;

    private String code;

    private String label;

    private Boolean active;

    private Long compagnieId;

    private Long vatRateId;

    private BigDecimal vatRate;

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isActive() {
        return active;
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

        VehicleUsageDTO vehicleUsageDTO = (VehicleUsageDTO) o;
        if(vehicleUsageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleUsageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "VehicleUsageDTO [id=" + id + ", code=" + code + ", label=" + label + ", active=" + active
				+ ", compagnieId=" + compagnieId + ", vatRateId=" + vatRateId + ", vatRate=" + vatRate + ", compagnie="
				+ compagnie + "]";
	}
}
