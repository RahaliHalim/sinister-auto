package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefTypeInterventionService;
import com.gaconnecte.auxilium.domain.RefTypeIntervention;
import com.gaconnecte.auxilium.repository.RefTypeInterventionRepository;
import com.gaconnecte.auxilium.repository.search.RefTypeInterventionSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTypeInterventionDTO;
import com.gaconnecte.auxilium.service.mapper.RefTypeInterventionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.stream.Collectors;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefTypeIntervention.
 */
@Service
@Transactional
public class RefTypeInterventionServiceImpl implements RefTypeInterventionService{

    private final Logger log = LoggerFactory.getLogger(RefTypeInterventionServiceImpl.class);

    private final RefTypeInterventionRepository refTypeInterventionRepository;

    private final RefTypeInterventionMapper refTypeInterventionMapper;

    private final RefTypeInterventionSearchRepository refTypeInterventionSearchRepository;

    public RefTypeInterventionServiceImpl(RefTypeInterventionRepository refTypeInterventionRepository, RefTypeInterventionMapper refTypeInterventionMapper, RefTypeInterventionSearchRepository refTypeInterventionSearchRepository) {
        this.refTypeInterventionRepository = refTypeInterventionRepository;
        this.refTypeInterventionMapper = refTypeInterventionMapper;
        this.refTypeInterventionSearchRepository = refTypeInterventionSearchRepository;
    }

    /**
     * Save a refTypeIntervention.
     *
     * @param refTypeInterventionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefTypeInterventionDTO save(RefTypeInterventionDTO refTypeInterventionDTO) {
        log.debug("Request to save RefTypeIntervention : {}", refTypeInterventionDTO);
        RefTypeIntervention refTypeIntervention = refTypeInterventionMapper.toEntity(refTypeInterventionDTO);
        refTypeIntervention = refTypeInterventionRepository.save(refTypeIntervention);
        RefTypeInterventionDTO result = refTypeInterventionMapper.toDto(refTypeIntervention);
        refTypeInterventionSearchRepository.save(refTypeIntervention);
        return result;
    }

    /**
     *  Get all the refTypeInterventions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeInterventionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefTypeInterventions");
        return refTypeInterventionRepository.findAll(pageable)
            .map(refTypeInterventionMapper::toDto);
    }

@Override
    @Transactional(readOnly = true)
    public Set<RefTypeInterventionDTO> findByType(Integer type) {
        log.debug("Request to get RefTypeServices By type");
        Set<RefTypeIntervention> interventionsType = refTypeInterventionRepository.findByType(type);
        if (CollectionUtils.isNotEmpty(interventionsType)) {
            return interventionsType.stream().map(refTypeInterventionMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    
    /**
     *  Get one refTypeIntervention by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefTypeInterventionDTO findOne(Long id) {
        log.debug("Request to get RefTypeIntervention : {}", id);
        RefTypeIntervention refTypeIntervention = refTypeInterventionRepository.findOne(id);
        return refTypeInterventionMapper.toDto(refTypeIntervention);
    }

    /**
     *  Delete the  refTypeIntervention by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefTypeIntervention : {}", id);
        refTypeInterventionRepository.delete(id);
        refTypeInterventionSearchRepository.delete(id);
    }

    /**
     * Search for the refTypeIntervention corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefTypeInterventionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefTypeInterventions for query {}", query);
        Page<RefTypeIntervention> result = refTypeInterventionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refTypeInterventionMapper::toDto);
    }
}
