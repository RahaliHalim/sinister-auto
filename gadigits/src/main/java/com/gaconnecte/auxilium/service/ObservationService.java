package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ObservationDTO;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Observation.
 */
public interface ObservationService {

    /**
     * Save a observation.
     *
     * @param observationDTO the entity to save
     * @return the persisted entity
     */
    ObservationDTO save(ObservationDTO observationDTO);

    /**
     *  Get all the observations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<ObservationDTO> findAll(String login);
    List<ObservationDTO> findAll();
    /**
     *  Get the "id" observation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ObservationDTO findOne(Long id);

    /**
     *  Delete the "id" observation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<ObservationDTO> findObservationByPrestation( Long prestationId);

    Page<ObservationDTO> findObservationByDevis(Pageable pageable, Long devisId);
    List<ObservationDTO> findByPrestation(String login);
    List<ObservationDTO> findBySinisterPec(String login);


}