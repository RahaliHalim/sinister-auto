package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DelegationService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Delegation.
 */
@RestController
@RequestMapping("/api")
public class DelegationResource {

    private final Logger log = LoggerFactory.getLogger(DelegationResource.class);

    private static final String ENTITY_NAME = "delegation";

    private final DelegationService delegationService;

    public DelegationResource(DelegationService delegationService) {
        this.delegationService = delegationService;
    }

    /**
     * POST  /delegations : Create a new delegation.
     *
     * @param delegationDTO the delegationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new delegationDTO, or with status 400 (Bad Request) if the delegation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delegations")
    @Timed
    public ResponseEntity<DelegationDTO> createDelegation(@RequestBody DelegationDTO delegationDTO) throws URISyntaxException {
        log.debug("REST request to save Delegation : {}", delegationDTO);
        if (delegationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new delegation cannot already have an ID")).body(null);
        }
        DelegationDTO result = delegationService.save(delegationDTO);
        return ResponseEntity.created(new URI("/api/delegations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delegations : Updates an existing delegation.
     *
     * @param delegationDTO the delegationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated delegationDTO,
     * or with status 400 (Bad Request) if the delegationDTO is not valid,
     * or with status 500 (Internal Server Error) if the delegationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delegations")
    @Timed
    public ResponseEntity<DelegationDTO> updateDelegation(@RequestBody DelegationDTO delegationDTO) throws URISyntaxException {
        log.debug("REST request to update Delegation : {}", delegationDTO);
        if (delegationDTO.getId() == null) {
            return createDelegation(delegationDTO);
        }
        DelegationDTO result = delegationService.save(delegationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, delegationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delegations : get all the delegations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of delegations in body
     */
    @GetMapping("/delegations-gageo")
    @Timed
    public List<DelegationDTO> findAllDelGageo() {
        log.debug("REST request to get all Delegations");
        return delegationService.findAll();
    }
    
    @GetMapping("/delegations")
    @Timed
    public List<DelegationDTO> getAllDelegations() {
        log.debug("REST request to get all Delegations");
        return delegationService.findAllDelNotGageo();
    }

    /**
     * GET  /delegations/governorate/:id : get all governorate delegations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of delegations in body
     */
    @GetMapping("/delegations/governorate/{id}")
    @Timed
    public List<DelegationDTO> getAllGovernorateDelegations(@PathVariable Long id) {
        log.debug("REST request to get all governorate Delegations");
        return delegationService.findAllByGovernorate(id);
    }

    /**
     * GET  /delegations/:id : get the "id" delegation.
     *
     * @param id the id of the delegationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the delegationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/delegations/{id}")
    @Timed
    public ResponseEntity<DelegationDTO> getDelegation(@PathVariable Long id) {
        log.debug("REST request to get Delegation : {}", id);
        DelegationDTO delegationDTO = delegationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(delegationDTO));
    }

    /**
     * DELETE  /delegations/:id : delete the "id" delegation.
     *
     * @param id the id of the delegationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delegations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDelegation(@PathVariable Long id) {
        log.debug("REST request to delete Delegation : {}", id);
        delegationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/delegations?query=:query : search for the delegation corresponding
     * to the query.
     *
     * @param query the query of the delegation search
     * @return the result of the search
     */
    @GetMapping("/_search/delegations")
    @Timed
    public List<DelegationDTO> searchDelegations(@RequestParam String query) {
        log.debug("REST request to search Delegations for query {}", query);
        return delegationService.search(query);
    }

}
