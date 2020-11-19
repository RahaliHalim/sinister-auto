package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.StatusPec;
import com.gaconnecte.auxilium.repository.StatusPecRepository;
import com.gaconnecte.auxilium.repository.search.StatusPecSearchRepository;
import com.gaconnecte.auxilium.service.dto.StatusPecDTO;
import com.gaconnecte.auxilium.service.mapper.StatusPecMapper;
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
 * Service Implementation for managing StatusPec.
 */
@Service
@Transactional
public class StatusPecService {

    private final Logger log = LoggerFactory.getLogger(StatusPecService.class);

    private final StatusPecRepository statusPecRepository;

    private final StatusPecMapper statusPecMapper;

    private final StatusPecSearchRepository statusPecSearchRepository;

    public StatusPecService(StatusPecRepository statusPecRepository, StatusPecMapper statusPecMapper, StatusPecSearchRepository statusPecSearchRepository) {
        this.statusPecRepository = statusPecRepository;
        this.statusPecMapper = statusPecMapper;
        this.statusPecSearchRepository = statusPecSearchRepository;
    }

    /**
     * Save a statusPec.
     *
     * @param statusPecDTO the entity to save
     * @return the persisted entity
     */
    public StatusPecDTO save(StatusPecDTO statusPecDTO) {
        log.debug("Request to save StatusPec : {}", statusPecDTO);
        StatusPec statusPec = statusPecMapper.toEntity(statusPecDTO);
        statusPec = statusPecRepository.save(statusPec);
        StatusPecDTO result = statusPecMapper.toDto(statusPec);
        statusPecSearchRepository.save(statusPec);
        return result;
    }

    /**
     *  Get all the statusPecs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StatusPecDTO> findAll() {
        log.debug("Request to get all StatusPecs");
        return statusPecRepository.findAll().stream()
            .map(statusPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the statusPecs by code
     *  @param code 
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StatusPecDTO> findAllByCode(String code) {
        log.debug("Request to get all StatusPecs");
        return statusPecRepository.findAllByCode(code).stream()
            .map(statusPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the statusPecs witch has reason
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StatusPecDTO> findAllWitchHasReason() {
        log.debug("Request to get all StatusPecs");
        return statusPecRepository.findAllByHasReason(Boolean.TRUE).stream()
            .map(statusPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get one statusPec by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public StatusPecDTO findOne(Long id) {
        log.debug("Request to get StatusPec : {}", id);
        StatusPec statusPec = statusPecRepository.findOne(id);
        return statusPecMapper.toDto(statusPec);
    }

    /**
     *  Delete the  statusPec by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StatusPec : {}", id);
        statusPecRepository.delete(id);
        statusPecSearchRepository.delete(id);
    }

    /**
     * Search for the statusPec corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StatusPecDTO> search(String query) {
        log.debug("Request to search StatusPecs for query {}", query);
        return StreamSupport
            .stream(statusPecSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(statusPecMapper::toDto)
            .collect(Collectors.toList());
    }
}
