package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DetailsMoService;
import com.gaconnecte.auxilium.service.dto.DetailsMoDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing DetailsMo.
 */
@RestController
@RequestMapping("/api")
public class DetailsMoResource {

    private final Logger log = LoggerFactory.getLogger(DetailsMoResource.class);

    private static final String ENTITY_NAME = "detailsMo";

    private final DetailsMoService detailsMoService;

    public DetailsMoResource(DetailsMoService detailsMoService) {
        this.detailsMoService = detailsMoService;
    }

    /**
     * POST  /details-mos : Create a new detailsMo.
     *
     * @param detailsMoDTO the detailsMoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailsMoDTO, or with status 400 (Bad Request) if the detailsMo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/details-mos")
    @Timed
    public ResponseEntity<DetailsMoDTO> createDetailsMo(@Valid @RequestBody DetailsMoDTO detailsMoDTO) throws URISyntaxException {
        log.debug("REST request to save DetailsMo : {}", detailsMoDTO);
        if (detailsMoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new detailsMo cannot already have an ID")).body(null);
        }
        DetailsMoDTO result = detailsMoService.save(detailsMoDTO);
        return ResponseEntity.created(new URI("/api/details-mos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /details-mos : Updates an existing detailsMo.
     *
     * @param detailsMoDTO the detailsMoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailsMoDTO,
     * or with status 400 (Bad Request) if the detailsMoDTO is not valid,
     * or with status 500 (Internal Server Error) if the detailsMoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/details-mos")
    @Timed
    public ResponseEntity<DetailsMoDTO> updateDetailsMo(@Valid @RequestBody DetailsMoDTO detailsMoDTO) throws URISyntaxException {
        log.debug("REST request to update DetailsMo : {}", detailsMoDTO);
        if (detailsMoDTO.getId() == null) {
            return createDetailsMo(detailsMoDTO);
        }
        DetailsMoDTO result = detailsMoService.save(detailsMoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailsMoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /details-mos : get all the detailsMos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detailsMos in body
     */
    @GetMapping("/details-mos")
    @Timed
    public ResponseEntity<List<DetailsMoDTO>> getAllDetailsMos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DetailsMos");
        Page<DetailsMoDTO> page = detailsMoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/details-mos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /details-mos/:id : get the "id" detailsMo.
     *
     * @param id the id of the detailsMoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailsMoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/details-mos/{id}")
    @Timed
    public ResponseEntity<DetailsMoDTO> getDetailsMo(@PathVariable Long id) {
        log.debug("REST request to get DetailsMo : {}", id);
        DetailsMoDTO detailsMoDTO = detailsMoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailsMoDTO));
    }

    /**
     * DELETE  /details-mos/:id : delete the "id" detailsMo.
     *
     * @param id the id of the detailsMoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/details-mos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailsMo(@PathVariable Long id) {
        log.debug("REST request to delete DetailsMo : {}", id);
        detailsMoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/details-mos?query=:query : search for the detailsMo corresponding
     * to the query.
     *
     * @param query the query of the detailsMo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/details-mos")
    @Timed
    public ResponseEntity<List<DetailsMoDTO>> searchDetailsMos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DetailsMos for query {}", query);
        Page<DetailsMoDTO> page = detailsMoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/details-mos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

        /**
     * GET  /details-mos/:devisId : get the "devisId" prestation.
     *
     * @param devisId the devisId of the DetailsMoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailsMoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/details-mos/devis/{devisId}")
    @Timed
    public ResponseEntity<List<DetailsMoDTO>> getDetailsMoByDevis(@ApiParam Pageable pageable, @PathVariable Long devisId) {
        log.debug("REST request to get detailsMo : {}", devisId);
        Page<DetailsMoDTO> detailsMoDTO = detailsMoService.findByDevis(pageable, devisId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(detailsMoDTO, "/api/details-mos/devis");
        return new ResponseEntity<>(detailsMoDTO.getContent(), headers, HttpStatus.OK);
    }

}
