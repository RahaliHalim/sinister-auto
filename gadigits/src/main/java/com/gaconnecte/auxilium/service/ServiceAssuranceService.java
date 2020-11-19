package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ServiceAssuranceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ServiceAssurance.
 */
public interface ServiceAssuranceService {

    /**
     * Save a serviceAssurance.
     *
     * @param serviceAssuranceDTO the entity to save
     * @return the persisted entity
     */
    ServiceAssuranceDTO save(ServiceAssuranceDTO serviceAssuranceDTO);

    /**
     *  Get all the serviceAssurances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServiceAssuranceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" serviceAssurance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ServiceAssuranceDTO findOne(Long id);

    /**
     *  Delete the "id" serviceAssurance.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the serviceAssurance corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServiceAssuranceDTO> search(String query, Pageable pageable);
}
