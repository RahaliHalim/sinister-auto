package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PolicyHolderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PolicyHolder and its DTO PolicyHolderDTO.
 */
@Mapper(componentModel = "spring", uses = {GovernorateMapper.class, DelegationMapper.class, UserMapper.class, })
public interface PolicyHolderMapper extends EntityMapper <PolicyHolderDTO, PolicyHolder> {

    @Mapping(source = "governorate.id", target = "governorateId")
    @Mapping(source = "governorate.label", target = "governorateLabel")

    @Mapping(source = "delegation.id", target = "delegationId")
    @Mapping(source = "delegation.label", target = "delegationLabel")

    @Mapping(source = "creationUser.id", target = "creationUserId")
    @Mapping(source = "creationUser.login", target = "creationUserLogin")

    @Mapping(source = "updateUser.id", target = "updateUserId")
    @Mapping(source = "updateUser.login", target = "updateUserLogin")
    PolicyHolderDTO toDto(PolicyHolder policyHolder); 

    @Mapping(source = "governorateId", target = "governorate")

    @Mapping(source = "delegationId", target = "delegation")

    @Mapping(source = "creationUserId", target = "creationUser")

    @Mapping(source = "updateUserId", target = "updateUser")
    PolicyHolder toEntity(PolicyHolderDTO policyHolderDTO); 
    default PolicyHolder fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.setId(id);
        return policyHolder;
    }
}
