package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.ViewSinisterPecService;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDT;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPecDTO;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing ViewSinisterPec.
 */
@RestController
@RequestMapping("/api")
public class ViewSinisterPecResource {

    private final Logger log = LoggerFactory.getLogger(ViewSinisterPecResource.class);

    private static final String ENTITY_NAME = "viewSinisterPec";

    private final ViewSinisterPecService viewSinisterPecService;

    private final UserExtraService userExtraService;
    @Autowired
    private UserService userService;

    public ViewSinisterPecResource(ViewSinisterPecService viewSinisterPecService, UserExtraService userExtraService) {
        this.viewSinisterPecService = viewSinisterPecService;
        this.userExtraService = userExtraService;
    }

    /**
     * POST /view-sinister-pecs : Create a new viewSinisterPec.
     *
     * @param viewSinisterPecDTO the viewSinisterPecDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         viewSinisterPecDTO, or with status 400 (Bad Request) if the
     *         viewSinisterPec has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/view-sinister-pecs")
    @Timed
    public ResponseEntity<ViewSinisterPecDTO> createViewSinisterPec(@RequestBody ViewSinisterPecDTO viewSinisterPecDTO)
            throws URISyntaxException {
        log.debug("REST request to save ViewSinisterPec : {}", viewSinisterPecDTO);
        if (viewSinisterPecDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                    "A new viewSinisterPec cannot already have an ID")).body(null);
        }
        ViewSinisterPecDTO result = viewSinisterPecService.save(viewSinisterPecDTO);
        return ResponseEntity.created(new URI("/api/view-sinister-pecs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /view-sinister-pecs : Updates an existing viewSinisterPec.
     *
     * @param viewSinisterPecDTO the viewSinisterPecDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         viewSinisterPecDTO, or with status 400 (Bad Request) if the
     *         viewSinisterPecDTO is not valid, or with status 500 (Internal Server
     *         Error) if the viewSinisterPecDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/view-sinister-pecs")
    @Timed
    public ResponseEntity<ViewSinisterPecDTO> updateViewSinisterPec(@RequestBody ViewSinisterPecDTO viewSinisterPecDTO)
            throws URISyntaxException {
        log.debug("REST request to update ViewSinisterPec : {}", viewSinisterPecDTO);
        if (viewSinisterPecDTO.getId() == null) {
            return createViewSinisterPec(viewSinisterPecDTO);
        }
        ViewSinisterPecDTO result = viewSinisterPecService.save(viewSinisterPecDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, viewSinisterPecDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/accepted-no-reparator/{id}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllAcceptedAndNoReparator(@PathVariable Long id) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllAcceptedAndNoReparator(id);
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/verification")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPecsForValidation() {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllPecsForValidation();
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/in-mission-expert")
    @Timed
    public Set<ViewSinisterPecDTO> getAllInMissionExpert() {

        log.debug("REST request to get all ViewSinisterPecs");

        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        return viewSinisterPecService.findAllInMissionExpert(user.getId());
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/in-cancel-mission-expert/{id}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllInCancelMissionExpert(@PathVariable Long id) {

        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllInCancelMissionExpert(id);
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/accepted-has-reparator")
    @Timed
    public Set<ViewSinisterPecDTO> getAllAcceptedAndHasReparator() {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllAcceptedAndHasReparator();
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/for-reparator/{id}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPecsForReparator(@PathVariable Long id) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllPecsForReparator(id);
    }

    @GetMapping("/view-sinister-pecs/update-devis/{id}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPecsForUpdateDevis(@PathVariable Long id) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllPecsForUpdateDevis(id);
    }

    @GetMapping("/view-sinister-pecs/revue/validation/devis/{id}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPecsInRevueValidationDevis(@PathVariable Long id) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllPecsInRevueValidationDevis(id);
    }

    @GetMapping("/view-sinister-pecs/assigned/{assignedToId}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinisterPecByAssignedId(@PathVariable Long assignedToId) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findSinisterPecByAssignedId(assignedToId);
    }

    @GetMapping("/view-sinister-pecs/modification-prestation")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinPecForModificationPrestation() {
        log.debug("REST request to get all ViewSinisterPecs");
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        return viewSinisterPecService.findAllSinPecForModificationPrestation(user.getId());
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs/expert-opinion/{id}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPecsForExpertOpinion(@PathVariable Long id) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllPecsForExpertOpinion(id);
    }

    /**
     * GET /view-sinister-pecs : get all the viewSinisterPecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         viewSinisterPecs in body
     */
    @GetMapping("/view-sinister-pecs")
    @Timed
    public List<ViewSinisterPecDTO> getAllViewSinisterPecs() {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAll();
    }

