package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DesignationFractionnementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DesignationFractionnement.
 */
public interface DesignationFractionnementService {

    /**
     * Save a designationFractionnement.
     *
     * @param designationFractionnementDTO the entity to save
     * @return the persisted entity
     */
    DesignationFractionnementDTO save(DesignationFractionnementDTO designationFractionnementDTO);

    /**
     *  Get all the designationFractionnements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationFractionnementDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" designationFractionnement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DesignationFractionnementDTO findOne(Long id);

    /**
     *  Delete the "id" designationFractionnement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the designationFractionnement corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationFractionnementDTO> search(String query, Pageable pageable);
}
