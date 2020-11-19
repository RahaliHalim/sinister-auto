package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.TiersDTO;
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
 * REST controller for managing Tiers.
 */
@RestController
@RequestMapping("/api")
public class TiersResource {

    private final Logger log = LoggerFactory.getLogger(TiersResource.class);

    private static final String ENTITY_NAME = "tiers";

    private final TiersService tiersService;

    public TiersResource(TiersService tiersService) {
        this.tiersService = tiersService;
    }

    /**
     * POST  /tiers : Create a new tiers.
     *
     * @param tiersDTO the tiersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tiersDTO, or with status 400 (Bad Request) if the tiers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tiers")
    @Timed
    public ResponseEntity<TiersDTO> createTiers(@Valid @RequestBody TiersDTO tiersDTO) throws URISyntaxException {
        log.debug("REST request to save Tiers : {}", tiersDTO);
        if (tiersDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tiers cannot already have an ID")).body(null);
        }
        TiersDTO result = tiersService.save(tiersDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * PUT  /tiers : Updates an existing tiers.
     *
     * @param tiersDTO the tiersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tiersDTO,
     * or with status 400 (Bad Request) if the tiersDTO is not valid,
     * or with status 500 (Internal Server Error) if the tiersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tiers")
    @Timed
    public ResponseEntity<TiersDTO> updateTiers(@Valid @RequestBody TiersDTO tiersDTO) throws URISyntaxException {
        log.debug("REST request to update Tiers : {}", tiersDTO);
        if (tiersDTO.getId() == null) {
            return createTiers(tiersDTO);
        }
        TiersDTO result = tiersService.save(tiersDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET  /tiers : get all the tiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tiers in body
     */
    @GetMapping("/tiers")
    @Timed
    public ResponseEntity<List<TiersDTO>> getAllTiers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Tiers");
        Page<TiersDTO> page = tiersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tiers/:id : get the "id" tiers.
     *
     * @param id the id of the tiersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tiersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tiers/{id}")
    @Timed
    public ResponseEntity<List<TiersDTO>> getTiers(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get Tiers : {}", id);
        Page<TiersDTO> tiersDTO = tiersService.findByPrestationPEC(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(tiersDTO, "/api/tiers");
        return new ResponseEntity<>(tiersDTO.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /tiers/:id : delete the "id" tiers.
     *
     * @param id the id of the tiersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTiers(@PathVariable Long id) {
        log.debug("REST request to delete Tiers : {}", id);
        tiersService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * SEARCH  /_search/tiers?query=:query : search for the tiers corresponding
     * to the query.
     *
     * @param query the query of the tiers search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tiers")
    @Timed
    public ResponseEntity<List<TiersDTO>> searchTiers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Tiers for query {}", query);
        Page<TiersDTO> page = tiersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/tiersImmat/{immatriculationTier}")
    @Timed
    public ResponseEntity<TiersDTO> findByImmatriculation(@PathVariable String immatriculationTier){
    	log.debug("REST request to get Tiers with immatriculation {}",immatriculationTier);
    	TiersDTO tiersDTO=tiersService.findByImmatriculation(immatriculationTier);
    	
    	if (tiersDTO ==null){return null;}
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tiersDTO));
    	
    }
    
    @GetMapping("/tiersByImmat/{immatriculationTier}/{clientId}")
    @Timed
    public ResponseEntity<Set<TiersDTO>> getTiersByImmatriculation(@PathVariable String immatriculationTier, @PathVariable Long clientId){
    	log.debug("REST request to get Tiers with immatriculation {}",immatriculationTier);
    	try {
    		Set<TiersDTO> tiersDTO=tiersService.findTierssByImmatriculation(immatriculationTier, clientId);
            return new ResponseEntity<>(tiersDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
