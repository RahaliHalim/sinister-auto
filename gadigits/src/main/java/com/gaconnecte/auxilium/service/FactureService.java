package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.FactureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Facture.
 */
public interface FactureService {

    /**
     * Save a facture.
     *
     * @param factureDTO the entity to save
     * @return the persisted entity
     */
    FactureDTO save(FactureDTO factureDTO);

    /**
     *  Get all the factures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FactureDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" facture.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FactureDTO findOne(Long id);

    /**
     *  Delete the "id" facture.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the facture corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FactureDTO> search(String query, Pageable pageable);
}
