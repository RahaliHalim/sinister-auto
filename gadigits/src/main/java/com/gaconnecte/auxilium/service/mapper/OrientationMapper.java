package com.gaconnecte.auxilium.service.mapper;
import com.gaconnecte.auxilium.domain.*;

import com.gaconnecte.auxilium.service.dto.OrientationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity orientation and its DTO OrientationDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleBrandMapper.class, RefAgeVehiculeMapper.class,ReparateurMapper.class,})
public interface OrientationMapper extends EntityMapper <OrientationDTO, Orientation> {

  
    @Mapping(source = "refAgeVehicule.id", target = "refAgeVehiculeId")
    @Mapping(source = "refAgeVehicule.valeur", target = "refAgeVehiculeValeur")
    @Mapping(source = "reparateur.id", target = "reparateurId")

    OrientationDTO toDto(Orientation orientation); 

    @Mapping(source = "refAgeVehiculeId", target = "refAgeVehicule")
    @Mapping(source = "reparateurId", target = "reparateur.id")
    Orientation toEntity(OrientationDTO orientationDTO); 
    default Orientation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orientation orientation = new Orientation();
        orientation.setId(id);
        return orientation;
    }
}
