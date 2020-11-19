package com.gaconnecte.auxilium.service.referential;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;

/**
 * Service Interface for managing RefPack.
 */
public interface RefPackService {

    /**
     * Save a refPack.
     *
     * @param refPackDTO the entity to save
     * @return the persisted entity
     */
    RefPackDTO save(RefPackDTO refPackDTO);

    /**
     *  Get all the refPacks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefPackDTO> findAll(Pageable pageable);

    /**
     *  Get all the refPacks.
     *
     *  @return the list of entities
     */
    Set<RefPackDTO> findAll();

    /**
     *  Get all the refPacks.
     *
     *  @return the list of entities
     */
    Set<RefPackDTO> findAllByServiceType(Long id);

    /**
     *  Get all the packs by company.
     *
     *  @return the list of entities
     */
    Set<RefPackDTO> findAllPacksByCompany(Long id);
    
    Set<RefPackDTO> findAllPacksActifByCompany(Long id);
    
    /**
     *  Get the "id" refPack.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefPackDTO findOne(Long id);
    /**
     *  Get the "id" refPack and id user.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefPackDTO findOneModesByUser(Long id, Long idUser);

    /**
     *  Delete the "id" refPack.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  block the "id" refPack.
     *
     *  @param id the id of the entity
     */
    void block(Long id);

    /**
     * Search for the refPack corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefPackDTO> search(String query, Pageable pageable);
    
    /**
     * Test id service type is present for the pack with the "id".
     *
     * @param packId the id of the pack
     * @param serviceTypeId the service type id
     */
    boolean isServiceTypeAuthorized(Long packId, Long serviceTypeId);

}