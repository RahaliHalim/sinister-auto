package com.gaconnecte.auxilium.service.prm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.prm.SinisterTypeSetting;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.mapper.RefModeGestionMapper;
import com.gaconnecte.auxilium.service.prm.dto.SinisterTypeSettingDTO;

/**
 * Mapper for the entity SinisterTypeSetting and its DTO SinisterTypeSettingDTO.
 */
@Mapper(componentModel = "spring", uses = {RefModeGestionMapper.class,})
public interface SinisterTypeSettingMapper extends EntityMapper <SinisterTypeSettingDTO, SinisterTypeSetting> {

    @Mapping(source = "sinisterType.id", target = "sinisterTypeId")
    @Mapping(source = "sinisterType.libelle", target = "sinisterTypeLabel")

    SinisterTypeSettingDTO toDto(SinisterTypeSetting sinisterTypeSetting);

    @Mapping(source = "sinisterTypeId", target = "sinisterType")

    SinisterTypeSetting toEntity(SinisterTypeSettingDTO sinisterTypeSettingDTO);

    default SinisterTypeSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        SinisterTypeSetting sinisterTypeSetting = new SinisterTypeSetting();
        sinisterTypeSetting.setId(id);
        return sinisterTypeSetting;
    }
}
