package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Policy;
import com.gaconnecte.auxilium.repository.PolicyRepository;
import com.gaconnecte.auxilium.repository.search.PolicySearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyMapper;
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
 * Service Implementation for managing Policy.
 */
@Service
@Transactional
public class PolicyService {

    private final Logger log = LoggerFactory.getLogger(PolicyService.class);

    private final PolicyRepository policyRepository;

    private final PolicyMapper policyMapper;

    private final PolicySearchRepository policySearchRepository;

    public PolicyService(PolicyRepository policyRepository, PolicyMapper policyMapper, PolicySearchRepository policySearchRepository) {
        this.policyRepository = policyRepository;
        this.policyMapper = policyMapper;
        this.policySearchRepository = policySearchRepository;
    }

    /**
     * Save a policy.
     *
     * @param policyDTO the entity to save
     * @return the persisted entity
     */
    public PolicyDTO save(PolicyDTO policyDTO) {
        log.debug("Request to save Policy : {}", policyDTO);
        Policy policy = policyMapper.toEntity(policyDTO);
        policy = policyRepository.save(policy);
        PolicyDTO result = policyMapper.toDto(policy);
        policySearchRepository.save(policy);
        return result;
    }

    /**
     *  Get all the policies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyDTO> findAll() {
        log.debug("Request to get all Policies");
        return policyRepository.findAll().stream()
            .map(policyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one policy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PolicyDTO findOne(Long id) {
        log.debug("Request to get Policy : {}", id);
        Policy policy = policyRepository.findOne(id);
        return policyMapper.toDto(policy);
    }

    /**
     *  Delete the  policy by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Policy : {}", id);
        policyRepository.delete(id);
        policySearchRepository.delete(id);
    }

    /**
     * Search for the policy corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyDTO> search(String query) {
        log.debug("Request to search Policies for query {}", query);
        return StreamSupport
            .stream(policySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(policyMapper::toDto)
            .collect(Collectors.toList());
    }
}
