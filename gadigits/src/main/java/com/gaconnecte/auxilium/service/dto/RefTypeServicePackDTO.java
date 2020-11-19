package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RefTypeService entity.
 */
public class RefTypeServicePackDTO implements Serializable {

    private Long id;

    private Long packsId;

    private Long serviceId;
    
    private String packNom;
    private   String serviceNom ;
   
	
	public String getPackNom() {
		return packNom;
	}
	public void setPackNom(String packNom) {
		this.packNom = packNom;
	}
	public String getServiceNom() {
		return serviceNom;
	}
	public void setServiceNom(String serviceNom) {
		this.serviceNom = serviceNom;
	}
	public void setPackId(Long packsId) {
		this.packsId = packsId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public long getPackId() {
		return packsId;
	}
	public void setPackId(long packId) {
		this.packsId = packId;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefTypeServicePackDTO refTypeServicePackDTO = (RefTypeServicePackDTO) o;
        if(refTypeServicePackDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refTypeServicePackDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefTypeServicePackDTO{" +
            "id=" + getId() +
          
            "}";
    }
}
