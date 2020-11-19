package com.gaconnecte.auxilium.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.gaconnecte.auxilium.security.SecurityUtils;

import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.repository.ViewSuiviDossiersRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewBonificationDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.service.mapper.ViewSuiviDossiersMapper;
import com.gaconnecte.auxilium.domain.view.ViewSuiviDossiers;

@Service
@Transactional
public class ViewSuiviDossiersService {

    private final Logger log = LoggerFactory.getLogger(ViewSuiviDossiersService.class);
    private final ViewSuiviDossiersRepository viewSuiviDossiersRepository;
    private final ViewSuiviDossiersMapper viewSuiviDossiersMapper;

    @Autowired
	UserRepository userRepository;

	@Autowired
	UserExtraRepository userExtraRepository;

    public ViewSuiviDossiersService(ViewSuiviDossiersRepository viewSuiviDossiersRepository,
            ViewSuiviDossiersMapper viewSuiviDossiersMapper) {

        this.viewSuiviDossiersRepository = viewSuiviDossiersRepository;
        this.viewSuiviDossiersMapper = viewSuiviDossiersMapper;

    }

    @Transactional(readOnly = true)
    public List<ViewSuiviDossiersDTO> findAll() {
        return viewSuiviDossiersRepository.findAll().stream().map(viewSuiviDossiersMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<ViewSuiviDossiersDTO> Search(SearchDTO searchDTO) {
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
            return viewSuiviDossiersRepository.findAllByDates(startDate, endDate).stream()
                    .map(viewSuiviDossiersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        } else {
            return viewSuiviDossiersRepository.findAllByDatesAndCompagny(searchDTO.getCompagnieId(), startDate, endDate)
                    .stream().map(viewSuiviDossiersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        }

    }

}
