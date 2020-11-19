package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefAgeVehiculeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing RefTva.
 */
public interface RefAgeVehiculeService {

    /**
     * Save a RefTva.
     *
     * @param RefTvaDTO the entity to save
     * @return the persisted entity
     */
  //  RefTvaDTO save(RefTvaDTO refTvaDTO);

    /**
     *  Get all the RefTvas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefAgeVehiculeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" refTva.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
   RefAgeVehiculeDTO findOne(Long id);

    /**
     *  Delete the "id" refTva.
     *
     *  @param id the id of the entity
     */
  //  void delete(Long id);

}
