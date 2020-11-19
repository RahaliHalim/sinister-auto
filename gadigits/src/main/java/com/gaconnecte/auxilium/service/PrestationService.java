package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.PrestationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Prestation.
 */
public interface PrestationService {

    /**
     * Save a prestation.
     *
     * @param prestationDTO the entity to save
     * @return the persisted entity
     */
    PrestationDTO save(PrestationDTO prestationDTO);

    PrestationDTO update(PrestationDTO prestationDTO);

    /**
     *  Get all the prestations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PrestationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" prestation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PrestationDTO findOne(Long id);

    /**
     *  Delete the "id" prestation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<PrestationDTO> findByDossier(Pageable pageable, Long dossierId);

    Long countPrestation();

    /**
     * Get insured city by prestation
     * 
     * @param id the prestation id
     */
    DelegationDTO findInsuredCityByPrestation(Long id);

    /**
     * Get insured governorate by prestation
     * 
     * @param id the prestation id
     */
    GovernorateDTO findInsuredGovernorateByPrestation(Long id);

}
