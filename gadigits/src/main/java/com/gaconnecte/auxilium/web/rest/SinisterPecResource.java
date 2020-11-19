package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ProcessesService;
import com.gaconnecte.auxilium.service.QuotationService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.HistoryPecService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.io.IOUtils;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;

/**
 * REST controller for managing SinisterPec.
 */
@RestController
@RequestMapping("/api")
public class SinisterPecResource {

    private final Logger log = LoggerFactory.getLogger(SinisterPecResource.class);

    private static final String ENTITY_NAME = "sinisterPec";

    private final SinisterPecService sinisterPecService;
    private final UserExtraService userExtraService;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    private ProcessesService processesService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoggerService loggerService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private HistoryPecService historyPecService;
    @Autowired
    QuotationService quotationService;

    private final AttachmentMapper attachmentMapper;

    private final FileStorageService fileStorageService;

    public SinisterPecResource(SinisterPecService sinisterPecService, UserExtraService userExtraService,
            AttachmentRepository attachmentRepository, FileStorageService fileStorageService,
            AttachmentMapper attachmentMapper) {
        this.sinisterPecService = sinisterPecService;
        this.userExtraService = userExtraService;
        this.fileStorageService = fileStorageService;
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    /**
     * POST /sinister-pecs : Create a new sinisterPec.
     *
     * @param sinisterPecDTO the sinisterPecDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         sinisterPecDTO, or with status 400 (Bad Request) if the sinisterPec
     *         has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinister-pecs")
    @Timed
    public ResponseEntity<SinisterPecDTO> createSinisterPec(@Valid @RequestBody SinisterPecDTO sinisterPecDTO)
            throws URISyntaxException {
        Long id = sinisterPecDTO.getId();
        try {
            SinisterPecDTO result = sinisterPecService.save(sinisterPecDTO);
            String ref = sinisterPecService.findOne(result.getId()).getReference();
            if (id != null) {
                processesService.closePecSaveTask(ref);
            } else {
                processesService.startPECProcess(ref);
            }
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT /prestation-pecs : get list og pec where reparateur is not affected
     *
     */
    @GetMapping("/sinsterPecAcceptedReparateurNull")
    @Timed
    public Set<SinisterPecDTO> getPrestationPECAcceptedRepNull() {
        log.debug("REST request to get a page of PrestationPEC Accepted with reparateur  are null");
        return (Set<SinisterPecDTO>) sinisterPecService.findAllPrestationPECAcceptedRepNull();
    }

    /**
     * PUT /prestation-pecs : get list og pec where in check supported .
     *
     */
    @GetMapping("/sinister-pecs/in-check-supported")
    @Timed
    public Set<SinisterPecDTO> getPrestationPECInCheckSupported() {
        log.debug("REST request to get a page of PrestationPEC  in-check-supported");
        return (Set<SinisterPecDTO>) sinisterPecService.findAllPrestationPECInCheckSupported();
    }

    /**
     * PUT /prestation-pecs : get list og pec where for-dismantling .
     *
     */
    @GetMapping("/sinister-pecs/for-dismantling/{userId}")
    @Timed
    public Set<SinisterPecDTO> getSinisterPecForDismantling(@PathVariable Long userId) {
        log.debug("REST request to get a page of SinisterPec for Dismantling");
        return (Set<SinisterPecDTO>) sinisterPecService.getSinisterPecForDismantling(userId);
    }

    /**
     * PUT /prestation-pecs : get list og pec where to Verification with
     * habilitation
     *
     */
    @GetMapping("/sinister-pecs/verification/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinisterPecInVerification(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.getAllSinisterPecInVerification(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/update-devis/{userId}")
    @Timed
    public Set<SinisterPecDTO> getSinisterPecForUpdateDevis(@PathVariable Long userId) {
        log.debug("REST request to get a page of SinisterPec for Dismantling");
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(userId);
        if (userExtraDTO.getPersonId() != null) {
            return (Set<SinisterPecDTO>) sinisterPecService.getSinisterPecForUpdateDevis(userExtraDTO.getPersonId());
        } else {
            return null;
        }
    }

    @GetMapping("/sinisters-pecs/expert-opinion/{userId}")
    @Timed
    public Set<SinisterPecDTO> getSinisterPecForExpertOpinion(@PathVariable Long userId) {
        log.debug("REST request to get a page of SinisterPec for expert opinion" + userId);
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(userId);
        if (userExtraDTO != null && userExtraDTO.getPersonId() != null) {
            return (Set<SinisterPecDTO>) sinisterPecService.getSinisterPecForExpertOpinion(userExtraDTO.getPersonId());
        } else {
            return null;
        }
    }

    /**
     * Get /prestation-pecs : get list og pec where in confirmation devis.
     *
     */
    @GetMapping("/sinister-pecs/confirmation-devis/{userId}")
    @Timed
    public Set<SinisterPecDTO> getSinisterPecInConfirmationDevis(@PathVariable Long userId) {
        log.debug("REST request to get a page of SinisterPec for confirmation Devis");
        return (Set<SinisterPecDTO>) sinisterPecService.getSinisterPecInConfirmationDevis(userId);
    }

    /**
     * Get /prestation-pecs : get list og pec where in confirmation devis
     * complémentaire.
     *
     */
    @GetMapping("/sinister-pecs/confirmation-devis-complementaire")
    @Timed
    public Set<SinisterPecDTO> getSinisterPecInConfirmationDevisComplementaire() {
        log.debug("REST request to get a page of SinisterPec for confirmation Devis Complementaire");
        return (Set<SinisterPecDTO>) sinisterPecService.getSinisterPecInConfirmationDevisComplementaire();
    }

    /**
     * Get /prestation-pecs : get list og pec where in revue validation devis.
     *
     */
    @GetMapping("sinister-pecs/revue/validation/devis/{userId}")
    @Timed
    public Set<SinisterPecDTO> getSinisterPecInRevueValidationDevis(@PathVariable Long userId) {
        log.debug("REST request to get a page of SinisterPec for revue validation Devis");
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(userId);
        if (userExtraDTO.getProfileId() != null && userExtraDTO.getProfileId().equals(6L)) {
            return (Set<SinisterPecDTO>) sinisterPecService
                    .getSinisterPecInRevueValidationDevis(userExtraDTO.getPersonId());
        } else {
            return null;
        }
    }

    /**
     * PUT /prestation-pecs : get list og pec where in-not-cancel-expert-mission.
     *
     */
    @GetMapping("/sinister-pecs/in-not-cancel-expert-mission")
    @Timed
    public Set<SinisterPecDTO> getPrestationPECInNotCancelExpertMission() {
        log.debug("REST request to get a page of PrestationPEC  in-not-cancel-expert-mission");
        return (Set<SinisterPecDTO>) sinisterPecService.findAllPrestationPECInNotCancelExpertMission();
    }

    /**
     * PUT /prestation-pecs : get list og pec where reparateur is affected .
     *
     */
    @GetMapping("/sinister-pecs/annuler-affecter-reparateur")
    @Timed
    public Set<SinisterPecDTO> getPrestationPECAcceptedAndReparateurIsAffected() {
        log.debug("REST request to get a page of PrestationPEC Accepted with reparateur  is affected");
        return (Set<SinisterPecDTO>) sinisterPecService.findAllPrestationPECAcceptedAndReparateurIsAffected();
    }

    @GetMapping("/sinister-pecs/reparateur/{userId}")
    @Timed
    public Set<SinisterPecDTO> getPrestationPECReparateur(@PathVariable Long userId) {
        log.debug("REST request to get a page of reception Vehicule");
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(userId);
        if (userExtraDTO.getPersonId() != null) {
            return (Set<SinisterPecDTO>) sinisterPecService.findPrestationPECsByReparateur(userExtraDTO.getPersonId());
        } else {
            return null;
        }
    }

    @GetMapping("/nbre-mission-reparator/{id}")
    @Timed
    public ResponseEntity<Long> getNbreMissionReparator(@PathVariable Long id) {

        Long nbreMission = sinisterPecService.countNumberAffeReparator(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nbreMission));
    }

