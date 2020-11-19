package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.AvisExpertPieceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AvisExpertPiece.
 */
public interface AvisExpertPieceService {

    /**
     * Save a avisExpertPiece.
     *
     * @param avisExpertPieceDTO the entity to save
     * @return the persisted entity
     */
    AvisExpertPieceDTO save(AvisExpertPieceDTO avisExpertPieceDTO);

    /**
     *  Get all the avisExpertPieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvisExpertPieceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" avisExpertPiece.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvisExpertPieceDTO findOne(Long id);

    /**
     *  Delete the "id" avisExpertPiece.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the avisExpertPiece corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvisExpertPieceDTO> search(String query, Pageable pageable);
}
