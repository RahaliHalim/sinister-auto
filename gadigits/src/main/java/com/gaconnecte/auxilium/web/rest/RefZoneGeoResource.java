package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefZoneGeoService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefZoneGeoDTO;
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
 * REST controller for managing RefZoneGeo.
 */
@RestController
@RequestMapping("/api")
public class RefZoneGeoResource {

    private final Logger log = LoggerFactory.getLogger(RefZoneGeoResource.class);

    private static final String ENTITY_NAME = "refZoneGeo";

    private final RefZoneGeoService refZoneGeoService;

    public RefZoneGeoResource(RefZoneGeoService refZoneGeoService) {
        this.refZoneGeoService = refZoneGeoService;
    }

    /**
     * POST  /ref-zone-geos : Create a new refZoneGeo.
     *
     * @param refZoneGeoDTO the refZoneGeoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refZoneGeoDTO, or with status 400 (Bad Request) if the refZoneGeo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-zone-geos")
    @Timed
    public ResponseEntity<RefZoneGeoDTO> createRefZoneGeo(@Valid @RequestBody RefZoneGeoDTO refZoneGeoDTO) throws URISyntaxException {
        log.debug("REST request to save RefZoneGeo : {}", refZoneGeoDTO);
        if (refZoneGeoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refZoneGeo cannot already have an ID")).body(null);
        }
        RefZoneGeoDTO result = refZoneGeoService.save(refZoneGeoDTO);
        return ResponseEntity.created(new URI("/api/ref-zone-geos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-zone-geos : Updates an existing refZoneGeo.
     *
     * @param refZoneGeoDTO the refZoneGeoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refZoneGeoDTO,
     * or with status 400 (Bad Request) if the refZoneGeoDTO is not valid,
     * or with status 500 (Internal Server Error) if the refZoneGeoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-zone-geos")
    @Timed
    public ResponseEntity<RefZoneGeoDTO> updateRefZoneGeo(@Valid @RequestBody RefZoneGeoDTO refZoneGeoDTO) throws URISyntaxException {
        log.debug("REST request to update RefZoneGeo : {}", refZoneGeoDTO);
        if (refZoneGeoDTO.getId() == null) {
            return createRefZoneGeo(refZoneGeoDTO);
        }
        RefZoneGeoDTO result = refZoneGeoService.save(refZoneGeoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refZoneGeoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-zone-geos : get all the refZoneGeos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refZoneGeos in body
     */
    @GetMapping("/ref-zone-geos")
    @Timed
    public ResponseEntity<List<RefZoneGeoDTO>> getAllRefZoneGeos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefZoneGeos");
        Page<RefZoneGeoDTO> page = refZoneGeoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-zone-geos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-zone-geos/:id : get the "id" refZoneGeo.
     *
     * @param id the id of the refZoneGeoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refZoneGeoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-zone-geos/{id}")
    @Timed
    public ResponseEntity<RefZoneGeoDTO> getRefZoneGeo(@PathVariable Long id) {
        log.debug("REST request to get RefZoneGeo : {}", id);
        RefZoneGeoDTO refZoneGeoDTO = refZoneGeoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refZoneGeoDTO));
    }

    /**
     * DELETE  /ref-zone-geos/:id : delete the "id" refZoneGeo.
     *
     * @param id the id of the refZoneGeoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-zone-geos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefZoneGeo(@PathVariable Long id) {
        log.debug("REST request to delete RefZoneGeo : {}", id);
        refZoneGeoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-zone-geos?query=:query : search for the refZoneGeo corresponding
     * to the query.
     *
     * @param query the query of the refZoneGeo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-zone-geos")
    @Timed
    public ResponseEntity<List<RefZoneGeoDTO>> searchRefZoneGeos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefZoneGeos for query {}", query);
        Page<RefZoneGeoDTO> page = refZoneGeoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-zone-geos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
