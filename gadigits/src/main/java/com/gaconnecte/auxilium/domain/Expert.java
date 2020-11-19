package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.javers.core.metamodel.annotation.TypeName;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Expert.
 */
@Entity
@Table(name = "expert")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "expert")
@TypeName("Expert")
public class Expert implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "raison_sociale")
	private String raisonSociale;
	
	@Column(name = "nom", length = 100)
	private String nom;
	@Column(name = "prenom", length = 100)
	private String prenom;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "fax")
	private String fax;

	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "mobile_2")
	private String mobile2;

	@Column(name = "numero_f_t_u_s_a")
	private String numeroFTUSA;

	@Column(name = "centre_expertise")
	private Boolean centreExpertise;

	@Column(name = "blocage")
	private Boolean blocage;
	
	
	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "eng")
	private Boolean eng;
	
	public Boolean getEng() {
		return eng;
	}

	public void setEng(Boolean eng) {
		this.eng = eng;
	}

	@Column(name = "debut_blocage")
	private LocalDate debutBlocage;

	@Column(name = "fin_blocage")
	private LocalDate finBlocage;
	
	@Column(name = "date_effet_convention")
	private LocalDate dateEffetConvention;

	@Column(name = "date_fin_convention")
	private LocalDate dateFinConvention;

	@ManyToOne
	private Delegation delegation;

	@Column(name = "adresse")
	private String adresse;
	
	@OneToMany(mappedBy = "expert", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExpertGarantieImplique> garantieImpliques = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "expert_villes", joinColumns = { @JoinColumn(name = "expert_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ville_id") })
	private Set<Delegation> listeVilles = new HashSet<>();
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "expert_governorates", joinColumns = { @JoinColumn(name = "expert_id") }, inverseJoinColumns = {
			@JoinColumn(name = "governorate_id") })
	private Set<Governorate> gouvernorats = new HashSet<>();
	
	
	
	
	public List<ExpertGarantieImplique> getGarantieImpliques() {
		return garantieImpliques;
	}

	public void setGarantieImpliques(List<ExpertGarantieImplique> garantieImpliques) {
		this.garantieImpliques = garantieImpliques;
	}

	public Set<Delegation> getListeVilles() {
		return listeVilles;
	}

	public void setListeVilles(Set<Delegation> listeVilles) {
		this.listeVilles = listeVilles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNumeroFTUSA() {
		return numeroFTUSA;
	}

	public Set<Governorate> getGouvernorats() {
		return gouvernorats;
	}

	public void setGouvernorats(Set<Governorate> gouvernorats) {
		this.gouvernorats = gouvernorats;
	}

	public void setNumeroFTUSA(String numeroFTUSA) {
		this.numeroFTUSA = numeroFTUSA;
	}

	public Boolean getCentreExpertise() {
		return centreExpertise;
	}

	public void setCentreExpertise(Boolean centreExpertise) {
		this.centreExpertise = centreExpertise;
	}

	public Boolean getBlocage() {
		return blocage;
	}

	public void setBlocage(Boolean blocage) {
		this.blocage = blocage;
	}

	public LocalDate getDebutBlocage() {
		return debutBlocage;
	}

	public void setDebutBlocage(LocalDate debutBlocage) {
		this.debutBlocage = debutBlocage;
	}

	public LocalDate getFinBlocage() {
		return finBlocage;
	}

	public void setFinBlocage(LocalDate finBlocage) {
		this.finBlocage = finBlocage;
	}

	public Delegation getDelegation() {
		return delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public LocalDate getDateEffetConvention() {
		return dateEffetConvention;
	}

	public void setDateEffetConvention(LocalDate dateEffetConvention) {
		this.dateEffetConvention = dateEffetConvention;
	}

	public LocalDate getDateFinConvention() {
		return dateFinConvention;
	}

	public void setDateFinConvention(LocalDate dateFinConvention) {
		this.dateFinConvention = dateFinConvention;
	}


	
	@Override
	public String toString() {
		return "Expert [raisonSociale=" + raisonSociale + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email
				+ ", telephone=" + telephone + ", fax=" + fax + ", mobile=" + mobile + ", blocage=" + blocage
				+ ", debutBlocage=" + debutBlocage + ", finBlocage=" + finBlocage + ", delegation=" + delegation
				+ ", adresse=" + adresse + "]";
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expert other = (Expert) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
