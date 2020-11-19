package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefFractionnementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefFractionnement.
 */
public interface RefFractionnementService {

    /**
     * Save a refFractionnement.
     *
     * @param refFractionnementDTO the entity to save
     * @return the persisted entity
     */
    RefFractionnementDTO save(RefFractionnementDTO refFractionnementDTO);

    /**
     *  Get all the refFractionnements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefFractionnementDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refFractionnement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefFractionnementDTO findOne(Long id);

    /**
     *  Delete the "id" refFractionnement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refFractionnement corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefFractionnementDTO> search(String query, Pageable pageable);
}
