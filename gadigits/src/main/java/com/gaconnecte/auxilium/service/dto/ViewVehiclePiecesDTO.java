package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the ViewSinister entity.
 */
public class ViewVehiclePiecesDTO implements Serializable {

    private Long id;

    private String code;
    
    private String reference;

    private String label;

    private String source;

    private Boolean active;

    private Boolean vetuste;

    private Integer graphicAreaId;

    private Integer functionalGroupId;

    private Integer natureId;

    private Long type;

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

    public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isVetuste() {
        return vetuste;
    }

    public void setVetuste(Boolean vetuste) {
        this.vetuste = vetuste;
    }

    public Integer getGraphicAreaId() {
        return graphicAreaId;
    }

    public void setGraphicAreaId(Integer graphicAreaId) {
        this.graphicAreaId = graphicAreaId;
    }

    public Integer getFunctionalGroupId() {
        return functionalGroupId;
    }

    public void setFunctionalGroupId(Integer functionalGroupId) {
        this.functionalGroupId = functionalGroupId;
    }

    public Integer getNatureId() {
        return natureId;
    }

    public void setNatureId(Integer natureId) {
        this.natureId = natureId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VehiclePiece{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", source='" + getSource() + "'" +
            ", active='" + isActive() + "'" +
            ", vetuste='" + isVetuste() + "'" +
            ", graphicAreaId='" + getGraphicAreaId() + "'" +
            ", functionalGroupId='" + getFunctionalGroupId() + "'" +
            ", natureId='" + getNatureId() + "'" +
            "}";
    }

}
