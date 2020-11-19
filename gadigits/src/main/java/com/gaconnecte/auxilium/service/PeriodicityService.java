package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Periodicity;
import com.gaconnecte.auxilium.repository.PeriodicityRepository;
import com.gaconnecte.auxilium.repository.search.PeriodicitySearchRepository;
import com.gaconnecte.auxilium.service.dto.PeriodicityDTO;
import com.gaconnecte.auxilium.service.mapper.PeriodicityMapper;
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
 * Service Implementation for managing Periodicity.
 */
@Service
@Transactional
public class PeriodicityService {

    private final Logger log = LoggerFactory.getLogger(PeriodicityService.class);

    private final PeriodicityRepository periodicityRepository;

    private final PeriodicityMapper periodicityMapper;

    private final PeriodicitySearchRepository periodicitySearchRepository;

    public PeriodicityService(PeriodicityRepository periodicityRepository, PeriodicityMapper periodicityMapper, PeriodicitySearchRepository periodicitySearchRepository) {
        this.periodicityRepository = periodicityRepository;
        this.periodicityMapper = periodicityMapper;
        this.periodicitySearchRepository = periodicitySearchRepository;
    }

    /**
     * Save a periodicity.
     *
     * @param periodicityDTO the entity to save
     * @return the persisted entity
     */
    public PeriodicityDTO save(PeriodicityDTO periodicityDTO) {
        log.debug("Request to save Periodicity : {}", periodicityDTO);
        Periodicity periodicity = periodicityMapper.toEntity(periodicityDTO);
        periodicity = periodicityRepository.save(periodicity);
        PeriodicityDTO result = periodicityMapper.toDto(periodicity);
        periodicitySearchRepository.save(periodicity);
        return result;
    }

    /**
     *  Get all the periodicities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PeriodicityDTO> findAll() {
        log.debug("Request to get all Periodicities");
        return periodicityRepository.findAll().stream()
            .map(periodicityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one periodicity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PeriodicityDTO findOne(Long id) {
        log.debug("Request to get Periodicity : {}", id);
        Periodicity periodicity = periodicityRepository.findOne(id);
        return periodicityMapper.toDto(periodicity);
    }

    /**
     *  Delete the  periodicity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Periodicity : {}", id);
        periodicityRepository.delete(id);
        periodicitySearchRepository.delete(id);
    }

    /**
     * Search for the periodicity corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PeriodicityDTO> search(String query) {
        log.debug("Request to search Periodicities for query {}", query);
        return StreamSupport
            .stream(periodicitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(periodicityMapper::toDto)
            .collect(Collectors.toList());
    }
}
