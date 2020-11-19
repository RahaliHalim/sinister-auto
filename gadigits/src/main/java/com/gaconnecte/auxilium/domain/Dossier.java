package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.NatureIncident;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dossier")
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "is_delete")
    private Boolean deleted;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private ZonedDateTime dateCreation;

    @Column(name = "date_cloture")
    private ZonedDateTime dateCloture;

    @Column(name = "date_derniere_maj")
    private ZonedDateTime dateDerniereMaj;

    @Column(name = "telephone")
    private String telephone;

    @Max(value = 99999999)
    @Column(name = "nbr_passagers")
    private Integer nbrPassagers;

    @NotNull
    @Column(name = "date_accident", nullable = false)
    private LocalDate dateAccident;

    @Size(max = 100)
    @Column(name = "prenom_conducteur", length = 100)
    private String prenomConducteur;

    @Size(max = 100)
    @Column(name = "nom_conducteur", length = 100)
    private String nomConducteur;

    @Max(value = 99999999)
    @Column(name = "permis_conduc")
    private Integer permisConduc;

    @ManyToOne
    private Tiers tiers;
    
    @ManyToOne(optional = false)
    @NotNull
    private VehiculeAssure vehicule;
    
    @ManyToOne(optional = true)
	private User user;
    
    @ManyToOne(optional = true)
    private Partner compagnie;

     @Enumerated(EnumType.STRING)
    @Column(name = "nature_incident")
    private NatureIncident natureIncident;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatDossierRmq etat;

    @OneToMany(mappedBy = "dossier")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journal> journals = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Dossier reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public boolean isDeleted() {
        return this.deleted != null ? this.deleted.booleanValue() : false;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Dossier deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public Dossier dateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateCloture() {
        return dateCloture;
    }

    public Dossier dateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
        return this;
    }

    public void setDateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public ZonedDateTime getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public Dossier dateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
        return this;
    }

    public void setDateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public String getTelephone() {
        return telephone;
    }

    public Dossier telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getNbrPassagers() {
        return nbrPassagers;
    }

    public Dossier nbrPassagers(Integer nbrPassagers) {
        this.nbrPassagers = nbrPassagers;
        return this;
    }

    public void setNbrPassagers(Integer nbrPassagers) {
        this.nbrPassagers = nbrPassagers;
    }

    public LocalDate getDateAccident() {
        return dateAccident;
    }

    public Dossier dateAccident(LocalDate dateAccident) {
        this.dateAccident = dateAccident;  
        return this;
        
    }

    public void setDateAccident(LocalDate dateAccident) {
        this.dateAccident = dateAccident;
    }

     public Set<Journal> getJournals() {
        return journals;
    }

    public Dossier journals(Set<Journal> journals) {
        this.journals = journals;
        return this;
    }

    public Dossier addJournal(Journal journal) {
        this.journals.add(journal);
        journal.setDossier(this);
        return this;
    }

    public Dossier removeJournal(Journal journal) {
        this.journals.remove(journal);
        journal.setDossier(null);
        return this;
    }

    public void setJournals(Set<Journal> journals) {
        this.journals = journals;
    }

    public String getPrenomConducteur() {
        return prenomConducteur;
    }

    public Dossier prenomConducteur(String prenomConducteur) {
        this.prenomConducteur = prenomConducteur;
        return this;
    }

    public void setPrenomConducteur(String prenomConducteur) {
        this.prenomConducteur = prenomConducteur;
    }

    public String getNomConducteur() {
        return nomConducteur;
    }

    public Dossier nomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
        return this;
    }

    public void setNomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
    }

    public Integer getPermisConduc() {
        return permisConduc;
    }

    public Dossier permisConduc(Integer permisConduc) {
        this.permisConduc = permisConduc;
        return this;
    }

    public void setPermisConduc(Integer permisConduc) {
        this.permisConduc = permisConduc;
    }

    public VehiculeAssure getVehicule() {
        return vehicule;
    }

    public Dossier vehicule(VehiculeAssure vehiculeAssure) {
        this.vehicule = vehiculeAssure;
        return this;
    }

    public void setVehicule(VehiculeAssure vehiculeAssure) {
        this.vehicule = vehiculeAssure;
    }

     public NatureIncident getNatureIncident() {
        return natureIncident;
    }

    public Dossier natureIncident(NatureIncident natureIncident) {
        this.natureIncident = natureIncident;
        return this;
    }

    public void setNatureIncident(NatureIncident natureIncident) {
        this.natureIncident = natureIncident;
    }

      public EtatDossierRmq getEtat() {
        return etat;
    }

    public Dossier etat(EtatDossierRmq etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatDossierRmq etat) {
        this.etat = etat;
    }

    public Partner getCompagnie() {
		return compagnie;
	}

	public void setCompagnie(Partner compagnie) {
		this.compagnie = compagnie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dossier dossier = (Dossier) o;
        if (dossier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dossier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", isDelete='" + isDeleted() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateDerniereMaj='" + getDateDerniereMaj() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", nbrPassagers='" + getNbrPassagers() + "'" +
            ", dateAccident='" + getDateAccident() + "'" +
            ", prenomConducteur='" + getPrenomConducteur() + "'" +
            ", nomConducteur='" + getNomConducteur() + "'" +
            ", permisConduc='" + getPermisConduc() + "'" +
            "}";
    }
}
