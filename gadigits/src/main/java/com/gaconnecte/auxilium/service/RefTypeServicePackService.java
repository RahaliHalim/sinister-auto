package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServicePackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefTypeService.
 */
public interface RefTypeServicePackService {

    /**
     * Save a refTypeService.
     *
     * @param refTypeServiceDTO the entity to save
     * @return the persisted entity
     */
	RefTypeServicePackDTO save(RefTypeServicePackDTO refTypeServicePackDTO);

    /**
     *  Get all the refTypeServices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypeServicePackDTO> findPacksByRefTypeService(Pageable pageable,Long id);
    Page<RefTypeServicePackDTO> findAll(Pageable pageable);
    /**
     *  Get the "id" refTypeService.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTypeServicePackDTO findOne(Long id);

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
    RefTypeServicePackDTO findByTypeService( Long id);
    
    Page<RefTypeServicePackDTO> search(String query, Pageable pageable);
}
