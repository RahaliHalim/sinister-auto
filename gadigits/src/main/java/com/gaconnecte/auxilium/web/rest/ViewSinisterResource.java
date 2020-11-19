/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewSinisterService;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDT;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.dto.ViewPrestationAssistanceDT;
import com.gaconnecte.auxilium.service.dto.ViewSinisterDT;
import com.gaconnecte.auxilium.service.dto.ViewSinisterDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing report assistance.
 * @author hannibaal
 */
@RestController
@RequestMapping("/api/view")
public class ViewSinisterResource {

    private final Logger log = LoggerFactory.getLogger(ViewSinisterResource.class);

    private final ViewSinisterService viewSinisterService;
    @Autowired
    private LoggerService loggerService;

    public ViewSinisterResource(ViewSinisterService viewSinisterService) {
        this.viewSinisterService = viewSinisterService;
    }

    /**
     * POST  /sinister : get all sinister line.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @GetMapping("/sinister")
    @Timed
    public List<ViewSinisterDTO> getAllSinister() {
        log.debug("REST request to get all sinister line");
        return viewSinisterService.findAll();
    }
    @PostMapping("/sinister/page")
    @Timed
    public ResponseEntity<ViewSinisterDT> getAllViewSinisterPage(@RequestBody DatatablesRequest datatablesRequest) {
        log.debug("REST request to get all sinister Page");
        ViewSinisterDT dt = new ViewSinisterDT();
        dt.setRecordsFiltered(viewSinisterService.getCountSinisterWithFiltter(datatablesRequest.getSearchValue()));
        dt.setRecordsTotal(viewSinisterService.getCountSinister());

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ViewSinisterDTO> dtos = viewSinisterService.findAllSinisterPage(datatablesRequest.getSearchValue(), pr).getContent();
            if(CollectionUtils.isNotEmpty(dtos)) {
                dt.setData(dtos);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        
        } catch (Exception e) {
            log.error("Erreur lors de la pagination");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping(value = "/sinister/export/excel/{search}", consumes = { "multipart/form-data" })//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportSinisterPageToExcel(@RequestPart(name = "search") String search, HttpServletResponse response) {
        log.debug("REST request to export all prestation into excel {}", search);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        if(search.equals("-1")) search = null;
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<ViewSinisterDTO> dtos = viewSinisterService.findAllSinisterPage(search, pr).getContent();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "dossier_sinister" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getSinisterExcelExport(dtos);
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
            //return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        	System.out.println("******************************************************");
        	ecx.printStackTrace();
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
    
    
    /**
     * GET  /prestation/inprogress : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @GetMapping("/prestation/inprogress")
    @Timed
    public ResponseEntity<Set<ViewSinisterPrestationDTO>> findInProgressService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<ViewSinisterPrestationDTO> sinisters = viewSinisterService.findInProgressService();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET  /prestation/closed : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @GetMapping("/prestation/closed")
    @Timed
    public ResponseEntity<Set<ViewSinisterPrestationDTO>> findClosedService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<ViewSinisterPrestationDTO> sinisters = viewSinisterService.findClosedService();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    @PostMapping("/view-prestation/page/{status}")
    @Timed
    public ResponseEntity<ViewPrestationAssistanceDT> getAllViewClosedPrestation(@RequestBody DatatablesRequest datatablesRequest, @PathVariable Long status) {
        log.debug("REST request to get all ViewClosedPrestatIon");
        ViewPrestationAssistanceDT dt = new ViewPrestationAssistanceDT();
        dt.setRecordsFiltered(viewSinisterService.getCountPrestationWithFiltter(datatablesRequest.getSearchValue(),status));
        dt.setRecordsTotal(viewSinisterService.getCountPrestation(status));
        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ViewSinisterPrestationDTO> dtos = viewSinisterService.findAll(datatablesRequest.getSearchValue(), pr,status).getContent();
            if(CollectionUtils.isNotEmpty(dtos)) {
                dt.setData(dtos);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        
        } catch (Exception e) {
            log.error("Erreur lors de la pagination");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    
    
    
    @PostMapping(value = "/view-prestation/export/excel/{status}", consumes = { "multipart/form-data" })//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportPrestationToExcel(@RequestPart(name = "search") String search, HttpServletResponse response, @PathVariable Long status) {
        log.debug("REST request to export all prestation into excel {}", search);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        if(search.equals("-1")) search = null;
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<ViewSinisterPrestationDTO> dtos = viewSinisterService.findAll(search, pr, status).getContent();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Prestations" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getPrestationExcelExport(dtos, status);
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
            //return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        	System.out.println("******************************************************");
        	ecx.printStackTrace();
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
    
    
    /**
     * GET  /prestation/canceled : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @GetMapping("/prestation/canceled")
    @Timed
    public ResponseEntity<Set<ViewSinisterPrestationDTO>> findCanceledService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<ViewSinisterPrestationDTO> sinisters = viewSinisterService.findCanceledService();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * GET  /prestation/not-eligible : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @GetMapping("/prestation/not-eligible")
    @Timed
    public ResponseEntity<Set<ViewSinisterPrestationDTO>> findNotEligibleService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<ViewSinisterPrestationDTO> sinisters = viewSinisterService.findNotEligibleService();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
   /* @PostMapping("/view-prestation-vr/page/{status}")
    @Timed
    public ResponseEntity<ViewPrestationAssistanceDT> getAllViewPrestationVr(@RequestBody DatatablesRequest datatablesRequest, @PathVariable Long status) {
        log.debug("REST request to get all ViewClosedPrestatIon");
        ViewPrestationAssistanceDT dt = new ViewPrestationAssistanceDT();
        dt.setRecordsFiltered(viewSinisterService.getCountPrestationVrWithFiltter(datatablesRequest.getSearchValue(),status));
        dt.setRecordsTotal(viewSinisterService.getCountPrestationVr(status));
        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ViewSinisterPrestationDTO> dtos = viewSinisterService.findAllVr(datatablesRequest.getSearchValue(), pr,status).getContent();
            if(CollectionUtils.isNotEmpty(dtos)) {
                dt.setData(dtos);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        
        } catch (Exception e) {
            log.error("Erreur lors de la pagination");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    */

 /*
   @PostMapping(value = "/view-prestation-vr/export/excel/{status}", consumes = { "multipart/form-data" })//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportPrestationVrToExcel(@RequestPart(name = "search") String search, HttpServletResponse response, @PathVariable Long status) {
        log.debug("REST request to export all prestation into excel {}", search);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        if(search.equals("-1")) search = null;
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<ViewSinisterPrestationDTO> dtos = viewSinisterService.findAllVr(search, pr, status).getContent();
        try {
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Prestations" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getPrestationVrExcelExport(dtos, status);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.flush();
        } catch (Exception ecx) {
        	System.out.println("******************************************************");
        	ecx.printStackTrace();
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

    }   */
    
    

}
