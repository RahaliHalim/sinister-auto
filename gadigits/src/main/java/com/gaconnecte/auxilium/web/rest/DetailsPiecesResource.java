package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.DetailsPiecesService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.mapper.DetailsPiecesMapper;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.collections.CollectionUtils;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DetailsPieces.
 */
@RestController
@RequestMapping("/api")
public class DetailsPiecesResource {

    private final Logger log = LoggerFactory.getLogger(DetailsPiecesResource.class);

    private static final String ENTITY_NAME = "detailsPieces";

    private final DetailsPiecesService detailsPiecesService;

    public DetailsPiecesResource(DetailsPiecesService detailsPiecesService) {
        this.detailsPiecesService = detailsPiecesService;
    }

    /**
     * POST /details-pieces : Create a new detailsPieces.
     *
     * @param detailsPiecesDTO the detailsPiecesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         detailsPiecesDTO, or with status 400 (Bad Request) if the
     *         detailsPieces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/details-pieces")
    @Timed
    public ResponseEntity<DetailsPiecesDTO> createDetailsPieces(@Valid @RequestBody DetailsPiecesDTO detailsPiecesDTO)
            throws URISyntaxException {
        log.debug("REST request to save DetailsPieces : {}", detailsPiecesDTO);

        DetailsPiecesDTO result = detailsPiecesService.save(detailsPiecesDTO);
        return ResponseEntity.created(new URI("/api/details-pieces/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /details-pieces : Updates an existing detailsPieces.
     *
     * @param detailsPiecesDTO the detailsPiecesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         detailsPiecesDTO, or with status 400 (Bad Request) if the
     *         detailsPiecesDTO is not valid, or with status 500 (Internal Server
     *         Error) if the detailsPiecesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/details-pieces")
    @Timed
    public ResponseEntity<DetailsPiecesDTO> updateDetailsPieces(@Valid @RequestBody DetailsPiecesDTO detailsPiecesDTO)
            throws URISyntaxException {
        log.debug("REST request to update DetailsPieces : {}", detailsPiecesDTO);
        System.out.println("detailspiecesID" + detailsPiecesDTO.getId());
        if (detailsPiecesDTO.getId() == null) {
            return createDetailsPieces(detailsPiecesDTO);
        }

        log.debug("REST request to update Vetuste DetailsPieces : {}", detailsPiecesDTO.getVetuste());
        DetailsPiecesDTO result = detailsPiecesService.save(detailsPiecesDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailsPiecesDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /details-pieces : get all the detailsPieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detailsPieces
     *         in body
     */
    @GetMapping("/details-pieces")
    @Timed
    public ResponseEntity<List<DetailsPiecesDTO>> getAllDetailsPieces(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DetailsPieces");
        Page<DetailsPiecesDTO> page = detailsPiecesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/details-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /details-pieces : get all the detailsPieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detailsPieces
     *         in body
     */
    @GetMapping("/details-pieces/{devisId}/{typeId}/{isMo}")
    @Timed
    public ResponseEntity<List<DetailsPiecesDTO>> getAllDetailsPiecesByDevisAndType(@PathVariable Long devisId,
            @PathVariable Long typeId, @PathVariable Boolean isMo) {
        log.debug("REST request to get a page of DetailsPieces");

        try {
        	List<DetailsPiecesDTO> page = detailsPiecesService.findAllByDevisAndType(devisId, typeId, isMo);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/details-piecesOther/{devisId}/{typeId}/{isMo}")
    @Timed
    public ResponseEntity<List<DetailsPiecesDTO>> getAllDetailsPiecesByDevisAndTypeOther(@PathVariable Long devisId,
            @PathVariable Long typeId, @PathVariable Boolean isMo) {
        log.debug("REST request to get a page of DetailsPieces");

        try {
        	List<DetailsPiecesDTO> page = detailsPiecesService.findAllByDevisAndTypeOther(devisId, typeId, isMo);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/details-pieces-for-quotation-mp/{devisId}")
    @Timed
    public Boolean detailsPiecesByDevis(@PathVariable Long devisId) {
        log.debug("REST request to get a page of DetailsPieces");
        Boolean page = detailsPiecesService.findAllByDevis(devisId);
        return page;
    }

    @GetMapping("/details-pieces/quotation-mp/{devisId}/{typeId}/{isMo}")
    @Timed
    public ResponseEntity<List<DetailsPiecesDTO>> getAllDetailsPiecesByQuotationMPAndType(@PathVariable Long devisId,
            @PathVariable Long typeId, @PathVariable Boolean isMo) {
        log.debug("REST request to get a page of DetailsPieces");
        try {
        	List<DetailsPiecesDTO> page = detailsPiecesService.findAllByQuotationMPAndType(devisId, typeId, isMo);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /details-pieces/:id : get the "id" detailsPieces.
     *
     * @param id the id of the detailsPiecesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         detailsPiecesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/details-pieces/{id}")
    @Timed
    public ResponseEntity<DetailsPiecesDTO> getDetailsPieces(@PathVariable Long id) {
        log.debug("REST request to get DetailsPieces : {}", id);
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailsPiecesDTO));
    }

    /**
     * GET /details-pieces-line-modified/:id : get the "id" detailsPieces.
     *
     * @param id the id of the detailsPiecesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         detailsPiecesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/details-pieces/line-modified/{quotationId}/{lineModifiedId}")
    @Timed
    public ResponseEntity<DetailsPiecesDTO> getDetailsPiecesModified(@PathVariable Long quotationId,
            @PathVariable Long lineModifiedId) {
        log.debug("REST request to get DetailsPieces : {}", quotationId, lineModifiedId);
        DetailsPiecesDTO detailsPiecesDTO = detailsPiecesService.findLineModified(quotationId, lineModifiedId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(detailsPiecesDTO));
    }

    /**
     * DELETE /details-pieces/:id : delete the "id" detailsPieces.
     *
     * @param id the id of the detailsPiecesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/details-pieces/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailsPieces(@PathVariable Long id) {
        log.debug("REST request to delete DetailsPieces : {}", id);
        detailsPiecesService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/details-pieces-by-mp/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailsPiecesByDevis(@PathVariable Long id) {
        log.debug("REST request to delete DetailsPieces : {}", id);
        detailsPiecesService.deleteByDevis(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/details-pieces-by-mp-comp/{id}")
    @Timed
    public ResponseEntity<Void> deleteDetailsPiecesByDevisComp(@PathVariable Long id) {
        log.debug("REST request to delete DetailsPieces : {}", id);
        detailsPiecesService.deleteByDevisComp(id);
        return ResponseEntity.ok().build();
    }

    /**
     * SEARCH /_search/details-pieces?query=:query : search for the detailsPieces
     * corresponding to the query.
     *
     * @param query    the query of the detailsPieces search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/details-pieces")
    @Timed
    public ResponseEntity<List<DetailsPiecesDTO>> searchDetailsPieces(@RequestParam String query,
            @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of DetailsPieces for query {}", query);
        Page<DetailsPiecesDTO> page = detailsPiecesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
                "/api/_search/details-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @PostMapping("/details-pieces-fusion-quotation/{sinisterPecId}/{primaryQuotationId}")
    @Timed
    public ResponseEntity<Void> fusionDetailsPieces(@PathVariable Long sinisterPecId, @PathVariable Long primaryQuotationId)
            throws URISyntaxException {
        log.debug("REST request to fusion quotation with sinPecId : {}", sinisterPecId);
        detailsPiecesService.fusion(primaryQuotationId, sinisterPecId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/details-pieces-quotation-annulee/{sinisterPecId}")
    @Timed
    public ResponseEntity<Void> deleteDetailsPiecesDevisAnnulee(@PathVariable Long sinisterPecId) {
        log.debug("REST request to delete DetailsPieces : {}", sinisterPecId);
        detailsPiecesService.deleteDetailsPiecesDevisAnnule(sinisterPecId);
        return ResponseEntity.ok().build();
    }

}
