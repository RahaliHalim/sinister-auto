package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;

@Mapper(componentModel = "spring", uses = {QuotationStatusMapper.class, DetailsPiecesMapper.class, SinisterPecMapper.class})
public interface PrimaryQuotationMapper extends EntityMapper<PrimaryQuotationDTO,PrimaryQuotation>{

	
	@Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    @Mapping(source = "status.id", target = "statusId")
	PrimaryQuotationDTO toDto(PrimaryQuotation primaryQuotation); 
	
	@Mapping(source = "sinisterPecId", target = "sinisterPec")
    @Mapping(source = "statusId", target = "status")

	PrimaryQuotation toEntity(PrimaryQuotationDTO primaryQuotationDTO); 
    default PrimaryQuotation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrimaryQuotation primaryQuotation = new PrimaryQuotation();
        primaryQuotation.setId(id);
        return primaryQuotation;
    }
}