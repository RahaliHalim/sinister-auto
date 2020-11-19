package com.gaconnecte.auxilium.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewContratService;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDT;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.RechercheDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewContratDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import com.gaconnecte.auxilium.service.dto.ViewContratDT;

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

@RestController
@RequestMapping("/api/view")
public class ViewContratResource {

	private final Logger log = LoggerFactory.getLogger(ViewContratResource.class);
	private static final String ENTITY_NAME = "viewContrat";

	private final ViewContratService viewContratService;

	@Autowired
	private LoggerService loggerService;

	public ViewContratResource(ViewContratService viewContratService) {

		this.viewContratService = viewContratService;

	}

	@GetMapping("/contrat")
	@Timed
	public List<ViewContratDTO> getAllContrat() {
		log.debug("REST request to get all contrat line");
		return viewContratService.findAll();
	}

	@PostMapping("/contrat/page")
	@Timed
	public ResponseEntity<ViewContratDT> getAllContratTest(@RequestBody DatatablesRequest datatablesRequest) {

		ViewContratDT dt = new ViewContratDT();
		dt.setRecordsFiltered(viewContratService.getCountsWithFiltter(datatablesRequest.getSearchValue()));
		dt.setRecordsTotal(viewContratService.getCount());
		try {
			final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength()))
					.intValue();
			final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
			List<ViewContratDTO> dtos = viewContratService.findAll(pr).getContent();
			if (CollectionUtils.isNotEmpty(dtos)) {
				dt.setData(dtos);
				return ResponseEntity.ok().body(dt);
			} else {
				return ResponseEntity.ok().body(dt);
			}

		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/contrat/searchPage")
	@Timed
	public ResponseEntity<ViewContratDT> findContratServicesd(@RequestBody SearchDTO searchDTO) {
		System.out.println("test1---------------------------------------------------");
		DatatablesRequest datatablesRequest = searchDTO.getDataTablesParameters();
		System.out.println("test2---------------------------------------------------");
		ViewContratDT dt = new ViewContratDT();
		dt.setRecordsFiltered(viewContratService.getCountsWithFiltter(datatablesRequest.getSearchValue()));
		dt.setRecordsTotal(viewContratService.getCount());

		try {
			final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength()))
					.intValue();
			final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
			List<ViewContratDTO> contrat = viewContratService.findContrat(searchDTO, pr).getContent();
			if (CollectionUtils.isNotEmpty(contrat)) {
				dt.setData(contrat);
				System.out.println("test3---------------------------------------------------");
				return ResponseEntity.ok().body(dt);
			} else {
				System.out.println("test4---------------------------------------------------");
				return ResponseEntity.ok().body(dt);
			}

		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			System.out.println("test5---------------------------------------------------");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/contrat")
	@Timed
	public Set<ViewContratDTO> findContratServices(@RequestBody SearchDTO searchDTO) {

		return viewContratService.findContrat(searchDTO);
	}

	@GetMapping(value = "/contrat/export/excel/{search}") 
	@Timed
	public void exportPoliciesToExcel(@PathVariable String search, HttpServletResponse response) {
		log.debug("REST request to export all contrat into excel {}", search);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
		XSSFWorkbook workbook = null;
		HttpHeaders headers = new HttpHeaders();
		if (search.equals("-1"))
			search = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
		List<ViewContratDTO> dtos = viewContratService.findAll();
		try {
			/* Logic to Export Excel */
			LocalDateTime now = LocalDateTime.now();
			String fileName = "Contrats" + now.format(formatter) + ".xlsx";

			workbook = (XSSFWorkbook) ExcelUtil.getSouscriptionsExcelExport(dtos);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			out.flush();
		} catch (Exception ecx) {
			// return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
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
