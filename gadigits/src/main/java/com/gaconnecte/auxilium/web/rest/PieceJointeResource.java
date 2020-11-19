package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.ResourceNotFoundException;
import com.gaconnecte.auxilium.domain.Result;
import com.gaconnecte.auxilium.service.PieceJointeService;
import com.gaconnecte.auxilium.service.StorageService;
import com.gaconnecte.auxilium.service.dto.PieceJointeDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PieceJointe.
 */
@RestController
@RequestMapping("/api")
public class PieceJointeResource {

    private final Logger log = LoggerFactory.getLogger(PieceJointeResource.class);

    private static final String ENTITY_NAME = "pieceJointe";

    private final PieceJointeService pieceJointeService;
    
    @Autowired
    private StorageService storageService;

    public PieceJointeResource(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    /**
     * POST  /piece-jointes : Create a new pieceJointe.
     *
     * @param pieceJointeDTO the pieceJointeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceJointeDTO, or with status 400 (Bad Request) if the pieceJointe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
   
    
  
    @PostMapping("/piece-jointes")
    @Timed
    public ResponseEntity<PieceJointeDTO> createPieceJointe(@Valid @RequestBody PieceJointeDTO pieceJointeDTO) throws URISyntaxException, ResourceNotFoundException {
        log.debug("REST request to save PieceJointe : {}", pieceJointeDTO);
        if (pieceJointeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pieceJointe cannot already have an ID")).body(null);
        }
        
        try {
	        PieceJointeDTO result = pieceJointeService.save(pieceJointeDTO); 
	        return ResponseEntity.ok()
	                
	                .body(result);

		} catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "storeFailed", "The has failed to store the uploaded file")).body(null);
		}
    }



    /**
     * POST  /piece-jointes : Create a new pieceJointe.
     *
     * @param pieceJointeDTO the pieceJointeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceJointeDTO, or with status 400 (Bad Request) if the pieceJointe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
   
    
  
    @PostMapping("/piece-jointes/Signature")
    @Timed
    public ResponseEntity<PieceJointeDTO> createPieceJointeSignature(@Valid @RequestBody PieceJointeDTO pieceJointeDTO) throws URISyntaxException, ResourceNotFoundException {
        log.debug("REST request to save PieceJointe : {}", pieceJointeDTO);
        if (pieceJointeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pieceJointe cannot already have an ID")).body(null);
        }
        
        try {
	        PieceJointeDTO result = pieceJointeService.savePieceJointeSignature(pieceJointeDTO); 
	        return ResponseEntity.created(new URI("/api/piece-jointes/" + result.getId()))
	                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	                .body(result);

		} catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "storeFailed", "The has failed to store the uploaded file")).body(null);
		}
    }

    /**
     * POST  /piece-jointes : Create a new pieceJointe.
     *
     * @param pieceJointeDTO the pieceJointeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pieceJointeDTO, or with status 400 (Bad Request) if the pieceJointe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attachments")
    @Timed
    public ResponseEntity<PieceJointeDTO> store(@Valid @RequestBody PieceJointeDTO pieceJointeDTO, @RequestParam("file") MultipartFile file) throws URISyntaxException, ResourceNotFoundException {
        log.debug("REST request to save PieceJointe : {}", pieceJointeDTO);
        if (pieceJointeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pieceJointe cannot already have an ID")).body(null);
        }

		try {
			storageService.store(file);
	        PieceJointeDTO result = pieceJointeService.save(pieceJointeDTO); 
			log.info("You successfully uploaded " + file.getOriginalFilename() + "!");
	        return ResponseEntity.created(new URI("/api/piece-jointes/" + result.getId()))
	                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	                .body(result);

		} catch (Exception e) {
			log.info("FAIL to upload " + file.getOriginalFilename() + "!");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "storeFailed", "The has failed to store the uploaded file")).body(null);
		}
    }

    

    /**
     * PUT  /piece-jointes : Updates an existing pieceJointe.
     *
     * @param pieceJointeDTO the pieceJointeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pieceJointeDTO,
     * or with status 400 (Bad Request) if the pieceJointeDTO is not valid,
     * or with status 500 (Internal Server Error) if the pieceJointeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

     
    @PutMapping("/piece-jointes")
    @Timed
    public ResponseEntity<PieceJointeDTO> updatePieceJointe(@Valid @RequestBody PieceJointeDTO pieceJointeDTO) throws URISyntaxException {
        log.debug("REST request to update PieceJointe : {}", pieceJointeDTO);
        if (pieceJointeDTO.getId() == null) {
            return createPieceJointe(pieceJointeDTO);
        }
        PieceJointeDTO result = pieceJointeService.save(pieceJointeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pieceJointeDTO.getId().toString()))
            .body(result);
    }
    
    

    /**
     * GET  /piece-jointes : get all the pieceJointes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pieceJointes in body
     */
    @GetMapping("/piece-jointes")
    @Timed
    public ResponseEntity<List<PieceJointeDTO>> getAllPieceJointes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PieceJointes");
        Page<PieceJointeDTO> page = pieceJointeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/piece-jointes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /piece-jointes/:id : get the "id" pieceJointe.
     *
     * @param id the id of the pieceJointeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pieceJointeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/piece-jointes/{id}")
    @Timed
    public ResponseEntity<PieceJointeDTO> getPieceJointe(@PathVariable Long id) {
        log.debug("REST request to get PieceJointe : {}", id);
        PieceJointeDTO pieceJointeDTO = pieceJointeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pieceJointeDTO));
    }

    /**
     * DELETE  /piece-jointes/:id : delete the "id" pieceJointe.
     *
     * @param id the id of the pieceJointeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/piece-jointes/{id}")
    @Timed
    public ResponseEntity<Void> deletePieceJointe(@PathVariable Long id) {
        log.debug("REST request to delete PieceJointe : {}", id);
        pieceJointeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/piece-jointes?query=:query : search for the pieceJointe corresponding
     * to the query.
     *
     * @param query the query of the pieceJointe search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/piece-jointes")
    @Timed
    public ResponseEntity<List<PieceJointeDTO>> searchPieceJointes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PieceJointes for query {}", query);
        Page<PieceJointeDTO> page = pieceJointeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/piece-jointes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

     /**
     * GET  /piece-jointes/:prestationId : get the "prestationId" prestation.
     *
     * @param prestationId the prestationId of the pieceJointeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/piece-jointes/prestation/{prestationId}")
    @Timed
    public ResponseEntity<List<PieceJointeDTO>> getPiecesJointeByPrestation(@ApiParam Pageable pageable, @PathVariable Long prestationId) {
        log.debug("REST request to get Prestation : {}", prestationId);
        Page<PieceJointeDTO> pieceJointeDTO = pieceJointeService.findByPrestation(pageable, prestationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pieceJointeDTO, "/api/piece-jointes/prestation");
        return new ResponseEntity<>(pieceJointeDTO.getContent(), headers, HttpStatus.OK);
    }

 /**
     * GET  /piece-jointes/:devisId : get the "devisId" prestation.
     *
     * @param devisId the devisId of the pieceJointeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/piece-jointes/devis/{devisId}")
    @Timed
    public ResponseEntity<List<PieceJointeDTO>> getPiecesJointeByDevis(@ApiParam Pageable pageable, @PathVariable Long devisId) {
        log.debug("REST request to get Prestation : {}", devisId);
        Page<PieceJointeDTO> pieceJointeDTO = pieceJointeService.findByDevis(pageable, devisId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(pieceJointeDTO, "/api/piece-jointes/devis");
        return new ResponseEntity<>(pieceJointeDTO.getContent(), headers, HttpStatus.OK);
    }

 /**
     * GET  /piece-jointes/affect : affect chemin to piece jointe.
     *
     * @param 
     * @return Result
     */
    @GetMapping("/piece-jointes/affect")
    @Timed
    public Result affectChemin() {
        Result chemin = pieceJointeService.affectChemin();
        return chemin;
    }


    @PostMapping("/piece-jointes/upload/{id}")
    @Timed
    public ResponseEntity<PieceJointeDTO> uploadChemin(@PathVariable Long id, @RequestParam("chemin") MultipartFile file)  {

        log.debug("in file upload process !!!");
        PieceJointeDTO pieceJointeDTO = pieceJointeService.uploadChemin(id, file);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pieceJointeDTO.getId().toString()))
            .body(pieceJointeDTO);

    }

    @PostMapping("/piece-jointes/upload")
    @Timed
    public void createChemin(@RequestParam("chemin") MultipartFile file)  {

        log.debug("in file upload process !!!");
        pieceJointeService.createChemin(file);

    }

}
