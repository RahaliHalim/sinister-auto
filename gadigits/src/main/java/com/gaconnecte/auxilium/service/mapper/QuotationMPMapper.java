package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.QuotationMP;
import com.gaconnecte.auxilium.service.dto.QuotationMPDTO;

@Mapper(componentModel = "spring", uses = {QuotationStatusMapper.class, DetailsPiecesMapper.class})
public interface QuotationMPMapper extends EntityMapper<QuotationMPDTO,QuotationMP>{
	
	@Mapping(source = "status.id", target = "statusId")
	QuotationMPDTO toDto(QuotationMP quotationMP); 	
    @Mapping(source = "statusId", target = "status")

	QuotationMP toEntity(QuotationMPDTO quotationMPDTO); 
    default QuotationMP fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotationMP quotationMP = new QuotationMP();
        quotationMP.setId(id);
        return quotationMP;
    }
}