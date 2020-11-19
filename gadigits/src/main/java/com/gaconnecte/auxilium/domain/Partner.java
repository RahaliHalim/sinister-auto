package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaconnecte.auxilium.domain.app.Attachment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A Partner.
 */
@Entity
@Table(name = "ref_partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "trade_register")
    private String tradeRegister;

    @Column(name = "tax_identification_number")
    private String taxIdentificationNumber;

    @Column(name = "foreign_company")
    private Boolean foreignCompany;

    @Column(name = "genre")
    private Integer genre;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;
  
    //@DiffIgnore
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisAVis> visAViss = new ArrayList<>();
    
    @Column(name = "is_conventionne")
    private Boolean conventionne;

    public Partner() {
    }

    public Partner(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Partner companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public Partner phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Partner address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public Partner tradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
        return this;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public Partner taxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        return this;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Boolean isForeignCompany() {
        return foreignCompany;
    }

  
    public Partner foreignCompany(Boolean foreignCompany) {
        this.foreignCompany = foreignCompany;
        return this;
    }

    public void setForeignCompany(Boolean foreignCompany) {
        this.foreignCompany = foreignCompany;
    }

    public Integer getGenre() {
        return genre;
    }

    public Partner genre(Integer genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Boolean isActive() {
        return active;
    }

    public Partner active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public Partner attachment(Attachment attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public List<VisAVis> getVisAViss() {
        return visAViss;
    }

    public void setVisAViss(List<VisAVis> visAViss) {
        this.visAViss = visAViss;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Boolean getConventionne() {
		return conventionne;
	}

	public void setConventionne(Boolean conventionne) {
		this.conventionne = conventionne;
	}

	public Boolean getForeignCompany() {
		return foreignCompany;
	}

	public Boolean getActive() {
		return active;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Partner partner = (Partner) o;
        if (partner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Partner{"
                + "id=" + getId()
                + ", companyName='" + getCompanyName() + "'"
                + ", phone='" + getPhone() + "'"
                + ", address='" + getAddress() + "'"
                + ", tradeRegister='" + getTradeRegister() + "'"
                + ", taxIdentificationNumber='" + getTaxIdentificationNumber() + "'"
                + ", foreignCompany='" + isForeignCompany() + "'"
                + ", genre='" + getGenre() + "'"
                + ", active='" + isActive() + "'"
                + "}";
    }
}
