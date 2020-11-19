package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.UserCelluleService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.UserCelluleDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserCellule.
 */
@RestController
@RequestMapping("/api")
public class UserCelluleResource {

    private final Logger log = LoggerFactory.getLogger(UserCelluleResource.class);

    private static final String ENTITY_NAME = "userCellule";

    private final UserCelluleService userCelluleService;

    public UserCelluleResource(UserCelluleService userCelluleService) {
        this.userCelluleService = userCelluleService;
    }

    /**
     * POST  /user-cellules : Create a new userCellule.
     *
     * @param userCelluleDTO the userCelluleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userCelluleDTO, or with status 400 (Bad Request) if the userCellule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-cellules")
    @Timed
    public ResponseEntity<UserCelluleDTO> createUserCellule(@RequestBody UserCelluleDTO userCelluleDTO) throws URISyntaxException {
        log.debug("REST request to save UserCellule : {}", userCelluleDTO);
        if (userCelluleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userCellule cannot already have an ID")).body(null);
        }
        UserCelluleDTO result = userCelluleService.save(userCelluleDTO);
        return ResponseEntity.created(new URI("/api/user-cellules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
        
    }

    /**
     * PUT  /user-cellules : Updates an existing userCellule.
     *
     * @param userCelluleDTO the userCelluleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userCelluleDTO,
     * or with status 400 (Bad Request) if the userCelluleDTO is not valid,
     * or with status 500 (Internal Server Error) if the userCelluleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-cellules")
    @Timed
    public ResponseEntity<UserCelluleDTO> updateUserCellule(@RequestBody UserCelluleDTO userCelluleDTO) throws URISyntaxException {
        log.debug("REST request to update UserCellule : {}", userCelluleDTO);
        if (userCelluleDTO.getId() == null) {
            return createUserCellule(userCelluleDTO);
        }
        UserCelluleDTO result = userCelluleService.save(userCelluleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userCelluleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-cellules : get all the userCellules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userCellules in body
     */
    @GetMapping("/user-cellules")
    @Timed
    public ResponseEntity<List<UserCelluleDTO>> getAllUserCellules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserCellules");
        Page<UserCelluleDTO> page = userCelluleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-cellules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-cellules/:id : get the "id" userCellule.
     *
     * @param id the id of the userCelluleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userCelluleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-cellules/{id}")
    @Timed
    public ResponseEntity<UserCelluleDTO> getUserCellule(@PathVariable Long id) {
        log.debug("REST request to get UserCellule : {}", id);
        UserCelluleDTO userCelluleDTO = userCelluleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCelluleDTO));
    }

    /**
     * GET  /user-cellules/:id : get the "id" userCellule.
     *
     * @param id the id of the userCelluleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userCelluleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-cellules/{userId}/{celluleId}")
    @Timed
    public ResponseEntity<UserCelluleDTO> getUserCelluleByUserAndCellule(@PathVariable Long userId, @PathVariable Long celluleId) {
        log.debug("REST request to get UserCellule : {}", userId,celluleId);
        UserCelluleDTO userCelluleDTO = userCelluleService.findByUserAndCellule(userId, celluleId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCelluleDTO));
    }


    /**
     * DELETE  /user-cellules/:id : delete the "id" userCellule.
     *
     * @param id the id of the userCelluleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-cellules/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserCellule(@PathVariable Long id) {
        log.debug("REST request to delete UserCellule : {}", id);
        userCelluleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refCompagnies in body
     */

    @GetMapping("/user-cellules/user/{userId}")
    @Timed
    public ResponseEntity<List<UserCelluleDTO>> getByUser(@PathVariable Long userId) {
        log.debug("REST request to get a page of All unblocked RefCompagnies");
        List<UserCelluleDTO> userCellule = userCelluleService.findByUser(userId);
        return new ResponseEntity<>(userCellule, HttpStatus.OK);
    }

    
}