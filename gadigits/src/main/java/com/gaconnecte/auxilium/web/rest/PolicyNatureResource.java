package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PolicyNatureService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.PolicyNatureDTO;
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
 * REST controller for managing PolicyNature.
 */
@RestController
@RequestMapping("/api")
public class PolicyNatureResource {

    private final Logger log = LoggerFactory.getLogger(PolicyNatureResource.class);

    private static final String ENTITY_NAME = "policyNature";

    private final PolicyNatureService policyNatureService;

    public PolicyNatureResource(PolicyNatureService policyNatureService) {
        this.policyNatureService = policyNatureService;
    }

    /**
     * POST  /policy-natures : Create a new policyNature.
     *
     * @param policyNatureDTO the policyNatureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policyNatureDTO, or with status 400 (Bad Request) if the policyNature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policy-natures")
    @Timed
    public ResponseEntity<PolicyNatureDTO> createPolicyNature(@RequestBody PolicyNatureDTO policyNatureDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyNature : {}", policyNatureDTO);
        if (policyNatureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new policyNature cannot already have an ID")).body(null);
        }
        PolicyNatureDTO result = policyNatureService.save(policyNatureDTO);
        return ResponseEntity.created(new URI("/api/policy-natures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policy-natures : Updates an existing policyNature.
     *
     * @param policyNatureDTO the policyNatureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policyNatureDTO,
     * or with status 400 (Bad Request) if the policyNatureDTO is not valid,
     * or with status 500 (Internal Server Error) if the policyNatureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policy-natures")
    @Timed
    public ResponseEntity<PolicyNatureDTO> updatePolicyNature(@RequestBody PolicyNatureDTO policyNatureDTO) throws URISyntaxException {
        log.debug("REST request to update PolicyNature : {}", policyNatureDTO);
        if (policyNatureDTO.getId() == null) {
            return createPolicyNature(policyNatureDTO);
        }
        PolicyNatureDTO result = policyNatureService.save(policyNatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policyNatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policy-natures : get all the policyNatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policyNatures in body
     */
    @GetMapping("/policy-natures")
    @Timed
    public List<PolicyNatureDTO> getAllPolicyNatures() {
        log.debug("REST request to get all PolicyNatures");
        return policyNatureService.findAll();
    }

    /**
     * GET  /policy-natures/:id : get the "id" policyNature.
     *
     * @param id the id of the policyNatureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policyNatureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/policy-natures/{id}")
    @Timed
    public ResponseEntity<PolicyNatureDTO> getPolicyNature(@PathVariable Long id) {
        log.debug("REST request to get PolicyNature : {}", id);
        PolicyNatureDTO policyNatureDTO = policyNatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policyNatureDTO));
    }

    /**
     * DELETE  /policy-natures/:id : delete the "id" policyNature.
     *
     * @param id the id of the policyNatureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policy-natures/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicyNature(@PathVariable Long id) {
        log.debug("REST request to delete PolicyNature : {}", id);
        policyNatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/policy-natures?query=:query : search for the policyNature corresponding
     * to the query.
     *
     * @param query the query of the policyNature search
     * @return the result of the search
     */
    @GetMapping("/_search/policy-natures")
    @Timed
    public List<PolicyNatureDTO> searchPolicyNatures(@RequestParam String query) {
        log.debug("REST request to search PolicyNatures for query {}", query);
        return policyNatureService.search(query);
    }

}
