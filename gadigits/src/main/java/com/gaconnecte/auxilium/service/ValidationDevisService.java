package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ValidationDevisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ValidationDevis.
 */
public interface ValidationDevisService {

    /**
     * Save a validationDevis.
     *
     * @param validationDevisDTO the entity to save
     * @return the persisted entity
     */
    ValidationDevisDTO save(ValidationDevisDTO validationDevisDTO);

    /**
     *  Get all the validationDevis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ValidationDevisDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" validationDevis.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ValidationDevisDTO findOne(Long id);

    /**
     *  Delete the "id" validationDevis.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the validationDevis corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ValidationDevisDTO> search(String query, Pageable pageable);
}
