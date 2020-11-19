package com.gaconnecte.auxilium.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.Dossiers;
import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.repository.PriseEnChargeRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.service.PriseEnChargeService;
import com.gaconnecte.auxilium.service.dto.DossiersDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.service.mapper.PriseEnChargeMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.apache.commons.lang3.StringUtils;

@Service
@Transactional
public class PriseEnChargeServiceImpl implements PriseEnChargeService {

	private final PriseEnChargeRepository priseEnChargeRepository;
	private final UserRepository userRepository;
	private final UserExtraRepository userExtraRepository;
	private final PriseEnChargeMapper priseEnChargeMapper;
	private final Logger log = LoggerFactory.getLogger(AssitancesServicesImpl.class);

	public PriseEnChargeServiceImpl(PriseEnChargeRepository priseEnChargeRepository,
			PriseEnChargeMapper priseEnChargeMapper, UserRepository userRepository,
			UserExtraRepository userExtraRepository) {
		this.priseEnChargeRepository = priseEnChargeRepository;
		this.priseEnChargeMapper = priseEnChargeMapper;
		this.userRepository = userRepository;
		this.userExtraRepository = userExtraRepository;
	}

	/**
	 * Get all the PriseEnCharges.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<PriseEnChargeDTO> findAll() {
		log.debug("Request to get all priseEnCharge");
		return priseEnChargeRepository.findAll().stream().map(priseEnChargeMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Transactional(readOnly = true)
	public Page<PriseEnChargeDTO> findAll(Long idUser, String filter, Pageable pageable) {
		log.debug("Request to get a ViewPriseEnCharge page");
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		
        Long personId = userExtra.getPersonId() == null ? 0L : userExtra.getPersonId();

		if (StringUtils.isNotBlank(filter)) {
			return priseEnChargeRepository.findAllWithFilter(filter.toLowerCase(), pageable,userExtra.getProfile().getId(),personId,userExtra.getId())
					.map(priseEnChargeMapper::toDto);
		} else {
			return priseEnChargeRepository.findallpriseencharge(pageable,userExtra.getProfile().getId(),personId,userExtra.getId()).map(priseEnChargeMapper::toDto);

		}
	}

	@Transactional(readOnly = true)
	public Long getCountPecWithFiltter(Long idUser,String filter) {
		log.debug("Request to get all ViewPolicies");
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
        Long personId = userExtra.getPersonId() == null ? 0L : userExtra.getPersonId();

		if (StringUtils.isNotBlank(filter)) {
			return priseEnChargeRepository.countAllWithFilter(filter.toLowerCase(),userExtra.getProfile().getId(),personId,userExtra.getId());
		} else {
			return priseEnChargeRepository.countallpriseencharge(userExtra.getProfile().getId(),personId,userExtra.getId());
		}
	}

	@Transactional(readOnly = true)
	public Long getCountPec() {
		log.debug("Request to get all ViewPolicies");
		return priseEnChargeRepository.count();
	}

	/**
	 * Get all the PriseEnCharges.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<PriseEnChargeDTO> findAll(Long idUser) {
		List<PriseEnCharge> entities = priseEnChargeRepository.findAll();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		List<PriseEnCharge> dossiersByUser = new ArrayList<>();

		if (size.equals(0)) {
			/*if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L) || (userExtra.getProfile().getId()).equals(23L)) {
				for (PriseEnCharge entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgencyId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(27L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getExpertId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(28L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getReparateurId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else {
				dossiersByUser = entities;
			}
		*/ }

		else {

			if ((userExtra.getProfile().getId()).equals(5L)) {

				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {
						if (userExtra.getId().equals(entity.getAssignedToId())) {

							dossiersByUser.add(entity);
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(userExtra.getId());
				for (UserExtra usr : usersChild) {

					for (PriseEnCharge entity : entities) {
						if (entity.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(entity.getAssignedToId())) {

								dossiersByUser.add(entity);
							}
						}
					}
				}

			}

			else if (userExtra.getProfile().getId().equals(4L)) {
				
				for (PriseEnCharge entity : entities) {
					if(entity.getModeId() != null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if (userPartnerMode.getModeGestion().getId().equals(entity.getModeId())
									&& userPartnerMode.getPartner().getId().equals(entity.getPartnerId())) {
								dossiersByUser.add(entity);
							}
						}
					}else {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if (userPartnerMode.getPartner().getId().equals(entity.getPartnerId())) {
								dossiersByUser.add(entity);
							}
						}
					}

				}

			}

			else if ((userExtra.getProfile().getId()).equals(7L)) {
				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {

						if ((userExtra.getId()).equals(entity.getAssignedToId())) {
							dossiersByUser.add(entity);
						}
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(8L)) {
				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {

						if ((userExtra.getId()).equals(entity.getAssignedToId())) {
							dossiersByUser.add(entity);
						}
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(28L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getReparateurId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(27L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getExpertId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(23L) || (userExtra.getProfile().getId()).equals(24L)) {

				for (PriseEnCharge entity : entities) {
					if(entity.getModeId() != null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if (userPartnerMode.getModeGestion().getId().equals(entity.getModeId())
									&& userPartnerMode.getPartner().getId().equals(entity.getPartnerId())
									&& userExtra.getPersonId().equals(entity.getAgencyId())) {
								dossiersByUser.add(entity);
							}
						}
					}else {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if (userPartnerMode.getPartner().getId().equals(entity.getPartnerId())
									&& userExtra.getPersonId().equals(entity.getAgencyId())) {
								dossiersByUser.add(entity);
							}
						}
					}

				}
			}

			else {

				for (PriseEnCharge entity : entities) {
					if(entity.getModeId() != null) {
						if (entity.getAssignedToId() == null) {
							for (UserPartnerMode userPartnerMode : usersPartnerModes) {
								if (userPartnerMode.getModeGestion().getId().equals(entity.getModeId())
										&& userPartnerMode.getPartner().getId().equals(entity.getPartnerId())) {
									dossiersByUser.add(entity);
								}
							}
						}

						else {
							if (entity.getAssignedToId().equals(idUser)) {

								dossiersByUser.add(entity);
							}
						}
					}else {
						if (entity.getAssignedToId() == null) {
							for (UserPartnerMode userPartnerMode : usersPartnerModes) {
								if (userPartnerMode.getPartner().getId().equals(entity.getPartnerId())) {
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

				// dossiersByUser = entities;

			}

		}

		if (CollectionUtils.isNotEmpty(dossiersByUser)) {
			return dossiersByUser.stream().map(priseEnChargeMapper::toDto).collect(Collectors.toList());
		}
		return new LinkedList<>();

	}

	/**
	 * Get one PriseEnCharge by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public PriseEnChargeDTO findOne(Long id) {
		// log.debug("Request to get Uploads : {}", id);
		PriseEnCharge priseEnCharge = priseEnChargeRepository.findOne(id);
		return priseEnChargeMapper.toDto(priseEnCharge);
	}

	/**
	 * Get all the SinisterPrestation.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public Set<PriseEnChargeDTO>
	 * findPriseEnCharge(RechercheDTO rechercheDTO) { // TODO Auto-generated method
	 * stub LocalDate startDate = rechercheDTO.getStartDate() != null ?
	 * rechercheDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1); LocalDate
	 * endDate = rechercheDTO.getEndDate() != null ? rechercheDTO.getEndDate() :
	 * LocalDate.of(9000, Month.JANUARY, 1); String imatriculation =
	 * rechercheDTO.getImmatriculation(); String referenceGa =
	 * rechercheDTO.getReference();
	 * 
	 * Set<PriseEnCharge> pec =
	 * priseEnChargeRepository.findAllPriseEnChargeByDates(startDate, endDate);
	 * 
	 * if (imatriculation != null) { return
	 * priseEnChargeRepository.findAllPriseEnChargemMatriculation(imatriculation,
	 * startDate, endDate).stream() .map(priseEnChargeMapper::toDto)
	 * .collect(Collectors.toCollection(HashSet::new)); } else { if (referenceGa !=
	 * null) {
	 * 
	 * return priseEnChargeRepository.findAllPriseEnChargeByReference(referenceGa,
	 * startDate, endDate).stream() .map(priseEnChargeMapper::toDto)
	 * .collect(Collectors.toCollection(HashSet::new));
	 * 
	 * } else { return
	 * priseEnChargeRepository.findAllPriseEnChargeByDates(startDate,
	 * endDate).stream() .map(priseEnChargeMapper::toDto)
	 * .collect(Collectors.toCollection(HashSet::new));
	 * 
	 * } }
	 * 
	 * }
	 */

	@Transactional(readOnly = true)
	public List<PriseEnChargeDTO> Search(SearchDTO searchDTO) {
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);

		return priseEnChargeRepository.findAllPriseEnChargeByDates(startDate, endDate).stream()
				.map(priseEnChargeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	@Transactional(readOnly = true)
	public List<PriseEnChargeDTO> Search(SearchDTO searchDTO, Long idUser) {
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);
		List<PriseEnCharge> entities = priseEnChargeRepository.findAllPriseEnChargeByDates(startDate, endDate);
		UserExtra userExtra = userExtraRepository.findByUser(idUser);

		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		List<PriseEnCharge> dossiersByUser = new ArrayList<>();

		if (size.equals(0)) {
			if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L) || (userExtra.getProfile().getId()).equals(23L)) {
				for (PriseEnCharge entity : entities) {
					if (userExtra.getPersonId().equals(entity.getAgencyId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(27L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getExpertId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(28L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getReparateurId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else {
				dossiersByUser = entities;
			}
		}

		else {

			if ((userExtra.getProfile().getId()).equals(5L)) {

				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {
						if (userExtra.getId().equals(entity.getAssignedToId())) {

							dossiersByUser.add(entity);
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(userExtra.getId());
				for (UserExtra usr : usersChild) {

					for (PriseEnCharge entity : entities) {
						if (entity.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(entity.getAssignedToId())) {

								dossiersByUser.add(entity);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {

						if (entity.getAssignedToId().equals(idUser)) {

							dossiersByUser.add(entity);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (PriseEnCharge entity : entities) {
						if (entity.getAssignedToId() != null) {

							if (entity.getAssignedToId().equals(usrCh.getId())) {

								dossiersByUser.add(entity);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (PriseEnCharge entity : entities) {
							if (entity.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(entity.getAssignedToId())) {
									dossiersByUser.add(entity);
								}
							}
						}
					}
				}

			}

			else if ((userExtra.getProfile().getId()).equals(7L)) {
				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {

						if ((userExtra.getId()).equals(entity.getAssignedToId())) {
							dossiersByUser.add(entity);
						}
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(8L)) {
				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() != null) {

						if ((userExtra.getId()).equals(entity.getAssignedToId())) {
							dossiersByUser.add(entity);
						}
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getPartnerId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(28L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getReparateurId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(27L)) {
				for (PriseEnCharge entity : entities) {
					if ((userExtra.getPersonId()).equals(entity.getExpertId())) {
						dossiersByUser.add(entity);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(23L) || (userExtra.getProfile().getId()).equals(24L)) {

				for (PriseEnCharge entity : entities) {
					for (UserPartnerMode userPartnerMode : usersPartnerModes) {
						if (userPartnerMode.getModeGestion().getId().equals(entity.getModeId())
								&& userPartnerMode.getPartner().getId().equals(entity.getPartnerId())
								&& userExtra.getPersonId().equals(entity.getAgencyId())) {
							dossiersByUser.add(entity);
						}
					}

				}
			}

			else {

				for (PriseEnCharge entity : entities) {
					if (entity.getAssignedToId() == null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if (userPartnerMode.getModeGestion().getId().equals(entity.getModeId())
									&& userPartnerMode.getPartner().getId().equals(entity.getPartnerId())) {
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

				// dossiersByUser = entities;

			}

		}

		if (CollectionUtils.isNotEmpty(dossiersByUser)) {
			return dossiersByUser.stream().map(priseEnChargeMapper::toDto).collect(Collectors.toList());
		}
		return new LinkedList<>();

	}

}
