package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefBaremeService;
import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefBareme;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.repository.RefBaremeRepository;
import com.gaconnecte.auxilium.repository.search.RefBaremeSearchRepository;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.RefBaremeDTO;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.RefBaremeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import com.gaconnecte.auxilium.service.impl.FileStorageService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefBareme.
 */
@Service
@Transactional
public class RefBaremeServiceImpl implements RefBaremeService{

    private final Logger log = LoggerFactory.getLogger(RefBaremeServiceImpl.class);

    private final RefBaremeRepository refBaremeRepository;

    private final RefBaremeMapper refBaremeMapper;

    private final RefBaremeSearchRepository refBaremeSearchRepository;
    
    private final AttachmentMapper attachmentMapper;
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    public RefBaremeServiceImpl(RefBaremeRepository refBaremeRepository, RefBaremeMapper refBaremeMapper, RefBaremeSearchRepository refBaremeSearchRepository
    		,AttachmentMapper attachmentMapper ,AttachmentRepository attachmentRepository,FileStorageService fileStorageService) {
        this.refBaremeRepository = refBaremeRepository;
        this.refBaremeMapper = refBaremeMapper;
        this.refBaremeSearchRepository = refBaremeSearchRepository;
        this.attachmentMapper = attachmentMapper;
		this.attachmentRepository = attachmentRepository;
		this.fileStorageService = fileStorageService;
    }

    /**
     * Save a refBareme.
     *
     * @param refBaremeDTO the entity to save
     * @return the persisted entity
     */
    /*@Override
    public RefBaremeDTO save(RefBaremeDTO refBaremeDTO) {
        log.debug("Request to save RefBareme : {}", refBaremeDTO);
        RefBareme refBareme = refBaremeMapper.toEntity(refBaremeDTO);
        refBareme = refBaremeRepository.save(refBareme);
        RefBaremeDTO result = refBaremeMapper.toDto(refBareme);
        refBaremeSearchRepository.save(refBareme);
        return result;
    }*/

    @Override
    public RefBaremeDTO save(MultipartFile casBareme, RefBaremeDTO refBaremeDTO) {
        log.debug("Request to save RefBareme : {}", refBaremeDTO);
        RefBareme refBareme = refBaremeMapper.toEntity(refBaremeDTO);

         // Treatment of attachment file
        if(casBareme != null) {
            String name = "";
            try {
                name = fileStorageService.storePECFilesNw(casBareme, Constants.ENTITY_REFBAREME, refBaremeDTO.getNomImage());
            } catch(Exception ex) {
                // TODO: treat the exception
                log.error("Error when saving file", ex);
            }
          Attachment casBaremeAttachment = new Attachment();
            casBaremeAttachment.setCreationDate(LocalDateTime.now());
            casBaremeAttachment.setEntityName(Constants.ENTITY_REFBAREME);
            casBaremeAttachment.setOriginal(Boolean.FALSE);
            casBaremeAttachment.setOriginalName(casBareme.getOriginalFilename());
            casBaremeAttachment.setName(name);
            casBaremeAttachment.setLabel(Constants.ENTITY_REFBAREME);
            //casBaremeAttachment.setName(refBaremeDTO.getId().toString());
            casBaremeAttachment.setPath(Constants.ENTITY_REFBAREME);
            refBareme.setAttachment(casBaremeAttachment);
        }
            refBareme = refBaremeRepository.save(refBareme);
            RefBaremeDTO result = refBaremeMapper.toDto(refBareme);
            //refBaremeSearchRepository.save(refBareme);
            return result;
    }


    /**
     *  Get all the refBaremes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefBaremeDTO> findAll() {
        log.debug("Request to get all RefBaremes");
      
      Set<RefBareme> refBaremes = refBaremeRepository.findAllRefBaremes();
      if(CollectionUtils.isNotEmpty(refBaremes)) {
        return refBaremes.stream().map(refBaremeMapper::toDto).collect(Collectors.toSet());
      }
      return new HashSet<>();
    }

    /**
     *  Get one refBareme by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefBaremeDTO findOne(Long id) {
        log.debug("Request to get RefBareme : {}", id);
        RefBareme refBareme = refBaremeRepository.findOne(id);
        return refBaremeMapper.toDto(refBareme);
    }

    /**
     *  Delete the  refBareme by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefBareme : {}", id);
        refBaremeRepository.delete(id);
        refBaremeSearchRepository.delete(id);
    }
    
    public AttachmentDTO saveAttachmentRefBareme(MultipartFile file, Long id, String label) {
		log.debug("Request to save partner refBareme Atachements : {}" + id);
		RefBareme refBareme = refBaremeRepository.findOne(id);
		// Treatment of attachment file
		Attachment refBaremeAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storeFile(file, Constants.ENTITY_REFBAREME);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			refBaremeAttchment.setCreationDate(LocalDateTime.now());
			refBaremeAttchment.setEntityName(Constants.ENTITY_REFBAREME);
			refBaremeAttchment.setOriginal(Boolean.FALSE);
			refBaremeAttchment.setOriginalName(file.getOriginalFilename());
			refBaremeAttchment.setName(name);
			refBaremeAttchment.setEntityId(id);
			refBaremeAttchment.setPath(Constants.ENTITY_REFBAREME);
			refBaremeAttchment.setLabel(label);
			refBaremeAttchment = attachmentRepository.save(refBaremeAttchment);
		}
		AttachmentDTO result = attachmentMapper.toDto(refBaremeAttchment);
		return result;
	}
    
    public AttachmentDTO updateAttachmentRefBareme(MultipartFile file, Long id, String label) {
		log.debug("Request to update refBareme Atachements : {}" + id);
		
		Attachment refBaremeAttchment = attachmentRepository.findOne(id);
		// Treatment of attachment file
		if (file != null) {
			try {
				fileStorageService.updateFile(file, Constants.ENTITY_REFBAREME, refBaremeAttchment.getName());
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			refBaremeAttchment.setCreationDate(LocalDateTime.now());
			attachmentRepository.save(refBaremeAttchment);
		}
		
		AttachmentDTO result = attachmentMapper.toDto(refBaremeAttchment);

		return result;
	}

    /**
     * Search for the refBareme corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefBaremeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefBaremes for query {}", query);
        Page<RefBareme> result = refBaremeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refBaremeMapper::toDto);
    }

    @Override
    public List<RefBaremeDTO> findBaremesWithoutPagination() {
        log.debug("Request to get  all baremes");
        List<RefBareme> result = refBaremeRepository.findAll();
        return refBaremeMapper.toDto(result);
    }

}
