package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTypeInterventionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;

/**
 * Service Interface for managing RefTypeIntervention.
 */
public interface RefTypeInterventionService {

    /**
     * Save a refTypeIntervention.
     *
     * @param refTypeInterventionDTO the entity to save
     * @return the persisted entity
     */
    RefTypeInterventionDTO save(RefTypeInterventionDTO refTypeInterventionDTO);

    /**
     *  Get all the refTypeInterventions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypeInterventionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refTypeIntervention.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTypeInterventionDTO findOne(Long id);

    /**
     *  Delete the "id" refTypeIntervention.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refTypeIntervention corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypeInterventionDTO> search(String query, Pageable pageable);


    Set<RefTypeInterventionDTO> findByType(Integer type);
}
