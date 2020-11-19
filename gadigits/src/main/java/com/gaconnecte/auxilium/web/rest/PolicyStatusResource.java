package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PolicyStatusService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PolicyStatusDTO;
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
 * REST controller for managing PolicyStatus.
 */
@RestController
@RequestMapping("/api")
public class PolicyStatusResource {

    private final Logger log = LoggerFactory.getLogger(PolicyStatusResource.class);

    private static final String ENTITY_NAME = "policyStatus";

    private final PolicyStatusService policyStatusService;

    public PolicyStatusResource(PolicyStatusService policyStatusService) {
        this.policyStatusService = policyStatusService;
    }

    /**
     * POST  /policy-statuses : Create a new policyStatus.
     *
     * @param policyStatusDTO the policyStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policyStatusDTO, or with status 400 (Bad Request) if the policyStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policy-statuses")
    @Timed
    public ResponseEntity<PolicyStatusDTO> createPolicyStatus(@RequestBody PolicyStatusDTO policyStatusDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyStatus : {}", policyStatusDTO);
        if (policyStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new policyStatus cannot already have an ID")).body(null);
        }
        PolicyStatusDTO result = policyStatusService.save(policyStatusDTO);
        return ResponseEntity.created(new URI("/api/policy-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policy-statuses : Updates an existing policyStatus.
     *
     * @param policyStatusDTO the policyStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policyStatusDTO,
     * or with status 400 (Bad Request) if the policyStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the policyStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policy-statuses")
    @Timed
    public ResponseEntity<PolicyStatusDTO> updatePolicyStatus(@RequestBody PolicyStatusDTO policyStatusDTO) throws URISyntaxException {
        log.debug("REST request to update PolicyStatus : {}", policyStatusDTO);
        if (policyStatusDTO.getId() == null) {
            return createPolicyStatus(policyStatusDTO);
        }
        PolicyStatusDTO result = policyStatusService.save(policyStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policyStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policy-statuses : get all the policyStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policyStatuses in body
     */
    @GetMapping("/policy-statuses")
    @Timed
    public List<PolicyStatusDTO> getAllPolicyStatuses() {
        log.debug("REST request to get all PolicyStatuses");
        return policyStatusService.findAll();
    }

    /**
     * GET  /policy-statuses/:id : get the "id" policyStatus.
     *
     * @param id the id of the policyStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policyStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/policy-statuses/{id}")
    @Timed
    public ResponseEntity<PolicyStatusDTO> getPolicyStatus(@PathVariable Long id) {
        log.debug("REST request to get PolicyStatus : {}", id);
        PolicyStatusDTO policyStatusDTO = policyStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policyStatusDTO));
    }

    /**
     * DELETE  /policy-statuses/:id : delete the "id" policyStatus.
     *
     * @param id the id of the policyStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policy-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicyStatus(@PathVariable Long id) {
        log.debug("REST request to delete PolicyStatus : {}", id);
        policyStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/policy-statuses?query=:query : search for the policyStatus corresponding
     * to the query.
     *
     * @param query the query of the policyStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/policy-statuses")
    @Timed
    public List<PolicyStatusDTO> searchPolicyStatuses(@RequestParam String query) {
        log.debug("REST request to search PolicyStatuses for query {}", query);
        return policyStatusService.search(query);
    }

}
