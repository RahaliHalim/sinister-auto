package com.gaconnecte.auxilium.service.prm.mapper;

import org.mapstruct.Mapper;

import com.gaconnecte.auxilium.domain.prm.Rules;
import com.gaconnecte.auxilium.service.mapper.EntityMapper;
import com.gaconnecte.auxilium.service.prm.dto.RulesDTO;

@Mapper(componentModel = "spring", uses = {})
public interface RulesMapper extends EntityMapper <RulesDTO, Rules> {

    RulesDTO toDto(Rules rules);

    Rules toEntity(RulesDTO rulesDTO);

    default Rules fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rules rules = new Rules();
        rules.setId(id);
        return rules;
    }
}
