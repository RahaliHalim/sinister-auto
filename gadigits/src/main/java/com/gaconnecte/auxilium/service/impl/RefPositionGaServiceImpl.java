package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefPositionGaService;
import com.gaconnecte.auxilium.domain.RefPositionGa;
import com.gaconnecte.auxilium.repository.RefPositionGaRepository;
import com.gaconnecte.auxilium.repository.search.RefPositionGaSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefPositionGaDTO;
import com.gaconnecte.auxilium.service.mapper.RefPositionGaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefPositionGa.
 */
@Service
@Transactional
public class RefPositionGaServiceImpl implements RefPositionGaService{

    private final Logger log = LoggerFactory.getLogger(RefPositionGaServiceImpl.class);

    private final RefPositionGaRepository refPositionGaRepository;

    private final RefPositionGaMapper refPositionGaMapper;

    private final RefPositionGaSearchRepository refPositionGaSearchRepository;

    public RefPositionGaServiceImpl(RefPositionGaRepository refPositionGaRepository, RefPositionGaMapper refPositionGaMapper, RefPositionGaSearchRepository refPositionGaSearchRepository) {
        this.refPositionGaRepository = refPositionGaRepository;
        this.refPositionGaMapper = refPositionGaMapper;
        this.refPositionGaSearchRepository = refPositionGaSearchRepository;
    }

    /**
     * Save a refPositionGa.
     *
     * @param refPositionGaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefPositionGaDTO save(RefPositionGaDTO refPositionGaDTO) {
        log.debug("Request to save RefPositionGa : {}", refPositionGaDTO);
        RefPositionGa refPositionGa = refPositionGaMapper.toEntity(refPositionGaDTO);
        refPositionGa = refPositionGaRepository.save(refPositionGa);
        RefPositionGaDTO result = refPositionGaMapper.toDto(refPositionGa);
        refPositionGaSearchRepository.save(refPositionGa);
        return result;
    }

    /**
     *  Get all the refPositionGas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefPositionGaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefPositionGas");
        return refPositionGaRepository.findAll(pageable)
            .map(refPositionGaMapper::toDto);
    }

    /**
     *  Get one refPositionGa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefPositionGaDTO findOne(Long id) {
        log.debug("Request to get RefPositionGa : {}", id);
        RefPositionGa refPositionGa = refPositionGaRepository.findOne(id);
        return refPositionGaMapper.toDto(refPositionGa);
    }

    /**
     *  Delete the  refPositionGa by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefPositionGa : {}", id);
        refPositionGaRepository.delete(id);
        refPositionGaSearchRepository.delete(id);
    }

    /**
     * Search for the refPositionGa corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefPositionGaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefPositionGas for query {}", query);
        Page<RefPositionGa> result = refPositionGaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refPositionGaMapper::toDto);
    }
}
