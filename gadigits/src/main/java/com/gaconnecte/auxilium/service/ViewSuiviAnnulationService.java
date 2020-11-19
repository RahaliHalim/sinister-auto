package com.gaconnecte.auxilium.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.ViewSuiviAnnulationRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviAnnulationDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSuiviAnnulationMapper;

@Service
@Transactional
public class ViewSuiviAnnulationService {
	private final Logger log = LoggerFactory.getLogger(ViewSuiviAnnulationService.class);

	private final ViewSuiviAnnulationRepository viewSuiviAnnulationRepository;
	private final ViewSuiviAnnulationMapper viewSuiviAnnulationMapper;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserExtraRepository userExtraRepository;

	public ViewSuiviAnnulationService(ViewSuiviAnnulationRepository viewSuiviAnnulationRepository,
			ViewSuiviAnnulationMapper viewSuiviAnnulationMapper) {

		this.viewSuiviAnnulationRepository = viewSuiviAnnulationRepository;
		this.viewSuiviAnnulationMapper = viewSuiviAnnulationMapper;

	}

	@Transactional(readOnly = true)
	public List<ViewSuiviAnnulationDTO> findAll() {
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);
		UserExtra userExtra = userExtraRepository.findByUser(user.getId());
		if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
			return viewSuiviAnnulationRepository.findAllByCompagny(userExtra.getPersonId()).stream().map(viewSuiviAnnulationMapper::toDto)
					.collect(Collectors.toCollection(LinkedList::new));
		} else {
			return viewSuiviAnnulationRepository.findAll().stream().map(viewSuiviAnnulationMapper::toDto)
					.collect(Collectors.toCollection(LinkedList::new));
		}
	}

	@Transactional(readOnly = true)
	public List<ViewSuiviAnnulationDTO> Search(SearchDTO searchDTO) {

		String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);
		UserExtra userExtra = userExtraRepository.findByUser(user.getId());
		LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
				: LocalDate.of(1900, Month.JANUARY, 1);
		LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
				: LocalDate.of(9000, Month.JANUARY, 1);

		if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
			searchDTO.setCompagnieId(userExtra.getPersonId());
		}

		if (searchDTO.getCompagnieId() == null) {
			return viewSuiviAnnulationRepository.findAllByDates(startDate, endDate).stream()
					.map(viewSuiviAnnulationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
		} else {
			return viewSuiviAnnulationRepository
					.findAllByDatesAndCompagny(searchDTO.getCompagnieId(), startDate, endDate).stream()
					.map(viewSuiviAnnulationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
		}

	}

}
