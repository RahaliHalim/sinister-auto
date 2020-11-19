package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.BonSortieService;
import com.gaconnecte.auxilium.domain.BonSortie;
import com.gaconnecte.auxilium.repository.BonSortieRepository;
import com.gaconnecte.auxilium.repository.search.BonSortieSearchRepository;
import com.gaconnecte.auxilium.service.dto.BonSortieDTO;
import com.gaconnecte.auxilium.service.mapper.BonSortieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BonSortie.
 */
@Service
@Transactional
public class BonSortieServiceImpl implements BonSortieService{

    private final Logger log = LoggerFactory.getLogger(BonSortieServiceImpl.class);

    private final BonSortieRepository bonSortieRepository;

    private final BonSortieMapper bonSortieMapper;

    private final BonSortieSearchRepository bonSortieSearchRepository;

    public BonSortieServiceImpl(BonSortieRepository bonSortieRepository, BonSortieMapper bonSortieMapper, BonSortieSearchRepository bonSortieSearchRepository) {
        this.bonSortieRepository = bonSortieRepository;
        this.bonSortieMapper = bonSortieMapper;
        this.bonSortieSearchRepository = bonSortieSearchRepository;
    }

    /**
     * Save a bonSortie.
     *
     * @param bonSortieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BonSortieDTO save(BonSortieDTO bonSortieDTO) {
        log.debug("Request to save BonSortie : {}", bonSortieDTO);
        BonSortie bonSortie = bonSortieMapper.toEntity(bonSortieDTO);
        bonSortie = bonSortieRepository.save(bonSortie);
        BonSortieDTO result = bonSortieMapper.toDto(bonSortie);
        bonSortieSearchRepository.save(bonSortie);
        return result;
    }

    /**
     *  Get all the bonSorties.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BonSortieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonSorties");
        return bonSortieRepository.findAll(pageable)
            .map(bonSortieMapper::toDto);
    }

    /**
     *  Get one bonSortie by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BonSortieDTO findOne(Long id) {
        log.debug("Request to get BonSortie : {}", id);
        BonSortie bonSortie = bonSortieRepository.findOne(id);
        return bonSortieMapper.toDto(bonSortie);
    }

    /**
     *  Delete the  bonSortie by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BonSortie : {}", id);
        bonSortieRepository.delete(id);
        bonSortieSearchRepository.delete(id);
    }

    /**
     * Search for the bonSortie corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BonSortieDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BonSorties for query {}", query);
        Page<BonSortie> result = bonSortieSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(bonSortieMapper::toDto);
    }


    public Long findNewNumBonSortie() {
    	log.debug("Request to find New Bon De Sortie Number" );
    	return bonSortieRepository.findNewNumBonSortie();
    }

}
