package com.gaconnecte.auxilium.service.prm.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SinisterTypeSetting entity.
 */
public class SinisterTypeSettingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    
    private Long sinisterTypeId;
    private String sinisterTypeLabel;

    private Double ceiling;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getSinisterTypeId() {
        return sinisterTypeId;
    }

    public void setSinisterTypeId(Long sinisterTypeId) {
        this.sinisterTypeId = sinisterTypeId;
    }

    public String getSinisterTypeLabel() {
        return sinisterTypeLabel;
    }

    public void setSinisterTypeLabel(String sinisterTypeLabel) {
        this.sinisterTypeLabel = sinisterTypeLabel;
    }

    public Double getCeiling() {
        return ceiling;
    }

    public void setCeiling(Double ceiling) {
        this.ceiling = ceiling;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final SinisterTypeSettingDTO other = (SinisterTypeSettingDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SinisterTypeSettingDTO{" + "id=" + id + ", label=" + label + ", sinisterTypeId=" + sinisterTypeId + ", sinisterTypeLabel=" + sinisterTypeLabel + ", ceiling=" + ceiling + '}';
    }

   
}
