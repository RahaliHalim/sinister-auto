package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ApecService;
import com.gaconnecte.auxilium.service.DetailsPiecesService;
import com.gaconnecte.auxilium.service.HistoryPecService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.QuotationService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.gaconnecte.auxilium.service.ApecService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import java.util.Set;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Apec.
 */
@RestController
@RequestMapping("/api")
public class ApecResource {
	private final Logger log = LoggerFactory.getLogger(ApecResource.class);

	private static final String ENTITY_NAME = "apec";

	private final ApecService apecService;
	private final UserExtraService userExtraService;
	private final HistoryService historyService;
	private final HistoryPecService historyPecService;

	private final SinisterPecService sinisterPecService;
	private final DetailsPiecesService detailsPiecesService;

	@Autowired
	private LoggerService loggerService;
	@Autowired
	QuotationService quotationService;

	public ApecResource(UserExtraService userExtraService, ApecService apecService, HistoryService historyService, HistoryPecService historyPecService,
			SinisterPecService sinisterPecService, DetailsPiecesService detailsPiecesService) {
		this.apecService = apecService;
		this.userExtraService = userExtraService;
		this.historyService = historyService;
		this.historyPecService = historyPecService;

		this.sinisterPecService = sinisterPecService;
		this.detailsPiecesService = detailsPiecesService;
	}

	/**
	 * POST /apecs : Create a new apec.
	 *
	 * @param apecDTO the apecDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         apecDTO, or with status 400 (Bad Request) if the apec has already an
	 *         ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/apecs")
	@Timed
	public ResponseEntity<ApecDTO> createApec(@Valid @RequestBody ApecDTO apecDTO) throws URISyntaxException {
		log.debug("REST request to save Apec : {}", apecDTO);
		if (apecDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new apec cannot already have an ID"))
					.body(null);
		}
		ApecDTO result = apecService.save(apecDTO);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}

	/**
	 * PUT /apecs : Updates an existing apec.
	 *
	 * @param apecDTO the apecDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         apecDTO, or with status 400 (Bad Request) if the apecDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the apecDTO
	 *         couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/apecs")
	@Timed
	public ResponseEntity<ApecDTO> updateApec(@Valid @RequestBody ApecDTO apecDTO) throws URISyntaxException {
		log.debug("REST request to update Apec : {}", apecDTO);
		if (apecDTO.getId() == null) {
			return createApec(apecDTO);
		}
		String apecType = apecDTO.getDecriptionObservation();
		if ((new String("AccordModifieDerogation")).equals(apecType)) {
			apecDTO.setDecriptionObservation(null);
		}
		Boolean testtest = apecDTO.getTestDevis();
		ApecDTO oldApec = apecService.findOne(apecDTO.getId());
		ApecDTO result = apecService.save(apecDTO);

		SinisterPecDTO oldSinsterPec = sinisterPecService.findOne(result.getSinisterPecId());
		SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(result.getSinisterPecId());
		Boolean test = true;

		if (result.getApecEdit() == true) {
			historyPecService.historyPecApecSave("Appp", sinisterPecDTO.getId(), result, 1L, sinisterPecDTO, "AccordModifie");
		}
		if ((new String("AccordModifieDerogation")).equals(apecType)) {
			historyPecService.historyPecApecSave("Appp", sinisterPecDTO.getId(), result, 1L, sinisterPecDTO,
					"AccordModifieDerogation");
		}

		if (result.getEtat() == 6) {
			sinisterPecDTO.setStepId(106L); // valider
		} else if (result.getEtat() == 7) {
			sinisterPecDTO.setStepId(107L); // validation part assure
			
			 if (sinisterPecDTO.getDateValidationPremierAccord() == null) {
				 
				 sinisterPecDTO.setDateValidationPremierAccord(LocalDateTime.now());
				 
			 }
		} else if (result.getEtat() == 4) {
			sinisterPecDTO.setStepId(104L); // Favorable avec modification
		} else if (result.getEtat() == 3) {
			sinisterPecDTO.setStepId(103L); // Imprimé
		} else if (result.getEtat() == 0) {
			sinisterPecDTO.setStepId(100L); // approubation APEC
		} else if (result.getEtat() == 10) {
			sinisterPecDTO.setStepId(110L); // Instance Reparation
		} else if (result.getEtat() == 13) {
			sinisterPecDTO.setStepId(37L); // Bon Sortie
		} else if (result.getEtat() == 17) {
			sinisterPecDTO.setStepId(16L); // Confirmation Devis Complementaire
		}

		sinisterPecService.save(sinisterPecDTO);

		if ((oldSinsterPec != null && result != null && result.getEtat() != null)) {

			historyPecService.historyPecsave("APEC", result.getId(), (Object) apecDTO, (Object) result,
					oldApec.getEtat().intValue() + 100, result.getEtat().intValue() + 100, "APEC");

			if (sinisterPecDTO.getOldStepNw() != null) {
				if (!result.getEtat().equals(10) && !result.getEtat().equals(20)) {
					if (!new Boolean(false).equals(testtest) && (!(sinisterPecDTO.getOldStepNw()).equals(106L)
							&& (!(sinisterPecDTO.getOldStepNw()).equals(20L)
									|| !(sinisterPecDTO.getOldStepNw()).equals(19L))
							|| !(sinisterPecDTO.getOldStepNw()).equals(18L))) {
						if (result.getEtat().equals(17)) {
							oldSinsterPec.setStepId(20L);
							sinisterPecDTO.setStepId(16L);
						}
						if ((new Integer(5)).equals(result.getDecision()) && result.getEtat().equals(6)) {
							oldSinsterPec.setStepId(106L);
							sinisterPecDTO.setStepId(200L);
						}
						historyPecService.historyPecsave("sinister pec", sinisterPecDTO.getId(), (Object) oldSinsterPec,
								(Object) sinisterPecDTO, oldSinsterPec.getStepId().intValue(),
								sinisterPecDTO.getStepId().intValue(), "PECS");

					}
				}
			}
		}

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}

	/**
	 * GET /apecs : get all the apecs.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of apecs in body
	 */
	@GetMapping("/apecs")
	@Timed
	public ResponseEntity<List<ApecDTO>> getAllApecs(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Apecs");
		Page<ApecDTO> page = apecService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apecs");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/apecs/decision/{etat}")
	@Timed
	public List<ApecDTO> getAllAccordByDecision(@PathVariable Integer etat) {
		log.debug("REST request to get all Accord by status");
		return apecService.findAllAccordByDecision(etat);
	}

