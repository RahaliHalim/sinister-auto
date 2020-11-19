package com.gaconnecte.auxilium.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.Utils.Utils;
import com.gaconnecte.auxilium.domain.Result;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.DelegationService;
import com.gaconnecte.auxilium.service.GovernorateService;
import com.gaconnecte.auxilium.service.HistoryPecService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.ProcessesService;
import com.gaconnecte.auxilium.service.RefRemorqueurService;
import com.gaconnecte.auxilium.service.SinisterPrestationService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.TugPricingService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.dto.AssitancesDT;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDT;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.TugPricingDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;
import com.gaconnecte.auxilium.service.referential.RefPackService;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REST controller for managing Sinister.
 */
@RestController
@RequestMapping("/api")
public class SinisterResource {

    private final Logger log = LoggerFactory.getLogger(SinisterResource.class);

    private static final String ENTITY_NAME = "sinister";

    private final SinisterService sinisterService;

    @Autowired
    private SinisterPrestationService sinisterPrestationService;

    @Autowired
    private ProcessesService processesService;

    @Autowired
    private DelegationService delegationService;

    @Autowired
    private RefRemorqueurService refremorqueurService;

    @Autowired
    private ContratAssuranceService contratAssuranceService;

    @Autowired
    private VehiculeAssureService vehiculeAssureService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private RefPackService refPackService;

    private final GovernorateService governorateService;

    @Autowired
    private AssureService assureService;
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private HistoryPecService historyPecService;
    
    @Autowired
    private TugPricingService tugPricingService;

    Result counter = new Result();

    public SinisterResource(SinisterService sinisterService, GovernorateService governorateService) {
        this.sinisterService = sinisterService;
        this.governorateService = governorateService;
    }

