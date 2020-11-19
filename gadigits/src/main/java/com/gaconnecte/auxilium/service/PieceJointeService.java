package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.PieceJointeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gaconnecte.auxilium.domain.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing PieceJointe.
 */
public interface PieceJointeService {

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointeDTO the entity to save
     * @return the persisted entity
     */
    PieceJointeDTO save(PieceJointeDTO pieceJointeDTO);
    PieceJointeDTO savePieceJointeSignature(PieceJointeDTO pieceJointeDTO);

    /**
     *  Get all the pieceJointes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PieceJointeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pieceJointe.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PieceJointeDTO findOne(Long id);

    /**
     *  Delete the "id" pieceJointe.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pieceJointe corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PieceJointeDTO> search(String query, Pageable pageable);

    Page<PieceJointeDTO> findByPrestation(Pageable pageable, Long prestationId);

    Page<PieceJointeDTO> findByDevis(Pageable pageable, Long devisId);

    Result affectChemin();
    
    Result affectCheminPdfReport();

    PieceJointeDTO uploadChemin(Long id, MultipartFile file);

    void createChemin(MultipartFile file);

    
    


}
