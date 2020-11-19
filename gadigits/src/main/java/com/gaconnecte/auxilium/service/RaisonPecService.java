package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.RaisonPec;
import com.gaconnecte.auxilium.repository.RaisonPecRepository;
import com.gaconnecte.auxilium.repository.search.RaisonPecSearchRepository;
import com.gaconnecte.auxilium.service.dto.RaisonPecDTO;
import com.gaconnecte.auxilium.service.dto.ReasonDTO;
import com.gaconnecte.auxilium.service.mapper.RaisonPecMapper;
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
 * Service Implementation for managing RaisonPec.
 */
@Service
@Transactional
public class RaisonPecService {

    private final Logger log = LoggerFactory.getLogger(RaisonPecService.class);

    private final RaisonPecRepository raisonPecRepository;

    private final RaisonPecMapper raisonPecMapper;

    private final RaisonPecSearchRepository raisonPecSearchRepository;

    public RaisonPecService(RaisonPecRepository raisonPecRepository, RaisonPecMapper raisonPecMapper, RaisonPecSearchRepository raisonPecSearchRepository) {
        this.raisonPecRepository = raisonPecRepository;
        this.raisonPecMapper = raisonPecMapper;
        this.raisonPecSearchRepository = raisonPecSearchRepository;
    }

    /**
     * Save a raisonPec.
     *
     * @param raisonPecDTO the entity to save
     * @return the persisted entity
     */
    public RaisonPecDTO save(RaisonPecDTO raisonPecDTO) {
        log.debug("Request to save RaisonPec : {}", raisonPecDTO);
        RaisonPec raisonPec = raisonPecMapper.toEntity(raisonPecDTO);
        raisonPec = raisonPecRepository.save(raisonPec);
        RaisonPecDTO result = raisonPecMapper.toDto(raisonPec);
        raisonPecSearchRepository.save(raisonPec);
        return result;
    }

    /**
     *  Get all the raisonPecs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findAll() {
        log.debug("Request to get all RaisonPecs");
        return raisonPecRepository.findAll().stream()
            .map(raisonPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one raisonPec by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RaisonPecDTO findOne(Long id) {
        log.debug("Request to get RaisonPec : {}", id);
        RaisonPec raisonPec = raisonPecRepository.findOne(id);
        return raisonPecMapper.toDto(raisonPec);
    }

    /**
     *  Delete the  raisonPec by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RaisonPec : {}", id);
        raisonPecRepository.delete(id);
        raisonPecSearchRepository.delete(id);
    }
    
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findAllMotifsReopened() {
        log.debug("Request to get all Reasons Reopened");
        return raisonPecRepository.findAllMotifReopened().stream()
            .map(raisonPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findAllMotifsCanceled() {
        log.debug("Request to get all Reasons Canceled");
        return raisonPecRepository.findAllMotifCanceled().stream()
            .map(raisonPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findAllMotifsRefused() {
        log.debug("Request to get all Reasons Refused");
        return raisonPecRepository.findAllMotifRefused().stream()
            .map(raisonPecMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findMotifsByStepId(Long stepId) {
        log.debug("Request to get Reasons By step : {}", stepId);
        return raisonPecRepository.findMotifsByStepId(stepId).stream()
                .map(raisonPecMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        
    }
    
    
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findMotifsByOperationId(Long operationId) {
        log.debug("Request to get Reasons By operation : {}", operationId);
        return raisonPecRepository.findMotifsByOperationId(operationId).stream()
                .map(raisonPecMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        
    }
    
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> findMotifsByStatusPecStatusChangeMatrix(Long changeMatrixId) {
        log.debug("Request to get Reasons By step : {}", changeMatrixId);
        return raisonPecRepository.findMotifsByStatusPecStatusChangeMatrix(changeMatrixId).stream()
                .map(raisonPecMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        
    }

    /**
     * Search for the raisonPec corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RaisonPecDTO> search(String query) {
        log.debug("Request to search RaisonPecs for query {}", query);
        return StreamSupport
            .stream(raisonPecSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(raisonPecMapper::toDto)
            .collect(Collectors.toList());
    }
}
