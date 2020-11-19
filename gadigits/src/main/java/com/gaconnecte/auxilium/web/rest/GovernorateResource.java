package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.GovernorateService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
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
 * REST controller for managing Governorate.
 */
@RestController
@RequestMapping("/api")
public class GovernorateResource {

    private final Logger log = LoggerFactory.getLogger(GovernorateResource.class);

    private static final String ENTITY_NAME = "governorate";

    private final GovernorateService governorateService;

    public GovernorateResource(GovernorateService governorateService) {
        this.governorateService = governorateService;
    }

    /**
     * POST  /governorates : Create a new governorate.
     *
     * @param governorateDTO the governorateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new governorateDTO, or with status 400 (Bad Request) if the governorate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/governorates")
    @Timed
    public ResponseEntity<GovernorateDTO> createGovernorate(@RequestBody GovernorateDTO governorateDTO) throws URISyntaxException {
        log.debug("REST request to save Governorate : {}", governorateDTO);
        if (governorateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new governorate cannot already have an ID")).body(null);
        }
        GovernorateDTO result = governorateService.save(governorateDTO);
        return ResponseEntity.created(new URI("/api/governorates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /governorates : Updates an existing governorate.
     *
     * @param governorateDTO the governorateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated governorateDTO,
     * or with status 400 (Bad Request) if the governorateDTO is not valid,
     * or with status 500 (Internal Server Error) if the governorateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/governorates")
    @Timed
    public ResponseEntity<GovernorateDTO> updateGovernorate(@RequestBody GovernorateDTO governorateDTO) throws URISyntaxException {
        log.debug("REST request to update Governorate : {}", governorateDTO);
        if (governorateDTO.getId() == null) {
            return createGovernorate(governorateDTO);
        }
        GovernorateDTO result = governorateService.save(governorateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, governorateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /governorates : get all the governorates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of governorates in body
     */
    @GetMapping("/governorates-gageo")
    @Timed
    public List<GovernorateDTO> findAllGovGageo() {
        log.debug("REST request to get all Governorates");
        return governorateService.findAll();
    }
    
    @GetMapping("/governorates")
    @Timed
    public List<GovernorateDTO> getAllGovernorates() {
        log.debug("REST request to get all Governorates");
        return governorateService.findAllGovNotGageo();
    }

    /**
     * GET  /governorates/:id : get the "id" governorate.
     *
     * @param id the id of the governorateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the governorateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/governorates/{id}")
    @Timed
    public ResponseEntity<GovernorateDTO> getGovernorate(@PathVariable Long id) {
        log.debug("REST request to get Governorate : {}", id);
        GovernorateDTO governorateDTO = governorateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(governorateDTO));
    }

    /**
     * GET  /governorates/delegation/:id : get the "id" delegation.
     *
     * @param id the id of the delegationDto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the governorateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/governorates/delegation/{id}")
    @Timed
    public ResponseEntity<GovernorateDTO> getGovernorateByDelegation(@PathVariable Long id) {
        log.debug("REST request to get Governorate : {}", id);
        GovernorateDTO governorateDTO = governorateService.findByDelegation(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(governorateDTO));
    }
    
    /**
     * GET  /governorate/region/:id : get all region governorates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of governorates in body
     */
    @GetMapping("/governorates/region/{id}")
    @Timed
    public List<GovernorateDTO> getAllRegionGovernorates(@PathVariable Long id) {
        log.debug("REST request to get all region governorates");
        return governorateService.findAllByRegion(id);
    }

    /**
     * DELETE  /governorates/:id : delete the "id" governorate.
     *
     * @param id the id of the governorateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/governorates/{id}")
    @Timed
    public ResponseEntity<Void> deleteGovernorate(@PathVariable Long id) {
        log.debug("REST request to delete Governorate : {}", id);
        governorateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/governorates?query=:query : search for the governorate corresponding
     * to the query.
     *
     * @param query the query of the governorate search
     * @return the result of the search
     */
    @GetMapping("/_search/governorates")
    @Timed
    public List<GovernorateDTO> searchGovernorates(@RequestParam String query) {
        log.debug("REST request to search Governorates for query {}", query);
        return governorateService.search(query);
    }

}
