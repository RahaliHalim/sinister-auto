package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.ContactDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing Camion.
 */
public interface RefTugTruckService {

    /**
     * Save a camion.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    RefTugTruckDTO save(RefTugTruckDTO tarifDTO);

    /**
     *  Get all the camions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTugTruckDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" camion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTugTruckDTO findOne(Long id);

    /**
     *  Delete the "id" camion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    /**
     * Search for the contact corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefTugTruckDTO> search(String query, Pageable pageable);
    Page<RefTugTruckDTO> findByRefRemorqueur(Pageable pageable, Long id);
    
    /**
     *  Get the "id" of a Remorqueur and return the main Camion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefTugTruckDTO findMainCamion(Long refemorqueurId);
    
    Set<RefTugTruckDTO> findAllTrucksByServiceType(Long serviceTypeId);
    
    /**
     * Find truck by it registration number
     * @param immatriculation
     * @return the entity
     */
    RefTugTruckDTO findTrucksByImmatriculation(String immatriculation);
    
    Set<RefTugTruckDTO> findAllTrucksByServiceTypeAndByGovernorate(Long serviceTypeId, Long governorateId);
}


