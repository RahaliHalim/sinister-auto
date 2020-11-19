package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ReparateurGrilleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


/**
 * Service Interface for managing ReparateurGrille.
 */
public interface ReparateurGrilleService {

    /**
     * Save a reparateurGrille.
     *
     * @param ReparateurGrilleDTO the entity to save
     * @return the persisted entity
     */
    ReparateurGrilleDTO save(ReparateurGrilleDTO reparateurGrilleDTO);

    /**
     *  Get all the reparateurGrille.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReparateurGrilleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" ReparateurGrille.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReparateurGrilleDTO findOne(Long id);

    /**
     *  Delete the "id" ReparateurGrille.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    ReparateurGrilleDTO findByReparateurAndGrille(Long grilleId, Long reparateurId);

    List<ReparateurGrilleDTO> findByReparateur(Long reparateurId);

    ReparateurGrilleDTO getByTypeInterventionAndReparateur(Long refTypeInterventionId, Long reparateurId);
}
