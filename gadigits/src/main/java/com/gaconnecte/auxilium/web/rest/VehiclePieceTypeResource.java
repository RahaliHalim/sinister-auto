package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.VehiclePieceTypeService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.VehiclePieceTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing VehiclePieceType.
 */
@RestController
@RequestMapping("/api")
public class VehiclePieceTypeResource {

    private final Logger log = LoggerFactory.getLogger(VehiclePieceTypeResource.class);

    private static final String ENTITY_NAME = "vehiclePieceType";

    private final VehiclePieceTypeService vehiclePieceTypeService;

    public VehiclePieceTypeResource(VehiclePieceTypeService vehiclePieceTypeService) {
        this.vehiclePieceTypeService = vehiclePieceTypeService;
    }

    /**
     * POST  /vehicle-piece-types : Create a new vehiclePieceType.
     *
     * @param vehiclePieceTypeDTO the vehiclePieceTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehiclePieceTypeDTO, or with status 400 (Bad Request) if the vehiclePieceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-piece-types")
    @Timed
    public ResponseEntity<VehiclePieceTypeDTO> createVehiclePieceType(@RequestBody VehiclePieceTypeDTO vehiclePieceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save VehiclePieceType : {}", vehiclePieceTypeDTO);
        if (vehiclePieceTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehiclePieceType cannot already have an ID")).body(null);
        }
        VehiclePieceTypeDTO result = vehiclePieceTypeService.save(vehiclePieceTypeDTO);
        return ResponseEntity.created(new URI("/api/vehicle-piece-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-piece-types : Updates an existing vehiclePieceType.
     *
     * @param vehiclePieceTypeDTO the vehiclePieceTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehiclePieceTypeDTO,
     * or with status 400 (Bad Request) if the vehiclePieceTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehiclePieceTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-piece-types")
    @Timed
    public ResponseEntity<VehiclePieceTypeDTO> updateVehiclePieceType(@RequestBody VehiclePieceTypeDTO vehiclePieceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update VehiclePieceType : {}", vehiclePieceTypeDTO);
        if (vehiclePieceTypeDTO.getId() == null) {
            return createVehiclePieceType(vehiclePieceTypeDTO);
        }
        VehiclePieceTypeDTO result = vehiclePieceTypeService.save(vehiclePieceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehiclePieceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-piece-types : get all the vehiclePieceTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehiclePieceTypes in body
     */
    @GetMapping("/vehicle-piece-types")
    @Timed
    public List<VehiclePieceTypeDTO> getAllVehiclePieceTypes() {
        log.debug("REST request to get all VehiclePieceTypes");
        return vehiclePieceTypeService.findAll();
    }

    /**
     * GET  /vehicle-piece-types/:id : get the "id" vehiclePieceType.
     *
     * @param id the id of the vehiclePieceTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehiclePieceTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-piece-types/{id}")
    @Timed
    public ResponseEntity<VehiclePieceTypeDTO> getVehiclePieceType(@PathVariable Long id) {
        log.debug("REST request to get VehiclePieceType : {}", id);
        VehiclePieceTypeDTO vehiclePieceTypeDTO = vehiclePieceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiclePieceTypeDTO));
    }

    /**
     * DELETE  /vehicle-piece-types/:id : delete the "id" vehiclePieceType.
     *
     * @param id the id of the vehiclePieceTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-piece-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehiclePieceType(@PathVariable Long id) {
        log.debug("REST request to delete VehiclePieceType : {}", id);
        vehiclePieceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-piece-types?query=:query : search for the vehiclePieceType corresponding
     * to the query.
     *
     * @param query the query of the vehiclePieceType search
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-piece-types")
    @Timed
    public List<VehiclePieceTypeDTO> searchVehiclePieceTypes(@RequestParam String query) {
        log.debug("REST request to search VehiclePieceTypes for query {}", query);
        return vehiclePieceTypeService.search(query);
    }

}
