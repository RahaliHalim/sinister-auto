package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Partner and its DTO PartnerDTO.
 */
@Mapper(componentModel = "spring", uses = {VisAVisMapper.class,AttachmentMapper.class, })
public interface PartnerMapper extends EntityMapper <PartnerDTO, Partner> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.label", target = "attachmentLabel")
    @Mapping(source = "attachment.originalName", target = "attachmentOriginalName")
    @Mapping(source = "attachment.name", target = "attachmentName")

    PartnerDTO toDto(Partner partner); 

    @Mapping(source = "attachmentId", target = "attachment")
    Partner toEntity(PartnerDTO partnerDTO); 
    default Partner fromId(Long id) {
        if (id == null) {
            return null;
        }
        Partner partner = new Partner();
        partner.setId(id);
        return partner;
    }
}
