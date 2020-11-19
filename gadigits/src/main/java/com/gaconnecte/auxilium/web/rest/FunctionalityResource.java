package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.FunctionalityService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.FunctionalityDTO;
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
 * REST controller for managing Functionality.
 */
@RestController
@RequestMapping("/api")
public class FunctionalityResource {

    private final Logger log = LoggerFactory.getLogger(FunctionalityResource.class);

    private static final String ENTITY_NAME = "functionality";

    private final FunctionalityService functionalityService;

    public FunctionalityResource(FunctionalityService functionalityService) {
        this.functionalityService = functionalityService;
    }

    /**
     * POST  /functionalities : Create a new functionality.
     *
     * @param functionalityDTO the functionalityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new functionalityDTO, or with status 400 (Bad Request) if the functionality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/functionalities")
    @Timed
    public ResponseEntity<FunctionalityDTO> createFunctionality(@RequestBody FunctionalityDTO functionalityDTO) throws URISyntaxException {
        log.debug("REST request to save Functionality : {}", functionalityDTO);
        if (functionalityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new functionality cannot already have an ID")).body(null);
        }
        FunctionalityDTO result = functionalityService.save(functionalityDTO);
        return ResponseEntity.created(new URI("/api/functionalities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /functionalities : Updates an existing functionality.
     *
     * @param functionalityDTO the functionalityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated functionalityDTO,
     * or with status 400 (Bad Request) if the functionalityDTO is not valid,
     * or with status 500 (Internal Server Error) if the functionalityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/functionalities")
    @Timed
    public ResponseEntity<FunctionalityDTO> updateFunctionality(@RequestBody FunctionalityDTO functionalityDTO) throws URISyntaxException {
        log.debug("REST request to update Functionality : {}", functionalityDTO);
        if (functionalityDTO.getId() == null) {
            return createFunctionality(functionalityDTO);
        }
        FunctionalityDTO result = functionalityService.save(functionalityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, functionalityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /functionalities : get all the functionalities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of functionalities in body
     */
    @GetMapping("/functionalities")
    @Timed
    public List<FunctionalityDTO> getAllFunctionalities() {
        log.debug("REST request to get all Functionalities");
        return functionalityService.findAll();
    }

    /**
     * GET  /functionalities/:id : get the "id" functionality.
     *
     * @param id the id of the functionalityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the functionalityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/functionalities/{id}")
    @Timed
    public ResponseEntity<FunctionalityDTO> getFunctionality(@PathVariable Long id) {
        log.debug("REST request to get Functionality : {}", id);
        FunctionalityDTO functionalityDTO = functionalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(functionalityDTO));
    }

    /**
     * DELETE  /functionalities/:id : delete the "id" functionality.
     *
     * @param id the id of the functionalityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/functionalities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFunctionality(@PathVariable Long id) {
        log.debug("REST request to delete Functionality : {}", id);
        functionalityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/functionalities?query=:query : search for the functionality corresponding
     * to the query.
     *
     * @param query the query of the functionality search
     * @return the result of the search
     */
    @GetMapping("/_search/functionalities")
    @Timed
    public List<FunctionalityDTO> searchFunctionalities(@RequestParam String query) {
        log.debug("REST request to search Functionalities for query {}", query);
        return functionalityService.search(query);
    }

}
