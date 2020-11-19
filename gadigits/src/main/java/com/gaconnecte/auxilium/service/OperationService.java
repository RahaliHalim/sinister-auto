package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Operation;
import com.gaconnecte.auxilium.repository.OperationRepository;
import com.gaconnecte.auxilium.repository.search.OperationSearchRepository;
import com.gaconnecte.auxilium.service.dto.OperationDTO;
import com.gaconnecte.auxilium.service.mapper.OperationMapper;
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
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationService.class);

    private final OperationRepository operationRepository;

    private final OperationMapper operationMapper;

    private final OperationSearchRepository operationSearchRepository;

    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper, OperationSearchRepository operationSearchRepository) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
        this.operationSearchRepository = operationSearchRepository;
    }

    /**
     * Save a operation.
     *
     * @param operationDTO the entity to save
     * @return the persisted entity
     */
    public OperationDTO save(OperationDTO operationDTO) {
        log.debug("Request to save Operation : {}", operationDTO);
        Operation operation = operationMapper.toEntity(operationDTO);
        operation = operationRepository.save(operation);
        OperationDTO result = operationMapper.toDto(operation);
        operationSearchRepository.save(operation);
        return result;
    }

    /**
     *  Get all the operations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> findAll() {
        log.debug("Request to get all Operations");
        return operationRepository.findAll().stream()
            .map(operationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<OperationDTO> findAllByCode(String code) {
        log.debug("Request to get all Operations by code {}", code);
        return operationRepository.findAllByCode(code).stream()
            .map(operationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one operation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public OperationDTO findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        Operation operation = operationRepository.findOne(id);
        return operationMapper.toDto(operation);
    }

    /**
     *  Delete the  operation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.delete(id);
        operationSearchRepository.delete(id);
    }

    /**
     * Search for the operation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> search(String query) {
        log.debug("Request to search Operations for query {}", query);
        return StreamSupport
            .stream(operationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(operationMapper::toDto)
            .collect(Collectors.toList());
    }
}
