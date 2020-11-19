package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.PointChocDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PointChoc.
 */
public interface PointChocService {

    /**
     * Save a pointChoc.
     *
     * @param pointChocDTO the entity to save
     * @return the persisted entity
     */
    PointChocDTO save(PointChocDTO pointChocDTO);

    /**
     *  Get all the pointChocs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PointChocDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pointChoc.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PointChocDTO findOne(Long id);

    /**
     *  Delete the "id" pointChoc.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pointChoc corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PointChocDTO> search(String query, Pageable pageable);
}
