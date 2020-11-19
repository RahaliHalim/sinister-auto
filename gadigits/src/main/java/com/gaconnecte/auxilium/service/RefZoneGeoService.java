package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefZoneGeoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RefZoneGeo.
 */
public interface RefZoneGeoService {

    /**
     * Save a refZoneGeo.
     *
     * @param refZoneGeoDTO the entity to save
     * @return the persisted entity
     */
    RefZoneGeoDTO save(RefZoneGeoDTO refZoneGeoDTO);

    /**
     *  Get all the refZoneGeos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefZoneGeoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refZoneGeo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefZoneGeoDTO findOne(Long id);

    /**
     *  Delete the "id" refZoneGeo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refZoneGeo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefZoneGeoDTO> search(String query, Pageable pageable);
}
