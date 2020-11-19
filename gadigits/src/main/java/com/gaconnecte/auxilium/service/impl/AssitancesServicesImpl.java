package com.gaconnecte.auxilium.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.Assitances;
import com.gaconnecte.auxilium.domain.Dossiers;
import com.gaconnecte.auxilium.domain.Observation;
import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.view.ViewPolicyIndicator;
import com.gaconnecte.auxilium.repository.AssitancesRepository;
import com.gaconnecte.auxilium.repository.ObservationRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.service.AssitancesServices;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.ObservationDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.mapper.AssitancesMapper;
import com.gaconnecte.auxilium.service.mapper.ObservationMapper;

@Service
@Transactional
public class AssitancesServicesImpl implements AssitancesServices {
	private final AssitancesRepository assitancesRepository;
	private final AssitancesMapper assitancesMapper;
	private final Logger log = LoggerFactory.getLogger(AssitancesServicesImpl.class);
	private final UserExtraRepository userExtraRepository;
	private final ObservationRepository observationRepository;
	private final ObservationMapper observationMapper;
	@Autowired
	private UserRepository userRepository;

	public AssitancesServicesImpl(AssitancesRepository assitancesRepository, AssitancesMapper assitancesMapper,
			UserExtraRepository userExtraRepository, ObservationRepository observationRepository,
			ObservationMapper observationMapper) {
		this.assitancesRepository = assitancesRepository;
		this.assitancesMapper = assitancesMapper;
		this.userExtraRepository = userExtraRepository;
		this.observationRepository = observationRepository;
		this.observationMapper = observationMapper;

	}

	/**
	 * Get all the sinisterPecs.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<AssitancesDTO> findAll(Long idUser) {
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		List<Assitances> entities = assitancesRepository.findAll();
		List<Assitances> dossiersByUser = new ArrayList<>();
		if (size.equals(0)) {
			if ((userExtra.getProfile().getId()).equals(25L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(26L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}
			} else if ((userExtra.getProfile().getId()).equals(23L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else {
				dossiersByUser = entities;
			}

		}

		else {

			if ((userExtra.getProfile().getId()).equals(25L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(23L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(26L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}

			}else if ((userExtra.getProfile().getId()).equals(4L) || (userExtra.getProfile().getId()).equals(5L)) {
				for (Assitances entity : entities) {
					for (UserPartnerMode userPartnerMode : usersPartnerModes) {
						if (userPartnerMode.getPartner().getId().equals(entity.getPartnerId())) {
							dossiersByUser.add(entity);
							break;
						}
					}
				}
			} else {
				for (Assitances entity : entities) {
					if (entity.getAssignedToId() == null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {

							if (userPartnerMode.getPartner().getId() == entity.getPartnerId()) {
								dossiersByUser.add(entity);
							}
						}
					}

					else {
						if (entity.getAssignedToId().equals(idUser)) {

							dossiersByUser.add(entity);

						}
					}

				}
			}
		}

		if (CollectionUtils.isNotEmpty(dossiersByUser)) {
			return dossiersByUser.stream().map(assitancesMapper::toDto).collect(Collectors.toList());
		}
		return new LinkedList<>();

	}

	/**
	 * Get one Assitances by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public AssitancesDTO findOne(Long id) {
		// log.debug("Request to get Uploads : {}", id);
		Assitances assitances = assitancesRepository.findOne(id);
		return assitancesMapper.toDto(assitances);
	}

	/**
	 * Get all the SinisterPrestation.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */

	@Transactional(readOnly = true)
	public List<AssitancesDTO> Search(SearchDTO searchDTO) {
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);

