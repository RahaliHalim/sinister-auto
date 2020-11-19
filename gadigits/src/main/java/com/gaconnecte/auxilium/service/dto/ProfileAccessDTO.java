package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;

/**
 * A DTO for the ProfileAccess entity.
 */
public class ProfileAccessDTO implements Serializable {

    private Long id;

    private String label;

    private String code;
    
    private String translationCode;    

    private String url;

    private Boolean leaf;

    private Long businessEntityId;

    private String businessEntityLabel;

    private Long functionalityId;

    private String functionalityLabel;

    private Long elementMenuId;

    private String elementMenuLabel;

    private Long profileId;

    private String profileLabel;
    
    private Boolean active;

    private List<Long> profileIds;
    
    public ProfileAccessDTO() {
        this.active = Boolean.TRUE;
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

    public String getBusinessEntityLabel() {
        return businessEntityLabel;
    }

    public void setBusinessEntityLabel(String businessEntityLabel) {
        this.businessEntityLabel = businessEntityLabel;
    }

    public Long getFunctionalityId() {
        return functionalityId;
    }

    public void setFunctionalityId(Long functionalityId) {
        this.functionalityId = functionalityId;
    }

    public String getFunctionalityLabel() {
        return functionalityLabel;
    }

    public void setFunctionalityLabel(String functionalityLabel) {
        this.functionalityLabel = functionalityLabel;
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

    public void setProfileId(Long userProfileId) {
        this.profileId = userProfileId;
    }

    public String getProfileLabel() {
        return profileLabel;
    }

    public void setProfileLabel(String userProfileLabel) {
        this.profileLabel = userProfileLabel;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Long> getProfileIds() {
        return profileIds;
    }

    public void setProfileIds(List<Long> profileIds) {
        this.profileIds = profileIds;
    }

    public void addProfileId(Long profileId) {
        if(this.profileIds == null) {
            this.profileIds = new LinkedList<>();
        }
        this.profileIds.add(profileId);
    }
    
    public void removeProfileId(Long profileId) {
        if(CollectionUtils.isNotEmpty(this.profileIds)) {
            this.profileIds.remove(profileId);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.businessEntityId);
        hash = 13 * hash + Objects.hashCode(this.functionalityId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProfileAccessDTO other = (ProfileAccessDTO) obj;
        if (!Objects.equals(this.businessEntityId, other.businessEntityId)) {
            return false;
        }
        if (!Objects.equals(this.functionalityId, other.functionalityId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProfileAccessDTO{" + "id=" + id + ", label=" + label + ", profileId=" + profileId + ", profileIds=" + profileIds + '}';
    }

    
}
