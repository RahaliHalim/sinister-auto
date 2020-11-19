package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.javers.core.metamodel.annotation.DiffIgnore;

/**
 * A DTO for the Region entity.
 */
public class OrientationDTO implements Serializable {
	@DiffIgnore
	private Long id;
	@DiffIgnore
	private Long reparateurId;
	private Double remiseMarque;
	@DiffIgnore
	private Long refAgeVehiculeId;
	private String refAgeVehiculeValeur;

	private String refMarquesString;
	@DiffIgnore
	private Set<VehicleBrandDTO> refMarques = new HashSet<>();

	public String getRefMarquesString() {
		return refMarquesString;
	}

	public void setRefMarquesString(String refMarquesString) {
		this.refMarquesString = refMarquesString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	public Double getRemiseMarque() {
		return remiseMarque;
	}

	public void setRemiseMarque(Double remiseMarque) {
		this.remiseMarque = remiseMarque;
	}

	public Set<VehicleBrandDTO> getRefMarques() {
		return refMarques;
	}

	public void setRefMarques(Set<VehicleBrandDTO> refMarques) {
		this.refMarques = refMarques;
	}

	public Long getRefAgeVehiculeId() {
		return refAgeVehiculeId;
	}

	public void setRefAgeVehiculeId(Long refAgeVehiculeId) {
		this.refAgeVehiculeId = refAgeVehiculeId;
	}

	public String getRefAgeVehiculeValeur() {
		return refAgeVehiculeValeur;
	}

	public void setRefAgeVehiculeValeur(String refAgeVehiculeValeur) {
		this.refAgeVehiculeValeur = refAgeVehiculeValeur;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		OrientationDTO regionDTO = (OrientationDTO) o;
		if (regionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), regionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "OrientationDTO [id=" + id + ", reparateurId=" + reparateurId + ", remiseMarque=" + remiseMarque
				+ ", refAgeVehiculeId=" + refAgeVehiculeId + ", refAgeVehiculeValeur=" + refAgeVehiculeValeur
				+ ", refMarques=" + refMarques + "]";
	}

}
