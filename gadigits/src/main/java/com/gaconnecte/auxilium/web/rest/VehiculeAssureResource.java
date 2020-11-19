package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.RefTugTruckDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.GroupDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeContratDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
 * REST controller for managing VehiculeAssure.
 */
@RestController
@RequestMapping("/api")
public class VehiculeAssureResource {

    private final Logger log = LoggerFactory.getLogger(VehiculeAssureResource.class);

    private static final String ENTITY_NAME = "vehiculeAssure";

    private final VehiculeAssureService vehiculeAssureService;
    @Autowired
    PartnerService partnerService;

    public VehiculeAssureResource(VehiculeAssureService vehiculeAssureService) {
        this.vehiculeAssureService = vehiculeAssureService;
    }
    

    /**
     * POST  /vehicule-assures : Create a new vehiculeAssure.
     *
     * @param vehiculeAssureDTO the vehiculeAssureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehiculeAssureDTO, or with status 400 (Bad Request) if the vehiculeAssure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicule-assures")
    @Timed
    public ResponseEntity<VehiculeAssureDTO> createVehiculeAssure(@Valid @RequestBody VehiculeAssureDTO vehiculeAssureDTO) throws URISyntaxException {
        log.debug("REST request to save VehiculeAssure : {not found}", vehiculeAssureDTO.getImmatriculationVehicule());
        //vehiculeAssureService.deleteByContrat(vehiculeAssureDTO.getContratId());
        VehiculeAssureDTO result = vehiculeAssureService.save(vehiculeAssureDTO);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * PUT  /vehicule-assures : Updates an existing vehiculeAssure.
     *
     * @param vehiculeAssureDTO the vehiculeAssureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehiculeAssureDTO,
     * or with status 400 (Bad Request) if the vehiculeAssureDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehiculeAssureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicule-assures")
    @Timed
    public ResponseEntity<VehiculeAssureDTO> updateVehiculeAssure(@Valid @RequestBody VehiculeAssureDTO vehiculeAssureDTO) throws URISyntaxException {
        log.debug("REST request to update VehiculeAssure : {}", vehiculeAssureDTO);
        if (vehiculeAssureDTO.getId() == null) {
            return createVehiculeAssure(vehiculeAssureDTO);
        }
        VehiculeAssureDTO result = vehiculeAssureService.save(vehiculeAssureDTO);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * GET  /vehicule-assures : get all the vehiculeAssures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vehiculeAssures in body
     */
    @GetMapping("/vehicule-assures")
    @Timed
    public ResponseEntity<List<VehiculeAssureDTO>> getAllVehiculeAssures(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VehiculeAssures");
        Page<VehiculeAssureDTO> page = vehiculeAssureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicule-assures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vehicule-assures/:id : get the "id" vehiculeAssure.
     *
     * @param id the id of the vehiculeAssureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehiculeAssureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicule-assures/{id}")
    @Timed
    public ResponseEntity<VehiculeAssureDTO> getVehiculeAssure(@PathVariable Long id) {
        log.debug("REST request to get VehiculeAssure : {}", id);
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiculeAssureDTO));
    }

    /**
     * DELETE  /vehicule-assures/:id : delete the "id" vehiculeAssure.
     *
     * @param id the id of the vehiculeAssureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicule-assures/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehiculeAssure(@PathVariable Long id) {
        log.debug("REST request to delete VehiculeAssure : {}", id);
        vehiculeAssureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicule-assures?query=:query : search for the vehiculeAssure corresponding
     * to the query.
     *
     * @param query the query of the vehiculeAssure search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vehicule-assures")
    @Timed
    public ResponseEntity<List<VehiculeAssureDTO>> searchVehiculeAssures(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of VehiculeAssures for query {}", query);
        Page<VehiculeAssureDTO> page = vehiculeAssureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vehicule-assures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 /**
     * GET  /vehicule-assures/:id : get the "id" vehiculeAssure.
     *
     * @param immatriculationVehicule the id of the vehiculeAssureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehiculeAssureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicule-assurees/{immatriculationVehicule}")
    @Timed
    public ResponseEntity<VehiculeAssureDTO> getVehiculeAssureByImmatriculation(@PathVariable String immatriculationVehicule) {
        log.debug("REST request to get VehiculeAssure : {}", immatriculationVehicule);
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByImmatriculation(immatriculationVehicule);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiculeAssureDTO));
    }
    @GetMapping("/vehicule-assures/contratAssurance/{contratId}")
    @Timed
    public ResponseEntity<List<VehiculeAssureDTO>> getVehiculeAssureByContrat(@ApiParam Pageable pageable,@PathVariable Long contratId) {
        log.debug("REST request to get VehiculeAssure : {}", contratId);
        Page<VehiculeAssureDTO> vehiculeAssureDTO = vehiculeAssureService.findOneByContrat(pageable,contratId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(vehiculeAssureDTO, "/api/vehicule-assures/contratAssurance");
        return new ResponseEntity<>(vehiculeAssureDTO.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/vehicule-assures/vehiculecontratAssurance")
    @Timed
    public ResponseEntity<List<VehiculeContratDTO>> getVehiculesForEachVehicle() {
        log.debug("REST request to get Vehicule for each contrat  : {}");
        List<VehiculeContratDTO> listvehiculeContrat = vehiculeAssureService.findContratsForEachVehicle();
        log.debug("REST request to get Vehicule f taille  : {}"+listvehiculeContrat.size());
        return new ResponseEntity<>(listvehiculeContrat, HttpStatus.OK);
    }
    
    @DeleteMapping("/vehicule-assures/contrat/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehiculeByContrat(@PathVariable Long id) {
        log.debug("REST request to delete Vehicule By Contrat : {}", id);
        vehiculeAssureService.deleteByContrat(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/vehicule-assurees-list/{immatriculationVehicule}")
    @Timed
    public ResponseEntity<Set<PartnerDTO>> getListVehiculeAssureByImmatriculation(@PathVariable String immatriculationVehicule) {
        log.debug("REST request to get VehiculeAssure : {}", immatriculationVehicule);
        Set<PartnerDTO> vehiculeAssureDTO = vehiculeAssureService.findListByImmatriculation(immatriculationVehicule);
        return new ResponseEntity<>(vehiculeAssureDTO, HttpStatus.OK);
    }
    
    @GetMapping("/vehicule-assures/company-immatriculation/{company}/{immatriculation}")
    @Timed
    public ResponseEntity<VehiculeAssureDTO> getVehiculeAssureByCompanyNameAndImmatriculation(@PathVariable String company, @PathVariable String immatriculation) {
        log.debug("REST request to get VehiculeAssure : {}", immatriculation);
        PartnerDTO partnerDTO = partnerService.findPartnerByName(company);
        VehiculeAssureDTO vehiculeAssureDTO = new VehiculeAssureDTO();
        if(partnerDTO != null) {
        	vehiculeAssureDTO = vehiculeAssureService.findOneByClientIdAndImmatriculation(partnerDTO.getId(), immatriculation);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiculeAssureDTO));
    }
    
    @GetMapping("/vehicule-assures/client-immatriculation/{clientId}/{immatriculation}")
    @Timed
    public ResponseEntity<VehiculeAssureDTO> getVehiculeAssureByClientIdAndImmatriculation(@PathVariable Long clientId, @PathVariable String immatriculation) {
        log.debug("REST request to get VehiculeAssure : {}", immatriculation);
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByClientIdAndImmatriculation(clientId, immatriculation);
        if(vehiculeAssureDTO != null) {
        	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiculeAssureDTO));
        }else {
        	return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new VehiculeAssureDTO()));
        }
    }
    
  
}
