package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.RefBaremeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing RefBareme.
 */
public interface RefBaremeService {

    /**
     * Save a refBareme.
     *
     * @param refBaremeDTO the entity to save
     * @return the persisted entity
     */
    RefBaremeDTO save(MultipartFile file, RefBaremeDTO refBaremeDTO);
    AttachmentDTO saveAttachmentRefBareme(MultipartFile file, Long id, String label);
    AttachmentDTO updateAttachmentRefBareme(MultipartFile file, Long id, String label);
    /**
     *  Get all the refBaremes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Set<RefBaremeDTO> findAll();

    /**
     *  Get the "id" refBareme.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RefBaremeDTO findOne(Long id);

    /**
     *  Delete the "id" refBareme.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the refBareme corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RefBaremeDTO> search(String query, Pageable pageable);

    List<RefBaremeDTO> findBaremesWithoutPagination();
}
