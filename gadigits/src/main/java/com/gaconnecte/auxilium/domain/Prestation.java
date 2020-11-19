package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.TypePrestation;

/**
 * A Prestation.
 */
@Entity
@Table(name = "prestation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prestation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private ZonedDateTime dateCreation;

   
    @Column(name = "date_cloture", nullable = false)
    private ZonedDateTime dateCloture;

    @Column(name = "date_derniere_maj", nullable = false)
    private ZonedDateTime dateDerniereMaj;

    @Column(name = "is_ferier")
    private Boolean isFerier;

    @Column(name = "is_nuit")
    private Boolean isNuit;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_prestation", nullable = false)
    private TypePrestation typePrestation;

    @ManyToOne(optional = false)
    @NotNull
    private Dossier dossier;
    
    @ManyToOne(optional = true)
    private User user;

    @OneToMany(mappedBy = "prestation")
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

    public Prestation reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean isIsDelete() {
        return isDelete;
    }

    public Prestation isDelete(Boolean isDelete) {
        this.isDelete = isDelete;
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public Prestation dateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateCloture() {
        return dateCloture;
    }

    public Prestation dateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
        return this;
    }

    public void setDateCloture(ZonedDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public ZonedDateTime getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public Prestation dateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
        return this;
    }

    public void setDateDerniereMaj(ZonedDateTime dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public Boolean isIsFerier() {
        return isFerier;
    }

    public Prestation isFerier(Boolean isFerier) {
        this.isFerier = isFerier;
        return this;
    }

    public void setIsFerier(Boolean isFerier) {
        this.isFerier = isFerier;
    }

    public Boolean isIsNuit() {
        return isNuit;
    }

    public Prestation isNuit(Boolean isNuit) {
        this.isNuit = isNuit;
        return this;
    }

    public void setIsNuit(Boolean isNuit) {
        this.isNuit = isNuit;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Prestation commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

     public Set<Journal> getJournals() {
        return journals;
    }

    public Prestation journals(Set<Journal> journals) {
        this.journals = journals;
        return this;
    }

    public Prestation addJournal(Journal journal) {
        this.journals.add(journal);
        //journal.setPrestation(this);
        return this;
    }

    public Prestation removeJournal(Journal journal) {
        this.journals.remove(journal);
        journal.setPrestation(null);
        return this;
    }

    public void setJournals(Set<Journal> journals) {
        this.journals = journals;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public Prestation dossier(Dossier dossier) {
        this.dossier = dossier;
        return this;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }


      public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public Prestation typePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
        return this;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
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
        Prestation prestation = (Prestation) o;
        if (prestation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prestation{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", isDelete='" + isIsDelete() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateDerniereMaj='" + getDateDerniereMaj() + "'" +
            ", isFerier='" + isIsFerier() + "'" +
            ", isNuit='" + isIsNuit() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
