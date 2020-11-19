package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.SysActionUtilisateurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SysActionUtilisateur and its DTO SysActionUtilisateurDTO.
 */
@Mapper(componentModel = "spring", uses = {RefMotifMapper.class})
public interface SysActionUtilisateurMapper extends EntityMapper <SysActionUtilisateurDTO, SysActionUtilisateur> {

    @Mapping(target = "journals", ignore = true)
    SysActionUtilisateur toEntity(SysActionUtilisateurDTO sysActionUtilisateurDTO);
    default SysActionUtilisateur fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysActionUtilisateur sysActionUtilisateur = new SysActionUtilisateur();
        sysActionUtilisateur.setId(id);
        return sysActionUtilisateur;
    }
}
