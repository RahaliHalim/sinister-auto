package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefMotifDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
/**
 * Service Interface for managing RefMotif.
 */
public interface RefMotifService {

    /**
     * Save a refMotif.
     *
     * @param refMotifDTO the entity to save
     * @return the persisted entity
     */
    RefMotifDTO save(RefMotifDTO refMotifDTO);

    /**
     *  Get all the refMotifs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefMotifDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refMotif.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefMotifDTO findOne(Long id);

    /**
     *  Delete the "id" refMotif.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refMotif corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefMotifDTO> search(String query, Pageable pageable);

    Set<RefMotifDTO> findAllMotifsByTypeAndEtat(String type, String etat);
}
