package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.QuotationHistory;
import com.gaconnecte.auxilium.service.dto.QuotationHistoryDTO;

/**
 * Mapper for the entity RefAgence and its DTO RefAgenceDTO.
 */
@Mapper(componentModel = "spring", uses = {PrestationPECMapper.class, })
public interface QuotationHistoryMapper extends EntityMapper <QuotationHistoryDTO, QuotationHistory> {

    @Mapping(source = "prestation.id", target = "prestationPecId")
    

    QuotationHistoryDTO toDto(QuotationHistory quotationHistory); 

    @Mapping(source = "prestationPecId", target = "prestation")

   
    QuotationHistory toEntity(QuotationHistoryDTO quotationHistoryDTO); 
    default QuotationHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotationHistory quotationHistory = new QuotationHistory();
        quotationHistory.setId(id);
        return quotationHistory;
    }
}
