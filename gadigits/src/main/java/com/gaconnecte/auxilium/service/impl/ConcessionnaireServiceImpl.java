package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ConcessionnaireService;
import com.gaconnecte.auxilium.domain.Concessionnaire;
import com.gaconnecte.auxilium.repository.ConcessionnaireRepository;
import com.gaconnecte.auxilium.repository.search.ConcessionnaireSearchRepository;
import com.gaconnecte.auxilium.service.dto.ConcessionnaireDTO;
import com.gaconnecte.auxilium.service.mapper.ConcessionnaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Concessionnaire.
 */
@Service
@Transactional
public class ConcessionnaireServiceImpl implements ConcessionnaireService{

    private final Logger log = LoggerFactory.getLogger(ConcessionnaireServiceImpl.class);

    private final ConcessionnaireRepository concessionnaireRepository;

    private final ConcessionnaireMapper concessionnaireMapper;

    private final ConcessionnaireSearchRepository concessionnaireSearchRepository;

    public ConcessionnaireServiceImpl(ConcessionnaireRepository concessionnaireRepository, ConcessionnaireMapper concessionnaireMapper, ConcessionnaireSearchRepository concessionnaireSearchRepository) {
        this.concessionnaireRepository = concessionnaireRepository;
        this.concessionnaireMapper = concessionnaireMapper;
        this.concessionnaireSearchRepository = concessionnaireSearchRepository;
    }

    /**
     * Save a concessionnaire.
     *
     * @param concessionnaireDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConcessionnaireDTO save(ConcessionnaireDTO concessionnaireDTO) {
        log.debug("Request to save Concessionnaire : {}", concessionnaireDTO);
        Concessionnaire concessionnaire = concessionnaireMapper.toEntity(concessionnaireDTO);
        concessionnaire = concessionnaireRepository.save(concessionnaire);
        ConcessionnaireDTO result = concessionnaireMapper.toDto(concessionnaire);
        concessionnaireSearchRepository.save(concessionnaire);
        return result;
    }

    /**
     *  Get all the concessionnaires.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConcessionnaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Concessionnaires");
        return concessionnaireRepository.findAll(pageable)
            .map(concessionnaireMapper::toDto);
    }

    /**
     *  Get one concessionnaire by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConcessionnaireDTO findOne(Long id) {
        log.debug("Request to get Concessionnaire : {}", id);
        Concessionnaire concessionnaire = concessionnaireRepository.findOneWithEagerRelationships(id);
        return concessionnaireMapper.toDto(concessionnaire);
    }

    /**
     *  Delete the  concessionnaire by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concessionnaire : {}", id);
        concessionnaireRepository.delete(id);
        concessionnaireSearchRepository.delete(id);
    }

    /**
     * Search for the concessionnaire corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConcessionnaireDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Concessionnaires for query {}", query);
        Page<Concessionnaire> result = concessionnaireSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(concessionnaireMapper::toDto);
    }
}
