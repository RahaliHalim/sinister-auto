package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ProfileAccessService;
import com.gaconnecte.auxilium.service.dto.EntityProfileAccessDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.ProfileAccessDTO;
import com.gaconnecte.auxilium.service.dto.UserAccessWorkDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;


/**
 * REST controller for managing ProfileAccess.
 */
@RestController
@RequestMapping("/api")
public class ProfileAccessResource {

    private final Logger log = LoggerFactory.getLogger(ProfileAccessResource.class);

    private static final String ENTITY_NAME = "profileAccess";

    private final ProfileAccessService profileAccessService;

    public ProfileAccessResource(ProfileAccessService profileAccessService) {
        this.profileAccessService = profileAccessService;
    }

    /**
     * POST /profile-accesses : Create a new profileAccess.
     *
     * @param profileAccessDTO the profileAccessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new profileAccessDTO, or with status 400 (Bad Request) if the
     * profileAccess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profile-accesses")
    @Timed
    public ResponseEntity<ProfileAccessDTO> createProfileAccess(@RequestBody ProfileAccessDTO profileAccessDTO) throws URISyntaxException {
        log.debug("REST request to save ProfileAccess : {}", profileAccessDTO);
        if (profileAccessDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new profileAccess cannot already have an ID")).body(null);
        }
        ProfileAccessDTO result = profileAccessService.save(profileAccessDTO);
        return ResponseEntity.created(new URI("/api/profile-accesses/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /profile-accesses : Updates an existing profileAccess.
     *
     * @param profileAccessDTO the profileAccessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * profileAccessDTO, or with status 400 (Bad Request) if the
     * profileAccessDTO is not valid, or with status 500 (Internal Server Error)
     * if the profileAccessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profile-accesses")
    @Timed
    public ResponseEntity<ProfileAccessDTO> updateProfileAccess(@RequestBody ProfileAccessDTO profileAccessDTO) throws URISyntaxException {
        log.debug("REST request to update ProfileAccess : {}", profileAccessDTO);
        if (profileAccessDTO.getId() == null) {
            return createProfileAccess(profileAccessDTO);
        }
        ProfileAccessDTO result = profileAccessService.save(profileAccessDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileAccessDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /profile-accesses : get all the profileAccesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * profileAccesses in body
     */
    @GetMapping("/profile-accesses")
    @Timed
    public List<ProfileAccessDTO> getAllProfileAccesses() {
        log.debug("REST request to get all ProfileAccesses");
        return profileAccessService.findAll();
    }

