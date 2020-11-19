package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.BusinessEntity;
import com.gaconnecte.auxilium.repository.BusinessEntityRepository;
import com.gaconnecte.auxilium.repository.search.BusinessEntitySearchRepository;
import com.gaconnecte.auxilium.service.dto.BusinessEntityDTO;
import com.gaconnecte.auxilium.service.mapper.BusinessEntityMapper;
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
 * Service Implementation for managing BusinessEntity.
 */
@Service
@Transactional
public class BusinessEntityService {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityService.class);

    private final BusinessEntityRepository businessEntityRepository;

    private final BusinessEntityMapper businessEntityMapper;

    private final BusinessEntitySearchRepository businessEntitySearchRepository;

    public BusinessEntityService(BusinessEntityRepository businessEntityRepository, BusinessEntityMapper businessEntityMapper, BusinessEntitySearchRepository businessEntitySearchRepository) {
        this.businessEntityRepository = businessEntityRepository;
        this.businessEntityMapper = businessEntityMapper;
        this.businessEntitySearchRepository = businessEntitySearchRepository;
    }

    /**
     * Save a businessEntity.
     *
     * @param businessEntityDTO the entity to save
     * @return the persisted entity
     */
    public BusinessEntityDTO save(BusinessEntityDTO businessEntityDTO) {
        log.debug("Request to save BusinessEntity : {}", businessEntityDTO);
        BusinessEntity businessEntity = businessEntityMapper.toEntity(businessEntityDTO);
        businessEntity = businessEntityRepository.save(businessEntity);
        BusinessEntityDTO result = businessEntityMapper.toDto(businessEntity);
        businessEntitySearchRepository.save(businessEntity);
        return result;
    }

    /**
     *  Get all the businessEntities.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BusinessEntityDTO> findAll() {
        log.debug("Request to get all BusinessEntities");
        return businessEntityRepository.findAll().stream()
            .map(businessEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one businessEntity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BusinessEntityDTO findOne(Long id) {
        log.debug("Request to get BusinessEntity : {}", id);
        BusinessEntity businessEntity = businessEntityRepository.findOne(id);
        return businessEntityMapper.toDto(businessEntity);
    }

    /**
     *  Delete the  businessEntity by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessEntity : {}", id);
        businessEntityRepository.delete(id);
        businessEntitySearchRepository.delete(id);
    }

    /**
     * Search for the businessEntity corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BusinessEntityDTO> search(String query) {
        log.debug("Request to search BusinessEntities for query {}", query);
        return StreamSupport
            .stream(businessEntitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(businessEntityMapper::toDto)
            .collect(Collectors.toList());
    }
}
