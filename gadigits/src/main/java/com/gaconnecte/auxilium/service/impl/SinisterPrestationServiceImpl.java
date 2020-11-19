package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.Utils.Constants;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
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
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.repository.ReportFollowUpAssistanceRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.VehiculeAssureRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.repository.SinisterPrestationRepository;
import com.gaconnecte.auxilium.service.SinisterPrestationService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.mapper.ReportFollowUpAssistanceMapper;
import com.gaconnecte.auxilium.service.mapper.SinisterMapper;
import com.gaconnecte.auxilium.service.mapper.SinisterPrestationMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import com.gaconnecte.auxilium.security.SecurityUtils;

/**
 * SinisterPrestation Implementation for managing SinisterPrestation.
 */
@Service
@Transactional
public class SinisterPrestationServiceImpl implements SinisterPrestationService {

    private final Logger log = LoggerFactory.getLogger(SinisterPrestationServiceImpl.class);

    private final SinisterPrestationRepository sinisterPrestationRepository;

    private final SinisterPrestationMapper sinisterPrestationMapper;

    private final SinisterMapper sinisterMapper;

    private final UserRepository userRepository;

    private final VehiculeAssureRepository vehiculeAssureRepository;

    private final ContratAssuranceRepository contratAssuranceRepository;

    private final ReportFollowUpAssistanceRepository reportFollowUpAssistanceRepository;
    private final ReportFollowUpAssistanceMapper reportFollowUpAssistanceMapper;

    @Autowired
    private UserExtraRepository userExtraRepository;

    public SinisterPrestationServiceImpl(SinisterPrestationRepository sinisterPrestationRepository,
            SinisterPrestationMapper sinisterPrestationMapper, ContratAssuranceRepository contratAssuranceRepository,
            UserRepository userRepository, VehiculeAssureRepository vehiculeAssuranceRepository,
            SinisterMapper sinisterMapper, ReportFollowUpAssistanceRepository reportFollowUpAssistanceRepository,
            ReportFollowUpAssistanceMapper reportFollowUpAssistanceMapper,
            VehiculeAssureRepository vehiculeAssureRepository) {
        this.sinisterPrestationRepository = sinisterPrestationRepository;
        this.sinisterPrestationMapper = sinisterPrestationMapper;
        this.sinisterMapper = sinisterMapper;
        this.userRepository = userRepository;
        this.reportFollowUpAssistanceRepository = reportFollowUpAssistanceRepository;
        this.reportFollowUpAssistanceMapper = reportFollowUpAssistanceMapper;
        this.contratAssuranceRepository = contratAssuranceRepository;
        this.vehiculeAssureRepository = vehiculeAssureRepository;

    }

