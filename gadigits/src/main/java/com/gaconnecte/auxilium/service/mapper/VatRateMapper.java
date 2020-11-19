package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.VatRateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VatRate and its DTO VatRateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VatRateMapper extends EntityMapper <VatRateDTO, VatRate> {
    
    
    default VatRate fromId(Long id) {
        if (id == null) {
            return null;
        }
        VatRate vatRate = new VatRate();
        vatRate.setId(id);
        return vatRate;
    }
}