    @GetMapping("/nbre-mission-expert/{id}")
    @Timed
    public ResponseEntity<Long> getNbreMissionExpert(@PathVariable Long id) {
        Long nbreMission = sinisterPecService.countNumberAffeExpert(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nbreMission));
    }

    /**
     * PUT /prestation-pecs : Updates an existing prestationPEC.
     *
     */
    @PutMapping("/sinister-pecs")
    @Timed
    public ResponseEntity<SinisterPecDTO> updateSinisterPEC(@Valid @RequestBody SinisterPecDTO sinisterPecDTO)
            throws URISyntaxException {
        log.debug("sinsterPECResource-->updateSinisterPEC: REST request to update sinster pec : {}", sinisterPecDTO);
        if (sinisterPecDTO.getId() == null) {
            return createSinisterPec(sinisterPecDTO);
        }

        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(sinisterPecDTO.getId());
        LocalDate ModifDate = LocalDate.now();
        
    

        if (sinisterPecDTO.getDecision() == null) {
            sinisterPecDTO.setAssignedDate(ModifDate);
        } else {
            sinisterPecDTO.setModifDecisionDate(ModifDate);
        }
        SinisterPecDTO result = sinisterPecService.save(sinisterPecDTO);
    

        if (!new Boolean(false).equals(sinisterPecDTO.getTestModifPrix()) && oldSinsterPec != null
                && oldSinsterPec.getStepId() != null) {

            if (result.getReasonCancelExpertId() != null) {
            	
            	if (!result.getReasonCancelExpertId().equals(oldSinsterPec.getReasonCancelExpertId())) {
            		
                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "annulation_missionnement_expert");
                } else {
                	

                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "PECS");
                }
            } else {

                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PECS");
            }
        }
        if (oldSinsterPec.getAssignedToId() != null && result.getAssignedToId() != null) {

            if (!oldSinsterPec.getAssignedToId().equals(result.getAssignedToId())) {
                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                        "modification charge");
            }
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * PUT /prestation-pecs : Updates an existing prestationPEC.
     *
     */
    /**
     * PUT /prestation-pecs : Updates an existing prestationPEC.
     *
     */
    /**
     * PUT /prestation-pecs : Updates an existing prestationPEC.
     *
     * @throws JAXBException
     *
     */
    @PutMapping("/sinister-pecs/quotation/{modeEdit}")
    @Timed
    public ResponseEntity<SinisterPecDTO> updateSinisterPEC(@Valid @RequestBody SinisterPecDTO sinisterPecDTO,
            @PathVariable Boolean modeEdit) throws URISyntaxException {

        Boolean testHistorique = sinisterPecDTO.getTestModifPrix();
        Long quoteId = sinisterPecDTO.getQuotation().getId();
        String historyAvisExpert = sinisterPecDTO.getHistoryAvisExpert();
        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(sinisterPecDTO.getId());
        SinisterPecDTO result = null;
        if (sinisterPecDTO.getId() == null) {
            // historyService.historysave(" Prestation PEC", prestationPECDTO.getId(),
            // "CREATION");
            return createSinisterPec(sinisterPecDTO);
        }

        if (sinisterPecDTO.getPrimaryQuotationId() != null && modeEdit == false) {
            log.debug("test411--------------------------------------------- : {}");
            ComplementaryQuotationDTO complementaryQuotationDTO = new ComplementaryQuotationDTO();
            complementaryQuotationDTO.setCreationDate(LocalDateTime.now());
            complementaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
            complementaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
            complementaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
            complementaryQuotationDTO.setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
            complementaryQuotationDTO.setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
            complementaryQuotationDTO.setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
            complementaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
            complementaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
            complementaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
            complementaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
            // complementaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());

            complementaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
            complementaryQuotationDTO.setIsConfirme(true);
            complementaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
            complementaryQuotationDTO.setStatusId(sinisterPecDTO.getQuotation().getStatusId());
            complementaryQuotationDTO.setSinisterPecId(sinisterPecDTO.getId());
            complementaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
            sinisterPecDTO.getListComplementaryQuotation().add(complementaryQuotationDTO);

            result = sinisterPecService.save(sinisterPecDTO);

            List<DetailsPiecesDTO> listPieces = new ArrayList<>();
            for (DetailsPiecesDTO pieces : Collections
                    .max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                    .getListPieces()) {
                pieces.setQuotationId(Collections
                        .max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId())).getId());
                listPieces.add(pieces);
            }
            Collections.max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                    .setSinisterPecId(sinisterPecDTO.getId());
            Collections.max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                    .setListPieces(listPieces);
            sinisterPecService.save(result);

            sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
            historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                    sinisterPecDTO.getStepId(), result, historyAvisExpert);

        }
        if (sinisterPecDTO.getPrimaryQuotationId() == null) {
            log.debug("test511--------------------------------------------- : {}");
            System.out.println("4-bkjbjbklb12");
            System.out.println("2------------------------------------");
            PrimaryQuotationDTO primaryQuotationDTO = new PrimaryQuotationDTO();
            primaryQuotationDTO.setCreationDate(LocalDateTime.now());
            primaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
            primaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
            primaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
            primaryQuotationDTO.setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
            primaryQuotationDTO.setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
            primaryQuotationDTO.setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
            primaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
            primaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
            primaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
            primaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
            // primaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());
            primaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
            primaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
            primaryQuotationDTO.setStatusId(sinisterPecDTO.getQuotation().getStatusId());
            primaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
            sinisterPecDTO.setPrimaryQuotation(primaryQuotationDTO);
            result = sinisterPecService.save(sinisterPecDTO);
            List<DetailsPiecesDTO> listPieces = new ArrayList<>();
            for (DetailsPiecesDTO pieces : result.getPrimaryQuotation().getListPieces()) {
                pieces.setQuotationId(result.getPrimaryQuotation().getId());
                listPieces.add(pieces);
            }
            // result.getPrimaryQuotation().setSinisterPecId(sinisterPecDTO.getId());
            result.getPrimaryQuotation().setListPieces(listPieces);
            sinisterPecService.save(result);
            sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
            if (!(new String("SaveQuotation")).equals(historyAvisExpert)) {
                historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                        sinisterPecDTO.getStepId(), result, historyAvisExpert);
            }
        }
        // Edit devis
        if (modeEdit == true) {
            System.out.println("Edit devis   principale");
            if (sinisterPecDTO.getQuotation() != null) {
                System.out.println("quottaion id-*-*-*" + sinisterPecDTO.getQuotation().getId()
                        + "primaryquotationid-----" + sinisterPecDTO.getPrimaryQuotationId());
                if (sinisterPecDTO.getQuotation().getId().equals(sinisterPecDTO.getPrimaryQuotationId())) {
                    log.debug("test611--------------------------------------------- : {}");
                    System.out.println("egalité quoatation and primary quoatation-*-**--");
                    PrimaryQuotationDTO primaryQuotationDTO = new PrimaryQuotationDTO();
                    primaryQuotationDTO.setId(sinisterPecDTO.getQuotation().getId());
                    primaryQuotationDTO.setCreationDate(sinisterPecDTO.getQuotation().getCreationDate());
                    primaryQuotationDTO.setAvisExpertDate(sinisterPecDTO.getQuotation().getAvisExpertDate());
                    primaryQuotationDTO.setRevueDate(sinisterPecDTO.getQuotation().getRevueDate());
                    primaryQuotationDTO.setVerificationDevisValidationDate(sinisterPecDTO.getQuotation().getVerificationDevisValidationDate());
                    primaryQuotationDTO.setVerificationDevisRectificationDate(sinisterPecDTO.getQuotation().getVerificationDevisRectificationDate());
                    primaryQuotationDTO.setConfirmationDevisDate(sinisterPecDTO.getQuotation().getConfirmationDevisDate());
                    primaryQuotationDTO.setMiseAJourDevisDate(sinisterPecDTO.getQuotation().getMiseAJourDevisDate());
                    primaryQuotationDTO.setConfirmationDevisComplementaireDate(sinisterPecDTO.getQuotation().getConfirmationDevisComplementaireDate());
                    primaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
                    primaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
                    primaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
                    primaryQuotationDTO.setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
                    primaryQuotationDTO.setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
                    primaryQuotationDTO.setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
                    primaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
                    primaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
                    primaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
                    primaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
                    // primaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());
                    primaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
                    primaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
                    primaryQuotationDTO.setStatusId(sinisterPecDTO.getQuotation().getStatusId());
                    primaryQuotationDTO.setEstimateJour(sinisterPecDTO.getQuotation().getEstimateJour());
                    primaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
                    sinisterPecDTO.setPrimaryQuotation(primaryQuotationDTO);

                    result = sinisterPecService.save(sinisterPecDTO);
                    List<DetailsPiecesDTO> listPieces = new ArrayList<>();
                    for (DetailsPiecesDTO pieces : result.getPrimaryQuotation().getListPieces()) {
                        pieces.setQuotationId(result.getPrimaryQuotation().getId());
                        listPieces.add(pieces);
                    }
                    result.getPrimaryQuotation().setListPieces(listPieces);
                    sinisterPecService.save(result);
                    sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
                    if (!(new String("SaveQuotation")).equals(historyAvisExpert)) {
                        historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                                sinisterPecDTO.getStepId(), result, historyAvisExpert);
                    }
                } else { // Edit de devis complementaire
                    log.debug("test711--------------------------------------------- : {}");
                    ComplementaryQuotationDTO complementaryQuotationDTO = new ComplementaryQuotationDTO();
                    complementaryQuotationDTO.setId(sinisterPecDTO.getQuotation().getId());
                    complementaryQuotationDTO.setCreationDate(sinisterPecDTO.getQuotation().getCreationDate());
                    complementaryQuotationDTO.setAvisExpertDate(sinisterPecDTO.getQuotation().getAvisExpertDate());
                    complementaryQuotationDTO.setRevueDate(sinisterPecDTO.getQuotation().getRevueDate());
                    complementaryQuotationDTO.setVerificationDevisValidationDate(sinisterPecDTO.getQuotation().getVerificationDevisValidationDate());
                    complementaryQuotationDTO.setVerificationDevisRectificationDate(sinisterPecDTO.getQuotation().getVerificationDevisRectificationDate());
                    complementaryQuotationDTO.setConfirmationDevisDate(sinisterPecDTO.getQuotation().getConfirmationDevisDate());
                    complementaryQuotationDTO.setMiseAJourDevisDate(sinisterPecDTO.getQuotation().getMiseAJourDevisDate());
                    complementaryQuotationDTO.setConfirmationDevisComplementaireDate(sinisterPecDTO.getQuotation().getConfirmationDevisComplementaireDate());
                    complementaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
                    complementaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
                    complementaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
                    complementaryQuotationDTO
                            .setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
                    complementaryQuotationDTO
                            .setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
                    complementaryQuotationDTO
                            .setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
                    complementaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
                    complementaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
                    complementaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
                    complementaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
                    // complementaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());
                    complementaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
                    complementaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
                    complementaryQuotationDTO.setIsConfirme(sinisterPecDTO.getQuotation().getIsConfirme());
                    if (sinisterPecDTO.getQuotation().getFromSignature() == null
                            || sinisterPecDTO.getQuotation().getFromSignature().equals(false)) {
                        complementaryQuotationDTO.setStatusId(4L);
                    } else {
                        complementaryQuotationDTO.setStatusId(10L);
                    }
                    complementaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
                    complementaryQuotationDTO.setSinisterPecId(sinisterPecDTO.getId());
                    SinisterPecDTO sinPec = sinisterPecService.findOne(sinisterPecDTO.getId());
                    Set<ComplementaryQuotationDTO> listComplementaryQuotationToModif = new HashSet<>();
                    for (ComplementaryQuotationDTO complementaryDevisDTO : sinPec.getListComplementaryQuotation()) {
                        if (complementaryDevisDTO.getId().equals(complementaryQuotationDTO.getId())) {
                            complementaryDevisDTO = complementaryQuotationDTO;
                        }
                        listComplementaryQuotationToModif.add(complementaryDevisDTO);
                    }
                    sinisterPecDTO.setListComplementaryQuotation(listComplementaryQuotationToModif);
                    Comparator<ComplementaryQuotationDTO> comparator = Comparator
                            .comparing(ComplementaryQuotationDTO::getId);

                    result = sinisterPecService.save(sinisterPecDTO);
                    // Comparator<ComplementaryQuotationDTO> comparator = Comparator.comparing(
                    // ComplementaryQuotationDTO::getId );
                    List<DetailsPiecesDTO> listPieces = new ArrayList<>();
                    for (DetailsPiecesDTO pieces : Collections
                            .max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                            .getListPieces()) {
                        pieces.setQuotationId(complementaryQuotationDTO.getId());
                        listPieces.add(pieces);
                    }
                    Collections.max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                            .setListPieces(listPieces);
                    log.debug("avantttttt save repository");
                    result = sinisterPecService.save(result);
                    sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
                    historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                            sinisterPecDTO.getStepId(), result, historyAvisExpert);

                }
            }
        }
        log.debug("aprres save repository");

        // log.debug("aprres save
        // repository"+historyService.findHistoryQuotationByAction(4L,335L,"Quotation"));
        // historyService.findHistoryQuotationByAction(4L,335L,"Quotation");
        if (oldSinsterPec != null && oldSinsterPec.getStepId() != null
                && !(new Boolean(false)).equals(testHistorique)) {
            historyPecService.historyPecsaveQuote("sinister pec", result.getId(), (Object) oldSinsterPec, (Object) result,
                    oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PEC", quoteId);

        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * GET /sinister-pecs : get all the sinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sinisterPecs
     *         in body
     */
    @GetMapping("/sinister-pecs")
    @Timed
    public ResponseEntity<List<SinisterPecDTO>> getAllSinisterPecs() {
        log.debug("REST request to get all SinisterPecs");
        try {
            List<SinisterPecDTO> SinisterPecs = sinisterPecService.findAll();
            return new ResponseEntity<>(SinisterPecs, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /sinister-pecs/:id : get the "id" sinisterPec.
     *
     * @param id the id of the sinisterPecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         sinisterPecDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sinister-pecs/{id}")
    @Timed
    public ResponseEntity<SinisterPecDTO> getSinisterPec(@PathVariable Long id) {
        log.debug("REST request to get SinisterPec : {}___________________________________________________________",id);
        try {
            SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(id);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterPecDTO));
        } catch (Exception e) {
            log.error("error reeception vehicule: ", e);
        }
        return null;
    }

    @GetMapping("/sinister-pecs/count/{idPec}")
    @Timed
    public long countSinisterPec(@PathVariable Long idPec) {
        log.debug("REST request to get SinisterPec : {}", idPec);
        try {
            long countPec = sinisterPecService.countSinisterPec(idPec);
            return countPec;

        } catch (Exception e) {
            log.error("error reeception vehicule: ", e);
            return 1000000L;
        }

    }

    /* get sinisterpec with the assignedToId */

    @GetMapping("/sinister-pecs/assigned/{assignedToId}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findSinisterPecByAssignedId(@PathVariable Long assignedToId) {
        log.debug("REST request to get SinisterPec : {}", assignedToId);
        Set<SinisterPecDTO> sinisterPecDTO = sinisterPecService.findSinisterPecByAssignedId(assignedToId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterPecDTO));
    }

    /**
     * DELETE /sinister-pecs/:id : delete the "id" sinisterPec.
     *
     * @param id the id of the sinisterPecDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sinister-pecs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSinisterPec(@PathVariable Long id) {
        log.debug("REST request to delete SinisterPec : {}", id);
        sinisterPecService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /apecs/:id : get the "id" apec by ok acc assured.
     *
     * @param id the id of the apecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apecDTO, or
     *         with status 404 (Not Found)
     */

    /**
     * SEARCH /_search/sinister-pecs?query=:query : search for the sinisterPec
     * corresponding to the query.
     *
     * @param query the query of the sinisterPec search
     * @return the result of the search
     */
    /*
     * @GetMapping("/_search/sinister-pecs")
     * 
     * @Timed public List<SinisterPecDTO> searchSinisterPecs(@RequestParam String
     * query) { log.debug("REST request to search SinisterPecs for query {}",
     * query); return sinisterPecService.search(query); }
     */
    /**
     * PostT /prestation-pecs : save Piéces jointes pour Prestation avec démontage .
     *
     * @param prestationPECDTO the prestationPECDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         prestationPECDTO, or with status 400 (Bad Request) if the
     *         prestationPECDTO is not valid, or with status 500 (Internal Server
     *         Error) if the prestationPECDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/sinister-pecs/{id}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<SinisterPecDTO> createSinisterPecDemontage(@PathVariable Long id,
            @RequestPart(name = "SinisterFiles", required = false) MultipartFile[] PrestationFiles)
            throws URISyntaxException {
        log.debug("REST request to save files Sinister : {}", id);
        SinisterPecDTO result = sinisterPecService.saveAttachments(PrestationFiles, id);
        // historyService.historysave("prestation Démontage", result.getId(),
        // "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPecFile(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPEC(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments/{id}/{label}/{nomImage}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPecFileNw(@PathVariable Long id, @PathVariable String label,
            @PathVariable String nomImage, @RequestPart(name = "constat", required = true) MultipartFile ConstatFile)
            throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPECNw(ConstatFile, id, label, nomImage);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments-quotation/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createQuotationFile(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentQuotation(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments-quotation/{id}/{label}/{nomImage}/{nomFolder}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createQuotationFileNw(@PathVariable Long id, @PathVariable String label,
            @PathVariable String nomImage,@PathVariable String nomFolder, @RequestPart(name = "constat", required = true) MultipartFile ConstatFile)
            throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentQuotationNw(ConstatFile, id, label, nomImage, nomFolder);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments-facture/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createFactureFile(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentFacture(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments-facture/{id}/{label}/{nomImage}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createFactureFileNw(@PathVariable Long id, @PathVariable String label,
            @PathVariable String nomImage, @RequestPart(name = "constat", required = true) MultipartFile ConstatFile)
            throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentFactureNw(ConstatFile, id, label, nomImage);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments/{id}/{label}/{description}/{original}/{nomImage}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPieceSinisterPecFileNw(@PathVariable Long id, @PathVariable String label,
            @PathVariable String nomImage, @PathVariable String description, @PathVariable Boolean original,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPieceSinisterPecNw(ConstatFile, id, label, description, original,
                nomImage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping(value = "/sinister-pecs/attachments/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> updatePecFile(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "constat", required = false) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to update files préstation ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.updateAttachmentPEC(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "MISE A JOUR");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping(value = "/sinister-pecs/attachments-quotation/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> updateQuotationFile(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "constat", required = false) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to update files préstation ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.updateAttachmentQuotation(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "MISE A JOUR");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping(value = "/sinister-pecs/attachments-facture/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> updateFactureFile(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "constat", required = false) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to update files préstation ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.updateAttachmentFacture(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "MISE A JOUR");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments/{id}/{label}/{description}/{original}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPieceSinisterPecFile(@PathVariable Long id, @PathVariable String label,
            @PathVariable String description, @PathVariable Boolean original,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPieceSinisterPec(ConstatFile, id, label, description, original);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/autres-pieces-jointes/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createAutresPiecesJointesFile(@PathVariable Long id,
            @PathVariable String label, @RequestPart(name = "constat", required = true) MultipartFile ConstatFile)
            throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAutresPiecesJointesFile(ConstatFile, id, label);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/autres-pieces-jointes/{id}/{label}/{nomImage}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createAutresPiecesJointesFileNw(@PathVariable Long id,
            @PathVariable String label, @PathVariable String nomImage,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile,
            @RequestPart(name = "note", required = false) String note) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAutresPiecesJointesFileNw(ConstatFile, id, label, nomImage, note);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments-rep/{id}/{label}/{nomImage}/{nomFolder}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPieceSinisterPecFilePhotoReparation(@PathVariable Long id,
            @PathVariable String label, @PathVariable String nomImage, @PathVariable String nomFolder,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPieceSinisterPecPhotoReparation(ConstatFile, id, label, nomImage, nomFolder);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    
    @PostMapping(value = "/sinister-pecs/attachments-pec-plus/{id}/{label}/{nomImage}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPieceSinisterPecFilePhotoPlus(@PathVariable Long id,
            @PathVariable String label, @PathVariable String nomImage,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPieceSinisterPecPhotoPlus(ConstatFile, id, label, nomImage);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments/entity/{id}/{label}/{description}/{entityName}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createPieceSinisterPecFile(@PathVariable Long id, @PathVariable String label,
            @PathVariable String description, @PathVariable String entityName,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentPieceApec(ConstatFile, id, label, description, entityName);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/sinister-pecs/attachments-bon-sortie/{id}/{label}/{description}/{original}", consumes = {
            "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createBonSortieSinisterPecFile(@PathVariable Long id,
            @PathVariable String label, @PathVariable String description, @PathVariable Boolean original,
            @RequestPart(name = "constat", required = true) MultipartFile ConstatFile) throws URISyntaxException {
        log.debug("REST request to save files préstation ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = sinisterPecService.saveAttachmentBonSortie(ConstatFile, id, label, description, original);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping("sinister/attachement/{id}")
    @Timed
    public HttpEntity<List<AttachmentDTO>> getAttachments(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get Attachments for sinister pec : {}", id);
        Page<AttachmentDTO> attachmentDTO = sinisterPecService.findAttachments(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(attachmentDTO, "/api/attachement");
        return new ResponseEntity<>(attachmentDTO.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("sinister/attachementImprime/{id}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getImprimeAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments for sinister pec : {}", id);
        Set<AttachmentDTO> attachmentsDTO = sinisterPecService.findImprimeAttachments(id);
        /*
         * for (AttachmentDTO attachmentDTO : attachmentsDTO) { if (attachmentDTO !=
         * null) { File file = fileStorageService.loadFile("imprimePec",
         * attachmentDTO.getName()); if (file != null) { try { Integer a =
         * attachmentDTO.getName().lastIndexOf("."); String b =
         * attachmentDTO.getName().substring(a + 1); attachmentDTO.setDataUnit(b);
         * attachmentDTO.setUrlTelechargment(
         * Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.
         * toPath()))); } catch (Exception e) { System.out.println(e); } } } }
         */
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/sinister-pec/pieces-jointes-file-for-attachment/{id}/{entityName}")
    @Timed
    public void getPiecesJointesFilesForAttachments(@PathVariable Long id, @PathVariable String entityName,
            HttpServletResponse response) {
        log.debug("REST request to get ref xls : {}", id);
        Attachment attachment = attachmentRepository.findOne(id);

        Integer a = attachment.getName().lastIndexOf(".");
        String b = attachment.getName().substring(a + 1);
        File file = fileStorageService.loadFile(entityName, attachment.getName());
        String fileName = "ref" + System.currentTimeMillis() + "." + b;
        try (InputStream fileInputStream = new FileInputStream(file);
                OutputStream output = response.getOutputStream();) {

            response.reset();
            if ((new String("pdf")).equals(b)) {
                response.setContentType("application/pdf;charset=UTF-8");
            } else if ((new String("jpg")).equals(b)) {
                response.setContentType("image/jpeg;charset=UTF-8");
            } else {
                response.setContentType("image/" + b + ";charset=UTF-8");
            }
            response.setContentLength((int) (file.length()));
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(fileInputStream, output);
            output.flush();
        } catch (Exception ecx) {
            log.error("Error Occurred while exporting to XLS ", ecx);
        }
    }

    @GetMapping("sinister/attachement-autres/{id}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getAutresPiecesAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments for sinister pec : {}", id);
        Set<AttachmentDTO> attachmentsDTO = sinisterPecService.findAutresPiecesAttachments(id);
        /*
         * for (AttachmentDTO attachmentDTO : attachmentsDTO) { if (attachmentDTO !=
         * null) { File file = fileStorageService.loadFile("autresPiecesJointes",
         * attachmentDTO.getName()); if (file != null) { try { Integer a =
         * attachmentDTO.getName().lastIndexOf("."); String b =
         * attachmentDTO.getName().substring(a + 1); attachmentDTO.setDataUnit(b);
         * attachmentDTO.setUrlTelechargment(
         * Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.
         * toPath()))); } catch (Exception e) { System.out.println(e); } } } }
         */
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }

    @GetMapping("sinister/attachementReparation/{id}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getReparationAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments reparation for sinister pec : {}", id);
        Set<AttachmentDTO> attachmentsDTO = sinisterPecService.findReparationAttachments(id);
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }
    
    @GetMapping("sinister/attachementExpertise/{id}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getExpertiseAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments expertise for sinister pec : {}", id);
        Set<AttachmentDTO> attachmentsDTO = sinisterPecService.findExpertiseAttachments(id);
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }
    
    @GetMapping("sinister/plus-pec-attachments/{id}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getPlusPecAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments reparation for sinister pec : {}", id);
        Set<AttachmentDTO> attachmentsDTO = sinisterPecService.findPlusDossiersAttachments(id);
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }

    @GetMapping("sinister/attachement-photo-avant-reparation/{entityName}/{entityId}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getPhotoAvantAttachments(@PathVariable String entityName,
            @PathVariable Long entityId) {
        log.debug("REST request to get Attachments reparation for sinister pec : {}", entityId);
        Set<Attachment> attachmentsDTO = attachmentRepository.findAttachments(entityName, entityId);
        Set<AttachmentDTO> att = attachmentsDTO.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(att)) {
            return new ResponseEntity<>(att, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
        }
    }

    @GetMapping("sinister/attachementBonSortie/{id}")
    @Timed
    public HttpEntity<AttachmentDTO> getBonSortieAttachments(@PathVariable Long id) {
        log.debug("REST request to get Attachments for sinister pec : {}", id);
        AttachmentDTO attachmentsDTO = sinisterPecService.findBonSortieAttachments(id);
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }

    @GetMapping("attachments/{entityName}/{entityId}")
    @Timed
    public HttpEntity<AttachmentDTO> getAttachmentByEntity(@PathVariable String entityName,
            @PathVariable Long entityId) {
        log.debug("REST request to get Attachments for sinister pec by entity: {}", entityId);
        AttachmentDTO attachmentsDTO = sinisterPecService.findAttachmentByEntity(entityName, entityId);
        return new ResponseEntity<>(attachmentsDTO, HttpStatus.OK);
    }

    /**
     * SEARCH /_search/prestation-pecs?query=:query : search for the prestationPEC
     * corresponding to the query.
     *
     * @param query    the query of the prestationPEC search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sinister-pecs")
    @Timed
    public ResponseEntity<List<SinisterPecDTO>> searchSinisterPECs(@RequestParam String query,
            @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of prestationPECs for query {}", query);
        Page<SinisterPecDTO> page = sinisterPecService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page,
                "/api/_search/prestation-pecs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/sinister-pecs/to-approve/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findAllSinisterPecToApprove(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinisterPecToApprove(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/refused/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinisterPecRefusedAndAprouveOrApprvWithModif(
            @PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService
                    .findAllSinisterPecRefusedAndAprouveOrApprvWithModif(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/being-processed/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findAllSinisterPecBeingProcessed(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinisterPecBeingProcessed(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/sinister-pecs/gt-rapport/pdf/{id}")
    @Timed
    public void getGTPdf(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Bon Sortie pdf : {}", id);
        AttachmentDTO attachmentsDTO = sinisterPecService.findGTAttachments(id);
        if (attachmentsDTO != null) {
            try {
                String fileName = "Rapport_GT-" + System.currentTimeMillis() + ".xlsx";

                OutputStream out = response.getOutputStream();
                File file = fileStorageService.loadFile("quotation", attachmentsDTO.getName());
                if (file != null) {
                    IOUtils.copy(new FileInputStream(file), out);
                }
                response.setContentType("application/pdf;charset=UTF-8");
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                out.flush();
            } catch (Exception ecx) {
                log.error("Error Occurred while exporting to XLS ", ecx);
            }
        }

    }

    @GetMapping("/sinister-pecs/user/{id}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findSinisterPecByUserId(@PathVariable Long id) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findSinisterPecByUserId(id);
            log.debug("iciiiiiiiiiiii " + sinistersPec.size());
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /* Get sinistrPec By sinisterId */
    @GetMapping("/sinister-pecs/sinister/{id}")
    @Timed
    public ResponseEntity<SinisterPecDTO> findBySinisterId(@PathVariable Long id) {
        try {
            SinisterPecDTO sinistersPec = sinisterPecService.findBySinisterId(id);
            log.debug("iciiiiiiiiiiii getting sinisterPec By sinister Id");
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/sinister-pecs/$/canceled")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findCanceledSinisterPec() {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findCanceledSinisterPec();
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/$/refused")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findRefusededSinisterPec() {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findRefusededSinisterPec();
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/consulter-demande-pec/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findAllSinisterPecConsulterDemandePec(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinisterPecConsulterDemandePec(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /prestation/:id/parent : get the "id" sinisterPec.
     *
     * @param id the id of the sinisterPecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         sinisterPecDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sinister-pecs/{id}/parent")
    @Timed
    public ResponseEntity<SinisterDTO> getSinisterFromSinisterPec(@PathVariable Long id) {
        log.debug("REST request to get Sinister from sinisterPec : {}", id);
        SinisterDTO sinisterDTO = sinisterPecService.findSinisterFromSinisterPec(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinisterDTO));
    }

    @GetMapping("/sinister-pecs/canceled/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinisterPecCanceledAndAprouveOrApprvWithModif(
            @PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService
                    .findAllSinisterPecCanceledAndAprouveOrApprvWithModif(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/accepted-with-reserve/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinisterPecAccWithResrveAndAprouveOrApprvWithModif(
            @PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService
                    .findAllSinisterPecAccWithResrveAndAprouveOrApprvWithModif(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/accepted-with-change-status/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinisterPecAccWithChangeStatusAndAprouveOrApprvWithModif(
            @PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService
                    .findAllSinisterPecAccWithChangeStatusAndAprouveOrApprvWithModif(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/refused-and-canceled/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(
            @PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService
                    .findAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    

    @GetMapping("/demands/pec/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> findAllDemandePec(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllDemandePec(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/ida-ouverture")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllPrestationPECForIdaOuverture() {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllPrestationPECForIdaOuverture();
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/annulation-pec/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllPrestationPECForCanceled(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllAnnulationPrestationPec(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/annulation-pec-confirm/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllPrestationPECForConfirmCanceled(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllConfirmAnnulationPrestation(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/refused-pec/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllPrestationPECForRefused(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllRefusedPrestationPec(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/refused-pec-confirm/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllPrestationPECForConfirmRefused(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllConfirmRefusedPrestation(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/pec-empty")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getPrestationPEC() {
        try {
            Set<SinisterPecDTO> sinistersPec = new HashSet<>();
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/reparateur/quotation/{operation}")
    @Timed
    public List<SinisterPecDTO> getSinisterPecReparateurQuotation(@PathVariable Long operation) {
        log.debug("REST request to get a SinisterPec By Repair");

        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
        if (userExtraDTO.getPersonId() != null) {
            List<SinisterPecDTO> listPecs = sinisterPecService
                    .findPrestationPECsByReparateurComp(userExtraDTO.getPersonId(), operation);
            return listPecs;
        } else {
            return null;
        }
    }

    @GetMapping("/sinister-pecs/with-decision")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinPecWithDecision() {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinPecWithDecision();
            ;
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/verification-printed/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinPecForVerificationPrinted(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinPecForVerificationPrinted(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/signature-bon-sortie/{idUser}")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinPecForSignatureBonSortie(@PathVariable Long idUser) {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinPecForSignatureBonSortie(idUser);
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/sinister-pecs/bon-sortie/pdf/{id}")
    @Timed
    public void getStatementPdf(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Bon Sortie pdf : {}", id);
        AttachmentDTO attachmentsDTO = sinisterPecService.findBonSortieAttachments(id);
        try {
            String fileName = "bonSortie-" + System.currentTimeMillis() + ".xlsx";

            OutputStream out = response.getOutputStream();
            File file = fileStorageService.loadFileBonSortie(attachmentsDTO.getName());
            if (file != null) {
                IOUtils.copy(new FileInputStream(file), out);
            }
            response.setContentType("application/pdf;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.flush();
        } catch (Exception ecx) {
            log.error("Error Occurred while exporting to XLS ", ecx);
        }
    }

    @GetMapping(value = "sinister-pecs/ordre-mission-expert/pdf/{id}")
    @Timed
    public void getOrdreMissionExpertPdf(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Bon Sortie pdf : {}", id);
        AttachmentDTO attachmentsDTO = sinisterPecService.findOrdreMissionAttachments(id);
        try {
            String fileName = "ordreMission-" + System.currentTimeMillis() + ".xlsx";

            OutputStream out = response.getOutputStream();
            File file = fileStorageService.loadFileBonSortie(attachmentsDTO.getName());
            if (file != null) {
                IOUtils.copy(new FileInputStream(file), out);
            }
            response.setContentType("application/pdf;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.flush();
        } catch (Exception ecx) {
            log.error("Error Occurred while exporting to XLS ", ecx);
        }
    }

    @GetMapping(value = "/sinister-pecs/signature-accord/pdf/{id}")
    @Timed
    public void getSignaturePdf(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get signature accord  : {}", id);
        AttachmentDTO attachmentsDTO = sinisterPecService.findAttachmentByEntity("APEC", id);
        try {
            String fileName = "signatureAccord-" + System.currentTimeMillis() + ".xlsx";

            OutputStream out = response.getOutputStream();
            File file = fileStorageService.loadFileBonSortie(attachmentsDTO.getName());
            if (file != null) {
                IOUtils.copy(new FileInputStream(file), out);
            }
            response.setContentType("application/pdf;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.flush();
        } catch (Exception ecx) {
            log.error("Error Occurred while exporting to XLS ", ecx);
        }
    }

    @GetMapping(value = "/sinister-pec/pieces-jointes-file-accord-complementaire/{id}/{entityName}/{label}")
    @Timed
    public void getPiecesJointesFilesForAccord(@PathVariable Long id, @PathVariable String entityName,
            @PathVariable String label, HttpServletResponse response) {
        log.debug("REST request to get ref xls : {}", id);
        Set<Attachment> attachments = attachmentRepository.findAttachmentsByEntityAndEntityNameAndLabel(entityName, id,
                label);
        Integer size = attachments.size();
        if (!size.equals(0)) {
            Attachment attachment = Collections.min(attachments, Comparator.comparing(s -> s.getId()));
            Integer a = attachment.getName().lastIndexOf(".");
            String b = attachment.getName().substring(a + 1);
            File file = fileStorageService.loadFile(entityName, attachment.getName());
            String fileName = "ref" + System.currentTimeMillis() + "." + b;
            try (InputStream fileInputStream = new FileInputStream(file);
                    OutputStream output = response.getOutputStream();) {

                response.reset();
                if ((new String("pdf")).equals(b)) {
                    response.setContentType("application/pdf;charset=UTF-8");
                } else if ((new String("jpg")).equals(b)) {
                    response.setContentType("image/jpeg;charset=UTF-8");
                } else {
                    response.setContentType("image/" + b + ";charset=UTF-8");
                }
                response.setContentLength((int) (file.length()));
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                IOUtils.copy(fileInputStream, output);
                output.flush();
            } catch (Exception ecx) {
                log.error("Error Occurred while exporting to XLS ", ecx);
            }
        }
    }

    @GetMapping("/sinister-pecs/modification-prestation")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinPecForModificationPrestation() {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinPecForModificationPrestation();
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sinister-pecs/confirmation-modification-prix")
    @Timed
    public ResponseEntity<Set<SinisterPecDTO>> getAllSinPecModificationPrix() {
        try {
            Set<SinisterPecDTO> sinistersPec = sinisterPecService.findAllSinPecModificationPrix();
            return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT /prestation-pecs : Updates an existing prestationPEC.
     *
     */
    @PutMapping("/sinister-pecs-mp-com-confirmer")
    @Timed
    public ResponseEntity<SinisterPecDTO> updateSinisterPECForMP(@Valid @RequestBody SinisterPecDTO sinisterPecDTO)
            throws URISyntaxException {
        log.debug("sinsterPECResource-->updateSinisterPEC: REST request to update sinster pec : {}", sinisterPecDTO);
        if (sinisterPecDTO.getId() == null) {
            return createSinisterPec(sinisterPecDTO);
        }

        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(sinisterPecDTO.getId());
        LocalDate ModifDate = LocalDate.now();
        if (sinisterPecDTO.getDecision() == null) {
            sinisterPecDTO.setAssignedDate(ModifDate);
        } else {
            sinisterPecDTO.setModifDecisionDate(ModifDate);
        }
        Set<ComplementaryQuotationDTO> listComplementaryQuotation = new HashSet<>();

        sinisterPecDTO.setListComplementaryQuotation(listComplementaryQuotation);
        SinisterPecDTO result = sinisterPecService.save(sinisterPecDTO);
        if (!new Boolean(false).equals(sinisterPecDTO.getTestModifPrix()) && oldSinsterPec != null
                && oldSinsterPec.getStepId() != null) {
            historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec, (Object) result,
                    oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PECS");
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping(value = "/sinister-pecs/quote/complementary/{id}")
    @Timed
    public ResponseEntity<QuotationDTO> getQuoteInProgress(@PathVariable Long id) {
        log.debug("REST request to get signature accord  : {}", id);
        SinisterPecDTO sinPec = sinisterPecService.findOne(id);
        QuotationDTO quotationDTO = quotationService.findOne(
                Collections.max(sinPec.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId())).getId());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotationDTO));
    }

    @GetMapping(value = "/sinister-pec/pieces-jointes-file/{id}/{entityName}/{label}")
    @Timed
    public void getPiecesJointesFiles(@PathVariable Long id, @PathVariable String entityName,
            @PathVariable String label, HttpServletResponse response) {
        log.debug("REST request to get ref xls : {}", id);
        Set<Attachment> attachments = attachmentRepository.findAttachmentsByEntityAndEntityNameAndLabel(entityName, id,
                label);
        Integer size = attachments.size();
        if (!size.equals(0)) {
            Attachment attachment = Collections.max(attachments, Comparator.comparing(s -> s.getId()));
            Integer a = attachment.getName().lastIndexOf(".");
            String b = attachment.getName().substring(a + 1);
            File file = fileStorageService.loadFile(entityName, attachment.getName());
            String fileName = "ref" + System.currentTimeMillis() + "." + b;
            try (InputStream fileInputStream = new FileInputStream(file);
                    OutputStream output = response.getOutputStream();) {

                response.reset();
                if ((new String("pdf")).equals(b)) {
                    response.setContentType("application/pdf;charset=UTF-8");
                } else if ((new String("jpg")).equals(b)) {
                    response.setContentType("image/jpeg;charset=UTF-8");
                } else {
                    response.setContentType("image/" + b + ";charset=UTF-8");
                }
                response.setContentLength((int) (file.length()));
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                IOUtils.copy(new FileInputStream(file), output);
                output.flush();
            } catch (Exception ecx) {
                log.error("Error Occurred while exporting to XLS ", ecx);
            }
        }

    }

    @PutMapping(value = "/sinister-pecs/quotation-with-attachment/{modeEdit}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<SinisterPecDTO> updateSinisterPECAndAttachment(
            @RequestPart(name = "sinisterPec") SinisterPecDTO sinisterPecDTO,
            @RequestPart(name = "Face Avant Droit", required = false) MultipartFile faceAvantDroitFiles,
            @RequestPart(name = "Carte Grise Quotation", required = false) MultipartFile carteGriseFiles,
            @RequestPart(name = "Face Avant Gauche", required = false) MultipartFile faceAvantGaucheFiles,
            @RequestPart(name = "Face Arriere Droit", required = false) MultipartFile faceArriereDroitFiles,
            @RequestPart(name = "Face Arriere Gauche", required = false) MultipartFile faceArriereGaucheFiles,
            @RequestPart(name = "Finition", required = false) MultipartFile finitionFiles,
            @RequestPart(name = "NSerie", required = false) MultipartFile nSerieFiles,
            @RequestPart(name = "Immatriculation", required = false) MultipartFile immatriculationFiles,
            @RequestPart(name = "Compteur", required = false) MultipartFile compteurFiles,
            @PathVariable Boolean modeEdit) throws URISyntaxException {

        Boolean testHistorique = sinisterPecDTO.getTestModifPrix();
        Long quoteId = sinisterPecDTO.getQuotation().getId();
        String historyAvisExpert = sinisterPecDTO.getHistoryAvisExpert();
        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(sinisterPecDTO.getId());
        SinisterPecDTO result = null;
        if (sinisterPecDTO.getId() == null) {
            // historyService.historysave(" Prestation PEC", prestationPECDTO.getId(),
            // "CREATION");
            return createSinisterPec(sinisterPecDTO);
        }
        System.out.println("4-bkjbjbklb1");

        if (sinisterPecDTO.getPrimaryQuotationId() != null && modeEdit == false) {
            log.debug("test411--------------------------------------------- : {}");
            ComplementaryQuotationDTO complementaryQuotationDTO = new ComplementaryQuotationDTO();
            complementaryQuotationDTO.setCreationDate(LocalDateTime.now());
            complementaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
            complementaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
            complementaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
            complementaryQuotationDTO.setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
            complementaryQuotationDTO.setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
            complementaryQuotationDTO.setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
            complementaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
            complementaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
            complementaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
            complementaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
            // complementaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());

            complementaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
            complementaryQuotationDTO.setIsConfirme(true);
            complementaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
            complementaryQuotationDTO.setStatusId(sinisterPecDTO.getQuotation().getStatusId());
            complementaryQuotationDTO.setSinisterPecId(sinisterPecDTO.getId());
            complementaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
            sinisterPecDTO.getListComplementaryQuotation().add(complementaryQuotationDTO);

            result = sinisterPecService.save(sinisterPecDTO);

            List<DetailsPiecesDTO> listPieces = new ArrayList<>();
            for (DetailsPiecesDTO pieces : Collections
                    .max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                    .getListPieces()) {
                pieces.setQuotationId(Collections
                        .max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId())).getId());
                listPieces.add(pieces);
            }
            Collections.max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                    .setSinisterPecId(sinisterPecDTO.getId());
            Collections.max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                    .setListPieces(listPieces);
            sinisterPecService.save(result);

            sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
            historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                    sinisterPecDTO.getStepId(), result, historyAvisExpert);

        }
        if (sinisterPecDTO.getPrimaryQuotationId() == null) {
            log.debug("test511--------------------------------------------- : {}");
            System.out.println("4-bkjbjbklb12");
            System.out.println("2------------------------------------");
            PrimaryQuotationDTO primaryQuotationDTO = new PrimaryQuotationDTO();
            primaryQuotationDTO.setCreationDate(LocalDateTime.now());
            primaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
            primaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
            primaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
            primaryQuotationDTO.setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
            primaryQuotationDTO.setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
            primaryQuotationDTO.setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
            primaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
            primaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
            primaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
            primaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
            // primaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());
            primaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
            primaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
            primaryQuotationDTO.setStatusId(sinisterPecDTO.getQuotation().getStatusId());
            primaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
            sinisterPecDTO.setPrimaryQuotation(primaryQuotationDTO);
            result = sinisterPecService.save(sinisterPecDTO);
            List<DetailsPiecesDTO> listPieces = new ArrayList<>();
            for (DetailsPiecesDTO pieces : result.getPrimaryQuotation().getListPieces()) {
                pieces.setQuotationId(result.getPrimaryQuotation().getId());
                listPieces.add(pieces);
            }
            // result.getPrimaryQuotation().setSinisterPecId(sinisterPecDTO.getId());
            result.getPrimaryQuotation().setListPieces(listPieces);
            sinisterPecService.save(result);
            sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
            historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                    sinisterPecDTO.getStepId(), result, historyAvisExpert);
        }
        // Edit devis
        if (modeEdit == true) {
            System.out.println("Edit devis   principale");
            if (sinisterPecDTO.getQuotation() != null) {
                System.out.println("quottaion id-*-*-*" + sinisterPecDTO.getQuotation().getId()
                        + "primaryquotationid-----" + sinisterPecDTO.getPrimaryQuotationId());
                if (sinisterPecDTO.getQuotation().getId().equals(sinisterPecDTO.getPrimaryQuotationId())) {
                    log.debug("test611--------------------------------------------- : {}");
                    System.out.println("egalité quoatation and primary quoatation-*-**--");
                    PrimaryQuotationDTO primaryQuotationDTO = new PrimaryQuotationDTO();
                    primaryQuotationDTO.setId(sinisterPecDTO.getQuotation().getId());
                    primaryQuotationDTO.setCreationDate(sinisterPecDTO.getQuotation().getCreationDate());
                    primaryQuotationDTO.setAvisExpertDate(sinisterPecDTO.getQuotation().getAvisExpertDate());
                    primaryQuotationDTO.setRevueDate(sinisterPecDTO.getQuotation().getRevueDate());
                    primaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
                    primaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
                    primaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
                    primaryQuotationDTO.setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
                    primaryQuotationDTO.setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
                    primaryQuotationDTO.setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
                    primaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
                    primaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
                    primaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
                    primaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
                    // primaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());
                    primaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
                    primaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
                    primaryQuotationDTO.setStatusId(sinisterPecDTO.getQuotation().getStatusId());
                    primaryQuotationDTO.setEstimateJour(sinisterPecDTO.getQuotation().getEstimateJour());
                    primaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
                    sinisterPecDTO.setPrimaryQuotation(primaryQuotationDTO);

                    result = sinisterPecService.save(sinisterPecDTO);
                    List<DetailsPiecesDTO> listPieces = new ArrayList<>();
                    for (DetailsPiecesDTO pieces : result.getPrimaryQuotation().getListPieces()) {
                        pieces.setQuotationId(result.getPrimaryQuotation().getId());
                        listPieces.add(pieces);
                    }
                    result.getPrimaryQuotation().setListPieces(listPieces);
                    sinisterPecService.save(result);
                    sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
                    historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                            sinisterPecDTO.getStepId(), result, historyAvisExpert);
                } else { // Edit de devis complementaire
                    log.debug("test711--------------------------------------------- : {}");
                    ComplementaryQuotationDTO complementaryQuotationDTO = new ComplementaryQuotationDTO();
                    complementaryQuotationDTO.setId(sinisterPecDTO.getQuotation().getId());
                    complementaryQuotationDTO.setCreationDate(sinisterPecDTO.getQuotation().getCreationDate());
                    complementaryQuotationDTO.setAvisExpertDate(sinisterPecDTO.getQuotation().getAvisExpertDate());
                    complementaryQuotationDTO.setRevueDate(sinisterPecDTO.getQuotation().getRevueDate());
                    complementaryQuotationDTO.setStampDuty(sinisterPecDTO.getQuotation().getStampDuty());
                    complementaryQuotationDTO.setTtcAmount(sinisterPecDTO.getQuotation().getTtcAmount());
                    complementaryQuotationDTO.setHtAmount(sinisterPecDTO.getQuotation().getHtAmount());
                    complementaryQuotationDTO
                            .setRepairableVehicle(sinisterPecDTO.getQuotation().getRepairableVehicle());
                    complementaryQuotationDTO
                            .setConcordanceReport(sinisterPecDTO.getQuotation().getConcordanceReport());
                    complementaryQuotationDTO
                            .setPreliminaryReport(sinisterPecDTO.getQuotation().getPreliminaryReport());
                    complementaryQuotationDTO.setConditionVehicle(sinisterPecDTO.getQuotation().getConditionVehicle());
                    complementaryQuotationDTO.setKilometer(sinisterPecDTO.getQuotation().getKilometer());
                    complementaryQuotationDTO.setPriceNewVehicle(sinisterPecDTO.getQuotation().getPriceNewVehicle());
                    complementaryQuotationDTO.setMarketValue(sinisterPecDTO.getQuotation().getMarketValue());
                    // complementaryQuotationDTO.setExpertiseDate(sinisterPecDTO.getQuotation().getExpertiseDate());
                    complementaryQuotationDTO.setExpertDecision(sinisterPecDTO.getQuotation().getExpertDecision());
                    complementaryQuotationDTO.setComment(sinisterPecDTO.getQuotation().getComment());
                    complementaryQuotationDTO.setIsConfirme(sinisterPecDTO.getQuotation().getIsConfirme());
                    if (sinisterPecDTO.getQuotation().getFromSignature() == null
                            || sinisterPecDTO.getQuotation().getFromSignature().equals(false)) {
                        complementaryQuotationDTO.setStatusId(4L);
                    } else {
                        complementaryQuotationDTO.setStatusId(10L);
                    }
                    complementaryQuotationDTO.setListPieces(sinisterPecDTO.getQuotation().getListPieces());
                    complementaryQuotationDTO.setSinisterPecId(sinisterPecDTO.getId());
                    SinisterPecDTO sinPec = sinisterPecService.findOne(sinisterPecDTO.getId());
                    Set<ComplementaryQuotationDTO> listComplementaryQuotationToModif = new HashSet<>();
                    for (ComplementaryQuotationDTO complementaryDevisDTO : sinPec.getListComplementaryQuotation()) {
                        if (complementaryDevisDTO.getId().equals(complementaryQuotationDTO.getId())) {
                            complementaryDevisDTO = complementaryQuotationDTO;
                        }
                        listComplementaryQuotationToModif.add(complementaryDevisDTO);
                    }
                    sinisterPecDTO.setListComplementaryQuotation(listComplementaryQuotationToModif);
                    Comparator<ComplementaryQuotationDTO> comparator = Comparator
                            .comparing(ComplementaryQuotationDTO::getId);

                    result = sinisterPecService.save(sinisterPecDTO);
                    // Comparator<ComplementaryQuotationDTO> comparator = Comparator.comparing(
                    // ComplementaryQuotationDTO::getId );
                    List<DetailsPiecesDTO> listPieces = new ArrayList<>();
                    for (DetailsPiecesDTO pieces : Collections
                            .max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                            .getListPieces()) {
                        pieces.setQuotationId(complementaryQuotationDTO.getId());
                        listPieces.add(pieces);
                    }
                    Collections.max(result.getListComplementaryQuotation(), Comparator.comparing(s -> s.getId()))
                            .setListPieces(listPieces);
                    log.debug("avantttttt save repository");
                    result = sinisterPecService.save(result);
                    sinisterPecDTO.getQuotation().setId(result.getPrimaryQuotationId());
                    historyPecService.historyPecDevisSave("Quotation", result.getId(), sinisterPecDTO.getQuotation(),
                            sinisterPecDTO.getStepId(), result, historyAvisExpert);

                }
            }
        }
        log.debug("aprres save repository");

        // log.debug("aprres save
        // repository"+historyService.findHistoryQuotationByAction(4L,335L,"Quotation"));
        // historyService.findHistoryQuotationByAction(4L,335L,"Quotation");
        if (oldSinsterPec != null && oldSinsterPec.getStepId() != null
                && !(new Boolean(false)).equals(testHistorique)) {
            historyPecService.historyPecsaveQuote("sinister pec", result.getId(), (Object) oldSinsterPec, (Object) result,
                    oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PEC", quoteId);

        }
        /*if (faceAvantDroitFiles != null) {
            AttachmentDTO att = sinisterPecService.saveAttachmentQuotationNw(faceAvantDroitFiles, result.getId(),
                    "Face Avant Droit", "noExtension");
        }
        if (carteGriseFiles != null) {
            AttachmentDTO carteGriseAtt = sinisterPecService.saveAttachmentQuotationNw(carteGriseFiles, result.getId(),
                    "Carte Grise Quotation", "noExtension");
        }
        if (faceAvantGaucheFiles != null) {
            AttachmentDTO faceAvantGaucheAtt = sinisterPecService.saveAttachmentQuotationNw(faceAvantGaucheFiles,
                    result.getId(), "Face Avant Gauche", "noExtension");
        }
        if (faceArriereDroitFiles != null) {
            AttachmentDTO faceArriereDroitAtt = sinisterPecService.saveAttachmentQuotationNw(faceArriereDroitFiles,
                    result.getId(), "Face Arriere Droit", "noExtension");
        }
        if (faceArriereGaucheFiles != null) {
            AttachmentDTO faceArriereGaucheAtt = sinisterPecService.saveAttachmentQuotationNw(faceArriereGaucheFiles,
                    result.getId(), "Face Arriere Gauche", "noExtension");
        }
        if (finitionFiles != null) {
            AttachmentDTO finitionAtt = sinisterPecService.saveAttachmentQuotationNw(finitionFiles, result.getId(),
                    "Finition", "noExtension");
        }
        if (nSerieFiles != null) {
            AttachmentDTO nSerieAtt = sinisterPecService.saveAttachmentQuotationNw(nSerieFiles, result.getId(),
                    "NSerie", "noExtension");
        }
        if (immatriculationFiles != null) {
            AttachmentDTO immatriculationAtt = sinisterPecService.saveAttachmentQuotationNw(immatriculationFiles,
                    result.getId(), "Immatriculation", "noExtension");
        }
        if (compteurFiles != null) {
            AttachmentDTO compteurAtt = sinisterPecService.saveAttachmentQuotationNw(compteurFiles, result.getId(),
                    "Compteur", "noExtension");
        }*/
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping(value = "/sinister-pecs-attachments", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<SinisterPecDTO> updateSinisterPECAndAttachment(
            @RequestPart(name = "sinisterPec") SinisterPecDTO sinisterPecDTO,
            @RequestPart(name = "Face Avant Droit", required = false) MultipartFile faceAvantDroitFiles,
            @RequestPart(name = "Carte Grise Quotation", required = false) MultipartFile carteGriseFiles,
            @RequestPart(name = "Face Avant Gauche", required = false) MultipartFile faceAvantGaucheFiles,
            @RequestPart(name = "Face Arriere Droit", required = false) MultipartFile faceArriereDroitFiles,
            @RequestPart(name = "Face Arriere Gauche", required = false) MultipartFile faceArriereGaucheFiles,
            @RequestPart(name = "Finition", required = false) MultipartFile finitionFiles,
            @RequestPart(name = "NSerie", required = false) MultipartFile nSerieFiles,
            @RequestPart(name = "Immatriculation", required = false) MultipartFile immatriculationFiles,
            @RequestPart(name = "Compteur", required = false) MultipartFile compteurFiles) throws URISyntaxException {
        log.debug("sinsterPECResource-->updateSinisterPEC: REST request to update sinster pec : {}", sinisterPecDTO);
        if (sinisterPecDTO.getId() == null) {
            return createSinisterPec(sinisterPecDTO);
        }

        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(sinisterPecDTO.getId());
        LocalDate ModifDate = LocalDate.now();
        if (sinisterPecDTO.getDecision() == null) {
            sinisterPecDTO.setAssignedDate(ModifDate);
        } else {
            sinisterPecDTO.setModifDecisionDate(ModifDate);
        }
        SinisterPecDTO result = sinisterPecService.save(sinisterPecDTO);
        if (!new Boolean(false).equals(sinisterPecDTO.getTestModifPrix()) && oldSinsterPec != null
                && oldSinsterPec.getStepId() != null) {
            if (result.getReasonCancelExpertId() != null) {
                if (!result.getReasonCancelExpertId().equals(oldSinsterPec.getReasonCancelExpertId())) {
                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "annulation_missionnement_expert");
                } else {
                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "PECS");
                }
            } else {
                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PECS");
            }
        }
        if (oldSinsterPec.getAssignedToId() != null && result.getAssignedToId() != null) {
            if (!oldSinsterPec.getAssignedToId().equals(result.getAssignedToId())) {

                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                        "modification charge");
            }
        }

        /*if (faceAvantDroitFiles != null) {
            AttachmentDTO att = sinisterPecService.saveAttachmentQuotationNw(faceAvantDroitFiles, result.getId(),
                    "Face Avant Droit", "noExtension");
        }
        if (carteGriseFiles != null) {
            AttachmentDTO carteGriseAtt = sinisterPecService.saveAttachmentQuotationNw(carteGriseFiles, result.getId(),
                    "Carte Grise Quotation", "noExtension");
        }
        if (faceAvantGaucheFiles != null) {
            AttachmentDTO faceAvantGaucheAtt = sinisterPecService.saveAttachmentQuotationNw(faceAvantGaucheFiles,
                    result.getId(), "Face Avant Gauche", "noExtension");
        }
        if (faceArriereDroitFiles != null) {
            AttachmentDTO faceArriereDroitAtt = sinisterPecService.saveAttachmentQuotationNw(faceArriereDroitFiles,
                    result.getId(), "Face Arriere Droit", "noExtension");
        }
        if (faceArriereGaucheFiles != null) {
            AttachmentDTO faceArriereGaucheAtt = sinisterPecService.saveAttachmentQuotationNw(faceArriereGaucheFiles,
                    result.getId(), "Face Arriere Gauche", "noExtension");
        }
        if (finitionFiles != null) {
            AttachmentDTO finitionAtt = sinisterPecService.saveAttachmentQuotationNw(finitionFiles, result.getId(),
                    "Finition", "noExtension");
        }
        if (nSerieFiles != null) {
            AttachmentDTO nSerieAtt = sinisterPecService.saveAttachmentQuotationNw(nSerieFiles, result.getId(),
                    "NSerie", "noExtension");
        }
        if (immatriculationFiles != null) {
            AttachmentDTO immatriculationAtt = sinisterPecService.saveAttachmentQuotationNw(immatriculationFiles,
                    result.getId(), "Immatriculation", "noExtension");
        }
        if (compteurFiles != null) {
            AttachmentDTO compteurAtt = sinisterPecService.saveAttachmentQuotationNw(compteurFiles, result.getId(),
                    "Compteur", "noExtension");
        }*/

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping("sinister/attachement-accord-signe/{entityName}/{entityId}/{label}")
    @Timed
    public HttpEntity<Set<AttachmentDTO>> getPhotoAvantAttachments(@PathVariable String entityName,
            @PathVariable Long entityId, @PathVariable String label) {
        log.debug("REST request to get Attachments accord singe for sinister pec : {}", entityId);
        Set<Attachment> attachments = attachmentRepository.findAttachmentsByEntityAndEntityNameAndLabel(entityName,
                entityId, label);
        Set<AttachmentDTO> att = attachments.stream().map(attachmentMapper::toDto).collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(att)) {
            return new ResponseEntity<>(att, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/sinister-pec/pieces-jointes-file-i-o-o-b/{id}/{entityName}/{label}")
    @Timed
    public void getPiecesJointesFilesIOOB(@PathVariable Long id, @PathVariable String entityName,
            @PathVariable String label, HttpServletResponse response) {
        log.debug("REST request to get ref xls : {}", id);
        Set<Attachment> attachments = attachmentRepository.findAttachmentsByEntityAndEntityNameAndLabel(entityName, id,
                label);
        Integer size = attachments.size();
        if (!size.equals(0)) {
            Attachment attachment = Collections.max(attachments, Comparator.comparing(s -> s.getId()));
            Integer a = attachment.getName().lastIndexOf(".");
            String b = attachment.getName().substring(a + 1);
            File file = fileStorageService.loadFileBonSortie(attachment.getName());
            String fileName = "ref" + System.currentTimeMillis() + "." + b;
            try (InputStream fileInputStream = new FileInputStream(file);
                    OutputStream output = response.getOutputStream();) {

                response.reset();
                if ((new String("pdf")).equals(b)) {
                    response.setContentType("application/pdf;charset=UTF-8");
                } else if ((new String("jpg")).equals(b)) {
                    response.setContentType("image/jpeg;charset=UTF-8");
                } else {
                    response.setContentType("image/" + b + ";charset=UTF-8");
                }
                response.setContentLength((int) (file.length()));
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                IOUtils.copy(new FileInputStream(file), output);
                output.flush();
            } catch (Exception ecx) {
                log.error("Error Occurred while exporting to XLS ", ecx);
            }
        }

    }
    
    @DeleteMapping("/sinister-pecs/attachment/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttachments(@PathVariable Long id) {
        log.debug("REST request to delete attachment : {}", id);
        sinisterPecService.deleteAttachment(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/sinister-pecs/signature-bon-sortie/{id}")
    @Timed
    public ResponseEntity<SinisterPecDTO> updatePEC(@PathVariable Long id)
            throws URISyntaxException {
        log.debug("sinsterPECResource-->updateSinisterPEC: REST request to update sinster pec : {}", id);
        SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(id);
        sinisterPecDTO.setDateCloture(LocalDateTime.now());
        sinisterPecDTO.setStepId(40L);
        if (sinisterPecDTO.getId() == null) {
            return createSinisterPec(sinisterPecDTO);
        }

        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(sinisterPecDTO.getId());
        LocalDate ModifDate = LocalDate.now();
        if (sinisterPecDTO.getDecision() == null) {
            sinisterPecDTO.setAssignedDate(ModifDate);
        } else {
            sinisterPecDTO.setModifDecisionDate(ModifDate);
        }
        SinisterPecDTO result = sinisterPecService.save(sinisterPecDTO);
        if (!new Boolean(false).equals(sinisterPecDTO.getTestModifPrix()) && oldSinsterPec != null
                && oldSinsterPec.getStepId() != null) {
            if (result.getReasonCancelExpertId() != null) {
                if (!result.getReasonCancelExpertId().equals(oldSinsterPec.getReasonCancelExpertId())) {
                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "annulation_missionnement_expert");
                } else {
                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "PECS");
                }
            } else {
                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PECS");
            }
        }
        if (oldSinsterPec.getAssignedToId() != null && result.getAssignedToId() != null) {
            if (!oldSinsterPec.getAssignedToId().equals(result.getAssignedToId())) {

                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                        "modification charge");
            }
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    
    @PutMapping("/sinister-pecs/modification_prix/{id}")
    @Timed
    public ResponseEntity<SinisterPecDTO> updateSinisterPECModifPrix(@PathVariable Long id)
            throws URISyntaxException {
        log.debug("sinsterPECResource-->updateSinisterPEC: REST request to update sinster pec : {}", id);

        SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(id);
        SinisterPecDTO sinisterPecDTO = oldSinsterPec;
        sinisterPecDTO.setOldStep(sinisterPecDTO.getStepId());
        sinisterPecDTO.setStepId(24L);
        sinisterPecDTO.setOldStepNw(27L);
        sinisterPecDTO.setModificationPrix(true);
        sinisterPecDTO.setActiveModificationPrix(true);
        LocalDate ModifDate = LocalDate.now();
        
    

        if (sinisterPecDTO.getDecision() == null) {
            sinisterPecDTO.setAssignedDate(ModifDate);
        } else {
            sinisterPecDTO.setModifDecisionDate(ModifDate);
        }
        SinisterPecDTO result = sinisterPecService.save(sinisterPecDTO);
    

        if (!new Boolean(false).equals(sinisterPecDTO.getTestModifPrix()) && oldSinsterPec != null
                && oldSinsterPec.getStepId() != null) {

            if (result.getReasonCancelExpertId() != null) {
            	
            	if (!result.getReasonCancelExpertId().equals(oldSinsterPec.getReasonCancelExpertId())) {
            		
                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "annulation_missionnement_expert");
                } else {
                	

                    historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                            (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                            "PECS");
                }
            } else {

                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(), "PECS");
            }
        }
        if (oldSinsterPec.getAssignedToId() != null && result.getAssignedToId() != null) {

            if (!oldSinsterPec.getAssignedToId().equals(result.getAssignedToId())) {
                historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
                        (Object) result, oldSinsterPec.getStepId().intValue(), result.getStepId().intValue(),
                        "modification charge");
            }
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

}
