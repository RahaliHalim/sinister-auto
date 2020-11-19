package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.AuthorityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assure and its DTO AssureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper extends EntityMapper <AuthorityDTO, Authority> {


    AuthorityDTO toDto(Authority authority);

    @Mapping(target = "liens", ignore = true)
    @Mapping(target = "usersCellules", ignore = true)
    Authority toEntity(AuthorityDTO authorityDTO);
    default Authority fromName(String name) {
        if (name == null) {
            return null;
        }
        Authority authority = new Authority();
        authority.setName(name);
        return authority;
    }
}
