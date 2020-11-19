package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PolicyNature;
import com.gaconnecte.auxilium.repository.PolicyNatureRepository;
import com.gaconnecte.auxilium.repository.search.PolicyNatureSearchRepository;
import com.gaconnecte.auxilium.service.dto.PolicyNatureDTO;
import com.gaconnecte.auxilium.service.mapper.PolicyNatureMapper;
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
 * Service Implementation for managing PolicyNature.
 */
@Service
@Transactional
public class PolicyNatureService {

    private final Logger log = LoggerFactory.getLogger(PolicyNatureService.class);

    private final PolicyNatureRepository policyNatureRepository;

    private final PolicyNatureMapper policyNatureMapper;

    private final PolicyNatureSearchRepository policyNatureSearchRepository;

    public PolicyNatureService(PolicyNatureRepository policyNatureRepository, PolicyNatureMapper policyNatureMapper, PolicyNatureSearchRepository policyNatureSearchRepository) {
        this.policyNatureRepository = policyNatureRepository;
        this.policyNatureMapper = policyNatureMapper;
        this.policyNatureSearchRepository = policyNatureSearchRepository;
    }

    /**
     * Save a policyNature.
     *
     * @param policyNatureDTO the entity to save
     * @return the persisted entity
     */
    public PolicyNatureDTO save(PolicyNatureDTO policyNatureDTO) {
        log.debug("Request to save PolicyNature : {}", policyNatureDTO);
        PolicyNature policyNature = policyNatureMapper.toEntity(policyNatureDTO);
        policyNature = policyNatureRepository.save(policyNature);
        PolicyNatureDTO result = policyNatureMapper.toDto(policyNature);
        policyNatureSearchRepository.save(policyNature);
        return result;
    }

    /**
     *  Get all the policyNatures.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyNatureDTO> findAll() {
        log.debug("Request to get all PolicyNatures");
        return policyNatureRepository.findAll().stream()
            .map(policyNatureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one policyNature by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PolicyNatureDTO findOne(Long id) {
        log.debug("Request to get PolicyNature : {}", id);
        PolicyNature policyNature = policyNatureRepository.findOne(id);
        return policyNatureMapper.toDto(policyNature);
    }

    /**
     *  Delete the  policyNature by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyNature : {}", id);
        policyNatureRepository.delete(id);
        policyNatureSearchRepository.delete(id);
    }

    /**
     * Search for the policyNature corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PolicyNatureDTO> search(String query) {
        log.debug("Request to search PolicyNatures for query {}", query);
        return StreamSupport
            .stream(policyNatureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(policyNatureMapper::toDto)
            .collect(Collectors.toList());
    }
}
