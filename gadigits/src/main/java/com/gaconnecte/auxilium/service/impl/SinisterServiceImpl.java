package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.Observation;
import com.gaconnecte.auxilium.domain.RaisonAssistance;
import com.gaconnecte.auxilium.domain.ReportFollowUpAssistance;
import com.gaconnecte.auxilium.domain.ReportFrequencyRate;
import com.gaconnecte.auxilium.domain.SinisterPec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.domain.ViewSinisterPec;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.referential.RefGrounds;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import com.gaconnecte.auxilium.domain.view.ViewSinisterPrestation;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.repository.ReportFollowUpAssistanceRepository;
import com.gaconnecte.auxilium.repository.ReportFrequencyRateRepository;
import com.gaconnecte.auxilium.repository.SinisterPrestationRepository;
import com.gaconnecte.auxilium.repository.SinisterRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.VehiculeAssureRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DemandPecDTO;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;
import com.gaconnecte.auxilium.service.mapper.ReportFrequencyRateMapper;
import com.gaconnecte.auxilium.service.mapper.SinisterMapper;
import com.gaconnecte.auxilium.service.mapper.SinisterPrestationMapper;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterPecMapper;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterPrestationMapper;
import com.gaconnecte.auxilium.service.referential.RefPackService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service Implementation for managing Sinister.
 */
@Service
@Transactional
public class SinisterServiceImpl implements SinisterService {

	private final Logger log = LoggerFactory.getLogger(SinisterServiceImpl.class);

	private final SinisterRepository sinisterRepository;

	private final SinisterPrestationRepository sinisterPrestationRepository;

	private final ContratAssuranceRepository contratAssuranceRepository;

	private final RefPackService packService;

	private final SinisterMapper sinisterMapper;

	private final ViewSinisterPrestationMapper viewSinisterPrestationMapper;
	private final ViewSinisterPecMapper viewSinisterPecMapper;

	private final UserRepository userRepository;

	@Autowired
	private UserExtraRepository userExtraRepository;

	private final ReportFrequencyRateRepository reportFrequencyRateRepository;
	private final ReportFrequencyRateMapper reportFrequencyRateMapper;
	private final ReportFollowUpAssistanceRepository reportFollowUpAssistanceRepository;
	private final VehiculeAssureRepository vehiculeAssureRepository;

	public SinisterServiceImpl(SinisterRepository sinisterRepository,
			ViewSinisterPrestationMapper viewSinisterPrestationMapper,
			VehiculeAssureRepository vehiculeAssureRepository,
			SinisterPrestationRepository sinisterPrestationRepository, SinisterMapper sinisterMapper,
			ContratAssuranceRepository contratAssuranceRepository, UserRepository userRepository,
			VehiculeAssureRepository vehiculeAssuranceRepository, RefPackService packService,
			ReportFrequencyRateRepository reportFrequencyRateRepository,
			ReportFollowUpAssistanceRepository reportFollowUpAssistanceRepository,
			ReportFrequencyRateMapper reportFrequencyRateMapper, ViewSinisterPecMapper viewSinisterPecMapper) {
		this.sinisterRepository = sinisterRepository;
		this.sinisterPrestationRepository = sinisterPrestationRepository;
		this.sinisterMapper = sinisterMapper;
		this.contratAssuranceRepository = contratAssuranceRepository;
		this.userRepository = userRepository;
		this.packService = packService;
		this.vehiculeAssureRepository = vehiculeAssureRepository;
		this.reportFrequencyRateRepository = reportFrequencyRateRepository;
		this.reportFollowUpAssistanceRepository = reportFollowUpAssistanceRepository;
		this.viewSinisterPrestationMapper = viewSinisterPrestationMapper;
		this.reportFrequencyRateMapper = reportFrequencyRateMapper;
		this.viewSinisterPecMapper = viewSinisterPecMapper;
	}

