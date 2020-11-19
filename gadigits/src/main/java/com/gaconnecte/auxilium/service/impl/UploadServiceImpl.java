package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ReferentielService;
import com.gaconnecte.auxilium.service.UploadService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.Upload;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.repository.UploadRepository;
import com.gaconnecte.auxilium.repository.search.UploadSearchRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.ReferentielDTO;
import com.gaconnecte.auxilium.service.dto.UploadDTO;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;

import com.gaconnecte.auxilium.service.mapper.UploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Tiers.
 */
@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    private final Logger log = LoggerFactory.getLogger(TiersServiceImpl.class);

    private final UploadRepository uploadRepository;

    private final UploadMapper uploadMapper;

    private final UploadSearchRepository uploadSearchRepository;
    
    @Autowired
    private UserService userService;
    @Autowired
    private ReferentielService referentielService;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    public UploadServiceImpl(UploadRepository uploadRepository, UploadMapper uploadMapper, UploadSearchRepository uploadSearchRepository,AttachmentMapper attachmentMapper ,AttachmentRepository attachmentRepository,FileStorageService fileStorageService) {
        this.uploadRepository = uploadRepository;
        this.uploadMapper = uploadMapper;
        this.uploadSearchRepository = uploadSearchRepository;
        this.attachmentMapper = attachmentMapper;
		this.attachmentRepository = attachmentRepository;
		this.fileStorageService = fileStorageService;
		
		
    }

    /**
     * Save a tiers.
     *
     * @param tiersDTO the entity to save
     * @return the persisted entity
     */
   

    /**
     *  Get all the tiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadDTO> findAll() {
        log.debug("Request to get all Uploads");
        return uploadRepository.findAll().stream()
                .map(uploadMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one tiers by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UploadDTO findOne(Long id) {
        log.debug("Request to get Uploads : {}", id);
        Upload upload = uploadRepository.findOne(id);
        return uploadMapper.toDto(upload);
    }

    /**
     *  Delete the  tiers by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Uploads : {}", id);
        uploadRepository.delete(id);
        uploadSearchRepository.delete(id);
    }

    /**
     * Search for the tiers corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Uploads for query {}", query);
        Page<Upload> result = uploadSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(uploadMapper::toDto);
    }

    @Override
    public UploadDTO save(MultipartFile casupload, UploadDTO uploadDTO) {
    	String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        log.debug("Request to save upload : {}", uploadDTO);
        Upload upload = uploadMapper.toEntity(uploadDTO);
        upload.setUser(user);
        upload.setDateUpload(LocalDateTime.now());
        upload.setDateExecution(LocalDateTime.now());
        upload = uploadRepository.save(upload);
        UploadDTO result = uploadMapper.toDto(upload);
         // Treatment of attachment file
        ReferentielDTO ref = referentielService.findOne(result.getReferentielId());
        if(casupload != null) {
            String name = "";
            try {
                name = fileStorageService.storeFileReferentiel(casupload, Constants.ENTITY_UPLOAD, ref.getLibelle());
            } catch(Exception ex) {
                log.error("Error when saving file", ex);
            }
          Attachment casUploadAttachment = new Attachment();
          casUploadAttachment.setCreationDate(LocalDateTime.now());
          casUploadAttachment.setEntityName(Constants.ENTITY_UPLOAD);
          casUploadAttachment.setEntityId(result.getId());
          casUploadAttachment.setOriginal(Boolean.FALSE);
          casUploadAttachment.setOriginalName(casupload.getOriginalFilename());
          casUploadAttachment.setName(name);
          casUploadAttachment.setLabel(ref.getLibelle());
          casUploadAttachment.setPath(Constants.ENTITY_UPLOAD);
          attachmentRepository.save(casUploadAttachment);
        }	
            return result;
    }

}