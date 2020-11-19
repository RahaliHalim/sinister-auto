package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Date;

/**
 * A DTO for the VatRate entity.
 */
public class VatRateDTO implements Serializable {

    private Long id;

    private BigDecimal vatRate;

    private Boolean active;

    private LocalDate startDate;

    private LocalDate effectiveDate;

    private Long referencedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getReferencedId() {
        return referencedId;
    }

    public void setReferencedId(Long referencedId) {
        this.referencedId = referencedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VatRateDTO vatRateDTO = (VatRateDTO) o;
        if(vatRateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vatRateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VatRateDTO{" +
            "id=" + getId() +
            ", vatRate='" + getVatRate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
