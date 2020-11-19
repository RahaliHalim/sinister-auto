package com.gaconnecte.auxilium.service.prm.impl;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.prm.ApecSettings;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.prm.ConventionAmendment;
import com.gaconnecte.auxilium.domain.prm.SinisterTypeSetting;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import com.gaconnecte.auxilium.repository.prm.ConventionAmendmentRepository;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.service.prm.ConventionAmendmentService;
import com.gaconnecte.auxilium.service.prm.dto.ConventionAmendmentDTO;
import com.gaconnecte.auxilium.service.prm.mapper.ConventionAmendmentMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing ConventionAmendment.
 */
@Service
@Transactional
public class ConventionAmendmentServiceImpl implements ConventionAmendmentService {

    private final Logger log = LoggerFactory.getLogger(ConventionAmendmentServiceImpl.class);

    private final ConventionAmendmentRepository conventionAmendmentRepository;

    private final FileStorageService fileStorageService;
    
    private final ConventionAmendmentMapper conventionAmendmentMapper;

    public ConventionAmendmentServiceImpl(ConventionAmendmentRepository conventionAmendmentRepository, ConventionAmendmentMapper conventionAmendmentMapper, FileStorageService fileStorageService) {
        this.conventionAmendmentRepository = conventionAmendmentRepository;
        this.conventionAmendmentMapper = conventionAmendmentMapper;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Save a conventionAmendment.
     *
     * @param conventionAmendmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConventionAmendmentDTO save(MultipartFile signedConventionAmendment, ConventionAmendmentDTO conventionAmendmentDTO) {
        log.debug("Request to save ConventionAmendment : {}", conventionAmendmentDTO);
        ConventionAmendment conventionAmendment = conventionAmendmentMapper.toEntity(conventionAmendmentDTO);
        // Treatment of packs list
        if(conventionAmendment.getRefPack()!= null){
        	conventionAmendment.getRefPack().setAmendment(conventionAmendment);
                if(CollectionUtils.isNotEmpty(conventionAmendment.getRefPack().getSinisterTypeSettings())){
                    for(SinisterTypeSetting setting : conventionAmendment.getRefPack().getSinisterTypeSettings()) {
                        setting.setPack(conventionAmendment.getRefPack());
                    }
                }
                if(CollectionUtils.isNotEmpty(conventionAmendment.getRefPack().getApecSettings())){
                    for(ApecSettings setting : conventionAmendment.getRefPack().getApecSettings()) {
                        setting.setPack(conventionAmendment.getRefPack());
                    }
                }            
        }
        
        // Treatment of attachment file
        if(signedConventionAmendment != null) {
            String name = "";
            try {
                name = fileStorageService.storeFile(signedConventionAmendment, Constants.ENTITY_AMENDMENT);
            } catch(Exception ex) {
                // TODO: treat the exception
                log.error("Error when saving file", ex);
            }

            Attachment signedConventionAmendmentAtt = new Attachment();
            signedConventionAmendmentAtt.setCreationDate(LocalDateTime.now());
            signedConventionAmendmentAtt.setEntityName(Constants.ENTITY_AMENDMENT);
            signedConventionAmendmentAtt.setOriginal(Boolean.FALSE);
            signedConventionAmendmentAtt.setOriginalName(signedConventionAmendment.getOriginalFilename());
            signedConventionAmendmentAtt.setName(name);
            signedConventionAmendmentAtt.setPath(Constants.ENTITY_AMENDMENT);
            conventionAmendment.setAttachment(signedConventionAmendmentAtt);
        }
        log.debug(" save ConventionAmendmen 4");
        conventionAmendment = conventionAmendmentRepository.save(conventionAmendment);
        log.debug(" save ConventionAmendmen 5");
        ConventionAmendmentDTO result = conventionAmendmentMapper.toDto(conventionAmendment);
        return result;
    }

    /**
     *  Get all the conventionAmendments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConventionAmendmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConventionAmendments");
        return conventionAmendmentRepository.findAll(pageable)
            .map(conventionAmendmentMapper::toDto);
    }

    /**
     *  Get all the conventionAmendments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<ConventionAmendmentDTO> findAll() {
        log.debug("Request to get all ConventionAmendments");
        Set<ConventionAmendment> packs = conventionAmendmentRepository.findAllAmendment();
        if(CollectionUtils.isNotEmpty(packs)) {
        	return packs.stream().map(conventionAmendmentMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     *  Get one conventionAmendment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConventionAmendmentDTO findOne(Long id) {
        log.debug("Request to get ConventionAmendment : {}", id);
        ConventionAmendment conventionAmendment = conventionAmendmentRepository.findOne(id);
        return conventionAmendmentMapper.toDto(conventionAmendment);
    }

    /**
     *  Delete the  conventionAmendment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConventionAmendment : {}", id);
        conventionAmendmentRepository.delete(id);
    }

    /**
     *  Block the  conventionAmendment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void block(Long id) {
        log.debug("Request to delete ConventionAmendment : {}", id);
        conventionAmendmentRepository.delete(id);
    }

    /**
     * Search for the conventionAmendment corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConventionAmendmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConventionAmendments for query {}", query);
        return null;
    }
}
