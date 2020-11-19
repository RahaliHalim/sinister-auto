package com.gaconnecte.auxilium.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.AssitancesServices;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.AssitancesDT;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDT;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.google.protobuf.TextFormat.ParseException;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class AssitancesResource {
	
	private final Logger log = LoggerFactory.getLogger(AssitancesResource.class);
	private final AssitancesServices assitancesServices;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private UserExtraService userExtraService;
	
	
	public AssitancesResource(AssitancesServices assitancesServices) {
        this.assitancesServices = assitancesServices;
    }


    /**
     * GET  /Assitances/:id : get the "id" Assitances.
     *
     * @param id the id of the AssitancesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the AssitancesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/getassitances/{id}")
    @Timed
    public ResponseEntity<AssitancesDTO> getAssitances(@PathVariable Long id) {
        //log.debug("REST request to get UploadDAO : {}", id);
    	AssitancesDTO assitancesDTO = assitancesServices.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assitancesDTO));
    }
    
    /**
     * GET  /upload : get all the Assitances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tiers in body
     */
    
    @GetMapping("/getassitances")
    @Timed
    public ResponseEntity<List<AssitancesDTO>> getAllAssitances() {
        //log.debug("REST request to get a set of assitances");
        try {
        	  String login = SecurityUtils.getCurrentUserLogin();
              User user = userService.findOneUserByLogin(login);

            List<AssitancesDTO> assitancesDTO = assitancesServices.findAll(user.getId());
            log.debug("REST request to get a set of assitances"+assitancesDTO.size());
            return new ResponseEntity<>(assitancesDTO, HttpStatus.OK);
        } catch (Exception e) {    
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * POST  /getassitances : get assistance by rechercheDTO.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assistance in body
     */
   /* @PostMapping("/getassitances")
    @Timed
    public ResponseEntity<Set<AssitancesDTO>> findAssistancesServices(@RequestBody RechercheDTO rechercheDTO) {
        log.debug("REST request to get a set of sinisters {}");
        try {
            Set<AssitancesDTO> assistances = assitancesServices.findAssitances(rechercheDTO);
            return new ResponseEntity<>(assistances, HttpStatus.OK);
        } catch (Exception e) {
            //loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } */
    @PostMapping("/getassitances")
    @Timed
    public ResponseEntity<List<AssitancesDTO>> findAssistancesServices(@RequestBody SearchDTO searchDTO) {
    	 try {
             String login = SecurityUtils.getCurrentUserLogin();
             User user = userService.findOneUserByLogin(login);

             List<AssitancesDTO> listDTO = assitancesServices.Search(searchDTO, user.getId());
         return new ResponseEntity<>(listDTO, HttpStatus.OK);

    	 }catch (Exception e) {    
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }    

    }
     
    @PostMapping("/view-assitances/page")
    @Timed
    public ResponseEntity<AssitancesDT> getAllAssitances(@RequestBody SearchDTO searchDTO) {
    	DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
    	log.debug("REST request to get all ViewAssitances");
    	
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
        
        
        Long partnerId = (userExtraDTO.getProfileId().equals(25L) || userExtraDTO.getProfileId().equals(26L)) == true
        		? userExtraDTO.getPersonId() : 0L;
        Long agencyId = (userExtraDTO.getProfileId().equals(23L) || userExtraDTO.getProfileId().equals(24L)) == true 
        		? userExtraDTO.getPersonId() : 0L;
        
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        boolean vr = ((new String("vr")).equals(searchDTO.getTypeService()) == true) ? true : false;
        String immatriculation = searchDTO.getImmatriculation() != null ? searchDTO.getImmatriculation() : "notimmatriculation" ;
        String reference = searchDTO.getReference()!= null ? searchDTO.getReference() : "notreference" ;
        Long statusId = searchDTO.getStatusId() == null ? 0L : searchDTO.getStatusId();
        
        
        AssitancesDT dt = new AssitancesDT();
        dt.setRecordsFiltered(assitancesServices.getCountAssitancesWithFiltter(datatablesRequest.getSearchValue(),startDate,endDate, immatriculation, reference, statusId, partnerId,agencyId, vr));
        dt.setRecordsTotal(assitancesServices.getCountAssitances(startDate, endDate, immatriculation, reference, statusId, partnerId, agencyId, vr));

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<AssitancesDTO> dtos = assitancesServices.findAll(datatablesRequest.getSearchValue(), startDate, endDate, pr, immatriculation,reference, statusId, partnerId, agencyId,vr).getContent();
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
    @PostMapping("/view-assitances/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportAssitancesToExcel(@RequestBody SearchDTO searchDTO, HttpServletResponse response) {
        log.debug("REST request to export all ViewAssitances into excel {}");
        
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
        
        boolean vr = ((new String("vr")).equals(searchDTO.getTypeService()) == true) ? true : false;
        String search = searchDTO.getDataTablesParameters().getSearchValue();
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        String immatriculation = searchDTO.getImmatriculation() != null ? searchDTO.getImmatriculation() : "notimmatriculation" ;
        String reference = searchDTO.getReference()!= null ? searchDTO.getReference() : "notreference" ;
        Long statusId = searchDTO.getStatusId() == null ? 0L : searchDTO.getStatusId();
        Long partnerId = (userExtraDTO.getProfileId().equals(25L) || userExtraDTO.getProfileId().equals(26L)) == true
        		? userExtraDTO.getPersonId() : 0L;
        Long agencyId = (userExtraDTO.getProfileId().equals(23L) || userExtraDTO.getProfileId().equals(24L)) == true 
        		? userExtraDTO.getPersonId() : 0L;
        		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<AssitancesDTO> dtos = assitancesServices.findAll(search, startDate, endDate, pr, immatriculation,reference, statusId, partnerId, agencyId,vr).getContent();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Assistances" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getAssitancesExcelExport(dtos);
            
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
