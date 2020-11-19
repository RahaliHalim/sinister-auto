package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AttachmentMapper extends EntityMapper <AttachmentDTO, Attachment> {

    @Mapping(source = "createUser.id", target = "createUserId")
    @Mapping(source = "createUser.firstName", target = "firstNameUser")
    @Mapping(source = "createUser.lastName", target = "lastNameUser")
    AttachmentDTO toDto(Attachment attachment); 
 
    Attachment toEntity(AttachmentDTO attachmentDTO); 
    @Mapping(source = "createUserId", target = "createUser")
    default Attachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setId(id);
        return attachment;
    }
}
