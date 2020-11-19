package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefTypeService.
 */
public interface RefTypeServiceService {

    /**
     * Save a refTypeService.
     *
     * @param refTypeServiceDTO the entity to save
     * @return the persisted entity
     */
    RefTypeServiceDTO save(RefTypeServiceDTO refTypeServiceDTO);

    /**
     *  Get all the refTypeServices.
     *
     *  @return the list of entities
     */
    Set<RefTypeServiceDTO> findAll();

    /**
     *  Get all the refTypeServices.
     *
     *  @return the list of entities
     */
    Set<RefTypeServiceDTO> findAllActive();

    /**
     *  Get the "id" refTypeService.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTypeServiceDTO findOne(Long id);

    /**
     *  Delete the "id" refTypeService.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refTypeService corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypeServiceDTO> search(String query, Pageable pageable);
}
