package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Service Interface for managing RefModeGestion.
 */
public interface RefModeGestionService {

    /**
     * Save a refModeGestion.
     *
     * @param refModeGestionDTO the entity to save
     * @return the persisted entity
     */
    RefModeGestionDTO save(RefModeGestionDTO refModeGestionDTO);

    /**
     *  Get all the refModeGestions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefModeGestionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refModeGestion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefModeGestionDTO findOne(Long id);

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
    Page<RefModeGestionDTO> search(String query, Pageable pageable);

    /**
     * Get list of mode gestion from garanties list
     * @param clientId
     * @return the list of mode de gestion
     */
    Set<RefModeGestionDTO> findModeGestionListByClient(Long clientId);

}
