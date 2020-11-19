package com.gaconnecte.auxilium.service.referential.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefHolidays entity.
 */
public class RefStepPecDTO implements Serializable {

    private Long id;

    private String label;

    private String code;
    private Long num;

    public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefStepPecDTO refHolidaysDTO = (RefStepPecDTO) o;
        if(refHolidaysDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refHolidaysDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "RefStepPecDTO [id=" + id + ", label=" + label + ", code=" + code + ", num=" + num + "]";
	}
}
