package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Service Interface for managing SinisterPrestation.
 */
public interface SinisterPrestationService {

    /**
     * Save a SinisterPrestation.
     *
     * @param serviceDTO the entity to save
     * @return the persisted entity
     */
    SinisterPrestationDTO save(SinisterPrestationDTO serviceDTO);

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterPrestationDTO> findAll();

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterPrestationDTO> findInProgressService();

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterPrestationDTO> findClosedService();

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterPrestationDTO> findCanceledService();

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterPrestationDTO> findNotEligibleService();

    /**
     *  Get all the SinisterPrestation.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<ReportFollowUpAssistanceDTO> findReport1Services(SearchDTO searchDTO);
   
    /**
     *  Get the "id" SinisterPrestation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SinisterPrestationDTO findOne(Long id);

    /**
     *  Get Sinister from "id" SinisterPrestation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SinisterDTO findSinisterFromPrestation(Long id);
    
    Long getCountReport1Services(SearchDTO searchDTO);


    /**
     *  Delete the "id" SinisterPrestation.
     *
     *  @param id the id of the entity
     */
    Boolean delete(Long id);
    
    Set <SinisterPrestationDTO> findSinisterPrestationBySinisterId(Long id);
    
    Boolean canSave(Long contractId);
    
    Long getCountReport1ServicesWithFiltter(String filter, SearchDTO searchDTO);
    
    Long countPrestationVr();
    
    Page<ReportFollowUpAssistanceDTO> findAllReport1ServicesS(String filter, Pageable pageable, SearchDTO searchDTO);

}