    /**
     * Save a SinisterPrestation.
     *
     * @param SinisterPrestationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SinisterPrestationDTO save(SinisterPrestationDTO sinisterPrestationDTO) {
        log.debug("Request to save sinister prestation : {}", sinisterPrestationDTO);
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        SinisterPrestation sinisterPrestation = sinisterPrestationMapper.toEntity(sinisterPrestationDTO);
        sinisterPrestation.setCreationDate(dateTime);
        sinisterPrestation.setUpdateDate(date);
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);

        sinisterPrestation.setCreationUser(user);
        sinisterPrestation.setUpdateUser(user);

        sinisterPrestation = sinisterPrestationRepository.save(sinisterPrestation);
        SinisterPrestationDTO result = sinisterPrestationMapper.toDto(sinisterPrestation);
        return result;
    }

    @Override
    public Boolean canSave(Long vehiculeId) {

        Long sinPrests = sinisterPrestationRepository.findCountSinisterPrestationForVehicule(vehiculeId);
        // Integer nbrIntervension = sinPrests.size();
        System.out.println("test1---------------------------- ");
        System.out.println(sinPrests);
        Double dIntN = Double.valueOf(sinPrests);
        VehiculeAssure vehiculeAssure = vehiculeAssureRepository.findOne(vehiculeId);
        Double nbIntervPack = vehiculeAssure.getPack().getInterventionNumber();
        boolean authorizedIntervNumber = true;
        if (nbIntervPack != null) {
            Double diffNbrInterv = nbIntervPack - dIntN;

            if (diffNbrInterv > 0) {
                authorizedIntervNumber = true;
            } else {
                authorizedIntervNumber = false;
            }
        }

        return authorizedIntervNumber;
    }

    /**
     * Get all the sinsiter prestation.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<SinisterPrestationDTO> findAll() {
        log.debug("Request to get all sinister prestation");
        Set<SinisterPrestation> sinisterPrestations = sinisterPrestationRepository.findAllSinisterPrestation();

        if (CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(sinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Get all the SinisterPrestation.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<SinisterPrestationDTO> findInProgressService() {
        log.debug("Request to get all sinister prestation in progress");
        Set<SinisterPrestation> sinisterPrestations = sinisterPrestationRepository
                .findAllByStatus(new RefStatusSinister(Constants.STATUS_IN_PROGRESS));

        if (CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(sinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Get all the SinisterPrestation.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<SinisterPrestationDTO> findClosedService() {
        log.debug("Request to get all sinister prestation closed");
        Set<SinisterPrestation> sinisterPrestations = sinisterPrestationRepository
                .findAllByStatus(new RefStatusSinister(Constants.STATUS_CLOSED));

        if (CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(sinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Get all the SinisterPrestation.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<SinisterPrestationDTO> findCanceledService() {
        log.debug("Request to get all sinister prestation canceled");
        Set<SinisterPrestation> sinisterPrestations = sinisterPrestationRepository
                .findAllByStatus(new RefStatusSinister(Constants.STATUS_CANCELED));

        if (CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(sinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Get all the SinisterPrestation.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<SinisterPrestationDTO> findNotEligibleService() {
        log.debug("Request to get all sinister prestation not eligible");
        Set<SinisterPrestation> sinisterPrestations = sinisterPrestationRepository
                .findAllByStatus(new RefStatusSinister(Constants.STATUS_NOTELIGIBLE));

        if (CollectionUtils.isNotEmpty(sinisterPrestations)) {
            return sinisterPrestations.stream().map(sinisterPrestationMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Get all the SinisterPrestation.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<ReportFollowUpAssistanceDTO> findReport1Services(SearchDTO searchDTO) {
        log.debug("Request to get all sinister prestation report1 {}", searchDTO);
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        UserExtra userExtra = userExtraRepository.findByUser(user.getId());
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
                : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
                : LocalDate.of(9000, Month.JANUARY, 1);
        Set<SinisterPrestation> sinisterPrestations = null;
        Set<SinisterPrestationDTO> ret = new HashSet<>();
        if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
            searchDTO.setPartnerId(userExtra.getPersonId());
        }
        if (searchDTO.getPartnerId() == null) {
            return reportFollowUpAssistanceRepository.findAllReportFollowUpAssistanceByDates(startDate, endDate)
                    .stream().map(reportFollowUpAssistanceMapper::toDto).collect(Collectors.toCollection(HashSet::new));
        } else {
            return reportFollowUpAssistanceRepository
                    .findAllReportFollowUpAssistanceByPartnerAndDates(searchDTO.getPartnerId(), startDate, endDate)
                    .stream().map(reportFollowUpAssistanceMapper::toDto).collect(Collectors.toCollection(HashSet::new));
        }
    }
    
    @Transactional(readOnly = true)
    public Long getCountReport1ServicesWithFiltter(String filter, SearchDTO searchDTO) {
        log.debug("Request to get all Report1");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        if (StringUtils.isNotBlank(filter)) {
        	if(searchDTO.getPartnerId() != null) {
        		return reportFollowUpAssistanceRepository.countAllWithFilterWithPartner(filter.toLowerCase(), startDate, endDate, searchDTO.getPartnerId());
        	}
        	return reportFollowUpAssistanceRepository.countAllWithFilter(filter.toLowerCase(), startDate, endDate);
        } else {
        	if(searchDTO.getPartnerId() != null) {
        		return reportFollowUpAssistanceRepository.countAllSWithPartner(startDate, endDate, searchDTO.getPartnerId());
        	}
        	return reportFollowUpAssistanceRepository.countAllS(startDate, endDate);
        }
    }
    
    @Transactional(readOnly = true)
    public Long getCountReport1Services(SearchDTO searchDTO) {
        log.debug("Request to get all Report1");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        if(searchDTO.getPartnerId() != null) {
        	return reportFollowUpAssistanceRepository.countAllSWithPartner(startDate, endDate, searchDTO.getPartnerId());
    	}
        return reportFollowUpAssistanceRepository.countAllS(startDate, endDate);
    }
    
    @Transactional(readOnly = true)
    public Page<ReportFollowUpAssistanceDTO> findAllReport1ServicesS(String filter, Pageable pageable, SearchDTO searchDTO) {
        log.debug("Request to get a Report1 page");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        if (StringUtils.isNotBlank(filter)) {
        	if(searchDTO.getPartnerId() != null) {
        		return reportFollowUpAssistanceRepository.findAllWithFilterWithPartner(filter.toLowerCase(), pageable, startDate, endDate, searchDTO.getPartnerId()).map(reportFollowUpAssistanceMapper::toDto);
        	}
            return reportFollowUpAssistanceRepository.findAllWithFilter(filter.toLowerCase(), pageable, startDate, endDate).map(reportFollowUpAssistanceMapper::toDto);
        } else {
        	if(searchDTO.getPartnerId() != null) {
        		return reportFollowUpAssistanceRepository.findAllSWithPartner(pageable, startDate, endDate, searchDTO.getPartnerId()).map(reportFollowUpAssistanceMapper::toDto);
        	}
            return reportFollowUpAssistanceRepository.findAllS(pageable, startDate, endDate).map(reportFollowUpAssistanceMapper::toDto);
        }
    }

    /**
     * Get one sinister prestation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SinisterPrestationDTO findOne(Long id) {
        log.debug("Request to get sinister prestation : {}", id);
        SinisterPrestation sinisterPrestation = sinisterPrestationRepository.findOne(id);
        return sinisterPrestationMapper.toDto(sinisterPrestation);
    }

    /**
     * Get Sinister from "id" SinisterPrestation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SinisterDTO findSinisterFromPrestation(Long id) {
        log.debug("Request to get sinister from prestation : {}", id);
        Long partnerId = 0L;
        Long agencyId = 0L;
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        UserExtra userExtraDTO = userExtraRepository.findByUser(user.getId());
        if(userExtraDTO.getProfile().getId().equals(25L) || userExtraDTO.getProfile().getId().equals(26L)) {
        	partnerId = userExtraDTO.getPersonId();
        }else if(userExtraDTO.getProfile().getId().equals(23L) || userExtraDTO.getProfile().getId().equals(24L)) {
        	agencyId = userExtraDTO.getPersonId();
        }
        SinisterPrestation sinisterPrestation = sinisterPrestationRepository.findOne(id);
        if(sinisterPrestation != null) {
        	if(!partnerId.equals(0L)) {
            	if(partnerId.equals(sinisterPrestation.getSinister().getContract().getClient().getId())) {
            		return sinisterMapper.toDto(sinisterPrestation.getSinister()); 
                }else {
                	return new SinisterDTO();
                }
            }else if(!agencyId.equals(0L)) {
            	if(agencyId.equals(sinisterPrestation.getSinister().getContract().getAgence().getId())) {
            		return sinisterMapper.toDto(sinisterPrestation.getSinister());
                }else {
                	return new SinisterDTO();
                }
            }
            return sinisterMapper.toDto(sinisterPrestation.getSinister());
        }else {
        	return new SinisterDTO();
        }
        
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SinisterPrestationDTO> findSinisterPrestationBySinisterId(Long id) {
        Set<SinisterPrestation> sinisterPrestation = sinisterPrestationRepository
                .findSinisterPrestationBySinisterId(id);
        return sinisterPrestation.stream().map(sinisterPrestationMapper::toDto).collect(Collectors.toSet());
    }

    /**
     * Delete the sinister prestation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public Boolean delete(Long id) {
        log.debug("Request to delete sinister prestation : {}", id);
        sinisterPrestationRepository.delete(id);
        return Boolean.TRUE;
    }
    
    
    
    @Transactional(readOnly = true)
    public Long countPrestationVr() {
        log.debug("Request to count vr prestation");
        	return sinisterPrestationRepository.countAllVr();
    	
    }

}
