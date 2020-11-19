package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.RaisonPec;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.User;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.SinisterPecRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.search.SinisterPecSearchRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.SinisterPecMapper;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;
import com.gaconnecte.auxilium.repository.DevisRepository;
import com.gaconnecte.auxilium.repository.QuotationRepository;
import com.gaconnecte.auxilium.repository.RaisonPecRepository;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.dao.KpiDao;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.TodoPrestationPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.SinisterMapper;
import com.gaconnecte.auxilium.Utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Service Implementation for managing Sinister.
 */
@Service
@Transactional
public class SinisterPecServiceImpl implements SinisterPecService {

	private final Logger log = LoggerFactory.getLogger(SinisterPecServiceImpl.class);
	private final SinisterPecRepository sinisterPecRepository;
	@Autowired
	UserExtraService userExtraService;
	
	@Autowired
	private QuotationRepository quotationRepository;
	
	private final SinisterPecMapper sinisterPecMapper;
	private final SinisterMapper sinisterMapper;
	private final UserRepository userRepository;
	private final SinisterPecSearchRepository sinisterPecSearchRepository;
	private final AttachmentMapper attachmentMapper;
	private final DevisRepository devisRepository;
	private final FileStorageService fileStorageService;
	private final AttachmentRepository attachmentRepository;
	private final KpiDao kpiDao;
	private final UserExtraRepository userExtraRepository;
	private final RaisonPecRepository raisonPecRepository;

	public SinisterPecServiceImpl(AttachmentMapper attachmentMapper, AttachmentRepository attachmentRepository,
			KpiDao kpiDao, SinisterPecRepository sinisterPecRepository, SinisterPecMapper sinisterPecMapper,
			SinisterMapper sinisterMapper, FileStorageService fileStorageService,
			SinisterPecSearchRepository sinisterPecSearchRepository, DevisRepository devisRepository,
			UserRepository userRepository, UserExtraRepository userExtraRepository, RaisonPecRepository raisonPecRepository) {
		this.sinisterPecRepository = sinisterPecRepository;
		this.sinisterPecSearchRepository = sinisterPecSearchRepository;
		this.sinisterPecMapper = sinisterPecMapper;
		this.userRepository = userRepository;
		this.kpiDao = kpiDao;
		this.attachmentMapper = attachmentMapper;
		this.attachmentRepository = attachmentRepository;
		this.fileStorageService = fileStorageService;
		this.devisRepository = devisRepository;
		this.sinisterMapper = sinisterMapper;
		this.userExtraRepository = userExtraRepository;
		this.raisonPecRepository = raisonPecRepository;
	}

	/**
	 * Save a sinisterPec.
	 *
	 * @param sinisterPecDTO the entity to save
	 * @return the persisted entity
	 */

	@Override
	public SinisterPecDTO save(SinisterPecDTO sinisterPecDTO) {
		log.debug("Request to save SinisterPec : {}", sinisterPecDTO);
		SinisterPec sinisterPec = sinisterPecMapper.toEntity(sinisterPecDTO);
		sinisterPec = sinisterPecRepository.save(sinisterPec);
		SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		return result;

	}

	/**
	 * Get all the sinisterPecs.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<SinisterPecDTO> findAll() {
		log.debug("Request to get all SinisterPecs");
		return sinisterPecRepository.findAll().stream().map(sinisterPecMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one sinisterPec by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public SinisterPecDTO findOne(Long id) {
		log.debug("Request to get SinisterPec : {}", id);
		Long partnerId = 0L;
        Long agencyId = 0L;
        Long expertId = 0L;
        Long reparateurId = 0L;
		String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        UserExtra userExtraDTO = userExtraRepository.findByUser(user.getId());
        if(userExtraDTO.getProfile().getId().equals(25L) || userExtraDTO.getProfile().getId().equals(26L)) {
        	partnerId = userExtraDTO.getPersonId();
        }else if(userExtraDTO.getProfile().getId().equals(23L) || userExtraDTO.getProfile().getId().equals(24L)) {
        	agencyId = userExtraDTO.getPersonId();
        }else if(userExtraDTO.getProfile().getId().equals(27L)) {
        	expertId = userExtraDTO.getPersonId();
        }else if(userExtraDTO.getProfile().getId().equals(28L)) {
        	reparateurId = userExtraDTO.getPersonId();
        }
		try {
			SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
			if(sinisterPec != null) {
				if(!partnerId.equals(0L)) {
					if(partnerId.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						return sinisterPecMapper.toDto(sinisterPec);
					}else {
						return new SinisterPecDTO();
					}
				}else if(!agencyId.equals(0L)) {
					if(agencyId.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						return sinisterPecMapper.toDto(sinisterPec);
					}else {
						return new SinisterPecDTO();
					}
					
				}else if(!expertId.equals(0L)) {
					if(expertId.equals(sinisterPec.getExpert().getId())) {
						return sinisterPecMapper.toDto(sinisterPec);
					}else {
						return new SinisterPecDTO();
					}
					
				}else if(!reparateurId.equals(0L)) {
					if(reparateurId.equals(sinisterPec.getReparateur().getId())) {
						return sinisterPecMapper.toDto(sinisterPec);
					}else {
						return new SinisterPecDTO();
					}
					
				}
				return sinisterPecMapper.toDto(sinisterPec);
			}else {
				return new SinisterPecDTO();
			}
			
		} catch (Exception e) {
			log.error("_______________________", e);
		}
		return null;

	}

	/**
	 * Delete the sinisterPec by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete SinisterPec : {}", id);
		sinisterPecRepository.delete(id);
		sinisterPecSearchRepository.delete(id);
	}

	/**
	 * Search for the sinisterPec corresponding to the query.
	 *
	 * @param query the query of the search
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<SinisterPecDTO> search(String query) {
		log.debug("Request to search SinisterPecs for query {}", query);
		return StreamSupport.stream(sinisterPecSearchRepository.search(queryStringQuery(query)).spliterator(), false)
				.map(sinisterPecMapper::toDto).collect(Collectors.toList());
	}

	/**
	 * Get all the prestationPECS.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Set<SinisterPecDTO> findAll() {
	 * log.debug("Request to get all PrestationPECS"); return
	 * sinisterPecRepository.findAll().map(sinisterPecMapper::toDto); }
	 */

