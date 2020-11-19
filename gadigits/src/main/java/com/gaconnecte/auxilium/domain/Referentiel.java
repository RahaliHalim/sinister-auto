package com.gaconnecte.auxilium.domain;
import com.gaconnecte.auxilium.domain.app.Attachment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A RefBareme.
 */
@Entity
@Table(name = "referentiel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "refrientiel")
public class Referentiel implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "libelle", length = 256, nullable = false)
    private String libelle;
    
    @OneToMany(mappedBy = "referentiel", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Upload> upload = new ArrayList<Upload>();
    
    
    public Referentiel() {
		super();
	}

	public Referentiel(String libelle) {
		super();
		this.libelle = libelle;
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

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<Upload> getUpload() {
		return upload;
	}

	public void setUpload(List<Upload> upload) {
		this.upload = upload;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefBareme refBareme = (RefBareme) o;
        if (refBareme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refBareme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

   
}