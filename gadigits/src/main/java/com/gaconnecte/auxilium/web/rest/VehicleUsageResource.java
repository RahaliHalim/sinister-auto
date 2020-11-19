package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.VehicleUsageService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.VehicleUsageDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing VehicleUsage.
 */
@RestController
@RequestMapping("/api")
public class VehicleUsageResource {

    private final Logger log = LoggerFactory.getLogger(VehicleUsageResource.class);

    private static final String ENTITY_NAME = "vehicleUsage";

    private final VehicleUsageService vehicleUsageService;
    
    @Autowired
    private HistoryService historyService;

    public VehicleUsageResource(VehicleUsageService vehicleUsageService) {
        this.vehicleUsageService = vehicleUsageService;
    }

    /**
     * POST  /vehicle-usages : Create a new vehicleUsage.
     *
     * @param vehicleUsageDTO the vehicleUsageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleUsageDTO, or with status 400 (Bad Request) if the vehicleUsage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-usages")
    @Timed
    public ResponseEntity<VehicleUsageDTO> createVehicleUsage(@RequestBody VehicleUsageDTO vehicleUsageDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleUsage : {}", vehicleUsageDTO);
        if (vehicleUsageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicleUsage cannot already have an ID")).body(null);
        }
        VehicleUsageDTO result = vehicleUsageService.save(vehicleUsageDTO);
        if(result!= null ) {
         	historyService.historysave("UsageVehicule", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/vehicle-usages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-usages : Updates an existing vehicleUsage.
     *
     * @param vehicleUsageDTO the vehicleUsageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleUsageDTO,
     * or with status 400 (Bad Request) if the vehicleUsageDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleUsageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-usages")
    @Timed
    public ResponseEntity<VehicleUsageDTO> updateVehicleUsage(@RequestBody VehicleUsageDTO vehicleUsageDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleUsage : {}", vehicleUsageDTO);
        if (vehicleUsageDTO.getId() == null) {
            return createVehicleUsage(vehicleUsageDTO);
        }
        VehicleUsageDTO oldVehiculeUsage = vehicleUsageService.findOne(vehicleUsageDTO.getId());
        VehicleUsageDTO result = vehicleUsageService.save(vehicleUsageDTO);
        historyService.historysave("UsageVehicule",result.getId(),oldVehiculeUsage,result,0,0, "Modification");

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleUsageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-usages : get all the vehicleUsages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleUsages in body
     */
    @GetMapping("/vehicle-usages")
    @Timed
    public List<VehicleUsageDTO> getAllVehicleUsages() {
        log.debug("REST request to get all VehicleUsages");
        return vehicleUsageService.findAll();
    }

    /**
     * GET  /vehicle-usages/:id : get the "id" vehicleUsage.
     *
     * @param id the id of the vehicleUsageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleUsageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-usages/{id}")
    @Timed
    public ResponseEntity<VehicleUsageDTO> getVehicleUsage(@PathVariable Long id) {
        log.debug("REST request to get VehicleUsage : {}", id);
        VehicleUsageDTO vehicleUsageDTO = vehicleUsageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleUsageDTO));
    }

    /**
     * DELETE  /vehicle-usages/:id : delete the "id" vehicleUsage.
     *
     * @param id the id of the vehicleUsageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-usages/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleUsage(@PathVariable Long id) {
        log.debug("REST request to delete VehicleUsage : {}", id);
        vehicleUsageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-usages?query=:query : search for the vehicleUsage corresponding
     * to the query.
     *
     * @param query the query of the vehicleUsage search
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-usages")
    @Timed
    public List<VehicleUsageDTO> searchVehicleUsages(@RequestParam String query) {
        log.debug("REST request to search VehicleUsages for query {}", query);
        return vehicleUsageService.search(query);
    }

}
