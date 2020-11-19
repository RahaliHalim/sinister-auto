package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.StampDuty;
import com.gaconnecte.auxilium.repository.StampDutyRepository;
import com.gaconnecte.auxilium.repository.search.StampDutySearchRepository;
import com.gaconnecte.auxilium.service.dto.StampDutyDTO;
import com.gaconnecte.auxilium.service.mapper.StampDutyMapper;
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
 * Service Implementation for managing StampDuty.
 */
@Service
@Transactional
public class StampDutyService {

    private final Logger log = LoggerFactory.getLogger(StampDutyService.class);

    private final StampDutyRepository stampDutyRepository;

    private final StampDutyMapper stampDutyMapper;

    private final StampDutySearchRepository stampDutySearchRepository;

    public StampDutyService(StampDutyRepository stampDutyRepository, StampDutyMapper stampDutyMapper, StampDutySearchRepository stampDutySearchRepository) {
        this.stampDutyRepository = stampDutyRepository;
        this.stampDutyMapper = stampDutyMapper;
        this.stampDutySearchRepository = stampDutySearchRepository;
    }

    /**
     * Save a stampDuty.
     *
     * @param stampDutyDTO the entity to save
     * @return the persisted entity
     */
    public StampDutyDTO save(StampDutyDTO stampDutyDTO) {
        log.debug("Request to save StampDuty : {}", stampDutyDTO);
        StampDuty stampDuty = stampDutyMapper.toEntity(stampDutyDTO);
        stampDuty = stampDutyRepository.save(stampDuty);
        StampDutyDTO result = stampDutyMapper.toDto(stampDuty);
        stampDutySearchRepository.save(stampDuty);
        return result;
    }

    /**
     *  Get all the stampDuties.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StampDutyDTO> findAll() {
        log.debug("Request to get all StampDuties");
        return stampDutyRepository.findAll().stream()
            .map(stampDutyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one stampDuty by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public StampDutyDTO findOne(Long id) {
        log.debug("Request to get StampDuty : {}", id);
        StampDuty stampDuty = stampDutyRepository.findOne(id);
        return stampDutyMapper.toDto(stampDuty);
    }

    /**
     *  Delete the  stampDuty by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StampDuty : {}", id);
        stampDutyRepository.delete(id);
        stampDutySearchRepository.delete(id);
    }

    /**
     * Search for the stampDuty corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StampDutyDTO> search(String query) {
        log.debug("Request to search StampDuties for query {}", query);
        return StreamSupport
            .stream(stampDutySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(stampDutyMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StampDutyDTO findActiveStampDuty(){
       StampDuty stampDuty = stampDutyRepository.findActiveStampDuty();
       return stampDutyMapper.toDto(stampDuty);
    }
    
}

