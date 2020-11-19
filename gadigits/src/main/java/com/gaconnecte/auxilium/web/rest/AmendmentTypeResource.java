package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.AmendmentTypeService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.AmendmentTypeDTO;
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
 * REST controller for managing AmendmentType.
 */
@RestController
@RequestMapping("/api")
public class AmendmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(AmendmentTypeResource.class);

    private static final String ENTITY_NAME = "amendmentType";

    private final AmendmentTypeService amendmentTypeService;

    public AmendmentTypeResource(AmendmentTypeService amendmentTypeService) {
        this.amendmentTypeService = amendmentTypeService;
    }

    /**
     * POST  /amendment-types : Create a new amendmentType.
     *
     * @param amendmentTypeDTO the amendmentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amendmentTypeDTO, or with status 400 (Bad Request) if the amendmentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amendment-types")
    @Timed
    public ResponseEntity<AmendmentTypeDTO> createAmendmentType(@RequestBody AmendmentTypeDTO amendmentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AmendmentType : {}", amendmentTypeDTO);
        if (amendmentTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new amendmentType cannot already have an ID")).body(null);
        }
        AmendmentTypeDTO result = amendmentTypeService.save(amendmentTypeDTO);
        return ResponseEntity.created(new URI("/api/amendment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amendment-types : Updates an existing amendmentType.
     *
     * @param amendmentTypeDTO the amendmentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amendmentTypeDTO,
     * or with status 400 (Bad Request) if the amendmentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the amendmentTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amendment-types")
    @Timed
    public ResponseEntity<AmendmentTypeDTO> updateAmendmentType(@RequestBody AmendmentTypeDTO amendmentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AmendmentType : {}", amendmentTypeDTO);
        if (amendmentTypeDTO.getId() == null) {
            return createAmendmentType(amendmentTypeDTO);
        }
        AmendmentTypeDTO result = amendmentTypeService.save(amendmentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amendmentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amendment-types : get all the amendmentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of amendmentTypes in body
     */
    @GetMapping("/amendment-types")
    @Timed
    public List<AmendmentTypeDTO> getAllAmendmentTypes() {
        log.debug("REST request to get all AmendmentTypes");
        return amendmentTypeService.findAll();
    }

    /**
     * GET  /amendment-types/:id : get the "id" amendmentType.
     *
     * @param id the id of the amendmentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amendmentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amendment-types/{id}")
    @Timed
    public ResponseEntity<AmendmentTypeDTO> getAmendmentType(@PathVariable Long id) {
        log.debug("REST request to get AmendmentType : {}", id);
        AmendmentTypeDTO amendmentTypeDTO = amendmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(amendmentTypeDTO));
    }

    /**
     * DELETE  /amendment-types/:id : delete the "id" amendmentType.
     *
     * @param id the id of the amendmentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amendment-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmendmentType(@PathVariable Long id) {
        log.debug("REST request to delete AmendmentType : {}", id);
        amendmentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/amendment-types?query=:query : search for the amendmentType corresponding
     * to the query.
     *
     * @param query the query of the amendmentType search
     * @return the result of the search
     */
    @GetMapping("/_search/amendment-types")
    @Timed
    public List<AmendmentTypeDTO> searchAmendmentTypes(@RequestParam String query) {
        log.debug("REST request to search AmendmentTypes for query {}", query);
        return amendmentTypeService.search(query);
    }

}
