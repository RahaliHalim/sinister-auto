package com.gaconnecte.auxilium.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.Utils.Utils;
import com.gaconnecte.auxilium.dao.KpiDao;
import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.PrestationPEC;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.repository.DevisRepository;
import com.gaconnecte.auxilium.repository.PrestationPECRepository;
import com.gaconnecte.auxilium.repository.search.PrestationPECSearchRepository;
import com.gaconnecte.auxilium.service.PrestationPECService;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.TodoPrestationPecDTO;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.PrestationPECMapper;

import io.swagger.annotations.ApiParam;

/**
 * Service Implementation for managing PrestationPEC.
 */
@Service
@Transactional
public class PrestationPECServiceImpl implements PrestationPECService {

	private final Logger log = LoggerFactory.getLogger(PrestationPECServiceImpl.class);

	private final PrestationPECRepository prestationPECRepository;
	private final AttachmentRepository attachmentRepository;
	private final KpiDao kpiDao;
	private final PrestationPECSearchRepository prestationPECSearchRepository;
	private final PrestationPECMapper prestationPECMapper;
	private final AttachmentMapper attachmentMapper;
	private final DevisRepository devisRepository;
	private final FileStorageService fileStorageService;

	public PrestationPECServiceImpl(AttachmentRepository attachmentRepository, KpiDao kpiDao,
			PrestationPECRepository prestationPECRepository,  AttachmentMapper attachmentMapper,PrestationPECMapper prestationPECMapper,
			FileStorageService fileStorageService, PrestationPECSearchRepository prestationPECSearchRepository,
			DevisRepository devisRepository) {
		this.prestationPECRepository = prestationPECRepository;
		this.prestationPECSearchRepository = prestationPECSearchRepository;
		this.prestationPECMapper = prestationPECMapper;
		this.kpiDao = kpiDao;
		this.attachmentMapper = attachmentMapper;
		this.attachmentRepository = attachmentRepository;
		this.devisRepository = devisRepository;
		this.fileStorageService = fileStorageService;
	}

