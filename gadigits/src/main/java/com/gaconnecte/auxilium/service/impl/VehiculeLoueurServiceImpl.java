package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.VehiculeLoueurService;
import com.gaconnecte.auxilium.domain.RefTugTruck;
import com.gaconnecte.auxilium.domain.VehiculeLoueur;
import com.gaconnecte.auxilium.repository.VehiculeLoueurRepository;
import com.gaconnecte.auxilium.repository.search.VehiculeLoueurSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeLoueurDTO;
import com.gaconnecte.auxilium.service.mapper.VehiculeLoueurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VehiculeLoueur.
 */
@Service
@Transactional
public class VehiculeLoueurServiceImpl implements VehiculeLoueurService{

    private final Logger log = LoggerFactory.getLogger(VehiculeLoueurServiceImpl.class);

    private final VehiculeLoueurRepository vehiculeLoueurRepository;

    private final VehiculeLoueurMapper vehiculeLoueurMapper;

    private final VehiculeLoueurSearchRepository vehiculeLoueurSearchRepository;

    public VehiculeLoueurServiceImpl(VehiculeLoueurRepository vehiculeLoueurRepository, VehiculeLoueurMapper vehiculeLoueurMapper, VehiculeLoueurSearchRepository vehiculeLoueurSearchRepository) {
        this.vehiculeLoueurRepository = vehiculeLoueurRepository;
        this.vehiculeLoueurMapper = vehiculeLoueurMapper;
        this.vehiculeLoueurSearchRepository = vehiculeLoueurSearchRepository;
    }

    /**
     * Save a vehiculeLoueur.
     *
     * @param vehiculeLoueurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehiculeLoueurDTO save(VehiculeLoueurDTO vehiculeLoueurDTO) {
        log.debug("Request to save VehiculeLoueur : {}", vehiculeLoueurDTO);
        VehiculeLoueur vehiculeLoueur = vehiculeLoueurMapper.toEntity(vehiculeLoueurDTO);
        vehiculeLoueur = vehiculeLoueurRepository.save(vehiculeLoueur);
        VehiculeLoueurDTO result = vehiculeLoueurMapper.toDto(vehiculeLoueur);
        vehiculeLoueurSearchRepository.save(vehiculeLoueur);
        return result;
    }

    /**
     *  Get all the vehiculeLoueurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehiculeLoueurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehiculeLoueurs");
        return vehiculeLoueurRepository.findAll(pageable)
            .map(vehiculeLoueurMapper::toDto);
    }

    /**
     *  Get one vehiculeLoueur by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VehiculeLoueurDTO findOne(Long id) {
        log.debug("Request to get VehiculeLoueur : {}", id);
        VehiculeLoueur vehiculeLoueur = vehiculeLoueurRepository.findOne(id);
        return vehiculeLoueurMapper.toDto(vehiculeLoueur);
    }

    /**
     *  Delete the  vehiculeLoueur by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehiculeLoueur : {}", id);
        vehiculeLoueurRepository.delete(id);
        vehiculeLoueurSearchRepository.delete(id);
    }

    /**
     * Search for the vehiculeLoueur corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehiculeLoueurDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VehiculeLoueurs for query {}", query);
        Page<VehiculeLoueur> result = vehiculeLoueurSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(vehiculeLoueurMapper::toDto);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public VehiculeLoueurDTO findVehiculesByImmatriculation(String immatriculation) {
        log.debug("Request to get truck by it registration number : {}", immatriculation);
        VehiculeLoueur vehicule = vehiculeLoueurRepository.findVehiculesByImmatriculation(immatriculation);
        return vehiculeLoueurMapper.toDto(vehicule);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public Page<VehiculeLoueurDTO> findByLoueur(Pageable pageable, Long loueurId) {
        log.debug("Request to get vehicule : {}", loueurId);
        Page<VehiculeLoueur> vehicules = vehiculeLoueurRepository.findVehiculesByLoueur(pageable, loueurId);
        return vehicules.map(vehiculeLoueurMapper::toDto);

    }

}
