package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.DesignationUsageContratDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DesignationUsageContrat.
 */
public interface DesignationUsageContratService {

    /**
     * Save a designationUsageContrat.
     *
     * @param designationUsageContratDTO the entity to save
     * @return the persisted entity
     */
    DesignationUsageContratDTO save(DesignationUsageContratDTO designationUsageContratDTO);

    /**
     *  Get all the designationUsageContrats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationUsageContratDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" designationUsageContrat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DesignationUsageContratDTO findOne(Long id);

    /**
     *  Delete the "id" designationUsageContrat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the designationUsageContrat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DesignationUsageContratDTO> search(String query, Pageable pageable);
    
    DesignationUsageContratDTO findOneByCompagnieIdAndUsageContratiD(Long compagnieId,Long refUsageContratId);
}
