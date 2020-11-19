package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.javers.core.metamodel.annotation.DiffIgnore;

import java.util.Objects;

/**
 * A DTO for the UserExtra entity.
 */
public class UserExtraDTO implements Serializable {

    private Long id;

    private String code;

    private Long personId;

    private Long userId;

    private String firstName;

    private String lastName;

    private Long userBossId;

    private String userLogin;

    private Long profileId;

    private String profileName;

    private String email;

    private Boolean activated;
    @DiffIgnore
    private Set<UserAccessDTO> accesses = new HashSet<>();

    //@DiffIgnore
    private Set<UserPartnerModeDTO> userPartnerModes = new HashSet<>();
    @DiffIgnore
    private UserAccessWorkDTO userAccessWork;

    @DiffIgnore
    private Set<PartnerModeMappingDTO> partnerModes = new HashSet<>();

    public UserExtraDTO() {
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

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserBossId() {
        return userBossId;
    }

    public void setUserBossId(Long userBossId) {
        this.userBossId = userBossId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long userProfileId) {
        this.profileId = userProfileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<UserAccessDTO> getAccesses() {
        return accesses;
    }

    public void setAccesses(Set<UserAccessDTO> accesses) {
        this.accesses = accesses;
    }

    public Set<PartnerModeMappingDTO> getPartnerModes() {
        return partnerModes;
    }

    public void setPartnerModes(Set<PartnerModeMappingDTO> partnerModes) {
        this.partnerModes = partnerModes;
    }

    public Set<UserPartnerModeDTO> getUserPartnerModes() {
        return userPartnerModes;
    }

    public void setUserPartnerModes(Set<UserPartnerModeDTO> userPartnerModes) {
        this.userPartnerModes = userPartnerModes;
    }

    public UserAccessWorkDTO getUserAccessWork() {
        return userAccessWork;
    }

    public void setUserAccessWork(UserAccessWorkDTO userAccessWork) {
        this.userAccessWork = userAccessWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExtraDTO userExtraDTO = (UserExtraDTO) o;
        if(userExtraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", personId='" + getPersonId() + "'" +
            "}";
    }
}
