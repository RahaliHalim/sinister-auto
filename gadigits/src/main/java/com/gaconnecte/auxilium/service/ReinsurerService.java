package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Reinsurer;
import com.gaconnecte.auxilium.repository.ReinsurerRepository;
import com.gaconnecte.auxilium.repository.search.ReinsurerSearchRepository;
import com.gaconnecte.auxilium.service.dto.ReinsurerDTO;
import com.gaconnecte.auxilium.service.mapper.ReinsurerMapper;
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
 * Service Implementation for managing Reinsurer.
 */
@Service
@Transactional
public class ReinsurerService {

    private final Logger log = LoggerFactory.getLogger(ReinsurerService.class);

    private final ReinsurerRepository reinsurerRepository;

    private final ReinsurerMapper reinsurerMapper;

    private final ReinsurerSearchRepository reinsurerSearchRepository;

    public ReinsurerService(ReinsurerRepository reinsurerRepository, ReinsurerMapper reinsurerMapper, ReinsurerSearchRepository reinsurerSearchRepository) {
        this.reinsurerRepository = reinsurerRepository;
        this.reinsurerMapper = reinsurerMapper;
        this.reinsurerSearchRepository = reinsurerSearchRepository;
    }

    /**
     * Save a reinsurer.
     *
     * @param reinsurerDTO the entity to save
     * @return the persisted entity
     */
    public ReinsurerDTO save(ReinsurerDTO reinsurerDTO) {
        log.debug("Request to save Reinsurer : {}", reinsurerDTO);
        Reinsurer reinsurer = reinsurerMapper.toEntity(reinsurerDTO);
        reinsurer = reinsurerRepository.save(reinsurer);
        ReinsurerDTO result = reinsurerMapper.toDto(reinsurer);
        reinsurerSearchRepository.save(reinsurer);
        return result;
    }

    /**
     *  Get all the reinsurers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReinsurerDTO> findAll() {
        log.debug("Request to get all Reinsurers");
        return reinsurerRepository.findAll().stream()
            .map(reinsurerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one reinsurer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ReinsurerDTO findOne(Long id) {
        log.debug("Request to get Reinsurer : {}", id);
        Reinsurer reinsurer = reinsurerRepository.findOne(id);
        return reinsurerMapper.toDto(reinsurer);
    }

    /**
     *  Delete the  reinsurer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reinsurer : {}", id);
        reinsurerRepository.delete(id);
        reinsurerSearchRepository.delete(id);
    }

    /**
     * Search for the reinsurer corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReinsurerDTO> search(String query) {
        log.debug("Request to search Reinsurers for query {}", query);
        return StreamSupport
            .stream(reinsurerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reinsurerMapper::toDto)
            .collect(Collectors.toList());
    }
}
