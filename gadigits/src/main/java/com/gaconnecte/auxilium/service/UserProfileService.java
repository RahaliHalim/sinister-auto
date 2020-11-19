package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.UserProfile;
import com.gaconnecte.auxilium.repository.UserProfileRepository;
import com.gaconnecte.auxilium.repository.search.UserProfileSearchRepository;
import com.gaconnecte.auxilium.service.dto.UserProfileDTO;
import com.gaconnecte.auxilium.service.mapper.UserProfileMapper;
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
 * Service Implementation for managing UserProfile.
 */
@Service
@Transactional
public class UserProfileService {

    private final Logger log = LoggerFactory.getLogger(UserProfileService.class);

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    private final UserProfileSearchRepository userProfileSearchRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, UserProfileSearchRepository userProfileSearchRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.userProfileSearchRepository = userProfileSearchRepository;
    }

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    public UserProfileDTO save(UserProfileDTO userProfileDTO) {
        log.debug("Request to save UserProfile : {}", userProfileDTO);
        UserProfile userProfile = userProfileMapper.toEntity(userProfileDTO);
        userProfile = userProfileRepository.save(userProfile);
        UserProfileDTO result = userProfileMapper.toDto(userProfile);
        userProfileSearchRepository.save(userProfile);
        return result;
    }

    /**
     *  Get all the userProfiles.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserProfileDTO> findAll() {
        log.debug("Request to get all UserProfiles");
        return userProfileRepository.findAllByOrderByLabelAsc().stream()
            .map(userProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one userProfile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserProfileDTO findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        UserProfile userProfile = userProfileRepository.findOne(id);
        return userProfileMapper.toDto(userProfile);
    }

    /**
     *  Delete the  userProfile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
        userProfileSearchRepository.delete(id);
    }

    /**
     * Search for the userProfile corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserProfileDTO> search(String query) {
        log.debug("Request to search UserProfiles for query {}", query);
        return StreamSupport
            .stream(userProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userProfileMapper::toDto)
            .collect(Collectors.toList());
    }
}
