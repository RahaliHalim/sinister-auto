package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.referential.RefPack;

/**
 * A RefTypeService.
 */
@Entity
@Table(name = "ref_type_service_pack")

@Document(indexName = "ref_type_service_pack")
public class RefTypeServicePack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @ManyToOne
    private RefTypeService service;
    @NotNull
    @ManyToOne
    private RefPack packs;
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefTypeService getService() {
		return service;
	}

	public void setService(RefTypeService service) {
		this.service = service;
	}
	
	public RefTypeServicePack Service(RefTypeService service) {
	        this.service = service;
	        return this;
	    }
	
	public RefPack getPack() {
		return packs;
	}

	public void setPack(RefPack packs) {
		this.packs = packs;
	}
	
  
    public RefTypeServicePack pack(RefPack packs) {
        this.packs = packs;
        return this;
    }
    
    
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefTypeServicePack refTypeServicePack = (RefTypeServicePack) o;
        if (refTypeServicePack.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refTypeServicePack.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefTypeServicePack{" +
            "id=" + getId() +
           
            "}";
    }
}
