package com.gaconnecte.auxilium.domain.prm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Sinister type setting.
 */
@Entity
@Table(name = "prm_sinister_type_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prm_sinister_type_setting")
public class SinisterTypeSetting extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "sinister_type_id")
    private RefModeGestion sinisterType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "pack_id")
    @JsonBackReference
    private RefPack pack;

    @Column(name = "ceiling")
    private Double ceiling;

    public RefModeGestion getSinisterType() {
        return sinisterType;
    }

    public void setSinisterType(RefModeGestion sinisterType) {
        this.sinisterType = sinisterType;
    }

    public RefPack getPack() {
        return pack;
    }

    public void setPack(RefPack pack) {
        this.pack = pack;
    }

    public Double getCeiling() {
        return ceiling;
    }

    public void setCeiling(Double ceiling) {
        this.ceiling = ceiling;
    }

    
}