    /**
     * GET /profile-accesses/:id : get the "id" profileAccess.
     *
     * @param id the id of the profileAccessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * profileAccessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profile-accesses/{id}")
    @Timed
    public ResponseEntity<ProfileAccessDTO> getProfileAccess(@PathVariable Long id) {
        log.debug("REST request to get ProfileAccess : {}", id);
        ProfileAccessDTO profileAccessDTO = profileAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profileAccessDTO));
    }

    /**
     * GET /profile-accesses/profile/:id : get the "id" profileAccess.
     *
     * @param id the id of the profileAccessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * profileAccessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profile-accesses/profile/{id}")
    @Timed
    public List<ProfileAccessDTO> getProfileAccessByProfile(@PathVariable Long id) {
        log.debug("REST request to get ProfileAccess by profile : {}", id);
        return profileAccessService.findAllByProfile(id);
    }

    /**
     * POST /profile-accesses/profile/:id : get the "id" profileAccess.
     *
     * @param id the id of the profileAccessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * profileAccessDTO, or with status 404 (Not Found)
     */
    @PostMapping("/profile-accesses/entity/profile")
    @Timed
    public UserAccessWorkDTO getEntityProfileAccessByProfile(@RequestBody UserAccessWorkDTO userAccessWorkDTO) {
    	log.debug("REST request to get EntityProfileAccess by profile : {}", userAccessWorkDTO.getId());
        // Init old access
        log.debug("0/________________{}", userAccessWorkDTO);
        if (userAccessWorkDTO.isAddFlag()) {
            List<EntityProfileAccessDTO> entityProfileAccesss = new LinkedList<>();
            Map<Long, EntityProfileAccessDTO> map = new HashMap<>();
            if (CollectionUtils.isNotEmpty(userAccessWorkDTO.getEntityProfileAccesss())) {
                for (EntityProfileAccessDTO entityProfileAccess : userAccessWorkDTO.getEntityProfileAccesss()) {
                    map.put(entityProfileAccess.getId(), entityProfileAccess);
                }
            }

            List<ProfileAccessDTO> profileAccesss = profileAccessService.findAllByProfile(userAccessWorkDTO.getId());
            if (CollectionUtils.isNotEmpty(profileAccesss)) {
                for (ProfileAccessDTO profileAccess : profileAccesss) {
                    if (map.containsKey(profileAccess.getBusinessEntityId())) {
                        map.get(profileAccess.getBusinessEntityId()).addProfileAccesss(profileAccess);
                    } else {
                        EntityProfileAccessDTO epa = new EntityProfileAccessDTO();
                        epa.setId(profileAccess.getBusinessEntityId());
                        epa.setLabel(profileAccess.getBusinessEntityLabel());
                        epa.addProfileAccesss(profileAccess);
                        map.put(profileAccess.getBusinessEntityId(), epa);
                    }
                }
                entityProfileAccesss.addAll(map.values());
                userAccessWorkDTO.setEntityProfileAccesss(entityProfileAccesss);
                log.debug("1/________________{}", userAccessWorkDTO);
            }
        } else {
            List<EntityProfileAccessDTO> entityProfileAccesss = new LinkedList<>();
            log.debug("100/_______________________________________________________________________________");
            for (EntityProfileAccessDTO epa : userAccessWorkDTO.getEntityProfileAccesss()) {
                List<ProfileAccessDTO> profileAccesss = new LinkedList<>();
                log.debug("101/________________{}", epa);
                for (ProfileAccessDTO pa : epa.getProfileAccesss()) {
                    log.debug("102/________________{}", pa);
                    if(pa.getProfileIds().contains(userAccessWorkDTO.getId())) {
                        log.debug("1031/________________{}", pa);
                        pa.removeProfileId(userAccessWorkDTO.getId());
                        log.debug("1031/________________{}", pa);
                    }
                    if(pa.getProfileIds().contains(userAccessWorkDTO.getId())) {
                        log.debug("1041/________________{}", pa);
                        pa.removeProfileId(userAccessWorkDTO.getId());
                        log.debug("1041/________________{}", pa);
                    }
                    if(CollectionUtils.isNotEmpty(pa.getProfileIds())) {
                        log.debug("1051/________________{}", pa);
                        if(pa.getProfileId().equals(userAccessWorkDTO.getId())) {
                            log.debug("1052/________________{}", pa);
                            pa.setProfileId(pa.getProfileIds().get(0));
                            log.debug("1053/________________{}", pa);
                        }
                        profileAccesss.add(pa);
                        log.debug("1061/________________{}", profileAccesss);
                    }
                    log.debug("1061/________________{}", profileAccesss);
                    log.debug("101/______________________________________");
                }
                if(CollectionUtils.isNotEmpty(profileAccesss)) {
                    epa.setProfileAccesss(null);
                    epa.getProfileAccesss().addAll(profileAccesss);
                    entityProfileAccesss.add(epa);
                    log.debug("11/________________{}", entityProfileAccesss);
                }
            }
            userAccessWorkDTO.setEntityProfileAccesss(entityProfileAccesss);
            log.debug("2/________________{}", userAccessWorkDTO);
        }
        return userAccessWorkDTO;
    }

    /**
     * DELETE /profile-accesses/:id : delete the "id" profileAccess.
     *
     * @param id the id of the profileAccessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profile-accesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfileAccess(@PathVariable Long id) {
        log.debug("REST request to delete ProfileAccess : {}", id);
        profileAccessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/profile-accesses?query=:query : search for the
     * profileAccess corresponding to the query.
     *
     * @param query the query of the profileAccess search
     * @return the result of the search
     */
    @GetMapping("/_search/profile-accesses")
    @Timed
    public List<ProfileAccessDTO> searchProfileAccesses(@RequestParam String query) {
        log.debug("REST request to search ProfileAccesses for query {}", query);
        return profileAccessService.search(query);
    }

}
