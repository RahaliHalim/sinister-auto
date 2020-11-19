package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.UserAccess;
import com.gaconnecte.auxilium.repository.UserAccessRepository;
import com.gaconnecte.auxilium.repository.search.UserAccessSearchRepository;
import com.gaconnecte.auxilium.service.dto.UserAccessDTO;
import com.gaconnecte.auxilium.service.dto.FunctionalityDTO;
import com.gaconnecte.auxilium.service.mapper.UserAccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserAccess.
 */
@Service
@Transactional
public class UserAccessService {

    private final Logger log = LoggerFactory.getLogger(UserAccessService.class);

    private final UserAccessRepository userAccessRepository;

    private final UserAccessMapper userAccessMapper;

    private final UserAccessSearchRepository userAccessSearchRepository;

    public UserAccessService(UserAccessRepository userAccessRepository, UserAccessMapper userAccessMapper, UserAccessSearchRepository userAccessSearchRepository) {
        this.userAccessRepository = userAccessRepository;
        this.userAccessMapper = userAccessMapper;
        this.userAccessSearchRepository = userAccessSearchRepository;
    }

    /**
     * Save a userAccess.
     *
     * @param userAccessDTO the entity to save
     * @return the persisted entity
     */
    public UserAccessDTO save(UserAccessDTO userAccessDTO) {
        log.debug("Request to save UserAccess : {}", userAccessDTO);
        UserAccess userAccess = userAccessMapper.toEntity(userAccessDTO);
        userAccess = userAccessRepository.save(userAccess);
        UserAccessDTO result = userAccessMapper.toDto(userAccess);
        userAccessSearchRepository.save(userAccess);
        return result;
    }

    /**
     *  Get all the userAccesses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserAccessDTO> findAll() {
        log.debug("Request to get all UserAccesses");
        return userAccessRepository.findAll().stream()
            .map(userAccessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one userAccess by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserAccessDTO findOne(Long id) {
        log.debug("Request to get UserAccess : {}", id);
        UserAccess userAccess = userAccessRepository.findOne(id);
        return userAccessMapper.toDto(userAccess);
    }

    /**
     *  Delete the  userAccess by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAccess : {}", id);
        userAccessRepository.delete(id);
        userAccessSearchRepository.delete(id);
    }

    /**
     * Search for the userAccess corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserAccessDTO> search(String query) {
        log.debug("Request to search UserAccesses for query {}", query);
        return StreamSupport
            .stream(userAccessSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userAccessMapper::toDto)
            .collect(Collectors.toList());
    }
}
