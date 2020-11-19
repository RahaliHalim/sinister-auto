package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.GrilleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Grille.
 */
public interface GrilleService {

    /**
     * Save a grille.
     *
     * @param grilleDTO the entity to save
     * @return the persisted entity
     */
    GrilleDTO save(GrilleDTO grilleDTO);

    /**
     *  Get all the grilles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GrilleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" grille.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GrilleDTO findOne(Long id);

    /**
     *  Delete the "id" grille.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the grille corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GrilleDTO> search(String query, Pageable pageable);
}
