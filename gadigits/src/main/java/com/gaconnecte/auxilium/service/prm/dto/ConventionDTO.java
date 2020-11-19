package com.gaconnecte.auxilium.service.prm.dto;


import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Convention entity.
 */
public class ConventionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    
    private Long clientId;
    private String clientLabel;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<ConventionAmendmentDTO> conventionAmendments;
    private List<RefPackDTO> packs;
    
    private AttachmentDTO attachment;
    
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

    /**
     * @return Long return the clientId
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * @return String return the clientLabel
     */
    public String getClientLabel() {
        return clientLabel;
    }

    /**
     * @param clientLabel the clientLabel to set
     */
    public void setClientLabel(String clientLabel) {
        this.clientLabel = clientLabel;
    }

    /**
     * @return LocalDate return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return LocalDate return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    public List<RefPackDTO> getPacks() {
        return packs;
    }

    public void setPacks(List<RefPackDTO> packs) {
        this.packs = packs;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }

    public List<ConventionAmendmentDTO> getConventionAmendments() {
		return conventionAmendments;
	}

	public void setConventionAmendments(List<ConventionAmendmentDTO> conventionAmendments) {
		this.conventionAmendments = conventionAmendments;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConventionDTO other = (ConventionDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "ConventionDTO [id=" + id + ", label=" + label + ", clientId=" + clientId + ", clientLabel="
				+ clientLabel + ", startDate=" + startDate + ", endDate=" + endDate + ", packs=" + packs + "]";
	}

    
}
