package com.gaconnecte.auxilium.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "vis_a_vis")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "vis_a_vis")
public class VisAVis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Column(name = "entity_name")
    private String entityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reparateur_id")
    @JsonBackReference(value = "reparateur")
    private Reparateur reparateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    @JsonBackReference(value = "partner")
    private Partner partner;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remorqueur_id")
    @JsonBackReference(value = "remorqueur")
    private RefRemorqueur remorqueur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loueur_id")
    @JsonBackReference(value = "loueur")
    private Loueur loueur;

    public RefRemorqueur getRemorqueur() {
		return remorqueur;
	}

	public void setRemorqueur(RefRemorqueur remorqueur) {
		this.remorqueur = remorqueur;
	}

	@Column(name = "is_gerant")
    private Boolean isGerant;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "prem_telephone")
    private String premTelephone;

    @Column(name = "deux_telephone")
    private String deuxTelephone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "prem_mail")
    private String premMail;

    @Column(name = "deux_mail")
    private String deuxMail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsGerant() {
        return isGerant;
    }

    public VisAVis isGerant(Boolean isGerant) {
        this.isGerant = isGerant;
        return this;
    }

    public void setIsGerant(Boolean isGerant) {
        this.isGerant = isGerant;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Reparateur getReparateur() {
        return reparateur;
    }

    public void setReparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPremTelephone() {
        return premTelephone;
    }

    public void setPremTelephone(String premTelephone) {
        this.premTelephone = premTelephone;
    }

    public String getDeuxTelephone() {
        return deuxTelephone;
    }

    public void setDeuxTelephone(String deuxTelephone) {
        this.deuxTelephone = deuxTelephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPremMail() {
        return premMail;
    }

    public void setPremMail(String premMail) {
        this.premMail = premMail;
    }

    public String getDeuxMail() {
        return deuxMail;
    }

    public void setDeuxMail(String deuxMail) {
        this.deuxMail = deuxMail;
    }

    public Boolean getIsGerant() {
        return isGerant;
    }

    public Loueur getLoueur() {
		return loueur;
	}

	public void setLoueur(Loueur loueur) {
		this.loueur = loueur;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisAVis contact = (VisAVis) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "visAVis [entityName=" + entityName + ", isGerant=" + isGerant + ", nom="
                + nom + ", prenom=" + prenom + ", premTelephone=" + premTelephone + ", deuxTelephone=" + deuxTelephone
                + ", fax=" + fax + ", premMail=" + premMail + ", deuxMail=" + deuxMail + "]";
    }

}
