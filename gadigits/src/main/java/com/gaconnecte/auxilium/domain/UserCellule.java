package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/**
 * A UserCellule.
 */
@Entity
@Table(name = "user_cellule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserCellule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Cellule cellule;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_cellule_authorities",
               joinColumns = @JoinColumn(name="users_cellules_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="authorities_name", referencedColumnName="name"))
    private Set<Authority> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public UserCellule user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cellule getCellule() {
        return cellule;
    }

    public UserCellule cellule(Cellule cellule) {
        this.cellule = cellule;
        return this;
    }

    public void setCellule(Cellule cellule) {
        this.cellule = cellule;
    }

   public Set<Authority> getAuthorities() {
        return authorities;
    }

    public UserCellule authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public UserCellule addAuthorities(Authority authority) {
        this.authorities.add(authority);
        authority.getUsersCellules().add(this);
        return this;
    }

    public UserCellule removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
        authority.getUsersCellules().remove(this);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCellule userCellule = (UserCellule) o;
        if (userCellule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userCellule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserCellule{" +
            "id=" + getId() +
            "}";
    }
}
