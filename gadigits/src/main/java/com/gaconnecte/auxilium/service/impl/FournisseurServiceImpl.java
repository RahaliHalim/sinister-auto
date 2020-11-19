package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.FournisseurService;
import com.gaconnecte.auxilium.domain.Fournisseur;
import com.gaconnecte.auxilium.repository.FournisseurRepository;
import com.gaconnecte.auxilium.repository.search.FournisseurSearchRepository;
import com.gaconnecte.auxilium.service.dto.FournisseurDTO;
import com.gaconnecte.auxilium.service.mapper.FournisseurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Fournisseur.
 */
@Service
@Transactional
public class FournisseurServiceImpl implements FournisseurService{

    private final Logger log = LoggerFactory.getLogger(FournisseurServiceImpl.class);

    private final FournisseurRepository fournisseurRepository;

    private final FournisseurMapper fournisseurMapper;

    private final FournisseurSearchRepository fournisseurSearchRepository;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, FournisseurMapper fournisseurMapper, FournisseurSearchRepository fournisseurSearchRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurMapper = fournisseurMapper;
        this.fournisseurSearchRepository = fournisseurSearchRepository;
    }

    /**
     * Save a fournisseur.
     *
     * @param fournisseurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FournisseurDTO save(FournisseurDTO fournisseurDTO) {
        log.debug("Request to save Fournisseur : {}", fournisseurDTO);
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDTO);
        fournisseur = fournisseurRepository.save(fournisseur);
        FournisseurDTO result = fournisseurMapper.toDto(fournisseur);
        fournisseurSearchRepository.save(fournisseur);
        return result;
    }

    /**
     *  Get all the fournisseurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FournisseurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fournisseurs");
        return fournisseurRepository.findAll(pageable)
            .map(fournisseurMapper::toDto);
    }

    /**
     *  Get one fournisseur by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FournisseurDTO findOne(Long id) {
        log.debug("Request to get Fournisseur : {}", id);
        Fournisseur fournisseur = fournisseurRepository.findOne(id);
        return fournisseurMapper.toDto(fournisseur);
    }

    /**
     *  Delete the  fournisseur by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fournisseur : {}", id);
        fournisseurRepository.delete(id);
        fournisseurSearchRepository.delete(id);
    }

    /**
     * Search for the fournisseur corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FournisseurDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fournisseurs for query {}", query);
        Page<Fournisseur> result = fournisseurSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(fournisseurMapper::toDto);
    }
}
