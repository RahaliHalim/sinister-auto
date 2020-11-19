/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ReportService;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDT;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing report assistance.
 * @author hannibaal
 */
@RestController
@RequestMapping("/api/report/assistance")
public class ReportAssistanceResource {

    private final Logger log = LoggerFactory.getLogger(ReportAssistanceResource.class);

    private final ReportService reportService;

    public ReportAssistanceResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report-tug-performance")
    @Timed
    public List<ReportTugPerformanceDTO> getAllReportTugPerformanceLine() {
        return reportService.findAll();
    }
    
    /**
     * POST  /report-tug-performance : get all ReportTugPerformance line.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ReportTugPerformance in body
     */
    @PostMapping("/report-tug-performance")
    @Timed
    public List<ReportTugPerformanceDTO> getAllReportTugPerformanceLine(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get all ReportTugPerformance line");
        return reportService.findAll(searchDTO);
    }
    
    @PostMapping("/report-tug-performance/page")
    @Timed
    public ResponseEntity<ReportTugPerformanceDT> getAllReportTugPerformanceLinePageS(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get all ReportTug");
        DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
        ReportTugPerformanceDT dt = new ReportTugPerformanceDT();
        dt.setRecordsFiltered(reportService.getCountReportTugWithFiltter(datatablesRequest.getSearchValue(), searchDTO));
        dt.setRecordsTotal(reportService.getCountReportTug(searchDTO));

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ReportTugPerformanceDTO> dtos = reportService.findAllReportS(datatablesRequest.getSearchValue(), pr, searchDTO).getContent();
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
    
  
     @PostMapping(value = "/report-tug-performance/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
     @Timed
     public void exportReportTugPerformanceToExcel(@RequestBody SearchDTO searchDTO, HttpServletResponse response) {
        	 log.debug("REST request to export all Report1Services into excel");
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
             XSSFWorkbook workbook = null;
             HttpHeaders headers = new HttpHeaders();
             //if(search.equals("-1")) search = null;
             
             DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
             
             /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
             final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE); 
             List<ReportTugPerformanceDTO> dtos = reportService.findAllReportS(datatablesRequest.getSearchValue(),pr,searchDTO).getContent();
         
            
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Performance_du_remorqueur" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getReportTugPerformanceExcelExport(dtos);
            
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
