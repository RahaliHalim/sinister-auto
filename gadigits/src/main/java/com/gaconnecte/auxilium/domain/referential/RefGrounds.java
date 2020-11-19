package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.gaconnecte.auxilium.domain.AbstractElementaryEntity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Grounds references.
 */
@Entity
@Table(name = "ref_grounds")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_grounds")
public class RefGrounds extends AbstractElementaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RefStatusSinister status;
    
    @Column(name = "active")
    private String active;

    public RefGrounds() {
    }

    public RefGrounds(Long id) {
        this.setId(id);
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RefStatusSinister getStatus() {
        return status;
    }

    public void setStatus(RefStatusSinister status) {
        this.status = status;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "RefGrounds{" + "description=" + description + ", status=" + status + ", active=" + active + '}';
    }
    
}
