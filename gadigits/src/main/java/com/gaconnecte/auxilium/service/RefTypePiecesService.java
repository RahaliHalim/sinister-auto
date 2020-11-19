package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTypePiecesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefTypePieces.
 */
public interface RefTypePiecesService {

    /**
     * Save a refTypePieces.
     *
     * @param refTypePiecesDTO the entity to save
     * @return the persisted entity
     */
    RefTypePiecesDTO save(RefTypePiecesDTO refTypePiecesDTO);

    /**
     *  Get all the refTypePieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypePiecesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refTypePieces.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTypePiecesDTO findOne(Long id);

    /**
     *  Delete the "id" refTypePieces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refTypePieces corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTypePiecesDTO> search(String query, Pageable pageable);
}
