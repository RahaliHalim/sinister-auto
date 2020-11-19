package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.FactureDetailsMoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FactureDetailsMo.
 */
public interface FactureDetailsMoService {

    /**
     * Save a factureDetailsMo.
     *
     * @param factureDetailsMoDTO the entity to save
     * @return the persisted entity
     */
    FactureDetailsMoDTO save(FactureDetailsMoDTO factureDetailsMoDTO);

    /**
     *  Get all the factureDetailsMos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FactureDetailsMoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" factureDetailsMo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FactureDetailsMoDTO findOne(Long id);

    /**
     *  Delete the "id" factureDetailsMo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the factureDetailsMo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FactureDetailsMoDTO> search(String query, Pageable pageable);
}
