package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ServiceAssuranceService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.ServiceAssuranceDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ServiceAssurance.
 */
@RestController
@RequestMapping("/api")
public class ServiceAssuranceResource {

    private final Logger log = LoggerFactory.getLogger(ServiceAssuranceResource.class);

    private static final String ENTITY_NAME = "serviceAssurance";

    private final ServiceAssuranceService serviceAssuranceService;

    public ServiceAssuranceResource(ServiceAssuranceService serviceAssuranceService) {
        this.serviceAssuranceService = serviceAssuranceService;
    }

    /**
     * POST  /service-assurances : Create a new serviceAssurance.
     *
     * @param serviceAssuranceDTO the serviceAssuranceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceAssuranceDTO, or with status 400 (Bad Request) if the serviceAssurance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-assurances")
    @Timed
    public ResponseEntity<ServiceAssuranceDTO> createServiceAssurance(@Valid @RequestBody ServiceAssuranceDTO serviceAssuranceDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceAssurance : {}", serviceAssuranceDTO);
        if (serviceAssuranceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviceAssurance cannot already have an ID")).body(null);
        }
        ServiceAssuranceDTO result = serviceAssuranceService.save(serviceAssuranceDTO);
        return ResponseEntity.created(new URI("/api/service-assurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-assurances : Updates an existing serviceAssurance.
     *
     * @param serviceAssuranceDTO the serviceAssuranceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceAssuranceDTO,
     * or with status 400 (Bad Request) if the serviceAssuranceDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceAssuranceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-assurances")
    @Timed
    public ResponseEntity<ServiceAssuranceDTO> updateServiceAssurance(@Valid @RequestBody ServiceAssuranceDTO serviceAssuranceDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceAssurance : {}", serviceAssuranceDTO);
        if (serviceAssuranceDTO.getId() == null) {
            return createServiceAssurance(serviceAssuranceDTO);
        }
        ServiceAssuranceDTO result = serviceAssuranceService.save(serviceAssuranceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceAssuranceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-assurances : get all the serviceAssurances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceAssurances in body
     */
    @GetMapping("/service-assurances")
    @Timed
    public ResponseEntity<List<ServiceAssuranceDTO>> getAllServiceAssurances(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ServiceAssurances");
        Page<ServiceAssuranceDTO> page = serviceAssuranceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-assurances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /service-assurances/:id : get the "id" serviceAssurance.
     *
     * @param id the id of the serviceAssuranceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceAssuranceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-assurances/{id}")
    @Timed
    public ResponseEntity<ServiceAssuranceDTO> getServiceAssurance(@PathVariable Long id) {
        log.debug("REST request to get ServiceAssurance : {}", id);
        ServiceAssuranceDTO serviceAssuranceDTO = serviceAssuranceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceAssuranceDTO));
    }

    /**
     * DELETE  /service-assurances/:id : delete the "id" serviceAssurance.
     *
     * @param id the id of the serviceAssuranceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-assurances/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceAssurance(@PathVariable Long id) {
        log.debug("REST request to delete ServiceAssurance : {}", id);
        serviceAssuranceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-assurances?query=:query : search for the serviceAssurance corresponding
     * to the query.
     *
     * @param query the query of the serviceAssurance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/service-assurances")
    @Timed
    public ResponseEntity<List<ServiceAssuranceDTO>> searchServiceAssurances(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ServiceAssurances for query {}", query);
        Page<ServiceAssuranceDTO> page = serviceAssuranceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/service-assurances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
