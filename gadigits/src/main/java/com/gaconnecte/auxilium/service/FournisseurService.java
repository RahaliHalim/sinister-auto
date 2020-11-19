package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.FournisseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Fournisseur.
 */
public interface FournisseurService {

    /**
     * Save a fournisseur.
     *
     * @param fournisseurDTO the entity to save
     * @return the persisted entity
     */
    FournisseurDTO save(FournisseurDTO fournisseurDTO);

    /**
     *  Get all the fournisseurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FournisseurDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" fournisseur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FournisseurDTO findOne(Long id);

    /**
     *  Delete the "id" fournisseur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fournisseur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FournisseurDTO> search(String query, Pageable pageable);
}
