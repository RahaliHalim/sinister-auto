package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PolicyService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PolicyDTO;
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
 * REST controller for managing Policy.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);

    private static final String ENTITY_NAME = "policy";

    private final PolicyService policyService;

    public PolicyResource(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * POST  /policies : Create a new policy.
     *
     * @param policyDTO the policyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policyDTO, or with status 400 (Bad Request) if the policy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policies")
    @Timed
    public ResponseEntity<PolicyDTO> createPolicy(@RequestBody PolicyDTO policyDTO) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policyDTO);
        if (policyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new policy cannot already have an ID")).body(null);
        }
        PolicyDTO result = policyService.save(policyDTO);
        return ResponseEntity.created(new URI("/api/policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policies : Updates an existing policy.
     *
     * @param policyDTO the policyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policyDTO,
     * or with status 400 (Bad Request) if the policyDTO is not valid,
     * or with status 500 (Internal Server Error) if the policyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policies")
    @Timed
    public ResponseEntity<PolicyDTO> updatePolicy(@RequestBody PolicyDTO policyDTO) throws URISyntaxException {
        log.debug("REST request to update Policy : {}", policyDTO);
        if (policyDTO.getId() == null) {
            return createPolicy(policyDTO);
        }
        PolicyDTO result = policyService.save(policyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policies : get all the policies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policies in body
     */
    @GetMapping("/policies")
    @Timed
    public List<PolicyDTO> getAllPolicies() {
        log.debug("REST request to get all Policies");
        return policyService.findAll();
    }

    /**
     * GET  /policies/:id : get the "id" policy.
     *
     * @param id the id of the policyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/policies/{id}")
    @Timed
    public ResponseEntity<PolicyDTO> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        PolicyDTO policyDTO = policyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policyDTO));
    }

    /**
     * DELETE  /policies/:id : delete the "id" policy.
     *
     * @param id the id of the policyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policies/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/policies?query=:query : search for the policy corresponding
     * to the query.
     *
     * @param query the query of the policy search
     * @return the result of the search
     */
    @GetMapping("/_search/policies")
    @Timed
    public List<PolicyDTO> searchPolicies(@RequestParam String query) {
        log.debug("REST request to search Policies for query {}", query);
        return policyService.search(query);
    }

}
