package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.TugPricingService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.TugPricingDTO;
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

/**
 * REST controller for managing TugPricing.
 */
@RestController
@RequestMapping("/api")
public class TugPricingResource {

    private final Logger log = LoggerFactory.getLogger(TugPricingResource.class);

    private static final String ENTITY_NAME = "tugPricing";

    private final TugPricingService tugPricingService;

    public TugPricingResource(TugPricingService tugPricingService) {
        this.tugPricingService = tugPricingService;
    }

    /**
     * POST  /tugPricings : Create a new tugPricing.
     *
     * @param tugPricingDTO the tugPricingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tugPricingDTO, or with status 400 (Bad Request) if the tugPricing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tugPricings")
    @Timed
    public ResponseEntity<TugPricingDTO> createPricing(@Valid @RequestBody TugPricingDTO tugPricingDTO) throws URISyntaxException {
        log.debug("REST request to save TugPricing : {}", tugPricingDTO);
        if (tugPricingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tarif cannot already have an ID")).body(null);
        }
        TugPricingDTO result = tugPricingService.save(tugPricingDTO);
        return ResponseEntity.created(new URI("/api/tugPricings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tugPricing : Updates an existing tugPricing.
     *
     * @param tugPricingDTO the tugPricingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tugPricingDTO,
     * or with status 400 (Bad Request) if the tugPricingDTO is not valid,
     * or with status 500 (Internal Server Error) if the tarifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tugPricings")
    @Timed
    public ResponseEntity<TugPricingDTO> updatePricing(@Valid @RequestBody TugPricingDTO tugPricingDTO) throws URISyntaxException {
        log.debug("REST request to update TugPricing : {}", tugPricingDTO);
        if (tugPricingDTO.getId() == null) {
            return createPricing(tugPricingDTO);
        }
        TugPricingDTO result = tugPricingService.save(tugPricingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tugPricingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tugPricings : get all the tugPricings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tugPricings in body
     */
    @GetMapping("/tugPricings")
    @Timed
    public ResponseEntity<List<TugPricingDTO>> getAllTugPricings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TugPricings");
        Page<TugPricingDTO> page = tugPricingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tugPricings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tugPricings/:id : get the "id" tugPricing.
     *
     * @param id the id of the TugPricingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tarifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tugPricings/{id}")
    @Timed
    public ResponseEntity<TugPricingDTO> getTugPricingByRmq(@PathVariable Long id) {
        log.debug("REST request to get TugPricings : {}", id);
        TugPricingDTO tugPricingDTO = tugPricingService.findBy(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tugPricingDTO));
    }

    /**
     * DELETE  /tugPricings/:id : delete the "id" tugPricing.
     *
     * @param id the id of the TugPricingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tugPricings/{id}")
    @Timed
    public ResponseEntity<Void> deletepricing(@PathVariable Long id) {
        log.debug("REST request to delete TugPricing : {}", id);
        tugPricingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
