package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefModeReglementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefModeReglement.
 */
public interface RefModeReglementService {

    /**
     * Save a refModeReglement.
     *
     * @param refModeReglementDTO the entity to save
     * @return the persisted entity
     */
    RefModeReglementDTO save(RefModeReglementDTO refModeReglementDTO);

    /**
     *  Get all the refModeReglements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefModeReglementDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refModeReglement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefModeReglementDTO findOne(Long id);

    /**
     *  Delete the "id" refModeReglement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refModeReglement corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefModeReglementDTO> search(String query, Pageable pageable);
}
