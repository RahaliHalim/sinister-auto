package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Functionality;
import com.gaconnecte.auxilium.repository.FunctionalityRepository;
import com.gaconnecte.auxilium.repository.search.FunctionalitySearchRepository;
import com.gaconnecte.auxilium.service.dto.FunctionalityDTO;
import com.gaconnecte.auxilium.service.mapper.FunctionalityMapper;
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
 * Service Implementation for managing Functionality.
 */
@Service
@Transactional
public class FunctionalityService {

    private final Logger log = LoggerFactory.getLogger(FunctionalityService.class);

    private final FunctionalityRepository functionalityRepository;

    private final FunctionalityMapper functionalityMapper;

    private final FunctionalitySearchRepository functionalitySearchRepository;

    public FunctionalityService(FunctionalityRepository functionalityRepository, FunctionalityMapper functionalityMapper, FunctionalitySearchRepository functionalitySearchRepository) {
        this.functionalityRepository = functionalityRepository;
        this.functionalityMapper = functionalityMapper;
        this.functionalitySearchRepository = functionalitySearchRepository;
    }

    /**
     * Save a functionality.
     *
     * @param functionalityDTO the entity to save
     * @return the persisted entity
     */
    public FunctionalityDTO save(FunctionalityDTO functionalityDTO) {
        log.debug("Request to save Functionality : {}", functionalityDTO);
        Functionality functionality = functionalityMapper.toEntity(functionalityDTO);
        functionality = functionalityRepository.save(functionality);
        FunctionalityDTO result = functionalityMapper.toDto(functionality);
        functionalitySearchRepository.save(functionality);
        return result;
    }

    /**
     *  Get all the functionalities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FunctionalityDTO> findAll() {
        log.debug("Request to get all Functionalities");
        return functionalityRepository.findAll().stream()
            .map(functionalityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one functionality by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FunctionalityDTO findOne(Long id) {
        log.debug("Request to get Functionality : {}", id);
        Functionality functionality = functionalityRepository.findOne(id);
        return functionalityMapper.toDto(functionality);
    }

    /**
     *  Delete the  functionality by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Functionality : {}", id);
        functionalityRepository.delete(id);
        functionalitySearchRepository.delete(id);
    }

    /**
     * Search for the functionality corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FunctionalityDTO> search(String query) {
        log.debug("Request to search Functionalities for query {}", query);
        return StreamSupport
            .stream(functionalitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(functionalityMapper::toDto)
            .collect(Collectors.toList());
    }
}
