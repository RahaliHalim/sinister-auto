package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.ProfileAccess;
import com.gaconnecte.auxilium.repository.ProfileAccessRepository;
import com.gaconnecte.auxilium.repository.search.ProfileAccessSearchRepository;
import com.gaconnecte.auxilium.service.dto.ProfileAccessDTO;
import com.gaconnecte.auxilium.service.mapper.ProfileAccessMapper;
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
 * Service Implementation for managing ProfileAccess.
 */
@Service
@Transactional
public class ProfileAccessService {

    private final Logger log = LoggerFactory.getLogger(ProfileAccessService.class);

    private final ProfileAccessRepository profileAccessRepository;

    private final ProfileAccessMapper profileAccessMapper;

    private final ProfileAccessSearchRepository profileAccessSearchRepository;

    public ProfileAccessService(ProfileAccessRepository profileAccessRepository, ProfileAccessMapper profileAccessMapper, ProfileAccessSearchRepository profileAccessSearchRepository) {
        this.profileAccessRepository = profileAccessRepository;
        this.profileAccessMapper = profileAccessMapper;
        this.profileAccessSearchRepository = profileAccessSearchRepository;
    }

    /**
     * Save a profileAccess.
     *
     * @param profileAccessDTO the entity to save
     * @return the persisted entity
     */
    public ProfileAccessDTO save(ProfileAccessDTO profileAccessDTO) {
        log.debug("Request to save ProfileAccess : {}", profileAccessDTO);
        ProfileAccess profileAccess = profileAccessMapper.toEntity(profileAccessDTO);
        profileAccess = profileAccessRepository.save(profileAccess);
        ProfileAccessDTO result = profileAccessMapper.toDto(profileAccess);
        profileAccessSearchRepository.save(profileAccess);
        return result;
    }

    /**
     *  Get all the profileAccesses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProfileAccessDTO> findAll() {
        log.debug("Request to get all ProfileAccesses");
        return profileAccessRepository.findAll().stream()
            .map(profileAccessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one profileAccess by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProfileAccessDTO findOne(Long id) {
        log.debug("Request to get ProfileAccess : {}", id);
        ProfileAccess profileAccess = profileAccessRepository.findOne(id);
        return profileAccessMapper.toDto(profileAccess);
    }

    /**
     *  Get all the profileAccesses by profile.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProfileAccessDTO> findAllByProfile(Long id) {
        log.debug("Request to get all ProfileAccesses by profile");
        return profileAccessRepository.findByProfileId(id).stream()
            .map(profileAccessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Delete the  profileAccess by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProfileAccess : {}", id);
        profileAccessRepository.delete(id);
        profileAccessSearchRepository.delete(id);
    }

    /**
     * Search for the profileAccess corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProfileAccessDTO> search(String query) {
        log.debug("Request to search ProfileAccesses for query {}", query);
        return StreamSupport
            .stream(profileAccessSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(profileAccessMapper::toDto)
            .collect(Collectors.toList());
    }
}
