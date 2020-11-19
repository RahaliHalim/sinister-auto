package com.gaconnecte.auxilium.service.prm;

import java.util.Set;

import com.gaconnecte.auxilium.service.prm.dto.ConventionDTO;
import com.gaconnecte.auxilium.service.dto.PartnerModeMappingDTO;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing Convention.
 */
public interface ConventionService {

    /**
     * Save a convention.
     *
     * @param conventionDTO the entity to save
     * @return the persisted entity
     */
    ConventionDTO save(MultipartFile signedConvention, ConventionDTO conventionDTO);

    /**
     * Get all the conventions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConventionDTO> findAll(Pageable pageable);

    /**
     * Get all the conventions.
     *
     * @return the list of entities
     */
    Set<ConventionDTO> findAll();

    /**
     * Get the "id" convention.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConventionDTO findOne(Long id);

    /**
     *  Delete the "id" convention.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  block the "id" convention.
     *
     *  @param id the id of the entity
     */
    void block(Long id);

    /**
     * Search for the convention corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConventionDTO> search(String query, Pageable pageable);
    Set<RefModeGestionDTO> findModesByPartner(Long partnerId);

     Set<PartnerModeMappingDTO> findPackByPartner(Long[] partnersId);
}
