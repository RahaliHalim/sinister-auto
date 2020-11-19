package com.gaconnecte.auxilium.service.prm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.prm.Convention;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
import com.gaconnecte.auxilium.service.prm.mapper.ConventionAmendmentMapper;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.prm.dto.ConventionDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPackMapper;

/**
 * Mapper for the entity Convention and its DTO ConventionDTO.
 */
@Mapper(componentModel = "spring", uses = {ConventionAmendmentMapper.class,PartnerMapper.class, RefPackMapper.class, AttachmentMapper.class,})
public interface ConventionMapper extends EntityMapper <ConventionDTO, Convention> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.companyName", target = "clientLabel")

    ConventionDTO toDto(Convention convention);

    @Mapping(source = "clientId", target = "client")

    Convention toEntity(ConventionDTO conventionDTO);

    default Convention fromId(Long id) {
        if (id == null) {
            return null;
        }
        Convention convention = new Convention();
        convention.setId(id);
        return convention;
    }
}
