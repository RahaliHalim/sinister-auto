package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.VisAVisService;
import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.repository.VisAVisRepository;
import com.gaconnecte.auxilium.repository.search.VisAVisSearchRepository;
import com.gaconnecte.auxilium.service.dto.VisAVisDTO;
import com.gaconnecte.auxilium.service.mapper.VisAVisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class VisAVisServiceImpl implements VisAVisService{

    private final Logger log = LoggerFactory.getLogger(VisAVisServiceImpl.class);

    private final VisAVisRepository visAVisRepository;

    private final VisAVisMapper visAVisMapper;

    private final VisAVisSearchRepository visAVisSearchRepository;

    public VisAVisServiceImpl(VisAVisRepository visAVisRepository, VisAVisMapper visAVisMapper, VisAVisSearchRepository visAVisSearchRepository) {
        this.visAVisRepository = visAVisRepository;
        this.visAVisMapper = visAVisMapper;
        this.visAVisSearchRepository = visAVisSearchRepository;
    }

    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VisAVisDTO save(VisAVisDTO visAVisDTO) {
        log.debug("Request to save Vis A vis  : {}", visAVisDTO);
        VisAVis visAVis = visAVisMapper.toEntity(visAVisDTO);
        visAVis = visAVisRepository.save(visAVis);
        VisAVisDTO result = visAVisMapper.toDto(visAVis);
        visAVisSearchRepository.save(visAVis);
        return result;
    }

    /**
     *  Get all the contacts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VisAVisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all vis a viss");
        return visAVisRepository.findAll(pageable)
            .map(visAVisMapper::toDto);
    }

    /**
     *  Get one contact by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VisAVisDTO findOne(Long id) {
        log.debug("Request to get vis a vis : {}", id);
        VisAVis visAVis = visAVisRepository.findOne(id);
        return visAVisMapper.toDto(visAVis);
    }

   

    /**
     *  Delete the  contact by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete vis a vis : {}", id);
        visAVisRepository.delete(id);
      
    }

    /**
     * Search for the contact corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VisAVisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of vis a vis for query {}", query);
        Page<VisAVis> result = visAVisSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(visAVisMapper::toDto);
    }

    
}
