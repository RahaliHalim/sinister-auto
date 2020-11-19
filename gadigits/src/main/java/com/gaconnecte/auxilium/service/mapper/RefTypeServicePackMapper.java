package com.gaconnecte.auxilium.service.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.gaconnecte.auxilium.domain.RefTypeServicePack;
import com.gaconnecte.auxilium.service.dto.RefTypeServicePackDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPackMapper;


/**
 * Mapper for the entity RefTypeService and its DTO RefTypeServicePackDTO.
 */
    @Mapper(componentModel = "spring", uses = { RefPackMapper.class,RefTypeServiceMapper.class,})
     public interface RefTypeServicePackMapper extends EntityMapper <RefTypeServicePackDTO, RefTypeServicePack> {
    
	@Mapping(source = "service.id", target = "serviceId")
	@Mapping(source = "service.nom", target = "serviceNom")
	@Mapping(source = "pack.id", target = "packId")
	@Mapping(source = "pack.label", target = "packNom")
	RefTypeServicePackDTO toDto(RefTypeServicePack refTypeServicePack);
	
	@Mapping(source = "packId", target = "pack")
	@Mapping(source = "serviceId", target = "service")
    RefTypeServicePack toEntity(RefTypeServicePackDTO refTypeServicePackDTO); 
    
  
    
    default RefTypeServicePack fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefTypeServicePack refTypeServicePack = new RefTypeServicePack();
        refTypeServicePack.setId(id);
        return refTypeServicePack;
    }
}
