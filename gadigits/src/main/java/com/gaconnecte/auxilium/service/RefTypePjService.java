package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTypePjDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefTypePj.
 */
public interface RefTypePjService {

    /**
     * Save a refTypePj.
     *
     * @param refTypePjDTO the entity to save
     * @return the persisted entity
     */
    RefTypePjDTO save(RefTypePjDTO refTypePjDTO);

    /**
     *  Get all the refTypePjs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypePjDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refTypePj.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTypePjDTO findOne(Long id);

    /**
     *  Delete the "id" refTypePj.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refTypePj corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypePjDTO> search(String query, Pageable pageable);
}
