package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefPositionGaService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefPositionGaDTO;
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
 * REST controller for managing RefPositionGa.
 */
@RestController
@RequestMapping("/api")
public class RefPositionGaResource {

    private final Logger log = LoggerFactory.getLogger(RefPositionGaResource.class);

    private static final String ENTITY_NAME = "refPositionGa";

    private final RefPositionGaService refPositionGaService;

    public RefPositionGaResource(RefPositionGaService refPositionGaService) {
        this.refPositionGaService = refPositionGaService;
    }

    /**
     * POST  /ref-position-gas : Create a new refPositionGa.
     *
     * @param refPositionGaDTO the refPositionGaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refPositionGaDTO, or with status 400 (Bad Request) if the refPositionGa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-position-gas")
    @Timed
    public ResponseEntity<RefPositionGaDTO> createRefPositionGa(@Valid @RequestBody RefPositionGaDTO refPositionGaDTO) throws URISyntaxException {
        log.debug("REST request to save RefPositionGa : {}", refPositionGaDTO);
        if (refPositionGaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refPositionGa cannot already have an ID")).body(null);
        }
        RefPositionGaDTO result = refPositionGaService.save(refPositionGaDTO);
        return ResponseEntity.created(new URI("/api/ref-position-gas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-position-gas : Updates an existing refPositionGa.
     *
     * @param refPositionGaDTO the refPositionGaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refPositionGaDTO,
     * or with status 400 (Bad Request) if the refPositionGaDTO is not valid,
     * or with status 500 (Internal Server Error) if the refPositionGaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-position-gas")
    @Timed
    public ResponseEntity<RefPositionGaDTO> updateRefPositionGa(@Valid @RequestBody RefPositionGaDTO refPositionGaDTO) throws URISyntaxException {
        log.debug("REST request to update RefPositionGa : {}", refPositionGaDTO);
        if (refPositionGaDTO.getId() == null) {
            return createRefPositionGa(refPositionGaDTO);
        }
        RefPositionGaDTO result = refPositionGaService.save(refPositionGaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refPositionGaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-position-gas : get all the refPositionGas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refPositionGas in body
     */
    @GetMapping("/ref-position-gas")
    @Timed
    public ResponseEntity<List<RefPositionGaDTO>> getAllRefPositionGas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefPositionGas");
        Page<RefPositionGaDTO> page = refPositionGaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-position-gas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-position-gas/:id : get the "id" refPositionGa.
     *
     * @param id the id of the refPositionGaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refPositionGaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-position-gas/{id}")
    @Timed
    public ResponseEntity<RefPositionGaDTO> getRefPositionGa(@PathVariable Long id) {
        log.debug("REST request to get RefPositionGa : {}", id);
        RefPositionGaDTO refPositionGaDTO = refPositionGaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refPositionGaDTO));
    }

    /**
     * DELETE  /ref-position-gas/:id : delete the "id" refPositionGa.
     *
     * @param id the id of the refPositionGaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-position-gas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefPositionGa(@PathVariable Long id) {
        log.debug("REST request to delete RefPositionGa : {}", id);
        refPositionGaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-position-gas?query=:query : search for the refPositionGa corresponding
     * to the query.
     *
     * @param query the query of the refPositionGa search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-position-gas")
    @Timed
    public ResponseEntity<List<RefPositionGaDTO>> searchRefPositionGas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefPositionGas for query {}", query);
        Page<RefPositionGaDTO> page = refPositionGaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-position-gas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
