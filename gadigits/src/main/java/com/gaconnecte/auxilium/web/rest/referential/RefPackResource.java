package com.gaconnecte.auxilium.web.rest.referential;

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
import com.gaconnecte.auxilium.service.referential.RefPackService;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing RefPack.
 */
@RestController
@RequestMapping("/api")
public class RefPackResource {

    private final Logger log = LoggerFactory.getLogger(RefPackResource.class);

    private static final String ENTITY_NAME = "refPack";

    private final RefPackService refPackService;

    private LoggerService loggerService;

    
    public RefPackResource(RefPackService refPackService, LoggerService loggerService) {
        this.refPackService = refPackService;
        this.loggerService = loggerService;
    }

    /**
     * POST  /ref-packs : Create a new refPack.
     *
     * @param refPackDTO the refPackDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refPackDTO, or with status 400 (Bad Request) if the refPack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-packs")
    @Timed
    public ResponseEntity<RefPackDTO> createRefPack(@Valid @RequestBody RefPackDTO refPackDTO) throws URISyntaxException {
        log.debug("REST request to save RefPack : {}", refPackDTO);
        if (refPackDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refPack cannot already have an ID")).body(null);
        }
        RefPackDTO result = refPackService.save(refPackDTO);
        return ResponseEntity.created(new URI("/api/ref-packs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-packs : Updates an existing refPack.
     *
     * @param refPackDTO the refPackDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refPackDTO,
     * or with status 400 (Bad Request) if the refPackDTO is not valid,
     * or with status 500 (Internal Server Error) if the refPackDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-packs")
    @Timed
    public ResponseEntity<RefPackDTO> updateRefPack(@Valid @RequestBody RefPackDTO refPackDTO) throws URISyntaxException {
        log.debug("REST request to update RefPack : {}", refPackDTO);
        if (refPackDTO.getId() == null) {
            return createRefPack(refPackDTO);
        }
        RefPackDTO result = refPackService.save(refPackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refPackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-packs : get all the refPacks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refPacks in body
     */
    @GetMapping("/ref-packs")
    @Timed
    public ResponseEntity<Set<RefPackDTO>> getAllRefPacks() {
        log.debug("REST request to get a page of RefPacks");
        try {
            Set<RefPackDTO> packs = refPackService.findAll();
            return new ResponseEntity<>(packs, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * GET  /ref-packs/:id : get all the refPacks of service type with id id.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refPacks in body
     */
    @GetMapping("/ref-packs/ts/{id}")
    @Timed
    public ResponseEntity<Set<RefPackDTO>> getAllRefPacksByServiceType() {
        log.debug("REST request to get a page of RefPacks by service type");
		try {
			Set<RefPackDTO> packs = refPackService.findAll();
			return new ResponseEntity<>(packs, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    /**
     * GET  /packs/company/:id : get all the refPacks of service type with id id.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refPacks in body
     */
    @GetMapping("/packs/company/{id}")
    @Timed
    public ResponseEntity<Set<RefPackDTO>> getAllPacksByCompany(@PathVariable Long id) {
        log.debug("REST request to get a page of packs by company");
        try {
            Set<RefPackDTO> packs = refPackService.findAllPacksByCompany(id);
            return new ResponseEntity<>(packs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * GET  /ref-packs/:id : get the "id" refPack.
     *
     * @param id the id of the refPackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refPackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-packs/{id}")
    @Timed
    public ResponseEntity<RefPackDTO> getRefPack(@PathVariable Long id) {
        log.debug("REST request to get RefPack : {}", id);
        RefPackDTO refPackDTO = refPackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refPackDTO));
    }
    
    /**
     * GET  /ref-packs/:id : get the "id" refPack.
     *
     * @param id the id of the refPackDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refPackDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-packs/{id}/{idUser}")
    @Timed
    public ResponseEntity<RefPackDTO> getRefPackModesGestionByUser(@PathVariable Long id, @PathVariable Long idUser) {
        log.debug("REST request to get RefPack modes By User : {}", id);
        RefPackDTO refPackDTO = refPackService.findOneModesByUser(id,idUser);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refPackDTO));
    }

    /**
     * DELETE  /ref-packs/:id : delete the "id" refPack.
     *
     * @param id the id of the refPackDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-packs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefPack(@PathVariable Long id) {
        log.debug("REST request to delete RefPack : {}", id);
        refPackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ref-packs?query=:query : search for the refPack corresponding
     * to the query.
     *
     * @param query the query of the refPack search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-packs")
    @Timed
    public ResponseEntity<List<RefPackDTO>> searchRefPacks(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefPacks for query {}", query);
        Page<RefPackDTO> page = refPackService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-packs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
