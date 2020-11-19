package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;


/**
 * A DTO for the PartnerModeMapping entity.
 */
public class PartnerModeMappingDTO implements Serializable {

    private Long id;

    private Long partnerId;

    private Long modeId;

    private String labelPartnerMode;

    private String partnerMode;

    private Long courtierId;

    private String courtierName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public String getLabelPartnerMode() {
        return labelPartnerMode;
    }

    public void setLabelPartnerMode(String labelPartnerMode) {
        this.labelPartnerMode = labelPartnerMode;
    }

    public String getPartnerMode() {
        return partnerMode;
    }

    public void setPartnerMode(String partnerMode) {
        this.partnerMode = partnerMode;
    }

    public Long getCourtierId() {
        return courtierId;
    }

    public void setCourtierId(Long courtierId) {
        this.courtierId = courtierId;
    }

    public String getCourtierName() {
        return courtierName;
    }

    public void setCourtierName(String courtierName) {
        this.courtierName = courtierName;
    }
}

