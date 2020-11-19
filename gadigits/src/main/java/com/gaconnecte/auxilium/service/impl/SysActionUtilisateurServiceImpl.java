package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.SysActionUtilisateurService;
import com.gaconnecte.auxilium.domain.SysActionUtilisateur;
import com.gaconnecte.auxilium.repository.SysActionUtilisateurRepository;
import com.gaconnecte.auxilium.repository.search.SysActionUtilisateurSearchRepository;
import com.gaconnecte.auxilium.service.dto.SysActionUtilisateurDTO;
import com.gaconnecte.auxilium.service.mapper.SysActionUtilisateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SysActionUtilisateur.
 */
@Service
@Transactional
public class SysActionUtilisateurServiceImpl implements SysActionUtilisateurService {

    private final Logger log = LoggerFactory.getLogger(SysActionUtilisateurServiceImpl.class);

    private final SysActionUtilisateurRepository sysActionUtilisateurRepository;

    private final SysActionUtilisateurMapper sysActionUtilisateurMapper;

    private final SysActionUtilisateurSearchRepository sysActionUtilisateurSearchRepository;

    public SysActionUtilisateurServiceImpl(SysActionUtilisateurRepository sysActionUtilisateurRepository, SysActionUtilisateurMapper sysActionUtilisateurMapper, SysActionUtilisateurSearchRepository sysActionUtilisateurSearchRepository) {
        this.sysActionUtilisateurRepository = sysActionUtilisateurRepository;
        this.sysActionUtilisateurMapper = sysActionUtilisateurMapper;
        this.sysActionUtilisateurSearchRepository = sysActionUtilisateurSearchRepository;
    }

    /**
     * Save a sysActionUtilisateur.
     *
     * @param sysActionUtilisateurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SysActionUtilisateurDTO save(SysActionUtilisateurDTO sysActionUtilisateurDTO) {
        log.debug("Request to save SysActionUtilisateur : {}", sysActionUtilisateurDTO);
        SysActionUtilisateur sysActionUtilisateur = sysActionUtilisateurMapper.toEntity(sysActionUtilisateurDTO);
        sysActionUtilisateur = sysActionUtilisateurRepository.save(sysActionUtilisateur);
        SysActionUtilisateurDTO result = sysActionUtilisateurMapper.toDto(sysActionUtilisateur);
        sysActionUtilisateurSearchRepository.save(sysActionUtilisateur);
        return result;
    }

    /**
     * Save a sysActionUtilisateur.
     *
     * @param sysActionUtilisateurDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public SysActionUtilisateurDTO update(SysActionUtilisateurDTO sysActionUtilisateurDTO) {
        log.debug("Request to save SysActionUtilisateur : {}", sysActionUtilisateurDTO);
        SysActionUtilisateur sysActionUtilisateur = sysActionUtilisateurMapper.toEntity(sysActionUtilisateurDTO);
            sysActionUtilisateur = sysActionUtilisateurRepository.save(sysActionUtilisateur);
            SysActionUtilisateurDTO result = sysActionUtilisateurMapper.toDto(sysActionUtilisateur);
            sysActionUtilisateurSearchRepository.save(sysActionUtilisateur);
            return result;
    }

    /**
     * Get all the sysActionUtilisateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysActionUtilisateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysActionUtilisateurs");
        return sysActionUtilisateurRepository.findAll(pageable)
            .map(sysActionUtilisateurMapper::toDto);
    }

    /**
     * Get one sysActionUtilisateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysActionUtilisateurDTO findOne(Long id) {
        log.debug("Request to get SysActionUtilisateur : {}", id);
        SysActionUtilisateur sysActionUtilisateur = sysActionUtilisateurRepository.findOneWithEagerRelationships(id);
        return sysActionUtilisateurMapper.toDto(sysActionUtilisateur);
    }


    /**
     * Get one sysActionUtilisateur by name.
     *
     * @param name the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysActionUtilisateurDTO findByName(String nom) {
        log.debug("Request to get SysActionUtilisateur : {}", nom);
        SysActionUtilisateur sysActionUtilisateur = sysActionUtilisateurRepository.findActionByName(nom);
        return sysActionUtilisateurMapper.toDto(sysActionUtilisateur);
    }

    /**
     * Delete the  sysActionUtilisateur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysActionUtilisateur : {}", id);
        sysActionUtilisateurRepository.delete(id);
        sysActionUtilisateurSearchRepository.delete(id);
    }

    /**
     * Search for the sysActionUtilisateur corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysActionUtilisateurDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SysActionUtilisateurs for query {}", query);
        Page<SysActionUtilisateur> result = sysActionUtilisateurSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(sysActionUtilisateurMapper::toDto);
    }
}
