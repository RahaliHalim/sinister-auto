package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.BusinessEntityService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.BusinessEntityDTO;
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
 * REST controller for managing BusinessEntity.
 */
@RestController
@RequestMapping("/api")
public class BusinessEntityResource {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityResource.class);

    private static final String ENTITY_NAME = "businessEntity";

    private final BusinessEntityService businessEntityService;

    public BusinessEntityResource(BusinessEntityService businessEntityService) {
        this.businessEntityService = businessEntityService;
    }

    /**
     * POST  /business-entities : Create a new businessEntity.
     *
     * @param businessEntityDTO the businessEntityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessEntityDTO, or with status 400 (Bad Request) if the businessEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-entities")
    @Timed
    public ResponseEntity<BusinessEntityDTO> createBusinessEntity(@RequestBody BusinessEntityDTO businessEntityDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessEntity : {}", businessEntityDTO);
        if (businessEntityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new businessEntity cannot already have an ID")).body(null);
        }
        BusinessEntityDTO result = businessEntityService.save(businessEntityDTO);
        return ResponseEntity.created(new URI("/api/business-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-entities : Updates an existing businessEntity.
     *
     * @param businessEntityDTO the businessEntityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessEntityDTO,
     * or with status 400 (Bad Request) if the businessEntityDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessEntityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-entities")
    @Timed
    public ResponseEntity<BusinessEntityDTO> updateBusinessEntity(@RequestBody BusinessEntityDTO businessEntityDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessEntity : {}", businessEntityDTO);
        if (businessEntityDTO.getId() == null) {
            return createBusinessEntity(businessEntityDTO);
        }
        BusinessEntityDTO result = businessEntityService.save(businessEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-entities : get all the businessEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businessEntities in body
     */
    @GetMapping("/business-entities")
    @Timed
    public List<BusinessEntityDTO> getAllBusinessEntities() {
        log.debug("REST request to get all BusinessEntities");
        return businessEntityService.findAll();
    }

    /**
     * GET  /business-entities/:id : get the "id" businessEntity.
     *
     * @param id the id of the businessEntityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessEntityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-entities/{id}")
    @Timed
    public ResponseEntity<BusinessEntityDTO> getBusinessEntity(@PathVariable Long id) {
        log.debug("REST request to get BusinessEntity : {}", id);
        BusinessEntityDTO businessEntityDTO = businessEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(businessEntityDTO));
    }

    /**
     * DELETE  /business-entities/:id : delete the "id" businessEntity.
     *
     * @param id the id of the businessEntityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessEntity(@PathVariable Long id) {
        log.debug("REST request to delete BusinessEntity : {}", id);
        businessEntityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/business-entities?query=:query : search for the businessEntity corresponding
     * to the query.
     *
     * @param query the query of the businessEntity search
     * @return the result of the search
     */
    @GetMapping("/_search/business-entities")
    @Timed
    public List<BusinessEntityDTO> searchBusinessEntities(@RequestParam String query) {
        log.debug("REST request to search BusinessEntities for query {}", query);
        return businessEntityService.search(query);
    }

}
