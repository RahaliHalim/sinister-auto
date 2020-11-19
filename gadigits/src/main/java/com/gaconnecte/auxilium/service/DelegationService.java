package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Delegation;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.repository.DelegationRepository;
import com.gaconnecte.auxilium.repository.search.DelegationSearchRepository;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.mapper.DelegationMapper;
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
 * Service Implementation for managing Delegation.
 */
@Service
@Transactional
public class DelegationService {

    private final Logger log = LoggerFactory.getLogger(DelegationService.class);

    private final DelegationRepository delegationRepository;

    private final DelegationMapper delegationMapper;

    private final DelegationSearchRepository delegationSearchRepository;

    public DelegationService(DelegationRepository delegationRepository, DelegationMapper delegationMapper, DelegationSearchRepository delegationSearchRepository) {
        this.delegationRepository = delegationRepository;
        this.delegationMapper = delegationMapper;
        this.delegationSearchRepository = delegationSearchRepository;
    }

    /**
     * Save a delegation.
     *
     * @param delegationDTO the entity to save
     * @return the persisted entity
     */
    public DelegationDTO save(DelegationDTO delegationDTO) {
        log.debug("Request to save Delegation : {}", delegationDTO);
        Delegation delegation = delegationMapper.toEntity(delegationDTO);
        delegation = delegationRepository.save(delegation);
        DelegationDTO result = delegationMapper.toDto(delegation);
        delegationSearchRepository.save(delegation);
        return result;
    }

    /**
     *  Get all the delegations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DelegationDTO> findAll() {
        log.debug("Request to get all Delegations");
        return delegationRepository.findAll().stream()
            .map(delegationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<DelegationDTO> findAllDelNotGageo() {
        log.debug("Request to get all Delegations");
        return delegationRepository.findAllDelNotGageo().stream()
            .map(delegationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all governorate delegations.
     *  @param id : governorate id
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DelegationDTO> findAllByGovernorate(Long id) {
        log.debug("Request to get all governorate Delegations");
        return delegationRepository.findAllByGovernorate(new Governorate(id)).stream()
            .map(delegationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get one delegation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DelegationDTO findOne(Long id) {
        log.debug("Request to get Delegation : {}", id);
        Delegation delegation = delegationRepository.findOne(id);
        return delegationMapper.toDto(delegation);
    }

    @Transactional(readOnly = true)
    public DelegationDTO findByName(String label) {
        log.debug("Request to get Delegation : {}", label);
        Delegation delegation = delegationRepository.findDelegationByName(label);
        return delegationMapper.toDto(delegation);
    }

    /**
     *  Delete the  delegation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Delegation : {}", id);
        delegationRepository.delete(id);
        delegationSearchRepository.delete(id);
    }

    /**
     * Search for the delegation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DelegationDTO> search(String query) {
        log.debug("Request to search Delegations for query {}", query);
        return StreamSupport
            .stream(delegationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(delegationMapper::toDto)
            .collect(Collectors.toList());
    }
}
