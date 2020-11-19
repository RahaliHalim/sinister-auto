package com.gaconnecte.auxilium.service.prm.impl;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.app.Attachment;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.prm.Convention;
import com.gaconnecte.auxilium.domain.prm.SinisterTypeSetting;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import com.gaconnecte.auxilium.repository.referential.RefPackRepository;
import com.gaconnecte.auxilium.repository.prm.ConventionRepository;
import com.gaconnecte.auxilium.repository.prm.ConventionAmendmentRepository;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.service.prm.ConventionService;
import com.gaconnecte.auxilium.service.prm.dto.ConventionDTO;
import com.gaconnecte.auxilium.service.dto.PartnerModeMappingDTO;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.dto.PartnerModeMappingDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.prm.mapper.ConventionMapper;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.prm.ApecSettings;
import com.gaconnecte.auxilium.service.PartnerService;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing Convention.
 */
@Service
@Transactional
public class ConventionServiceImpl implements ConventionService {

    private final Logger log = LoggerFactory.getLogger(ConventionServiceImpl.class);

    private final ConventionRepository conventionRepository;

    private final FileStorageService fileStorageService;
    
    private final ConventionMapper conventionMapper;

    private final PartnerService partnerService;

    private final RefPackRepository refPackRepository;

    public ConventionServiceImpl(ConventionRepository conventionRepository, ConventionMapper conventionMapper, FileStorageService fileStorageService, PartnerService partnerService, RefPackRepository refPackRepository) {
        this.conventionRepository = conventionRepository;
        this.conventionMapper = conventionMapper;
        this.fileStorageService = fileStorageService;
        this.partnerService = partnerService;
        this.refPackRepository = refPackRepository;
    }

    /**
     * Save a convention.
     *
     * @param conventionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConventionDTO save(MultipartFile signedConvention, ConventionDTO conventionDTO) {
        log.debug("Request to save Convention : {}", conventionDTO);
        Convention convention = conventionMapper.toEntity(conventionDTO);
        // Treatment of packs list
        if(CollectionUtils.isNotEmpty(convention.getPacks())){
            for(RefPack pack : convention.getPacks()) {
                pack.setConvention(convention);
                if(CollectionUtils.isNotEmpty(pack.getSinisterTypeSettings())){
                    for(SinisterTypeSetting setting : pack.getSinisterTypeSettings()) {
                        setting.setPack(pack);
                    }
                }
                if(CollectionUtils.isNotEmpty(pack.getApecSettings())){
                    for(ApecSettings setting : pack.getApecSettings()) {
                        setting.setPack(pack);
                    }
                }
            }
        }
        
        // Treatment of attachment file
        if(signedConvention != null) {
            String name = "";
            try {
                name = fileStorageService.storeFile(signedConvention, Constants.ENTITY_CONVENTION);
            } catch(Exception ex) {
                // TODO: treat the exception
                log.error("Error when saving file", ex);
            }

            Attachment signedConventionAtt = new Attachment();
            signedConventionAtt.setCreationDate(LocalDateTime.now());
            signedConventionAtt.setEntityName(Constants.ENTITY_CONVENTION);
            signedConventionAtt.setOriginal(Boolean.FALSE);
            signedConventionAtt.setOriginalName(signedConvention.getOriginalFilename());
            signedConventionAtt.setName(name);
            signedConventionAtt.setPath(Constants.ENTITY_CONVENTION);
            convention.setAttachment(signedConventionAtt);
        }
        
        convention = conventionRepository.save(convention);
        ConventionDTO result = conventionMapper.toDto(convention);
        return result;
    }

    /**
     *  Get all the conventions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConventionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Conventions");
        return conventionRepository.findAll(pageable)
            .map(conventionMapper::toDto);
    }

     @Override
    @Transactional(readOnly = true)
    public Set<PartnerModeMappingDTO> findPackByPartner(Long[] partnersId){
      log.debug("Request to get all pack by partner");
       Set<PartnerModeMappingDTO> listPartnerModeMapping = new HashSet<>();
        for(Long partnerId: partnersId){
                PartnerDTO partnerDTO = partnerService.findOne(partnerId);
                Set<RefPack> packs = conventionRepository.findPackByPartner(partnerId);
                Set<RefPack> packsAmendment = refPackRepository.findAllByPartnerIdAndAmendment(partnerId);
                Set<RefPack> combinedPack = Stream
                                            .concat(packs.stream(), packsAmendment.stream())
                                            .collect(Collectors.toSet());
            if(CollectionUtils.isNotEmpty(combinedPack)) {
                for(RefPack pack: combinedPack){
                    for (RefModeGestion mode: pack.getModeGestions()){
                    PartnerModeMappingDTO partnerModeMappingDTO  = new PartnerModeMappingDTO();
                        partnerModeMappingDTO.setModeId(mode.getId());
                        partnerModeMappingDTO.setLabelPartnerMode(mode.getLibelle()+" ( "+partnerDTO.getCompanyName() +" )");
                        partnerModeMappingDTO.setPartnerMode(partnerId.toString().concat((mode.getId()).toString()));
                        partnerModeMappingDTO.setPartnerId(partnerId); 
                        listPartnerModeMapping.add(partnerModeMappingDTO);
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(listPartnerModeMapping)) {
        	return listPartnerModeMapping;
        }
        return new HashSet<>();
        
    }
     
     @Override
     @Transactional(readOnly = true)
     public Set<RefModeGestionDTO> findModesByPartner(Long partnerId){
       log.debug("Request to get all mode gestion by partner");
        Set<RefModeGestionDTO> listPartnerModeMapping = new HashSet<>();
      
                 PartnerDTO partnerDTO = partnerService.findOne(partnerId);
                 Set<RefPack> packs = conventionRepository.findPackByPartner(partnerId);
                 Set<RefPack> packsAmendment = refPackRepository.findAllByPartnerIdAndAmendment(partnerId);
                 Set<RefPack> combinedPack = Stream
                                             .concat(packs.stream(), packsAmendment.stream())
                                             .collect(Collectors.toSet());
             if(CollectionUtils.isNotEmpty(combinedPack)) {
                 for(RefPack pack: combinedPack){
                     for (RefModeGestion mode: pack.getModeGestions()){
                    	 RefModeGestionDTO refModeGestionDTO  = new RefModeGestionDTO();
                    	 refModeGestionDTO.setDescription(mode.getDescription());
                    	 refModeGestionDTO.setId(mode.getId());
                    	 refModeGestionDTO.setLibelle(mode.getLibelle());
                         listPartnerModeMapping.add(refModeGestionDTO);
                     }
                 }
             }
         
         if(CollectionUtils.isNotEmpty(listPartnerModeMapping)) {
         	return listPartnerModeMapping;
         }
         return new HashSet<>();
         
     }
     
     
     

    /**
     *  Get all the conventions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<ConventionDTO> findAll() {
        log.debug("Request to get all Conventions");
        Set<Convention> packs = conventionRepository.findAllPacks();
        if(CollectionUtils.isNotEmpty(packs)) {
        	return packs.stream().map(conventionMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     *  Get one convention by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConventionDTO findOne(Long id) {
        log.debug("Request to get Convention : {}", id);
        Convention convention = conventionRepository.findOne(id);
        return conventionMapper.toDto(convention);
    }

    /**
     *  Delete the  convention by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Convention : {}", id);
        conventionRepository.delete(id);
    }

    /**
     *  Block the  convention by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void block(Long id) {
        log.debug("Request to delete Convention : {}", id);
        conventionRepository.delete(id);
    }

    /**
     * Search for the convention corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConventionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Conventions for query {}", query);
        return null;
    }
}
