package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserAccess entity.
 */
public class UserAccessDTO implements Serializable {

    private Long id;

    private String label;

    private String code;

    private String translationCode;
    
    private String url;

    private Boolean leaf;

    private Long businessEntityId;

    private Long functionalityId;

    private Long elementMenuId;
    
    private String elementMenuLabel;
    
    private Long profileId;

    private String profileLabel;
    
    private Long userId;

    private String userLogin;

    public UserAccessDTO() {
    }

    public UserAccessDTO(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Long getBusinessEntityId() {
        return businessEntityId;
    }

    public void setBusinessEntityId(Long businessEntityId) {
        this.businessEntityId = businessEntityId;
    }

    public Long getFunctionalityId() {
        return functionalityId;
    }

    public void setFunctionalityId(Long functionalityId) {
        this.functionalityId = functionalityId;
    }

    public Long getElementMenuId() {
        return elementMenuId;
    }

    public void setElementMenuId(Long elementMenuId) {
        this.elementMenuId = elementMenuId;
    }

    public String getElementMenuLabel() {
        return elementMenuLabel;
    }

    public void setElementMenuLabel(String elementMenuLabel) {
        this.elementMenuLabel = elementMenuLabel;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getProfileLabel() {
        return profileLabel;
    }

    public void setProfileLabel(String profileLabel) {
        this.profileLabel = profileLabel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAccessDTO userAccessDTO = (UserAccessDTO) o;
        if(userAccessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccessDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", code='" + getCode() + "'" +
            ", url='" + getUrl() + "'" +
            ", leaf='" + isLeaf() + "'" +
            "}";
    }
}
