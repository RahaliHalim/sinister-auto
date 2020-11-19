package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Attachment entity.
 */
public class ChangeValueDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nameAttribute;
	private String lastValue;
	private String newValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameAttribute() {
		return nameAttribute;
	}
	public void setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}
	public String getLastValue() {
		return lastValue;
	}
	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(this.id);
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
		final ChangeValueDTO other = (ChangeValueDTO) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "ChangeValueDTO [id=" + id + ", nameAttribute=" + nameAttribute + ", lastValue=" + lastValue
				+ ", newValue=" + newValue + "]";
	}
	
	

}
