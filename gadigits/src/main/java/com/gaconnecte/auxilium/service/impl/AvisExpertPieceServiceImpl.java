package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.AvisExpertPieceService;
import com.gaconnecte.auxilium.domain.AvisExpertPiece;
import com.gaconnecte.auxilium.repository.AvisExpertPieceRepository;
import com.gaconnecte.auxilium.repository.search.AvisExpertPieceSearchRepository;
import com.gaconnecte.auxilium.service.dto.AvisExpertPieceDTO;
import com.gaconnecte.auxilium.service.mapper.AvisExpertPieceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AvisExpertPiece.
 */
@Service
@Transactional
public class AvisExpertPieceServiceImpl implements AvisExpertPieceService{

    private final Logger log = LoggerFactory.getLogger(AvisExpertPieceServiceImpl.class);

    private final AvisExpertPieceRepository avisExpertPieceRepository;

    private final AvisExpertPieceMapper avisExpertPieceMapper;

    private final AvisExpertPieceSearchRepository avisExpertPieceSearchRepository;

    public AvisExpertPieceServiceImpl(AvisExpertPieceRepository avisExpertPieceRepository, AvisExpertPieceMapper avisExpertPieceMapper, AvisExpertPieceSearchRepository avisExpertPieceSearchRepository) {
        this.avisExpertPieceRepository = avisExpertPieceRepository;
        this.avisExpertPieceMapper = avisExpertPieceMapper;
        this.avisExpertPieceSearchRepository = avisExpertPieceSearchRepository;
    }

    /**
     * Save a avisExpertPiece.
     *
     * @param avisExpertPieceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AvisExpertPieceDTO save(AvisExpertPieceDTO avisExpertPieceDTO) {
        log.debug("Request to save AvisExpertPiece : {}", avisExpertPieceDTO);
        AvisExpertPiece avisExpertPiece = avisExpertPieceMapper.toEntity(avisExpertPieceDTO);
        avisExpertPiece = avisExpertPieceRepository.save(avisExpertPiece);
        AvisExpertPieceDTO result = avisExpertPieceMapper.toDto(avisExpertPiece);
        avisExpertPieceSearchRepository.save(avisExpertPiece);
        return result;
    }

    /**
     *  Get all the avisExpertPieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvisExpertPieceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AvisExpertPieces");
        return avisExpertPieceRepository.findAll(pageable)
            .map(avisExpertPieceMapper::toDto);
    }

    /**
     *  Get one avisExpertPiece by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AvisExpertPieceDTO findOne(Long id) {
        log.debug("Request to get AvisExpertPiece : {}", id);
        AvisExpertPiece avisExpertPiece = avisExpertPieceRepository.findOne(id);
        return avisExpertPieceMapper.toDto(avisExpertPiece);
    }

    /**
     *  Delete the  avisExpertPiece by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AvisExpertPiece : {}", id);
        avisExpertPieceRepository.delete(id);
        avisExpertPieceSearchRepository.delete(id);
    }

    /**
     * Search for the avisExpertPiece corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AvisExpertPieceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AvisExpertPieces for query {}", query);
        Page<AvisExpertPiece> result = avisExpertPieceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(avisExpertPieceMapper::toDto);
    }
}
