package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.domain.enumeration.ApprovPec;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A SinisterPec.
 */
@Entity
@Table(name = "app_motifs_sinister_pec")

public class MotifsSinisterPec implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "sinister_pec_id")
	private SinisterPec sinisterPec;
    @ManyToOne
    @JoinColumn(name = "ref_motif_id")
	private RefMotif refMotif;
    @ManyToOne
    @JoinColumn(name = "ref_mode_id")
	private RefModeGestion refModeGestion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SinisterPec getSinisterPec() {
        return sinisterPec;
    }

    public void setSinisterPec(SinisterPec sinisterPec) {
        this.sinisterPec = sinisterPec;
    }

    public RefMotif getRefMotif() {
        return refMotif;
    }

    public void setRefMotif(RefMotif refMotif) {
        this.refMotif = refMotif;
    }

    public RefModeGestion getRefModeGestion() {
        return refModeGestion;
    }

    public void setRefModeGestion(RefModeGestion refModeGestion) {
        this.refModeGestion = refModeGestion;
    }

}