package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.DetailsQuotation;
import com.gaconnecte.auxilium.service.dto.DetailsQuotationDTO;

@Mapper(componentModel = "spring", uses = {})
public interface DetailsQuotationMapper extends EntityMapper<DetailsQuotationDTO, DetailsQuotation> {

    DetailsQuotationDTO toDto(DetailsQuotation detailsQuotation);

    DetailsQuotation toEntity(DetailsQuotationDTO detailsQuotationDTO);

    default DetailsQuotation fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailsQuotation detailsQuotation = new DetailsQuotation();
        detailsQuotation.setId(id);
        return detailsQuotation;
    }

}