package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.referential.RefPackService;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Assure.
 */
@RestController
@RequestMapping("/api")
public class AssureResource {

    private final Logger log = LoggerFactory.getLogger(AssureResource.class);

    private static final String ENTITY_NAME = "assure";
    @Autowired
    VehiculeAssureService vehiculeAssureService;
    @Autowired
    ContratAssuranceService contratAssuranceService;
    @Autowired
    HistoryService historyService;
    @Autowired
    private UserService userService;
    @Autowired
    private RefPackService refPackService;
    @Autowired
    private SinisterService sinisterService;
    @Autowired
    private com.gaconnecte.auxilium.service.MailService notificationService;

    private final AssureService assureService;

    public AssureResource(AssureService assureService) {
        this.assureService = assureService;
    }

    /**
     * POST /assures : Create a new assure.
     *
     * @param assureDTO the assureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new assureDTO, or with status 400 (Bad Request) if the assure has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assures")
    @Timed
    public ResponseEntity<AssureDTO> createAssure(@Valid @RequestBody AssureDTO assureDTO) throws URISyntaxException {
        log.debug("REST request to save Assure : {}", assureDTO);
        if (assureDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assure cannot already have an ID")).body(null);
        }
        AssureDTO result = assureService.save(assureDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * PUT /assures : Updates an existing assure.
     *
     * @param assureDTO the assureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * assureDTO, or with status 400 (Bad Request) if the assureDTO is not
     * valid, or with status 500 (Internal Server Error) if the assureDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assures")
    @Timed
    public ResponseEntity<AssureDTO> updateAssure(@Valid @RequestBody AssureDTO assureDTO) throws URISyntaxException {
        log.debug("REST request to update Assure : {}", assureDTO);
        if (assureDTO.getId() == null) {
            return createAssure(assureDTO);
        }
        AssureDTO result = assureService.save(assureDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * GET /assures : get all the assures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assures
     * in body
     */
    @GetMapping("/assures")
    @Timed
    public ResponseEntity<List<AssureDTO>> getAllAssures(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Assures");
        Page<AssureDTO> page = assureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /assures/:id : get the "id" assure.
     *
     * @param id the id of the assureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * assureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assures/{id}")
    @Timed
    public ResponseEntity<AssureDTO> getAssure(@PathVariable Long id) {
        log.debug("REST request to get Assure : {}", id);
        AssureDTO assureDTO = assureService.findOne(id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assureDTO));
    }

