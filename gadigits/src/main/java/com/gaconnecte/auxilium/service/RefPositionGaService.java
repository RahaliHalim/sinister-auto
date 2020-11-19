package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefPositionGaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefPositionGa.
 */
public interface RefPositionGaService {

    /**
     * Save a refPositionGa.
     *
     * @param refPositionGaDTO the entity to save
     * @return the persisted entity
     */
    RefPositionGaDTO save(RefPositionGaDTO refPositionGaDTO);

    /**
     *  Get all the refPositionGas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefPositionGaDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refPositionGa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefPositionGaDTO findOne(Long id);

    /**
     *  Delete the "id" refPositionGa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refPositionGa corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefPositionGaDTO> search(String query, Pageable pageable);
}
