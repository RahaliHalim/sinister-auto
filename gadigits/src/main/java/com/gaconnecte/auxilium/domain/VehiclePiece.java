package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VehiclePiece.
 */
@Entity
@Table(name = "ref_vehicle_piece")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vehiclepiece")
public class VehiclePiece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "type_id")
    private VehiclePieceType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public VehiclePiece code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public VehiclePiece label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public VehiclePiece source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean isActive() {
        return active;
    }

    public VehiclePiece active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isVetuste() {
        return vetuste;
    }

    public VehiclePiece vetuste(Boolean vetuste) {
        this.vetuste = vetuste;
        return this;
    }

    public void setVetuste(Boolean vetuste) {
        this.vetuste = vetuste;
    }

    public Integer getGraphicAreaId() {
        return graphicAreaId;
    }

    public VehiclePiece graphicAreaId(Integer graphicAreaId) {
        this.graphicAreaId = graphicAreaId;
        return this;
    }

    public void setGraphicAreaId(Integer graphicAreaId) {
        this.graphicAreaId = graphicAreaId;
    }

    public Integer getFunctionalGroupId() {
        return functionalGroupId;
    }

    public VehiclePiece functionalGroupId(Integer functionalGroupId) {
        this.functionalGroupId = functionalGroupId;
        return this;
    }

    public void setFunctionalGroupId(Integer functionalGroupId) {
        this.functionalGroupId = functionalGroupId;
    }

    public Integer getNatureId() {
        return natureId;
    }

    public VehiclePiece natureId(Integer natureId) {
        this.natureId = natureId;
        return this;
    }

    public void setNatureId(Integer natureId) {
        this.natureId = natureId;
    }

    public VehiclePieceType getType() {
        return type;
    }

    public void setType(VehiclePieceType type) {
        this.type = type;
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
        VehiclePiece vehiclePiece = (VehiclePiece) o;
        if (vehiclePiece.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiclePiece.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
