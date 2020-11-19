package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A truck.
 */
@Entity
@Table(name = "ref_tug_truck")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ref_tug_truck")
public class RefTugTruck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column(name= "immatriculation")
    private String immatriculation;
    
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(name = "ref_tug_id")
    private RefRemorqueur refTug;
    
    @Column(name = "has_habillage")
    private Boolean hasHabillage;
        
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ref_tug_truck_service_type", joinColumns = {
        @JoinColumn(name = "ref_tug_truck_id")}, inverseJoinColumns = {
        @JoinColumn(name = "service_type_id")})
    private Set<RefTypeService> serviceTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public RefRemorqueur getRefTug() {
        return refTug;
    }

    public void setRefTug(RefRemorqueur refTug) {
        this.refTug = refTug;
    }

    public Boolean getHasHabillage() {
        return hasHabillage;
    }

    public void setHasHabillage(Boolean hasHabillage) {
        this.hasHabillage = hasHabillage;
    }

    public Set<RefTypeService> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<RefTypeService> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final RefTugTruck other = (RefTugTruck) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RefTugTruck{" + "id=" + id + ", immatriculation=" + immatriculation + ", refTug=" + refTug + ", hasHabillage=" + hasHabillage + ", serviceTypes=" + serviceTypes + '}';
    }
    
}
