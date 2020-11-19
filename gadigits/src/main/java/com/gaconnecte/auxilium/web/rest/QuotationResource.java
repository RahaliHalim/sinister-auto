package com.gaconnecte.auxilium.web.rest;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.gadigits.gtestimate.xml.response.Estimate;
import com.gaconnecte.gadigits.gtestimate.services.EstimateService;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import com.gaconnecte.gadigits.gtestimate.xml.response.Operation;
import com.gaconnecte.gadigits.gtestimate.xml.response.Operation.DetailList.Detail;
import com.gaconnecte.gadigits.gtestimate.xml.response.ItemResult;
import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.EstimationDTO;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;
import com.gaconnecte.auxilium.domain.enumeration.NaturePiece;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.JournalService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.VehiclePieceService;
import com.gaconnecte.auxilium.service.PrimaryQuotationService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.QuotationService;
import com.gaconnecte.auxilium.service.QuotationStatusService;
import com.gaconnecte.auxilium.service.RefTypeInterventionService;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.mapper.VehiclePieceMapper;
import com.gaconnecte.auxilium.service.mapper.QuotationStatusMapper;
import com.gaconnecte.auxilium.service.mapper.RefTypeInterventionMapper;
import com.gaconnecte.auxilium.domain.RefTypeIntervention;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.VehiclePiece;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.domain.Quotation;
import com.gaconnecte.auxilium.domain.QuotationStatus;
import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.config.MySessionHandler;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.DetailsPieces;
import com.gaconnecte.auxilium.repository.ComplementaryQuotationRepository;
import com.gaconnecte.auxilium.repository.PrimaryQuotationRepository;
import com.gaconnecte.auxilium.repository.QuotationStatusRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;

@RestController
@RequestMapping("/api")
public class QuotationResource {

    private final Logger log = LoggerFactory.getLogger(QuotationResource.class);
    private final PrimaryQuotationRepository primaryQuotationRepository;
    private final ComplementaryQuotationRepository complementaryQuotationRepository;
    private static final String ENTITY_NAME = "quotation";
    private final QuotationService quotationService;
    private final EstimateService estimateService;
    private final HistoryService historyService;;

    private final ApplicationProperties properties;
    private final PrimaryQuotationService primaryQuotationService;
    private final VehiclePieceService vehiclePieceService;
    private final VehiclePieceMapper vehiclePieceMapper;
    private final RefTypeInterventionMapper refTypeInterventionMapper;
    private final QuotationStatusMapper quotationStatusMapper;

    @Autowired
    private LoggerService loggerService;
    @Autowired
    private JournalService journalService;
    @Autowired
    private RefTypeInterventionService refTypeInterventionService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserExtraService userExtraService;
    @Autowired
    private ReparateurService reparateurService;
    @Autowired
    private QuotationStatusService quotationStatusService;
    @Autowired
    private SinisterPecService sinisterPecService;
    @Autowired
    QuotationStatusRepository quotationStatusRepository;

    public QuotationResource(ApplicationProperties properties, RefTypeInterventionMapper refTypeInterventionMapper,
            ComplementaryQuotationRepository complementaryQuotationRepository,
            PrimaryQuotationRepository primaryQuotationRepository, VehiclePieceMapper vehiclePieceMapper,
            QuotationStatusMapper quotationStatusMapper, QuotationService quotationService,
            EstimateService estimateService, HistoryService historyService,
            PrimaryQuotationService primaryQuotationService, VehiclePieceService vehiclePieceService) {
        this.primaryQuotationRepository = primaryQuotationRepository;
        this.quotationService = quotationService;
        this.estimateService = estimateService;
        this.historyService = historyService;
        this.properties = properties;
        this.primaryQuotationService = primaryQuotationService;
        this.vehiclePieceService = vehiclePieceService;
        this.vehiclePieceMapper = vehiclePieceMapper;
        this.refTypeInterventionMapper = refTypeInterventionMapper;
        this.quotationStatusMapper = quotationStatusMapper;
        this.complementaryQuotationRepository = complementaryQuotationRepository;

    }

