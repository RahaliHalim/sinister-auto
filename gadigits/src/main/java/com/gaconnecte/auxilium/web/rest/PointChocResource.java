package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.PointChocService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.PointChocDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PointChoc.
 */
@RestController
@RequestMapping("/api")
public class PointChocResource {

    private final Logger log = LoggerFactory.getLogger(PointChocResource.class);

    private static final String ENTITY_NAME = "pointChoc";

    private final PointChocService pointChocService;

    public PointChocResource(PointChocService pointChocService) {
        this.pointChocService = pointChocService;
    }

    /**
     * POST  /point-chocs : Create a new pointChoc.
     *
     * @param pointChocDTO the pointChocDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pointChocDTO, or with status 400 (Bad Request) if the pointChoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/point-chocs")
    @Timed
    public ResponseEntity<PointChocDTO> createPointChoc(@RequestBody PointChocDTO pointChocDTO) throws URISyntaxException {
        log.debug("REST request to save PointChoc : {}", pointChocDTO);
        if (pointChocDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pointChoc cannot already have an ID")).body(null);
        }
        PointChocDTO result = pointChocService.save(pointChocDTO);
        return ResponseEntity.created(new URI("/api/point-chocs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /point-chocs : Updates an existing pointChoc.
     *
     * @param pointChocDTO the pointChocDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pointChocDTO,
     * or with status 400 (Bad Request) if the pointChocDTO is not valid,
     * or with status 500 (Internal Server Error) if the pointChocDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/point-chocs")
    @Timed
    public ResponseEntity<PointChocDTO> updatePointChoc(@RequestBody PointChocDTO pointChocDTO) throws URISyntaxException {
        log.debug("REST request to update PointChoc : {}", pointChocDTO);
        if (pointChocDTO.getId() == null) {
            return createPointChoc(pointChocDTO);
        }
        PointChocDTO result = pointChocService.save(pointChocDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pointChocDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /point-chocs : get all the pointChocs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pointChocs in body
     */
    @GetMapping("/point-chocs")
    @Timed
    public ResponseEntity<List<PointChocDTO>> getAllPointChocs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PointChocs");
        Page<PointChocDTO> page = pointChocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/point-chocs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /point-chocs/:id : get the "id" pointChoc.
     *
     * @param id the id of the pointChocDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pointChocDTO, or with status 404 (Not Found)
     */
    @GetMapping("/point-chocs/{id}")
    @Timed
    public ResponseEntity<PointChocDTO> getPointChoc(@PathVariable Long id) {
        log.debug("REST request to get PointChoc : {}", id);
        PointChocDTO pointChocDTO = pointChocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pointChocDTO));
    }

    /**
     * DELETE  /point-chocs/:id : delete the "id" pointChoc.
     *
     * @param id the id of the pointChocDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/point-chocs/{id}")
    @Timed
    public ResponseEntity<Void> deletePointChoc(@PathVariable Long id) {
        log.debug("REST request to delete PointChoc : {}", id);
        pointChocService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/point-chocs?query=:query : search for the pointChoc corresponding
     * to the query.
     *
     * @param query the query of the pointChoc search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/point-chocs")
    @Timed
    public ResponseEntity<List<PointChocDTO>> searchPointChocs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PointChocs for query {}", query);
        Page<PointChocDTO> page = pointChocService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/point-chocs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
