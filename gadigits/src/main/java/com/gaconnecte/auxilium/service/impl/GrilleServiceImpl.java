package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.GrilleService;
import com.gaconnecte.auxilium.domain.Grille;
import com.gaconnecte.auxilium.repository.GrilleRepository;
import com.gaconnecte.auxilium.repository.search.GrilleSearchRepository;
import com.gaconnecte.auxilium.service.dto.GrilleDTO;
import com.gaconnecte.auxilium.service.mapper.GrilleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Grille.
 */
@Service
@Transactional
public class GrilleServiceImpl implements GrilleService{

    private final Logger log = LoggerFactory.getLogger(GrilleServiceImpl.class);

    private final GrilleRepository grilleRepository;

    private final GrilleMapper grilleMapper;

    private final GrilleSearchRepository grilleSearchRepository;

    public GrilleServiceImpl(GrilleRepository grilleRepository, GrilleMapper grilleMapper, GrilleSearchRepository grilleSearchRepository) {
        this.grilleRepository = grilleRepository;
        this.grilleMapper = grilleMapper;
        this.grilleSearchRepository = grilleSearchRepository;
    }

    /**
     * Save a grille.
     *
     * @param grilleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GrilleDTO save(GrilleDTO grilleDTO) {
        log.debug("Request to save Grille : {}", grilleDTO);
        Grille grille = grilleMapper.toEntity(grilleDTO);
        grille = grilleRepository.save(grille);
        GrilleDTO result = grilleMapper.toDto(grille);
        grilleSearchRepository.save(grille);
        return result;
    }

    /**
     *  Get all the grilles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GrilleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Grilles");
        return grilleRepository.findAll(pageable)
            .map(grilleMapper::toDto);
    }

    /**
     *  Get one grille by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GrilleDTO findOne(Long id) {
        log.debug("Request to get Grille : {}", id);
        Grille grille = grilleRepository.findOne(id);
        return grilleMapper.toDto(grille);
    }

    /**
     *  Delete the  grille by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Grille : {}", id);
        grilleRepository.delete(id);
        grilleSearchRepository.delete(id);
    }

    /**
     * Search for the grille corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GrilleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Grilles for query {}", query);
        Page<Grille> result = grilleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(grilleMapper::toDto);
    }
}
