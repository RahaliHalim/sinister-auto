package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.PersonneMoraleService;
import com.gaconnecte.auxilium.domain.PersonneMorale;
import com.gaconnecte.auxilium.repository.PersonneMoraleRepository;
import com.gaconnecte.auxilium.repository.search.PersonneMoraleSearchRepository;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
import com.gaconnecte.auxilium.service.mapper.PersonneMoraleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PersonneMorale.
 */
@Service
@Transactional
public class PersonneMoraleServiceImpl implements PersonneMoraleService{

    private final Logger log = LoggerFactory.getLogger(PersonneMoraleServiceImpl.class);

    private final PersonneMoraleRepository personneMoraleRepository;

    private final PersonneMoraleMapper personneMoraleMapper;

    private final PersonneMoraleSearchRepository personneMoraleSearchRepository;

    public PersonneMoraleServiceImpl(PersonneMoraleRepository personneMoraleRepository, PersonneMoraleMapper personneMoraleMapper, PersonneMoraleSearchRepository personneMoraleSearchRepository) {
        this.personneMoraleRepository = personneMoraleRepository;
        this.personneMoraleMapper = personneMoraleMapper;
        this.personneMoraleSearchRepository = personneMoraleSearchRepository;
    }

    /**
     * Save a personneMorale.
     *
     * @param personneMoraleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonneMoraleDTO save(PersonneMoraleDTO personneMoraleDTO) {
        log.debug("Request to save PersonneMorale : {}", personneMoraleDTO);
        PersonneMorale personneMorale = personneMoraleMapper.toEntity(personneMoraleDTO);
        personneMorale = personneMoraleRepository.save(personneMorale);
        PersonneMoraleDTO result = personneMoraleMapper.toDto(personneMorale);
        return result;
    }

    /**
     *  Get all the personneMorales.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonneMoraleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonneMorales");
        return personneMoraleRepository.findAll(pageable)
            .map(personneMoraleMapper::toDto);
    }

    /**
     *  Get one personneMorale by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonneMoraleDTO findOne(Long id) {
        log.debug("Request to get PersonneMorale : {}", id);
        PersonneMorale personneMorale = personneMoraleRepository.findOne(id);
        return personneMoraleMapper.toDto(personneMorale);
    }

    /**
     *  Delete the  personneMorale by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonneMorale : {}", id);
        personneMoraleRepository.delete(id);
    }

    /**
     * Search for the personneMorale corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonneMoraleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PersonneMorales for query {}", query);
        Page<PersonneMorale> result = personneMoraleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(personneMoraleMapper::toDto);
    }
}
