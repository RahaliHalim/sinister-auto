package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ContratAssurance.
 */
public interface ContratAssuranceService {

    /**
     * Save a contratAssurance.
     *
     * @param contratAssuranceDTO the entity to save
     * @return the persisted entity
     */
    ContratAssuranceDTO save(ContratAssuranceDTO contratAssuranceDTO);

    /**
     *  Get all the contratAssurances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<ContratAssuranceDTO> findAll();
    
    Page<ContratAssuranceDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" contratAssurance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContratAssuranceDTO findOne(Long id);

    /**
     *  Get the "id" contratAssurance.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ContratAssuranceDTO findOneWithVehicles(Long id);

    /**
     *  Delete the "id" contratAssurance.
     *
     *  @param id the id of the entity
     */
    Boolean delete(Long id);

    Page<ContratAssuranceDTO> findByVehicule(Pageable pageable, Long vehiculeId);

    /**
     * Search for the contratAssurance corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ContratAssuranceDTO> search(String query, Pageable pageable);

    ContratAssuranceDTO findOneByImmatriculation(String immatriculationVehicule);

    Boolean findOneForTiersByImmatriculation(String immatriculationVehicule);

    ContratAssuranceDTO findContratAssuranceByVehicule (Long id);
  
    ContratAssuranceDTO findPolicyByNumber(String pnumber);
    
    ContratAssuranceDTO findByImmatriculationIgnoreFinValidite(String immatriculationVehicule);
    
    ContratAssuranceDTO findByContractNum(String numeroContrat);
}
