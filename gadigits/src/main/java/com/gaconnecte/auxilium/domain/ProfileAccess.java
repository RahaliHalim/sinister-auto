package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProfileAccess.
 */
@Entity
@Table(name = "auth_profile_access")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profileaccess")
public class ProfileAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "code")
    private String code;
    
    @Column(name = "translation_code")
    private String translationCode;    

    @Column(name = "url")
    private String url;

    @Column(name = "leaf")
    private Boolean leaf;

    @ManyToOne
    private BusinessEntity businessEntity;

    @ManyToOne
    private Functionality functionality;

    @ManyToOne
    private ElementMenu elementMenu;

    @ManyToOne
    private UserProfile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public ProfileAccess label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public ProfileAccess code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTranslationCode() {
        return translationCode;
    }

    public void setTranslationCode(String translationCode) {
        this.translationCode = translationCode;
    }

    public String getUrl() {
        return url;
    }

    public ProfileAccess url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public ProfileAccess leaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public ProfileAccess businessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
        return this;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public ProfileAccess functionality(Functionality functionality) {
        this.functionality = functionality;
        return this;
    }

    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }

    public ElementMenu getElementMenu() {
        return elementMenu;
    }

    public ProfileAccess elementMenu(ElementMenu elementMenu) {
        this.elementMenu = elementMenu;
        return this;
    }

    public void setElementMenu(ElementMenu elementMenu) {
        this.elementMenu = elementMenu;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public ProfileAccess profile(UserProfile userProfile) {
        this.profile = userProfile;
        return this;
    }

    public void setProfile(UserProfile userProfile) {
        this.profile = userProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileAccess profileAccess = (ProfileAccess) o;
        if (profileAccess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileAccess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileAccess{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", code='" + getCode() + "'" +
            ", url='" + getUrl() + "'" +
            ", leaf='" + isLeaf() + "'" +
            "}";
    }
}
