package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefNatureExpertiseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefNatureExpertise.
 */
public interface RefNatureExpertiseService {

    /**
     * Save a refNatureExpertise.
     *
     * @param refNatureExpertiseDTO the entity to save
     * @return the persisted entity
     */
    RefNatureExpertiseDTO save(RefNatureExpertiseDTO refNatureExpertiseDTO);

    /**
     *  Get all the refNatureExpertises.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefNatureExpertiseDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refNatureExpertise.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefNatureExpertiseDTO findOne(Long id);

    /**
     *  Delete the "id" refNatureExpertise.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refNatureExpertise corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefNatureExpertiseDTO> search(String query, Pageable pageable);
}
