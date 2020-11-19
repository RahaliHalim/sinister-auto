package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefExclusionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Service Interface for managing RefModeGestion.
 */
public interface RefExclusionService {

    /**
     * Save a refModeGestion.
     *
     * @param refModeGestionDTO the entity to save
     * @return the persisted entity
     */
	RefExclusionDTO save(RefExclusionDTO refRefExclusionDTO);

    /** 
     *  Get all the refModeGestions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefExclusionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refModeGestion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefExclusionDTO findOne(Long id);

    /**
     *  Delete the "id" refModeGestion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refModeGestion corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefExclusionDTO> search(String query, Pageable pageable);

    /**
     * Get list of mode gestion from garanties list
     * @param clientId
     * @return the list of mode de gestion
     */
    

}
