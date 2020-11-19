package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.RaisonPec;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.ViewSinisterPec;
import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.repository.RaisonPecRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.ViewSinisterPecRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSinisterPecMapper;
import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Implementation for managing ViewSinisterPec.
 */
@Service
@Transactional
public class ViewSinisterPecService {

	private final Logger log = LoggerFactory.getLogger(ViewSinisterPecService.class);

	private final ViewSinisterPecRepository viewSinisterPecRepository;

	private final ViewSinisterPecMapper viewSinisterPecMapper;

	private final UserExtraService userExtraService;

	private final UserExtraRepository userExtraRepository;
	private final RaisonPecRepository raisonPecRepository;
	private final ContratAssuranceRepository contratAssuranceRepository;
	@Autowired
	private UserService userService;

	public ViewSinisterPecService(ViewSinisterPecRepository viewSinisterPecRepository,
			ViewSinisterPecMapper viewSinisterPecMapper, UserExtraService userExtraService,
			UserExtraRepository userExtraRepository, 
			ContratAssuranceRepository contratAssuranceRepository
			,RaisonPecRepository raisonPecRepository) {
		this.viewSinisterPecRepository = viewSinisterPecRepository;
		this.viewSinisterPecMapper = viewSinisterPecMapper;
		this.userExtraService = userExtraService;
		this.userExtraRepository = userExtraRepository;
		this.contratAssuranceRepository = contratAssuranceRepository;
		this.raisonPecRepository = raisonPecRepository;
	}

	/**
	 * Save a viewSinisterPec.
	 *
	 * @param viewSinisterPecDTO the entity to save
	 * @return the persisted entity
	 */
	public ViewSinisterPecDTO save(ViewSinisterPecDTO viewSinisterPecDTO) {
		log.debug("Request to save ViewSinisterPec : {}", viewSinisterPecDTO);
		ViewSinisterPec viewSinisterPec = viewSinisterPecMapper.toEntity(viewSinisterPecDTO);
		viewSinisterPec = viewSinisterPecRepository.save(viewSinisterPec);
		ViewSinisterPecDTO result = viewSinisterPecMapper.toDto(viewSinisterPec);
		return result;
	}

