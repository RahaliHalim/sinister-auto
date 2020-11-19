package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefTypeServiceService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import com.gaconnecte.auxilium.service.HistoryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing RefTypeService.
 */
@RestController
@RequestMapping("/api")
public class RefTypeServiceResource {

    private final Logger log = LoggerFactory.getLogger(RefTypeServiceResource.class);

    private static final String ENTITY_NAME = "refTypeService";

    private final RefTypeServiceService refTypeServiceService;
    private final HistoryService historyService;

    public RefTypeServiceResource(RefTypeServiceService refTypeServiceService, HistoryService historyService ) {
        this.refTypeServiceService = refTypeServiceService;
      this.historyService = historyService;
    }

    /**
     * POST  /ref-type-services : Create a new refTypeService.
     *
     * @param refTypeServiceDTO the refTypeServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refTypeServiceDTO, or with status 400 (Bad Request) if the refTypeService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-type-services")
    @Timed
    public ResponseEntity<RefTypeServiceDTO> createRefTypeService(@Valid @RequestBody RefTypeServiceDTO refTypeServiceDTO) throws URISyntaxException {
        log.debug("REST request to save RefTypeService : {}", refTypeServiceDTO);
        if (refTypeServiceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refTypeService cannot already have an ID")).body(null);
        }
        RefTypeServiceDTO result = refTypeServiceService.save(refTypeServiceDTO);
        //historyService.historysave("TYPES DE SERVICE",  result.getId(), "CREATION");
        return ResponseEntity.created(new URI("/api/ref-type-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-type-services : Updates an existing refTypeService.
     *
     * @param refTypeServiceDTO the refTypeServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refTypeServiceDTO,
     * or with status 400 (Bad Request) if the refTypeServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the refTypeServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-type-services")
    @Timed
    public ResponseEntity<RefTypeServiceDTO> updateRefTypeService(@Valid @RequestBody RefTypeServiceDTO refTypeServiceDTO) throws URISyntaxException {
        log.debug("REST request to update RefTypeService : {}", refTypeServiceDTO);
        if (refTypeServiceDTO.getId() == null) {
            return createRefTypeService(refTypeServiceDTO);
        }
        RefTypeServiceDTO result = refTypeServiceService.save(refTypeServiceDTO);
       // historyService.historysave("TYPES DE SERVICE",  result.getId(), "UPDATE");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refTypeServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-type-services : get all the refTypeServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTypeServices in body
     */
    @GetMapping("/ref-type-services")
    @Timed
    public ResponseEntity<Set<RefTypeServiceDTO>> getAllRefTypeServices() {
        log.debug("REST request to get a page of RefTypeServices");
        Set<RefTypeServiceDTO> page = refTypeServiceService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /ref-type-services/active : get all the refTypeServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTypeServices in body
     */
    @GetMapping("/ref-type-services/active")
    @Timed
    public ResponseEntity<Set<RefTypeServiceDTO>> getAllActiveServiceTypes() {
        log.debug("REST request to get a page of RefTypeServices");
        Set<RefTypeServiceDTO> page = refTypeServiceService.findAllActive();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    
    /**
     * GET  /ref-type-services/:id : get the "id" refTypeService.
     *
     * @param id the id of the refTypeServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTypeServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-type-services/{id}")
    @Timed
    public ResponseEntity<RefTypeServiceDTO> getRefTypeService(@PathVariable Long id) {
        log.debug("REST request to get RefTypeService : {}", id);
        RefTypeServiceDTO refTypeServiceDTO = refTypeServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTypeServiceDTO));
    }

    /**
     * DELETE  /ref-type-services/:id : delete the "id" refTypeService.
     *
     * @param id the id of the refTypeServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-type-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefTypeService(@PathVariable Long id) {
        log.debug("REST request to delete RefTypeService : {}", id);
        refTypeServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-type-services?query=:query : search for the refTypeService corresponding
     * to the query.
     *
     * @param query the query of the refTypeService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-type-services")
    @Timed
    public ResponseEntity<List<RefTypeServiceDTO>> searchRefTypeServices(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefTypeServices for query {}", query);
        Page<RefTypeServiceDTO> page = refTypeServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-type-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