	@GetMapping("/apecs/status/{etat}")
	@Timed
	public List<ApecDTO> getAllAccordByStatus(@PathVariable Integer etat) {
		log.debug("REST request to get all Accord by status");
		return apecService.findAllAccordByStatus(etat);
	}

	/**
	 * GET /apecs/:id : get the "id" apec by ok acc assured.
	 *
	 * @param id the id of the apecDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the apecDTO, or
	 *         with status 404 (Not Found)
	 */

	/*
	 * public Set<ApecDTO> getAllApecsByStateAccord(@PathVariable Long
	 * userId, @PathVariable Long etat) {
	 * log.debug("REST request to get a page of Apecs By statte accord");
	 * UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(userId); if
	 * (userExtraDTO.getPersonId() != null) { return (Set<ApecDTO>)
	 * apecService.findByStateAccord(userExtraDTO.getPersonId(), etat); } else {
	 * return null; }
	 * 
	 * }
	 */

	@GetMapping("/signature-accord/{userId}/{etat}")
	@Timed
	public ResponseEntity<Set<ApecDTO>> getAllApecsByStateAccord(@PathVariable Long userId,
			@PathVariable Integer etat) {
		try {
			Set<ApecDTO> sinistersPec = apecService.findByStateAccord(userId, etat);
			return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * GET /apecs/:id : get the "id" apec.
	 *
	 * @param id the id of the apecDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the apecDTO, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/apecs/{id}")
	@Timed
	public ResponseEntity<ApecDTO> getApec(@PathVariable Long id) {
		log.debug("REST request to get Apec : {}", id);
		ApecDTO apecDTO = apecService.findOne(id);
		System.out.println("testApecObservation");
		System.out.println(apecDTO.toString());
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apecDTO));
	}

