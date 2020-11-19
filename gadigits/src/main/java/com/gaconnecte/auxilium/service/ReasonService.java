package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Reason;
import com.gaconnecte.auxilium.repository.ReasonRepository;
import com.gaconnecte.auxilium.repository.search.ReasonSearchRepository;
import com.gaconnecte.auxilium.service.dto.ReasonDTO;
import com.gaconnecte.auxilium.service.mapper.ReasonMapper;
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
 * Service Implementation for managing Reason.
 */
@Service
@Transactional
public class ReasonService {

    private final Logger log = LoggerFactory.getLogger(ReasonService.class);

    private final ReasonRepository reasonRepository;

    private final ReasonMapper reasonMapper;

    private final ReasonSearchRepository reasonSearchRepository;

    public ReasonService(ReasonRepository reasonRepository, ReasonMapper reasonMapper, ReasonSearchRepository reasonSearchRepository) {
        this.reasonRepository = reasonRepository;
        this.reasonMapper = reasonMapper;
        this.reasonSearchRepository = reasonSearchRepository;
    }

    /**
     * Save a reason.
     *
     * @param reasonDTO the entity to save
     * @return the persisted entity
     */
    public ReasonDTO save(ReasonDTO reasonDTO) {
        log.debug("Request to save Reason : {}", reasonDTO);
        Reason reason = reasonMapper.toEntity(reasonDTO);
        reason = reasonRepository.save(reason);
        ReasonDTO result = reasonMapper.toDto(reason);
        reasonSearchRepository.save(reason);
        return result;
    }

    /**
     *  Get all the reasons.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReasonDTO> findAll() {
        log.debug("Request to get all Reasons");
        return reasonRepository.findAll().stream()
            .map(reasonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get all the Motifs Reopened.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReasonDTO> findAllMotifsReopened() {
        log.debug("Request to get all Reasons Reopened");
        return reasonRepository.findAllMotifReopened().stream()
            .map(reasonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<ReasonDTO> findAllMotifsCanceled() {
        log.debug("Request to get all Reasons Canceled");
        return reasonRepository.findAllMotifCanceled().stream()
            .map(reasonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<ReasonDTO> findAllMotifsRefused() {
        log.debug("Request to get all Reasons Refused");
        return reasonRepository.findAllMotifRefused().stream()
            .map(reasonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Transactional(readOnly = true)
    public List<ReasonDTO> findMotifsByStepId(Long stepId) {
        log.debug("Request to get Reasons By step : {}", stepId);
        return reasonRepository.findMotifsByStepId(stepId).stream()
                .map(reasonMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        
    }
    

    
    /**
     *  Get one reason by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    
    @Transactional(readOnly = true)
    public ReasonDTO findOne(Long id) {
        log.debug("Request to get Reason : {}", id);
        Reason reason = reasonRepository.findOne(id);
        return reasonMapper.toDto(reason);
    }

    /**
     *  Delete the  reason by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reason : {}", id);
        reasonRepository.delete(id);
        reasonSearchRepository.delete(id);
    }

    /**
     * Search for the reason corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReasonDTO> search(String query) {
        log.debug("Request to search Reasons for query {}", query);
        return StreamSupport
            .stream(reasonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reasonMapper::toDto)
            .collect(Collectors.toList());
    }
}