		return assitancesRepository.findAllAssistancesByDates(startDate, endDate).stream().map(assitancesMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public List<ObservationDTO> findByPrestation(String login, Long id) {

		log.debug("Request to get all Observations");
		User user = userRepository.findOneUserByLogin(login);
		UserExtra userExtra = userExtraRepository.findByUser(user.getId());
		Set<ObservationDTO> dtos = new HashSet<>();
		List<Observation> entities = observationRepository.findAll();
		log.debug("Request to get all Observations aaa " + entities.size());
		List<Assitances> assit = assitancesRepository.findAll();

		for (Observation obs : entities) {
			if (userExtra.getId().equals(obs.getUser().getId())) {

				if ((obs.getSinisterPrestation().getId()) != null) {

					dtos.add(observationMapper.toDto(obs));

				}

			}

		}
		if (CollectionUtils.isNotEmpty(dtos)) {
			List<ObservationDTO> ret = new LinkedList<>();
			ret.addAll(dtos);
			return ret;
		}
		return new LinkedList<>();
	}

	@Transactional(readOnly = true)
	public List<AssitancesDTO> Search(SearchDTO searchDTO, Long idUser) {
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);
		List<Assitances> entities = assitancesRepository.findAllAssistancesByDates(startDate, endDate);
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		List<Assitances> dossiersByUser = new ArrayList<>();
		if (size.equals(0)) {
			if ((userExtra.getProfile().getId()).equals(25L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(26L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}
			} else if ((userExtra.getProfile().getId()).equals(23L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else {
				dossiersByUser = entities;
			}

		}

		else {

			if ((userExtra.getProfile().getId()).equals(25L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(23L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(26L)) {
				for (Assitances entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L)) {
				for (Assitances entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgenceId())) {
						dossiersByUser.add(entity);
					}
				}

			} else {
				for (Assitances entity : entities) {
					if (entity.getAssignedToId() == null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {

							if (userPartnerMode.getPartner().getId() == entity.getPartnerId()) {
								dossiersByUser.add(entity);
							}
						}
					}

					else {
						if (entity.getAssignedToId().equals(idUser)) {

							dossiersByUser.add(entity);

						}
					}

				}
			}
		}

		if (CollectionUtils.isNotEmpty(dossiersByUser)) {
			return dossiersByUser.stream().map(assitancesMapper::toDto).collect(Collectors.toList());
		}
		return new LinkedList<>();

	}
   
    @Transactional(readOnly = true)
    public Long getCountAssitances(LocalDate startDate, LocalDate endDate, String immatriculation, String reference, Long statutId, Long partnerId, Long agencyId, boolean vr) {
        log.debug("Request to get all ViewAssitances");
        return assitancesRepository.countAssistanceWithSearch(startDate.toString(), endDate.toString(), immatriculation, reference, statutId, partnerId, agencyId, vr);
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    @Transactional(readOnly = true)
    public Long getCountAssitancesWithFiltter(String filter, LocalDate startDate, LocalDate endDate, String immatriculation, String reference, Long statutId, Long partnerId, Long agencyId, boolean vr ) {
        log.debug("Request to get all ViewAssitances");
        if (StringUtils.isNotBlank(filter)) {
    		return assitancesRepository.countAllAssistanceWithFilterDt(filter.toLowerCase(), startDate.toString(), endDate.toString(), immatriculation, reference, statutId, partnerId, agencyId, vr);
        } else {
        	
            return assitancesRepository.countAssistanceWithSearch(startDate.toString(), endDate.toString(), immatriculation, reference, statutId, partnerId, agencyId, vr);
        }
    }
    
    
    
    @Transactional(readOnly = true)
    public Page<AssitancesDTO> findAll(String filter, LocalDate startDate, LocalDate endDate, Pageable pageable,String immatriculation, String reference, Long statusId, Long partnerId, Long agencyId, boolean vr) {
        log.debug("Request to get a ViewAssitances page");
        if (StringUtils.isNotBlank(filter)) {
           return assitancesRepository.findAllAssistancesWithFilter(filter.toLowerCase(), startDate.toString(), endDate.toString(), pageable,immatriculation,reference,  statusId, partnerId, agencyId, vr).map(assitancesMapper::toDto);

        } else {
            return assitancesRepository.findAllAssistanceWithoutFilter(startDate.toString(), endDate.toString(), pageable,immatriculation,reference,  statusId, partnerId, agencyId, vr).map(assitancesMapper::toDto);
        }
    }
}
