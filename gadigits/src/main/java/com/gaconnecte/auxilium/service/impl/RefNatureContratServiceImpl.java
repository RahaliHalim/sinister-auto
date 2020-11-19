package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefNatureContratService;
import com.gaconnecte.auxilium.domain.RefNatureContrat;
import com.gaconnecte.auxilium.repository.RefNatureContratRepository;
import com.gaconnecte.auxilium.repository.search.RefNatureContratSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefNatureContratDTO;
import com.gaconnecte.auxilium.service.mapper.RefNatureContratMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefNatureContrat.
 */
@Service
@Transactional
public class RefNatureContratServiceImpl implements RefNatureContratService{

    private final Logger log = LoggerFactory.getLogger(RefNatureContratServiceImpl.class);

    private final RefNatureContratRepository refNatureContratRepository;

    private final RefNatureContratMapper refNatureContratMapper;

    private final RefNatureContratSearchRepository refNatureContratSearchRepository;

    public RefNatureContratServiceImpl(RefNatureContratRepository refNatureContratRepository, RefNatureContratMapper refNatureContratMapper, RefNatureContratSearchRepository refNatureContratSearchRepository) {
        this.refNatureContratRepository = refNatureContratRepository;
        this.refNatureContratMapper = refNatureContratMapper;
        this.refNatureContratSearchRepository = refNatureContratSearchRepository;
    }

    /**
     * Save a refNatureContrat.
     *
     * @param refNatureContratDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefNatureContratDTO save(RefNatureContratDTO refNatureContratDTO) {
        log.debug("Request to save RefNatureContrat : {}", refNatureContratDTO);
        RefNatureContrat refNatureContrat = refNatureContratMapper.toEntity(refNatureContratDTO);
        refNatureContrat = refNatureContratRepository.save(refNatureContrat);
        RefNatureContratDTO result = refNatureContratMapper.toDto(refNatureContrat);
        refNatureContratSearchRepository.save(refNatureContrat);
        return result;
    }

    /**
     *  Get all the refNatureContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefNatureContratDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefNatureContrats");
        return refNatureContratRepository.findAll(pageable)
            .map(refNatureContratMapper::toDto);
    }

    /**
     *  Get one refNatureContrat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefNatureContratDTO findOne(Long id) {
        log.debug("Request to get RefNatureContrat : {}", id);
        RefNatureContrat refNatureContrat = refNatureContratRepository.findOneWithEagerRelationships(id);
        return refNatureContratMapper.toDto(refNatureContrat);
    }

    /**
     *  Delete the  refNatureContrat by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefNatureContrat : {}", id);
        refNatureContratRepository.delete(id);
        refNatureContratSearchRepository.delete(id);
    }

    /**
     * Search for the refNatureContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefNatureContratDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefNatureContrats for query {}", query);
        Page<RefNatureContrat> result = refNatureContratSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refNatureContratMapper::toDto);
    }
}
