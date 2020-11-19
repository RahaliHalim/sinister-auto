package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefEtatBsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefEtatBs.
 */
public interface RefEtatBsService {

    /**
     * Save a refEtatBs.
     *
     * @param refEtatBsDTO the entity to save
     * @return the persisted entity
     */
    RefEtatBsDTO save(RefEtatBsDTO refEtatBsDTO);

    /**
     *  Get all the refEtatBs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefEtatBsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refEtatBs.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefEtatBsDTO findOne(Long id);

    /**
     *  Delete the "id" refEtatBs.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refEtatBs corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefEtatBsDTO> search(String query, Pageable pageable);
}
