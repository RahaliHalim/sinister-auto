package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.SysActionUtilisateurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SysActionUtilisateur.
 */
public interface SysActionUtilisateurService {

    /**
     * Save a sysActionUtilisateur.
     *
     * @param sysActionUtilisateurDTO the entity to save
     * @return the persisted entity
     */
    SysActionUtilisateurDTO save(SysActionUtilisateurDTO sysActionUtilisateurDTO);

    /**
     *  Get all the sysActionUtilisateurs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SysActionUtilisateurDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" sysActionUtilisateur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysActionUtilisateurDTO findOne(Long id);

    /**
     *  Delete the "id" sysActionUtilisateur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sysActionUtilisateur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SysActionUtilisateurDTO> search(String query, Pageable pageable);


    SysActionUtilisateurDTO findByName(String nom);

    SysActionUtilisateurDTO update(SysActionUtilisateurDTO sysActionUtilisateurDTO);
}
