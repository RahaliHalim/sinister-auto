package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.JournalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Journal and its DTO JournalDTO.
 */
@Mapper(componentModel = "spring", uses = {SysActionUtilisateurMapper.class, RefMotifMapper.class, DossierMapper.class, PrestationPECMapper.class , RefRemorqueurMapper.class ,ReparateurMapper.class})
public interface JournalMapper extends EntityMapper <JournalDTO, Journal> {

	@Mapping(source = "sysActionUtilisateur.id", target = "sysActionUtilisateurId")
    @Mapping(source = "sysActionUtilisateur.nom", target = "sysActionUtilisateurNom")
    @Mapping(source = "dossier.id", target = "dossierId")
    @Mapping(source = "dossier.reference", target = "dossierReference")
    @Mapping(source = "prestation.id", target = "prestationId")
    @Mapping(source = "refRemorqueur.id", target = "refRemorqueurId")
    @Mapping(source = "reparateur.id", target = "reparateurId")

    
    JournalDTO toDto(Journal journal);

    @Mapping(source = "dossierId", target = "dossier")
    @Mapping(source = "prestationId", target = "prestation")
    @Mapping(source = "sysActionUtilisateurId", target = "sysActionUtilisateur")
    @Mapping(source = "refRemorqueurId", target = "refRemorqueur")
    @Mapping(source = "reparateurId", target = "reparateur")

    Journal toEntity(JournalDTO journalDTO);
    default Journal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Journal journal = new Journal();
        journal.setId(id);
        return journal;
    }
}