	/**
	 * Save a prestationPEC.
	 *
	 * @param prestationPECDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public PrestationPECDTO save(PrestationPECDTO prestationPECDTO) {
		log.debug("Request to save PrestationPEC : {}", prestationPECDTO);
		PrestationPEC prestationPEC = prestationPECMapper.toEntity(prestationPECDTO);
		if(prestationPEC.getDateReceptionVehicule()== null) {
			prestationPEC.setDateReceptionVehicule(LocalDate.now());
		}
		prestationPEC = prestationPECRepository.save(prestationPEC);
		PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		return result;

	}

	/**
	 * Save a Qutation Attachments.
	 *
	 * @param conventionDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public PrestationPECDTO saveAttachments(MultipartFile[] prestationFiles, Long id) {
		log.debug("Request to save prestation Atachements : {}" + id);
		PrestationPEC prestationPEC = prestationPECRepository.findOne(id);
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
		prestationPEC = prestationPECRepository.save(prestationPEC);
		PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		return result;
	}

	/**
	 * Get all the prestationPECS.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PrestationPECDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PrestationPECS");
		return prestationPECRepository.findAll(pageable).map(prestationPECMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PrestationPECDTO> findAllPrestationPECAcceptedRepNullExpertNull(Pageable pageable) {
		log.debug("Request to get all PrestationPECS");
		return prestationPECRepository.findAllPrestationPECAcceptedRepNullExpertNull(pageable)
				.map(prestationPECMapper::toDto);

	}
	@Override
	public Page<AttachmentDTO> attachmentsByPrestationPEC(Pageable pageable, Long id) {
		    log.debug("Request to find Attachments By Prestation Demontage {}", id);
	        Page<Attachment> result = attachmentRepository.findAttachments(pageable,id);
	        if(result !=null) 
	        return result.map(attachmentMapper::toDto);   
	        else return null;
	}
	@Override
	public AttachmentDTO updateAttachmentPEC(MultipartFile file, Long id, String label) {
		log.debug("Request to update prestation constat Atachements : {}" + id);
		//PrestationPEC prestationPEC = prestationPECRepository.findOne(id);
		Attachment PrestationAttchment= attachmentRepository.findOne(id);
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
		//prestationPEC = prestationPECRepository.save(prestationPEC);
		// PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);

		return result;
	}
	@Override
	public AttachmentDTO saveAttachmentPEC(MultipartFile file, Long id, String label) {
		log.debug("Request to save prestation constat Atachements : {}" + id);
		PrestationPEC prestationPEC = prestationPECRepository.findOne(id);
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
			attachmentRepository.save(PrestationAttchment);
			
		}
		prestationPEC = prestationPECRepository.save(prestationPEC);
		// PrestationPECDTO result = prestationPECMapper.toDto(prestationPEC);
		AttachmentDTO result = attachmentMapper.toDto(PrestationAttchment);

		return result;
	}
	@Override
	@Transactional(readOnly = true)
	public Page<PrestationPECDTO> findAllPrestationPECWithGenerateAccordStatusForDevis(Pageable pageable) {

		log.debug("Request to get all PrestationPECS");

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();
		List<PrestationPEC> prestations = prestationPECRepository
				.findAllPrestationPECAcceptedExpertAndReparateurNotNull();
		System.out.println("prestations expert and reparateur" + prestations.size());
		for (PrestationPEC item : prestations) {
			System.out.println("in list prestations555555");
			List<Devis> devis = devisRepository.findLastDevisBySinister(item.getId());
			if (devis != null && !devis.isEmpty()) {
				prestationsWithDevis.add(item);
				
			}

		}
		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PrestationPECDTO> findAllPrestationPECAcceptedExpertAndReparateurNotNull(Pageable pageable) {

		List<PrestationPEC> prestations = prestationPECRepository
				.findAllPrestationPECAcceptedExpertAndReparateurNotNull();
		Page<PrestationPEC> page = new PageImpl<>(prestations, pageable, prestations.size());
		return page.map(prestationPECMapper::toDto);

	}

	/**
	 * get all the prestationPECS where AffetctExpert is null.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<PrestationPECDTO> findAllWhereAffetctExpertIsNull() {
		log.debug("Request to get all prestationPECS where AffetctExpert is null");
		return StreamSupport.stream(prestationPECRepository.findAll().spliterator(), false)
				.map(prestationPECMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * get all the prestationPECS when tiers is not null.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<PrestationPECDTO> findAllPrestationPECWhenTiersIsNotNull(Long id, @ApiParam Pageable pageable) {
		log.debug("Request to get all prestationPECS when Tiers is not null"); // prestationPEC.getMode().getId() ==
																				// mode
		log.debug("mode:");
		List<PrestationPECDTO> listPrestationPECDTO = StreamSupport
				.stream(prestationPECRepository.findAll().spliterator(), false)
				.filter(prestationPEC -> (prestationPEC.getMode() != null && prestationPEC.getMode().getId() == id))
				.map(prestationPECMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

		return new PageImpl<>(listPrestationPECDTO, pageable, listPrestationPECDTO.size());
	}

	/**
	 * get all the prestationPECS where AffectReparateur is null.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<PrestationPECDTO> findAllWhereAffectReparateurIsNull() {
		log.debug("Request to get all prestationPECS where AffectReparateur is null");
		List<PrestationPEC> prestationPEC = prestationPECRepository.findPrestationPECsWhereAffectReparateurIsNull();
		return prestationPECMapper.toDto(prestationPEC);
	}

	/**
	 * Get one prestationPEC by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public PrestationPECDTO findOne(Long id) {
		log.debug("Request to get PrestationPEC : {}", id);
		PrestationPEC prestationPEC = prestationPECRepository.findOneWithEagerRelationships(id);
		return prestationPECMapper.toDto(prestationPEC);
	}

	/**
	 * Delete the prestationPEC by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete PrestationPEC : {}", id);
		prestationPECRepository.delete(id);
	}

	/**
	 * Get one prestationPEC by prestationId.
	 *
	 * @param prestationId the prestationId of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public PrestationPECDTO findPECByDossier(Long dossierId) {
		log.debug("Request to get PrestationAvt : {}", dossierId);
		PrestationPEC prestationPEC = prestationPECRepository.findPecByDossier(dossierId);
		return prestationPECMapper.toDto(prestationPEC);
	}

	@Override
	public PrestationPECDTO findPrestationPECByReference(String reference) {
		log.debug("Request to get PrestationPECByReference : {}", reference);
		PrestationPEC prestationPEC = prestationPECRepository.findByPrestationReference(reference);
		return prestationPECMapper.toDto(prestationPEC);
	}

	/**
	 * Search for the prestationPEC corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PrestationPECDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of prestationPECs for query {}", query);
		Page<PrestationPEC> result = prestationPECSearchRepository.search(queryStringQuery(query), pageable);
		return result.map(prestationPECMapper::toDto);
	}

	/**
	 * added by sofien
	 *
	 * get all the prestationPECS where AffectReparateur is not null.
	 * 
	 * @return the list of entities
	 */

