package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Operation entity.
 */
public class ActionDTO implements Serializable {

    private Long id;



    private String label;

   

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDTO operationDTO = (ActionDTO) o;
        if(operationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ActionDTO [id=" + id + ", label=" + label + "]";
	}


}
