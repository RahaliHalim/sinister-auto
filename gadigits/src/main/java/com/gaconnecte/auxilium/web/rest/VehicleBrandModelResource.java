package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.VehicleBrandModelService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.VehicleBrandModelDTO;
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
 * REST controller for managing VehicleBrandModel.
 */
@RestController
@RequestMapping("/api")
public class VehicleBrandModelResource {

    private final Logger log = LoggerFactory.getLogger(VehicleBrandModelResource.class);

    private static final String ENTITY_NAME = "vehicleBrandModel";

    private final VehicleBrandModelService vehicleBrandModelService;
    
    @Autowired
    private HistoryService historyService;

    public VehicleBrandModelResource(VehicleBrandModelService vehicleBrandModelService) {
        this.vehicleBrandModelService = vehicleBrandModelService;
    }

    /**
     * POST  /vehicle-brand-models : Create a new vehicleBrandModel.
     *
     * @param vehicleBrandModelDTO the vehicleBrandModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleBrandModelDTO, or with status 400 (Bad Request) if the vehicleBrandModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-brand-models")
    @Timed
    public ResponseEntity<VehicleBrandModelDTO> createVehicleBrandModel(@RequestBody VehicleBrandModelDTO vehicleBrandModelDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleBrandModel : {}", vehicleBrandModelDTO);
        if (vehicleBrandModelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicleBrandModel cannot already have an ID")).body(null);
        }
        VehicleBrandModelDTO result = vehicleBrandModelService.save(vehicleBrandModelDTO);
        if(result!= null ) {
         	historyService.historysave("Modele", result.getId(),null, result,0,1, "Création");
            }
        return ResponseEntity.created(new URI("/api/vehicle-brand-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-brand-models : Updates an existing vehicleBrandModel.
     *
     * @param vehicleBrandModelDTO the vehicleBrandModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleBrandModelDTO,
     * or with status 400 (Bad Request) if the vehicleBrandModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleBrandModelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-brand-models")
    @Timed
    public ResponseEntity<VehicleBrandModelDTO> updateVehicleBrandModel(@RequestBody VehicleBrandModelDTO vehicleBrandModelDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleBrandModel : {}", vehicleBrandModelDTO);
        if (vehicleBrandModelDTO.getId() == null) {
            return createVehicleBrandModel(vehicleBrandModelDTO);
        }
        VehicleBrandModelDTO oldVehicleBrand = vehicleBrandModelService.findOne(vehicleBrandModelDTO.getId()); 
        VehicleBrandModelDTO result = vehicleBrandModelService.save(vehicleBrandModelDTO);
        historyService.historysave("Modele",result.getId(),oldVehicleBrand,result,0,0, "Modification");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleBrandModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-brand-models : get all the vehicleBrandModels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleBrandModels in body
     */
    @GetMapping("/vehicle-brand-models")
    @Timed
    public List<VehicleBrandModelDTO> getAllVehicleBrandModels() {
        log.debug("REST request to get all VehicleBrandModels");
        return vehicleBrandModelService.findAll();
    }

    /**
     * GET  /vehicle-brand-models : get all the vehicleBrandModels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleBrandModels in body
     */
    @GetMapping("/vehicle-brand-models/brand/{id}")
    @Timed
    public List<VehicleBrandModelDTO> getAllVehicleBrandModelsByBrand(@PathVariable Long id) {
        log.debug("REST request to get all VehicleBrandModels by brand {}", id);
        return vehicleBrandModelService.findAllByBrand(id);
    }

    /**
     * GET  /vehicle-brand-models/:id : get the "id" vehicleBrandModel.
     *
     * @param id the id of the vehicleBrandModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleBrandModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-brand-models/{id}")
    @Timed
    public ResponseEntity<VehicleBrandModelDTO> getVehicleBrandModel(@PathVariable Long id) {
        log.debug("REST request to get VehicleBrandModel : {}", id);
        VehicleBrandModelDTO vehicleBrandModelDTO = vehicleBrandModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleBrandModelDTO));
    }

    /**
     * DELETE  /vehicle-brand-models/:id : delete the "id" vehicleBrandModel.
     *
     * @param id the id of the vehicleBrandModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-brand-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleBrandModel(@PathVariable Long id) {
        log.debug("REST request to delete VehicleBrandModel : {}", id);
        vehicleBrandModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-brand-models?query=:query : search for the vehicleBrandModel corresponding
     * to the query.
     *
     * @param query the query of the vehicleBrandModel search
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-brand-models")
    @Timed
    public List<VehicleBrandModelDTO> searchVehicleBrandModels(@RequestParam String query) {
        log.debug("REST request to search VehicleBrandModels for query {}", query);
        return vehicleBrandModelService.search(query);
    }

    @PostMapping("/vehicle-brand-models/label")
    @Timed
    public ResponseEntity<VehicleBrandModelDTO>  findByLabel(@RequestBody VehicleBrandModelDTO vehicleBrandModelDTOLabel){
        log.debug("REST request to get VehicleBrandModels by label: {}", vehicleBrandModelDTOLabel.getLabel());
        VehicleBrandModelDTO vehicleBrandModelDTO = vehicleBrandModelService.findByLabel(vehicleBrandModelDTOLabel.getLabel());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleBrandModelDTO));
    }

}