	@Override
	public List<PrestationPECDTO> findAllWhereAffectReparateurIsNotNull() {
		log.debug("Request to get all prestationPECS where AffectReparateur is not null");

		return StreamSupport.stream(prestationPECRepository.findAll().spliterator(), false)
				.map(prestationPECMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public List<PrestationPECDTO> findAllWherePrestationNotAccepted() {
		log.debug("Request to get all Dossiers not accepted ");
		return StreamSupport.stream(prestationPECRepository.findAll().spliterator(), false)
				.filter(prestationPEC -> prestationPEC.getDecision() != Decision.ACCEPTED)
				.map(prestationPECMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Count the number of pec prestation
	 * 
	 * @return the number of pec prestation
	 */
	@Override
	public Long getCountPrestationPec(String debut, String fin) {

		String dateF = fin + "/31/" + debut;
		String dateD = fin + "/01/" + debut;
		String dateDToConvert = debut + "-" + fin + "-01";
		LocalDate convertedDateF = LocalDate.parse(dateD, DateTimeFormatter.ofPattern("M/d/yyyy"));
		log.debug("convertedDateF: ", convertedDateF);
		convertedDateF = convertedDateF.withDayOfMonth(convertedDateF.getMonth().length(convertedDateF.isLeapYear()));
		log.debug("convertedDateF after convert: ", convertedDateF);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate convertedDateD = LocalDate.parse(dateDToConvert, fmt);
		ZonedDateTime convertedD = convertedDateD.atStartOfDay(ZoneId.systemDefault());
		ZonedDateTime convertedF = convertedDateF.atStartOfDay(ZoneId.systemDefault());
		return prestationPECRepository.countPrestationPEC(convertedD, convertedF);
	}

	/**
	 * Count the number of pec prestation
	 * 
	 * @return the number of pec prestation
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

	/**
	 * Find in progress pec prestation
	 * 
	 * @param reference
	 */
	@Override
	public TodoPrestationPecDTO findInProgressPrestationByReference(String reference) {
		log.debug("Request to get findServicePecByReference : {}", reference);
		PrestationPEC prestationPEC = prestationPECRepository.findByPrestationReference(reference);
		if (prestationPEC != null) {
			TodoPrestationPecDTO todo = new TodoPrestationPecDTO();
			todo.setId(prestationPEC.getId());
			todo.setPrestationId(prestationPEC.getId());
			todo.setDescPtsChoc(prestationPEC.getDescPtsChoc());
			// todo.setPrestationReference(prestationPEC.getReference());
			todo.setNbrVehicules(prestationPEC.getNbrVehicules());
			todo.setModeLibelle(prestationPEC.getMode() != null ? prestationPEC.getMode().getLibelle() : null);
			todo.setPosGaLibelle(prestationPEC.getPosGa() != null ? prestationPEC.getPosGa().getLibelle() : null);
			todo.setUserFirstName(prestationPEC.getUser() != null ? prestationPEC.getUser().getFirstName() : null);
			todo.setUserLastName(prestationPEC.getUser() != null ? prestationPEC.getUser().getLastName() : null);
			// Calculate quotation
			List<Devis> devis = devisRepository.findLastDevisBySinister(prestationPEC.getId());
			/*
			 * if(devis != null) { todo.setQuotationId(devis.getId());
			 * todo.setQuotationStatus(devis.getEtatDevis().name()); }
			 */
			if (!(devis.isEmpty())) {
				Devis result = (devis != null && !devis.isEmpty()) ? devis.get(0) : null;
				if (result != null) {
					todo.setQuotationId(result.getId());
					todo.setQuotationStatus(result.getEtatDevis().name());
				}
			}
			todo.setStatusId(null);
			todo.setStatusName(prestationPEC.getDecision() != null ? prestationPEC.getDecision().name() : "");
			return todo;
		}
		return null;
	}
	@Override
	@Transactional(readOnly = true)
	public List<PrestationPECDTO> findPrestationPECsByReparateur(Long id, Long operation) {
		System.out.println("prestation pec find Prestation PECs By Reparateur" + id);
		List<PrestationPEC> prestationForReparateur = new ArrayList<PrestationPEC>();
		List<PrestationPEC> prestationPEC = prestationPECRepository.findPrestationPECsByReparateur(id);
		prestationPEC.forEach(pec -> {
			// Liste des presations sans devis
			if (operation == Utils.QUOTATION_STATUS_NULL) {
				if (pec.getPrimaryQuotation() == null && pec.getEtatChoc()!= Utils.CHOC_NON_LEGER) { // prec without quotation
					prestationForReparateur.add(pec);
				}
			}
			// Liste des Devis en cours
			if (operation == Utils.QUOTATION_STATUS_IN_PROGRESS) {

				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null&& pec.getEtatChoc()!= Utils.CHOC_NON_LEGER) {
					System.out.println("id statussssssss-------------" + pec.getPrimaryQuotation().getStatus().getId());
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.QUOTATION_STATUS_IN_PROGRESS) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestations ont des accords Validé par GA
			if (operation == Utils.QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus()
							.getId() == Utils.QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestations ont des accords générer par Reparateur
			if (operation == Utils.QUOTATION_STATUS_ACCORD_GENERATED_BY_REPAIR) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus()
							.getId() == Utils.QUOTATION_STATUS_ACCORD_GENERATED_BY_REPAIR) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}	
			}
			// Liste des prestation ont des accords signé par Assurée
			if (operation == Utils.QUOTATION_STATUS_ACCORD_SIGNED_BY_ASSURE) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus()
							.getId() == Utils.QUOTATION_STATUS_ACCORD_SIGNED_BY_ASSURE) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestation ont des BS validé par GA
			if (operation == Utils.BS_STATUS_VALIDATED_BY_GA) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.BS_STATUS_VALIDATED_BY_GA) { // primery  quotation
						prestationForReparateur.add(pec);
					}
				}
			}

			// Liste des prestation ont des BS générer par reparateur
			if (operation == Utils.BS_STATUS_GENERATED_BY_REPAIR) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.BS_STATUS_GENERATED_BY_REPAIR) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			// Liste des prestations avec comme status devis demontage
			if (operation == Utils.DEMONTAGE) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.DEMONTAGE) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			
			// Liste des prestations avec avis expert ok pour démontage
			if (operation == Utils.CIRCONSTANCE_CONFORME_OK_POUR_DEMONTAGE) {
				if (pec.getEtatChoc() != null && pec.getStep() != null) {
					if (pec.getEtatChoc()== Utils.CHOC_NON_LEGER && pec.getStep() == Utils.CIRCONSTANCE_CONFORME_OK_POUR_DEMONTAGE) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			
			// Liste des prestations avec avis expert "Accord pour réparation avec modification" 
			if (operation == Utils.ACCORD_POUR_REPARATION_AVEC_MODIFICATION) {
				if (pec.getPrimaryQuotation() != null && pec.getStep() != null) {
					if (pec.getStep() == Utils.ACCORD_POUR_REPARATION_AVEC_MODIFICATION) { // primery quotation
						prestationForReparateur.add(pec);
					}
				}
			}
			
			
			
			
		});

		return prestationPECMapper.toDto(prestationForReparateur);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<PrestationPECDTO> findPrestationPECsByExpert(Long id, Long operation) {

		List<PrestationPEC> prestationForExpert = new ArrayList<PrestationPEC>();
		List<PrestationPEC> prestationPEC = prestationPECRepository.findPrestationPECsByExpert(id);

		prestationPEC.forEach(pec -> {
			// Liste des Devis valider par GA
			if (operation == Utils.QUOTATION_STATUS_VALID_GA) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.QUOTATION_STATUS_VALID_GA) { // primery
																											// quotation
						prestationForExpert.add(pec);
					}
				}
				/*
				 * if(pec.getListComplementaryQuotation() != null &&
				 * !pec.getListComplementaryQuotation().isEmpty()){
				 * pec.getListComplementaryQuotation().forEach(peccomp->{ if(peccomp.getStatus()
				 * != null){ if(peccomp.getStatus().getId() == Utils.QUOTATION_STATUS_VALID_GA){
				 * // complementary quotation prestationForExpert.add(pec); } } }); }
				 */

			}
			// Liste des Devis Expertise Terrain par GA
			if (operation == Utils.QUOTATION_STATUS_EXPERTISE_TERRAIN) {
				if (pec.getPrimaryQuotation() != null && pec.getPrimaryQuotation().getStatus() != null) {
					if (pec.getPrimaryQuotation().getStatus().getId() == Utils.QUOTATION_STATUS_EXPERTISE_TERRAIN) { // primery
																														// quotation
						prestationForExpert.add(pec);
					}
				}
				/*
				 * if(pec.getListComplementaryQuotation() != null &&
				 * !pec.getListComplementaryQuotation().isEmpty()){
				 * pec.getListComplementaryQuotation().forEach(peccomp->{ if(peccomp.getStatus()
				 * != null){ if(peccomp.getStatus().getId() ==
				 * Utils.QUOTATION_STATUS_EXPERTISE_TERRAIN){ // complementary quotation
				 * prestationForExpert.add(pec); } } }); }
				 */

			}
		});

		return prestationPECMapper.toDto(prestationForExpert);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestationPECDTO> findPrestationPECsByUserId(Long id) {

		List<PrestationPEC> prestationPEC = prestationPECRepository.findPrestationPECsByUserId(id);
		return prestationPECMapper.toDto(prestationPEC);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestationPECDTO> findPrestationPECsByCompagnieId(Long id) {

		List<PrestationPEC> prestationPEC = prestationPECRepository.findPrestationPECsByCompagnieId(id);
		return prestationPECMapper.toDto(prestationPEC);
	}

	@Override
	public Long countNumberAffeReparator(Long id) {

		return prestationPECRepository.countNumberAffeReparator(id);
	}

	@Override
	public Long countNumberAffeExpert(Long id) {

		return prestationPECRepository.countNumberAffeExpert(id);
	}

	@Override
	public Page<PrestationPECDTO> findAllPrestationPECCanceled(Pageable pageable) {

		Page<PrestationPEC> result = prestationPECRepository.findAllPrestationPECCanceled(pageable);
		return result.map(prestationPECMapper::toDto);

	}

	@Override
	public Page<PrestationPECDTO> findAllPrestationPECAccepted(Pageable pageable) {

		Page<PrestationPEC> result = prestationPECRepository.findAllPrestationPECAccepted(pageable);
		return result.map(prestationPECMapper::toDto);

	}

	@Override
	public Page<PrestationPECDTO> findPrestationPECWithReserve(Pageable pageable) {

		Page<PrestationPEC> result = prestationPECRepository.findAllPrestationPECWithReserve(pageable);
		return result.map(prestationPECMapper::toDto);

	}

	@Override
	public Page<PrestationPECDTO> findAllPrestationPECInProgress(Pageable pageable) {
		Page<PrestationPEC> result = prestationPECRepository.findAllPrestationPECInProgress(pageable);
		return result.map(prestationPECMapper::toDto);
	}

	@Override
	public Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationInProgress(Pageable pageable) {
		log.debug("Request to get all PrestationPECS");

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();
		List<PrestationPEC> prestations = prestationPECRepository
				.findAllPrestationPECAcceptedExpertAndReparateurNotNull();
		System.out.println("prestations expert and reparateur" + prestations.size());

		for (PrestationPEC pec : prestations) {

			List<Devis> devis = devisRepository.findDevisByPrestation(pec.getId());
			if (devis != null && !devis.isEmpty()) {

				devis.forEach(item -> {
					if (item.getQuotationStatus() != null) {
						if (item.getQuotationStatus().getId().equals(1L)) {
							pec.setDevisId(item.getId());
							pec.setQuotationStatusId(1L);
							prestationsWithDevis.add(pec);
						}
					}
				});

			} else {
				prestationsWithDevis.add(pec);
			}

		}
		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}

	@Override
	public Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationRefused(Pageable pageable) {

		log.debug("Request to get all PrestationPECS");
		List<PrestationPEC> prestations = devisRepository.findPrestationByStatusQuotation(7L);

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();

		for (PrestationPEC pec : prestations) {
			List<Devis> devis = devisRepository.findDevisByPrestation(pec.getId());
			if (devis != null && !devis.isEmpty()) {

				devis.forEach(item -> {
					if (item.getQuotationStatus() != null) {

						pec.setDevisId(item.getId());
						pec.setQuotationStatusId(7L);
						prestationsWithDevis.add(pec);

					}
				});

			}
		}

		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}

	@Override
	public Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationAccord(Pageable pageable) {

		log.debug("Request to get all PrestationPECS");
		List<PrestationPEC> prestations = devisRepository.findPrestationByStatusQuotation(6L);

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();

		for (PrestationPEC pec : prestations) {
			List<Devis> devis = devisRepository.findDevisByPrestation(pec.getId());
			if (devis != null && !devis.isEmpty()) {

				devis.forEach(item -> {
					if (item.getQuotationStatus() != null) {

						pec.setDevisId(item.getId());
						pec.setQuotationStatusId(6L);
						prestationsWithDevis.add(pec);

					}
				});

			}
		}

		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}

	@Override
	public Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationGeneratedAccord(Pageable pageable) {
		log.debug("Request to get all PrestationPECS");
		List<PrestationPEC> prestations = devisRepository.findPrestationByStatusQuotation(9L);

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();

		for (PrestationPEC pec : prestations) {
			List<Devis> devis = devisRepository.findDevisByPrestation(pec.getId());
			if (devis != null && !devis.isEmpty()) {

				devis.forEach(item -> {
					if (item.getQuotationStatus() != null) {

						pec.setDevisId(item.getId());
						pec.setQuotationStatusId(9L);
						prestationsWithDevis.add(pec);

					}
				});

			}
		}
		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}

	@Override
	public Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationSignAccord(Pageable pageable) {
		log.debug("Request to get all PrestationPECS");
		List<PrestationPEC> prestations = devisRepository.findPrestationByStatusQuotation(8L);

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();

		for (PrestationPEC pec : prestations) {
			List<Devis> devis = devisRepository.findDevisByPrestation(pec.getId());
			if (devis != null && !devis.isEmpty()) {

				devis.forEach(item -> {
					if (item.getQuotationStatus() != null) {

						pec.setDevisId(item.getId());
						pec.setQuotationStatusId(8L);
						prestationsWithDevis.add(pec);

					}
				});

			}
		}
		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}

	@Override
	public Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationValidBill(Pageable pageable) {
		log.debug("Request to get all PrestationPECS");
		List<PrestationPEC> prestations = devisRepository.findPrestationByStatusQuotation(11L);

		List<PrestationPEC> prestationsWithDevis = new ArrayList<PrestationPEC>();

		for (PrestationPEC pec : prestations) {
			List<Devis> devis = devisRepository.findDevisByPrestation(pec.getId());
			if (devis != null && !devis.isEmpty()) {

				devis.forEach(item -> {
					if (item.getQuotationStatus() != null) {

						pec.setDevisId(item.getId());
						pec.setQuotationStatusId(11L);
						prestationsWithDevis.add(pec);

					}
				});

			}
		}
		System.out.println("prestation with devis" + prestationsWithDevis.size());
		Page<PrestationPEC> page = new PageImpl<>(prestationsWithDevis, pageable, prestationsWithDevis.size());
		return page.map(prestationPECMapper::toDto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<AttachmentDTO> findAttachments(Pageable pageable ,Long id){
		log.debug("Request to find Attachments  {}", id);
		Page<Attachment> result = attachmentRepository.findAttachments( pageable,id);
		return result.map(attachmentMapper::toDto);	
	}
	
	@Override
	@Transactional(readOnly = true)
	public ArrayList<AttachmentDTO> findAttachments(String entityName, Long entityId){
		log.debug("Request to find Attachments  {}", entityId);
		Set<Attachment> result = attachmentRepository.findAttachments( entityName,entityId);
		ArrayList<AttachmentDTO> methodReturn=new ArrayList<AttachmentDTO>();
		for(Attachment attach : result) {
			methodReturn.add(attachmentMapper.toDto(attach));
		}
		return methodReturn;	
	}
}
