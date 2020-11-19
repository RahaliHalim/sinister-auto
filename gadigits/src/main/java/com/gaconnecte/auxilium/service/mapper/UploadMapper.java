package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.Upload;
import com.gaconnecte.auxilium.service.dto.UploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleUsage and its DTO VehicleUsageDTO.
 */
@Mapper(componentModel = "spring", uses = {ReferentielMapper.class, AttachmentMapper.class, UserMapper.class})
public interface UploadMapper extends EntityMapper <UploadDTO, Upload> {
    
	@Mapping(source = "referentiel.id", target = "referentielId")
	@Mapping(source = "attachment.id", target = "pieceJointeId")
	@Mapping(source = "referentiel.libelle", target = "libelle")
	@Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
	UploadDTO toDto(Upload upload); 
	
	@Mapping(source = "referentielId", target = "referentiel")
	@Mapping(source = "pieceJointeId", target = "attachment")
	@Mapping(source = "userId", target = "user")
	Upload toEntity(UploadDTO uploadDTO); 
    default Upload fromId(Long id) {
        if (id == null) {
            return null;
        }
        Upload upload = new Upload();
        upload.setId(id);
        return upload;
    }
}