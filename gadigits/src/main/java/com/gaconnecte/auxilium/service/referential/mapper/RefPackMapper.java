package com.gaconnecte.auxilium.service.referential.mapper;

import com.gaconnecte.auxilium.domain.referential.RefPack;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
import com.gaconnecte.auxilium.service.mapper.RefExclusionMapper;
import com.gaconnecte.auxilium.service.mapper.RefModeGestionMapper;
import com.gaconnecte.auxilium.service.mapper.RefTypeServiceMapper;
import com.gaconnecte.auxilium.service.mapper.ReinsurerMapper;
import com.gaconnecte.auxilium.service.mapper.VehicleUsageMapper;
import com.gaconnecte.auxilium.service.prm.mapper.ApecSettingsMapper;
import com.gaconnecte.auxilium.service.prm.mapper.ConventionMapper;
import com.gaconnecte.auxilium.service.prm.mapper.SinisterTypeSettingMapper;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity RefPack and its DTO RefPackDTO.
 */
@Mapper(componentModel = "spring", uses = { ApecSettingsMapper.class, PartnerMapper.class, RefTypeServiceMapper.class,
        RefModeGestionMapper.class, VehicleUsageMapper.class, RefExclusionMapper.class, SinisterTypeSettingMapper.class,
        ConventionMapper.class, ReinsurerMapper.class })
public interface RefPackMapper extends EntityMapper<RefPackDTO, RefPack> {

    @Mapping(source = "reinsurer.id", target = "reinsurerId")
    @Mapping(source = "reinsurer.companyName", target = "reinsurerName")
    @Mapping(source = "convention.id", target = "conventionId")
    RefPackDTO toDto(RefPack refPack);

    @Mapping(source = "reinsurerId", target = "reinsurer")
    RefPack toEntity(RefPackDTO refPackDTO);

    default RefPack fromId(Long id) {
        if (id == null) {
            return null;
        }
        RefPack refPack = new RefPack();
        refPack.setId(id);
        return refPack;
    }
}
