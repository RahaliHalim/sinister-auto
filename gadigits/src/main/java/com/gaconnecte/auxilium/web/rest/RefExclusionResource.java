package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefExclusionService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefExclusionDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RefModeGestion.
 */
@RestController
@RequestMapping("/api")
public class RefExclusionResource {

    private final Logger log = LoggerFactory.getLogger(RefExclusionResource.class);

    private static final String ENTITY_NAME = "refExclusion";

    private final RefExclusionService refExclusionService;

    public RefExclusionResource(RefExclusionService refExclusionService) {
        this.refExclusionService = refExclusionService;
    }

    /**
     * POST  /ref-mode-gestions : Create a new refModeGestion.
     *
     * @param refModeGestionDTO the refModeGestionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refModeGestionDTO, or with status 400 (Bad Request) if the refModeGestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
   

    /**
     * PUT  /ref-mode-gestions : Updates an existing refModeGestion.
     *
     * @param refModeGestionDTO the refModeGestionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refModeGestionDTO,
     * or with status 400 (Bad Request) if the refModeGestionDTO is not valid,
     * or with status 500 (Internal Server Error) if the refModeGestionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
   

    /**
     * GET  /ref-mode-gestions : get all the refModeGestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refModeGestions in body
     */
    @GetMapping("/ref-mode-Exclusions")
    @Timed
    public ResponseEntity<List<RefExclusionDTO>> getAllRefExclusions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of getAllRefExclusions");
        Page<RefExclusionDTO> page = refExclusionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-exclusions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-mode-gestions/:id : get the "id" refModeGestion.
     *
     * @param id the id of the refModeGestionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refModeGestionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-Exclusions/{id}")
    @Timed
    public ResponseEntity<RefExclusionDTO> getAllRefExclusion(@PathVariable Long id) {
        log.debug("REST request to get getAllRefExclusion : {}", id);
        RefExclusionDTO refExclusionDTO = refExclusionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refExclusionDTO));
    }

    /**
     * DELETE  /ref-mode-gestions/:id : delete the "id" refModeGestion.
     *
     * @param id the id of the refModeGestionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
   


}
