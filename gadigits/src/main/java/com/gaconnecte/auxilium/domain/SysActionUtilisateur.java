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
 * A SysActionUtilisateur.
 */
@Entity
@Table(name = "sys_action_utilisateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sysactionutilisateur")
public class SysActionUtilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @OneToMany(mappedBy = "sysActionUtilisateur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journal> journals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sys_action_utilisateur_motif",
               joinColumns = @JoinColumn(name="sys_action_utilisateurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="motifs_id", referencedColumnName="id"))
    private Set<RefMotif> motifs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public SysActionUtilisateur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Journal> getJournals() {
        return journals;
    }

    public SysActionUtilisateur journals(Set<Journal> journals) {
        this.journals = journals;
        return this;
    }

    public SysActionUtilisateur addJournal(Journal journal) {
        this.journals.add(journal);
        journal.setSysActionUtilisateur(this);
        return this;
    }

    public SysActionUtilisateur removeJournal(Journal journal) {
        this.journals.remove(journal);
        journal.setSysActionUtilisateur(null);
        return this;
    }

    public void setJournals(Set<Journal> journals) {
        this.journals = journals;
    }

     public Set<RefMotif> getMotifs() {
        return motifs;
    }

    public SysActionUtilisateur motifs(Set<RefMotif> refMotifs) {
        this.motifs = refMotifs;
        return this;
    }

    public SysActionUtilisateur addMotif(RefMotif refMotif) {
        this.motifs.add(refMotif);
        refMotif.getActionUtilisateurs().add(this);
        return this;
    }

    public SysActionUtilisateur removeMotif(RefMotif refMotif) {
        this.motifs.remove(refMotif);
        refMotif.getActionUtilisateurs().remove(this);
        return this;
    }

    public void setMotifs(Set<RefMotif> refMotifs) {
        this.motifs = refMotifs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysActionUtilisateur sysActionUtilisateur = (SysActionUtilisateur) o;
        if (sysActionUtilisateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysActionUtilisateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysActionUtilisateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
