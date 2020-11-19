package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Reparateur.
 */
public interface ReparateurService {

    /**
     * Save a reparateur.
     *
     * @param reparateurDTO the entity to save
     * @return the persisted entity
     */
    ReparateurDTO save(ReparateurDTO reparateurDTO);

    /**
     *  Get all the reparateurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<ReparateurDTO> findAll();

    /**
     *  Get the "id" reparateur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReparateurDTO findOne(Long id);
    
    /**
     *  Get the "id" expert.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    
    void historysaveReparateur(String entityName, Long entityId, ReparateurDTO oldValue, ReparateurDTO newValue, String operationName);
    
    ReparateurDTO getReparateurByRegistreCommerce(String registreCommerce,String pname);
    /**
     *  Delete the "id" reparateur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the reparateur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReparateurDTO> search(String query, Pageable pageable);
    
    /**
     *  Get all the reparateurs of a selected Gouvernorat.
     *
     *  
     *  @return the list of entities
     */
 List<ReparateurDTO> findByGouvernorat(Long id);



}
