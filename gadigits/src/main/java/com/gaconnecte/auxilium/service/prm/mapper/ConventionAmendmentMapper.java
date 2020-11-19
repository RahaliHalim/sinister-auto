package com.gaconnecte.auxilium.service.prm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.prm.ConventionAmendment;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.prm.dto.ConventionAmendmentDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPackMapper;

/**
 * Mapper for the entity ConventionAmendment and its DTO ConventionAmendmentDTO.
 */
@Mapper(componentModel = "spring", uses = {ConventionMapper.class,RefPackMapper.class, AttachmentMapper.class,})
public interface ConventionAmendmentMapper extends EntityMapper <ConventionAmendmentDTO, ConventionAmendment> {

    @Mapping(source = "convention.id", target = "conventionId")

    ConventionAmendmentDTO toDto(ConventionAmendment conventionAmendment);

    @Mapping(source = "conventionId", target = "convention")

    ConventionAmendment toEntity(ConventionAmendmentDTO conventionAmendmentDTO);

    default ConventionAmendment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConventionAmendment conventionAmendment = new ConventionAmendment();
        conventionAmendment.setId(id);
        return conventionAmendment;
    }
}
