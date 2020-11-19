package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DesignationNatureContratDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DesignationNatureContrat.
 */
public interface DesignationNatureContratService {

    /**
     * Save a designationNatureContrat.
     *
     * @param designationNatureContratDTO the entity to save
     * @return the persisted entity
     */
    DesignationNatureContratDTO save(DesignationNatureContratDTO designationNatureContratDTO);

    /**
     *  Get all the designationNatureContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationNatureContratDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" designationNatureContrat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DesignationNatureContratDTO findOne(Long id);

    /**
     *  Delete the "id" designationNatureContrat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the designationNatureContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationNatureContratDTO> search(String query, Pageable pageable);
}
