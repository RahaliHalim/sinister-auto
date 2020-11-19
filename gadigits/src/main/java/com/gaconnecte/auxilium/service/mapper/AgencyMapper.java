package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agency and its DTO AgencyDTO.
 */
@Mapper(componentModel = "spring", uses = {PartnerMapper.class, GovernorateMapper.class, DelegationMapper.class, RegionMapper.class, })
public interface AgencyMapper extends EntityMapper <AgencyDTO, Agency> {

    @Mapping(source = "partner.id", target = "partnerId")
    @Mapping(source = "partner.companyName", target = "partnerCompanyName")

    @Mapping(source = "governorate.id", target = "governorateId")
    @Mapping(source = "governorate.label", target = "governorateLabel")

    @Mapping(source = "delegation.id", target = "delegationId")
    @Mapping(source = "delegation.label", target = "delegationLabel")

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.label", target = "regionLabel")
    AgencyDTO toDto(Agency agency); 

    @Mapping(source = "partnerId", target = "partner")

    @Mapping(source = "governorateId", target = "governorate")

    @Mapping(source = "delegationId", target = "delegation")

    @Mapping(source = "regionId", target = "region")
    Agency toEntity(AgencyDTO agencyDTO); 
    default Agency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agency agency = new Agency();
        agency.setId(id);
        return agency;
    }
}