	/**
	 * Save a dossier.
	 *
	 * @param dossierDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public SinisterDTO save(SinisterDTO sinisterDTO) {
		LocalDate date = LocalDate.now();
		LocalDateTime dateTime = LocalDateTime.now();
		Sinister sinister = sinisterMapper.toEntity(sinisterDTO);
		sinister.setDeleted(false);
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);
		if (sinister.getCreationDate() == null) {
			sinister.setCreationUser(user);
			sinister.setCreationDate(dateTime);
		}
		sinister.setUpdateUser(user);
		sinister.setUpdateDate(date);
		sinister.setAssignedTo(user);
		sinister.setAssignmentDate(date);
		if (CollectionUtils.isNotEmpty(sinister.getPrestations())) {
			for (SinisterPrestation prestation : sinister.getPrestations()) {
				prestation.setSinister(sinister);
				if (prestation.getCreationDate() == null) {
					prestation.setCreationUser(user);
					prestation.setCreationDate(dateTime);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}
				if (Constants.STATUS_CLOSED.equals(prestation.getStatus().getId())
						&& prestation.getClosureDate() == null) {
					prestation.setClosureUser(user);
					prestation.setClosureDate(dateTime);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}
				if (Constants.STATUS_CANCELED.equals(prestation.getStatus().getId())
						&& prestation.getCancelDate() == null) {
					prestation.setCancelDate(dateTime);
					prestation.setCancelededUser(user);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}
				if (prestation.getAffectedTugId() != null && prestation.getTugAssignmentDate() == null) {
					prestation.setTugAssignmentDate(dateTime);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}
				
				/*if (Constants.STATUS_REFUSED.equals(prestation.getStatus().getId())
						&& prestation.getRefusedDate() == null) {
					prestation.setRefusedDate(dateTime);
					prestation.setRefusedUser(user);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}
				if (prestation.getLoueurId() != null && prestation.getLoueurAffectedDate() == null) {
					prestation.setLoueurAffectedDate(dateTime);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}*/
				
				if (prestation.getAssignmentDate() == null) {
					prestation.setAssignedTo(user);
					prestation.setAssignmentDate(date);
					prestation.setUpdateUser(user);
					prestation.setUpdateDate(date);
				}
				
