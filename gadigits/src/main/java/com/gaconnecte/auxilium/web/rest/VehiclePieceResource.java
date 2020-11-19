package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.ViewExtractPiece;
import com.gaconnecte.auxilium.repository.ViewExtractPieceRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.VehiclePieceService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.VehiclePieceDTO;
import com.gaconnecte.auxilium.service.dto.ViewExtractPieceDTO;

import com.gaconnecte.auxilium.service.util.ExcelUtil;

import io.github.jhipster.web.util.ResponseUtil;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletResponse;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing VehiclePiece.
 */
@RestController
@RequestMapping("/api")
public class VehiclePieceResource {

    private final Logger log = LoggerFactory.getLogger(VehiclePieceResource.class);

    private static final String ENTITY_NAME = "vehiclePiece";

    private final VehiclePieceService vehiclePieceService;
    @Autowired
    ViewExtractPieceRepository viewExtractPieceRepository;

    public VehiclePieceResource(VehiclePieceService vehiclePieceService) {
        this.vehiclePieceService = vehiclePieceService;
    }

    /**
     * POST  /vehicle-pieces : Create a new vehiclePiece.
     *
     * @param vehiclePieceDTO the vehiclePieceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehiclePieceDTO, or with status 400 (Bad Request) if the vehiclePiece has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-pieces")
    @Timed
    public ResponseEntity<VehiclePieceDTO> createVehiclePiece(@RequestBody VehiclePieceDTO vehiclePieceDTO) throws URISyntaxException {
        log.debug("REST request to save VehiclePiece : {}", vehiclePieceDTO);
        if (vehiclePieceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehiclePiece cannot already have an ID")).body(null);
        }
        VehiclePieceDTO result = vehiclePieceService.save(vehiclePieceDTO);
        return ResponseEntity.created(new URI("/api/vehicle-pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-pieces : Updates an existing vehiclePiece.
     *
     * @param vehiclePieceDTO the vehiclePieceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehiclePieceDTO,
     * or with status 400 (Bad Request) if the vehiclePieceDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehiclePieceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-pieces")
    @Timed
    public ResponseEntity<VehiclePieceDTO> updateVehiclePiece(@RequestBody VehiclePieceDTO vehiclePieceDTO) throws URISyntaxException {
        log.debug("REST request to update VehiclePiece : {}", vehiclePieceDTO);
        if (vehiclePieceDTO.getId() == null) {
            return createVehiclePiece(vehiclePieceDTO);
        }
        VehiclePieceDTO result = vehiclePieceService.save(vehiclePieceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehiclePieceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-pieces : get all the vehiclePieces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehiclePieces in body
     */
    @GetMapping("/vehicle-pieces")
    @Timed
    public List<VehiclePieceDTO> getAllVehiclePieces() {
        log.debug("REST request to get all VehiclePieces");
        return vehiclePieceService.findAll();
    }

    /**
     * GET  /vehicle-pieces/:id : get the "id" vehiclePiece.
     *
     * @param id the id of the vehiclePieceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehiclePieceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-pieces/{id}")
    @Timed
    public ResponseEntity<VehiclePieceDTO> getVehiclePiece(@PathVariable Long id) {
        log.debug("REST request to get VehiclePiece : {}", id);
        VehiclePieceDTO vehiclePieceDTO = vehiclePieceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehiclePieceDTO));
    }

    /**
     * DELETE  /vehicle-pieces/:id : delete the "id" vehiclePiece.
     *
     * @param id the id of the vehiclePieceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-pieces/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehiclePiece(@PathVariable Long id) {
        log.debug("REST request to delete VehiclePiece : {}", id);
        vehiclePieceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-pieces?query=:query : search for the vehiclePiece corresponding
     * to the query.
     *
     * @param query the query of the vehiclePiece search
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-pieces")
    @Timed
    public List<VehiclePieceDTO> searchVehiclePieces(@RequestParam String query) {
        log.debug("REST request to search VehiclePieces for query {}", query);
        return vehiclePieceService.search(query);
    }

    @GetMapping("/vehicle-pieces/type/{id}")
    @Timed
    public List<VehiclePieceDTO> getVehiclePiecesByType(@PathVariable Long id) {
        log.debug("REST request to get a list of Pieces by type", id);
        List<VehiclePieceDTO> pieces = vehiclePieceService.findVehiclePieceByType(id);
        return pieces;
    }

    @GetMapping("/vehicle-pieces/reference/{reference}/{type}")
    @Timed
    public VehiclePieceDTO getVehiclePiecesByReference(@PathVariable String reference, @PathVariable Long type) {
        log.debug("REST request to get a list of Pieces by reference and type", reference);
        VehiclePieceDTO pieces = vehiclePieceService.findVehiclePieceByReferenceAndType(reference, type);
        return pieces;
    }

    @GetMapping("/vehicle-pieces/designation/{designation}/{type}")
    @Timed
    public VehiclePieceDTO getVehiclePiecesByDesignation(@PathVariable String designation, @PathVariable Long type) {
        log.debug("REST request to get a list of Pieces by designation", designation);
        VehiclePieceDTO pieces = vehiclePieceService.findVehiclePieceByDesignationAndType(designation, type);
        return pieces;
    }
    
    @PostMapping(value = "/view_extract_piece/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportExtractPieceToExcel(@RequestBody SearchDTO searchDTO, HttpServletResponse response) {
        log.debug("REST request to export all ViewAssitances into excel {}");
        
       
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<ViewExtractPiece> dtos = viewExtractPieceRepository.findAll(pr).getContent();
        System.out.println("blablabla");
        System.out.println(dtos.size());
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "extract_piece" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getExtractPieceExcelExport(dtos);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            //ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            //response.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8"));
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            //headers.set("Content-Disposition", "attachment; filename=" + fileName);
            //headers.setContentLength(baos.toByteArray().length);
            out.flush();
            //return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);            
        } catch (Exception ecx) {
        	System.out.println("******************************************************");
        	ecx.printStackTrace();
            //return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        } finally {
            if (null != workbook) {
                try {
                    log.info("_____________________________________________________________");
                    workbook.close();
                } catch (IOException eio) {
                    log.error("Error Occurred while exporting to XLS ", eio);
                }
            }
        }

    }    


}
