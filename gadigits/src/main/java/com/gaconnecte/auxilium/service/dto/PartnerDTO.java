package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Partner entity.
 */
public class PartnerDTO implements Serializable {

    private Long id;

    private String companyName;

    private String phone;

    private String address;

    private String tradeRegister;

    private String taxIdentificationNumber;

    private Boolean foreignCompany;

    private Integer genre;

    private Boolean active;

    private Long attachmentId;

    private String attachmentLabel;
    
    private String attachmentOriginalName;
    
    private String attachmentName;

    private String companyLogoAttachment64;
    
    private String companyLogoAttachmentName;
    
    private String concessionnaireLogoAttachment64;
    
    private String concessionnaireLogoAttachmentName;
    
    private List<VisAVisDTO> visAViss;
    
    private LocalDate dateCreation;
    
    
    
    public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentOriginalName() {
		return attachmentOriginalName;
	}

	public void setAttachmentOriginalName(String attachmentOriginalName) {
		this.attachmentOriginalName = attachmentOriginalName;
	}

	private Boolean conventionne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Boolean isForeignCompany() {
        return foreignCompany;
    }

    public void setForeignCompany(Boolean foreignCompany) {
        this.foreignCompany = foreignCompany;
    }

    public Integer getGenre() {
        return genre;
    }

    public void setGenre(Integer genre) {
        this.genre = genre;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentLabel() {
        return attachmentLabel;
    }

    public void setAttachmentLabel(String attachmentLabel) {
        this.attachmentLabel = attachmentLabel;
    }

    public List<VisAVisDTO> getVisAViss() {
		return visAViss;
	}

	public void setVisAViss(List<VisAVisDTO> visAViss) {
		this.visAViss = visAViss;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getCompanyLogoAttachment64() {
		return companyLogoAttachment64;
	}

	public void setCompanyLogoAttachment64(String companyLogoAttachment64) {
		this.companyLogoAttachment64 = companyLogoAttachment64;
	}

	public String getCompanyLogoAttachmentName() {
		return companyLogoAttachmentName;
	}

	public void setCompanyLogoAttachmentName(String companyLogoAttachmentName) {
		this.companyLogoAttachmentName = companyLogoAttachmentName;
	}

	public String getConcessionnaireLogoAttachment64() {
		return concessionnaireLogoAttachment64;
	}

	public void setConcessionnaireLogoAttachment64(String concessionnaireLogoAttachment64) {
		this.concessionnaireLogoAttachment64 = concessionnaireLogoAttachment64;
	}

	public String getConcessionnaireLogoAttachmentName() {
		return concessionnaireLogoAttachmentName;
	}

	public void setConcessionnaireLogoAttachmentName(String concessionnaireLogoAttachmentName) {
		this.concessionnaireLogoAttachmentName = concessionnaireLogoAttachmentName;
	}

	public Boolean getForeignCompany() {
		return foreignCompany;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getConventionne() {
		return conventionne;
	}

	public void setConventionne(Boolean conventionne) {
		this.conventionne = conventionne;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartnerDTO partnerDTO = (PartnerDTO) o;
        if(partnerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "PartnerDTO [id=" + id + ", companyName=" + companyName + ", phone=" + phone + ", address=" + address
				+ ", tradeRegister=" + tradeRegister + ", taxIdentificationNumber=" + taxIdentificationNumber
				+ ", foreignCompany=" + foreignCompany + ", genre=" + genre + ", active=" + active + ", attachmentId="
				+ attachmentId + ", attachmentLabel=" + attachmentLabel + ", attachmentOriginalName="
				+ attachmentOriginalName + ", attachmentName=" + attachmentName + ", companyLogoAttachment64="
				+ companyLogoAttachment64 + ", companyLogoAttachmentName=" + companyLogoAttachmentName
				+ ", concessionnaireLogoAttachment64=" + concessionnaireLogoAttachment64
				+ ", concessionnaireLogoAttachmentName=" + concessionnaireLogoAttachmentName + ", visAViss=" + visAViss
				+ ", dateCreation=" + dateCreation + ", conventionne=" + conventionne + "]";
	}
}
