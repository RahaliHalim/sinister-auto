package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefMaterielDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefMateriel.
 */
public interface RefMaterielService {

    /**
     * Save a refMateriel.
     *
     * @param refMaterielDTO the entity to save
     * @return the persisted entity
     */
    RefMaterielDTO save(RefMaterielDTO refMaterielDTO);

    /**
     *  Get all the refMateriels.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefMaterielDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refMateriel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefMaterielDTO findOne(Long id);

    /**
     *  Delete the "id" refMateriel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refMateriel corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefMaterielDTO> search(String query, Pageable pageable);
}
