package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Policy and its DTO PolicyDTO.
 */
@Mapper(componentModel = "spring", uses = {PolicyTypeMapper.class, PolicyNatureMapper.class, PeriodicityMapper.class, PartnerMapper.class, AgencyMapper.class, PolicyHolderMapper.class, })
public interface PolicyMapper extends EntityMapper <PolicyDTO, Policy> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.label", target = "typeLabel")

    @Mapping(source = "nature.id", target = "natureId")
    @Mapping(source = "nature.label", target = "natureLabel")

    @Mapping(source = "periodicity.id", target = "periodicityId")
    @Mapping(source = "periodicity.label", target = "periodicityLabel")

    @Mapping(source = "partner.id", target = "partnerId")
    @Mapping(source = "partner.companyName", target = "partnerCompanyName")

    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "agency.name", target = "agencyName")

    @Mapping(source = "policyHolder.id", target = "policyHolderId")
    @Mapping(source = "policyHolder.companyName", target = "policyHolderCompanyName")
    PolicyDTO toDto(Policy policy); 

    @Mapping(source = "typeId", target = "type")

    @Mapping(source = "natureId", target = "nature")

    @Mapping(source = "periodicityId", target = "periodicity")

    @Mapping(source = "partnerId", target = "partner")

    @Mapping(source = "agencyId", target = "agency")

    @Mapping(source = "policyHolderId", target = "policyHolder")
    @Mapping(target = "vehicles", ignore = true)
    Policy toEntity(PolicyDTO policyDTO); 
    default Policy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Policy policy = new Policy();
        policy.setId(id);
        return policy;
    }
}
