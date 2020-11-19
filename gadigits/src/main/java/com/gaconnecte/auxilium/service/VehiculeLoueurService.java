package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeLoueurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VehiculeLoueur.
 */
public interface VehiculeLoueurService {

    /**
     * Save a vehiculeLoueur.
     *
     * @param vehiculeLoueurDTO the entity to save
     * @return the persisted entity
     */
    VehiculeLoueurDTO save(VehiculeLoueurDTO vehiculeLoueurDTO);

    /**
     *  Get all the vehiculeLoueurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VehiculeLoueurDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" vehiculeLoueur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VehiculeLoueurDTO findOne(Long id);

    /**
     *  Delete the "id" vehiculeLoueur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the vehiculeLoueur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VehiculeLoueurDTO> search(String query, Pageable pageable);
    VehiculeLoueurDTO findVehiculesByImmatriculation(String immatriculation);
    Page<VehiculeLoueurDTO> findByLoueur(Pageable pageable, Long id);


}
