package com.gaconnecte.auxilium.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * A features
 */
@Entity
@Table(name = "z_to_delete_adm_features")
public class ToDeleteFeatures implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "url")
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "entitie_id")
    private ToDeleteBusinessEntity entitie;

    @Column(name = "parent_id")
    private Long parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public ToDeleteBusinessEntity getEntitie() {
        return entitie;
    }

    public void setEntitie(ToDeleteBusinessEntity entitie) {
        this.entitie = entitie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

}
