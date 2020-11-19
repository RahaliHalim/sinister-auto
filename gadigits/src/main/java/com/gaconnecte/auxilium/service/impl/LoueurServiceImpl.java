package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.LoueurService;
import com.gaconnecte.auxilium.domain.Loueur;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.domain.VehiculeLoueur;
import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.repository.LoueurRepository;
import com.gaconnecte.auxilium.repository.search.LoueurSearchRepository;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;
import com.gaconnecte.auxilium.service.mapper.LoueurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Loueur.
 */
@Service
@Transactional
public class LoueurServiceImpl implements LoueurService{

    private final Logger log = LoggerFactory.getLogger(LoueurServiceImpl.class);

    private final LoueurRepository loueurRepository;

    private final LoueurMapper loueurMapper;

    private final LoueurSearchRepository loueurSearchRepository;

    public LoueurServiceImpl(LoueurRepository loueurRepository, LoueurMapper loueurMapper, LoueurSearchRepository loueurSearchRepository) {
        this.loueurRepository = loueurRepository;
        this.loueurMapper = loueurMapper;
        this.loueurSearchRepository = loueurSearchRepository;
    }

    /**
     * Save a loueur.
     *
     * @param loueurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LoueurDTO save(LoueurDTO loueurDTO) {
        log.debug("Request to save Loueur : {}", loueurDTO);
        Loueur loueur = loueurMapper.toEntity(loueurDTO);
        Partner partner = null;
        Reparateur reparateur = null;
        RefRemorqueur remorqueur = null;

        
        if (loueur.getVisAViss() != null) {
            for (VisAVis visAVis : loueur.getVisAViss()) {
                visAVis.setLoueur(loueur);
                visAVis.setPartner(partner);
                visAVis.setReparateur(reparateur);
                visAVis.setRemorqueur(remorqueur);

            }
        }
        
        if (loueur.getVehicules() != null) {
        	
            for (VehiculeLoueur vh : loueur.getVehicules()) {
            	vh.setLoueur(loueur);
            }
        }
        


        loueur = loueurRepository.save(loueur);
        LoueurDTO result = loueurMapper.toDto(loueur);
        return result;
    }

    /**
     *  Get all the loueurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoueurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Loueurs");
        return loueurRepository.findAll(pageable)
            .map(loueurMapper::toDto);
    }

    /**
     *  Get one loueur by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LoueurDTO findOne(Long id) {
        log.debug("Request to get Loueur : {}", id);
        Loueur loueur = loueurRepository.findOne(id);
        return loueurMapper.toDto(loueur);
    }
    @Override
    @Transactional(readOnly = true)
    public List<LoueurDTO> findByGovernorate(Long id) {
        log.debug("Request to get Loueur : {}", id);       
        return loueurRepository.findByGovernorate (id).stream().map(loueurMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
    
    }

    /**
     *  Delete the  loueur by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Loueur : {}", id);
        loueurRepository.delete(id);
        loueurSearchRepository.delete(id);
    }

    /**
     * Search for the loueur corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoueurDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Loueurs for query {}", query);
        Page<Loueur> result = loueurSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(loueurMapper::toDto);
    }
}
