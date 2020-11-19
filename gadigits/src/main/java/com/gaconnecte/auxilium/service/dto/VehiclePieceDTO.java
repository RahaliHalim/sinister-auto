package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehiclePiece entity.
 */
public class VehiclePieceDTO implements Serializable {

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
    
    private Long typeId;
    
    private String typeLabel;

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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehiclePieceDTO vehiclePieceDTO = (VehiclePieceDTO) o;
        if(vehiclePieceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiclePieceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiclePieceDTO{" +
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
