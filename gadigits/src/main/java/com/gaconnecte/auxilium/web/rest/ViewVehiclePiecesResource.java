/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewVehiclePiecesService;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.ViewVehiclePiecesDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * REST controller for managing vehicle pieces.
 * @author hannibaal
 */
@RestController
@RequestMapping("/api/view")
public class ViewVehiclePiecesResource {

    private final Logger log = LoggerFactory.getLogger(ViewVehiclePiecesResource.class);

    private final ViewVehiclePiecesService viewVehiclePiecesService;
    @Autowired
    private LoggerService loggerService;

    public ViewVehiclePiecesResource(ViewVehiclePiecesService viewVehiclePiecesService) {
        this.viewVehiclePiecesService = viewVehiclePiecesService;
    }

    /**
     * POST  /sinister : get all sinister line.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicle pieces in body
     */
    @GetMapping("/vehicle-pieces")
    @Timed
    public List<ViewVehiclePiecesDTO> getVehiclePieces() {
        log.debug("REST request to get all Vehicle Pieces");
        return viewVehiclePiecesService.findAll();
    }

    @GetMapping("/vehicle-pieces/{id}")
    @Timed
    public List<ViewVehiclePiecesDTO> getVehiclePiecesByType(@PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type", id);
        List<ViewVehiclePiecesDTO> pieces = viewVehiclePiecesService.findVehiclePieceByType(id);
        return pieces;
    }
    @PutMapping(value = "/vehicle-pieces/reference/{id}", consumes = { "multipart/form-data" })
    @Timed
    public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndReference(@RequestPart(name = "reference", required = false) String reference, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and reference", id);
        ViewVehiclePiecesDTO pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndReference(reference, id);
        return pieces;
    }

    @GetMapping("/vehicle-pieces/code/{code}/{id}")
    @Timed
    public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndCode(@PathVariable String code, @PathVariable Long id) {
    	if(code.contains("@")) {
    		code = code.replace("@", "/");
    	}
        log.debug("REST request to get a list of view vehicle Pieces by type and code", id);
        ViewVehiclePiecesDTO pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndCode(code, id);
        return pieces;
    }

    @PutMapping(value = "/vehicle-pieces/typed/reference/{id}", consumes = { "multipart/form-data" })
    @Timed
    public List<ViewVehiclePiecesDTO> getVehiclePiecesByTypeAndTapedReference(@RequestPart(name = "reference", required = false) String reference, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and typed reference", id);
        if(reference != null) {
        	List<ViewVehiclePiecesDTO> pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndTapedReference(reference, id);
            return pieces;
        }
        return new ArrayList<>();
    }
    @PutMapping(value = "/vehicle-pieces/typed/designation/{id}", consumes = { "multipart/form-data" })
    @Timed
    public List<ViewVehiclePiecesDTO> getVehiclePiecesByTypeAndTapedDesignation(@RequestPart(name = "designation", required = false) String designation, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and typed designation", id);
        if(designation != null) {
        	List<ViewVehiclePiecesDTO> pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndTapedDesignation(designation, id);
            return pieces;
        }
        return new ArrayList<>();
        
    }


    @PutMapping(value = "/vehicle-pieces/designation/{id}", consumes = { "multipart/form-data" })
    @Timed
    public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndDesignation(@RequestPart(name = "designation", required = false) String designation, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and designation", id);
        ViewVehiclePiecesDTO pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndDesignation(designation, id);
        return pieces;
    }
    
    @PutMapping(value = "/vehicle-pieces/designation/complete/{id}", consumes = { "multipart/form-data" })
    @Timed
    public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndDesignationAutoComplete(@RequestPart(name = "designation", required = false) String designation, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and designation", id);
        ViewVehiclePiecesDTO pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndDesignationAutoComplete(designation, id);
        return pieces;
    }
    
    @PutMapping(value = "/vehicle-pieces/reference/ref/{id}", consumes = { "multipart/form-data" })
    @Timed
    public ViewVehiclePiecesDTO getVehiclePiecesByTypeAndReferenceAutoComplete(@RequestPart(name = "reference", required = false) String reference, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and reference", id);
        ViewVehiclePiecesDTO pieces = viewVehiclePiecesService.getVehiclePiecesByTypeAndReferenceAutoComplete(reference, id);
        return pieces;
    }
    
    @PutMapping(value = "/vehicle-pieces/designation/design/{id}", consumes = { "multipart/form-data" })
    @Timed
    public Boolean getVehiclePiecesByTypeAndDesignationIsPresent(@RequestPart(name = "designation", required = false) String designation, @PathVariable Long id) {
        log.debug("REST request to get a list of view vehicle Pieces by type and designation", id);
        Boolean pieceIsPresent = viewVehiclePiecesService.getVehiclePiecesByTypeAndDesignationIsPresent(designation, id);
        return pieceIsPresent;
    }
    
    @GetMapping(value = "/view-vehicule-pieces/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportAssitancesToExcel(HttpServletResponse response) {
        log.debug("REST request to export all ViewAssitances into excel {}");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
       
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<ViewVehiclePiecesDTO> dtos = viewVehiclePiecesService.findAllVehiculePieces(pr).getContent();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Assistances" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getVehiculePiece(dtos);
            
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
