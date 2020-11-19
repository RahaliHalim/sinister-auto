package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTypeContratService;
import com.gaconnecte.auxilium.domain.RefTypeContrat;
import com.gaconnecte.auxilium.repository.RefTypeContratRepository;
import com.gaconnecte.auxilium.repository.search.RefTypeContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypeContratDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypeContratMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefTypeContrat.
 */
@Service
@Transactional
public class RefTypeContratServiceImpl implements RefTypeContratService{

    private final Logger log = LoggerFactory.getLogger(RefTypeContratServiceImpl.class);

    private final RefTypeContratRepository refTypeContratRepository;

    private final RefTypeContratMapper refTypeContratMapper;

    private final RefTypeContratSearchRepository refTypeContratSearchRepository;

    public RefTypeContratServiceImpl(RefTypeContratRepository refTypeContratRepository, RefTypeContratMapper refTypeContratMapper, RefTypeContratSearchRepository refTypeContratSearchRepository) {
        this.refTypeContratRepository = refTypeContratRepository;
        this.refTypeContratMapper = refTypeContratMapper;
        this.refTypeContratSearchRepository = refTypeContratSearchRepository;
    }

    /**
     * Save a refTypeContrat.
     *
     * @param refTypeContratDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTypeContratDTO save(RefTypeContratDTO refTypeContratDTO) {
        log.debug("Request to save RefTypeContrat : {}", refTypeContratDTO);
        RefTypeContrat refTypeContrat = refTypeContratMapper.toEntity(refTypeContratDTO);
        refTypeContrat = refTypeContratRepository.save(refTypeContrat);
        RefTypeContratDTO result = refTypeContratMapper.toDto(refTypeContrat);
        refTypeContratSearchRepository.save(refTypeContrat);
        return result;
    }

    /**
     *  Get all the refTypeContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefTypeContrats");
        return refTypeContratRepository.findAll(pageable)
            .map(refTypeContratMapper::toDto);
    }

    /**
     *  Get one refTypeContrat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypeContratDTO findOne(Long id) {
        log.debug("Request to get RefTypeContrat : {}", id);
        RefTypeContrat refTypeContrat = refTypeContratRepository.findOneWithEagerRelationships(id);
        return refTypeContratMapper.toDto(refTypeContrat);
    }

    /**
     *  Delete the  refTypeContrat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTypeContrat : {}", id);
        refTypeContratRepository.delete(id);
        refTypeContratSearchRepository.delete(id);
    }

    /**
     * Search for the refTypeContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeContratDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefTypeContrats for query {}", query);
        Page<RefTypeContrat> result = refTypeContratSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refTypeContratMapper::toDto);
    }
}
