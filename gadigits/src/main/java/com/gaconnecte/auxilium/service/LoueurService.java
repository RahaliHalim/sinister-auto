package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.LoueurDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Loueur.
 */
public interface LoueurService {

    /**
     * Save a loueur.
     *
     * @param loueurDTO the entity to save
     * @return the persisted entity
     */
    LoueurDTO save(LoueurDTO loueurDTO);

    /**
     *  Get all the loueurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LoueurDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" loueur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LoueurDTO findOne(Long id);
    List<LoueurDTO> findByGovernorate(Long id);


    /**
     *  Delete the "id" loueur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the loueur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LoueurDTO> search(String query, Pageable pageable);
}
