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
 * A RefTypeIntervention.
 */
@Entity
@Table(name = "ref_type_intervention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reftypeintervention")
public class RefTypeIntervention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "libelle", length = 60, nullable = false)
    private String libelle;

    @Column(name = "type")
    private Integer type;

    @OneToMany(mappedBy = "typeIntervention")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailsMo> detailMos = new HashSet<>();

    public RefTypeIntervention() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RefTypeIntervention(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public RefTypeIntervention libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<DetailsMo> getDetailMos() {
        return detailMos;
    }

    public RefTypeIntervention detailMos(Set<DetailsMo> detailsMos) {
        this.detailMos = detailsMos;
        return this;
    }

    public RefTypeIntervention addDetailMo(DetailsMo detailsMo) {
        this.detailMos.add(detailsMo);
        detailsMo.setTypeIntervention(this);
        return this;
    }

    public RefTypeIntervention removeDetailMo(DetailsMo detailsMo) {
        this.detailMos.remove(detailsMo);
        detailsMo.setTypeIntervention(null);
        return this;
    }

    public void setDetailMos(Set<DetailsMo> detailsMos) {
        this.detailMos = detailsMos;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefTypeIntervention refTypeIntervention = (RefTypeIntervention) o;
        if (refTypeIntervention.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refTypeIntervention.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RefTypeIntervention{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
