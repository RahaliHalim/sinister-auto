package com.gaconnecte.auxilium.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_gerant")
    private Boolean isGerant;

    /**
     * Relation concernant la personne Physique
     */
    @NotNull
    @ApiModelProperty(value = "Relation concernant la personne Physique")
    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private PersonnePhysique personnePhysique;


     @ManyToOne
    private PersonneMorale personneMorale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsGerant() {
        return isGerant;
    }

    public Contact isGerant(Boolean isGerant) {
        this.isGerant = isGerant;
        return this;
    }

    public void setIsGerant(Boolean isGerant) {
        this.isGerant = isGerant;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public Contact personnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
        return this;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

   public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public Contact personneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }
   

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", isGerant='" + isIsGerant() + "'" +
            "}";
    }
}
