package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefNatureContratDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefNatureContrat.
 */
public interface RefNatureContratService {

    /**
     * Save a refNatureContrat.
     *
     * @param refNatureContratDTO the entity to save
     * @return the persisted entity
     */
    RefNatureContratDTO save(RefNatureContratDTO refNatureContratDTO);

    /**
     *  Get all the refNatureContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefNatureContratDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refNatureContrat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefNatureContratDTO findOne(Long id);

    /**
     *  Delete the "id" refNatureContrat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refNatureContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefNatureContratDTO> search(String query, Pageable pageable);
}
