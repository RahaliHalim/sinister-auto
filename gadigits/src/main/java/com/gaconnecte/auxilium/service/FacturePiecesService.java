package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.FacturePiecesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FacturePieces.
 */
public interface FacturePiecesService {

    /**
     * Save a facturePieces.
     *
     * @param facturePiecesDTO the entity to save
     * @return the persisted entity
     */
    FacturePiecesDTO save(FacturePiecesDTO facturePiecesDTO);

    /**
     *  Get all the facturePieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FacturePiecesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" facturePieces.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FacturePiecesDTO findOne(Long id);

    /**
     *  Delete the "id" facturePieces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the facturePieces corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FacturePiecesDTO> search(String query, Pageable pageable);
}
