package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;

/**
 * vehicle brand references.
 */
@Entity
@Table(name = "ref_agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_agency")
public class RefAgency extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "code", length = 60)
    private String code;

    @Column(name = "active")
    private Boolean active;
    
    
    /**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

    /**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active != null ? this.active.booleanValue() : false;
	}
	

	@Override
    public String toString() {
        return "RefEnergy {" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
