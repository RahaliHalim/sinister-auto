package com.gaconnecte.auxilium.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.Dossier;
import com.gaconnecte.auxilium.domain.Dossiers;
import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.domain.Upload;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.repository.DossierRepository;
import com.gaconnecte.auxilium.repository.DossiersRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.VehiculeAssureRepository;
import com.gaconnecte.auxilium.service.DossiersService;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DossierDTO;
import com.gaconnecte.auxilium.service.dto.DossiersDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.UploadDTO;
import com.gaconnecte.auxilium.service.mapper.DossierMapper;
import com.gaconnecte.auxilium.service.mapper.DossiersMapper;
import com.gaconnecte.auxilium.web.rest.PriseEnChargeResource;

@Service
@Transactional
public class DossiersServiceImpl implements DossiersService {
	private final DossiersRepository dossiersRepository;
	private final UserRepository userRepository;
	private final UserExtraRepository userExtraRepository;
	private final DossiersMapper dossiersMapper;
	private final Logger log = LoggerFactory.getLogger(DossiersServiceImpl.class);

	public DossiersServiceImpl(DossiersRepository dossiersRepository, DossiersMapper dossiersMapper,
			UserRepository userRepository, UserExtraRepository userExtraRepository) {
		this.dossiersRepository = dossiersRepository;
		this.dossiersMapper = dossiersMapper;
		this.userRepository = userRepository;
		this.userExtraRepository = userExtraRepository;
	}

	/**
	 * Get all the dossiers.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */

	/**
	 * @Override
	 * @Transactional(readOnly = true) public Page<DossiersDTO> findAll(Pageable
	 *                         pageable) { //log.debug("Request to get all
	 *                         Uploads"); return
	 *                         dossiersRepository.findAll(pageable)
	 *                         .map(dossiersMapper::toDto); }
	 */
	@Transactional(readOnly = true)
	public List<DossiersDTO> findAll() {
		log.debug("Request to get all priseEnCharge");
		return dossiersRepository.findAll().stream().map(dossiersMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Transactional(readOnly = true)
	public List<DossiersDTO> findAll(Long idUser) {
		List<Dossiers> dossiers = dossiersRepository.findAll();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		List<Dossiers> dossiersByUser = new ArrayList<>();

		if (size.equals(0)) {
			if ((userExtra.getProfile().getId()).equals(25L)) {
				for (Dossiers dossier : dossiers) {
					if ((userExtra.getPersonId()).equals(dossier.getCompagnieId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(26L)) {
				for (Dossiers dossier : dossiers) {
					if ((userExtra.getPersonId()).equals(dossier.getCompagnieId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(23L)) {
				for (Dossiers dossier : dossiers) {
					if (userExtra.getPersonId().equals(dossier.getAgencyId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L)) {
				for (Dossiers dossier : dossiers) {
					if (userExtra.getPersonId().equals(dossier.getAgencyId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else {
				dossiersByUser = dossiers;
			}
		} else {
			if ((userExtra.getProfile().getId()).equals(25L)) {

				for (Dossiers dossier : dossiers) {
					if ((userExtra.getPersonId()).equals(dossier.getCompagnieId())) {
						dossiersByUser.add(dossier);
					}
				}
			} else if ((userExtra.getProfile().getId()).equals(24L)) {

				for (Dossiers dossier : dossiers) {
					if (userExtra.getPersonId().equals(dossier.getAgencyId())) {
						dossiersByUser.add(dossier);
					}
				}
			} else if ((userExtra.getProfile().getId()).equals(26L)) {
				for (Dossiers dossier : dossiers) {
					if ((userExtra.getPersonId()).equals(dossier.getCompagnieId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(23L)) {
				for (Dossiers dossier : dossiers) {
					if (userExtra.getPersonId().equals(dossier.getAgencyId())) {
						dossiersByUser.add(dossier);
					}
				}
			}
			else if ((userExtra.getProfile().getId()).equals(4L)) {
                            dossiersByUser = dossiers;
			}
			else {
				for (Dossiers dossier : dossiers) {
					if (dossier.getAssignedToId() == null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if ((userPartnerMode.getPartner().getId()).equals(dossier.getCompagnieId())) {
								dossiersByUser.add(dossier);
							}
						}
					}

					else {
						if (dossier.getAssignedToId().equals(idUser)) {

							dossiersByUser.add(dossier);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(dossiersByUser)) {
			return dossiersByUser.stream().map(dossiersMapper::toDto).collect(Collectors.toList());
		}
		return new LinkedList<>();

	}

	/**
	 * Get one tiers by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public DossiersDTO findOne(Long id) {
		// log.debug("Request to get Uploads : {}", id);
		Dossiers dossiers = dossiersRepository.findOne(id);
		return dossiersMapper.toDto(dossiers);
	}

	/**
	 * Get all the SinisterPrestation.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */

	@Transactional(readOnly = true)
	public List<DossiersDTO> findAll(SearchDTO searchDTO, Long idUser) {
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);
		List<Dossiers> dossiers = dossiersRepository.findAllDossiersByDates(startDate, endDate);

		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		List<Dossiers> dossiersByUser = new ArrayList<>();

		if (size.equals(0)) {
			if ((userExtra.getProfile().getId()).equals(25L)) {
				for (Dossiers dossier : dossiers) {
					if ((userExtra.getPersonId()).equals(dossier.getCompagnieId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else if ((userExtra.getProfile().getId()).equals(24L)) {
				for (Dossiers dossier : dossiers) {
					if (userExtra.getPersonId().equals(dossier.getAgencyId())) {
						dossiersByUser.add(dossier);
					}
				}
			}

			else {
				dossiersByUser = dossiers;
			}
		} else {
			if ((userExtra.getProfile().getId()).equals(25L)) {

				for (Dossiers dossier : dossiers) {
					if ((userExtra.getPersonId()).equals(dossier.getCompagnieId())) {
						dossiersByUser.add(dossier);
					}
				}
			} else if ((userExtra.getProfile().getId()).equals(24L)) {

				for (Dossiers dossier : dossiers) {
					if (userExtra.getPersonId().equals(dossier.getAgencyId())) {
						dossiersByUser.add(dossier);
					}
				}
			} else {
				for (Dossiers dossier : dossiers) {
					if (dossier.getAssignedToId() == null) {
						for (UserPartnerMode userPartnerMode : usersPartnerModes) {
							if ((userPartnerMode.getPartner().getId()).equals(dossier.getCompagnieId())) {
								dossiersByUser.add(dossier);
							}
						}
					}

					else {
						if (dossier.getAssignedToId().equals(idUser)) {

							dossiersByUser.add(dossier);

						}
					}

				}
			}
		}

		if (CollectionUtils.isNotEmpty(dossiersByUser)) {
			return dossiersByUser.stream().map(dossiersMapper::toDto).collect(Collectors.toList());
		}
		return new LinkedList<>();

	}

	@Transactional(readOnly = true)
	public List<DossiersDTO> Search(SearchDTO searchDTO) {
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);

		return dossiersRepository.findAllDossiersByDates(startDate, endDate).stream().map(dossiersMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

}
