package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.time.LocalDate;
/**
 * A UserCellule.
 */
@Entity
@Table(name = "grille_reparateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReparateurGrille implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
	private LocalDate date;

    @ManyToOne
    private Reparateur reparateur;

    @ManyToOne
    private Grille grille;

    @ManyToOne(optional = false)
    private RefTypeIntervention refTypeIntervention;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReparateurGrille date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Reparateur getReparateur() {
        return reparateur;
    }

    public ReparateurGrille reparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
        return this;
    }

    public void setReparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
    }

    public Grille getGrille() {
        return grille;
    }

    public ReparateurGrille grille(Grille grille) {
        this.grille = grille;
        return this;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

     public RefTypeIntervention getRefTypeIntervention() {
        return refTypeIntervention;
    }

    public ReparateurGrille refTypeIntervention(RefTypeIntervention refTypeIntervention) {
        this.refTypeIntervention = refTypeIntervention;
        return this;
    }

    public void setRefTypeIntervention(RefTypeIntervention refTypeIntervention) {
        this.refTypeIntervention = refTypeIntervention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReparateurGrille reparateurGrille = (ReparateurGrille) o;
        if (reparateurGrille.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reparateurGrille.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "reparateurGrille{" +
            "id=" + getId() +
            "}";
    }
}
