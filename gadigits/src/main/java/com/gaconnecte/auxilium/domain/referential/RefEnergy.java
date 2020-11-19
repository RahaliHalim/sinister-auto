package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;

/**
 * cities references.
 */
@Entity
@Table(name = "ref_energy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_energy")
public class RefEnergy extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "RefEnergy {" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
