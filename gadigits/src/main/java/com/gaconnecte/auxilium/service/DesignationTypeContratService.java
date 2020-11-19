package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DesignationTypeContratDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DesignationTypeContrat.
 */
public interface DesignationTypeContratService {

    /**
     * Save a designationTypeContrat.
     *
     * @param designationTypeContratDTO the entity to save
     * @return the persisted entity
     */
    DesignationTypeContratDTO save(DesignationTypeContratDTO designationTypeContratDTO);

    /**
     *  Get all the designationTypeContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationTypeContratDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" designationTypeContrat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DesignationTypeContratDTO findOne(Long id);

    /**
     *  Delete the "id" designationTypeContrat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the designationTypeContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationTypeContratDTO> search(String query, Pageable pageable);
}
