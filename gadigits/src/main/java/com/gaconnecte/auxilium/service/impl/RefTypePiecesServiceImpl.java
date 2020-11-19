package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTypePiecesService;
import com.gaconnecte.auxilium.domain.RefTypePieces;
import com.gaconnecte.auxilium.repository.RefTypePiecesRepository;
import com.gaconnecte.auxilium.repository.search.RefTypePiecesSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypePiecesDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypePiecesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefTypePieces.
 */
@Service
@Transactional
public class RefTypePiecesServiceImpl implements RefTypePiecesService{

    private final Logger log = LoggerFactory.getLogger(RefTypePiecesServiceImpl.class);

    private final RefTypePiecesRepository refTypePiecesRepository;

    private final RefTypePiecesMapper refTypePiecesMapper;

    private final RefTypePiecesSearchRepository refTypePiecesSearchRepository;

    public RefTypePiecesServiceImpl(RefTypePiecesRepository refTypePiecesRepository, RefTypePiecesMapper refTypePiecesMapper, RefTypePiecesSearchRepository refTypePiecesSearchRepository) {
        this.refTypePiecesRepository = refTypePiecesRepository;
        this.refTypePiecesMapper = refTypePiecesMapper;
        this.refTypePiecesSearchRepository = refTypePiecesSearchRepository;
    }

    /**
     * Save a refTypePieces.
     *
     * @param refTypePiecesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTypePiecesDTO save(RefTypePiecesDTO refTypePiecesDTO) {
        log.debug("Request to save RefTypePieces : {}", refTypePiecesDTO);
        RefTypePieces refTypePieces = refTypePiecesMapper.toEntity(refTypePiecesDTO);
        refTypePieces = refTypePiecesRepository.save(refTypePieces);
        RefTypePiecesDTO result = refTypePiecesMapper.toDto(refTypePieces);
        refTypePiecesSearchRepository.save(refTypePieces);
        return result;
    }

    /**
     *  Get all the refTypePieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypePiecesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefTypePieces");
        return refTypePiecesRepository.findAll(pageable)
            .map(refTypePiecesMapper::toDto);
    }

    /**
     *  Get one refTypePieces by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypePiecesDTO findOne(Long id) {
        log.debug("Request to get RefTypePieces : {}", id);
        RefTypePieces refTypePieces = refTypePiecesRepository.findOne(id);
        return refTypePiecesMapper.toDto(refTypePieces);
    }

    /**
     *  Delete the  refTypePieces by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTypePieces : {}", id);
        refTypePiecesRepository.delete(id);
        refTypePiecesSearchRepository.delete(id);
    }

    /**
     * Search for the refTypePieces corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypePiecesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefTypePieces for query {}", query);
        Page<RefTypePieces> result = refTypePiecesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refTypePiecesMapper::toDto);
    }
}
