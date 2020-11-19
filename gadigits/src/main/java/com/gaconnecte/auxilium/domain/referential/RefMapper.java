package com.gaconnecte.auxilium.domain.referential;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ref_mapping")
public class RefMapper implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
	@Column(name = "code_referentiel")
	private Long codeRef;
	@Column(name = "id_compagnie")
	private Long idCompagnie;
	@Column(name = "code_correspondance")
	private String codeCorrespondance;
	@Column(name = "type_referentiel")
	private Long typeRef;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCodeRef() {
		return codeRef;
	}
	public void setCodeRef(Long codeRef) {
		this.codeRef = codeRef;
	}
	public Long getIdCompagnie() {
		return idCompagnie;
	}
	public void setIdCompagnie(Long idCompagnie) {
		this.idCompagnie = idCompagnie;
	}
	public String getCodeCorrespondance() {
		return codeCorrespondance;
	}
	public void setCodeCorrespondance(String codeCorrespondance) {
		this.codeCorrespondance = codeCorrespondance;
	}
	public Long getTypeRef() {
		return typeRef;
	}
	public void setTypeRef(Long typeRef) {
		this.typeRef = typeRef;
	}
	
	
	
	
}
