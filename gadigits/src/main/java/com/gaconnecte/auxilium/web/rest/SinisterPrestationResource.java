package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.Utils.Utils;
import com.gaconnecte.auxilium.domain.Result;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.ProcessesService;
import com.gaconnecte.auxilium.service.SinisterPrestationService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDT;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDT;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * REST controller for managing Sinister.
 */
@RestController
@RequestMapping("/api/sinister")
public class SinisterPrestationResource {

    private final Logger log = LoggerFactory.getLogger(SinisterPrestationResource.class);

    private static final String ENTITY_NAME = "sinisterPrestation";

    private final SinisterPrestationService sinisterPrestationService;
    
    @Autowired
    private ProcessesService processesService;
    
    @Autowired
    private ContratAssuranceService contratAssuranceService;
    
    private final PartnerService partnerService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private LoggerService loggerService;
    
    @Autowired
	private HistoryService historyService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserExtraService userExtraService;

    Result counter = new Result();

    public SinisterPrestationResource(SinisterPrestationService sinisterPrestationService, PartnerService partnerService) {
        this.sinisterPrestationService = sinisterPrestationService;
        this.partnerService = partnerService;
    }

    /**
     * POST  /prestation : Create a new sinister.
     *
     * @param sinisterPrestationDTO the sinisterPrestationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sinisterPrestationDTO, or with status 400 (Bad Request) if the sinister has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestation")
    @Timed
    public ResponseEntity<SinisterPrestationDTO> createSinisterPrestation(@Valid @RequestBody SinisterPrestationDTO sinisterPrestationDTO) throws URISyntaxException {
        log.debug("REST request to save Sinister : {}", sinisterPrestationDTO);

        try {
            SinisterPrestationDTO result = sinisterPrestationService.save(sinisterPrestationDTO);
            //journalService.journalisationSinister("Creer sinister", SecurityUtils.getCurrentUserLogin(), 218L, sinisterDTO);
            return ResponseEntity.created(new URI("/api/sinister/prestation/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT  /prestation : Updates an existing sinister.
     *
     * @param sinisterPrestationDTO the sinisterPrestationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sinisterPrestationDTO,
     * or with status 400 (Bad Request) if the sinisterPrestationDTO is not valid,
     * or with status 500 (Internal Server Error) if the sinisterPrestationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestation")
    @Timed
    public ResponseEntity<SinisterPrestationDTO> updateSinisterPrestation(@Valid @RequestBody SinisterPrestationDTO sinisterPrestationDTO) {
        log.debug("REST request to update Sinister : {}", sinisterPrestationDTO);
        //journalService.journalisationSinister("Mettre a jour  sinister", SecurityUtils.getCurrentUserLogin(), 219L, sinisterDTO);
        SinisterPrestationDTO oldSinsterPrestation = sinisterPrestationService.findOne(sinisterPrestationDTO.getId());
        SinisterPrestationDTO result = sinisterPrestationService.save(sinisterPrestationDTO);
        if(oldSinsterPrestation != null && oldSinsterPrestation.getStatusId() != null){
    		historyService.historysave("Sinister_Prestation",oldSinsterPrestation.getId(),(Object)oldSinsterPrestation,(Object)result,oldSinsterPrestation.getStatusId().intValue(),result.getStatusId().intValue(), "Prestations");
    		}
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sinisterPrestationDTO.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/prestation/authorized/{vehiculeId}")
    @Timed
    public ResponseEntity<Boolean> canCreatePrestation(@PathVariable Long vehiculeId)
            throws URISyntaxException {

        try {
           
            boolean authorizedIntervNumber = sinisterPrestationService.canSave(vehiculeId);
           
            return ResponseEntity.ok().body(authorizedIntervNumber);
        } catch (Exception e) {
            log.error("Error", e);
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET  /prestation : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @GetMapping("/prestation")
    @Timed
    public ResponseEntity<Set<SinisterPrestationDTO>> getAllSinisterPrestations() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<SinisterPrestationDTO> sinisters = sinisterPrestationService.findAll();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Set<SinisterPrestationDTO>> findInProgressService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<SinisterPrestationDTO> sinisters = sinisterPrestationService.findInProgressService();
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
    public ResponseEntity<Set<SinisterPrestationDTO>> findClosedService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<SinisterPrestationDTO> sinisters = sinisterPrestationService.findClosedService();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Set<SinisterPrestationDTO>> findCanceledService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<SinisterPrestationDTO> sinisters = sinisterPrestationService.findCanceledService();
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
    public ResponseEntity<Set<SinisterPrestationDTO>> findNotEligibleService() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<SinisterPrestationDTO> sinisters = sinisterPrestationService.findNotEligibleService();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * GET  /prestation/report1 : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister in body
     */
    @PostMapping("/prestation/report1")
    @Timed
    public ResponseEntity<Set<ReportFollowUpAssistanceDTO>> findReport1Services(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get a set of sinisters {}", searchDTO);
        try {
            Set<ReportFollowUpAssistanceDTO> sinisters = sinisterPrestationService.findReport1Services(searchDTO);
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/prestation/report1/page")
    @Timed
    public ResponseEntity<ReportFollowUpAssistanceDT> getAllReport1ServicesPage(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get all Report1");
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        UserExtraDTO userExtraDTO = userExtraService.findOne(user.getId());
        if ((userExtraDTO.getProfileId()).equals(25L) || (userExtraDTO.getProfileId()).equals(26L)) {
            searchDTO.setPartnerId(userExtraDTO.getPersonId());
        }
        DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
        ReportFollowUpAssistanceDT dt = new ReportFollowUpAssistanceDT();
        dt.setRecordsFiltered(sinisterPrestationService.getCountReport1ServicesWithFiltter(datatablesRequest.getSearchValue(), searchDTO));
        dt.setRecordsTotal(sinisterPrestationService.getCountReport1Services(searchDTO));

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ReportFollowUpAssistanceDTO> dtos = sinisterPrestationService.findAllReport1ServicesS(datatablesRequest.getSearchValue(), pr, searchDTO).getContent();
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
    
    @PostMapping(value = "/prestation/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportReport1ServicesToExcel(@RequestBody SearchDTO searchDTO, HttpServletResponse response) {
    	 log.debug("REST request to export all Report1Services into excel");
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
         XSSFWorkbook workbook = null;
         HttpHeaders headers = new HttpHeaders();
         //if(search.equals("-1")) search = null;
         
         DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
         
         /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
         final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE); 
         List<ReportFollowUpAssistanceDTO> dtos = sinisterPrestationService.findAllReport1ServicesS(datatablesRequest.getSearchValue(),pr,searchDTO).getContent();
     
         try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Suivi du service assistance" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getReport1ServicesExcelExport(dtos);
            
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


    @RequestMapping(value = "/prestation/report1/download", produces = "application/vnd.ms-excel;charset=UTF-8")
    @Timed
    public void getReport1ServicesFile(@RequestBody SearchDTO searchDTO, final HttpServletResponse response) throws IOException {
        log.debug("REST request to get a set of sinisters file {}", searchDTO);
        /*    
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String excelFileName = "test.xls";
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", excelFileName);
        response.setHeader(headerKey, headerValue);
        Set<SinisterPrestationDTO> sinisters = sinisterPrestationService.findReport1Services(searchDTO);
        //ExcelService builts the workbook using POI
        Workbook workbook = ExcelUtil.getExcelExport(sinisters, new Date());
        

        //The response is stored in an outputstream
        OutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        byte[] byteArray = ((HSSFWorkbook)workbook).getBytes();
        out.write(byteArray);
        out.flush();
        out.close();*/
    }    

    /**
     * GET  /prestation/:id : get the "id" sinister.
     *
     * @param id the id of the sinisterPrestationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sinisterPrestationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prestation/{id}")
    @Timed
    public ResponseEntity<SinisterPrestationDTO> getSinisterPrestation(@PathVariable Long id) {
        log.debug("REST request to get Sinister : {}", id);
        SinisterPrestationDTO sinisterPrestationDTO = sinisterPrestationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterPrestationDTO));
    }
    
    @GetMapping("/prestation/sinister/{id}")
    @Timed
    public ResponseEntity<Set<SinisterPrestationDTO>> findSinisterPrestationBySinisterId(@PathVariable Long id) {
        log.debug("REST request to get Sinister : {}", id);
        Set<SinisterPrestationDTO> sinisterPrestationDTO = sinisterPrestationService.findSinisterPrestationBySinisterId(id);
        return new ResponseEntity<>(sinisterPrestationDTO, HttpStatus.OK);    }
    
   
    /**
     * GET  /prestation/:id/parent : get the "id" sinisterPrestation.
     *
     * @param id the id of the sinisterPrestationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sinisterPrestationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prestation/{id}/parent")
    @Timed
    public ResponseEntity<SinisterDTO> getSinisterFromPrestation(@PathVariable Long id) {
        log.debug("REST request to get Sinister from prestation : {}", id);
        SinisterDTO sinisterDTO = sinisterPrestationService.findSinisterFromPrestation(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterDTO));
    }
    

    
    /**
     * DELETE  /prestation/:id : delete the "id" sinister.
     *
     * @param id the id of the sinisterPrestationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestation/{id}")
    @Timed
    public ResponseEntity<Void> deleteSinisterPrestation(@PathVariable Long id) {
        log.debug("REST request to delete Sinister : {}", id);
        SinisterPrestationDTO sinisterPrestationDTO = sinisterPrestationService.findOne(id);
        //journalService.journalisationSinister("Supprimer sinister", SecurityUtils.getCurrentUserLogin(), 102L , sinisterDTO);
        Boolean delete = sinisterPrestationService.delete(id);

        if (delete) {
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "deleteSinister", "Impossible de supprimer ce sinister car il contient des prestations")).body(null);
        }
    }

}
