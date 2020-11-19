package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.javers.core.diff.Diff;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Expert.
 */
@RestController
@RequestMapping("/api")
public class ExpertResource {

	private final Logger log = LoggerFactory.getLogger(ExpertResource.class);

	private static final String ENTITY_NAME = "expert";

	private final ExpertService expertService;

	@Autowired
	private HistoryService historyService;

	public ExpertResource(ExpertService expertService) {
		this.expertService = expertService;
	}

	/**
	 * POST /experts : Create a new expert.
	 *
	 * @param expertDTO the expertDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         expertDTO, or with status 400 (Bad Request) if the expert has already
	 *         an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/experts")
	@Timed
	public ResponseEntity<ExpertDTO> createExpert(@Valid @RequestBody ExpertDTO expertDTO) throws URISyntaxException {
		log.debug("REST request to save Expert : {}", expertDTO);
		if (expertDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new expert cannot already have an ID"))
					.body(null);
		}
		ExpertDTO result = expertService.save(expertDTO);
		historyService.historysave("Expert", result.getId(), null, result, 0, 0, "creation");
		return ResponseEntity.created(new URI("/api/experts/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /experts : Updates an existing expert.
	 *
	 * @param expertDTO the expertDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         expertDTO, or with status 400 (Bad Request) if the expertDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the expertDTO
	 *         couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/experts")
	@Timed
	public ResponseEntity<ExpertDTO> updateExpert(@Valid @RequestBody ExpertDTO expertDTO) throws URISyntaxException {
		log.debug("REST request to update Expert : {}", expertDTO);
		ExpertDTO oldExpert = expertService.findOne(expertDTO.getId());
		if (expertDTO.getId() == null) {
			return createExpert(expertDTO);
		}
		ExpertDTO result = expertService.save(expertDTO);

		expertService.historysaveExpert("Expert", result.getId(), oldExpert, result,"Modification");

		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expertDTO.getId().toString())).body(result);
	}

	/**
	 * GET /experts : get all the experts.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of experts in
	 *         body
	 */
	@GetMapping("/experts")
	@Timed
	public ResponseEntity<List<ExpertDTO>> getAllExperts() {
		log.debug("REST request to get a page of Experts");
		List<ExpertDTO> page = expertService.findAll();

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(page));
	}

	/**
	 * GET /experts : get all the experts Non Bloqué.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of experts in
	 *         body
	 */
	@GetMapping("/experts/nonBloque")
	@Timed
	public ResponseEntity<List<ExpertDTO>> getAllExpertsNonBloque(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Experts");
		Page<ExpertDTO> page = expertService.findAllExpert(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experts");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /experts/:id : get the "id" expert.
	 *
	 * @param id the id of the expertDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the expertDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/experts/{id}")
	@Timed
	public ResponseEntity<ExpertDTO> getExpert(@PathVariable Long id) {
		log.debug("REST request to get Expert : {}", id);
		ExpertDTO expertDTO = expertService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(expertDTO));
	}

	/**
	 * DELETE /experts/:id : delete the "id" expert.
	 *
	 * @param id the id of the expertDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/experts/{id}")
	@Timed
	public ResponseEntity<Void> deleteExpert(@PathVariable Long id) {
		log.debug("REST request to delete Expert : {}", id);
		expertService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/experts?query=:query : search for the expert corresponding to
	 * the query.
	 *
	 * @param query    the query of the expert search
	 * @param pageable the pagination information
	 * @return the result of the search
	 */
	@GetMapping("/_search/experts")
	@Timed
	public ResponseEntity<List<ExpertDTO>> searchExperts(@RequestParam String query, @ApiParam Pageable pageable) {
		log.debug("REST request to search for a page of Experts for query {}", query);
		Page<ExpertDTO> page = expertService.search(query, pageable);
		HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/experts");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /experts/ville/villeId : get the "id" expert.
	 *
	 * @param id the id of the ville to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the expertDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/experts/ville/{villeId}")
	@Timed
	public ResponseEntity<List<ExpertDTO>> getExpertByVille(@ApiParam Pageable pageable, @PathVariable Long villeId) {
		log.debug("REST request to get expert : {}", villeId);
		Page<ExpertDTO> page = expertService.findExpertByVille(pageable, villeId);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experts/ville");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /experts : get all the expert(s) of a selected Gouvernorat.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of experts in
	 *         body
	 */
	@GetMapping("/experts/gouvernorat/{gouvernoratId}")
	@Timed
	public ResponseEntity<List<ExpertDTO>> getExpertsByGouvernorat(@ApiParam Pageable pageable,
			@PathVariable Long gouvernoratId) {
		log.debug("REST request to get Experts of a Gouvernorat : {}", gouvernoratId);
		Page<ExpertDTO> page = expertService.findByGouvernorat(pageable, gouvernoratId);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experts/gouvernorat");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/experts/byNameFTUSA/{numeroFTUSA}/{pname}")
	@Timed
	public ResponseEntity<ExpertDTO> getExpCompanyByFTUSA(@PathVariable("numeroFTUSA") String numeroFTUSA,
			@PathVariable("pname") String pname) {
		log.debug("REST request to get expert : {}", pname);
		ExpertDTO expertDTO = expertService.getExpCompanyByFTUSA(numeroFTUSA, pname);
		if (expertDTO == null) {
			expertDTO = new ExpertDTO();
		}
		return ResponseEntity.ok().body(expertDTO);
	}
	
	@GetMapping("/experts/by-governorate/{governorateId}/{partnerId}/{modeId}")
    @Timed
    public ResponseEntity<Set<ExpertDTO>> findByGovernorate(@PathVariable Long governorateId, @PathVariable Long partnerId, @PathVariable Long modeId) {
        try {
            Set<ExpertDTO> experts = expertService.findByGovernorate(governorateId, partnerId, modeId);
            return new ResponseEntity<>(experts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
