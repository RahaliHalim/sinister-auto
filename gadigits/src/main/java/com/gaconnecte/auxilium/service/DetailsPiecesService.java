package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DetailsPieces.
 */
public interface DetailsPiecesService {

    /**
     * Save a detailsPieces.
     *
     * @param detailsPiecesDTO the entity to save
     * @return the persisted entity
     */
    DetailsPiecesDTO save(DetailsPiecesDTO detailsPiecesDTO);

    /**
     *  Get all the detailsPieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DetailsPiecesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" detailsPieces.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DetailsPiecesDTO findOne(Long id);
    /**
     *  Get the "id" detailsPiecesModified.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DetailsPiecesDTO findLineModified(Long quotationId,Long LineModified);
    
    /**
     *  Delete the "id" detailsPieces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the detailsPieces corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DetailsPiecesDTO> search(String query, Pageable pageable);

    List<DetailsPiecesDTO> findAllByDevisAndType(Long devisId, Long typeId, Boolean isMo);
    List<DetailsPiecesDTO> findAllByDevisAndTypeOther(Long devisId, Long typeId, Boolean isMo);

    
    List<DetailsPiecesDTO> findAllByQuotationMPAndType(Long devisId, Long typeId, Boolean isMo);
    
    public Boolean findAllByDevis(Long devisId);
    
    public void deleteByDevis(Long id);
    
    public void deleteByDevisComp(Long id);
    
    List<DetailsPiecesDTO> findDetailsPiecesBySinisterPecId(Long sinisterPecId);
    
    void deleteDetailsPiecesDevisAnnule(Long sinisterPecId);
    
    DetailsPiecesDTO fusion(Long primaryQuotationId, Long sinisterPecId);
  
}
