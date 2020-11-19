package com.gaconnecte.auxilium.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.*;

import com.gaconnecte.auxilium.domain.MotifsSinisterPec;
import com.gaconnecte.auxilium.service.dto.MotifsSinisterPecDTO;

@Mapper(componentModel = "spring", uses = {SinisterPecMapper.class, RefModeGestionMapper.class, RefMotifMapper.class})
public interface MotifsSinisterPecMapper extends EntityMapper <MotifsSinisterPecDTO, MotifsSinisterPec> {
	
    @Mapping(source = "sinisterPec.id", target = "sinisterPecId")
    @Mapping(source = "refMotif.id", target = "refMotifId")
    @Mapping(source = "refModeGestion.id", target = "refModeGestionId")

    MotifsSinisterPecDTO toDto(MotifsSinisterPec motifsSinisterPec); 

    @Mapping(source = "sinisterPecId", target = "sinisterPec")
    @Mapping(source = "refMotifId", target = "refMotif")
    @Mapping(source = "refModeGestionId", target = "refModeGestion")

    MotifsSinisterPec toEntity(MotifsSinisterPecDTO motifsSinisterPecDTO);
    default MotifsSinisterPec fromId(Long id) {
        if (id == null) {
            return null;
        }
        MotifsSinisterPec motifsSinisterPec = new MotifsSinisterPec();
        motifsSinisterPec.setId(id);
        return motifsSinisterPec;
    }

}
