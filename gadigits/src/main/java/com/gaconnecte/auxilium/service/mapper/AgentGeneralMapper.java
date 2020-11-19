package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AgentGeneralDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AgentGeneral and its DTO AgentGeneralDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonnePhysiqueMapper.class, ServiceAssuranceMapper.class, })
public interface AgentGeneralMapper extends EntityMapper <AgentGeneralDTO, AgentGeneral> {

    @Mapping(source = "personnePhysique.id", target = "personnePhysiqueId")
    @Mapping(source = "personnePhysique.nom", target = "personnePhysiqueNom")

    //@Mapping(source = "agence.id", target = "agenceId")
    //@Mapping(source = "agence.nom", target = "agenceNom")

    @Mapping(source = "serviceAssurance.id", target = "serviceAssuranceId")
    @Mapping(source = "serviceAssurance.libelle", target = "serviceAssuranceLibelle")
    AgentGeneralDTO toDto(AgentGeneral agentGeneral); 

    @Mapping(source = "personnePhysiqueId", target = "personnePhysique")

    //@Mapping(source = "agenceId", target = "agence")

    @Mapping(source = "serviceAssuranceId", target = "serviceAssurance")
    @Mapping(target = "dossiers", ignore = true)
    
    AgentGeneral toEntity(AgentGeneralDTO agentGeneralDTO); 
    default AgentGeneral fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgentGeneral agentGeneral = new AgentGeneral();
        agentGeneral.setId(id);
        return agentGeneral;
    }
}
