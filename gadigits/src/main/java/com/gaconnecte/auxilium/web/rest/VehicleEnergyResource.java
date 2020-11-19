package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.VehicleEnergyService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.VehicleEnergyDTO;
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
 * REST controller for managing VehicleEnergy.
 */
@RestController
@RequestMapping("/api")
public class VehicleEnergyResource {

    private final Logger log = LoggerFactory.getLogger(VehicleEnergyResource.class);

    private static final String ENTITY_NAME = "vehicleEnergy";

    private final VehicleEnergyService vehicleEnergyService;
    
    @Autowired
    private HistoryService historyService;

    public VehicleEnergyResource(VehicleEnergyService vehicleEnergyService) {
        this.vehicleEnergyService = vehicleEnergyService;
    }

    /**
     * POST  /vehicle-energies : Create a new vehicleEnergy.
     *
     * @param vehicleEnergyDTO the vehicleEnergyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleEnergyDTO, or with status 400 (Bad Request) if the vehicleEnergy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-energies")
    @Timed
    public ResponseEntity<VehicleEnergyDTO> createVehicleEnergy(@RequestBody VehicleEnergyDTO vehicleEnergyDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleEnergy : {}", vehicleEnergyDTO);
        if (vehicleEnergyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicleEnergy cannot already have an ID")).body(null);
        }
        VehicleEnergyDTO result = vehicleEnergyService.save(vehicleEnergyDTO);
        if(result!= null ) {
         	historyService.historysave("VehicleEnergie", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/vehicle-energies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-energies : Updates an existing vehicleEnergy.
     *
     * @param vehicleEnergyDTO the vehicleEnergyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleEnergyDTO,
     * or with status 400 (Bad Request) if the vehicleEnergyDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleEnergyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-energies")
    @Timed
    public ResponseEntity<VehicleEnergyDTO> updateVehicleEnergy(@RequestBody VehicleEnergyDTO vehicleEnergyDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleEnergy : {}", vehicleEnergyDTO);
        if (vehicleEnergyDTO.getId() == null) {
            return createVehicleEnergy(vehicleEnergyDTO);
        }
        VehicleEnergyDTO oldVehicleEnergy = vehicleEnergyService.findOne(vehicleEnergyDTO.getId()); 
        VehicleEnergyDTO result = vehicleEnergyService.save(vehicleEnergyDTO);
        historyService.historysave("VehicleEnergie",result.getId(),oldVehicleEnergy,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleEnergyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-energies : get all the vehicleEnergies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleEnergies in body
     */
    @GetMapping("/vehicle-energies")
    @Timed
    public List<VehicleEnergyDTO> getAllVehicleEnergies() {
        log.debug("REST request to get all VehicleEnergies");
        return vehicleEnergyService.findAll();
    }

    /**
     * GET  /vehicle-energies/:id : get the "id" vehicleEnergy.
     *
     * @param id the id of the vehicleEnergyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleEnergyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-energies/{id}")
    @Timed
    public ResponseEntity<VehicleEnergyDTO> getVehicleEnergy(@PathVariable Long id) {
        log.debug("REST request to get VehicleEnergy : {}", id);
        VehicleEnergyDTO vehicleEnergyDTO = vehicleEnergyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleEnergyDTO));
    }

    /**
     * DELETE  /vehicle-energies/:id : delete the "id" vehicleEnergy.
     *
     * @param id the id of the vehicleEnergyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-energies/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleEnergy(@PathVariable Long id) {
        log.debug("REST request to delete VehicleEnergy : {}", id);
        vehicleEnergyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-energies?query=:query : search for the vehicleEnergy corresponding
     * to the query.
     *
     * @param query the query of the vehicleEnergy search
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-energies")
    @Timed
    public List<VehicleEnergyDTO> searchVehicleEnergies(@RequestParam String query) {
        log.debug("REST request to search VehicleEnergies for query {}", query);
        return vehicleEnergyService.search(query);
    }

    @GetMapping("/vehicle-energies/label/{label}")
    @Timed
    public ResponseEntity<VehicleEnergyDTO>  findByLabel(@PathVariable String label){
        log.debug("REST request to get VehicleEnergy by label: {}", label);
        VehicleEnergyDTO vehicleEnergyDTO = vehicleEnergyService.findByLabel(label);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleEnergyDTO));
    }

}
