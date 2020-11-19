package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTypePjService;
import com.gaconnecte.auxilium.domain.RefTypePj;
import com.gaconnecte.auxilium.repository.RefTypePjRepository;
import com.gaconnecte.auxilium.repository.search.RefTypePjSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypePjDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypePjMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefTypePj.
 */
@Service
@Transactional
public class RefTypePjServiceImpl implements RefTypePjService{

    private final Logger log = LoggerFactory.getLogger(RefTypePjServiceImpl.class);

    private final RefTypePjRepository refTypePjRepository;

    private final RefTypePjMapper refTypePjMapper;

    private final RefTypePjSearchRepository refTypePjSearchRepository;

    public RefTypePjServiceImpl(RefTypePjRepository refTypePjRepository, RefTypePjMapper refTypePjMapper, RefTypePjSearchRepository refTypePjSearchRepository) {
        this.refTypePjRepository = refTypePjRepository;
        this.refTypePjMapper = refTypePjMapper;
        this.refTypePjSearchRepository = refTypePjSearchRepository;
    }

    /**
     * Save a refTypePj.
     *
     * @param refTypePjDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTypePjDTO save(RefTypePjDTO refTypePjDTO) {
        log.debug("Request to save RefTypePj : {}", refTypePjDTO);
        RefTypePj refTypePj = refTypePjMapper.toEntity(refTypePjDTO);
        refTypePj = refTypePjRepository.save(refTypePj);
        RefTypePjDTO result = refTypePjMapper.toDto(refTypePj);
        refTypePjSearchRepository.save(refTypePj);
        return result;
    }

    /**
     *  Get all the refTypePjs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypePjDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefTypePjs");
        return refTypePjRepository.findAll(pageable)
            .map(refTypePjMapper::toDto);
    }

    /**
     *  Get one refTypePj by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypePjDTO findOne(Long id) {
        log.debug("Request to get RefTypePj : {}", id);
        RefTypePj refTypePj = refTypePjRepository.findOne(id);
        return refTypePjMapper.toDto(refTypePj);
    }

    /**
     *  Delete the  refTypePj by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTypePj : {}", id);
        refTypePjRepository.delete(id);
        refTypePjSearchRepository.delete(id);
    }

    /**
     * Search for the refTypePj corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypePjDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefTypePjs for query {}", query);
        Page<RefTypePj> result = refTypePjSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refTypePjMapper::toDto);
    }
}
