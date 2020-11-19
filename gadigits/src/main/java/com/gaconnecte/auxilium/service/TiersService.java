package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Tiers;
import com.gaconnecte.auxilium.service.dto.TiersDTO;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Service Interface for managing Tiers.
 */
public interface TiersService {

    /**
     * Save a tiers.
     *
     * @param tiersDTO the entity to save
     * @return the persisted entity
     */
    TiersDTO save(TiersDTO tiersDTO);

    /**
     *  Get all the tiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TiersDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tiers.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TiersDTO findOne(Long id);

    /**
     *  Delete the "id" tiers.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tiers corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TiersDTO> search(String query, Pageable pageable);

    Page<TiersDTO> findByPrestationPEC(Pageable pageable, Long prestationPecId);
    TiersDTO findByImmatriculation(String immatriculationTier);
    
    Set<TiersDTO> findTierssByImmatriculation(String immatriculationTier, Long clientId);
}
