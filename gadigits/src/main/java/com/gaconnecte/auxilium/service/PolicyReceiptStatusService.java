package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PolicyReceiptStatus;
import com.gaconnecte.auxilium.repository.PolicyReceiptStatusRepository;
import com.gaconnecte.auxilium.repository.search.PolicyReceiptStatusSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyReceiptStatusDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyReceiptStatusMapper;
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
 * Service Implementation for managing PolicyReceiptStatus.
 */
@Service
@Transactional
public class PolicyReceiptStatusService {

    private final Logger log = LoggerFactory.getLogger(PolicyReceiptStatusService.class);

    private final PolicyReceiptStatusRepository policyReceiptStatusRepository;

    private final PolicyReceiptStatusMapper policyReceiptStatusMapper;

    private final PolicyReceiptStatusSearchRepository policyReceiptStatusSearchRepository;

    public PolicyReceiptStatusService(PolicyReceiptStatusRepository policyReceiptStatusRepository, PolicyReceiptStatusMapper policyReceiptStatusMapper, PolicyReceiptStatusSearchRepository policyReceiptStatusSearchRepository) {
        this.policyReceiptStatusRepository = policyReceiptStatusRepository;
        this.policyReceiptStatusMapper = policyReceiptStatusMapper;
        this.policyReceiptStatusSearchRepository = policyReceiptStatusSearchRepository;
    }

    /**
     * Save a policyReceiptStatus.
     *
     * @param policyReceiptStatusDTO the entity to save
     * @return the persisted entity
     */
    public PolicyReceiptStatusDTO save(PolicyReceiptStatusDTO policyReceiptStatusDTO) {
        log.debug("Request to save PolicyReceiptStatus : {}", policyReceiptStatusDTO);
        PolicyReceiptStatus policyReceiptStatus = policyReceiptStatusMapper.toEntity(policyReceiptStatusDTO);
        policyReceiptStatus = policyReceiptStatusRepository.save(policyReceiptStatus);
        PolicyReceiptStatusDTO result = policyReceiptStatusMapper.toDto(policyReceiptStatus);
        policyReceiptStatusSearchRepository.save(policyReceiptStatus);
        return result;
    }

    /**
     *  Get all the policyReceiptStatuses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyReceiptStatusDTO> findAll() {
        log.debug("Request to get all PolicyReceiptStatuses");
        return policyReceiptStatusRepository.findAll().stream()
            .map(policyReceiptStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one policyReceiptStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PolicyReceiptStatusDTO findOne(Long id) {
        log.debug("Request to get PolicyReceiptStatus : {}", id);
        PolicyReceiptStatus policyReceiptStatus = policyReceiptStatusRepository.findOne(id);
        return policyReceiptStatusMapper.toDto(policyReceiptStatus);
    }

    /**
     *  Delete the  policyReceiptStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyReceiptStatus : {}", id);
        policyReceiptStatusRepository.delete(id);
        policyReceiptStatusSearchRepository.delete(id);
    }

    /**
     * Search for the policyReceiptStatus corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyReceiptStatusDTO> search(String query) {
        log.debug("Request to search PolicyReceiptStatuses for query {}", query);
        return StreamSupport
            .stream(policyReceiptStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(policyReceiptStatusMapper::toDto)
            .collect(Collectors.toList());
    }
}
