package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.RefBaremeDTO;
import com.gaconnecte.auxilium.service.dto.UploadDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing Tiers.
 */
public interface UploadService {

    /**
     * Save a tiers.
     *
     * @param tiersDTO the entity to save
     * @return the persisted entity
     */
	UploadDTO save(MultipartFile file, UploadDTO uploadDTO);
   /* AttachmentDTO saveAttachmentUpload(MultipartFile file, Long id, String label);
    AttachmentDTO updateAttachmentUpload(MultipartFile file, Long id, String label);*/

    /**
     *  Get all the tiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
	List<UploadDTO> findAll();

    /**
     *  Get the "id" tiers.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UploadDTO findOne(Long id);

    /**
     *  Delete the "id" tiers.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tiers corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UploadDTO> search(String query, Pageable pageable);
    
    
    
    
    
    
    

}