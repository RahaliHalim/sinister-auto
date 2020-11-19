package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PecStatusChangeMatrix;
import com.gaconnecte.auxilium.repository.PecStatusChangeMatrixRepository;
import com.gaconnecte.auxilium.repository.search.PecStatusChangeMatrixSearchRepository;
import com.gaconnecte.auxilium.service.dto.PecStatusChangeMatrixDTO;
import com.gaconnecte.auxilium.service.mapper.PecStatusChangeMatrixMapper;
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
 * Service Implementation for managing PecStatusChangeMatrix.
 */
@Service
@Transactional
public class PecStatusChangeMatrixService {

    private final Logger log = LoggerFactory.getLogger(PecStatusChangeMatrixService.class);

    private final PecStatusChangeMatrixRepository pecStatusChangeMatrixRepository;

    private final PecStatusChangeMatrixMapper pecStatusChangeMatrixMapper;

    private final PecStatusChangeMatrixSearchRepository pecStatusChangeMatrixSearchRepository;

    public PecStatusChangeMatrixService(PecStatusChangeMatrixRepository pecStatusChangeMatrixRepository, PecStatusChangeMatrixMapper pecStatusChangeMatrixMapper, PecStatusChangeMatrixSearchRepository pecStatusChangeMatrixSearchRepository) {
        this.pecStatusChangeMatrixRepository = pecStatusChangeMatrixRepository;
        this.pecStatusChangeMatrixMapper = pecStatusChangeMatrixMapper;
        this.pecStatusChangeMatrixSearchRepository = pecStatusChangeMatrixSearchRepository;
    }

    /**
     * Save a pecStatusChangeMatrix.
     *
     * @param pecStatusChangeMatrixDTO the entity to save
     * @return the persisted entity
     */
    public PecStatusChangeMatrixDTO save(PecStatusChangeMatrixDTO pecStatusChangeMatrixDTO) {
        log.debug("Request to save PecStatusChangeMatrix : {}", pecStatusChangeMatrixDTO);
        PecStatusChangeMatrix pecStatusChangeMatrix = pecStatusChangeMatrixMapper.toEntity(pecStatusChangeMatrixDTO);
        pecStatusChangeMatrix = pecStatusChangeMatrixRepository.save(pecStatusChangeMatrix);
        PecStatusChangeMatrixDTO result = pecStatusChangeMatrixMapper.toDto(pecStatusChangeMatrix);
        pecStatusChangeMatrixSearchRepository.save(pecStatusChangeMatrix);
        return result;
    }

    /**
     *  Get all the pecStatusChangeMatrices.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PecStatusChangeMatrixDTO> findAll() {
        log.debug("Request to get all PecStatusChangeMatrices");
        return pecStatusChangeMatrixRepository.findAll().stream()
            .map(pecStatusChangeMatrixMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one pecStatusChangeMatrix by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PecStatusChangeMatrixDTO findOne(Long id) {
        log.debug("Request to get PecStatusChangeMatrix : {}", id);
        PecStatusChangeMatrix pecStatusChangeMatrix = pecStatusChangeMatrixRepository.findOne(id);
        return pecStatusChangeMatrixMapper.toDto(pecStatusChangeMatrix);
    }

    /**
     *  Delete the  pecStatusChangeMatrix by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PecStatusChangeMatrix : {}", id);
        pecStatusChangeMatrixRepository.delete(id);
        pecStatusChangeMatrixSearchRepository.delete(id);
    }

    /**
     * Search for the pecStatusChangeMatrix corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PecStatusChangeMatrixDTO> search(String query) {
        log.debug("Request to search PecStatusChangeMatrices for query {}", query);
        return StreamSupport
            .stream(pecStatusChangeMatrixSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(pecStatusChangeMatrixMapper::toDto)
            .collect(Collectors.toList());
    }
}
