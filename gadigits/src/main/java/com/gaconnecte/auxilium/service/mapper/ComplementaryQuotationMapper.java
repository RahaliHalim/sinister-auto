package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;

@Mapper(componentModel = "spring", uses = {QuotationStatusMapper.class, DetailsPiecesMapper.class, SinisterPecMapper.class})
public interface ComplementaryQuotationMapper extends EntityMapper<ComplementaryQuotationDTO,ComplementaryQuotation>{

	
	@Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    @Mapping(source = "status.id", target = "statusId")
	ComplementaryQuotationDTO toDto(ComplementaryQuotation complementaryQuotation); 
	
	@Mapping(source = "sinisterPecId", target = "sinisterPec")
    @Mapping(source = "statusId", target = "status")

	ComplementaryQuotation toEntity(ComplementaryQuotationDTO complementaryQuotationDTO); 
    default ComplementaryQuotation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ComplementaryQuotation complementaryQuotation = new ComplementaryQuotation();
        complementaryQuotation.setId(id);
        return complementaryQuotation;
    }
}