package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserPartnerMode entity.
 */
public class UserPartnerModeDTO implements Serializable {

    private Long id;

    private Long partnerId;

    private Long modeId;

    private Long userExtraId;

    private Long courtierId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserExtraId() {
        return userExtraId;
    }

    public void setUserExtraId(Long userExtraId) {
        this.userExtraId = userExtraId;
    }

    public Long getCourtierId() {
        return courtierId;
    }

    public void setCourtierId(Long courtierId) {
        this.courtierId = courtierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPartnerModeDTO userPartnerModeDTO = (UserPartnerModeDTO) o;
        if(userPartnerModeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPartnerModeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPartnerModeDTO{" +
            "id=" + getId() +
            ", partnerId='" + getPartnerId() + "'" +
            ", modeId='" + getModeId() + "'" +
            "}";
    }
}
