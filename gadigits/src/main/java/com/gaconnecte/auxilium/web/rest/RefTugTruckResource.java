package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.ContactDTO;

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
import com.gaconnecte.auxilium.service.RefTugTruckService;
import java.util.Set;

/**
 * REST controller for managing Camion.
 */
@RestController
@RequestMapping("/api")
public class RefTugTruckResource {

    private final Logger log = LoggerFactory.getLogger(RefTugTruckResource.class);

    private static final String ENTITY_NAME = "camion";

    private final RefTugTruckService camionService;

    public RefTugTruckResource(RefTugTruckService camionService) {
        this.camionService = camionService;
    }

    /**
     * POST  /camions : Create a new camion.
     *
     * @param camionDTO the camionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tarifDTO, or with status 400 (Bad Request) if the camion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/camions")
    @Timed
    public ResponseEntity<RefTugTruckDTO> createCamion(@Valid @RequestBody RefTugTruckDTO camionDTO) throws URISyntaxException {
        log.debug("REST request to save Camion : {}", camionDTO);
        if (camionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new camion cannot already have an ID")).body(null);
        }
        RefTugTruckDTO result = camionService.save(camionDTO);
        return ResponseEntity.created(new URI("/api/camions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /camions : Updates an existing camion.
     *
     * @param tarifDTO the camionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated camionDTO,
     * or with status 400 (Bad Request) if the camionDTO is not valid,
     * or with status 500 (Internal Server Error) if the camionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/camions")
    @Timed
    public ResponseEntity<RefTugTruckDTO> updateCamion(@Valid @RequestBody RefTugTruckDTO camionDTO) throws URISyntaxException {
        log.debug("REST request to update Camion : {}", camionDTO);
        if (camionDTO.getId() == null) {
            return createCamion(camionDTO);
        }
        RefTugTruckDTO result = camionService.save(camionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, camionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /camions : get all the camions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of camions in body
     */
    @GetMapping("/camions")
    @Timed
    public ResponseEntity<List<RefTugTruckDTO>> getAllCamions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Camions");
        Page<RefTugTruckDTO> page = camionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/camions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /camion/:id : get the "id" camion.
     *
     * @param id the id of the camionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the camionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/camions/{id}")
    @Timed
    public ResponseEntity<RefTugTruckDTO> getCamion(@PathVariable Long id) {
        log.debug("REST request to get Camion : {}", id);
        RefTugTruckDTO  camionDTO = camionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(camionDTO));
    }
    
    /**
     * GET  /camions/:id : get the "id" camion.
     *
     * @param id the id of the camionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the camionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/camions/refRemorqueur/{id}")
    @Timed
    public ResponseEntity<List<RefTugTruckDTO>> getCamionsByRefRemorqueur(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get Camion : {}", id);
        Page<RefTugTruckDTO> camionDTO = camionService.findByRefRemorqueur(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(camionDTO, "/api/camions/refRemorqueur");
        return new ResponseEntity<>(camionDTO.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /camions/serviceType/:id : get the "id" camion.
     *
     * @param id the id of the camionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the camionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/camions/serviceType/{id}")
    @Timed
    public ResponseEntity<Set<RefTugTruckDTO>> findAllTrucksByServiceType(@PathVariable Long id) {
        log.debug("REST request to get truck by service type : {}", id);
        Set<RefTugTruckDTO> trucks = camionService.findAllTrucksByServiceType(id);
        return new ResponseEntity<>(trucks, HttpStatus.OK);
    }
    
    @GetMapping("/camions/serviceType-governorate/{id}/{governorateId}")
    @Timed
    public ResponseEntity<Set<RefTugTruckDTO>> findAllTrucksByServiceTypeAndByGovernorate(@PathVariable Long id, @PathVariable Long governorateId) {
        log.debug("REST request to get truck by service type : {}, {}", id, governorateId);
        Set<RefTugTruckDTO> trucks = camionService.findAllTrucksByServiceTypeAndByGovernorate(id,governorateId);
        return new ResponseEntity<>(trucks, HttpStatus.OK);
    }
    
    /**
     * DELETE  /camions/:id : delete the "id" camion.
     *
     * @param id the id of the camionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/camions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCamion(@PathVariable Long id) {
        log.debug("REST request to delete Camion : {}", id);
        camionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /camions/rn/:rn : get truck by it registration number
     *
     * @param id the id of the camionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the camionDTO
     */
    @GetMapping("/camions/rn/{rn}")
    @Timed
    public ResponseEntity<RefTugTruckDTO> findAllTrucksByServiceType(@PathVariable String rn) {
        log.debug("REST request to get truck by it registration number : {}", rn);
        RefTugTruckDTO truck = camionService.findTrucksByImmatriculation(rn);
        if (truck == null) {
            truck = new RefTugTruckDTO();
        }
        return ResponseEntity.ok().body(truck);
    }
}
