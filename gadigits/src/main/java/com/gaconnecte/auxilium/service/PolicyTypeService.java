package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PolicyType;
import com.gaconnecte.auxilium.repository.PolicyTypeRepository;
import com.gaconnecte.auxilium.repository.search.PolicyTypeSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyTypeDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyTypeMapper;
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
 * Service Implementation for managing PolicyType.
 */
@Service
@Transactional
public class PolicyTypeService {

    private final Logger log = LoggerFactory.getLogger(PolicyTypeService.class);

    private final PolicyTypeRepository policyTypeRepository;

    private final PolicyTypeMapper policyTypeMapper;

    private final PolicyTypeSearchRepository policyTypeSearchRepository;

    public PolicyTypeService(PolicyTypeRepository policyTypeRepository, PolicyTypeMapper policyTypeMapper, PolicyTypeSearchRepository policyTypeSearchRepository) {
        this.policyTypeRepository = policyTypeRepository;
        this.policyTypeMapper = policyTypeMapper;
        this.policyTypeSearchRepository = policyTypeSearchRepository;
    }

    /**
     * Save a policyType.
     *
     * @param policyTypeDTO the entity to save
     * @return the persisted entity
     */
    public PolicyTypeDTO save(PolicyTypeDTO policyTypeDTO) {
        log.debug("Request to save PolicyType : {}", policyTypeDTO);
        PolicyType policyType = policyTypeMapper.toEntity(policyTypeDTO);
        policyType = policyTypeRepository.save(policyType);
        PolicyTypeDTO result = policyTypeMapper.toDto(policyType);
        policyTypeSearchRepository.save(policyType);
        return result;
    }

    /**
     *  Get all the policyTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyTypeDTO> findAll() {
        log.debug("Request to get all PolicyTypes");
        return policyTypeRepository.findAll().stream()
            .map(policyTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one policyType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PolicyTypeDTO findOne(Long id) {
        log.debug("Request to get PolicyType : {}", id);
        PolicyType policyType = policyTypeRepository.findOne(id);
        return policyTypeMapper.toDto(policyType);
    }

    /**
     *  Delete the  policyType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyType : {}", id);
        policyTypeRepository.delete(id);
        policyTypeSearchRepository.delete(id);
    }

    /**
     * Search for the policyType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyTypeDTO> search(String query) {
        log.debug("Request to search PolicyTypes for query {}", query);
        return StreamSupport
            .stream(policyTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(policyTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
