package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PolicyHolder;
import com.gaconnecte.auxilium.repository.PolicyHolderRepository;
import com.gaconnecte.auxilium.repository.search.PolicyHolderSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyHolderDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyHolderMapper;
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
 * Service Implementation for managing PolicyHolder.
 */
@Service
@Transactional
public class PolicyHolderService {

    private final Logger log = LoggerFactory.getLogger(PolicyHolderService.class);

    private final PolicyHolderRepository policyHolderRepository;

    private final PolicyHolderMapper policyHolderMapper;

    private final PolicyHolderSearchRepository policyHolderSearchRepository;

    public PolicyHolderService(PolicyHolderRepository policyHolderRepository, PolicyHolderMapper policyHolderMapper, PolicyHolderSearchRepository policyHolderSearchRepository) {
        this.policyHolderRepository = policyHolderRepository;
        this.policyHolderMapper = policyHolderMapper;
        this.policyHolderSearchRepository = policyHolderSearchRepository;
    }

    /**
     * Save a policyHolder.
     *
     * @param policyHolderDTO the entity to save
     * @return the persisted entity
     */
    public PolicyHolderDTO save(PolicyHolderDTO policyHolderDTO) {
        log.debug("Request to save PolicyHolder : {}", policyHolderDTO);
        PolicyHolder policyHolder = policyHolderMapper.toEntity(policyHolderDTO);
        policyHolder = policyHolderRepository.save(policyHolder);
        PolicyHolderDTO result = policyHolderMapper.toDto(policyHolder);
        policyHolderSearchRepository.save(policyHolder);
        return result;
    }

    /**
     *  Get all the policyHolders.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyHolderDTO> findAll() {
        log.debug("Request to get all PolicyHolders");
        return policyHolderRepository.findAll().stream()
            .map(policyHolderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one policyHolder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PolicyHolderDTO findOne(Long id) {
        log.debug("Request to get PolicyHolder : {}", id);
        PolicyHolder policyHolder = policyHolderRepository.findOne(id);
        return policyHolderMapper.toDto(policyHolder);
    }

    /**
     *  Delete the  policyHolder by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyHolder : {}", id);
        policyHolderRepository.delete(id);
        policyHolderSearchRepository.delete(id);
    }

    /**
     * Search for the policyHolder corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyHolderDTO> search(String query) {
        log.debug("Request to search PolicyHolders for query {}", query);
        return StreamSupport
            .stream(policyHolderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(policyHolderMapper::toDto)
            .collect(Collectors.toList());
    }
}
