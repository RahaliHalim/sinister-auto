package com.gaconnecte.auxilium.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.PriseEnChargeService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.DossiersDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDT;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.ViewSuiviDossiersDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;

import org.springframework.data.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class PriseEnChargeResource {
	
	private final Logger log = LoggerFactory.getLogger(PriseEnChargeResource.class);
	@Autowired
    private UserService userService;
	private final PriseEnChargeService priseEnChargeService;
	@Autowired
    private UserExtraService userExtraService;
	
	public PriseEnChargeResource(PriseEnChargeService priseEnChargeService) {
        this.priseEnChargeService = priseEnChargeService;
    }


    /**
     * GET  /priseencharges/:id : get the "id" priseencharges.
     *
     * @param id the id of the priseEnChargeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priseEnChargeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/getpriseencharges/{id}")
    @Timed
    public ResponseEntity<PriseEnChargeDTO> getPriseencharges(@PathVariable Long id) {
        //log.debug("REST request to get priseEnCharge : {}", id);
    	PriseEnChargeDTO priseEnChargeDTO = priseEnChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(priseEnChargeDTO));
    }
    
    /**
     * GET  /priseencharges : get all the priseencharges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tiers in body
     */
    
    @GetMapping("/getpriseencharges")
    @Timed
    public ResponseEntity<List<PriseEnChargeDTO>> getPriseencharges() {
        //log.debug("REST request to get a set of priseEnCharge");
        try {
            String login = SecurityUtils.getCurrentUserLogin();
            User user = userService.findOneUserByLogin(login);

            List<PriseEnChargeDTO> priseEnChargeDTO = priseEnChargeService.findAll(user.getId());
            return new ResponseEntity<>(priseEnChargeDTO, HttpStatus.OK);
        } catch (Exception e) {    
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/view-prise-en-charge/page")
    @Timed
    public ResponseEntity<PriseEnChargeDT> getAllViewPriseEnCharge(@RequestBody SearchDTO searchDTO) {
    	DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
    	 String login = SecurityUtils.getCurrentUserLogin();
         User user = userService.findOneUserByLogin(login);
        log.debug("REST request to get all PriseEnCharge");
        PriseEnChargeDT dt = new PriseEnChargeDT();
        dt.setRecordsFiltered(priseEnChargeService.getCountPecWithFiltter(user.getId(),datatablesRequest.getSearchValue()));
        dt.setRecordsTotal(priseEnChargeService.getCountPec());

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<PriseEnChargeDTO> dtos = priseEnChargeService.findAll(user.getId(),datatablesRequest.getSearchValue(), pr).getContent();
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
    @PostMapping("/view-prise-en-charge/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportViewPriseEnChargeToExcel(@RequestBody SearchDTO searchDTO, HttpServletResponse response) {
        log.debug("REST request to export all ViewAssitances into excel {}");
    	DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();

        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<PriseEnChargeDTO> dtos = priseEnChargeService.findAll(user.getId(),datatablesRequest.getSearchValue(), pr).getContent();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "ViewPriseEnCharge" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getViewPriseEnChargeExcelExport(dtos);
            
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
    
 /*   @PostMapping("/getpriseencharges")
    @Timed
    public ResponseEntity<Set<PriseEnChargeDTO>> findDossiersServices(@RequestBody RechercheDTO rechercheDTO) {
       // log.debug("REST request to get a set of sinisters {}");
        try {
            Set<PriseEnChargeDTO> priseEnChargeDTO = priseEnChargeService.findPriseEnCharge(rechercheDTO);
            return new ResponseEntity<>(priseEnChargeDTO, HttpStatus.OK);
        } catch (Exception e) {
            //loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
  */  
    
    

    @PostMapping("/getpriseencharges")
    @Timed
    public ResponseEntity<List<PriseEnChargeDTO>> findDossiersServices(@RequestBody SearchDTO searchDTO) {
    	
    	 try {
             String login = SecurityUtils.getCurrentUserLogin();
             User user = userService.findOneUserByLogin(login);

             List<PriseEnChargeDTO> listDTO = priseEnChargeService.Search(searchDTO, user.getId());
         return new ResponseEntity<>(listDTO, HttpStatus.OK);

    	 }catch (Exception e) {    
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

    }

    

    
    
    
    
}
