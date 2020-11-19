package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PolicyStatus;
import com.gaconnecte.auxilium.repository.PolicyStatusRepository;
import com.gaconnecte.auxilium.repository.search.PolicyStatusSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyStatusDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PolicyStatus.
 */
@Service
@Transactional
public class PolicyStatusService {

    private final Logger log = LoggerFactory.getLogger(PolicyStatusService.class);

    private final PolicyStatusRepository policyStatusRepository;

    private final PolicyStatusMapper policyStatusMapper;

    private final PolicyStatusSearchRepository policyStatusSearchRepository;

    public PolicyStatusService(PolicyStatusRepository policyStatusRepository, PolicyStatusMapper policyStatusMapper, PolicyStatusSearchRepository policyStatusSearchRepository) {
        this.policyStatusRepository = policyStatusRepository;
        this.policyStatusMapper = policyStatusMapper;
        this.policyStatusSearchRepository = policyStatusSearchRepository;
    }

    /**
     * Save a policyStatus.
     *
     * @param policyStatusDTO the entity to save
     * @return the persisted entity
     */
    public PolicyStatusDTO save(PolicyStatusDTO policyStatusDTO) {
        log.debug("Request to save PolicyStatus : {}", policyStatusDTO);
        PolicyStatus policyStatus = policyStatusMapper.toEntity(policyStatusDTO);
        policyStatus = policyStatusRepository.save(policyStatus);
        PolicyStatusDTO result = policyStatusMapper.toDto(policyStatus);
        policyStatusSearchRepository.save(policyStatus);
        return result;
    }

    /**
     *  Get all the policyStatuses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyStatusDTO> findAll() {
        log.debug("Request to get all PolicyStatuses");
        return policyStatusRepository.findAll().stream()
            .map(policyStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one policyStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PolicyStatusDTO findOne(Long id) {
        log.debug("Request to get PolicyStatus : {}", id);
        PolicyStatus policyStatus = policyStatusRepository.findOne(id);
        return policyStatusMapper.toDto(policyStatus);
    }

    /**
     *  Delete the  policyStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyStatus : {}", id);
        policyStatusRepository.delete(id);
        policyStatusSearchRepository.delete(id);
    }

    /**
     * Search for the policyStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyStatusDTO> search(String query) {
        log.debug("Request to search PolicyStatuses for query {}", query);
        return StreamSupport
            .stream(policyStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(policyStatusMapper::toDto)
            .collect(Collectors.toList());
    }
}
