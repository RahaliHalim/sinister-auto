package com.gaconnecte.auxilium.service.prm;

import java.util.Set;

import com.gaconnecte.auxilium.service.prm.dto.ConventionAmendmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing ConventionAmendment.
 */
public interface ConventionAmendmentService {

    /**
     * Save a conventionAmendment.
     *
     * @param conventionAmendmentDTO the entity to save
     * @return the persisted entity
     */
    ConventionAmendmentDTO save(MultipartFile signedConventionAmendment, ConventionAmendmentDTO conventionAmendmentDTO);

    /**
     * Get all the conventionAmendments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConventionAmendmentDTO> findAll(Pageable pageable);

    /**
     * Get all the conventionAmendments.
     *
     * @return the list of entities
     */
    Set<ConventionAmendmentDTO> findAll();

    /**
     * Get the "id" conventionAmendment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConventionAmendmentDTO findOne(Long id);

    /**
     *  Delete the "id" conventionAmendment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  block the "id" conventionAmendment.
     *
     *  @param id the id of the entity
     */
    void block(Long id);

    /**
     * Search for the conventionAmendment corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConventionAmendmentDTO> search(String query, Pageable pageable);
}
