package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.Lien;
import com.gaconnecte.auxilium.service.dto.LienDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Lien and its DTO LienDTO.
 */
@Mapper(componentModel = "spring", uses = { CelluleMapper.class, AuthorityMapper.class, })
public interface LienMapper extends EntityMapper<LienDTO, Lien> {

    @Mapping(source = "parent.id", target = "parentId")
    LienDTO toDto(Lien lien);

    @Mapping(source = "parentId", target = "parent")
    Lien toEntity(LienDTO lienDTO);

    default Lien fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lien lien = new Lien();
        lien.setId(id);
        return lien;
    }
}