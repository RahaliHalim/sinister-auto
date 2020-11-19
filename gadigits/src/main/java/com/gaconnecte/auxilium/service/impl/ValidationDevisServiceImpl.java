package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ValidationDevisService;
import com.gaconnecte.auxilium.domain.ValidationDevis;
import com.gaconnecte.auxilium.repository.ValidationDevisRepository;
import com.gaconnecte.auxilium.repository.search.ValidationDevisSearchRepository;
import com.gaconnecte.auxilium.service.dto.ValidationDevisDTO;
import com.gaconnecte.auxilium.service.mapper.ValidationDevisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ValidationDevis.
 */
@Service
@Transactional
public class ValidationDevisServiceImpl implements ValidationDevisService{

    private final Logger log = LoggerFactory.getLogger(ValidationDevisServiceImpl.class);

    private final ValidationDevisRepository validationDevisRepository;

    private final ValidationDevisMapper validationDevisMapper;

    private final ValidationDevisSearchRepository validationDevisSearchRepository;

    public ValidationDevisServiceImpl(ValidationDevisRepository validationDevisRepository, ValidationDevisMapper validationDevisMapper, ValidationDevisSearchRepository validationDevisSearchRepository) {
        this.validationDevisRepository = validationDevisRepository;
        this.validationDevisMapper = validationDevisMapper;
        this.validationDevisSearchRepository = validationDevisSearchRepository;
    }

    /**
     * Save a validationDevis.
     *
     * @param validationDevisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ValidationDevisDTO save(ValidationDevisDTO validationDevisDTO) {
        log.debug("Request to save ValidationDevis : {}", validationDevisDTO);
        ValidationDevis validationDevis = validationDevisMapper.toEntity(validationDevisDTO);
        validationDevis = validationDevisRepository.save(validationDevis);
        ValidationDevisDTO result = validationDevisMapper.toDto(validationDevis);
        validationDevisSearchRepository.save(validationDevis);
        return result;
    }

    /**
     *  Get all the validationDevis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ValidationDevisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ValidationDevis");
        return validationDevisRepository.findAll(pageable)
            .map(validationDevisMapper::toDto);
    }

    /**
     *  Get one validationDevis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ValidationDevisDTO findOne(Long id) {
        log.debug("Request to get ValidationDevis : {}", id);
        ValidationDevis validationDevis = validationDevisRepository.findOne(id);
        return validationDevisMapper.toDto(validationDevis);
    }

    /**
     *  Delete the  validationDevis by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ValidationDevis : {}", id);
        validationDevisRepository.delete(id);
        validationDevisSearchRepository.delete(id);
    }

    /**
     * Search for the validationDevis corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ValidationDevisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ValidationDevis for query {}", query);
        Page<ValidationDevis> result = validationDevisSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(validationDevisMapper::toDto);
    }
}
