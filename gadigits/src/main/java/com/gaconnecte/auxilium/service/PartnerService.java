package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.Loueur;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.repository.PartnerRepository;
import com.gaconnecte.auxilium.repository.search.PartnerSearchRepository;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Partner.
 */
@Service
@Transactional
public class PartnerService {

    private final Logger log = LoggerFactory.getLogger(PartnerService.class);

    private final PartnerRepository partnerRepository;

    private final PartnerMapper partnerMapper;

    private final PartnerSearchRepository partnerSearchRepository;
    
    private final AttachmentMapper attachmentMapper;
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    public PartnerService(PartnerRepository partnerRepository, PartnerMapper partnerMapper, PartnerSearchRepository partnerSearchRepository,
    		AttachmentMapper attachmentMapper ,AttachmentRepository attachmentRepository,FileStorageService fileStorageService) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
        this.partnerSearchRepository = partnerSearchRepository;
        this.attachmentMapper = attachmentMapper;
		this.attachmentRepository = attachmentRepository;
		this.fileStorageService = fileStorageService;
    }

    /**
     * Save a partner.
     *
     * @param partnerDTO the entity to save
     * @return the persisted entity
     */
    public PartnerDTO save(PartnerDTO partnerDTO) {
        log.debug("Request to save Partner : {}", partnerDTO);
        Partner partner = partnerMapper.toEntity(partnerDTO);
        LocalDate dateCreation = LocalDate.now();
        Reparateur reparateur = null ;
        RefRemorqueur refRemorqueur =  null ;
        Loueur loueur =  null ;

        if(partner.getVisAViss()!= null){
            for(VisAVis visAVis : partner.getVisAViss()) {
            	visAVis.setPartner(partner);
            	visAVis.setReparateur(reparateur);
            	visAVis.setRemorqueur(refRemorqueur);
            	visAVis.setLoueur(loueur);

            }
        } 
        partner.setDateCreation(dateCreation);
        partner = partnerRepository.save(partner);
        PartnerDTO result = partnerMapper.toDto(partner);
        //partnerSearchRepository.save(partner);
        return result;
    }

    /**
     *  Get all the partners.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PartnerDTO> findAll() {
        log.debug("Request to get all Partners");
        return partnerRepository.findAll().stream()
            .map(partnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all companies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PartnerDTO> findAllCompanies() {
        log.debug("Request to get all companies");
        return partnerRepository.findAllByGenre(1).stream()
            .map(partnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get all dealers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PartnerDTO> findAllDealers() {
        log.debug("Request to get all dealers");
        return partnerRepository.findAllByGenre(2).stream()
            .map(partnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get one partner by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PartnerDTO findOne(Long id) {
        log.debug("Request to get Partner : {}", id);
        Partner partner = partnerRepository.findOne(id);
        return partnerMapper.toDto(partner);
    }
    
    @Transactional(readOnly = true)
    public PartnerDTO findByName(String compagnyName) {
        log.debug("Request to get Partner : {}", compagnyName);
        Partner partner = partnerRepository.findCompanyByName(compagnyName, 1);
        return partnerMapper.toDto(partner);
    }
    
    @Transactional(readOnly = true)
    public PartnerDTO findPartnerByName(String compagnyName) {
        log.debug("Request to get Partner : {}", compagnyName);
        Partner partner = partnerRepository.findPartnerByName(compagnyName);
        return partnerMapper.toDto(partner);
    }

    /**
     *  Delete the  partner by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Partner : {}", id);
        partnerRepository.delete(id);
        partnerSearchRepository.delete(id);
    }
    
    
	public AttachmentDTO saveAttachmentLogoCompany(MultipartFile file, Long id, String label,String nomImage) {
		log.debug("Request to save company logo Atachements : {}" + id);
		Partner partner = partnerRepository.findOne(id);
		// Treatment of attachment file
		Attachment logoAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_COMPANY,nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			logoAttchment.setCreationDate(LocalDateTime.now());
			logoAttchment.setEntityName(Constants.ENTITY_COMPANY);
			logoAttchment.setOriginal(Boolean.FALSE);
			logoAttchment.setOriginalName(file.getOriginalFilename());
			logoAttchment.setName(name);
			logoAttchment.setEntityId(id);
			logoAttchment.setPath(Constants.ENTITY_COMPANY);
			logoAttchment.setLabel(label);
			logoAttchment = attachmentRepository.save(logoAttchment);
			partner.setAttachment(logoAttchment);
		}
		AttachmentDTO result = attachmentMapper.toDto(logoAttchment);
		return result;
	}
	
	public AttachmentDTO updateAttachmentLogoCompany(MultipartFile file, Long id, String label) {
		log.debug("Request to update logo Atachements : {}" + id);
		
		Attachment logoAttchment = attachmentRepository.findOne(id);
		// Treatment of attachment file
		if (file != null) {
			try {
				fileStorageService.updateFile(file, Constants.ENTITY_COMPANY, logoAttchment.getName());
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			logoAttchment.setCreationDate(LocalDateTime.now());
			attachmentRepository.save(logoAttchment);
		}
		
		AttachmentDTO result = attachmentMapper.toDto(logoAttchment);

		return result;
	}
	
	public AttachmentDTO saveAttachmentLogoConcessionnaire(MultipartFile file, Long id, String label, String nomImage) {
		log.debug("Request to save concessionnaire logo Atachements : {}" + id);
		Partner partner = partnerRepository.findOne(id);
		// Treatment of attachment file
		Attachment logoAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_CONCESSIONNAIRE,nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			logoAttchment.setCreationDate(LocalDateTime.now());
			logoAttchment.setEntityName(Constants.ENTITY_CONCESSIONNAIRE);
			logoAttchment.setOriginal(Boolean.FALSE);
			logoAttchment.setOriginalName(file.getOriginalFilename());
			logoAttchment.setName(name);
			logoAttchment.setEntityId(id);
			logoAttchment.setPath(Constants.ENTITY_CONCESSIONNAIRE);
			logoAttchment.setLabel(label);
			logoAttchment = attachmentRepository.save(logoAttchment);
			partner.setAttachment(logoAttchment);
		}
		AttachmentDTO result = attachmentMapper.toDto(logoAttchment);
		return result;
	}
	
	public AttachmentDTO updateAttachmentLogoConcessionnaire(MultipartFile file, Long id, String label) {
		log.debug("Request to update logo Atachements : {}" + id);
		
		Attachment logoAttchment = attachmentRepository.findOne(id);
		// Treatment of attachment file
		if (file != null) {
			try {
				fileStorageService.updateFile(file, Constants.ENTITY_CONCESSIONNAIRE, logoAttchment.getName());
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			logoAttchment.setCreationDate(LocalDateTime.now());
			attachmentRepository.save(logoAttchment);
		}
		
		AttachmentDTO result = attachmentMapper.toDto(logoAttchment);

		return result;
	}
	
	public Page<AttachmentDTO> findAttachments(Pageable pageable, Long id) {
		log.debug("Request to find Attachments for partner {}", id);
		Page<Attachment> result = attachmentRepository.findAttachments(pageable, id);
		if (result != null)
			return result.map(attachmentMapper::toDto);
		else
			return null;
	}
	
	public PartnerDTO getCompanyByNameReg(String pname, String tradeRegister, Integer genre){
	      log.debug("Request to get PartnerDTO : {}", pname);
	      Partner partner = partnerRepository.findCompanyByNameReg(pname, tradeRegister, genre);
	       if (partner == null) {
	           return null;
	       }
	       return partnerMapper.toDto(partner);

	   }
	
	
	public PartnerDTO getDealerByNameReg(String pname, String tradeRegister, Integer genre){
	      log.debug("Request to get PartnerDTO : {}", pname);
	      Partner partner = partnerRepository.findDealerByNameReg(pname, tradeRegister, genre);
	       if (partner == null) {
	           return null;
	       }
	       return partnerMapper.toDto(partner);

	   }
	
    

    /**
     * Search for the partner corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PartnerDTO> search(String query) {
        log.debug("Request to search Partners for query {}", query);
        return StreamSupport
            .stream(partnerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(partnerMapper::toDto)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
	public void convertFileToBase64(PartnerDTO partnerDTO) {
		Set<Attachment> attachmentsCompany = attachmentRepository.findAttachments("concessionnaire",partnerDTO.getId());
		for(Attachment attachment : attachmentsCompany) {
			if(attachment.getLabel().equals("concessionnaire")) {
				File file = fileStorageService.loadFile("concessionnaire", attachment.getName());
				if(file != null) {
					try {
						Integer a = attachment.getName().lastIndexOf(".");
						String b = attachment.getName().substring(a + 1);
						partnerDTO.setConcessionnaireLogoAttachmentName(b);
						partnerDTO.setConcessionnaireLogoAttachment64(Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath())));
					} catch (Exception e) {
						System.out.println(e);
					}
                }
			}
	    }
		Set<Attachment> attachmentsConcess = attachmentRepository.findAttachments("company",partnerDTO.getId());
		for(Attachment attachment : attachmentsConcess) {
			if(attachment.getLabel().equals("company")) {
				File file = fileStorageService.loadFile("company", attachment.getName());
				if(file != null) {
					try {
						Integer a = attachment.getName().lastIndexOf(".");
						String b = attachment.getName().substring(a + 1);
						partnerDTO.setCompanyLogoAttachmentName(b);
						partnerDTO.setCompanyLogoAttachment64(Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath())));
					} catch (Exception e) {
						System.out.println(e);
					}
                }
			}
	    }
	}
}
