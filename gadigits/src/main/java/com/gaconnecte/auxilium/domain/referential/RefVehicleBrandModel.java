package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;

/**
 * vehicle brand references.
 */
@Entity
@Table(name = "ref_vehicle_brand_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_vehicle_brand_model")
public class RefVehicleBrandModel extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "ref_vehicle_brand_id")
    private RefVehicleBrand vehicleBrand;	

	/**
	 * @return the vehicleBrand
	 */
	public RefVehicleBrand getVehicleBrand() {
		return vehicleBrand;
	}


	/**
	 * @param vehicleBrand the vehicleBrand to set
	 */
	public void setVehicleBrand(RefVehicleBrand vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}


	@Override
    public String toString() {
        return "RefEnergy {" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", vehicleBrand='" + this.vehicleBrand != null ? this.vehicleBrand.getLabel() : "" + "'" +
            "}";
    }
}
