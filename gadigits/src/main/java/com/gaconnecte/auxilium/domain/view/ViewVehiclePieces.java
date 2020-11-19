package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "view_vehicle_piece")
public class ViewVehiclePieces implements Serializable{
private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "code")
    private String code;
    
    @Column(name = "reference")
    private String reference;

    @Column(name = "label")
    private String label;

    @Column(name = "source")
    private String source;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "vetuste")
    private Boolean vetuste;

    @Column(name = "graphic_area_id")
    private Integer graphicAreaId;

    @Column(name = "functional_group_id")
    private Integer functionalGroupId;

    @Column(name = "nature_id")
    private Integer natureId;

    @Column(name = "type_id")
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
