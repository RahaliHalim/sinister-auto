package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.ReportFrequencyRate;
import com.gaconnecte.auxilium.domain.view.ViewSinisterPrestation;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DemandPecDTO;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;

import java.time.LocalDate;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Service Interface for managing Sinister.
 */
public interface SinisterService {

    /**
     * Save a sinister.
     *
     * @param sinisterDTO the entity to save
     * @return the persisted entity
     */
    SinisterDTO save(SinisterDTO sinisterDTO);

    /**
     * Save a sinister.
     *
     * @param sinisterDTO the entity to save
     * @return the persisted entity
     */
    SinisterDTO canSave(SinisterDTO sinisterDTO, Long packId);

    /**
     * test duplicated sinister.
     *
     * @param sinisterDTO the entity to test
     * @return the persisted entity
     */
    SinisterDTO isDuplicatedSinister(SinisterDTO sinisterDTO);
    
    /**
     *  Get all the sinister.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterDTO> findAll();

        /**
     *  Get all the sinister.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<SinisterDTO> findReport2Sinisters(SearchDTO searchDTO);

    Set<SinisterDTO> findByVehicleRegistration(String vehicleRegistration);
    
    
    List<DemandPecDTO> getAllNewExternalDemands();

    /**
     *  Get the "id" sinister.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SinisterDTO findOne(Long id);

    /**
     *  Delete the "id" dossier.
     *
     *  @param id the id of the entity
     */
    Boolean delete(Long id);

    /**
     * Get sinister count
     * @return 
     */
    Long countSinister();
    
    List<SinisterDTO> findByContratId(Long id);
    
    SinisterDTO findByVehicleIdAndIncidentDateAndStatus(Long vehiculeId, LocalDate incidentDate, Long statusId);
    Set<ViewSinisterPecDTO> findViewPecByVehicleRegistration(Long vehiculeId);
    Set<ViewSinisterPrestationDTO> findViewPrestationsByVehicleRegistration(Long vehiculeId);
	Long getCountReport2ServicesWithFiltter(String filter,SearchDTO searchDTO);
	Long getCountReport2Services(SearchDTO searchDTO);
	Page<ReportFrequencyRateDTO> findAllReport2Services(String filter, Pageable pageable,SearchDTO searchDTO);



}