    /**
     * POST /sinister : Create a new sinister.
     *
     * @param sinisterDTO the sinisterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new sinisterDTO, or with status 400 (Bad Request) if the sinister has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinister")
    @Timed
    public ResponseEntity<SinisterDTO> createSinister(@Valid @RequestBody SinisterDTO sinisterDTO)
            throws URISyntaxException {
        log.debug("REST request to save Sinister : {}", sinisterDTO);
        try {
            ContratAssuranceDTO contratAssurance = contratAssuranceService.findOne(sinisterDTO.getContractId());
            PartnerDTO client = partnerService.findOne(contratAssurance.getClientId());
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            sinisterDTO.setReference(Utils.getCurrentYear() + fmt.format("/%02d/%05d", client.getId(), getSinisterCounter()));
            sinisterDTO.setPartnerId(client.getId());
            SinisterDTO result = sinisterService.save(sinisterDTO);
            if (result.getSinisterPec() != null) {
               // historyService.historysave("sinister pec", result.getSinisterPec().getId(),null, result, 0, result.getSinisterPec().getStepId().intValue(), "Demande Pec");
                historyPecService.historyPecsave("sinister pec", result.getSinisterPec().getId(),null, result, 0, result.getSinisterPec().getStepId().intValue(), "Demande Pec");

            
            }
            if (result.getPrestations().size() > 0) {
                for (SinisterPrestationDTO resultPrestationDTO : result.getPrestations()) {
                    historyService.historysave("Sinister_Prestation", resultPrestationDTO.getId(),null, resultPrestationDTO, 0, resultPrestationDTO.getStatusId().intValue(), "Prestations");
                }

            }
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
        } catch (Exception e) {
            log.error("Error", e);
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST /sinister/authorized : Create a new sinister if not authorized.
     *
     * @param sinisterDTO the sinisterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new sinisterDTO, or with status 400 (Bad Request) if the sinister has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinister/authorized")
    @Timed
    public ResponseEntity<SinisterDTO> canCreateSinister(@Valid @RequestBody SinisterDTO sinisterDTO)
            throws URISyntaxException {
        log.debug("REST request to authorise save Sinister : {}", sinisterDTO);

        try {
            ContratAssuranceDTO contratAssurance = contratAssuranceService.findOne(sinisterDTO.getContractId());
            VehiculeAssureDTO vehicle = vehiculeAssureService.findOne(sinisterDTO.getVehicleId());

            if (sinisterDTO.getId() == null) {
                PartnerDTO client = partnerService.findOne(contratAssurance.getClientId());
                StringBuilder sbuf = new StringBuilder();
                Formatter fmt = new Formatter(sbuf);

                sinisterDTO.setReference(Utils.getCurrentYear()
                        + fmt.format("/%02d/%05d", client.getId(), getSinisterCounter()));
                sinisterDTO.setPartnerId(client.getId());
            }

            SinisterDTO result = sinisterService.canSave(sinisterDTO, vehicle.getPackId());
            // journalService.journalisationSinister("Creer sinister",
            // SecurityUtils.getCurrentUserLogin(), 218L, sinisterDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.error("Error", e);
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST /sinister/duplicated : Create a new sinister if not authorized.
     *
     * @param sinisterDTO the sinisterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new sinisterDTO, or with status 400 (Bad Request) if the sinister has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinister/duplicated")
    @Timed
    public ResponseEntity<SinisterDTO> isDuplicatedSinister(@Valid @RequestBody SinisterDTO sinisterDTO)
            throws URISyntaxException {
        log.debug("REST request to test duplicated sinister : {}", sinisterDTO);

        try {
            SinisterDTO result = sinisterService.isDuplicatedSinister(sinisterDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT /sinister : Updates an existing sinister.
     *
     * @param sinisterDTO the sinisterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * sinisterDTO, or with status 400 (Bad Request) if the sinisterDTO is not
     * valid, or with status 500 (Internal Server Error) if the sinisterDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sinister")
    @Timed
    public ResponseEntity<SinisterDTO> updateSinister(@Valid @RequestBody SinisterDTO sinisterDTO) {
        log.debug("REST request to update Sinister : {}", sinisterDTO);
        SinisterDTO oldSinster = sinisterService.findOne(sinisterDTO.getId());
        SinisterPecDTO oldSinsterPec = oldSinster.getSinisterPec();
        List<SinisterPrestationDTO> oldListesPrestations = oldSinster.getPrestations();
        SinisterDTO result = sinisterService.save(sinisterDTO);
        if (result.getPrestations().size() > 0) {
            for (SinisterPrestationDTO resultPrestationDTO : result.getPrestations()) {
                for (SinisterPrestationDTO sinisterPrestationDTO : oldListesPrestations) {
                    if (resultPrestationDTO.getId().equals(sinisterPrestationDTO.getId())) {
                        historyService.historysave("Sinister_Prestation", sinisterPrestationDTO.getId(), sinisterPrestationDTO, resultPrestationDTO, sinisterPrestationDTO.getStatusId().intValue(), resultPrestationDTO.getStatusId().intValue(), "Prestations");
                    }
                }
            }

        }
        for (SinisterPrestationDTO resultDTO : result.getPrestations()) {
            //HistoryDTO historyDTO = new HistoryDTO();
            HistoryDTO historyDTO = historyService.findHistoryByEntityAndAssistance(resultDTO.getId(), "Sinister_Prestation", "Création Prestation");
            if (historyDTO == null) {
                historyService.historysave("Sinister_Prestation", resultDTO.getId(), null, resultDTO, 0, resultDTO.getStatusId().intValue(), "Prestations");
            }
        }
        if (result.getSinisterPec() != null && oldSinsterPec != null) {
            if (result.getSinisterPec().getReasonCancelAffectedRepId() != null) {
                if (!result.getSinisterPec().getReasonCancelAffectedRepId().equals(oldSinsterPec.getReasonCancelAffectedRepId())) {
                   // historyService.historysave("sinister pec", result.getSinisterPec().getId(), oldSinsterPec, result.getSinisterPec(), oldSinsterPec.getStepId().intValue(), result.getSinisterPec().getStepId().intValue(), "annulation_affectation_reparateur");
                	historyPecService.historyPecsave("sinister pec", result.getSinisterPec().getId(), oldSinsterPec, result.getSinisterPec(), oldSinsterPec.getStepId().intValue(), result.getSinisterPec().getStepId().intValue(), "annulation_affectation_reparateur");
                }else {
                    //historyService.historysave("sinister pec", result.getSinisterPec().getId(), oldSinsterPec, result.getSinisterPec(), oldSinsterPec.getStepId().intValue(), result.getSinisterPec().getStepId().intValue(), "PEC");

                	historyPecService.historyPecsave("sinister pec", result.getSinisterPec().getId(), oldSinsterPec, result.getSinisterPec(), oldSinsterPec.getStepId().intValue(), result.getSinisterPec().getStepId().intValue(), "PEC");
                }
            }else {
                //historyService.historysave("sinister pec", result.getSinisterPec().getId(), oldSinsterPec, result.getSinisterPec(), oldSinsterPec.getStepId().intValue(), result.getSinisterPec().getStepId().intValue(), "PEC");
                historyPecService.historyPecsave("sinister pec", result.getSinisterPec().getId(), oldSinsterPec, result.getSinisterPec(), oldSinsterPec.getStepId().intValue(), result.getSinisterPec().getStepId().intValue(), "PEC");

            
            }
        }
        if (oldSinsterPec == null && result.getSinisterPec() != null) {
        	//historyService.historysave("sinister pec", result.getSinisterPec().getId(),null, result, 0, result.getSinisterPec().getStepId().intValue(), "Demande Pec");

        	historyPecService.historyPecsave("sinister pec", result.getSinisterPec().getId(),null, result, 0, result.getSinisterPec().getStepId().intValue(), "Demande Pec");
        }
        if (result != null) {
            historyService.historysave("sinister", result.getId(), oldSinster, result, oldSinster.getStatusId().intValue(), result.getStatusId().intValue(), "sinister");
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET /sinister : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister
     * in body
     */
    @GetMapping("/sinister")
    @Timed
    public ResponseEntity<Set<SinisterDTO>> getAllSinisters() {
        log.debug("REST request to get a set of sinisters");
        try {
            Set<SinisterDTO> sinisters = sinisterService.findAll();
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister/vehicleregistration/{vehicleRegistration}")
    @Timed
    public ResponseEntity<Set<SinisterDTO>> getSinistersByVehicleRegistration(@PathVariable String vehicleRegistration) {
        log.debug("REST request to get a set of sinisters by vehicle registration");
        try {
            Set<SinisterDTO> sinisters = sinisterService.findByVehicleRegistration(vehicleRegistration);
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister/viewvehicleregistration/{vehiculeId}")
    @Timed
    public ResponseEntity<Set<ViewSinisterPrestationDTO>> getSinisterPrestationsByVehicleRegistration(@PathVariable Long vehiculeId) {
        log.debug("REST request to get a set of ViewSinisterPrestations by vehicle registration");
        try {
            Set<ViewSinisterPrestationDTO> sinisterPrestations = sinisterService.findViewPrestationsByVehicleRegistration(vehiculeId);
            return new ResponseEntity<>(sinisterPrestations, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/sinisterPec/viewvehicleregistration/{vehiculeId}")
    @Timed
    public ResponseEntity<Set<ViewSinisterPecDTO>> getSinisterPecByVehicleRegistration(@PathVariable Long vehiculeId) {
        log.debug("REST request to get a set of ViewSinisterPrestations by vehicle registration");
        try {
            Set<ViewSinisterPecDTO> sinisterPec = sinisterService.findViewPecByVehicleRegistration(vehiculeId);
            return new ResponseEntity<>(sinisterPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * GET /sinister/report2 : get all the sinister.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sinister
     * in body
     */
    @PostMapping("/sinister/report2")
    @Timed
    public ResponseEntity<Set<SinisterDTO>> findReport2Services(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get a set of sinisters {}", searchDTO);
        try {
            Set<SinisterDTO> sinisters = sinisterService.findReport2Sinisters(searchDTO);
            return new ResponseEntity<>(sinisters, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/sinister/report2/page")
    @Timed
    public ResponseEntity<ReportFrequencyRateDT> findReport2ServicesPage(@RequestBody SearchDTO searchDTO) {
        log.debug("REST request to get all sinisters");
        DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();

        ReportFrequencyRateDT dt = new ReportFrequencyRateDT();
        dt.setRecordsFiltered(sinisterService.getCountReport2ServicesWithFiltter(datatablesRequest.getSearchValue(),searchDTO));
        dt.setRecordsTotal(sinisterService.getCountReport2Services(searchDTO));
       
        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ReportFrequencyRateDTO> dtos = sinisterService.findAllReport2Services(datatablesRequest.getSearchValue(), pr,searchDTO).getContent();
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
    @PostMapping(value = "/sinister/report2/export/excel")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportReport2ServiceToExcel(@RequestBody SearchDTO searchDTO, HttpServletResponse response) {
       	 log.debug("REST request to export all Report1Services into excel");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
            XSSFWorkbook workbook = null;
            HttpHeaders headers = new HttpHeaders();
            //if(search.equals("-1")) search = null;
            
            DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
            
            /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
            final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE); 
            List<ReportFrequencyRateDTO> dtos = sinisterService.findAllReport2Services(datatablesRequest.getSearchValue(),pr,searchDTO).getContent();
        
           
       try {
           /* Logic to Export Excel */
           LocalDateTime now = LocalDateTime.now();
           String fileName = "Taux_de_fréquence" + now.format(formatter) + ".xlsx";
               
           workbook = (XSSFWorkbook) ExcelUtil.getReport2ServiceExcelExport(dtos);
           
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
    
 
    /**
     * GET /sinister/:id : get the "id" sinister.
     *
     * @param id the id of the sinisterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * sinisterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sinister/{id}")
    @Timed
    public ResponseEntity<SinisterDTO> getSinister(@PathVariable Long id) {
        log.debug("REST request to get Sinister : {}", id);
        SinisterDTO sinisterDTO = sinisterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterDTO));
    }

    /**
     * DELETE /sinister/:id : delete the "id" sinister.
     *
     * @param id the id of the sinisterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sinister/{id}")
    @Timed
    public ResponseEntity<Void> deleteSinister(@PathVariable Long id) {
        log.debug("REST request to delete Sinister : {}", id);
        SinisterDTO sinisterDTO = sinisterService.findOne(id);
        //journalService.journalisationSinister("Supprimer sinister", SecurityUtils.getCurrentUserLogin(), 102L , sinisterDTO);
        Boolean delete = sinisterService.delete(id);

        if (delete) {
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "deleteSinister", "Impossible de supprimer ce sinister car il contient des prestations")).body(null);
        }
    }

    @PostMapping("/sinisters1")
    //@ResponseBody
    //@Timed
    public ResponseEntity<SinisterDTO> createSinisterFromMobileApplication(@RequestParam(name = "sinisterDTO") String sinisterDTO4) throws URISyntaxException {
        log.debug("REST request to save Sinister : {}", sinisterDTO4);

        try {
            JSONObject sinistreDTO1 = new JSONObject(String.valueOf(sinisterDTO4));
            Long contratId = sinistreDTO1.getLong("contractId");
            Long vehiculeId = sinistreDTO1.getLong("vehiculeId");
            String immatriculation = (String) sinistreDTO1.get("immatriculation");
            Long statusId = (long) 1;
            LocalDate date1 = LocalDate.now();
            SinisterDTO sinister = sinisterService.findByVehicleIdAndIncidentDateAndStatus(vehiculeId, date1, statusId);

            if (sinister != null) {

                Integer passengerCount = sinistreDTO1.getInt("nbrPassgrs");
                sinister.setPassengerCount(passengerCount);
                String phone = (String) sinistreDTO1.get("phone");
                sinister.setPhone(phone);
                String destination = (String) sinistreDTO1.get("destination");

                String villeDestination = (String) sinistreDTO1.get("villeDestination");
                String nameLocation = (String) sinistreDTO1.get("nameLocation");
                String nameSubLocation = (String) sinistreDTO1.get("nameSubLocation");
                GovernorateDTO sysGouvernoratDTO = new GovernorateDTO();
                GovernorateDTO sysGouvernoratDTO1 = new GovernorateDTO();
                DelegationDTO refDelegationDTO = new DelegationDTO();
                GovernorateDTO sysGouvernoratDTOGaGeo = new GovernorateDTO();
                SinisterPrestationDTO sinisterPrestationDTO = new SinisterPrestationDTO();
                refDelegationDTO = delegationService.findByName(villeDestination);
                sysGouvernoratDTO1 = governorateService.findByName(destination);
                sysGouvernoratDTO = governorateService.findByName(nameLocation);
                if (sysGouvernoratDTO != null) {
                    sinisterPrestationDTO.setIncidentGovernorateId(sysGouvernoratDTO.getId());
                } else {
                    sysGouvernoratDTOGaGeo.setLabel(nameLocation);
                    sysGouvernoratDTOGaGeo.setAddedGageo(true);
                    governorateService.save(sysGouvernoratDTOGaGeo);
                    sinisterPrestationDTO.setIncidentGovernorateId(sysGouvernoratDTOGaGeo.getId());
                }

                sinisterPrestationDTO.setDestinationGovernorateId(sysGouvernoratDTO1.getId());
                sinisterPrestationDTO.setDestinationLocationId(refDelegationDTO.getId());
                sinisterPrestationDTO.setVehicleRegistration(immatriculation);
                Long typeServ = (long) 2;
                sinisterPrestationDTO.setServiceTypeId(typeServ);
                Long StatusId1 = (long) 1;
                sinisterPrestationDTO.setStatusId(StatusId1);

                DelegationDTO refDelegationDTO1 = new DelegationDTO();
                DelegationDTO refDelegationDTOGaGeo = new DelegationDTO();
                refDelegationDTO1 = delegationService.findByName(nameSubLocation);
                if (refDelegationDTO1 != null) {
                    sinisterPrestationDTO.setIncidentLocationId(refDelegationDTO1.getId());
                } else {
                    refDelegationDTOGaGeo.setLabel(nameSubLocation);
                    refDelegationDTOGaGeo.setAddedGageo(true);
                    delegationService.save(refDelegationDTOGaGeo);
                    sinisterPrestationDTO.setIncidentLocationId(refDelegationDTOGaGeo.getId());
                }
                sinisterPrestationDTO.setGageo(true);
                sinisterPrestationDTO.setInsuredName((String) sinistreDTO1.get("conducteurFirstName"));
                sinisterPrestationDTO.setIncidentDate(date1);
                sinisterPrestationDTO.setSinister_id(sinister.getId());
                sinisterPrestationDTO.setSinisterId(sinister.getId());
                Double numb = Double.valueOf(0);
                sinisterPrestationDTO.setMileage(numb);
                sinisterPrestationDTO.setVatRate(numb);
                sinisterPrestationDTO.setPriceHt(numb);
                sinisterPrestationDTO.setPriceTtc(numb);
                List<SinisterPrestationDTO> listPrestations = new ArrayList<>();
                listPrestations = sinister.getPrestations();
                sinister.setVehicleRegistration(immatriculation);
                listPrestations.add(sinisterPrestationDTO);
                sinister.setVehicleId(sinistreDTO1.getLong("vehiculeId"));
                sinister.setPrestations(listPrestations);
                SinisterDTO result = sinisterService.save(sinister);
                //journalService.journalisationSinister("Creer sinister", SecurityUtils.getCurrentUserLogin(), 218L, sinisterDTO);
                return ResponseEntity.created(new URI("/api/sinister/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                        .body(result);

            } else {
                SinisterDTO sinisterDTO = new SinisterDTO();
                ContratAssuranceDTO contratAssurance = contratAssuranceService.findOne(contratId);
                PartnerDTO client = partnerService.findOne(contratAssurance.getClientId());
                StringBuilder sbuf = new StringBuilder();
                Formatter fmt = new Formatter(sbuf);
                sinisterDTO.setReference(Utils.getCurrentYear() + fmt.format("/%02d/%05d", client.getId(), getSinisterCounter()));
                sinisterDTO.setPartnerId(client.getId());
                String phone = (String) sinistreDTO1.get("phone");
                sinisterDTO.setPhone(phone);
                sinisterDTO.setConductorFirstName((String) sinistreDTO1.get("conducteurFirstName"));
                sinisterDTO.setConductorLastName((String) sinistreDTO1.get("conducteurLastName"));
                String type = (String) sinistreDTO1.get("nature");
                if (type.equals("A")) {
                    sinisterDTO.setNature("ACCIDENT");
                } else if (type.equals("P")) {
                    sinisterDTO.setNature("PANNE");
                }
                LocalDate date = LocalDate.now();
                sinisterDTO.setIncidentDate(date);
                sinisterDTO.setContractId(sinistreDTO1.getLong("contractId"));
                sinisterDTO.setVehicleId(sinistreDTO1.getLong("vehiculeId"));
                sinisterDTO.setInsuredId(sinistreDTO1.getLong("id"));
                Integer passengerCount = sinistreDTO1.getInt("nbrPassgrs");
                sinisterDTO.setPassengerCount(passengerCount);
                String destination = (String) sinistreDTO1.get("destination");
                String villeDestination = (String) sinistreDTO1.get("villeDestination");
                String nameLocation = (String) sinistreDTO1.get("nameLocation");
                String nameSubLocation = (String) sinistreDTO1.get("nameSubLocation");
                GovernorateDTO sysGouvernoratDTO = new GovernorateDTO();
                GovernorateDTO sysGouvernoratDTO1 = new GovernorateDTO();
                GovernorateDTO sysGouvernoratGaGeo = new GovernorateDTO();
                DelegationDTO refDelegationDTOGaGEO = new DelegationDTO();
                DelegationDTO refDelegationVilleDestination = new DelegationDTO();
                sysGouvernoratDTO1 = governorateService.findByName(destination);
                refDelegationVilleDestination = delegationService.findByName(villeDestination);
                sysGouvernoratDTO = governorateService.findByName(nameLocation);
                if (sysGouvernoratDTO != null) {
                    sinisterDTO.setGovernorateId(sysGouvernoratDTO.getId());
                } else {
                    sysGouvernoratGaGeo.setLabel(nameLocation);
                    sysGouvernoratGaGeo.setAddedGageo(true);
                    governorateService.save(sysGouvernoratGaGeo);
                    sinisterDTO.setGovernorateId(sysGouvernoratGaGeo.getId());
                }
                DelegationDTO refDelegationDTO1 = new DelegationDTO();
                refDelegationDTO1 = delegationService.findByName(nameSubLocation);
                if (refDelegationDTO1 != null) {
                    sinisterDTO.setLocationId(refDelegationDTO1.getId());
                } else {
                    refDelegationDTOGaGEO.setLabel(nameSubLocation);
                    refDelegationDTOGaGEO.setAddedGageo(true);
                    delegationService.save(refDelegationDTOGaGEO);
                    sinisterDTO.setLocationId(refDelegationDTOGaGEO.getId());
                }
                sinisterDTO.setVehicleRegistration(immatriculation);

                //Cration Prestation
                SinisterPrestationDTO sinisterPrestationDTO = new SinisterPrestationDTO();
                sinisterPrestationDTO.setDestinationGovernorateId(sysGouvernoratDTO1.getId());
                sinisterPrestationDTO.setDestinationLocationId(refDelegationVilleDestination.getId());

                sinisterPrestationDTO.setVehicleRegistration(immatriculation);
                Long typeServ = (long) 2;
                sinisterPrestationDTO.setServiceTypeId(typeServ);
                Long StatusId1 = (long) 1;
                sinisterPrestationDTO.setStatusId(StatusId1);
                sinisterPrestationDTO.setGageo(true);
                sinisterPrestationDTO.setSinister_id(sinisterDTO.getId());
                sinisterPrestationDTO.setSinisterId(sinisterDTO.getId());
                if (sysGouvernoratDTO != null) {
                    sinisterPrestationDTO.setIncidentGovernorateId(sysGouvernoratDTO.getId());
                } else {
                    sinisterPrestationDTO.setIncidentGovernorateId(sysGouvernoratGaGeo.getId());
                }
                if (refDelegationDTO1 != null) {
                    sinisterPrestationDTO.setIncidentLocationId(refDelegationDTO1.getId());
                } else {
                    sinisterPrestationDTO.setIncidentLocationId(refDelegationDTOGaGEO.getId());
                }
                sinisterPrestationDTO.setInsuredName((String) sinistreDTO1.get("conducteurFirstName"));
                sinisterPrestationDTO.setIncidentDate(date);
                Double numb = Double.valueOf(0);
                sinisterPrestationDTO.setMileage(numb);
                sinisterPrestationDTO.setVatRate(numb);
                sinisterPrestationDTO.setPriceHt(numb);
                sinisterPrestationDTO.setPriceTtc(numb);
                List<SinisterPrestationDTO> listPrestations = new ArrayList<>();
                listPrestations.add(sinisterPrestationDTO);
                sinisterDTO.setPrestations(listPrestations);
                Long StatusId = (long) 1;
                sinisterDTO.setStatusId(StatusId);
                SinisterDTO result = sinisterService.save(sinisterDTO);
                return ResponseEntity.created(new URI("/api/sinister/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                        .body(result);
            }

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/service-rmqs/affect")
    //@Timed
    public ResponseEntity<SinisterDTO> affectRmq(@RequestParam(name = "assr") String assr, @RequestParam(name = "remq") String remq, @RequestParam(name = "prest") String prest) {
        try {

            JSONObject remorqueur = new JSONObject(String.valueOf(remq));
            JSONObject assure = new JSONObject(String.valueOf(assr));
            JSONObject prestation = new JSONObject(String.valueOf(prest));
            Long contratId = assure.getLong("contratAssuranceId");
            Long remorqueurId = remorqueur.getLong("idrmq1");
            Long idPrest = prestation.getLong("idPrest");
            Long statusId = 1L;
            RefRemorqueurDTO refRemorqueurDTO = new RefRemorqueurDTO();
            refRemorqueurDTO = refremorqueurService.findOne(remorqueurId);
            Long serviceTypeId = 2L;
            SinisterDTO sinisterDTO1 = new SinisterDTO();
            SinisterPrestationDTO sinisterPrestationDTO = sinisterPrestationService.findOne(idPrest);

            sinisterPrestationDTO.setAffectedTugId(remorqueurId);
            sinisterPrestationDTO.setAffectedTugLabel(refRemorqueurDTO.getSocieteRaisonSociale());
            sinisterPrestationService.save(sinisterPrestationDTO);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterDTO1));
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clotPrest")
    @Timed
    public ResponseEntity<SinisterPrestationDTO> clotPres(@RequestParam(name = "prest") String prest) throws URISyntaxException {
        try {
            JSONObject loc = new JSONObject(String.valueOf(prest));
            Long idPrest = loc.getLong("idPrest");
            Long idRmq = loc.getLong("idRemq");
            Double mileage = (Double) loc.getDouble("mileage");
            double kilometrage = mileage < 1 ? 1d : mileage;
            Double prixGrutage = (Double) loc.getDouble("prixGrutage");

            //Double kilometrage = refremorqueurService.distanceAssureRemorqueur(latitudeAssr, longitudeAssr, latitudeRmq, longitudeRmq);
            Double vatRate = 0D;
            Double priceHt = 0D;
            Double priceTtc = 0D;

            SinisterPrestationDTO sinisterPrestationDTO = sinisterPrestationService.findOne(idPrest);

            if (idRmq != null) {
                RefRemorqueurDTO rmq = refremorqueurService.findOne(idRmq);
                TugPricingDTO tugPricing = tugPricingService.findBy(idRmq);
                if (rmq != null && tugPricing != null) {
                    Double priceIncreaseRate = 1D;

                    if (sinisterPrestationDTO.isHolidays()) {
                        priceIncreaseRate *= 1.5;
                    }
                    if (sinisterPrestationDTO.isNight()) {
                        priceIncreaseRate *= 1.5;
                    }
                    if (sinisterPrestationDTO.isHeavyWeights()) {
                        priceIncreaseRate *= 1.5;
                    }
                    if (sinisterPrestationDTO.isHalfPremium()) {
                        priceIncreaseRate *= 1.25;
                    }

                    if (sinisterPrestationDTO.getServiceTypeId() == 2) {

                        Double mileagePart1 = kilometrage >= 60d ? 60d : kilometrage;
                        Double mileagePart2 = kilometrage > 60d ? (kilometrage >= 300d ? 240d : kilometrage - 60d) : 0d;
                        Double mileagePart3 = kilometrage > 300d ? kilometrage - 300d : 0d;
                        priceHt = ((mileagePart1 > 0d ? mileagePart1 * tugPricing.getPriceUrbanPlanService() : 0d)
                                + mileagePart2 * tugPricing.getPriceKlmShortDistance() + mileagePart3 * tugPricing.getPriceKlmLongDistance());

                        priceHt = priceHt * priceIncreaseRate;
                        priceTtc = priceHt * (1 + rmq.getVatRate() / 100);
                        vatRate = rmq.getVatRate();

                    } else if (sinisterPrestationDTO.getServiceTypeId() == 3) {

                        priceHt = tugPricing.getPriceReparation() * priceIncreaseRate;
                        vatRate = rmq.getVatRate();
                        priceTtc = priceHt * (1 + vatRate / 100);

                    } else if (sinisterPrestationDTO.getServiceTypeId() == 4) {

                        priceHt = tugPricing.getPriceUrbanMobility() * priceIncreaseRate;
                        vatRate = rmq.getVatRate();
                        priceTtc = priceHt * (1 + vatRate / 100);

                    } else if (sinisterPrestationDTO.getServiceTypeId() == 6) {

                        priceHt = tugPricing.getPriceExtraction();
                        vatRate = rmq.getVatRate();
                        vatRate = (double) (vatRate != 0 ? 19 : 0);
                        priceTtc = priceHt * (1 + vatRate / 100);

                    } else if (sinisterPrestationDTO.getServiceTypeId() == 5) {

                        priceHt = prixGrutage;
                        vatRate = rmq.getVatRate();
                        priceTtc = priceHt * (1 + vatRate / 100);

                    }

                }
            }
            sinisterPrestationDTO.setMileage(kilometrage);
            sinisterPrestationDTO.setVatRate(vatRate);
            sinisterPrestationDTO.setPriceHt(priceHt);
            sinisterPrestationDTO.setPriceTtc(priceTtc);
            LocalDate date = LocalDate.now();
            LocalDateTime date1 = LocalDateTime.now();
            sinisterPrestationDTO.setClosureDate(date1);
            sinisterPrestationDTO.setInsuredArrivalDate(date1);

            Long statutId = 3L;
            sinisterPrestationDTO.setStatusId(statutId);
            SinisterDTO sinisterDTO = sinisterService.findOne(sinisterPrestationDTO.getSinisterId());
            sinisterDTO.setStatusId(statutId);

            sinisterPrestationService.save(sinisterPrestationDTO);

            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterPrestationDTO));

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * get row number of sinister in database
     *
     * @return a value counter
     */
    private Long getSinisterCounter() {
        long occ = sinisterService.countSinister();
        occ++;
        return occ;
    }

    @PostMapping("/changeTypeDeService")
    @Timed
    public ResponseEntity<SinisterPrestationDTO> changeTypeDeService(@RequestParam(name = "prest") String prest) throws URISyntaxException {

        try {
            JSONObject loc = new JSONObject(String.valueOf(prest));
            Long idPrest = loc.getLong("idPrest");
            Long serviceRemorquage = loc.getLong("serviceRemorquage");
            Long serviceGrutage = loc.getLong("serviceGrutage");
            Long serviceDepanage = loc.getLong("serviceDepanage");
            Long serviceDeplacement = loc.getLong("serviceDeplacement");
            Long serviceExtraction = loc.getLong("serviceExtraction");
            Boolean nuit = loc.getBoolean("nuit");
            Boolean feriee = loc.getBoolean("feriee");
            Boolean demiMajore = loc.getBoolean("demiMajore");
            Boolean poidsLourd = loc.getBoolean("poidsLourd");

            Long servicePrest;
            String servicePrestLabel;
            if (serviceRemorquage != 0) {
                servicePrest = serviceRemorquage;
                servicePrestLabel = "Service remorquage";
            } else if (serviceGrutage != 0) {
                servicePrest = serviceGrutage;
                servicePrestLabel = "Service grutage";
            } else if (serviceDepanage != 0) {
                servicePrest = serviceDepanage;
                servicePrestLabel = "Service Dépannage";
            } else if (serviceDeplacement != 0) {
                servicePrest = serviceDeplacement;
                servicePrestLabel = "Service déplacement";
            } else {
                servicePrest = serviceExtraction;
                servicePrestLabel = "Service Extraction";
            }
            SinisterPrestationDTO sinisterPrestationDTO = new SinisterPrestationDTO();
            sinisterPrestationDTO = sinisterPrestationService.findOne(idPrest);

            sinisterPrestationDTO.setServiceTypeId(servicePrest);
            sinisterPrestationDTO.setServiceTypeLabel(servicePrestLabel);
            sinisterPrestationDTO.setHeavyWeights(poidsLourd);
            sinisterPrestationDTO.setHolidays(feriee);
            sinisterPrestationDTO.setNight(nuit);
            sinisterPrestationDTO.setHalfPremium(demiMajore);
            sinisterPrestationService.save(sinisterPrestationDTO);

            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterPrestationDTO));

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/arriveRmqLieuSinister")
    @Timed
    public ResponseEntity<SinisterPrestationDTO> arriveRmqLieuSinister(@RequestParam(name = "prest") String prest) throws URISyntaxException {

        try {
            JSONObject loc = new JSONObject(String.valueOf(prest));
            Long idPrest = loc.getLong("idPrest");

            SinisterPrestationDTO sinisterPrestationDTO = new SinisterPrestationDTO();
            sinisterPrestationDTO = sinisterPrestationService.findOne(idPrest);
            LocalDateTime date = LocalDateTime.now();
            sinisterPrestationDTO.setTugArrivalDate(date);
            sinisterPrestationService.save(sinisterPrestationDTO);

            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterPrestationDTO));

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/clotSinister")
    @Timed
    public ResponseEntity<SinisterDTO> clotSinister(@RequestParam(name = "prest") String prest) throws URISyntaxException {

        try {
            JSONObject loc = new JSONObject(String.valueOf(prest));
            Long idPrest = loc.getLong("idPrest");
            SinisterPrestationDTO sinisterPrestationDTO = sinisterPrestationService.findOne(idPrest);

            Long statutId = 3L;
            SinisterDTO sinisterDTO = sinisterService.findOne(sinisterPrestationDTO.getSinisterId());
            sinisterDTO.setStatusId(statutId);

            sinisterService.save(sinisterDTO);

            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterDTO));

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
