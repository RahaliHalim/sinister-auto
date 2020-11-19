package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PolicyHolderService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PolicyHolderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PolicyHolder.
 */
@RestController
@RequestMapping("/api")
public class PolicyHolderResource {

    private final Logger log = LoggerFactory.getLogger(PolicyHolderResource.class);

    private static final String ENTITY_NAME = "policyHolder";

    private final PolicyHolderService policyHolderService;

    public PolicyHolderResource(PolicyHolderService policyHolderService) {
        this.policyHolderService = policyHolderService;
    }

    /**
     * POST  /policy-holders : Create a new policyHolder.
     *
     * @param policyHolderDTO the policyHolderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policyHolderDTO, or with status 400 (Bad Request) if the policyHolder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policy-holders")
    @Timed
    public ResponseEntity<PolicyHolderDTO> createPolicyHolder(@Valid @RequestBody PolicyHolderDTO policyHolderDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyHolder : {}", policyHolderDTO);
        if (policyHolderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new policyHolder cannot already have an ID")).body(null);
        }
        PolicyHolderDTO result = policyHolderService.save(policyHolderDTO);
        return ResponseEntity.created(new URI("/api/policy-holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policy-holders : Updates an existing policyHolder.
     *
     * @param policyHolderDTO the policyHolderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policyHolderDTO,
     * or with status 400 (Bad Request) if the policyHolderDTO is not valid,
     * or with status 500 (Internal Server Error) if the policyHolderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policy-holders")
    @Timed
    public ResponseEntity<PolicyHolderDTO> updatePolicyHolder(@Valid @RequestBody PolicyHolderDTO policyHolderDTO) throws URISyntaxException {
        log.debug("REST request to update PolicyHolder : {}", policyHolderDTO);
        if (policyHolderDTO.getId() == null) {
            return createPolicyHolder(policyHolderDTO);
        }
        PolicyHolderDTO result = policyHolderService.save(policyHolderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policyHolderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policy-holders : get all the policyHolders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policyHolders in body
     */
    @GetMapping("/policy-holders")
    @Timed
    public List<PolicyHolderDTO> getAllPolicyHolders() {
        log.debug("REST request to get all PolicyHolders");
        return policyHolderService.findAll();
    }

    /**
     * GET  /policy-holders/:id : get the "id" policyHolder.
     *
     * @param id the id of the policyHolderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policyHolderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/policy-holders/{id}")
    @Timed
    public ResponseEntity<PolicyHolderDTO> getPolicyHolder(@PathVariable Long id) {
        log.debug("REST request to get PolicyHolder : {}", id);
        PolicyHolderDTO policyHolderDTO = policyHolderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policyHolderDTO));
    }

    /**
     * DELETE  /policy-holders/:id : delete the "id" policyHolder.
     *
     * @param id the id of the policyHolderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policy-holders/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicyHolder(@PathVariable Long id) {
        log.debug("REST request to delete PolicyHolder : {}", id);
        policyHolderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/policy-holders?query=:query : search for the policyHolder corresponding
     * to the query.
     *
     * @param query the query of the policyHolder search
     * @return the result of the search
     */
    @GetMapping("/_search/policy-holders")
    @Timed
    public List<PolicyHolderDTO> searchPolicyHolders(@RequestParam String query) {
        log.debug("REST request to search PolicyHolders for query {}", query);
        return policyHolderService.search(query);
    }

}