    /**
     * POST /quotation : Create a new quotation.
     *
     * @param quotationDTO the quotationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         conventionDTO, or with status 400 (Bad Request) if the quotation has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/primaryQuotation/create", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<QuotationDTO> createQuotation(
            @RequestPart(name = "quotationFiles", required = false) MultipartFile[] quotationFiles,
            @RequestPart(name = "primaryQuotation", required = false) QuotationDTO primaryQuotation)
            throws URISyntaxException {
        log.debug("REST request to save Quotation with Parts: {}", primaryQuotation.getId());
        QuotationDTO result = quotationService.save(quotationFiles, primaryQuotation);
        // historyService.historysave("Quotation", result.getId(), "CREATION");
        return ResponseEntity.created(new URI("/api/quotation/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * POST /quotation : Update a new quotation.
     *
     * @param quotationDTO the quotationDTO to update
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         quotation Dto, or with status 400 (Bad Request) if the quotation has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/primaryQuotation/update", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<QuotationDTO> updateQuotation(
            @RequestPart(name = "quotationFiles", required = false) MultipartFile[] quotationFiles,
            @RequestPart(name = "primaryQuotation", required = false) QuotationDTO primaryQuotation)
            throws URISyntaxException {
        log.debug("REST request to save Quotation with Parts: {}", primaryQuotation.getId());
        QuotationDTO result = quotationService.save(quotationFiles, primaryQuotation);
        // historyService.historysave("Quotation", result.getId(), "Update");
        return ResponseEntity.created(new URI("/api/quotation/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * @return save Quotation
     *
     *         @PostMapping("/quotation")
     * @Timed public ResponseEntity<QuotationDTO>
     *        createQuotation(@Valid @RequestBody QuotationDTO quotationDTO) throws
     *        URISyntaxException { log.debug("REST request to save Quotation : {}",
     *        quotationDTO); QuotationDTO result =
     *        quotationService.save(quotationDTO); return ResponseEntity.created(new
     *        URI("/api/quotation/" + result.getId()))
     *        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
     *        result.getId().toString())).body(result); }
     */
    /**
     * @return the update Quotation
     */
    @PutMapping("/quotation")
    @Timed
    public ResponseEntity<QuotationDTO> updateQuotation(@Valid @RequestBody QuotationDTO quotationDTO)
            throws URISyntaxException {
        log.debug("REST request to update  Quotation : {}", quotationDTO);
        QuotationDTO result = quotationService.update(quotationDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotationDTO.getId().toString())).body(result);
    }

    /**
     * @return the Quotation
     */
    @GetMapping("/quotation/{id}")
    @Timed
    public ResponseEntity<QuotationDTO> getQuotation(@PathVariable Long id) {
        log.debug("REST request to get Quotation By Id:{}", id);
        QuotationDTO quotationDTO = quotationService.findOne(id);
        System.out.println("quottaion dto----" + quotationDTO.getId());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotationDTO));
    }

    /**
     * @return the Quotation by history
     *
     */
    @GetMapping("/quotation/{actionId}/{entityId}/{entityName}")
    @Timed
    public ResponseEntity<QuotationDTO> getQuotationByHistory(@PathVariable Long actionId, @PathVariable Long entityId,
            @PathVariable String entityName) {
        log.debug("REST request to get Quotation By Id:{}", actionId);
        QuotationDTO quotationDTO = historyService.findHistoryQuotationByAction(actionId, entityId, entityName);
        System.out.println("quottaion dto----" + quotationDTO.getId());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotationDTO));
    }

    /**
     * @return the Url to take interface Of creation new estimation
     */
    @GetMapping("/devis/estimation-creation/{sinisterPecId}")
    @Timed
    public ResponseEntity<EstimationDTO> createEstimation(@PathVariable Long sinisterPecId) {
        log.debug("REST request to create estimation : ");
        EstimationDTO estimationDTO = quotationService.getCreationEstimateUrl(sinisterPecId);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, "d1")).body(estimationDTO);
    }

    /**
     * @return the Url to of file storage path
     */
    @GetMapping("/storage/folder")
    @Timed
    public ResponseEntity<EstimationDTO> geturl() {
        log.debug("REST request to create estimation : ");
        EstimationDTO estimationDTO = new EstimationDTO();
        estimationDTO.setUrl(properties.getRootStoragePath());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, "d1")).body(estimationDTO);
    }

    /**
     * @return the Attachments of to of quotation
     */
    @GetMapping("/quotation/attachement/{id}")
    @Timed
    public HttpEntity<List<AttachmentDTO>> getAttachments(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get Attachments for prestation demontage : {}", id);
        Page<AttachmentDTO> attachmentDTO = quotationService.findAttachments(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(attachmentDTO, "/api/attachement");
        return new ResponseEntity<>(attachmentDTO.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return the Url to take interface Of update new estimation
     */
    @GetMapping("/devis/estimation/{id}/{sinisterPecId}")
    @Timed
    public ResponseEntity<EstimationDTO> updateEstimation(@PathVariable Long id, @PathVariable Long sinisterPecId) {
        log.debug("REST request to update estimation : ");
        EstimationDTO estimationDTO = quotationService.getUpdateEstimateUrl(id, sinisterPecId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, "d1")).body(estimationDTO);
    }

    /**
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @methode who goes through the list of operations of an estimate and saves in
     *          details pieces
     */
    /**
     * @return @return get the estimation in quotation
     */
    /**
     * GET /service-rmqs/:id : get the "id" serviceRmq.
     *
     * @param id the id of the serviceRmqDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         serviceRmqDTO, or with status 404 (Not Found)
     */
    @PutMapping("/quotation/non-confirme-motif/{id}/{motifs}")
    @Timed
    public ResponseEntity<QuotationDTO> NonConfirmationDevisMotif(@PathVariable Long id, @PathVariable Long[] motifs) {
        log.debug("REST request to bloquerrr motiff remorqueurr : {}", id);
        QuotationDTO result = null;
        try {
            QuotationDTO quotationDTO = quotationService.findOne(id);
            journalService.journalisationMotifNonConfirmeDevis("non confirmation devis",
                    SecurityUtils.getCurrentUserLogin(), 202L, quotationDTO, motifs);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, quotationDTO.getId().toString()))
                    .body(result);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        }
    }

    /**
     * @return save the estimation in quotation
     */
    @PostMapping("/devis/estimation/gtestimate/{id}/{prestationId}")
    @Timed
    public ResponseEntity<QuotationDTO> createDevisEstimation(@PathVariable Long id, @PathVariable Long prestationId)
            throws URISyntaxException {
        log.debug("REST request to create Devis Gtestimation  on base de doonés{}");
        log.debug("REST request to create  to log devis.idddddd " + id);
        String typeIntervention = "Remplacer";

        try {
            // Get quotation from gt estimate
            ZoneId timeZone = ZoneId.systemDefault();
            Estimate estimateDevis = null;
            SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(prestationId);
            QuotationDTO oldQuotationDTO = new QuotationDTO();
            ComplementaryQuotation complementaryQuotation = new ComplementaryQuotation();
            PrimaryQuotation quotation = new PrimaryQuotation();
            ReparateurDTO reparateurDTO = reparateurService.findOne(sinisterPecDTO.getReparateurId());
            String login = SecurityUtils.getCurrentUserLogin();
            User user = userService.findOneUserByLogin(login);
            UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
            if ((new Long(28)).equals(userExtraDTO.getProfileId())) {
                estimateDevis = estimateService.getEstimate("SGS036200C", "F83506D31B", "GT_TUNISIE",
                        reparateurDTO.getLogin(), String.valueOf(id));
            } else {
                estimateDevis = estimateService.getEstimate("SGS036200C", "F83506D31B", "GT_TUNISIE", "testintegration",
                        String.valueOf(id));
            }

            Boolean isComplementary = false;
            if (sinisterPecDTO.getPrimaryQuotationId() != null) {
                if (sinisterPecDTO.getPrimaryQuotationId().equals(id)) {
                    isComplementary = false;
                    oldQuotationDTO = quotationService.findOne(id);
                } else {
                    isComplementary = true;
                    oldQuotationDTO = quotationService.findOne(id);
                }

            }

            DecimalFormat df = new DecimalFormat("#.##");
            /* quotation fill from estimation */
            log.debug("REST tto test taillle de la list" + estimateDevis.getOperationList().getOperation().size());
            if (isComplementary) {
                complementaryQuotation.setCreationDate(oldQuotationDTO.getCreationDate());
                complementaryQuotation.setId(id);
                complementaryQuotation.setTtcAmount((new BigDecimal(estimateDevis.getResult().getTotal().doubleValue())
                        .setScale(3, RoundingMode.HALF_UP)).doubleValue());
                complementaryQuotation.setHtAmount((new BigDecimal(estimateDevis.getResult().getTaxBase().doubleValue())
                        .setScale(3, RoundingMode.HALF_UP)).doubleValue());
                complementaryQuotation
                        .setStatus(quotationStatusMapper.toEntity(quotationStatusService.findOne((long) 1)));
                complementaryQuotation.setPriceNewVehicle(oldQuotationDTO.getPriceNewVehicle());
                complementaryQuotation.setMarketValue(oldQuotationDTO.getMarketValue());
                complementaryQuotation.setExpertDecision(oldQuotationDTO.getExpertDecision());
                complementaryQuotation.setRevueDate(oldQuotationDTO.getRevueDate());
                complementaryQuotation.setAvisExpertDate(oldQuotationDTO.getAvisExpertDate());
                complementaryQuotation.setVerificationDevisValidationDate(oldQuotationDTO.getVerificationDevisValidationDate());
                complementaryQuotation.setVerificationDevisRectificationDate(oldQuotationDTO.getVerificationDevisRectificationDate());
                complementaryQuotation.setConfirmationDevisDate(oldQuotationDTO.getConfirmationDevisDate());
                complementaryQuotation.setMiseAJourDevisDate(oldQuotationDTO.getMiseAJourDevisDate());
                complementaryQuotation.setConfirmationDevisComplementaireDate(oldQuotationDTO.getConfirmationDevisComplementaireDate());
                complementaryQuotation.setStampDuty(oldQuotationDTO.getStampDuty());
                complementaryQuotation.setRepairableVehicle(oldQuotationDTO.getRepairableVehicle());
                complementaryQuotation.setConcordanceReport(oldQuotationDTO.getConcordanceReport());
                complementaryQuotation.setPreliminaryReport(oldQuotationDTO.getPreliminaryReport());
                complementaryQuotation.setImmobilizedVehicle(oldQuotationDTO.getImmobilizedVehicle());
                complementaryQuotation.setConditionVehicle(oldQuotationDTO.getConditionVehicle());
                complementaryQuotation.setKilometer(oldQuotationDTO.getKilometer());
                complementaryQuotation.setTotalMo(oldQuotationDTO.getTotalMo());
                complementaryQuotation.setHeureMO(oldQuotationDTO.getHeureMO());
                complementaryQuotation.setEstimateJour(oldQuotationDTO.getEstimateJour());
                complementaryQuotation.setIsConfirme(true);
            } else {
                quotation.setCreationDate(oldQuotationDTO.getCreationDate());
                quotation.setId(id);
                quotation.setTtcAmount((new BigDecimal(estimateDevis.getResult().getTotal().doubleValue()).setScale(3,
                        RoundingMode.HALF_UP)).doubleValue());
                quotation.setHtAmount((new BigDecimal(estimateDevis.getResult().getTaxBase().doubleValue()).setScale(3,
                        RoundingMode.HALF_UP)).doubleValue());
                quotation.setStatus(quotationStatusMapper.toEntity(quotationStatusService.findOne((long) 1)));
                quotation.setPriceNewVehicle(oldQuotationDTO.getPriceNewVehicle());
                quotation.setMarketValue(oldQuotationDTO.getMarketValue());
                quotation.setExpertDecision(oldQuotationDTO.getExpertDecision());
                quotation.setRevueDate(oldQuotationDTO.getRevueDate());
                quotation.setAvisExpertDate(oldQuotationDTO.getAvisExpertDate());
                quotation.setVerificationDevisValidationDate(oldQuotationDTO.getVerificationDevisValidationDate());
                quotation.setVerificationDevisRectificationDate(oldQuotationDTO.getVerificationDevisRectificationDate());
                quotation.setConfirmationDevisDate(oldQuotationDTO.getConfirmationDevisDate());
                quotation.setMiseAJourDevisDate(oldQuotationDTO.getMiseAJourDevisDate());
                quotation.setConfirmationDevisComplementaireDate(oldQuotationDTO.getConfirmationDevisComplementaireDate());
                quotation.setStampDuty(oldQuotationDTO.getStampDuty());
                quotation.setRepairableVehicle(oldQuotationDTO.getRepairableVehicle());
                quotation.setConcordanceReport(oldQuotationDTO.getConcordanceReport());
                quotation.setPreliminaryReport(oldQuotationDTO.getPreliminaryReport());
                quotation.setImmobilizedVehicle(oldQuotationDTO.getImmobilizedVehicle());
                quotation.setConditionVehicle(oldQuotationDTO.getConditionVehicle());
                quotation.setKilometer(oldQuotationDTO.getKilometer());
                quotation.setTotalMo(oldQuotationDTO.getTotalMo());
                quotation.setHeureMO(oldQuotationDTO.getHeureMO());
                quotation.setEstimateJour(oldQuotationDTO.getEstimateJour());
            }

            List<DetailsPieces> listDetailsPieces = new ArrayList<>();
            List<DetailsPieces> listDetailsPiecesFinales = new ArrayList<>();
            List<Operation> operations = estimateDevis.getOperationList().getOperation();
            /* here the supplies ingredients */
            // Treat pieces
            DetailsPieces detailsPieces = new DetailsPieces();
            DetailsPieces detailsPiecesMO = new DetailsPieces();
            Operation operation = new Operation();
            Detail detail = new Detail();
            for (int i = 0; i < operations.size(); i++) {
                VehiclePiece piece1 = vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                        operations.get(i).getShortNumber(), operations.get(i).getDescription().getValue(), operations.get(i).getReference(), 1L);
                VehiclePiece piece2 = vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                        operations.get(i).getShortNumber(), operations.get(i).getDescription().getValue(), operations.get(i).getReference(), 2L);

            }

            for (int i = 0; i < operations.size(); i++) {
            	System.out.println("-----------refDesign----------------------");

                // auto create a novel parts
                log.debug("main--2----------------------" + i + "test------"
                        + operations.get(i).getDetailList().getDetail().size());
                if (operations.get(i).getActionDescription().getValue().equals("Remplacer")) {
                    log.debug("main--2------------ iciiiiiiiii    lasstt val IIIII " + i);
                    detailsPieces = new DetailsPieces();
                    operation = operations.get(i);
                    detailsPieces.setIsMo(false);
                    detailsPieces.setTypeIntervention(
                            refTypeInterventionMapper.toEntity(refTypeInterventionService.findOne((long) 2)));
                    detailsPieces.setQuantite(1F);
                    detailsPieces.setDesignation(vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                            operations.get(i).getShortNumber(), operations.get(i).getDescription().getValue(), operations.get(i).getReference(), 1L));
                    log.debug(
                            "__________________________________________ referece  pieceeeee ______apressss loll______________________________________________LOL"
                                    + operations.get(i).getShortNumber());
                    detailsPieces.setNaturePiece(NaturePiece.ORIGINE);
                    detailsPieces.setReference(operations.get(i).getPartNumber());

                    detailsPieces.setPrixUnit(
                            (new BigDecimal(operations.get(i).getPrecalculation().getPriceMaterial().doubleValue())
                                    .setScale(3, RoundingMode.HALF_UP)).doubleValue());

                    if (isComplementary) {
                        detailsPieces.setQuotation(complementaryQuotation);
                    } else {
                        detailsPieces.setQuotation(quotation);
                    }
                    detailsPieces.setTva((float) 19);
                    detailsPieces.setDiscount((float) 0);
                    detailsPieces.setTotalHt((new BigDecimal(
                            (detailsPieces.getPrixUnit().floatValue() * detailsPieces.getQuantite().floatValue()))
                                    .setScale(3, RoundingMode.HALF_UP)).floatValue());
                    detailsPieces.setTotalTtc(
                            (new BigDecimal((detailsPieces.getTotalHt() + ((detailsPieces.getTotalHt() * 19) / 100)))
                                    .setScale(3, RoundingMode.HALF_UP)).floatValue());
                    listDetailsPieces.add(detailsPieces);
                }

            }
            log.debug("iciii logggggg  tailllee de lisdte labour time",
                    estimateDevis.getResult().getLabourTime().getDetails().getItem().size());
            for (ItemResult item : estimateDevis.getResult().getLabourTime().getDetails().getItem()) {
                if (item.getParent() == null) {

                    detailsPieces = new DetailsPieces();
                    detailsPieces.setIsMo(true);
                    // detailsPieces.setTypeIntervention(new RefTypeIntervention(1L));

                    if (!CollectionUtils.isEmpty(item.getOperations().getOperation())) {
                        log.debug(
                                "___________________________________________________________________________________________________________LOL");
                        detailsPieces.setDesignation(vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                                item.getOperations().getOperation().get(0).getShortNumber(),
                                item.getOperations().getOperation().get(0).getShortNumber(), item.getOperations().getOperation().get(0).getShortNumber(), 1L));
                    } else {
                        detailsPieces.setDesignation(vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                                item.getCode().getValue(), item.getDescription().getValue(), item.getCode().getValue(), 1L));
                    }

                    detailsPieces.setNombreHeures(
                            (new BigDecimal(item.getUnits().getValue().floatValue()).setScale(3, RoundingMode.HALF_UP))
                                    .floatValue());
                    detailsPieces
                            .setTotalHt((new BigDecimal(item.getTotal().floatValue()).setScale(3, RoundingMode.HALF_UP))
                                    .floatValue());
                    detailsPieces.setTotalTtc(
                            (new BigDecimal((detailsPieces.getTotalHt() + ((detailsPieces.getTotalHt() * 19) / 100)))
                                    .setScale(3, RoundingMode.HALF_UP)).floatValue());
                    detailsPieces.setTva((float) 19);
                    detailsPieces.setDiscount((float) 0);

                    detailsPieces.setPrixUnit(
                            (new BigDecimal(item.getPrice().getValue().doubleValue()).setScale(3, RoundingMode.HALF_UP))
                                    .doubleValue());

                    if (item.getOperations().getOperation().size() == 0) {
                        if (detailsPieces.getPrixUnit().intValue() == reparateurDTO
                                .getTauxHorairesReparationHauteTechnicite().intValue()) {
                            detailsPieces.setTypeIntervention(new RefTypeIntervention(7L));
                        } else {
                            detailsPieces.setTypeIntervention(new RefTypeIntervention(1L));
                        }
                    } else {
                        if (typeIntervention
                                .equals(item.getOperations().getOperation().get(0).getActionDescription().getValue())) {
                            detailsPieces.setTypeIntervention(new RefTypeIntervention(2L));
                        } else {
                            if (detailsPieces.getPrixUnit().intValue() == reparateurDTO
                                    .getTauxHorairesReparationHauteTechnicite().intValue()) {
                                detailsPieces.setTypeIntervention(new RefTypeIntervention(7L));
                            } else {
                                detailsPieces.setTypeIntervention(new RefTypeIntervention(1L));
                            }
                        }
                    }

                    if (isComplementary) {
                        detailsPieces.setQuotation(complementaryQuotation);
                    } else {
                        detailsPieces.setQuotation(quotation);
                    }
                    listDetailsPieces.add(detailsPieces);

                }

                log.debug("Details pieces to add {}", detailsPieces);
            }
            Double somH = 0.0;
            for (ItemResult item : estimateDevis.getResult().getPaint().getDetails().getItem()) {
                if (item.getUnits().getValue() != null) {
                    somH = somH + item.getUnits().getValue().doubleValue();
                }

                log.debug("Details pieces to add {}", detailsPieces);
            }

            log.debug("iciii logggggg  tailllee de lisdte paint",
                    estimateDevis.getResult().getPaint().getDetails().getItem().size());
            for (ItemResult item : estimateDevis.getResult().getPaint().getDetails().getItem()) {

                // (new BigDecimal().setScale(3, RoundingMode.HALF_UP)).doubleValue()
                detailsPiecesMO = new DetailsPieces();
                detailsPiecesMO.setIsMo(true);
                detailsPiecesMO.setTypeIntervention(new RefTypeIntervention(3L));
                detailsPieces = new DetailsPieces();
                detailsPieces.setIsMo(false);

                if (!CollectionUtils.isEmpty(item.getOperations().getOperation())) {
                    log.debug(
                            "___________________________________________________________________________________________________________LOL");
                    detailsPieces.setDesignation(vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                            item.getOperations().getOperation().get(0).getShortNumber(),
                            item.getOperations().getOperation().get(0).getShortNumber(), item.getOperations().getOperation().get(0).getShortNumber(), 2L));
                    detailsPiecesMO.setDesignation(vehiclePieceService.findOrCreateVehiclePieceByReferenceAndType(
                            item.getOperations().getOperation().get(0).getShortNumber(),
                            item.getOperations().getOperation().get(0).getShortNumber(), item.getOperations().getOperation().get(0).getShortNumber(), 1L));
                } else {
                    log.debug(
                            "___________________________________________________________________________________________________________RIDHA");
                    detailsPieces.setDesignation(vehiclePieceMapper
                            .toEntity(vehiclePieceService.findVehiclePieceByReferenceAndType("R01P01", 2L)));
                    detailsPiecesMO.setDesignation(vehiclePieceMapper
                            .toEntity(vehiclePieceService.findVehiclePieceByReferenceAndType("R01P01", 1L)));
                }

                detailsPieces.setTva((float) 19);
                detailsPiecesMO.setTva((float) 19);
                detailsPieces.setDiscount((float) 0);
                detailsPiecesMO.setDiscount((float) 0);
                if (item.getUnits().getValue() != null /* && item.getUnits().getValue().intValue()>=1 */) {
                    detailsPieces.setQuantite(
                            (new BigDecimal(item.getUnits().getValue().floatValue()).setScale(3, RoundingMode.HALF_UP))
                                    .floatValue());
                    detailsPiecesMO.setNombreHeures(
                            (new BigDecimal(item.getUnits().getValue().floatValue()).setScale(3, RoundingMode.HALF_UP))
                                    .floatValue());

                } /*
                   * else if(item.getUnits().getValue() != null &&
                   * item.getUnits().getValue().doubleValue()>0D){ detailsPieces .setQuantite(1F);
                   * detailsPiecesMO .setNombreHeures((float) 1); }
                   */ else {
                    detailsPieces.setQuantite(0F);
                    detailsPiecesMO.setNombreHeures((float) 0);
                }

                if (isComplementary) {
                    detailsPieces.setQuotation(complementaryQuotation);
                    detailsPiecesMO.setQuotation(complementaryQuotation);
                } else {
                    detailsPieces.setQuotation(quotation);
                    detailsPiecesMO.setQuotation(quotation);
                }

                detailsPieces.setPrixUnit((new BigDecimal(
                        estimateDevis.getResult().getPaint().getMaterialsSubtotal().doubleValue() / somH).setScale(3,
                                RoundingMode.HALF_UP)).doubleValue());

                if (detailsPieces.getPrixUnit().intValue() == reparateurDTO.getSolvant().intValue()) {
                    detailsPieces.setTypeIntervention(new RefTypeIntervention(5L));
                }
                if (detailsPieces.getPrixUnit().intValue() == reparateurDTO.getHydro().intValue()) {
                    detailsPieces.setTypeIntervention(new RefTypeIntervention(4L));
                }
                detailsPiecesMO.setPrixUnit((double) 21);

                if (detailsPieces.getPrixUnit() != 0D && detailsPieces.getQuantite() != 0D) {
                    detailsPieces
                            .setTotalHt((new BigDecimal((detailsPieces.getPrixUnit() * detailsPieces.getQuantite()))
                                    .setScale(3, RoundingMode.HALF_UP)).floatValue());
                    detailsPiecesMO.setTotalHt(
                            (new BigDecimal((detailsPiecesMO.getPrixUnit() * detailsPiecesMO.getNombreHeures()))
                                    .setScale(3, RoundingMode.HALF_UP)).floatValue());
                    detailsPieces.setTotalTtc(
                            (new BigDecimal((detailsPieces.getTotalHt() + ((detailsPieces.getTotalHt() * 19) / 100)))
                                    .setScale(3, RoundingMode.HALF_UP)).floatValue());
                    detailsPiecesMO.setTotalTtc((new BigDecimal(
                            (detailsPiecesMO.getTotalHt() + ((detailsPiecesMO.getTotalHt() * 19) / 100))).setScale(3,
                                    RoundingMode.HALF_UP)).floatValue());
                    listDetailsPieces.add(detailsPieces);
                    listDetailsPieces.add(detailsPiecesMO);
                }

                log.debug("Details pieces to add {}", detailsPieces);
            }
            for(DetailsPieces dpf : listDetailsPieces) {
            	if(!listDetailsPiecesFinales.contains(dpf)) {
            		listDetailsPiecesFinales.add(dpf);
            	}
            }
            if (isComplementary) {
                complementaryQuotation.setListPieces(listDetailsPiecesFinales);
                /* save qoutation */
                log.debug("REST request to create  debuuutttt" + complementaryQuotation.getId());
                complementaryQuotation = complementaryQuotationRepository.save(complementaryQuotation);
                QuotationDTO quotationDTO = quotationService.findOne(complementaryQuotation.getId());
                if (quotationDTO != null) {
                    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotationDTO));
                } else {
                    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new QuotationDTO()));
                }
            } else {
                quotation.setListPieces(listDetailsPiecesFinales);
                /* save qoutation */
                log.debug("REST request to create  debuuutttt" + quotation.getId());
                quotation = primaryQuotationRepository.save(quotation);
                QuotationDTO quotationDTO = quotationService.findOne(quotation.getId());
                if (quotationDTO != null) {
                    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quotationDTO));
                } else {
                    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new QuotationDTO()));
                }
            }

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            log.debug("erreur-----------------" + e);
            log.debug("erreur-----------------");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * @return update of estimation
     */
    /*
     * @PutMapping("/devis/estimation/update/{id}")
     * 
     * @Timed public ResponseEntity<Quotation> updateDevisEstimation(@PathVariable
     * Long id) throws URISyntaxException {
     * log.debug("REST request to update Devis Gtestimation  on base de doonés{}");
     * Estimate estimateDevis = null; Quotation quotation =
     * quotationMapper.toEntity(quotationService.findOne(id)); try { //estimateDevis
     * = estimateService.getEstimate(String.valueOf(id)); } catch (JAXBException e1)
     * { e1.printStackTrace(); } try { /* quotation fill from estimation
     */
    /*
     * ZoneId timeZone = ZoneId.systemDefault();
     * quotation.setCreationDate(LocalDateTime
     * .parse(estimateDevis.getDateCreation(),
     * DateTimeFormatter.ISO_DATE_TIME).atZone(timeZone)); quotation.setId(id);
     * quotation.setStampDuty(estimateDevis.getResult().getTaxBase().floatValue());
     * quotation.setTtcAmount(estimateDevis.getResult().getTotal().doubleValue());
     * quotation.setHtAmount(estimateDevis.getResult().getTotalWasteRecyclingRate().
     * doubleValue());
     * quotation.setRepairableVehicle(estimateDevis.getVehicleInfo().getMakeCode().
     * isLocked()); quotation.setConcordanceReport(true);
     * quotation.setPreliminaryReport(true); quotation.setKilometer(
     * estimateDevis.getUserData().getVehicleAttributes().getKilometers().getValue()
     * .doubleValue()); quotation.setPriceNewVehicle(-(double)
     * estimateDevis.getVehicleInfo().getCommercialModelId());
     * quotation.setComment(estimateDevis.getUserInfo().getCulture());
     * List<DetailsPieces> listDetailsPieces = new ArrayList<>(); List<Operation>
     * operations = estimateDevis.getOperationList().getOperation(); /* here the
     * supplies ingredients
     */
    /*
     * DetailsPieces detailsPieces = new DetailsPieces(); if
     * (vehiclePieceService.findpiece("les ingredients de peinture", (long) 2) !=
     * null) { detailsPieces.setDesignation(vehiclePieceService.
     * findpiece("les ingredients de peinture", (long) 2)); } else { VehiclePiece
     * pieceD = new VehiclePiece(); pieceD.setCode("les ingredients de peinture");
     * pieceD.setType(vehiclePieceTypeMapper.toEntity(vehiclePieceTypeService.
     * findOne((long) 2))); pieceD.setVetuste(false); detailsPieces.setDesignation(
     * vehiclePieceMapper.toEntity(vehiclePieceService.save(vehiclePieceMapper.toDto
     * (pieceD)))); }
     * detailsPieces.setNombreHeures(estimateDevis.getResult().getPaint().
     * getLabourTimeHours().floatValue()); detailsPieces.setPrixUnit((double) 36);
     * detailsPieces.setIsMo(false); detailsPieces.setTotalTtc((float)
     * (detailsPieces.getNombreHeures() * detailsPieces.getPrixUnit()));
     * detailsPieces.setQuotation(quotation); listDetailsPieces.add(detailsPieces);
     * /* course list of operations
     */
    // this.detailsPiece(operations, listDetailsPieces, quotation);
    // quotation.setListPieces(listDetailsPieces);
    /* save qoutation */
    // quotation = quotationRepository.save(quotation);
    /* history action */
    // historyService.historysave("Quotation", quotation.getId(), "CREATION");
    // return ResponseEntity.created(new URI("/api/devis/" + quotation.getId()))
    // .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
    // quotation.getId().toString()))
    // .body(quotation);
    // } catch (Exception e) {
    // loggerService.log(e, this.getClass().getName() + "." +
    // e.getStackTrace()[0].getMethodName());
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    /* } */

    @DeleteMapping("/quotation/additionel-quote/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdditionnelQuote(@PathVariable Long id) {
        log.debug("REST request to delete DetailsPieces : {}", id);
        SinisterPecDTO sinsterPec = sinisterPecService.findOne(id);
        for (ComplementaryQuotationDTO complementaryQuotationDTO : sinsterPec.getListComplementaryQuotation()) {
            quotationService.delete(complementaryQuotationDTO.getId());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/quotation/motifnonConfirme/{id}/{motifs}")
    @Timed
    public ResponseEntity<QuotationDTO> createMotifNonComfirme(@PathVariable Long id, @PathVariable Long[] motifs) {
        log.debug("REST request to  motiff non comfirme Devis : {}", id);

        QuotationDTO result = null;

        try {

            QuotationDTO quotationDTO = quotationService.findOne(id);
            journalService.journalisationMotifNonConfirmeDevis("motif non confirme devis",
                    SecurityUtils.getCurrentUserLogin(), 202L, quotationDTO, motifs);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCancelAlert(ENTITY_NAME, quotationDTO.getId().toString()))
                    .body(result);
        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
        }
    }

    @DeleteMapping("/quotation/delete-quote/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuotation(@PathVariable Long id) {
        log.debug("REST request to delete DetailsPieces : {}", id);
        quotationService.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/web-sockets/gt-estimate/{sinisterPecId}/{quotationId}")
    @Timed
    public void sendEventForGtEstimate(@PathVariable Long sinisterPecId, @PathVariable Long quotationId) {
    	WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        
        String url = "ws://197.13.12.139:8099/my-ws/websocket";
        StompSessionHandler sessionHandler = new MySessionHandler();
        
        try {
			ListenableFuture<StompSession> f = stompClient.connect(url, sessionHandler);
			StompSession stompSession = f.get();
			//quotationService.subscribeGreetings(stompSession);

			quotationService.sendEventGtEstimate(stompSession, sinisterPecId, quotationId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @GetMapping("/web-sockets/gt-estimate-first-connection/{sinisterPecId}/{quotationId}")
    @Timed
    public void sendFirstConnectionEventForGtEstimate(@PathVariable Long sinisterPecId, @PathVariable Long quotationId) {
    	WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        
        String url = "ws://197.13.12.139:8099/my-ws/websocket";
        StompSessionHandler sessionHandler = new MySessionHandler();
        
        try {
			ListenableFuture<StompSession> f = stompClient.connect(url, sessionHandler);
			StompSession stompSession = f.get();
			//quotationService.subscribeGreetings(stompSession);

			quotationService.sendEventFirstConnexionGtEstimate(stompSession, sinisterPecId, quotationId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @PostMapping("/devis/status/quotation/{quotationId}")
    @Timed
    public ResponseEntity<Void> updateStatusQuotation(@PathVariable Long quotationId) throws URISyntaxException {
        log.debug("REST request to create Devis Gtestimation  on base de doonés{}");
        ComplementaryQuotation complementaryQuotation = complementaryQuotationRepository.findOne(quotationId);
        QuotationStatus quotationStatus = quotationStatusRepository.findOne(4L);
        complementaryQuotation.setStatus(quotationStatus);
        complementaryQuotationRepository.save(complementaryQuotation);
        return ResponseEntity.ok().build();
        }

}