	@GetMapping("/apecs/{id}/{etat}")
	@Timed
	public ResponseEntity<ApecDTO> findAccordBySinPecAndEtat(@PathVariable Long id, @PathVariable Integer etat) {

		log.debug("REST request to get Apec By idpec etat and decision: {}", id, etat);
		ApecDTO apecDTO = apecService.findAccordBySinPecAndEtat(id, etat);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apecDTO));
	}

	@GetMapping("/apecs/etat-fixe/{id}")
	@Timed
	public ResponseEntity<ApecDTO> findAccordBySinPecAndEtatFixe(@PathVariable Long id) {

		log.debug("REST request to get Apec By idpec etat and decision: {}", id);
		ApecDTO apecDTO = apecService.findAccordBySinPecAndEtatFixe(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apecDTO));
	}

	@GetMapping("/apecs-by-pec/{id}")
	@Timed
	public ResponseEntity<Set<ApecDTO>> findAccordBySinPec(@PathVariable Long id) {

		log.debug("REST request to get Apec By idpec etat and decision: {}", id);
		try {
			Set<ApecDTO> apecs = apecService.findAccordBySinPec(id);
			return new ResponseEntity<>(apecs, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/apecs/sinister-pecs-with-accord/{id}")
	@Timed
	public ResponseEntity<Set<SinisterPecDTO>> getPrestationPEC(@PathVariable Long id) {
		try {
			Set<SinisterPecDTO> sinistersPec = apecService.findAllSinPecWithValidAccord(id);
			return new ResponseEntity<>(sinistersPec, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/apecs/by-sinister-pec/{id}")
	@Timed
	public ResponseEntity<Set<ApecDTO>> getValidAccordBySinPec(@PathVariable Long id) {
		log.debug("REST request to get Apec : {}", id);

		try {
			Set<ApecDTO> apecsDTO = apecService.findValidAccordBySinPec(id);
			return new ResponseEntity<>(apecsDTO, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * DELETE /apecs/:id : delete the "id" apec.
	 *
	 * @param id the id of the apecDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/apecs/{id}")
	@Timed
	public ResponseEntity<Void> deleteApec(@PathVariable Long id) {
		log.debug("REST request to delete Apec : {}", id);
		apecService.delete(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/apecs/delete-by-quotation/{id}")
	@Timed
	public ResponseEntity<Void> deleteApecByQuotationId(@PathVariable Long id) {
		log.debug("REST request to delete Apec : {}", id);
		ApecDTO apecDTO = apecService.findAccordByQuotation(id);
		if (apecDTO != null) {
			if (apecDTO.getId() != null) {
				apecService.delete(apecDTO.getId());
			}
		}
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/apecs/sin-pec-devis-com/{id}")
	@Timed
	public ResponseEntity<Void> deleteApecSinPecModifPrix(@PathVariable Long id) {
		log.debug("REST request to delete Apec : {}", id);
		Set<ApecDTO> apecs = apecService.findAccordBySinPec(id);
		for (ApecDTO apecDTO : apecs) {
			apecService.delete(apecDTO.getId());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/apecs/quotation/{id}")
	@Timed
	public ApecDTO getApecByQuotation(@PathVariable Long id) {
		log.debug("REST request to get Apec : {}", id);
		ApecDTO apecDTO = apecService.findAccordByQuotation(id);
		if (apecDTO != null && apecDTO.getId() != null) {
			return apecDTO;
		}
		return new ApecDTO();
	}

	@GetMapping("/apecs/apecs-by-quotation/{id}")
	@Timed
	public ResponseEntity<Set<ApecDTO>> getListAccordByQuotation(@PathVariable Long id) {
		log.debug("REST request to get Apec : {}", id);
		try {
			Set<ApecDTO> apecs = apecService.findListAccordByQuotation(id);
			return new ResponseEntity<>(apecs, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * SEARCH /_search/apecs?query=:query : search for the apec corresponding to the
	 * query.
	 *
	 * @param query    the query of the apec search
	 * @param pageable the pagination information
	 * @return the result of the search
	 */
	@GetMapping("/_search/apecs")
	@Timed
	public ResponseEntity<List<ApecDTO>> searchApecs(@RequestParam String query, @ApiParam Pageable pageable) {
		log.debug("REST request to search for a page of Apecs for query {}", query);
		Page<ApecDTO> page = apecService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/apecs");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@PutMapping("/apecs-for-facture/{id}")
	@Timed
	public ResponseEntity<ApecDTO> updateApecs(@PathVariable Long id) throws URISyntaxException {
		log.debug("REST request to update Apec : {}", id);
		Set<ApecDTO> apecsDTO = apecService.findAccordBySinPec(id);
		SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(id);
		for (ApecDTO apecDTOToSave : apecsDTO) {
			QuotationDTO quotationDTO = quotationService.findOne(apecDTOToSave.getQuotationId());
			if (!(new Boolean(false).equals(quotationDTO.getIsConfirme()))) {
				apecDTOToSave.setEtat(13);
				apecService.save(apecDTOToSave);
			}
		}
		sinisterPecDTO.setStepId(37L); // Bon Sortie
		sinisterPecService.save(sinisterPecDTO);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new ApecDTO()));
	}

	@DeleteMapping("/apecs/sin-pec-by-quotation/{quotationId}")
	@Timed
	public ResponseEntity<Void> deleteApecByIdDevis(@PathVariable Long quotationId) {
		log.debug("REST request to delete Apec : {}", quotationId);
		Set<ApecDTO> apec = apecService.findAccordByIdDevis(quotationId);
		for (ApecDTO apecDTO : apec) {
			apecService.delete(apecDTO.getId());
		}
		return ResponseEntity.ok().build();
	}

}
