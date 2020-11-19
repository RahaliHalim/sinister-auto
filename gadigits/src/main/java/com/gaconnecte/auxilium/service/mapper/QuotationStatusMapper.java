package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;


import com.gaconnecte.auxilium.domain.QuotationStatus;
import com.gaconnecte.auxilium.service.dto.QuotationStatusDTO;

@Mapper(componentModel = "spring", uses = {})
public interface QuotationStatusMapper extends EntityMapper <QuotationStatusDTO, QuotationStatus> {
    
    
    QuotationStatus toEntity(QuotationStatusDTO quotationStatusDTO); 
    default QuotationStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotationStatus quotationStatus = new QuotationStatus();
        quotationStatus.setId(id);
        return quotationStatus;
    }
}
