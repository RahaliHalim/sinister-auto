package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.FacturePiecesService;
import com.gaconnecte.auxilium.domain.FacturePieces;
import com.gaconnecte.auxilium.repository.FacturePiecesRepository;
import com.gaconnecte.auxilium.repository.search.FacturePiecesSearchRepository;
import com.gaconnecte.auxilium.service.dto.FacturePiecesDTO;
import com.gaconnecte.auxilium.service.mapper.FacturePiecesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FacturePieces.
 */
@Service
@Transactional
public class FacturePiecesServiceImpl implements FacturePiecesService{

    private final Logger log = LoggerFactory.getLogger(FacturePiecesServiceImpl.class);

    private final FacturePiecesRepository facturePiecesRepository;

    private final FacturePiecesMapper facturePiecesMapper;

    private final FacturePiecesSearchRepository facturePiecesSearchRepository;

    public FacturePiecesServiceImpl(FacturePiecesRepository facturePiecesRepository, FacturePiecesMapper facturePiecesMapper, FacturePiecesSearchRepository facturePiecesSearchRepository) {
        this.facturePiecesRepository = facturePiecesRepository;
        this.facturePiecesMapper = facturePiecesMapper;
        this.facturePiecesSearchRepository = facturePiecesSearchRepository;
    }

    /**
     * Save a facturePieces.
     *
     * @param facturePiecesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FacturePiecesDTO save(FacturePiecesDTO facturePiecesDTO) {
        log.debug("Request to save FacturePieces : {}", facturePiecesDTO);
        FacturePieces facturePieces = facturePiecesMapper.toEntity(facturePiecesDTO);
        facturePieces = facturePiecesRepository.save(facturePieces);
        FacturePiecesDTO result = facturePiecesMapper.toDto(facturePieces);
        facturePiecesSearchRepository.save(facturePieces);
        return result;
    }

    /**
     *  Get all the facturePieces.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturePiecesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FacturePieces");
        return facturePiecesRepository.findAll(pageable)
            .map(facturePiecesMapper::toDto);
    }

    /**
     *  Get one facturePieces by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FacturePiecesDTO findOne(Long id) {
        log.debug("Request to get FacturePieces : {}", id);
        FacturePieces facturePieces = facturePiecesRepository.findOne(id);
        return facturePiecesMapper.toDto(facturePieces);
    }

    /**
     *  Delete the  facturePieces by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FacturePieces : {}", id);
        facturePiecesRepository.delete(id);
        facturePiecesSearchRepository.delete(id);
    }

    /**
     * Search for the facturePieces corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacturePiecesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FacturePieces for query {}", query);
        Page<FacturePieces> result = facturePiecesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(facturePiecesMapper::toDto);
    }
}
