package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefTypeServicePackService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServicePackDTO;
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
 * REST controller for managing RefTypeService.
 */
@RestController
@RequestMapping("/api")
public class RefTypeServicePackResource {

    private final Logger log = LoggerFactory.getLogger(RefTypeServicePackResource.class);

    private static final String ENTITY_NAME = "refTypeServicePack";

    private final RefTypeServicePackService refTypeServicePackService;

    public RefTypeServicePackResource(RefTypeServicePackService refTypeServicePackService) {
        this.refTypeServicePackService = refTypeServicePackService;
    }

    /**
     * POST  /ref-type-servicePacks : Create a new refTypeService.  Pack
     *
     * @param refTypeServiceDTO the refTypeServiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refTypeServiceDTO, or with status 400 (Bad Request) if the refTypeService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-type-service-packs")
    @Timed
    public ResponseEntity<RefTypeServicePackDTO> createRefTypeServicePack(@Valid @RequestBody RefTypeServicePackDTO refTypeServicePackDTO) throws URISyntaxException {
        log.debug("REST request to save RefTypeServicePacks : {}", refTypeServicePackDTO);
        if (refTypeServicePackDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refTypeServicePack cannot already have an ID")).body(null);
        }
        RefTypeServicePackDTO result = refTypeServicePackService.save(refTypeServicePackDTO);
        return ResponseEntity.created(new URI("/api/ref-type-service-packs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-type-services : Updates an existing refTypeService.
     *
     * @param refTypeServiceDTO the refTypeServiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refTypeServiceDTO,
     * or with status 400 (Bad Request) if the refTypeServiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the refTypeServiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-type-service-packs")
    @Timed
    public ResponseEntity<RefTypeServicePackDTO> updateRefTypeServicePacks(@Valid @RequestBody RefTypeServicePackDTO refTypeServicePackDTO) throws URISyntaxException {
        log.debug("REST request to update RefTypeServicePacks : {}", refTypeServicePackDTO);
        if (refTypeServicePackDTO.getId() == null) {
            return createRefTypeServicePack(refTypeServicePackDTO);
        }
        RefTypeServicePackDTO result = refTypeServicePackService.save(refTypeServicePackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refTypeServicePackDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-type-services : get all the refTypeServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refTypeServicePacks in body
     */
    @GetMapping("/ref-type-service-packs")
    @Timed
    public ResponseEntity<List<RefTypeServicePackDTO>> getAllRefTypeServicePacks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RefTypeServicePacks");
        Page<RefTypeServicePackDTO> page = refTypeServicePackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ref-type-service-packs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ref-type-services/:id : get the "id" refTypeService.
     *
     * @param id the id of the refTypeServiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refTypeServiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-type-service-packks/{id}")
    @Timed
    public ResponseEntity<RefTypeServicePackDTO> getRefTypeServicePack(@PathVariable Long id) {
        log.debug("REST request to get RefTypeServicePack : {}", id);
        RefTypeServicePackDTO refTypeServicePackDTO = refTypeServicePackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTypeServicePackDTO));
    }

    /**
     * DELETE  /ref-type-services/:id : delete the "id" refTypeService.
     *
     * @param id the id of the refTypeServiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-type-service-packks/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefTypeServicePack(@PathVariable Long id) {
        log.debug("REST request to delete RefTypeServicePack : {}", id);
        System.out.println("delete tyos serrr----");
        refTypeServicePackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
  
    /**
     * GET  /reftypeservicepacks/:id : get the "id" .
     *
     * @param id the id of the camionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reftypeservicepacksDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-type-service-packs-by-type-service/{id}")
    @Timed
    public ResponseEntity<RefTypeServicePackDTO> getRefTypeServicePacksbyTypeService(@PathVariable Long id) {
        log.debug("REST request to search for a page of RefTypeServicepacks By Type Service for query {}",  id);
        RefTypeServicePackDTO refTypeServicePackDTO  = refTypeServicePackService.findByTypeService(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refTypeServicePackDTO));
       
    }
    
    @GetMapping("/ref-type-service-packs-type-service/{id}")
    @Timed
    public ResponseEntity<List<RefTypeServicePackDTO>> getPackssByRefTypeServie(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get RefTypesServicePack : {}", id);
        System.out.println("REST request to get RefTypesServicePack tyos serrr----++++++++++++++++++++++++++++++");
        Page<RefTypeServicePackDTO> refTypeServicePackDTO = refTypeServicePackService.findPacksByRefTypeService(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(refTypeServicePackDTO, "/ref-type-service-packs-type-service");
        return new ResponseEntity<>(refTypeServicePackDTO.getContent(), headers, HttpStatus.OK);
    }
    /**
     * SEARCH  /_search/ref-type-services?query=:query : search for the refTypeService corresponding
     * to the query.
     *
     * @param query the query of the refTypeService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-type-service-packs")
    @Timed
    public ResponseEntity<List<RefTypeServicePackDTO>> searchRefTypeServicePacks(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefTypeServicepacks for query {}", query);
        Page<RefTypeServicePackDTO> page = refTypeServicePackService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-type-service-packs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
