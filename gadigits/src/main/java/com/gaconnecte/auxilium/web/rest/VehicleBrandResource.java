package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.VehicleBrandService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;

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
 * REST controller for managing VehicleBrand.
 */
@RestController
@RequestMapping("/api")
public class VehicleBrandResource {

    private final Logger log = LoggerFactory.getLogger(VehicleBrandResource.class);

    private static final String ENTITY_NAME = "vehicleBrand";

    private final VehicleBrandService vehicleBrandService;
    
    @Autowired
    private HistoryService historyService;

    public VehicleBrandResource(VehicleBrandService vehicleBrandService) {
        this.vehicleBrandService = vehicleBrandService;
    }

    /**
     * POST  /vehicle-brands : Create a new vehicleBrand.
     *
     * @param vehicleBrandDTO the vehicleBrandDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleBrandDTO, or with status 400 (Bad Request) if the vehicleBrand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-brands")
    @Timed
    public ResponseEntity<VehicleBrandDTO> createVehicleBrand(@RequestBody VehicleBrandDTO vehicleBrandDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleBrand : {}", vehicleBrandDTO);
        if (vehicleBrandDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicleBrand cannot already have an ID")).body(null);
        }
        VehicleBrandDTO result = vehicleBrandService.save(vehicleBrandDTO);
        if(result!= null ) {
         	historyService.historysave("Marque", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/vehicle-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-brands : Updates an existing vehicleBrand.
     *
     * @param vehicleBrandDTO the vehicleBrandDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleBrandDTO,
     * or with status 400 (Bad Request) if the vehicleBrandDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleBrandDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-brands")
    @Timed
    public ResponseEntity<VehicleBrandDTO> updateVehicleBrand(@RequestBody VehicleBrandDTO vehicleBrandDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleBrand : {}", vehicleBrandDTO);
        if (vehicleBrandDTO.getId() == null) {
            return createVehicleBrand(vehicleBrandDTO);
        }
        VehicleBrandDTO oldVehicleBrand = vehicleBrandService.findOne(vehicleBrandDTO.getId()); 
        VehicleBrandDTO result = vehicleBrandService.save(vehicleBrandDTO);
        historyService.historysave("Marque",result.getId(),oldVehicleBrand,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-brands : get all the vehicleBrands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleBrands in body
     */
    @GetMapping("/vehicle-brands")
    @Timed
    public List<VehicleBrandDTO> getAllVehicleBrands() {
        log.debug("REST request to get all VehicleBrands");
        return vehicleBrandService.findAll();
    }

    /**
     * GET  /vehicle-brands/:id : get the "id" vehicleBrand.
     *
     * @param id the id of the vehicleBrandDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleBrandDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-brands/{id}")
    @Timed
    public ResponseEntity<VehicleBrandDTO> getVehicleBrand(@PathVariable Long id) {
        log.debug("REST request to get VehicleBrand : {}", id);
        VehicleBrandDTO vehicleBrandDTO = vehicleBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleBrandDTO));
    }

    /**
     * DELETE  /vehicle-brands/:id : delete the "id" vehicleBrand.
     *
     * @param id the id of the vehicleBrandDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-brands/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleBrand(@PathVariable Long id) {
        log.debug("REST request to delete VehicleBrand : {}", id);
        vehicleBrandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-brands?query=:query : search for the vehicleBrand corresponding
     * to the query.
     *
     * @param query the query of the vehicleBrand search
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-brands")
    @Timed
    public List<VehicleBrandDTO> searchVehicleBrands(@RequestParam String query) {
        log.debug("REST request to search VehicleBrands for query {}", query);
        return vehicleBrandService.search(query);
    }

    @GetMapping("/vehicle-brands/label/{label}")
    @Timed
    public ResponseEntity<VehicleBrandDTO>  findByLabel(@PathVariable String label){
        log.debug("REST request to get VehicleBrand by label: {}", label);
        VehicleBrandDTO vehicleBrandDTO = vehicleBrandService.findByLabel(label);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleBrandDTO));
    }

}
