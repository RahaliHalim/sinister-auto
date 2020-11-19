package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.RaisonAssistance;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import com.gaconnecte.auxilium.repository.RaisonAssistanceRepository;
import com.gaconnecte.auxilium.repository.search.RaisonAssistanceSearchRepository;
import com.gaconnecte.auxilium.service.dto.RaisonAssistanceDTO;
import com.gaconnecte.auxilium.service.mapper.RaisonAssistanceMapper;
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
 * Service Implementation for managing RaisonAssistance.
 */
@Service
@Transactional
public class RaisonAssistanceService {

    private final Logger log = LoggerFactory.getLogger(RaisonAssistanceService.class);

    private final RaisonAssistanceRepository raisonAssistanceRepository;

    private final RaisonAssistanceMapper raisonAssistanceMapper;

    private final RaisonAssistanceSearchRepository raisonAssistanceSearchRepository;

    public RaisonAssistanceService(RaisonAssistanceRepository raisonAssistanceRepository, RaisonAssistanceMapper raisonAssistanceMapper, RaisonAssistanceSearchRepository raisonAssistanceSearchRepository) {
        this.raisonAssistanceRepository = raisonAssistanceRepository;
        this.raisonAssistanceMapper = raisonAssistanceMapper;
        this.raisonAssistanceSearchRepository = raisonAssistanceSearchRepository;
    }

    /**
     * Save a raisonAssistance.
     *
     * @param raisonAssistanceDTO the entity to save
     * @return the persisted entity
     */
    public RaisonAssistanceDTO save(RaisonAssistanceDTO raisonAssistanceDTO) {
        log.debug("Request to save RaisonAssistance : {}", raisonAssistanceDTO);
        RaisonAssistance raisonAssistance = raisonAssistanceMapper.toEntity(raisonAssistanceDTO);
        raisonAssistance = raisonAssistanceRepository.save(raisonAssistance);
        RaisonAssistanceDTO result = raisonAssistanceMapper.toDto(raisonAssistance);
        raisonAssistanceSearchRepository.save(raisonAssistance);
        return result;
    }

    /**
     *  Get all the raisonAssistances.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RaisonAssistanceDTO> findAll() {
        log.debug("Request to get all RaisonAssistances");
        return raisonAssistanceRepository.findAll().stream()
            .map(raisonAssistanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the raisonAssistances.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RaisonAssistanceDTO> findAllByStatus(Long id) {
        log.debug("Request to get all RaisonAssistances");
        return raisonAssistanceRepository.findAllByStatus(new RefStatusSinister(id)).stream()
            .map(raisonAssistanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    
    @Transactional(readOnly = true)
    public List<RaisonAssistanceDTO> findAllByOperation(Long id) {
        log.debug("Request to get all RaisonAssistances by operation");
        return raisonAssistanceRepository.findAllByOperation(id).stream()
            .map(raisonAssistanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    
    
    /**
     *  Get one raisonAssistance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RaisonAssistanceDTO findOne(Long id) {
        log.debug("Request to get RaisonAssistance : {}", id);
        RaisonAssistance raisonAssistance = raisonAssistanceRepository.findOne(id);
        return raisonAssistanceMapper.toDto(raisonAssistance);
    }

    /**
     *  Delete the  raisonAssistance by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RaisonAssistance : {}", id);
        raisonAssistanceRepository.delete(id);
        raisonAssistanceSearchRepository.delete(id);
    }

    /**
     * Search for the raisonAssistance corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RaisonAssistanceDTO> search(String query) {
        log.debug("Request to search RaisonAssistances for query {}", query);
        return StreamSupport
            .stream(raisonAssistanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(raisonAssistanceMapper::toDto)
            .collect(Collectors.toList());
    }
}
