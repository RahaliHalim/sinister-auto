package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PolicyReceiptStatusService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PolicyReceiptStatusDTO;
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
 * REST controller for managing PolicyReceiptStatus.
 */
@RestController
@RequestMapping("/api")
public class PolicyReceiptStatusResource {

    private final Logger log = LoggerFactory.getLogger(PolicyReceiptStatusResource.class);

    private static final String ENTITY_NAME = "policyReceiptStatus";

    private final PolicyReceiptStatusService policyReceiptStatusService;

    public PolicyReceiptStatusResource(PolicyReceiptStatusService policyReceiptStatusService) {
        this.policyReceiptStatusService = policyReceiptStatusService;
    }

    /**
     * POST  /policy-receipt-statuses : Create a new policyReceiptStatus.
     *
     * @param policyReceiptStatusDTO the policyReceiptStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policyReceiptStatusDTO, or with status 400 (Bad Request) if the policyReceiptStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policy-receipt-statuses")
    @Timed
    public ResponseEntity<PolicyReceiptStatusDTO> createPolicyReceiptStatus(@RequestBody PolicyReceiptStatusDTO policyReceiptStatusDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyReceiptStatus : {}", policyReceiptStatusDTO);
        if (policyReceiptStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new policyReceiptStatus cannot already have an ID")).body(null);
        }
        PolicyReceiptStatusDTO result = policyReceiptStatusService.save(policyReceiptStatusDTO);
        return ResponseEntity.created(new URI("/api/policy-receipt-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policy-receipt-statuses : Updates an existing policyReceiptStatus.
     *
     * @param policyReceiptStatusDTO the policyReceiptStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policyReceiptStatusDTO,
     * or with status 400 (Bad Request) if the policyReceiptStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the policyReceiptStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policy-receipt-statuses")
    @Timed
    public ResponseEntity<PolicyReceiptStatusDTO> updatePolicyReceiptStatus(@RequestBody PolicyReceiptStatusDTO policyReceiptStatusDTO) throws URISyntaxException {
        log.debug("REST request to update PolicyReceiptStatus : {}", policyReceiptStatusDTO);
        if (policyReceiptStatusDTO.getId() == null) {
            return createPolicyReceiptStatus(policyReceiptStatusDTO);
        }
        PolicyReceiptStatusDTO result = policyReceiptStatusService.save(policyReceiptStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policyReceiptStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policy-receipt-statuses : get all the policyReceiptStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policyReceiptStatuses in body
     */
    @GetMapping("/policy-receipt-statuses")
    @Timed
    public List<PolicyReceiptStatusDTO> getAllPolicyReceiptStatuses() {
        log.debug("REST request to get all PolicyReceiptStatuses");
        return policyReceiptStatusService.findAll();
    }

    /**
     * GET  /policy-receipt-statuses/:id : get the "id" policyReceiptStatus.
     *
     * @param id the id of the policyReceiptStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policyReceiptStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/policy-receipt-statuses/{id}")
    @Timed
    public ResponseEntity<PolicyReceiptStatusDTO> getPolicyReceiptStatus(@PathVariable Long id) {
        log.debug("REST request to get PolicyReceiptStatus : {}", id);
        PolicyReceiptStatusDTO policyReceiptStatusDTO = policyReceiptStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policyReceiptStatusDTO));
    }

    /**
     * DELETE  /policy-receipt-statuses/:id : delete the "id" policyReceiptStatus.
     *
     * @param id the id of the policyReceiptStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policy-receipt-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicyReceiptStatus(@PathVariable Long id) {
        log.debug("REST request to delete PolicyReceiptStatus : {}", id);
        policyReceiptStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/policy-receipt-statuses?query=:query : search for the policyReceiptStatus corresponding
     * to the query.
     *
     * @param query the query of the policyReceiptStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/policy-receipt-statuses")
    @Timed
    public List<PolicyReceiptStatusDTO> searchPolicyReceiptStatuses(@RequestParam String query) {
        log.debug("REST request to search PolicyReceiptStatuses for query {}", query);
        return policyReceiptStatusService.search(query);
    }

}
