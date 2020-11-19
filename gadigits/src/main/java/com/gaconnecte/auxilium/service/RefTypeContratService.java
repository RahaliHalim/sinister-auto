package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTypeContratDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefTypeContrat.
 */
public interface RefTypeContratService {

    /**
     * Save a refTypeContrat.
     *
     * @param refTypeContratDTO the entity to save
     * @return the persisted entity
     */
    RefTypeContratDTO save(RefTypeContratDTO refTypeContratDTO);

    /**
     *  Get all the refTypeContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypeContratDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refTypeContrat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTypeContratDTO findOne(Long id);

    /**
     *  Delete the "id" refTypeContrat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refTypeContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypeContratDTO> search(String query, Pageable pageable);
}
