package com.gaconnecte.auxilium.service.prm.mapper;

import com.gaconnecte.auxilium.domain.prm.ApecSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.mapper.RefModeGestionMapper;
import com.gaconnecte.auxilium.service.prm.dto.ApecSettingsDTO;

/**
 * Mapper for the entity ApecSettings and its DTO ApecSettingsDTO.
 */
@Mapper(componentModel = "spring", uses = {RefModeGestionMapper.class,})
public interface ApecSettingsMapper extends EntityMapper <ApecSettingsDTO, ApecSettings> {

    @Mapping(source = "mgntMode.id", target = "mgntModeId")
    @Mapping(source = "mgntMode.libelle", target = "mgntModeLabel")
    ApecSettingsDTO toDto(ApecSettings apecSettings);

    @Mapping(source = "mgntModeId", target = "mgntMode")

    ApecSettings toEntity(ApecSettingsDTO apecSettingsDTO);

    default ApecSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApecSettings apecSettings = new ApecSettings();
        apecSettings.setId(id);
        return apecSettings;
    }
}
