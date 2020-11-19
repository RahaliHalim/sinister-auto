package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ServiceAssuranceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceAssurance and its DTO ServiceAssuranceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceAssuranceMapper extends EntityMapper <ServiceAssuranceDTO, ServiceAssurance> {
    
    
    default ServiceAssurance fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceAssurance serviceAssurance = new ServiceAssurance();
        serviceAssurance.setId(id);
        return serviceAssurance;
    }
}