    /**
     * GET /view-sinister-pecs/:id : get the "id" viewSinisterPec.
     *
     * @param id the id of the viewSinisterPecDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         viewSinisterPecDTO, or with status 404 (Not Found)
     */
    @GetMapping("/view-sinister-pecs/{id}")
    @Timed
    public ResponseEntity<ViewSinisterPecDTO> getViewSinisterPec(@PathVariable Long id) {
        log.debug("REST request to get ViewSinisterPec : {}", id);
        ViewSinisterPecDTO viewSinisterPecDTO = viewSinisterPecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(viewSinisterPecDTO));
    }

    /**
     * DELETE /view-sinister-pecs/:id : delete the "id" viewSinisterPec.
     *
     * @param id the id of the viewSinisterPecDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/view-sinister-pecs/{id}")
    @Timed
    public ResponseEntity<Void> deleteViewSinisterPec(@PathVariable Long id) {
        log.debug("REST request to delete ViewSinisterPec : {}", id);
        viewSinisterPecService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/view-sinister-pecs/with-decision")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinPecWithDecision() {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllSinPecWithDecision();
    }

    @GetMapping("/view-sinister-pecs/with-decision-for-autres-pieces-jointes/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinPecWithDecisionForAutresJointes(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllAutresPiecesJointes(idUser);
    }

    @GetMapping("/view-sinister-pecs/to-approve/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinisterPecToApprove(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllSinisterPecToApprove(idUser);
    }

    @GetMapping("/view-sinister-pecs/ida-ouverture/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPrestationPECForIdaOuverture(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllPrestationPECForIdaOuverture(idUser);
    }

    @GetMapping("/view-sinister-pecs/for-derogation/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinisterPecForDerogation(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllSinisterPecForDerogation(idUser);
    }

    @GetMapping("/view-sinister-pecs/refused-pec-confirm/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPrestationPECForConfirmRefused(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllConfirmRefusedPrestation(idUser);
    }

    @GetMapping("/view-sinister-pecs/refused-pec/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPrestationPECForRefused(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllRefusedPrestationPec(idUser);
    }

    @GetMapping("/view-sinister-pecs/annulation-pec/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllPrestationPECForCanceled(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllAnnulationPrestationPec(idUser);
    }

    /**
     * GET /view-sinister-pecs : get all the view-sinister-pecs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         view-sinister-pecs in body
     */
    @PostMapping("/view-sinister-pecs/page")
    @Timed
    public ResponseEntity<ViewSinisterPecDT> getAllViewPecDemand(@RequestBody DatatablesRequest datatablesRequest) {
        log.debug("REST request to get all ViewPolicies");
        ViewSinisterPecDT dt = new ViewSinisterPecDT();
        dt.setRecordsFiltered(viewSinisterPecService.getCountDemandPecWithFiltter(datatablesRequest.getSearchValue()));
        dt.setRecordsTotal(viewSinisterPecService.getCountDemandPec());

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength()))
                    .intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ViewSinisterPecDTO> dtos = viewSinisterPecService
                    .findAllDemandsPec(datatablesRequest.getSearchValue(), pr).getContent();
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dtos)) {
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
    
    @GetMapping("/view-sinister-pecs/signature-bon-sortie/{idUser}")
    @Timed
    public Set<ViewSinisterPecDTO> getAllSinPecForSignatureBonSortie(@PathVariable Long idUser) {
        log.debug("REST request to get all ViewSinisterPecs");
        return viewSinisterPecService.findAllSinPecForSignatureBonSortie(idUser);
    }
    
 
    
   @GetMapping("/view-sinister-pecs/refused/{idUser}")
   @Timed
   public ResponseEntity<Set<ViewSinisterPecDTO>> getAllViewSinisterPecRefusedAndAprouveOrApprvWithModif(
           @PathVariable Long idUser) {
     
           Set<ViewSinisterPecDTO> sinistersPec = viewSinisterPecService
                   .findAllViewSinisterPecRefusedAndAprouveOrApprvWithModif(idUser);
           return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
       
   }
   
   
   @GetMapping("/view-sinister-pecs/refused-and-canceled/{idUser}")
   @Timed
   public ResponseEntity<Set<ViewSinisterPecDTO>> getAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(
           @PathVariable Long idUser) {
           Set<ViewSinisterPecDTO> sinistersPec = viewSinisterPecService
                   .findAllViewSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(idUser);
           return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
       
   }
   

}