	/**
	 * Get all the viewSinisterPecs.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<ViewSinisterPecDTO> findAll() {
		log.debug("Request to get all ViewSinisterPecs");
		return viewSinisterPecRepository.findAll().stream().map(viewSinisterPecMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get all the AcceptedAndNoReparator.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllAcceptedAndNoReparator(Long idUser) {
		log.debug("Request to get all AcceptedAndNoReparator");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllAcceptedAndNoReparator();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();

	}

	/**
	 * Get all the AcceptedAndNoReparator.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllInMissionExpert(Long id) {
		log.debug("Request to get all In Mission Expert");
		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
		Integer size = userExtraDTO.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = viewSinisterPecRepository.findAllInMissionExpertForSuperUser();
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto)
					.collect(Collectors.toCollection(HashSet::new));
		} else {
			if (userExtraDTO.getProfileId() != null && (userExtraDTO.getProfileId().equals(6L) || userExtraDTO.getProfileId().equals(7L))) {
				sinistersPecByUser = viewSinisterPecRepository.findAllInMissionExpert(id);
				return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto)
						.collect(Collectors.toCollection(HashSet::new));
			} else if (userExtraDTO.getProfileId() != null && userExtraDTO.getProfileId().equals(5L)) {
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
				for (UserExtra usr : usersChild) {
					sinistersPecByUser.addAll(viewSinisterPecRepository.findAllInMissionExpert(usr.getId()));
				}
				return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto)
						.collect(Collectors.toCollection(HashSet::new));
			} else {
				return new HashSet<>();
			}
		}

	}

	/**
	 * Get all the AcceptedAndNoReparator.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllInCancelMissionExpert(Long idUser) {
		log.debug("Request to get all In cancel Mission Expert");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllInCancelMissionExpertForSuperUser();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();

	}

	/**
	 * Get all the AcceptedAndNoReparator.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllAcceptedAndHasReparator() {
		log.debug("Request to get all AcceptedAndNoReparator");
		//String login = SecurityUtils.getCurrentUserLogin();
		//User user = userService.findOneUserByLogin(login);
		//UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
		//Integer size = userExtraDTO.getUserPartnerModes().size();
		//if (size.equals(0)) {
			//return viewSinisterPecRepository.findAllAcceptedAndHasReparatorForSuperUser().stream()
				//	.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		//} else {
		//	return viewSinisterPecRepository.findAllAcceptedAndHasReparator(user.getId()).stream()
		//			.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		//}
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userService.findOneUserByLogin(login);
		
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllAcceptedAndHasReparatorForSuperUser();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(user.getId());
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(user.getId())) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(user.getId());
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(user.getId())) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(user.getId());
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(user.getId())) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();

	}

	/**
	 * Get all the AcceptedAndNoReparator.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllPecsForReparator(Long id) {
		log.debug("Request to get all AcceptedAndNoReparator");
		UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
		if (userExtraDTO.getProfileId().equals(28L)) {
			return viewSinisterPecRepository.findAllPecsForReparator(
					userExtraDTO != null && userExtraDTO.getPersonId() != null ? userExtraDTO.getPersonId() : 0L)
					.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		} else {
			return viewSinisterPecRepository.findAllPecsForReparatorForSuperUser().stream()
					.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		}
	}

	/**
	 * Get all the findAllPecsForExpertOpinion.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllPecsForExpertOpinion(Long id) {
		log.debug("Request to get all findAllPecsForExpertOpinion");
		UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
		if (userExtraDTO.getProfileId().equals(27L)) {
			return viewSinisterPecRepository.findAllPecsForExpertOpinion(
					userExtraDTO != null && userExtraDTO.getPersonId() != null ? userExtraDTO.getPersonId() : 0L)
					.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		} else {
			return viewSinisterPecRepository.findAllPecsForExpertOpinionForSuperUser().stream()
					.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		}
	}

	/**
	 * Get all the verification pecs.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllPecsForValidation() {
		log.debug("Request to get all AcceptedAndNoReparator");
		return viewSinisterPecRepository.findAllPecsForValidation().stream().map(viewSinisterPecMapper::toDto)
				.collect(Collectors.toCollection(HashSet::new));
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllPecsForUpdateDevis(Long id) {
		log.debug("Request to get all pecs");
		UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(id);
		if (userExtraDTO.getProfileId().equals(28L)) {
			return viewSinisterPecRepository.findAllPecsForUpdateDevis(userExtraDTO.getPersonId()).stream()
					.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		} else {
			return viewSinisterPecRepository.findAllPecsForUpdateDevisForSuperUser().stream()
					.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
		}

	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllPecsInRevueValidationDevis(Long idUser) {

		log.debug("Request to get all sinisters pec to approve");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllPecsInRevueValidationDevisForSuperUser();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllSinPecForModificationPrestation(Long idUser) {
		log.debug("Request to get all pecs");

		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllSinPecForModificationPrestation();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findSinisterPecByAssignedId(Long assignedToId) {
		log.debug("Request to get all pecs");
		return viewSinisterPecRepository.findSinisterPecByAssignedId(assignedToId).stream()
				.map(viewSinisterPecMapper::toDto).collect(Collectors.toCollection(HashSet::new));
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllSinPecWithDecision() {
		log.debug("Request to get all pecs");
		return viewSinisterPecRepository.findAllSinPecWithDecision().stream().map(viewSinisterPecMapper::toDto)
				.collect(Collectors.toCollection(HashSet::new));
	}

	/**
	 * Get one viewSinisterPec by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public ViewSinisterPecDTO findOne(Long id) {
		log.debug("Request to get ViewSinisterPec : {}", id);
		ViewSinisterPec viewSinisterPec = viewSinisterPecRepository.findOne(id);
		return viewSinisterPecMapper.toDto(viewSinisterPec);
	}

	/**
	 * Delete the viewSinisterPec by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete ViewSinisterPec : {}", id);
		viewSinisterPecRepository.delete(id);
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllSinisterPecToApprove(Long idUser) {
		log.debug("Request to get all sinisters pec to approve");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllSinisterPecToApprove();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllPrestationPECForIdaOuverture(Long idUser) {
		log.debug("Request to get all sinisters pec for derogation");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllPrestationPECForIdaOuverture();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();

		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllSinisterPecForDerogation(Long idUser) {
		log.debug("Request to get all sinisters pec for derogation");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllSinisterPecForDerogation();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();

		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllAutresPiecesJointes(Long idUser) {
		log.debug("Request to get all sinisters pec to approve");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllSinisterPecForDerogation();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					//ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
					if (userExtra.getPersonId().equals(sinisterPec.getClientId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					//ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
					if (userExtra.getPersonId().equals(sinisterPec.getAgencyId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(28L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getReparateurId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(27L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getExpertId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if (sinisterPec.getAssignedToId() == null) {
							//ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
							if (userPartnerMode.getPartner().getId().equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if (sinisterPec.getAssignedToId() == null) {
							//ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
							if (userPartnerMode.getPartner().getId().equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						//ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
						if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
								&& userPartnerMode.getPartner().getId().equals(sinisterPec.getClientId())
								&& userExtra.getPersonId().equals(sinisterPec.getAgencyId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if (sinisterPec.getAssignedToId() == null) {
							//ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
							if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
									&& userPartnerMode.getPartner().getId().equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllConfirmRefusedPrestation(Long idUser) {
		log.debug("Request to get all refused sinisters pec");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllConfirmRefusedPrestation();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if (sinisterPec.getAssignedToId() == null) {
							ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
							if (userPartnerMode.getPartner().getId().equals(contract.getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if (sinisterPec.getAssignedToId() == null) {
							ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
							if (userPartnerMode.getPartner().getId().equals(contract.getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}

				for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if (sinisterPec.getAssignedToId() == null) {
							ContratAssurance contract = contratAssuranceRepository.findOne(sinisterPec.getContractId());
							if (userPartnerMode.getPartner().getId().equals(contract.getClient().getId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllRefusedPrestationPec(Long idUser) {
		log.debug("Request to get all refused sinisters pec");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllRefusedPrestation();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllAnnulationPrestationPec(Long idUser) {
		log.debug("Request to get all canceled sinisters pec");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllCanceledPrestation();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			sinistersPecByUser = sinistersPec;
		} else {
			if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	@Transactional(readOnly = true)
	public Long getCountDemandPec() {
		log.debug("Request to get all sinisters pec");
		return viewSinisterPecRepository.countAllDemandPecs();
	}

	@Transactional(readOnly = true)
	public Long getCountDemandPecWithFiltter(String filter) {
		log.debug("Request to get all sinisters pec");
		if (StringUtils.isNotBlank(filter)) {
			return viewSinisterPecRepository.countAllDemandPecsWithFilter(filter.toLowerCase());
		} else {
			return viewSinisterPecRepository.countAllDemandPecs();
		}
	}

	/**
	 * Get all the viewPolicies.
	 *
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<ViewSinisterPecDTO> findAllDemandsPec(String filter, Pageable pageable) {
		log.debug("Request to get a sinisters pec page");
		if (StringUtils.isNotBlank(filter)) {
			return viewSinisterPecRepository.findAllPecsDemand(pageable).map(viewSinisterPecMapper::toDto);

		} else {
			return viewSinisterPecRepository.findAllPecsDemand(pageable).map(viewSinisterPecMapper::toDto);
		}
	}
	
	@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllSinPecForSignatureBonSortie(Long idUser) {
		log.debug("Request to get all sinister pec For Signature Bon Sortie ");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllSinPecForSignatureBonSortie();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(28L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getReparateurId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(28L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getReparateurId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}
	
	
	
	
	
@Transactional(readOnly = true)
	public Set<ViewSinisterPecDTO> findAllViewSinisterPecRefusedAndAprouveOrApprvWithModif(Long idUser) {
		log.debug("Request to get all sinisters pec to approve");
		Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository.findAllViewSinisterPecRefusedAndAprouveOrApprvWithModif();

		Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
		UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
		Integer size = userExtra.getUserPartnerModes().size();
		if (size.equals(0)) {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getClientId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getAgencyId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else {
				sinistersPecByUser = sinistersPec;
			}
		} else {
			if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if (userExtra.getPersonId().equals(sinisterPec.getClientId())) {
						sinistersPecByUser.add(sinisterPec);
					}
				}
			} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getModeLabel() != null) {
							if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getAgencyId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}else {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())
									&& userExtra.getPersonId()
											.equals(sinisterPec.getAgencyId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			} else if (userExtra.getProfile().getId().equals(5L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getModeLabel() != null) {
							if (sinisterPec.getAssignedToId() == null) {
								if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getClientId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedToId() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getClientId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usr : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}

			} else if (userExtra.getProfile().getId().equals(4L)) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getModeLabel() != null) {
							if (sinisterPec.getAssignedToId() == null) {
								if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getClientId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedToId() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getClientId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
				Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
				for (UserExtra usrCh : usersChild) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {

							if (sinPec.getAssignedToId().equals(usrCh.getId())) {

								sinistersPecByUser.add(sinPec);

							}
						}
					}
					Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
					for (UserExtra usr : usersChilds) {
						for (ViewSinisterPec sinPec : sinistersPec) {
							if (sinPec.getAssignedToId() != null) {
								if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
									sinistersPecByUser.add(sinPec);
								}
							}
						}
					}
				}

			} else {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(idUser)) {
							sinistersPecByUser.add(sinPec);
						}
					}
				}
				for (UserPartnerMode userPartnerMode : usersPartnerModes) {
					for (ViewSinisterPec sinisterPec : sinistersPec) {
						if(sinisterPec.getModeLabel() != null) {
							if (sinisterPec.getAssignedToId() == null) {
								if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
										&& userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getClientId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}else {
							if (sinisterPec.getAssignedToId() == null) {
								if (userPartnerMode.getPartner().getId()
												.equals(sinisterPec.getClientId())) {
									sinistersPecByUser.add(sinisterPec);
								}
							}
						}
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
			return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}
	



@Transactional(readOnly = true)
public Set<ViewSinisterPecDTO> findAllViewSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(Long idUser) {
	log.debug("Request to get all sinisters pec Acc With Change Status");
	Set<ViewSinisterPec> sinistersPec = viewSinisterPecRepository
			.findAllViewSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif();

	Set<ViewSinisterPec> sinistersPecByUser = new HashSet<>();
	UserExtra userExtra = userExtraRepository.findByUser(idUser);
	Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
	Integer size = userExtra.getUserPartnerModes().size();
	if (size.equals(0)) {
		if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
			for (ViewSinisterPec sinisterPec : sinistersPec) {
				if (userExtra.getPersonId().equals(sinisterPec.getClientId())) {
					sinistersPecByUser.add(sinisterPec);
				}
			}
		} else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
			for (ViewSinisterPec sinisterPec : sinistersPec) {
				if (userExtra.getPersonId().equals(sinisterPec.getAgencyId())) {
					sinistersPecByUser.add(sinisterPec);
				}
			}
		} else {
			sinistersPecByUser = sinistersPec;
		}
	} else {
		if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
			for (ViewSinisterPec sinisterPec : sinistersPec) {
				if (userExtra.getPersonId().equals(sinisterPec.getClientId())) {
					sinistersPecByUser.add(sinisterPec);
				}
			}
		} else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
			for (UserPartnerMode userPartnerMode : usersPartnerModes) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if(sinisterPec.getModeLabel() != null) {
						if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
								&& userPartnerMode.getPartner().getId()
										.equals(sinisterPec.getClientId())
								&& userExtra.getPersonId()
										.equals(sinisterPec.getAgencyId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}else {
						if (userPartnerMode.getPartner().getId()
										.equals(sinisterPec.getClientId())
								&& userExtra.getPersonId()
										.equals(sinisterPec.getAgencyId())) {
							sinistersPecByUser.add(sinisterPec);
						}
					}
				}
			}
		} else if (userExtra.getProfile().getId().equals(5L)) {
			for (ViewSinisterPec sinPec : sinistersPec) {
				if (sinPec.getAssignedToId() != null) {

					if (sinPec.getAssignedToId().equals(idUser)) {

						sinistersPecByUser.add(sinPec);

					}
				}
			}
			for (UserPartnerMode userPartnerMode : usersPartnerModes) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if(sinisterPec.getModeLabel() != null) {
						if (sinisterPec.getAssignedToId() == null) {
							if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}else {
						if (sinisterPec.getAssignedToId() == null) {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			}
			Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
			for (UserExtra usr : usersChild) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {
						if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
							sinistersPecByUser.add(sinPec);
						}
					}
				}
			}

		} else if (userExtra.getProfile().getId().equals(4L)) {
			for (ViewSinisterPec sinPec : sinistersPec) {
				if (sinPec.getAssignedToId() != null) {

					if (sinPec.getAssignedToId().equals(idUser)) {

						sinistersPecByUser.add(sinPec);

					}
				}
			}
			for (UserPartnerMode userPartnerMode : usersPartnerModes) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if(sinisterPec.getModeLabel() != null) {
						if (sinisterPec.getAssignedToId() == null) {
							if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}else {
						if (sinisterPec.getAssignedToId() == null) {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			}
			Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(idUser);
			for (UserExtra usrCh : usersChild) {
				for (ViewSinisterPec sinPec : sinistersPec) {
					if (sinPec.getAssignedToId() != null) {

						if (sinPec.getAssignedToId().equals(usrCh.getId())) {

							sinistersPecByUser.add(sinPec);

						}
					}
				}
				Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
				for (UserExtra usr : usersChilds) {
					for (ViewSinisterPec sinPec : sinistersPec) {
						if (sinPec.getAssignedToId() != null) {
							if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
								sinistersPecByUser.add(sinPec);
							}
						}
					}
				}
			}

		} else {
			for (ViewSinisterPec sinPec : sinistersPec) {
				if (sinPec.getAssignedToId() != null) {

					if (sinPec.getAssignedToId().equals(idUser)) {

						sinistersPecByUser.add(sinPec);

					}
				}
			}
			for (UserPartnerMode userPartnerMode : usersPartnerModes) {
				for (ViewSinisterPec sinisterPec : sinistersPec) {
					if(sinisterPec.getModeLabel() != null) {
						if (sinisterPec.getAssignedToId() == null) {
							if (userPartnerMode.getModeGestion().getLibelle().equals(sinisterPec.getModeLabel())
									&& userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}else {
						if (sinisterPec.getAssignedToId() == null) {
							if (userPartnerMode.getPartner().getId()
											.equals(sinisterPec.getClientId())) {
								sinistersPecByUser.add(sinisterPec);
							}
						}
					}
				}
			}
		}
	}

	if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
		return sinistersPecByUser.stream().map(viewSinisterPecMapper::toDto).collect(Collectors.toSet());
	}
	return new HashSet<>();
}


	
	
	
	
}
