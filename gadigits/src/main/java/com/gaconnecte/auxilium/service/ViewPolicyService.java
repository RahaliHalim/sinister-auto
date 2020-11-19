package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import com.gaconnecte.auxilium.domain.view.ViewPolicyIndicator;
import com.gaconnecte.auxilium.repository.ViewPolicyRepository;
import com.gaconnecte.auxilium.repository.search.ViewPolicySearchRepository;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.mapper.ViewPolicyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Implementation for managing ViewPolicy.
 */
@Service
@Transactional
public class ViewPolicyService {

    private final Logger log = LoggerFactory.getLogger(ViewPolicyService.class);

    private final ViewPolicyRepository viewPolicyRepository;

    private final ViewPolicyMapper viewPolicyMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserExtraRepository userExtraRepository;

    private final ViewPolicySearchRepository viewPolicySearchRepository;

    public ViewPolicyService(ViewPolicyRepository viewPolicyRepository, ViewPolicyMapper viewPolicyMapper,
            ViewPolicySearchRepository viewPolicySearchRepository) {
        this.viewPolicyRepository = viewPolicyRepository;
        this.viewPolicyMapper = viewPolicyMapper;
        this.viewPolicySearchRepository = viewPolicySearchRepository;
    }

    /**
     * Save a viewPolicy.
     *
     * @param viewPolicyDTO the entity to save
     * @return the persisted entity
     */
    public ViewPolicyDTO save(ViewPolicyDTO viewPolicyDTO) {
        log.debug("Request to save ViewPolicy : {}", viewPolicyDTO);
        ViewPolicy viewPolicy = viewPolicyMapper.toEntity(viewPolicyDTO);
        viewPolicy = viewPolicyRepository.save(viewPolicy);
        ViewPolicyDTO result = viewPolicyMapper.toDto(viewPolicy);
        viewPolicySearchRepository.save(viewPolicy);
        return result;
    }

    /**
     * Get all the viewPolicies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewPolicyDTO> findAll() {
        log.debug("Request to get all ViewPolicies");
        return viewPolicyRepository.findAll().stream().map(viewPolicyMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the viewPolicies indicators.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewPolicyIndicator> findAllPolicyIndicators() {
        log.debug("Request to get all ViewPolicies indicators");
        List<ViewPolicyIndicator> indicators = new LinkedList<ViewPolicyIndicator>();
        String[][] matrix = viewPolicyRepository.findAllPolicyIndicators();
        for (int i = 0; i < matrix.length; i++) {
            String[] line = matrix[i];
            indicators.add(new ViewPolicyIndicator(line[0], line[1], Long.valueOf(line[2]), line[3]));
        }
        return indicators;
    }

    /**
     * Get all the viewPolicies indicators.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewPolicyIndicator> findAllPolicyIndicators(SearchDTO searchDTO) {
        log.debug("Request to get all ViewPolicies indicators {}", searchDTO.toString());
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        UserExtra userExtra = userExtraRepository.findByUser(user.getId());
        List<ViewPolicyIndicator> indicators = new LinkedList<ViewPolicyIndicator>();
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate()
                : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate()
                : LocalDate.of(9000, Month.JANUARY, 1);

        // String startDate = searchDTO.getStartDate() != null ?
        // DateTimeFormatter.ofPattern("yyyy-MM-dd").format(searchDTO.getStartDate()) :
        // "1970-01-01";
        // String endDate = searchDTO.getEndDate() != null ?
        // DateTimeFormatter.ofPattern("yyyy-MM-dd").format(searchDTO.getEndDate()) :
        // "2222-01-01";
        if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
            searchDTO.setCompagnieId(userExtra.getPersonId());
        }

        String[][] matrix = viewPolicyRepository.findAllPolicyIndicators(
                searchDTO.getCompagnieId() != null ? searchDTO.getCompagnieId() : 0L,
                searchDTO.getZoneId() != null ? searchDTO.getZoneId() : 0L,
                searchDTO.getPackId() != null ? searchDTO.getPackId() : 0L, startDate, endDate);
        for (int i = 0; i < matrix.length; i++) {
            String[] line = matrix[i];
            indicators.add(new ViewPolicyIndicator(line[0], line[1], Long.valueOf(line[2]), line[3]));
        }
        return indicators;
    }

    /**
     * Get all the viewPolicies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ViewPolicyDTO> findAll(String filter, Pageable pageable) {
        log.debug("Request to get a ViewPolicies page");
        if (StringUtils.isNotBlank(filter)) {
            return viewPolicyRepository.findAllWithFilter(filter.toLowerCase(), pageable).map(viewPolicyMapper::toDto);
        } else {
            return viewPolicyRepository.findAll(pageable).map(viewPolicyMapper::toDto);
        }
    }

    /**
     * Get all the viewPolicies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Long getCountPolicies() {
        log.debug("Request to get all ViewPolicies");
        return viewPolicyRepository.count();
    }

    /**
     * Get all the viewPolicies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Long getCountPoliciesWithFiltter(String filter) {
        log.debug("Request to get all ViewPolicies");
        if (StringUtils.isNotBlank(filter)) {
            return viewPolicyRepository.countAllWithFilter(filter.toLowerCase());
        } else {
            return viewPolicyRepository.count();
        }
    }

    /**
     * Get one viewPolicy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ViewPolicyDTO findOne(Long id) {
        log.debug("Request to get ViewPolicy : {}", id);
        ViewPolicy viewPolicy = viewPolicyRepository.findOne(id);
        return viewPolicyMapper.toDto(viewPolicy);
    }

    /**
     * Delete the viewPolicy by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ViewPolicy : {}", id);
        viewPolicyRepository.delete(id);
        viewPolicySearchRepository.delete(id);
    }

    /**
     * Search for the viewPolicy corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewPolicyDTO> search(String query) {
        log.debug("Request to search ViewPolicies for query {}", query);
        return StreamSupport.stream(viewPolicySearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .map(viewPolicyMapper::toDto).collect(Collectors.toList());
    }
}
