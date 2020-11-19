package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.FactureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Facture and its DTO FactureDTO.
 */
@Mapper(componentModel = "spring", uses = {DevisMapper.class, BonSortieMapper.class, PrestationPECMapper.class })
public interface FactureMapper extends EntityMapper <FactureDTO, Facture> {

    @Mapping(source = "devis.id", target = "devisId")

    @Mapping(source = "bonSortie.id", target = "bonSortieId")
    @Mapping(source = "bonSortie.numero", target = "bonSortieNumero")

   @Mapping(source = "dossier.id", target = "dossierId")
    FactureDTO toDto(Facture facture); 

    @Mapping(source = "devisId", target = "devis")

    @Mapping(source = "bonSortieId", target = "bonSortie")

    @Mapping(source = "dossierId", target = "dossier")
    @Mapping(target = "factureMos", ignore = true)
    @Mapping(target = "factPieces", ignore = true)
    Facture toEntity(FactureDTO factureDTO); 
    default Facture fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facture facture = new Facture();
        facture.setId(id);
        return facture;
    }
}
