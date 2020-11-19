package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "jhi_user_extra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userextra")
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "person_id")
    private Long personId;

    @OneToOne
    private User user;

     @Column(name = "user_boss_id")
    private Long userBossId;

    @ManyToOne
    private UserProfile profile;

    @OneToMany(mappedBy = "userExtra", orphanRemoval = true, cascade = {CascadeType.ALL})
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserAccess> accesses = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.ALL})
    @JsonIgnore
    private Set<UserPartnerMode> userPartnerModes = new HashSet<>();

    public UserExtra(Long id) {
        this.id = id;
    }

    public UserExtra() {
       
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public UserExtra code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPersonId() {
        return personId;
    }

    public UserExtra personId(Long personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public UserExtra profile(UserProfile userProfile) {
        this.profile = userProfile;
        return this;
    }

    public void setProfile(UserProfile userProfile) {
        this.profile = userProfile;
    }

    public Set<UserAccess> getAccesses() {
        return accesses;
    }

    public UserExtra accesses(Set<UserAccess> userAccesses) {
        this.accesses = userAccesses;
        return this;
    }

    public UserExtra addAccess(UserAccess userAccess) {
        this.accesses.add(userAccess);
        userAccess.setUserExtra(this);
        return this;
    }

    public UserExtra addUserPartnerMode(UserPartnerMode userPartnerMode) {
        this.userPartnerModes.add(userPartnerMode);
        userPartnerMode.setUserExtra(this);
        return this;
    }

    public UserExtra removeAccess(UserAccess userAccess) {
        this.accesses.remove(userAccess);
        userAccess.setUserExtra(null);
        return this;
    }

    public void setAccesses(Set<UserAccess> userAccesses) {
        this.accesses = userAccesses;
    }

      public Set<UserPartnerMode> getUserPartnerModes() {
        return userPartnerModes;
    }

    public void setUserPartnerModes(Set<UserPartnerMode> userPartnerModes) {
        this.userPartnerModes = userPartnerModes;
    }

    public Long getUserBossId() {
        return userBossId;
    }

    public void setUserBossId(Long userBossId) {
        this.userBossId = userBossId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExtra userExtra = (UserExtra) o;
        if (userExtra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", personId='" + getPersonId() + "'" +
            "}";
    }
}
