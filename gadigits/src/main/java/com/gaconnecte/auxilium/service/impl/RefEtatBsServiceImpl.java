package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefEtatBsService;
import com.gaconnecte.auxilium.domain.RefEtatBs;
import com.gaconnecte.auxilium.repository.RefEtatBsRepository;
import com.gaconnecte.auxilium.repository.search.RefEtatBsSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefEtatBsDTO;
import com.gaconnecte.auxilium.service.mapper.RefEtatBsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefEtatBs.
 */
@Service
@Transactional
public class RefEtatBsServiceImpl implements RefEtatBsService{

    private final Logger log = LoggerFactory.getLogger(RefEtatBsServiceImpl.class);

    private final RefEtatBsRepository refEtatBsRepository;

    private final RefEtatBsMapper refEtatBsMapper;

    private final RefEtatBsSearchRepository refEtatBsSearchRepository;

    public RefEtatBsServiceImpl(RefEtatBsRepository refEtatBsRepository, RefEtatBsMapper refEtatBsMapper, RefEtatBsSearchRepository refEtatBsSearchRepository) {
        this.refEtatBsRepository = refEtatBsRepository;
        this.refEtatBsMapper = refEtatBsMapper;
        this.refEtatBsSearchRepository = refEtatBsSearchRepository;
    }

    /**
     * Save a refEtatBs.
     *
     * @param refEtatBsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefEtatBsDTO save(RefEtatBsDTO refEtatBsDTO) {
        log.debug("Request to save RefEtatBs : {}", refEtatBsDTO);
        RefEtatBs refEtatBs = refEtatBsMapper.toEntity(refEtatBsDTO);
        refEtatBs = refEtatBsRepository.save(refEtatBs);
        RefEtatBsDTO result = refEtatBsMapper.toDto(refEtatBs);
        refEtatBsSearchRepository.save(refEtatBs);
        return result;
    }

    /**
     *  Get all the refEtatBs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefEtatBsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefEtatBs");
        return refEtatBsRepository.findAll(pageable)
            .map(refEtatBsMapper::toDto);
    }

    /**
     *  Get one refEtatBs by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefEtatBsDTO findOne(Long id) {
        log.debug("Request to get RefEtatBs : {}", id);
        RefEtatBs refEtatBs = refEtatBsRepository.findOne(id);
        return refEtatBsMapper.toDto(refEtatBs);
    }

    /**
     *  Delete the  refEtatBs by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefEtatBs : {}", id);
        refEtatBsRepository.delete(id);
        refEtatBsSearchRepository.delete(id);
    }

    /**
     * Search for the refEtatBs corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefEtatBsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefEtatBs for query {}", query);
        Page<RefEtatBs> result = refEtatBsSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refEtatBsMapper::toDto);
    }
}
