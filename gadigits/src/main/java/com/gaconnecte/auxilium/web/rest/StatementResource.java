package com.gaconnecte.auxilium.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.gaconnecte.auxilium.service.StatementService;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.StatementDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Statement.
 */
@RestController
@RequestMapping("/api")
public class StatementResource {

	private final Logger log = LoggerFactory.getLogger(StatementResource.class);

	private static final String ENTITY_NAME = "statement";

	private final StatementService statementService;

	private final FileStorageService fileStorageService;

	public StatementResource(StatementService statementService, FileStorageService fileStorageService) {
		this.statementService = statementService;
		this.fileStorageService = fileStorageService;
	}

	/**
	 * POST /statements : Create a new statement.
	 *
	 * @param statementDTO the statementDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 * new statementDTO, or with status 400 (Bad Request) if the statement has
	 * already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/statements")
	@Timed
	public ResponseEntity<StatementDTO> createStatement(@RequestBody StatementDTO statementDTO) throws URISyntaxException {
		log.debug("REST request to save Statement : {}", statementDTO);
		if (statementDTO.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new statement cannot already have an ID")).body(null);
		}
		StatementDTO result = statementService.save(statementDTO);
		return ResponseEntity.created(new URI("/api/statements/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /statements : Updates an existing statement.
	 *
	 * @param statementDTO the statementDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 * statementDTO, or with status 400 (Bad Request) if the statementDTO is not
	 * valid, or with status 500 (Internal Server Error) if the statementDTO
	 * couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/statements")
	@Timed
	public ResponseEntity<StatementDTO> updateStatement(@RequestBody StatementDTO statementDTO) throws URISyntaxException {
		log.debug("REST request to update Statement : {}", statementDTO);
		if (statementDTO.getId() == null) {
			return createStatement(statementDTO);
		}
		StatementDTO result = statementService.save(statementDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statementDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /statements : get all the statements.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 * statements in body
	 */
	@GetMapping("/statements")
	@Timed
	public List<StatementDTO> getAllStatements() {
		log.debug("REST request to get all Statements");
		try {
			List<StatementDTO> dtos = new LinkedList<>();
			List<StatementDTO> statements = statementService.findAll();
			for (StatementDTO statement : statements) {
				if (statement.getName() != null) {
					File file = fileStorageService.loadFile(statement.getPath() + File.separator + statement.getName());
					if (file != null) {
						statement.setAttachment64(Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath())));
					}
				}
				dtos.add(statement);
			}
			return dtos;
		} catch (Exception e) {
			e.printStackTrace();
			return new LinkedList<>();
		}
	}

	/**
	 * GET /statements/:id : get the "id" statement.
	 *
	 * @param id the id of the statementDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 * statementDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/statements/{id}")
	@Timed
	public ResponseEntity<StatementDTO> getStatement(@PathVariable Long id) {
		log.debug("REST request to get Statement : {}", id);
		StatementDTO statementDTO = statementService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statementDTO));
	}

	/**
	 * GET /statements/get/pdf/{id} : Download the pdf statement by id
	 * @param id
	 * @param response
	 */
	@GetMapping(value = "/statements/get/pdf/{id}")
	@Timed
	public void getStatementPdf(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get Statement pdf : {}", id);

		StatementDTO statementDTO = statementService.findOne(id);
		statementDTO.setStep(1); // Step 1 : download statement for validation ==> validate : yes or no
		statementService.save(statementDTO);

		try {
			String fileName = "statement-" + System.currentTimeMillis() + ".pdf";

			OutputStream out = response.getOutputStream();
			File file = fileStorageService.loadFile(statementDTO.getPath() + File.separator + statementDTO.getName());
			if (file != null) {
				IOUtils.copy(new FileInputStream(file), out);
			}            
			response.setContentType("application/pdf;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			out.flush();
		} catch (Exception ecx) {
			log.error("Error Occurred while downloading statement pdf !", ecx);
		}
	}

	/**
	 * GET /statements/generate/pdf/{id} : Regenerate the statement pdf by ID
	 * @param id
	 * @param response
	 */
	@GetMapping(value = "/statements/generate/pdf/{id}")
	@Timed
	public void regenerateStatementPdf(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to generate Statement pdf : {}", id);
		StatementDTO statementDTO = statementService.findOne(id);
		statementDTO.setStep(0); // Step 0 : statement just created
		statementService.save(statementDTO);
		
		// Regenerate & Download
		try {
			statementService.regenerateStatement(statementDTO.getTugId(), statementDTO.getName().split("[.]")[0], statementDTO.getName()); // Regenerate
			
			String fileName = "statement-" + System.currentTimeMillis() + ".pdf";

			OutputStream out = response.getOutputStream();
			File file = fileStorageService.loadFile(statementDTO.getPath() + File.separator + statementDTO.getName());
			if (file != null) {
				IOUtils.copy(new FileInputStream(file), out);
			}            
			response.setContentType("application/pdf;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			out.flush();
		} catch (Exception ecx) {
			log.error("Error Occurred while downloading statement pdf !", ecx);
		}
	}

	/**
	 * Validate invoice
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/statements/validate/{id}")
	@Timed
	public ResponseEntity<StatementDTO> validateStatement(@PathVariable Long id) {
		log.debug("REST request to validate Statement : {}", id);
		StatementDTO statementDTO = statementService.findOne(id);
		statementDTO.setStep(2);
		StatementDTO result = statementService.save(statementDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statementDTO.getId().toString()))
				.body(result);

	}

	/**
	 * Invalidate invoice ==> regenerate
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/statements/invalidate/{id}")
	@Timed
	public ResponseEntity<StatementDTO> unvalidateStatement(@PathVariable Long id) {
		log.debug("REST request to unvalidate Statement : {}", id);
		StatementDTO statementDTO = statementService.findOne(id);
		statementDTO.setStep(3);
		StatementDTO result = statementService.save(statementDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statementDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /statements/companies : get all companies.
	 *
	 * @param month
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 * statements in body
	 */
	@GetMapping("/statements/{month}/companies")
	@Timed
	public List<PartnerDTO> getAllCompaniesForStatements(@PathVariable String month) {
		log.debug("REST request to get all companies for statements");
		return statementService.findAllCompaniesForStatement(month);
	}
	
	/**
	 * DELETE /statements/:id : delete the "id" statement.
	 *
	 * @param id the id of the statementDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/statements/{id}")
	@Timed
	public ResponseEntity<Void> deleteStatement(@PathVariable Long id) {
		log.debug("REST request to delete Statement : {}", id);
		statementService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * SEARCH /_search/statements?query=:query : search for the statement
	 * corresponding to the query.
	 *
	 * @param query the query of the statement search
	 * @return the result of the search
	 */
	@GetMapping("/_search/statements")
	@Timed
	public List<StatementDTO> searchStatements(@RequestParam String query) {
		log.debug("REST request to search Statements for query {}", query);
		return statementService.search(query);
	}


	@GetMapping(value = "/statements/{month}/export/excel/{id}")
	@Timed
	public void exportStatementInExcel(@PathVariable String month, @PathVariable Long id, HttpServletResponse response) {
		XSSFWorkbook workbook = null;
		try {
			/* Logic to Export Excel */
			String fileName = "Bordereau-" + id + ".xlsx";

			workbook = statementService.getStatementExcelExport(id, month);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			out.flush();
		} catch (Exception ecx) {
			ecx.printStackTrace();
		} finally {
			if (null != workbook) {
				try {
					workbook.close();
				} catch (IOException eio) {
					log.error("Error Occurred while exporting to XLS ", eio);
				}
			}
		}
	}    

}
