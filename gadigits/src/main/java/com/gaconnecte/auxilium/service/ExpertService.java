package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Expert.
 */
public interface ExpertService {

    /**
     * Save a expert.
     *
     * @param expertDTO the entity to save
     * @return the persisted entity
     */
    ExpertDTO save(ExpertDTO expertDTO);

    /**
     *  Get all the experts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    List<ExpertDTO> findAll();

    /**
     *  Get the "id" expert.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    
    void historysaveExpert(String entityName, Long entityId, ExpertDTO oldValue, ExpertDTO newValue, String operationName);

    ExpertDTO findOne(Long id);
    ExpertDTO getExpCompanyByFTUSA(String numeroFTUSA,String pname);

    /**
     *  Delete the "id" expert.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the expert corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExpertDTO> search(String query, Pageable pageable);

    Page<ExpertDTO> findAllExpert(Pageable pageable);

    Page<ExpertDTO> findExpertByVille(Pageable pageable, Long villeId);
    /**
     *  Get all the experts of a selected Gouvernorat.
     *
     *  
     *  @return the list of entities
     */
    Page<ExpertDTO> findByGouvernorat(Pageable pageable,Long gouvernoratId);
    
    Set<ExpertDTO> findByGovernorate(Long governorateId, Long partnerId, Long modeId);

 
}
