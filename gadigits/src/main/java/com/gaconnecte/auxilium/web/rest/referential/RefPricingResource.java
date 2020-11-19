package com.gaconnecte.auxilium.web.rest.referential;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.referential.RefPricingService;
import com.gaconnecte.auxilium.service.referential.dto.RefPricingDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing RefPricing.
 */
@RestController
@RequestMapping("/api")
public class RefPricingResource {

    private final Logger log = LoggerFactory.getLogger(RefPricingResource.class);

    private static final String ENTITY_NAME = "refPricing";

    private final RefPricingService refPricingService;

    public RefPricingResource(RefPricingService refPricingService) {
        this.refPricingService = refPricingService;
    }

    /**
     * POST  /ref-pricing : Create a new refPricing.
     *
     * @param refPricingDTO the refPricingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refPricingDTO, or with status 400 (Bad Request) if the refPricing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-pricing")
    @Timed
    public ResponseEntity<RefPricingDTO> createRefPricing(@Valid @RequestBody RefPricingDTO refPricingDTO) throws URISyntaxException {
        log.debug("REST request to save RefPricing : {}", refPricingDTO);
        if (refPricingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refPricing cannot already have an ID")).body(null);
        }
        RefPricingDTO result = refPricingService.save(refPricingDTO);
        return ResponseEntity.created(new URI("/api/ref-pricing/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-pricing : Updates an existing refPricing.
     *
     * @param refPricingDTO the refPricingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refPricingDTO,
     * or with status 400 (Bad Request) if the refPricingDTO is not valid,
     * or with status 500 (Internal Server Error) if the refPricingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-pricing")
    @Timed
    public ResponseEntity<RefPricingDTO> updateRefPricing(@Valid @RequestBody RefPricingDTO refPricingDTO) throws URISyntaxException {
        log.debug("REST request to update RefPricing : {}", refPricingDTO);
        if (refPricingDTO.getId() == null) {
            return createRefPricing(refPricingDTO);
        }
        RefPricingDTO result = refPricingService.save(refPricingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refPricingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-pricing : get all the refPricings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refPricings in body
     */
    @GetMapping("/ref-pricing")
    @Timed
    public ResponseEntity<List<RefPricingDTO>> getAllRefPricings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefPricings");
        Page<RefPricingDTO> page = refPricingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-pricing");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-pricing/:id : get the "id" refPricing.
     *
     * @param id the id of the refPricingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refPricingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-pricing/{id}")
    @Timed
    public ResponseEntity<RefPricingDTO> getRefPricing(@PathVariable Long id) {
        log.debug("REST request to get RefPricing : {}", id);
        RefPricingDTO refPricingDTO = refPricingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refPricingDTO));
    }

    /**
     * DELETE  /ref-pricing/:id : delete the "id" refPricing.
     *
     * @param id the id of the refPricingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-pricing/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefPricing(@PathVariable Long id) {
        log.debug("REST request to delete RefPricing : {}", id);
        refPricingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