    /**
     * DELETE /assures/:id : delete the "id" assure.
     *
     * @param id the id of the assureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assures/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssure(@PathVariable Long id) {
        log.debug("REST request to delete Assure : {}", id);
        assureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH /_search/assures?query=:query : search for the assure
     * corresponding to the query.
     *
     * @param query the query of the assure search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/assures")
    @Timed
    public ResponseEntity<List<AssureDTO>> searchAssures(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Assures for query {}", query);
        Page<AssureDTO> page = assureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/assures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    //@CrossOrigin(origins = "*")
    @GetMapping("/assure/{immatriculation}/{mdp}")
    @Timed
    public ResponseEntity<AssureDTO> connexion(@PathVariable("immatriculation") String immatriculation, @PathVariable("mdp") String mdp) {
        log.debug("REST request to get Assure By immatriculation and MDP : {},{}", immatriculation.toString(), mdp.toString());

        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByImmatriculation(immatriculation);
        if (vehiculeAssureDTO != null) {
            ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findByImmatriculationIgnoreFinValidite(immatriculation);
            AssureDTO assrDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
            assrDTO.setDateFinDeContrat(contratAssuranceDTO.getFinValidite());
            assrDTO.setNumContrat(contratAssuranceDTO.getNumeroContrat());
            assrDTO.setMarqueVehicule(vehiculeAssureDTO.getMarqueLibelle());
            assrDTO.setUsageVehicule(vehiculeAssureDTO.getUsageLibelle());
            assrDTO.setContratAssuranceId(contratAssuranceDTO.getId());
            Integer nbrDePlace = 10;

            RefPackDTO refPackDTO = refPackService.findOne(vehiculeAssureDTO.getPackId());
            Set<RefTypeServiceDTO> typeServices = refPackDTO.getServiceTypes();
            Double interventionNumber = refPackDTO.getInterventionNumber();
            assrDTO.setNombreConventionne(interventionNumber);
            Integer i = 0;
            List<SinisterDTO> sinisters = sinisterService.findByContratId(contratAssuranceDTO.getId());
            for (SinisterDTO sinister : sinisters) {
                List<SinisterPrestationDTO> prestations = sinister.getPrestations();
                for (SinisterPrestationDTO prest : prestations) {
                    if (prest.getStatusId() == 3 || prest.getStatusId() == 1) {
                        interventionNumber = interventionNumber - 1;
                        assrDTO.setNombreConventionne(interventionNumber);
                    }
                }
            }
            Long typeService = 0L;
            assrDTO.setTypeService(typeService);
            for (RefTypeServiceDTO refTypeServiceDTO : typeServices) {
                if (refTypeServiceDTO.getId() == 2) {
                    typeService = 2L;

                    assrDTO.setTypeService(typeService);
                }
            }

            assrDTO.setNombreDePlace(nbrDePlace);
            assrDTO.setVehiculeId(vehiculeAssureDTO.getId());
            assrDTO.setAgencyId(contratAssuranceDTO.getAgenceId());
            if (mdp.equals((vehiculeAssureDTO.getMdp()))) {
                assrDTO.setMdp(vehiculeAssureDTO.getMdp());
                return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assrDTO));
            } else {
                AssureDTO error = new AssureDTO();

                error.setNom("123456789");
                return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
            }
        } else {
            AssureDTO error = new AssureDTO();

            error.setNom("123456789");
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
        }

    }

    @PostMapping("/assureInscription")
    @Timed
    public ResponseEntity<AssureDTO> inscription(@RequestParam(name = "inscription") String inscription) throws URISyntaxException {

        try {
            JSONObject inscrip = new JSONObject(String.valueOf(inscription));
            String immatriculation = (String) inscrip.get("immatriculation");
            String mail = (String) inscrip.get("premMail");
            String mdp = (String) inscrip.get("mdp");
            VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByImmatriculation(immatriculation);
            if (vehiculeAssureDTO != null) {
                ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOneByImmatriculation(immatriculation);

                AssureDTO assrDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
                User user = userService.findOneUserByLogin(SecurityUtils.getCurrentUserLogin());
                if (assrDTO != null) {
                    if (vehiculeAssureDTO.getMdp() == null) {

                        vehiculeAssureDTO.setMdp(mdp);

                        if (!mail.equals("")) {
                            assrDTO.setPremMail(mail);
                        }
                        vehiculeAssureService.save(vehiculeAssureDTO);
                        AssureDTO result = assureService.save(assrDTO);
                        /*HistoryDTO historyDTO = new HistoryDTO();
                	historyDTO.setEntityName("GAGEO Insured");
                	historyDTO.setOperationDate(ZonedDateTime.now());
                	historyDTO.setOperationName("Inscription assure GAGEO"+ " : "+ assrDTO.getNom()+" "+ assrDTO.getPrenom());
                	historyDTO.setUserId(user.getId());
                	historyService.save(historyDTO);*/
                        return ResponseEntity.created(new URI("/api/assures/" + result.getId()))
                                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                                .body(result);
                    } else {
                        //Test if compte existe
                        AssureDTO error = new AssureDTO();

                        error.setNom("1234567");
                        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
                    }

                } else {
                    AssureDTO error = new AssureDTO();

                    error.setNom("123456789");
                    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
                }
            } else {
                AssureDTO error = new AssureDTO();

                error.setNom("123456789");
                return ResponseUtil.wrapOrNotFound(Optional.ofNullable(error));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/assures12")
    @Timed
    public ResponseEntity<AssureDTO> miseAjour(@RequestParam(name = "modification") String modification) throws URISyntaxException {

        try {
            JSONObject modif = new JSONObject(String.valueOf(modification));
            String mail = (String) modif.get("premMail");
            String adrss = (String) modif.get("adresse");
            String phone = (String) modif.get("NumMobile");
            String immatriculation = (String) modif.get("immatriculation");
            String passwrd = (String) modif.get("motDePasse");
            VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByImmatriculation(immatriculation);
            ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOneByImmatriculation(immatriculation);
            AssureDTO assrDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
            //User user = userService.findOneUserByLogin(SecurityUtils.getCurrentUserLogin());
            if (!adrss.equals("")) {
                assrDTO.setAdresse(adrss);
            }
            if (!mail.equals("")) {
                assrDTO.setPremMail(mail);
            }

            if (!phone.equals("")) {
                assrDTO.setPremTelephone(phone);
            }
            if (!passwrd.equals("")) {
                vehiculeAssureDTO.setMdp(passwrd);
            }
            vehiculeAssureService.save(vehiculeAssureDTO);
            AssureDTO result = assureService.save(assrDTO);
            //TODO: inserer l'historique
            /*HistoryDTO historyDTO = new HistoryDTO();
        	historyDTO.setEntityName("GAGEO Insured");
        	historyDTO.setOperationDate(ZonedDateTime.now());
        	historyDTO.setOperationName("Mise à jour donnée Assuré"+ " : "+ assrDTO.getNom()+" "+ assrDTO.getPrenom());
        	Long userId=(long) 46565;
        	historyDTO.setUserId(userId);
        	historyService.save(historyDTO);*/
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                    .body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/assurebase")
    @Timed
    public ResponseEntity<AssureDTO> getAssure1(@RequestParam(name = "location") String location) throws URISyntaxException {

        try {
            JSONObject loc = new JSONObject(String.valueOf(location));
            String immatriculation = (String) loc.get("immatriculation");
            Double longitude = (Double) loc.getDouble("longitude1");
            Double latitude = (Double) loc.getDouble("latitude1");

            ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findByImmatriculationIgnoreFinValidite(immatriculation);
            VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByImmatriculation(immatriculation);
            AssureDTO assrDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
            System.out.println("latitude" + latitude);
            System.out.println("longitude" + longitude);
            assrDTO.setLatitude(latitude);
            assrDTO.setLongitude(longitude);
            assureService.save(assrDTO);

            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assrDTO));

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/assureModif/{immatriculation}")
    @Timed
    public ResponseEntity<AssureDTO> getAssureByImmatriculation(@PathVariable("immatriculation") String immatriculation) {
        log.debug("REST request to get Assure By immatriculation : {}", immatriculation.toString());
        ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOneByImmatriculation(immatriculation);
        VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOneByImmatriculation(immatriculation);
        AssureDTO assrDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assrDTO));
    }

    @PostMapping("/send-mail11")
    @Timed
    public String emailReclamation(@RequestParam(name = "reclamation") String reclamation) throws URISyntaxException {
        try {

            JSONObject reclam = new JSONObject(String.valueOf(reclamation));
            String messageReclamation = (String) reclam.get("messageReclamation");
            String assureNom = (String) reclam.get("assureNom");
            String assurePrenom = (String) reclam.get("assurePrenom");
            String assureNumContrat = (String) reclam.get("assureNumContrat");
            String raisonSocialeRmq = (String) reclam.get("raisonSocialeRmq");
            String idRMQ = (String) reclam.get("idRMQ");
            String subject = "reclamation de l'assure " + assureNom + " " + assureNom
                    + "sur le remorqueur " + raisonSocialeRmq;
            String content = "Numero Contrat : " + assureNumContrat + messageReclamation;

            notificationService.sendReclamationGAGEO("admin@gaconnecte.com", subject, content, true, true);
        } catch (Exception mailException) {
            mailException.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }

}
