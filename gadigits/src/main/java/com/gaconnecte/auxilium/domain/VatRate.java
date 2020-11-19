package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Date;

/**
 * A VatRate.
 */
@Entity
@Table(name = "ref_vat_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refvatrate")
public class VatRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vat_rate")
    private BigDecimal vatRate;

    @Column(name = "active")
    private Boolean active;  

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "referenced_id")
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

    public VatRate vatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
        return this;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public Boolean isActive() {
        return active;
    }

    public VatRate active(Boolean active) {
        this.active = active;
        return this;
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
        VatRate vatRate = (VatRate) o;
        if (vatRate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vatRate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VatRate{" +
            "id=" + getId() +
            ", vatRate='" + getVatRate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
