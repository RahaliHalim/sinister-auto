package com.gaconnecte.auxilium.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RefMotif.
 */
@Entity
@Table(name = "ref_motif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refmotif")
public class RefMotif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "motifs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journal> journals = new HashSet<>();

    @ManyToMany(mappedBy = "motifs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SysActionUtilisateur> actionUtilisateurs = new HashSet<>();

    @ManyToOne
    private Authority authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public RefMotif libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Journal> getJournals() {
        return journals;
    }

    public RefMotif journals(Set<Journal> journals) {
        this.journals = journals;
        return this;
    }

    public RefMotif addJournal(Journal journal) {
        this.journals.add(journal);
        journal.getMotifs().add(this);
        return this;
    }

    public RefMotif removeJournal(Journal journal) {
        this.journals.remove(journal);
        journal.getMotifs().remove(this);
        return this;
    }

    public void setJournals(Set<Journal> journals) {
        this.journals = journals;
    }

    public Authority getAuthority() {
        return authority;
    }

    public RefMotif authority(Authority authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<SysActionUtilisateur> getActionUtilisateurs() {
        return actionUtilisateurs;
    }

    public RefMotif actionUtilisateurs(Set<SysActionUtilisateur> sysActionUtilisateurs) {
        this.actionUtilisateurs = sysActionUtilisateurs;
        return this;
    }

    public RefMotif addActionUtilisateur(SysActionUtilisateur sysActionUtilisateur) {
        this.actionUtilisateurs.add(sysActionUtilisateur);
        sysActionUtilisateur.getMotifs().add(this);
        return this;
    }

    public RefMotif removeActionUtilisateur(SysActionUtilisateur sysActionUtilisateur) {
        this.actionUtilisateurs.remove(sysActionUtilisateur);
        sysActionUtilisateur.getMotifs().remove(this);
        return this;
    }

    public void setActionUtilisateurs(Set<SysActionUtilisateur> sysActionUtilisateurs) {
        this.actionUtilisateurs = sysActionUtilisateurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefMotif refMotif = (RefMotif) o;
        if (refMotif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refMotif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefMotif{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