	@Override
	public Page<AttachmentDTO> findAttachments(Pageable pageable, Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Page<Attachment> result = attachmentRepository.findAttachments(pageable, id);
		if (result != null)
			return result.map(attachmentMapper::toDto);
		else
			return null;
	}

	@Override
	public Set<AttachmentDTO> findImprimeAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Set<Attachment> result = attachmentRepository.findImprimeAttachments(id);
		if (result != null)
			return result.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
		else
			return null;
	}

	@Override
	public Set<AttachmentDTO> findAutresPiecesAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Set<Attachment> result = attachmentRepository.findAutresPiecesAttachments(id);
		if (result != null)
			return result.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
		else
			return null;
	}

	@Override
	public Set<AttachmentDTO> findReparationAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Set<Attachment> result = attachmentRepository.findReparationAttachments(id);
		if (result != null)
			return result.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
		else
			return null;
	}
	
	@Override
	public Set<AttachmentDTO> findExpertiseAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Set<Attachment> result = attachmentRepository.findExpertiseAttachments(id);
		if (result != null)
			return result.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
		else
			return null;
	}
	
	@Override
	public Set<AttachmentDTO> findPlusDossiersAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Set<Attachment> result = attachmentRepository.findPlusDossiersAttachments(id);
		if (result != null)
			return result.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
		else
			return null;
	}

	@Transactional(readOnly = true)
	public AttachmentDTO findBonSortieAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Attachment result = attachmentRepository.findBonSortieAttachments(id);
		return attachmentMapper.toDto(result);
	}

	@Transactional(readOnly = true)
	public AttachmentDTO findOrdreMissionAttachments(Long id) {
		log.debug("Request to find Attachments By ordre mission{}", id);
		List<Attachment> result = attachmentRepository.findOrdreMissionAttachments(id);
		Attachment res = result.get(0);
		return attachmentMapper.toDto(res);
	}

	@Transactional(readOnly = true)
	public AttachmentDTO findAttachmentByEntity(String entityName, Long entityId) {
		log.debug("Request to find Attachments By entity{}", entityId);
		if (attachmentRepository.findAttachmentsByEntity(entityName, entityId) != null) {
			Attachment result = attachmentRepository.findAttachmentsByEntity(entityName, entityId);
			return attachmentMapper.toDto(result);
		} else
			return null;
	}

	/**
	 * Save a Qutation Attachments.
	 *
	 * @param conventionDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public SinisterPecDTO saveAttachments(MultipartFile[] prestationFiles, Long id) {
		log.debug("Request to save prestation Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		if (prestationFiles != null) {
			String name = "";
			for (int i = 0; i < prestationFiles.length; i++) {
				try {
					name = fileStorageService.storeFile(prestationFiles[i], Constants.ENTITY_PRESTATION);
				} catch (Exception ex) {
					// TODO: treat the exception
					log.error("Error when saving file", ex);
				}
				Attachment signedPrestationAttchment = new Attachment();
				signedPrestationAttchment.setCreationDate(LocalDateTime.now());
				signedPrestationAttchment.setEntityName(Constants.ENTITY_PRESTATION);
				signedPrestationAttchment.setOriginal(Boolean.FALSE);
				signedPrestationAttchment.setOriginalName(prestationFiles[i].getOriginalFilename());
				signedPrestationAttchment.setName(name);
				signedPrestationAttchment.setEntityId(id);
				signedPrestationAttchment.setPath(Constants.ENTITY_PRESTATION);
				signedPrestationAttchment.setLabel(prestationFiles[i].getOriginalFilename());
				attachmentRepository.save(signedPrestationAttchment);
			}
		}
		sinisterPec = sinisterPecRepository.save(sinisterPec);
		SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		return result;
	}

	@Override
	public AttachmentDTO updateAttachmentPEC(MultipartFile file, Long id, String label) {
		log.debug("Request to update prestation constat Atachements : {}" + id);
		// PrestationPEC prestationPEC = prestationPECRepository.findOne(id);
		Attachment PrestationAttchment = attachmentRepository.findOne(id);
		// Treatment of attachment file
		if (file != null) {
			try {
				fileStorageService.updatePECFiles(file, Constants.ENTITY_PRESTATIONPEC, PrestationAttchment.getName());
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			attachmentRepository.save(PrestationAttchment);
		}
		// prestationPEC = prestationPECRepository.save(prestationPEC);
		// PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);

		return result;
	}

	@Override
	public AttachmentDTO updateAttachmentQuotation(MultipartFile file, Long id, String label) {
		log.debug("Request to update prestation constat Atachements : {}" + id);
		// PrestationPEC prestationPEC = prestationPECRepository.findOne(id);
		Attachment PrestationAttchment = attachmentRepository.findOne(id);
		// Treatment of attachment file
		if (file != null) {
			try {
				fileStorageService.updatePECFiles(file, Constants.ENTITY_QUOTATION, PrestationAttchment.getName());
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			attachmentRepository.save(PrestationAttchment);
		}
		// prestationPEC = prestationPECRepository.save(prestationPEC);
		// PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);

		return result;
	}

	@Override
	public AttachmentDTO updateAttachmentFacture(MultipartFile file, Long id, String label) {
		log.debug("Request to update prestation constat Atachements : {}" + id);
		// PrestationPEC prestationPEC = prestationPECRepository.findOne(id);
		Attachment PrestationAttchment = attachmentRepository.findOne(id);
		// Treatment of attachment file
		if (file != null) {
			try {
				fileStorageService.updatePECFiles(file, Constants.ENTITY_FACTURE, PrestationAttchment.getName());
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			attachmentRepository.save(PrestationAttchment);
		}
		// prestationPEC = prestationPECRepository.save(prestationPEC);
		// PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);

		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentPEC(MultipartFile file, Long id, String label) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, Constants.ENTITY_PRESTATIONPEC);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_PRESTATIONPEC);
			PrestationAttchment.setOriginal(Boolean.FALSE);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_PRESTATIONPEC);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentQuotation(MultipartFile file, Long id, String label) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, Constants.ENTITY_QUOTATION);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_QUOTATION);
			PrestationAttchment.setOriginal(Boolean.FALSE);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_QUOTATION);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentPECNw(MultipartFile file, Long id, String label, String nomImage) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_PRESTATIONPEC, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_PRESTATIONPEC);
			PrestationAttchment.setOriginal(Boolean.FALSE);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_PRESTATIONPEC);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentQuotationNw(MultipartFile file, Long id, String label, String nomImage, String nomFolder) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, nomFolder, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(nomFolder);
			PrestationAttchment.setOriginal(Boolean.FALSE);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(nomFolder);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentFacture(MultipartFile file, Long id, String label) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, Constants.ENTITY_FACTURE);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_FACTURE);
			PrestationAttchment.setOriginal(Boolean.FALSE);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_FACTURE);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentFactureNw(MultipartFile file, Long id, String label, String nomImage) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_FACTURE, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_FACTURE);
			PrestationAttchment.setOriginal(Boolean.FALSE);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_FACTURE);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentPieceSinisterPecNw(MultipartFile file, Long id, String label, String description,
			Boolean original, String nomImage) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_IMPRIME, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			String login = SecurityUtils.getCurrentUserLogin();
	        User user = userRepository.findOneUserByLogin(login);
	        PrestationAttchment.setCreateUser(user);
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_IMPRIME);
			PrestationAttchment.setOriginal(original);
			PrestationAttchment.setNote(description);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_IMPRIME);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentPieceSinisterPec(MultipartFile file, Long id, String label, String description,
			Boolean original) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, Constants.ENTITY_IMPRIME);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_IMPRIME);
			PrestationAttchment.setOriginal(original);
			PrestationAttchment.setNote(description);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_IMPRIME);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAutresPiecesJointesFile(MultipartFile file, Long id, String label) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, Constants.ENTITY_AUTRES_PIECES_JOINTES);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_AUTRES_PIECES_JOINTES);
			PrestationAttchment.setOriginal(false);
			PrestationAttchment.setNote("vide");
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_AUTRES_PIECES_JOINTES);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAutresPiecesJointesFileNw(MultipartFile file, Long id, String label, String nomImage, String note) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_AUTRES_PIECES_JOINTES, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			String login = SecurityUtils.getCurrentUserLogin();
	        User user = userRepository.findOneUserByLogin(login);
	        PrestationAttchment.setCreateUser(user);
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_AUTRES_PIECES_JOINTES);
			PrestationAttchment.setOriginal(false);
			PrestationAttchment.setNote(note);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_AUTRES_PIECES_JOINTES);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentPieceSinisterPecPhotoReparation(MultipartFile file, Long id, String label,
			String nomImage, String nomFolder) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, nomFolder, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(nomFolder);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(nomFolder);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}
	
	@Override
	public AttachmentDTO saveAttachmentPieceSinisterPecPhotoPlus(MultipartFile file, Long id, String label,
			String nomImage) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFilesNw(file, Constants.ENTITY_PECPLUS, nomImage);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_PECPLUS);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_PECPLUS);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentBonSortie(MultipartFile file, Long id, String label, String description,
			Boolean original) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, Constants.ENTITY_SIGNATURE_BON_SORTIE);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(Constants.ENTITY_SIGNATURE_BON_SORTIE);
			PrestationAttchment.setOriginal(original);
			PrestationAttchment.setNote(description);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(Constants.ENTITY_SIGNATURE_BON_SORTIE);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	@Override
	public AttachmentDTO saveAttachmentPieceApec(MultipartFile file, Long id, String label, String description,
			String entityName) {
		log.debug("Request to save sinister pec constat Atachements : {}" + id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		// Treatment of attachment file
		Attachment PrestationAttchment = new Attachment();
		if (file != null) {
			String name = "";
			try {
				name = fileStorageService.storePECFiles(file, entityName);
			} catch (Exception ex) {
				// TODO: treat the exception
				log.error("Error when saving file", ex);
			}
			PrestationAttchment.setCreationDate(LocalDateTime.now());
			PrestationAttchment.setEntityName(entityName);
			PrestationAttchment.setOriginal(true);
			PrestationAttchment.setNote(description);
			PrestationAttchment.setOriginalName(file.getOriginalFilename());
			PrestationAttchment.setName(name);
			PrestationAttchment.setEntityId(id);
			PrestationAttchment.setPath(entityName);
			PrestationAttchment.setLabel(label);
			PrestationAttchment = attachmentRepository.save(PrestationAttchment);
		}
		// sinisterPec = sinisterPecRepository.save(sinisterPec);
		// SinisterPecDTO result = sinisterPecMapper.toDto(sinisterPec);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);
		return result;
	}

	/**
	 * get all the prestationPECS where AffetctExpert is null.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<SinisterPecDTO> findAllWhereAffetctExpertIsNull() {
		log.debug("Request to get all prestationPECS where AffetctExpert is null");
		return StreamSupport.stream(sinisterPecRepository.findAll().spliterator(), false).map(sinisterPecMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * get all the prestationPECS when tiers is not null.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<SinisterPecDTO> findAllSinisterPECWhenTiersIsNotNull(Long id, @ApiParam Pageable pageable) {
		log.debug("Request to get all prestationPECS when Tiers is not null"); // prestationPEC.getMode().getId()
																				// mode
		log.debug("mode:");
		List<SinisterPecDTO> listSinisterPecDTO = StreamSupport
				.stream(sinisterPecRepository.findAll().spliterator(), false)
				.filter(sinisterPec -> (sinisterPec.getMode() != null && sinisterPec.getMode().getId().equals(id)))
				.map(sinisterPecMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

		return new PageImpl<>(listSinisterPecDTO, pageable, listSinisterPecDTO.size());
	}

	/**
	 * get all the prestationPECS where AffectReparateur is null.
	 * 
	 * @return the list of entities /*
	 */

	@Override
	@Transactional(readOnly = true)
	public Page<SinisterPecDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of sinisiter  pec for query {}", query);
		Page<SinisterPec> result = sinisterPecSearchRepository.search(queryStringQuery(query), pageable);
		return result.map(sinisterPecMapper::toDto);
	}

	/**
	 *
	 * get all the prestationPECS where AffectReparateur is not null.
	 * 
	 * @return the list of entities
	 */

	@Override
	public List<SinisterPecDTO> findAllWhereAffectReparateurIsNotNull() {
		log.debug("Request to get all prestationPECS where AffectReparateur is not null");

		return StreamSupport.stream(sinisterPecRepository.findAll().spliterator(), false)
				.filter(prestationPEC -> prestationPEC != null).map(sinisterPecMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public List<SinisterPecDTO> findAllWherePrestationNotAccepted() {
		log.debug("Request to get all Dossiers not accepted ");
		return StreamSupport.stream(sinisterPecRepository.findAll().spliterator(), false)
				.filter(sinisterPec -> sinisterPec.getDecision() != Decision.ACCEPTED).map(sinisterPecMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Count the number of pec sinister
	 * 
	 * @return the number of pec sinister
	 */
	@Override
	public String[] getDepcAll(String debut, String fin) throws SQLException {
		String dateF = fin + "/31/" + debut;
		String dateD = fin + "/01/" + debut;
		String dateDToConvert = debut + "-" + fin + "-01";
		/* get end day from a given month */
		LocalDate convertedDateF = LocalDate.parse(dateD, DateTimeFormatter.ofPattern("M/d/yyyy"));
		convertedDateF = convertedDateF.withDayOfMonth(convertedDateF.getMonth().length(convertedDateF.isLeapYear()));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate convertedDateD = LocalDate.parse(dateDToConvert, fmt);
		LocalDate yearNow = convertedDateD.plusYears(-1);
		LocalDate yearLater = convertedDateF.plusYears(-1);
		String[] tableau = new String[100];
		tableau = kpiDao.nombreDpec(convertedDateD, convertedDateF);
		return tableau;
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecToApprove(Long idUser) {
		log.debug("Request to get all sinisters pec to approve");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinisterPecToApprove();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {
						if (sinPec.getAssignedTo().getId().equals(idUser)) {
							sinistersPecByUser.add(sinPec);
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecRefusedAndAprouveOrApprvWithModif(Long idUser) {
		log.debug("Request to get all sinisters pec to approve");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinisterPecRefusedAndAprouveOrApprvWithModif();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}else {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {
							sinistersPecByUser.add(sinPec);
						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findSinisterPecByUserId(Long userId) {
		log.debug("Request to get sinisterPecs where userId is ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findSinisterPecByUserId(userId);
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public SinisterPecDTO findBySinisterId(Long sinisterId) {
		log.debug("Request to get sinisterPecs where userId is ");
		SinisterPec sinisterPec = sinisterPecRepository.findBySinisterId(sinisterId);
		log.debug("Request to get sinisterPecs where sinisterId is " + sinisterId);
		return sinisterPecMapper.toDto(sinisterPec);

	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findSinisterPecByAssignedId(@PathVariable Long assignedToId) {
		log.debug("Request to get sinisterPecs where assignedToId is ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findSinisterPecByAssignedId(assignedToId);
		log.debug("Request to get sinisterPecs where assignedToId rrrrrrrrrrriiiii is " + assignedToId);
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * Find in progress pec prestation
	 * 
	 * @param reference
	 */
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecBeingProcessed(Long idUser) {
		log.debug("Request to get all sinisters pec  being processed");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinisterPecBeingProcessed();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * Find in pec prestation canceled
	 * 
	 * @param reference
	 */
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findCanceledSinisterPec() {
		log.debug("Request to get all canceled sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findCanceledSinisterPec();
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * Find in pec prestation refused
	 * 
	 * @param reference
	 */
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findRefusededSinisterPec() {
		log.debug("Request to get all refused sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findRefusededSinisterPec();
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * Find in request pec pec prestation to consulte
	 * 
	 * @param reference
	 */
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecConsulterDemandePec(Long idUser) {
		log.debug("Request to get all sinisters pec  being processed");
		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinisterPecConsulterDemandePec();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {

			if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
								&& userPartnerMode.getPartner().getId()
										.equals(sinisterPec.getSinister().getContract().getClient().getId())
								&& userExtra.getPersonId()
										.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
			} else {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
								&& userPartnerMode.getPartner().getId()
										.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
			}

		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public List<SinisterPecDTO> findPrestationPECsByReparateurComp(Long id, Long operation) {
		System.out.println("prestation pec find Prestation PECs By Reparateur" + id);

		List<SinisterPec> prestationForReparateur = new ArrayList<SinisterPec>();

		List<SinisterPec> prestationPEC = sinisterPecRepository.findPrestationPECsByReparateur(id);
		prestationPEC.forEach(pec -> {
			// Liste des presations sans devis
			if (operation == Utils.QUOTATION_STATUS_NULL) {
				if (pec.getPrimaryQuotation() == null) { // prec without quotation
					prestationForReparateur.add(pec);
				}
			}
			// Liste des presations pour devis complementaire
			if (operation == Utils.QUOTATION_STATUS_IN_PROGRESS) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.QUOTATION_STATUS_IN_PROGRESS) {

						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des presations en cours
			/*
			 * if (operation == Utils.QUOTATION_STATUS_IN_PROGRESS) {
			 * 
			 * if (pec.getPrimaryQuotation() != null &&
			 * pec.getPrimaryQuotation().getStatus() != null ) {
			 * System.out.println("id statussssssss-------------" +
			 * pec.getPrimaryQuotation().getStatus().getId()); if
			 * (pec.getPrimaryQuotation().getStatus().getId() ==
			 * Utils.QUOTATION_STATUS_IN_PROGRESS) { prestationForReparateur.add(pec); } } }
			 */
			// Liste des prestations ont des accords Validé par GA
			if (operation == Utils.QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus()
							.getId() == Utils.QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA) {
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestations ont des accords générer par Reparateur
			if (operation == Utils.QUOTATION_STATUS_ACCORD_GENERATED_BY_REPAIR) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus()
							.getId() == Utils.QUOTATION_STATUS_ACCORD_GENERATED_BY_REPAIR) {
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestation ont des accords signé par Assurée
			if (operation == Utils.QUOTATION_STATUS_ACCORD_SIGNED_BY_ASSURE) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus()
							.getId() == Utils.QUOTATION_STATUS_ACCORD_SIGNED_BY_ASSURE) {
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestation ont des BS validé par GA
			if (operation == Utils.BS_STATUS_VALIDATED_BY_GA) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.BS_STATUS_VALIDATED_BY_GA) {
						prestationForReparateur.add(pec);
					}
				}
			}

			// Liste des prestation ont des BS générer par reparateur
			if (operation == Utils.BS_STATUS_GENERATED_BY_REPAIR) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.BS_STATUS_GENERATED_BY_REPAIR) {
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestations avec comme status devis demontage
			if (operation == Utils.DEMONTAGE) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.DEMONTAGE) {
						prestationForReparateur.add(pec);
					}
				}
			}

			// Liste des prestations avec avis expert ok pour démontage
			/*
			 * if (operation == Utils.CIRCONSTANCE_CONFORME_OK_POUR_DEMONTAGE) { if
			 * (pec.getStep() != null) { if (pec.getPrimaryQuotation().getStatus().getId()
			 * == Utils.CIRCONSTANCE_CONFORM_POUR_DEMONTAGE) {
			 * prestationForReparateur.add(pec); } } }
			 */

			// Liste des prestations avec avis expert "Accord pour réparation avec
			// modification" 
			/*
			 * if (operation == Utils.ACCORD_POUR_REPARATION_AVEC_MODIFICATION) { if
			 * (pec.getPrimaryQuotation() != null && pec.getStep() != null) { if
			 * (pec.getPrimaryQuotation().getStatus().getId() ==
			 * Utils.ACCORD_POUR_REPARATION_MODIFICATION) {
			 * prestationForReparateur.add(pec); } } }
			 */

		});

		return sinisterPecMapper.toDto(prestationForReparateur);
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecAccWithResrveAndAprouveOrApprvWithModif(Long idUser) {
		log.debug("Request to get all sinisters pec Acc With Resrve");
		Set<SinisterPec> sinistersPec = sinisterPecRepository
				.findAllSinisterPecAccWithResrveAndAprouveOrApprvWithModif();
		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					RaisonPec motif = raisonPecRepository.findOne(sinisterPec.getReasonDecision().getId());
					if ((ResponsibleEnum.company.equals(motif.getResponsible()) ||  (ResponsibleEnum.agent.equals(motif.getResponsible()) /*&& sinisterPec.getSinister().getContract().getClient().getId().equals(6L)*/ ) ) && userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					RaisonPec motif = raisonPecRepository.findOne(sinisterPec.getReasonDecision().getId());
					if ( (ResponsibleEnum.agent.equals(motif.getResponsible()) || (ResponsibleEnum.company.equals(motif.getResponsible()) && sinisterPec.getSinister().getContract().getClient().getId().equals(3L)) ) && userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					RaisonPec motif = raisonPecRepository.findOne(sinisterPec.getReasonDecision().getId());
					if ((ResponsibleEnum.company.equals(motif.getResponsible()) ||  (ResponsibleEnum.agent.equals(motif.getResponsible()) /*&& sinisterPec.getSinister().getContract().getClient().getId().equals(6L)*/ ) ) && userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						RaisonPec motif = raisonPecRepository.findOne(sinisterPec.getReasonDecision().getId());
						if ((ResponsibleEnum.agent.equals(motif.getResponsible()) || (ResponsibleEnum.company.equals(motif.getResponsible()) && sinisterPec.getSinister().getContract().getClient().getId().equals(3L)) ) && userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
								&& userPartnerMode.getPartner().getId()
										.equals(sinisterPec.getSinister().getContract().getClient().getId())
								&& userExtra.getPersonId()
										.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					RaisonPec motif = raisonPecRepository.findOne(sinPec.getReasonDecision().getId());
					if ((ResponsibleEnum.ga.equals(motif.getResponsible()) || 
							(ResponsibleEnum.company.equals(motif.getResponsible()) && (sinPec.getSinister().getContract().getClient().getId().equals(3L) || sinPec.getSinister().getContract().getClient().getId().equals(2L)) )  ||
							(ResponsibleEnum.agent.equals(motif.getResponsible()) && (sinPec.getSinister().getContract().getClient().getId().equals(6L) || sinPec.getSinister().getContract().getClient().getId().equals(2L) ) )) && sinPec.getAssignedTo() != null) {
						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						RaisonPec motif = raisonPecRepository.findOne(sinPec.getReasonDecision().getId());
						if ((ResponsibleEnum.ga.equals(motif.getResponsible()) || 
								(ResponsibleEnum.company.equals(motif.getResponsible()) && (sinPec.getSinister().getContract().getClient().getId().equals(3L) || sinPec.getSinister().getContract().getClient().getId().equals(2L)) )  ||
								(ResponsibleEnum.agent.equals(motif.getResponsible()) && (sinPec.getSinister().getContract().getClient().getId().equals(6L) || sinPec.getSinister().getContract().getClient().getId().equals(2L) ) )) && sinPec.getAssignedTo() != null) {
							if (sinPec.getAssignedTo().getId().equals(usr.getUser().getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					RaisonPec motif = raisonPecRepository.findOne(sinPec.getReasonDecision().getId());
					if (ResponsibleEnum.ga.equals(motif.getResponsible()) && sinPec.getAssignedTo() != null) {
						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						RaisonPec motif = raisonPecRepository.findOne(sinPec.getReasonDecision().getId());
						if ( ResponsibleEnum.ga.equals(motif.getResponsible()) && sinPec.getAssignedTo() != null) {
							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							RaisonPec motif = raisonPecRepository.findOne(sinPec.getReasonDecision().getId());
							if (ResponsibleEnum.ga.equals(motif.getResponsible()) &&sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					RaisonPec motif = raisonPecRepository.findOne(sinPec.getReasonDecision().getId());
					if ((ResponsibleEnum.ga.equals(motif.getResponsible()) || 
							(ResponsibleEnum.company.equals(motif.getResponsible()) && (sinPec.getSinister().getContract().getClient().getId().equals(3L) || sinPec.getSinister().getContract().getClient().getId().equals(2L)) )  ||
							(ResponsibleEnum.agent.equals(motif.getResponsible()) && (sinPec.getSinister().getContract().getClient().getId().equals(6L) || sinPec.getSinister().getContract().getClient().getId().equals(2L) ) )) && sinPec.getAssignedTo() != null) {
						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllDemandePec(Long idUser) {
		log.debug("Request to get all demande pec ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllDemandePec();
		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						} else {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}else {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllPrestationPECForIdaOuverture() {
		log.debug("Request to get all sinister pec ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllPrestationPECForIdaOuverture();
		Set<SinisterPec> sinistersPecTiersNotNull = new HashSet<>();
		for (SinisterPec sinPec : sinistersPec) {
			if (sinPec.getTiers().size() > 0) {
				sinistersPecTiersNotNull.add(sinPec);
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecTiersNotNull)) {
			return sinistersPecTiersNotNull.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecCanceledAndAprouveOrApprvWithModif(Long idUser) {
		log.debug("Request to get all sinisters pec to approve");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinisterPecCanceledAndAprouveOrApprvWithModif();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}else {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecAccWithChangeStatusAndAprouveOrApprvWithModif(Long idUser) {
		log.debug("Request to get all sinisters pec Acc With Change Status");
		Set<SinisterPec> sinistersPec = sinisterPecRepository
				.findAllSinisterPecAccWithChangeStatusAndAprouveOrApprvWithModif();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if(sinisterPec.getModeModif() != null) {
						if(sinisterPec.getModeModif().getId().equals(5L) || sinisterPec.getModeModif().getId().equals(6L)
								|| sinisterPec.getModeModif().getId().equals(10L) || sinisterPec.getModeModif().getId().equals(11L)) {
							if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if(sinisterPec.getModeModif() != null) {
						if(sinisterPec.getModeModif().getId().equals(5L) || sinisterPec.getModeModif().getId().equals(6L)
								|| sinisterPec.getModeModif().getId().equals(10L) || sinisterPec.getModeModif().getId().equals(11L)) {
							if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
								&& userPartnerMode.getPartner().getId()
										.equals(sinisterPec.getSinister().getContract().getClient().getId())
								&& userExtra.getPersonId()
										.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if(sinPec.getModeModif() != null) {
						if (sinPec.getAssignedTo() != null && (sinPec.getSinister().getContract().getClient().getId().equals(3L)
								|| (!sinPec.getModeModif().getId().equals(5L) && !sinPec.getModeModif().getId().equals(6L)
								&& !sinPec.getModeModif().getId().equals(10L) && !sinPec.getModeModif().getId().equals(11L)))) {

							if (sinPec.getAssignedTo().getId().equals(idUser)) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if(sinPec.getModeModif() != null) {
							if (sinPec.getAssignedTo() != null && (sinPec.getSinister().getContract().getClient().getId().equals(3L)
									|| (!sinPec.getModeModif().getId().equals(5L) && !sinPec.getModeModif().getId().equals(6L)
											&& !sinPec.getModeModif().getId().equals(10L) && !sinPec.getModeModif().getId().equals(11L)))) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(Long idUser) {
		log.debug("Request to get all sinisters pec Acc With Change Status");
		Set<SinisterPec> sinistersPec = sinisterPecRepository
				.findAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getSinister().getContract().getClient().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}else {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getSinister().getContract().getClient().getId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getSinister().getContract().getAgence().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getMode() != null) {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getModeGestion().getId().equals(sinisterPec.getMode().getId())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedTo() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getAllSinisterPecInVerification(Long idUser) {
		log.debug("Request to get all sinisters pec Acc With  Verification");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinisterPecForValidation();
		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);

		if (userExtra.getProfile().getId().equals(5L)) {
			for (SinisterPec sinPec : sinistersPec) {
				Integer size = sinPec.getListComplementaryQuotation().size();
				if (size.equals(0)) {
					if (sinPec.getPrimaryQuotation() != null 
							&& sinPec.getMode() != null && sinPec.getAssignedTo().getId().equals(idUser)) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getPrimaryQuotation().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote > 3000D
								&& sommeQuote < 5000D
								&& sinPec.getMode().getId() != 7) {
							sinistersPecByUser.add(sinPec);
						}
					}
				} else {
					Comparator<ComplementaryQuotation> comparator = Comparator.comparing(ComplementaryQuotation::getId);
					if (sinPec.getListComplementaryQuotation().stream().max(comparator).get() != null
							&& sinPec.getMode() != null && sinPec.getAssignedTo().getId().equals(idUser)) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getListComplementaryQuotation().stream().max(comparator).get().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote > 3000D
								&& sommeQuote < 5000D
								&& sinPec.getMode().getId() != 7) {
							sinistersPecByUser.add(sinPec);
						}
					}
				}

			}
			Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
			for (UserExtra usr : usersChild) {
				for (SinisterPec sinPec : sinistersPec) {
					Integer size = sinPec.getListComplementaryQuotation().size();
					if (size.equals(0)) {
						if (sinPec.getPrimaryQuotation() != null && sinPec.getMode() != null
								&& usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
							Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getPrimaryQuotation().getId());
							Double sommeQuote = 0D;
							if (sommeDevis != null) {
								sommeQuote = sommeDevis.doubleValue();
							}
							if (sommeQuote > 3000D
									&& sommeQuote < 5000D
									&& sinPec.getMode().getId() != 7) {
								sinistersPecByUser.add(sinPec);
							}
						}
					} else {
						Comparator<ComplementaryQuotation> comparator = Comparator
								.comparing(ComplementaryQuotation::getId);
						if (sinPec.getListComplementaryQuotation().stream().max(comparator).get() != null
								&& sinPec.getMode() != null
								&& usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
							Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getListComplementaryQuotation().stream().max(comparator).get().getId());
							Double sommeQuote = 0D;
							if (sommeDevis != null) {
								sommeQuote = sommeDevis.doubleValue();
							}
							if (sommeQuote > 3000D
									&& sommeQuote < 5000D
									&& sinPec.getMode().getId() != 7) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}
			}
		} else if (userExtra.getProfile().getId().equals(21L)) {
			for (SinisterPec sinPec : sinistersPec) {
				Integer size = sinPec.getListComplementaryQuotation().size();
				if (size.equals(0)) {
					if (sinPec.getPrimaryQuotation() != null && sinPec.getMode() != null) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getPrimaryQuotation().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote > 5000D && sinPec.getMode().getId() != 7) {
							sinistersPecByUser.add(sinPec);
						}
					}
				} else {
					Comparator<ComplementaryQuotation> comparator = Comparator.comparing(ComplementaryQuotation::getId);
					if (sinPec.getListComplementaryQuotation().stream().max(comparator).get() != null &&  sinPec.getMode() != null) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getListComplementaryQuotation().stream().max(comparator).get().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote > 5000D
								&& sinPec.getMode().getId() != 7) {
							sinistersPecByUser.add(sinPec);
						}
					}
				}

			}

		} else if (userExtra.getProfile().getId().equals(4L)) {
			for (SinisterPec sinPec : sinistersPec) {
				Integer size = sinPec.getListComplementaryQuotation().size();
				if (size.equals(0)) {
					if (sinPec.getPrimaryQuotation() != null &&  sinPec.getMode() != null) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getPrimaryQuotation().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote > 15000D && sinPec.getMode().getId() != 7) {

							sinistersPecByUser.add(sinPec);

						}
					}
				} else {
					Comparator<ComplementaryQuotation> comparator = Comparator.comparing(ComplementaryQuotation::getId);
					if (sinPec.getListComplementaryQuotation().stream().max(comparator).get() != null &&  sinPec.getMode() != null) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getListComplementaryQuotation().stream().max(comparator).get().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote > 15000D && sinPec.getMode().getId() != 7) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}

		} else if (userExtra.getProfile().getId().equals(6L) || userExtra.getProfile().getId().equals(7L)) {
			for (SinisterPec sinPec : sinistersPec) {
				Integer size = sinPec.getListComplementaryQuotation().size();
				if (size.equals(0)) {
					if (sinPec.getMode() != null && sinPec.getMode().getId().equals(7L)) {
						if (sinPec.getAssignedTo() != null) {
							if (sinPec.getAssignedTo().getId().equals(idUser)) {
								sinistersPecByUser.add(sinPec);
							}
						}
					} else if (sinPec.getPrimaryQuotation() != null) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getPrimaryQuotation().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote <= 3000D) {
							if (sinPec.getAssignedTo() != null) {
								if (sinPec.getAssignedTo().getId().equals(idUser)) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				} else {
					Comparator<ComplementaryQuotation> comparator = Comparator.comparing(ComplementaryQuotation::getId);
					if (sinPec.getMode() != null && sinPec.getMode().getId().equals(7L)) {
						if (sinPec.getAssignedTo() != null) {
							if (sinPec.getAssignedTo().getId().equals(idUser)) {
								sinistersPecByUser.add(sinPec);
							}
						}
					} else if (sinPec.getListComplementaryQuotation().stream().max(comparator).get() != null ) {
						Float sommeDevis = quotationRepository.findTtcQuotation(sinPec.getListComplementaryQuotation().stream().max(comparator).get().getId());
						Double sommeQuote = 0D;
						if (sommeDevis != null) {
							sommeQuote = sommeDevis.doubleValue();
						}
						if (sommeQuote <= 3000D) {
							if (sinPec.getAssignedTo() != null) {
								if (sinPec.getAssignedTo().getId().equals(idUser)) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}

				}

			}
		} else {
			sinistersPecByUser = sinistersPec;
		}
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * Get Sinister from "id" SinisterPec.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public SinisterDTO findSinisterFromSinisterPec(Long id) {
		log.debug("Request to get sinister from SinisterPec : {}", id);
		SinisterPec sinisterPec = sinisterPecRepository.findOne(id);
		return sinisterMapper.toDto(sinisterPec.getSinister());
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllPrestationPECAcceptedRepNull() {
		log.debug("Request to get all PrestationPECS");
		Set<SinisterPec> list = sinisterPecRepository.findAllPrestationPECAcceptedRepNull();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public AttachmentDTO findGTAttachments(Long id) {
		log.debug("Request to find Attachments By Prestation Demontage {}", id);
		Attachment result = null;
		List<Attachment> result1 = attachmentRepository.findGTAttachments(id);
		if (result1.size() >= 1) {
			result = result1.get(0);
		}
		if (result != null) {
			return attachmentMapper.toDto(result);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findPrestationPECsByReparateur(Long id) {
		System.out.println("prestation pec find Prestation PECs By Reparateur" + id);
		Set<SinisterPec> prestationPEC = sinisterPecRepository.findPrestationPECsByReparateurInReceptionVehicule(id);
		if (CollectionUtils.isNotEmpty(prestationPEC)) {
			return prestationPEC.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();

	}

	@Override
	public long countSinisterPec(Long idPec) {

		return sinisterPecRepository.findCountSinisterPec(idPec);

	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllPrestationPECAcceptedAndReparateurIsAffected() {
		log.debug("Request to get all PrestationPECS where reparateur is affected");
		Set<SinisterPec> list = sinisterPecRepository.findAllPrestationPECAcceptedRepIsAffected();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllPrestationPECInCheckSupported() {
		log.debug("Request to get all find All Prestation PEC In Check Supported");
		Set<SinisterPec> list = sinisterPecRepository.findAllPrestationPECInCheckSupported();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getSinisterPecForUpdateDevis(Long id) {
		log.debug("Request to get all find All Prestation PEC for Update Devis");
		Set<SinisterPec> list = sinisterPecRepository.findSinisterPecForUpdateDevis(id);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getSinisterPecForDismantling(Long id) {
		log.debug("Request to get all find All Prestation PEC for dismantling");
		UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
		if (userExtraDTO.getProfileId().equals(28L)) {
			Set<SinisterPec> list = sinisterPecRepository.findSinisterPecForDismantling(userExtraDTO.getPersonId());
			if (CollectionUtils.isNotEmpty(list)) {
				return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
			}
			return new HashSet<>();
		} else {
			Set<SinisterPec> list = sinisterPecRepository.findSinisterPecForDismantlingForSuperUser();
			if (CollectionUtils.isNotEmpty(list)) {
				return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
			}
			return new HashSet<>();
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getSinisterPecInConfirmationDevis(Long id) {
		log.debug("Request to get all find All Prestation PEC for confirmation devis");
		UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
		if (userExtraDTO.getProfileId().equals(28L)) {
			Set<SinisterPec> list = sinisterPecRepository
					.findSinisterPecInConfirmationDevis(userExtraDTO.getPersonId());
			if (CollectionUtils.isNotEmpty(list)) {
				return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
			}
			return new HashSet<>();
		} else {
			Set<SinisterPec> list = sinisterPecRepository.findSinisterPecInConfirmationDevisForSuperUser();
			if (CollectionUtils.isNotEmpty(list)) {
				return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
			}
			return new HashSet<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getSinisterPecInConfirmationDevisComplementaire() {
		log.debug("Request to get all find All Prestation PEC for confirmation devis complémentaire");
		Set<SinisterPec> list = sinisterPecRepository.findSinisterPecInConfirmationDevisComplementaire();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getSinisterPecInRevueValidationDevis(Long id) {
		log.debug("Request to get all find All Prestation PEC in revue validation devis");
		Set<SinisterPec> list = sinisterPecRepository.findSinisterInRevueValidationDevis();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> getSinisterPecForExpertOpinion(Long id) {
		log.debug("Request to get all find All Prestation PEC for Expert Opinion");
		Set<SinisterPec> list = sinisterPecRepository.findSinisterPecForExpertOpinion(id);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllPrestationPECInNotCancelExpertMission() {
		log.debug("Request to get all find All Prestation PEC In Not Cancel Expert Mission");
		Set<SinisterPec> list = sinisterPecRepository.findAllPrestationPECInNotCancelExpertMission();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllAnnulationPrestation() {
		log.debug("Request to get all refused sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findRefusededSinisterPec();
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllAnnulationPrestationPec(Long idUser) {
		log.debug("Request to get all canceled sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllAnnulationPrestation();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllConfirmAnnulationPrestation(Long idUser) {
		log.debug("Request to get all canceled sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllConfirmAnnulationPrestation();
		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllRefusedPrestationPec(Long idUser) {
		log.debug("Request to get all refused sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllRefusedPrestation();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllConfirmRefusedPrestation(Long idUser) {
		log.debug("Request to get all refused sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllConfirmRefusedPrestation();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (SinisterPec sinisterPec : sinistersPec) {
						if (userPartnerMode.getPartner().getId()
								.equals(sinisterPec.getSinister().getContract().getClient().getId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinPecWithDecision() {
		log.debug("Request to get all sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinPecWithDecision();
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinPecForVerificationPrinted(Long idUser) {
		log.debug("Request to get all sinister pec bon sortie generated ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinPecForVerificationPrinted();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinPecForSignatureBonSortie(Long idUser) {
		log.debug("Request to get all sinister pec For Signature Bon Sortie ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinPecForSignatureBonSortie();

		Set<SinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(28L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getReparateur().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(28L)) {
				for (SinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getReparateur().getId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (SinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedTo() != null) {

							if (sinPec.getAssignedTo().getId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (SinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedTo() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (SinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedTo() != null) {

						if (sinPec.getAssignedTo().getId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinPecForModificationPrestation() {
		log.debug("Request to get all sinisters pec");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinPecForModificationPrestation();
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	public Long countNumberAffeExpert(Long id) {
		int i = 0;
		List<SinisterPec> sinistersPec = sinisterPecRepository.countNumberAffeExpert(id);
		log.debug("iciii  le  taille de lisste pec  " + sinistersPec.size());
		for (SinisterPec sinPec : sinistersPec) {
			log.debug("iciii  le non de desicion  " + sinPec.getExpertDecision());
			if (sinPec.getExpertDecision() != null && (sinPec.getExpertDecision().equals("Accord pour réparation")
					|| sinPec.getExpertDecision().equals("Circonstance de l’accident non conforme"))) {

				log.debug("iciii  le non de desicion yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy " + sinPec.getExpertDecision());
				i = i + 1;

			}
		}
		log.debug("iciii  le  taille de iiiiiiiii pec  " + i);
		return (long) (sinistersPec.size() - i);
	}

	@Transactional(readOnly = true)
	public Set<SinisterPecDTO> findAllSinPecModificationPrix() {
		log.debug("Request to get all confirmation prix ");
		Set<SinisterPec> sinistersPec = sinisterPecRepository.findAllSinPecModificationPrix();
		if (CollectionUtils.isNotEmpty(sinistersPec)) {
			return sinistersPec.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	public Long countNumberAffeReparator(Long id) {
		return sinisterPecRepository.countNumberAffeReparator(id);
	}
	
	public void deleteAttachment(Long id) {
		log.debug("Request to delete Attachment : {}", id);
		attachmentRepository.delete(id);
		//sinisterPecSearchRepository.delete(id);
	}

	@Override
	public Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationRefused(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationAccord(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationGeneratedAccord(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationSignAccord(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationValidBill(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SinisterPecDTO findPECByDossier(Long dossierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SinisterPecDTO findSininsterPECByReference(String reference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllSinisterPECWithGenerateAccordStatusForDevis(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationInProgress(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllSinisterPECCanceled(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllSinisterPECAccepted(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllSinisterPECInProgress(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findSinisterPECWithReserve(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllSinisterPECAcceptedExpertAndReparateurNotNull(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SinisterPecDTO> findSinisterPECsByReparateur(Long id, Long operation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SinisterPecDTO> findSinisterPECsByExpert(Long id, Long operation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SinisterPecDTO> findSinisterPECsByUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SinisterPecDTO> findSinisterPECsByCompagnieId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCountSinisterPec(String debut, String fin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TodoPrestationPecDTO findInProgressSinisterByReference(String reference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<AttachmentDTO> attachmentsBySinisterPEC(Pageable pageable, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SinisterPecDTO> findAllSinisterPECAcceptedRepNullExpertNull(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
