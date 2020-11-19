package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ConcessionnaireDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Concessionnaire.
 */
public interface ConcessionnaireService {

    /**
     * Save a concessionnaire.
     *
     * @param concessionnaireDTO the entity to save
     * @return the persisted entity
     */
    ConcessionnaireDTO save(ConcessionnaireDTO concessionnaireDTO);

    /**
     *  Get all the concessionnaires.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConcessionnaireDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" concessionnaire.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConcessionnaireDTO findOne(Long id);

    /**
     *  Delete the "id" concessionnaire.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the concessionnaire corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConcessionnaireDTO> search(String query, Pageable pageable);
}
