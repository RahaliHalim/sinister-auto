package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PolicyTypeService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PolicyTypeDTO;
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
 * REST controller for managing PolicyType.
 */
@RestController
@RequestMapping("/api")
public class PolicyTypeResource {

    private final Logger log = LoggerFactory.getLogger(PolicyTypeResource.class);

    private static final String ENTITY_NAME = "policyType";

    private final PolicyTypeService policyTypeService;

    public PolicyTypeResource(PolicyTypeService policyTypeService) {
        this.policyTypeService = policyTypeService;
    }

    /**
     * POST  /policy-types : Create a new policyType.
     *
     * @param policyTypeDTO the policyTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policyTypeDTO, or with status 400 (Bad Request) if the policyType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policy-types")
    @Timed
    public ResponseEntity<PolicyTypeDTO> createPolicyType(@RequestBody PolicyTypeDTO policyTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyType : {}", policyTypeDTO);
        if (policyTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new policyType cannot already have an ID")).body(null);
        }
        PolicyTypeDTO result = policyTypeService.save(policyTypeDTO);
        return ResponseEntity.created(new URI("/api/policy-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policy-types : Updates an existing policyType.
     *
     * @param policyTypeDTO the policyTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policyTypeDTO,
     * or with status 400 (Bad Request) if the policyTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the policyTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policy-types")
    @Timed
    public ResponseEntity<PolicyTypeDTO> updatePolicyType(@RequestBody PolicyTypeDTO policyTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PolicyType : {}", policyTypeDTO);
        if (policyTypeDTO.getId() == null) {
            return createPolicyType(policyTypeDTO);
        }
        PolicyTypeDTO result = policyTypeService.save(policyTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policyTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policy-types : get all the policyTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policyTypes in body
     */
    @GetMapping("/policy-types")
    @Timed
    public List<PolicyTypeDTO> getAllPolicyTypes() {
        log.debug("REST request to get all PolicyTypes");
        return policyTypeService.findAll();
    }

    /**
     * GET  /policy-types/:id : get the "id" policyType.
     *
     * @param id the id of the policyTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policyTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/policy-types/{id}")
    @Timed
    public ResponseEntity<PolicyTypeDTO> getPolicyType(@PathVariable Long id) {
        log.debug("REST request to get PolicyType : {}", id);
        PolicyTypeDTO policyTypeDTO = policyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policyTypeDTO));
    }

    /**
     * DELETE  /policy-types/:id : delete the "id" policyType.
     *
     * @param id the id of the policyTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policy-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicyType(@PathVariable Long id) {
        log.debug("REST request to delete PolicyType : {}", id);
        policyTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/policy-types?query=:query : search for the policyType corresponding
     * to the query.
     *
     * @param query the query of the policyType search
     * @return the result of the search
     */
    @GetMapping("/_search/policy-types")
    @Timed
    public List<PolicyTypeDTO> searchPolicyTypes(@RequestParam String query) {
        log.debug("REST request to search PolicyTypes for query {}", query);
        return policyTypeService.search(query);
    }

}
