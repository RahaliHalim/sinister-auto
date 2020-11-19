package com.gaconnecte.auxilium.service.mapper;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.service.dto.ProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Produit and its DTO ProduitDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProduitMapper extends EntityMapper <ProduitDTO, Produit> {
    
    Produit toEntity(ProduitDTO produitDTO); 
    default Produit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }
}
