package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserAccess.
 */
@Entity
@Table(name = "auth_user_access")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "useraccess")
public class UserAccess implements Serializable {

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
    @JoinColumn(name = "business_entity_id")
    private BusinessEntity businessEntity;

    @ManyToOne
    @JoinColumn(name = "functionality_id")
    private Functionality functionality;

    @ManyToOne
    @JoinColumn(name = "element_menu_id")
    private ElementMenu elementMenu;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile profile;

    @ManyToOne
    @JoinColumn(name = "user_extra_id")
    private UserExtra userExtra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public UserAccess label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public UserAccess code(String code) {
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

    public UserAccess url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public UserAccess leaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public BusinessEntity getBusinessEntity() {
        return businessEntity;
    }

    public UserAccess businessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
        return this;
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public UserAccess functionality(Functionality functionality) {
        this.functionality = functionality;
        return this;
    }

    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }

    public ElementMenu getElementMenu() {
        return elementMenu;
    }

    public UserAccess elementMenu(ElementMenu elementMenu) {
        this.elementMenu = elementMenu;
        return this;
    }

    public void setElementMenu(ElementMenu elementMenu) {
        this.elementMenu = elementMenu;
    }

    public UserExtra getUserExtra() {
        return userExtra;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public UserAccess userExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
        return this;
    }

    public void setUserExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccess userAccess = (UserAccess) o;
        if (userAccess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccess.getId());
    }

    @Override
	public String toString() {
		return "UserAccess [label=" + label + ", code=" + code + ", translationCode=" + translationCode + ", url=" + url
				+ ", businessEntity=" + businessEntity + ", functionality=" + functionality + ", elementMenu="
				+ elementMenu + ", profile=" + profile + ", userExtra=" + userExtra + "]";
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

  
}
