package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.AssureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Assure.
 */
public interface AssureService {

    /**
     * Save a assure.
     *
     * @param assureDTO the entity to save
     * @return the persisted entity
     */
    AssureDTO save(AssureDTO assureDTO);

    /**
     *  Get all the assures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AssureDTO> findAll(Pageable pageable);
    
 
  

    /**
     *  Get the "id" assure.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AssureDTO findOne(Long id);

    /**
     *  Delete the "id" assure.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the assure corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AssureDTO> search(String query, Pageable pageable);

    /**
     * Count the number of assure
     * @return the number of assure
     */
    Long getCountAssure(String debut, String fin);
}