				for (Observation observation : prestation.getObservations()) {
					observation.setSinisterPrestation(prestation);
				}
			}
		}
		if (sinister.getSinisterPec() != null) {
			log.debug("Request to save Doss 9");
			if (sinister.getSinisterPec().getPointChoc() != null) {
				log.debug("Request to save  point choc ");
				sinister.getSinisterPec().getPointChoc().setSinisterPec(sinister.getSinisterPec());
				;
			}
			sinister.getSinisterPec().setSinister(sinister);

		}

		sinister = sinisterRepository.save(sinister);
		SinisterDTO result = sinisterMapper.toDto(sinister);
		return result;
	}

	/**
	 * Save a dossier.
	 *
	 * @param dossierDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public SinisterDTO canSave(SinisterDTO sinisterDTO, Long packId) {
		log.debug("Request to save Dossier : {}", sinisterDTO);
		Sinister savedSinister = null;
		if (sinisterDTO.getId() != null) {
			savedSinister = sinisterRepository.findOne(sinisterDTO.getId());
		}
		LocalDate date = LocalDate.now();
		LocalDateTime dateTime = LocalDateTime.now();
		Sinister sinister = sinisterMapper.toEntity(sinisterDTO);
		sinister.setDeleted(false);
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);
		if (sinister.getCreationDate() == null) {
			sinister.setCreationUser(user);
			sinister.setCreationDate(dateTime);
		}
		sinister.setUpdateUser(user);
		sinister.setUpdateDate(date);
		sinister.setAssignedTo(user);
		sinister.setAssignmentDate(date);
		SinisterPrestation prestation = sinister.getPrestations().get(0);
		boolean authorized = packService.isServiceTypeAuthorized(packId, prestation.getServiceType().getId());
		ContratAssurance contract = contratAssuranceRepository.findOne(sinisterDTO.getContractId());
		boolean isContractValid = contract != null
				&& (date.isBefore(contract.getFinValidite()) || date.isEqual(contract.getFinValidite()));
		boolean isIncidentDateValid = sinister.getIncidentDate() != null && contract != null
				&& (sinister.getIncidentDate().isAfter(contract.getDebutValidite())
						|| contract.getDebutValidite().isEqual(sinister.getIncidentDate()))
				&& (sinister.getIncidentDate().isBefore(contract.getFinValidite())
						|| contract.getFinValidite().isEqual(sinister.getIncidentDate()));
		log.info("Validité date incident {}, type incident {}, contract echeance {}", isIncidentDateValid, authorized,
				isContractValid);
		if (authorized && isIncidentDateValid && isContractValid) {
			sinisterDTO.setStatusId(Constants.STATUS_IN_PROGRESS);
			return sinisterDTO;
		} else {
			if (!authorized) {
				prestation.setNotEligibleGrounds(new RaisonAssistance(8L));
			} else if (isIncidentDateValid) {
				prestation.setNotEligibleGrounds(new RaisonAssistance(7L));
			}
			sinister.setStatus(new RefStatusSinister(Constants.STATUS_NOTELIGIBLE));
			prestation.setSinister(sinister);
			if (prestation.getCreationDate() == null) {
				prestation.setCreationUser(user);
				prestation.setCreationDate(dateTime);
			}
			if (prestation.getAssignmentDate() == null) {
				prestation.setAssignedTo(user);
				prestation.setAssignmentDate(date);
			}
			prestation.setStatus(new RefStatusSinister(Constants.STATUS_NOTELIGIBLE));
			prestation.setUpdateUser(user);
			prestation.setUpdateDate(date);
			if (savedSinister != null && CollectionUtils.isNotEmpty(savedSinister.getPrestations())) {
				sinister.getPrestations().addAll(savedSinister.getPrestations());
			}
			sinister = sinisterRepository.save(sinister);
			SinisterDTO result = sinisterMapper.toDto(sinister);
			return result;
		}
	}

	/**
	 * Test duplicated sinister.
	 *
	 * @param sinisterDTO the entity to test
	 * @return the persisted entity
	 */
	@Override
	public SinisterDTO isDuplicatedSinister(SinisterDTO sinisterDTO) {
		log.debug("Service to test duplicated sinister : {}", sinisterDTO);

		SinisterDTO result = sinisterDTO;
		List<Sinister> sinisters = sinisterRepository.findByVehicleAndIncidentDate(sinisterDTO.getVehicleId(),
				sinisterDTO.getIncidentDate());
		if (CollectionUtils.isNotEmpty(sinisters)) {
			result = sinisterMapper.toDto(sinisters.get(0));
		}
		return result;
	}

	/**
	 * Get all the sinisters.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<SinisterDTO> findAll() {
		log.debug("Request to get all sinisters");
		Set<Sinister> sinisters = sinisterRepository.findAllSinister();

		if (CollectionUtils.isNotEmpty(sinisters)) {
			return sinisters.stream().map(sinisterMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SinisterDTO> findByVehicleRegistration(String vehicleRegistration) {

		log.debug("Request to get all sinisters by vehicle registration");
		Set<Sinister> sinisters = sinisterRepository.findByVehicleRegistration(vehicleRegistration);

		if (CollectionUtils.isNotEmpty(sinisters)) {
			return sinisters.stream().map(sinisterMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<ViewSinisterPrestationDTO> findViewPrestationsByVehicleRegistration(Long vehiculeId) {

		log.debug("Request to get all ViewSinistersPrestations by vehicle registration");
		Set<ViewSinisterPrestation> sinisterPrestations = sinisterRepository
				.findViewPrestationsByVehicleRegistration(vehiculeId);

		if (CollectionUtils.isNotEmpty(sinisterPrestations)) {
			return sinisterPrestations.stream().map(viewSinisterPrestationMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findViewPecByVehicleRegistration(Long vehiculeId) {

		log.debug("Request to get all ViewSinistersPrestations by vehicle registration");
		Set<ViewSinisterPec> sinisterPec = sinisterRepository.findViewPecByVehicleRegistration(vehiculeId);

		if (CollectionUtils.isNotEmpty(sinisterPec)) {
			return sinisterPec.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	/**
	 * Get one sinister by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public SinisterDTO findOne(Long id) {
		log.debug("Request to get sinister : {}", id);
		Sinister sinister = sinisterRepository.findOne(id);
		return sinisterMapper.toDto(sinister);
	}

	/**
	 * Delete the sinister by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public Boolean delete(Long id) {
		log.debug("Request to delete sinister : {}", id);
		Sinister sinister = sinisterRepository.findOne(id);
		sinister.setDeleted(Boolean.TRUE);
		sinisterRepository.save(sinister);
		return Boolean.TRUE;
	}

	@Override
	public Long countSinister() {

		return sinisterRepository.count();

	}

	/**
	 * Get all demand.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DemandPecDTO> getAllNewExternalDemands() {
		log.debug("Service to get all demands");
		List<SinisterPec> prestations = sinisterRepository.getAllExternalNewDemands();
		List<DemandPecDTO> dtos = new ArrayList<>();
		for (SinisterPec pec : prestations) {
			DemandPecDTO dto = new DemandPecDTO();
			dto.setId(pec.getId());
			dto.setDateAccident(pec.getSinister().getIncidentDate());
			dto.setReferenceSinistre(pec.getReference());
			dto.setManagmentMode(pec.getMode().getLibelle());
			dto.setDateCreation(pec.getSinister().getCreationDate());
			dto.setImmatriculation(pec.getSinister().getVehicle().getImmatriculationVehicule());
			VehiculeAssure va = vehiculeAssureRepository
					.findByImmatriculation(pec.getSinister().getVehicle().getImmatriculationVehicule());
			ContratAssurance ca = va.getContrat();
			dto.setContractNumber(ca.getNumeroContrat());
			if (va.getInsured().getCompany() != null) {
				dto.setInsuredName(va.getInsured().getCompany().booleanValue() ? va.getInsured().getRaisonSociale()
						: va.getInsured().getNom() + " " + va.getInsured().getPrenom());
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SinisterDTO> findByContratId(Long id) {
		List<Sinister> sinisters = sinisterRepository.findByContratId(id);
		return sinisterMapper.toDto(sinisters);
	}

	@Override
	@Transactional(readOnly = true)
	public SinisterDTO findByVehicleIdAndIncidentDateAndStatus(Long vehiculeid, LocalDate incidentDate, Long statusId) {
		List<Sinister> sinisters = sinisterRepository.findByVehicleIdAndIncidentDateAndStatus(vehiculeid, incidentDate,
				statusId);
		Sinister sinister = null;
		if (sinisters.size() > 0) {
			sinister = sinisters.get(0);
		}
		return sinisterMapper.toDto(sinister);
	}

	/**
	 * Get all the sinisters.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<SinisterDTO> findReport2Sinisters(SearchDTO searchDTO) {
		log.debug("Request to get all sinister prestation report2 {}", searchDTO);
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);
		UserExtra userExtra = userExtraRepository.findByUser(user.getId());
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);
		Set<ReportFrequencyRate> sinisters = null;
		Set<SinisterDTO> ret = new HashSet<>();
		if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
			searchDTO.setPartnerId(userExtra.getPersonId());
		}
		if (searchDTO.getPartnerId() == null) {
			sinisters = reportFrequencyRateRepository.findAllReportFrequencyRateByDates(startDate, endDate);
		} else {
			sinisters = reportFrequencyRateRepository
					.findAllReportFrequencyRateByPartnerAndDates(searchDTO.getPartnerId(), startDate, endDate);
		}
		
		//System.out.println("***********************************"+current.getContractCount());

		Long oldPartner = 0l;
		double frequencyRate = 0;
		if (CollectionUtils.isNotEmpty(sinisters)) {
			for (ReportFrequencyRate current : sinisters) {
				SinisterDTO dto = new SinisterDTO();
				dto.setId(current.getId());
				dto.setPartnerId(current.getPartnerId());
				dto.setPartnerLabel(current.getPartnerName());
				dto.setNature(current.getIncidentNature());
				dto.setUsageLabel(current.getUsageLabel());
				dto.setCreationD(current.getCreationDate());
				Long currentPartner = current.getPartnerId();
				

				if (oldPartner.equals(currentPartner)) {
					dto.setFrequencyRate(frequencyRate);
				} else {
					long countSinister = reportFollowUpAssistanceRepository
							.countReportFollowUpAssistanceByPartnerAndDates(current.getPartnerId(), startDate, endDate);
					frequencyRate = current.getContractCount() == 0 ? 0d
							: (double) countSinister / (double) current.getContractCount();
					dto.setFrequencyRate(frequencyRate);
				}
				ret.add(dto);
			}
			return ret;
		}
		return new HashSet<>();
	}

	  @Transactional(readOnly = true)
	    public Long getCountReport2ServicesWithFiltter(String filter,SearchDTO searchDTO) {
	        log.debug("Request to get all Report2Service");
	        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	       
	        if (StringUtils.isNotBlank(filter)) {
	        	
	        	if (searchDTO.getPartnerId() == null) {
		    		return reportFrequencyRateRepository.countAllWithFilter(filter.toLowerCase(), startDate, endDate);

				} else {
					return reportFrequencyRateRepository
							.countAllByPartnerAndDatesAndFilter(filter.toLowerCase(),searchDTO.getPartnerId(), startDate, endDate);
				}
	        } 	        
	        else {
	        	if (searchDTO.getPartnerId() == null) {
	            	return reportFrequencyRateRepository.countAllwithoutfilter(startDate, endDate);

				} else {
					return reportFrequencyRateRepository
							.countAllByPartnerAndDates(searchDTO.getPartnerId(), startDate, endDate);
				}
	        }
	    }
	  @Transactional(readOnly = true)
	    public Long getCountReport2Services(SearchDTO searchDTO) {
	        log.debug("Request to get all ViewAssitances");
	        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	       
	        return reportFrequencyRateRepository.countAllwithoutfilter(startDate, endDate);
	    }
	  @Transactional(readOnly = true)
	    public Page<ReportFrequencyRateDTO> findAllReport2Services(String filter, Pageable pageable,SearchDTO searchDTO) {
	        log.debug("Request to get a ViewAssitances page");
	        String login = SecurityUtils.getCurrentUserLogin();
			User user = userRepository.findOneUserByLogin(login);
			UserExtra userExtra = userExtraRepository.findByUser(user.getId());
	    	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	    	//DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/MM/yyyy hh:mm");
	        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
	        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
	        if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
				searchDTO.setPartnerId(userExtra.getPersonId());
			}
			
			
	        if (StringUtils.isNotBlank(filter)) {	
	        	if (searchDTO.getPartnerId() == null) {
		            return reportFrequencyRateRepository.findAllWithFilter(filter.toLowerCase(), pageable,startDate, endDate).map(reportFrequencyRateMapper::toDto);

				} else {
					return reportFrequencyRateRepository
							.findAllByPartnerAndDatesAndFilter(filter.toLowerCase(),pageable,searchDTO.getPartnerId(), startDate, endDate).map(reportFrequencyRateMapper::toDto);
				}

	         } else {
	        	 
	        	 if (searchDTO.getPartnerId() == null) {
			             return reportFrequencyRateRepository.findAllWithoutFilter(pageable,startDate, endDate).map(reportFrequencyRateMapper::toDto);

					} else {
						return reportFrequencyRateRepository
								.findAllByPartnerAndDates(pageable,searchDTO.getPartnerId(), startDate, endDate).map(reportFrequencyRateMapper::toDto);
					}

	         }
	    }
	    
	    
}
