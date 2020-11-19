package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.AmendmentType;
import com.gaconnecte.auxilium.repository.AmendmentTypeRepository;
import com.gaconnecte.auxilium.repository.search.AmendmentTypeSearchRepository;
import com.gaconnecte.auxilium.service.dto.AmendmentTypeDTO;
import com.gaconnecte.auxilium.service.mapper.AmendmentTypeMapper;
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
 * Service Implementation for managing AmendmentType.
 */
@Service
@Transactional
public class AmendmentTypeService {

    private final Logger log = LoggerFactory.getLogger(AmendmentTypeService.class);

    private final AmendmentTypeRepository amendmentTypeRepository;

    private final AmendmentTypeMapper amendmentTypeMapper;

    private final AmendmentTypeSearchRepository amendmentTypeSearchRepository;

    public AmendmentTypeService(AmendmentTypeRepository amendmentTypeRepository, AmendmentTypeMapper amendmentTypeMapper, AmendmentTypeSearchRepository amendmentTypeSearchRepository) {
        this.amendmentTypeRepository = amendmentTypeRepository;
        this.amendmentTypeMapper = amendmentTypeMapper;
        this.amendmentTypeSearchRepository = amendmentTypeSearchRepository;
    }

    /**
     * Save a amendmentType.
     *
     * @param amendmentTypeDTO the entity to save
     * @return the persisted entity
     */
    public AmendmentTypeDTO save(AmendmentTypeDTO amendmentTypeDTO) {
        log.debug("Request to save AmendmentType : {}", amendmentTypeDTO);
        AmendmentType amendmentType = amendmentTypeMapper.toEntity(amendmentTypeDTO);
        amendmentType = amendmentTypeRepository.save(amendmentType);
        AmendmentTypeDTO result = amendmentTypeMapper.toDto(amendmentType);
        amendmentTypeSearchRepository.save(amendmentType);
        return result;
    }

    /**
     *  Get all the amendmentTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AmendmentTypeDTO> findAll() {
        log.debug("Request to get all AmendmentTypes");
        return amendmentTypeRepository.findAll().stream()
            .map(amendmentTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one amendmentType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AmendmentTypeDTO findOne(Long id) {
        log.debug("Request to get AmendmentType : {}", id);
        AmendmentType amendmentType = amendmentTypeRepository.findOne(id);
        return amendmentTypeMapper.toDto(amendmentType);
    }

    /**
     *  Delete the  amendmentType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AmendmentType : {}", id);
        amendmentTypeRepository.delete(id);
        amendmentTypeSearchRepository.delete(id);
    }

    /**
     * Search for the amendmentType corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AmendmentTypeDTO> search(String query) {
        log.debug("Request to search AmendmentTypes for query {}", query);
        return StreamSupport
            .stream(amendmentTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(amendmentTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
