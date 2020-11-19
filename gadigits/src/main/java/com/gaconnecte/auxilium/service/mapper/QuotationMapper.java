package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.Quotation;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;

@Mapper(componentModel = "spring", uses = {QuotationStatusMapper.class, DetailsPiecesMapper.class,SinisterPecMapper.class})
public interface QuotationMapper extends EntityMapper<QuotationDTO,Quotation>{
	
	@Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "sinisterPec.id", target = "sinisterPecId")
	QuotationDTO toDto(Quotation quotation); 
	@Mapping(target = "listPieces", ignore = true)	
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "sinisterPecId", target = "sinisterPec")

	Quotation toEntity(QuotationDTO quotationDTO); 
    default Quotation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quotation quotation = new Quotation();
        quotation.setId(id);
        return quotation;
    }
}