package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Delegation;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.Region;
import com.gaconnecte.auxilium.repository.DelegationRepository;
import com.gaconnecte.auxilium.repository.GovernorateRepository;
import com.gaconnecte.auxilium.repository.search.GovernorateSearchRepository;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.mapper.GovernorateMapper;
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
 * Service Implementation for managing Governorate.
 */
@Service
@Transactional
public class GovernorateService {

    private final Logger log = LoggerFactory.getLogger(GovernorateService.class);

    private final GovernorateRepository governorateRepository;
    
    private final DelegationRepository delegationRepository;

    private final GovernorateMapper governorateMapper;

    private final GovernorateSearchRepository governorateSearchRepository;

    public GovernorateService(GovernorateRepository governorateRepository, GovernorateMapper governorateMapper, 
            GovernorateSearchRepository governorateSearchRepository, DelegationRepository delegationRepository) {
        this.governorateRepository = governorateRepository;
        this.governorateMapper = governorateMapper;
        this.governorateSearchRepository = governorateSearchRepository;
        this.delegationRepository = delegationRepository;
    }

    /**
     * Save a governorate.
     *
     * @param governorateDTO the entity to save
     * @return the persisted entity
     */
    public GovernorateDTO save(GovernorateDTO governorateDTO) {
        log.debug("Request to save Governorate : {}", governorateDTO);
        Governorate governorate = governorateMapper.toEntity(governorateDTO);
        governorate = governorateRepository.save(governorate);
        GovernorateDTO result = governorateMapper.toDto(governorate);
        governorateSearchRepository.save(governorate);
        return result;
    }

    /**
     *  Get all the governorates.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GovernorateDTO> findAll() {
        log.debug("Request to get all Governorates");
        return governorateRepository.findAll().stream()
            .map(governorateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<GovernorateDTO> findAllGovNotGageo() {
        log.debug("Request to get all Governorates");
        return governorateRepository.findAllGovNotGageo().stream()
            .map(governorateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one governorate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GovernorateDTO findOne(Long id) {
        log.debug("Request to get Governorate : {}", id);
        Governorate governorate = governorateRepository.findOne(id);
        return governorateMapper.toDto(governorate);
    }

    /**
     *  Get one governorate by id delegation.
     *
     *  @param delegationId the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GovernorateDTO findByDelegation(Long delegationId) {
        log.debug("Request to get Governorate of delegation : {}", delegationId);
        Delegation delegation = delegationRepository.findOne(delegationId);
        Governorate governorate = governorateRepository.findOne(delegation.getGovernorate().getId());
        return governorateMapper.toDto(governorate);
    }
    
    /**
     *  Get one governorate by name.
     *
     *  @param name the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GovernorateDTO findByName(String name) {
        log.debug("Request to get Governorate : {}", name);
        Governorate governorate = governorateRepository.findByLabel(name);
        return governorateMapper.toDto(governorate);
    }
    
    /**
     *  Get all region governorates.
     *  @param id : region id
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GovernorateDTO> findAllByRegion(Long id) {
        log.debug("Request to get all governorate Delegations");
        return governorateRepository.findAllByRegion(new Region(id)).stream()
            .map(governorateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Delete the  governorate by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Governorate : {}", id);
        governorateRepository.delete(id);
        governorateSearchRepository.delete(id);
    }

    /**
     * Search for the governorate corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GovernorateDTO> search(String query) {
        log.debug("Request to search Governorates for query {}", query);
        return StreamSupport
            .stream(governorateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(governorateMapper::toDto)
            .collect(Collectors.toList());
    }
}
