package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.TarifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tarif and its DTO TarifDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TarifMapper extends EntityMapper <TarifDTO, Tarif> {
    
    
    default Tarif fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarif tarif = new Tarif();
        tarif.setId(id);
        return tarif;
    }
}
