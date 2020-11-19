package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.enumeration.TypeObservation;

/**
 * A Observation.
 */
@Entity
@Table(name = "observation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

  
    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

  
    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", length = 100)
    private TypeObservation type;

    @ManyToOne(optional = true)
 
    private User user;

    @ManyToOne
    @JoinColumn(name = "sinister_pec_id")
    @JsonBackReference
    private SinisterPec sinisterPec;
    
    @ManyToOne
    @JoinColumn(name = "sinister_prestation_id")
    @JsonBackReference
    private SinisterPrestation sinisterPrestation;

    @ManyToOne(optional = true)
    private Devis devis;

    @Column(name = "prestation_id")
    private Long prestationId;
    
    
    public Long getPrestationId() {
		return prestationId;
	}

	public void setPrestationId(Long prestationId) {
		this.prestationId = prestationId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Observation commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Observation date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public TypeObservation getType() {
        return type;
    }

    public Observation type(TypeObservation type) {
        this.type = type;
        return this;
    }

    public void setType(TypeObservation type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public Observation User(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

 

     public SinisterPec getSinisterPec() {
		return sinisterPec;
	}

	public void setSinisterPec(SinisterPec sinisterPec) {
		this.sinisterPec = sinisterPec;
	}

	public Devis getDevis() {
        return devis;
    }

    public Observation prestation(Devis devis) {
        this.devis = devis;
        return this;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public SinisterPrestation getSinisterPrestation() {
		return sinisterPrestation;
	}

	public void setSinisterPrestation(SinisterPrestation sinisterPrestation) {
		this.sinisterPrestation = sinisterPrestation;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Observation observation = (Observation) o;
        if (observation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), observation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "Observation [id=" + id + ", commentaire=" + commentaire + ", date=" + date + ", type=" + type
				+ ", user=" + user + ", sinisterPec=" + sinisterPec + ", devis=" + devis + ", prestationId="
				+ prestationId + "]";
	}
}
