package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Apec;
import com.gaconnecte.auxilium.service.dto.ApecDTO;

import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import java.util.Set;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Service Interface for managing Apec.
 */
public interface ApecService {

    /**
     * Save a apec.
     *
     * @param apecDTO the entity to save
     * @return the persisted entity
     */
    ApecDTO save(ApecDTO apecDTO);

    /**
     * Get all the apecs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApecDTO> findAll(Pageable pageable);

    /**
     * Get the "id" apec.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ApecDTO findOne(Long id);

    Set<ApecDTO> findByStateAccord(Long userId, Integer etat);

    ApecDTO findAccordBySinPecAndEtatFixe(Long id);

    Set<SinisterPecDTO> findAllSinPecWithValidAccord(Long id);

    Set<ApecDTO> findValidAccordBySinPec(Long id);

    /**
     * Delete the "id" apec.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the apec corresponding to the query.
     *
     * @param query    the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApecDTO> search(String query, Pageable pageable);

    List<ApecDTO> findAllAccordByDecision(Integer etat);

    List<ApecDTO> findAllAccordByStatus(Integer etat);

    ApecDTO findAccordByQuotation(Long id);

    ApecDTO findAccordBySinPecAndEtat(Long id, Integer etat);

    Set<ApecDTO> findAccordBySinPec(Long id);

    Integer findLastApecNumber();

    public Set<ApecDTO> findValidAccordBySinPecForBonSortie(Long id);

    public Set<ApecDTO> findAccordByIdDevis(Long quotationId);

    public Set<ApecDTO> findListAccordByQuotation(Long id);

}
