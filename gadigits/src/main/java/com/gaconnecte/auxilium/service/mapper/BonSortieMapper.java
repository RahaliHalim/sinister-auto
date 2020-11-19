package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.BonSortieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BonSortie and its DTO BonSortieDTO.
 */
@Mapper(componentModel = "spring", uses = {RefEtatBsMapper.class, })
public interface BonSortieMapper extends EntityMapper <BonSortieDTO, BonSortie> {

    @Mapping(source = "refEtatBs.id", target = "refEtatBsId")
    BonSortieDTO toDto(BonSortie bonSortie); 

    @Mapping(source = "refEtatBsId", target = "refEtatBs")
    BonSortie toEntity(BonSortieDTO bonSortieDTO); 
    default BonSortie fromId(Long id) {
        if (id == null) {
            return null;
        }
        BonSortie bonSortie = new BonSortie();
        bonSortie.setId(id);
        return bonSortie;
    }
}
