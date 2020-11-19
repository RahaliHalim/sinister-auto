package com.gaconnecte.auxilium.web.rest.prm;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.prm.ConventionService;
import com.gaconnecte.auxilium.service.prm.dto.ConventionDTO;
import com.gaconnecte.auxilium.service.dto.PartnerModeMappingDTO;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.util.HashSet;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Convention.
 */
@RestController
@RequestMapping("/api")
public class ConventionResource {

    private final Logger log = LoggerFactory.getLogger(ConventionResource.class);

    private static final String ENTITY_NAME = "convention";

    private final ConventionService conventionService;

    private LoggerService loggerService;


    public ConventionResource(ConventionService conventionService, LoggerService loggerService) {
        this.conventionService = conventionService;
        this.loggerService = loggerService;
    }

    /**
     * POST  /convention : Create a new convention.
     *
     * @param conventionDTO the conventionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conventionDTO, or with status 400 (Bad Request) if the convention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/convention", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<ConventionDTO> createConvention(@RequestPart(name = "signedConvention", required = false) MultipartFile signedConvention, @RequestPart(name = "convention") ConventionDTO conventionDTO) throws URISyntaxException {
        log.debug("REST request to save Convention : {}", conventionDTO.toString());
        if (conventionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new convention cannot already have an ID")).body(null);
        }
        ConventionDTO result = conventionService.save(signedConvention, conventionDTO);
        return ResponseEntity.created(new URI("/api/convention/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /convention : Updates an existing convention.
     *
     * @param conventionDTO the conventionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conventionDTO,
     * or with status 400 (Bad Request) if the conventionDTO is not valid,
     * or with status 500 (Internal Server Error) if the conventionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/convention")
    @Timed
    public ResponseEntity<ConventionDTO> updateConvention(@Valid @RequestBody ConventionDTO conventionDTO) throws URISyntaxException {
        log.debug("REST request to update Convention : {}", conventionDTO);
        if (conventionDTO.getId() == null) {
            return createConvention(null, conventionDTO);
        }
        ConventionDTO result = conventionService.save(null, conventionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conventionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /convention : get all the conventions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conventions in body
     */
    @GetMapping("/convention")
    @Timed
    public ResponseEntity<Set<ConventionDTO>> getAllConventions() {
        log.debug("REST request to get a page of Conventions");
		try {
			Set<ConventionDTO> packs = conventionService.findAll();
			return new ResponseEntity<>(packs, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

     @GetMapping("/convention/packs/{partnersId}")
    @Timed
    public ResponseEntity<Set<PartnerModeMappingDTO>> getAllPacksByPartner(@PathVariable Long[] partnersId) {
        log.debug("REST request to get a mode de gestion by partner");
		try {
			Set<PartnerModeMappingDTO> modes = conventionService.findPackByPartner(partnersId);
			return new ResponseEntity<>(modes, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
     
     @GetMapping("/convention/packs/modeGestion/{partnerId}")
     @Timed
     public ResponseEntity<Set<RefModeGestionDTO>> getAllModeGestionByPartner(@PathVariable Long partnerId) {
         log.debug("REST request to get a mode de gestion by partner");
 		try {
 			Set<RefModeGestionDTO> modes = conventionService.findModesByPartner(partnerId);
 			return new ResponseEntity<>(modes, HttpStatus.OK);
 		} catch (Exception e) {
 			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
 			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 		}
     } 
     
     
     

    /**
     * GET  /convention/:id : get the "id" convention.
     *
     * @param id the id of the conventionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conventionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/convention/{id}")
    @Timed
    public ResponseEntity<ConventionDTO> getConvention(@PathVariable Long id) {
        log.debug("REST request to get Convention : {}", id);
        ConventionDTO conventionDTO = conventionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conventionDTO));
    }

    /**
     * DELETE  /convention/:id : delete the "id" convention.
     *
     * @param id the id of the conventionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/convention/{id}")
    @Timed
    public ResponseEntity<Void> deleteConvention(@PathVariable Long id) {
        log.debug("REST request to delete Convention : {}", id);
        conventionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/convention?query=:query : search for the convention corresponding
     * to the query.
     *
     * @param query the query of the convention search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/convention")
    @Timed
    public ResponseEntity<List<ConventionDTO>> searchConventions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Conventions for query {}", query);
        Page<ConventionDTO> page = conventionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/convention");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
