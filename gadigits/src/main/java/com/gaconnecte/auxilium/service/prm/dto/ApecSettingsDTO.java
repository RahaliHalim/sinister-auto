package com.gaconnecte.auxilium.service.prm.dto;


import com.gaconnecte.auxilium.domain.enumeration.Operator;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ApecSettings entity.
 */
public class ApecSettingsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    private Long packId;
    private String packLabel;
    private Long mgntModeId;
    private String mgntModeLabel;
    private Operator operator;
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

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public String getPackLabel() {
        return packLabel;
    }

    public void setPackLabel(String packLabel) {
        this.packLabel = packLabel;
    }

    public Long getMgntModeId() {
        return mgntModeId;
    }

    public void setMgntModeId(Long mgntModeId) {
        this.mgntModeId = mgntModeId;
    }

    public String getMgntModeLabel() {
        return mgntModeLabel;
    }

    public void setMgntModeLabel(String mgntModeLabel) {
        this.mgntModeLabel = mgntModeLabel;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Double getCeiling() {
        return ceiling;
    }

    public void setCeiling(Double ceiling) {
        this.ceiling = ceiling;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final ApecSettingsDTO other = (ApecSettingsDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ApecSettingsDTO{" + "id=" + id + ", label=" + label + ", packId=" + packId + ", packLabel=" + packLabel + ", mgntModeId=" + mgntModeId + ", mgntModeLabel=" + mgntModeLabel + ", operator=" + operator + ", ceiling=" + ceiling + '}';
    }
}
