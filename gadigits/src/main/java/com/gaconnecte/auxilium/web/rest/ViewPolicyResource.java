package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.ViewPolicyService;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDT;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDT;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing ViewPolicy.
 */
@RestController
@RequestMapping("/api")
public class ViewPolicyResource {

    private final Logger log = LoggerFactory.getLogger(ViewPolicyResource.class);

    private static final String ENTITY_NAME = "viewPolicy";

    private final ViewPolicyService viewPolicyService;

    public ViewPolicyResource(ViewPolicyService viewPolicyService) {
        this.viewPolicyService = viewPolicyService;
    }

    /**
     * POST  /view-policies : Create a new viewPolicy.
     *
     * @param viewPolicyDTO the viewPolicyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new viewPolicyDTO, or with status 400 (Bad Request) if the viewPolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/view-policies")
    @Timed
    public ResponseEntity<ViewPolicyDTO> createViewPolicy(@RequestBody ViewPolicyDTO viewPolicyDTO) throws URISyntaxException {
        log.debug("REST request to save ViewPolicy : {}", viewPolicyDTO);
        if (viewPolicyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new viewPolicy cannot already have an ID")).body(null);
        }
        ViewPolicyDTO result = viewPolicyService.save(viewPolicyDTO);
        return ResponseEntity.created(new URI("/api/view-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /view-policies : Updates an existing viewPolicy.
     *
     * @param viewPolicyDTO the viewPolicyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated viewPolicyDTO,
     * or with status 400 (Bad Request) if the viewPolicyDTO is not valid,
     * or with status 500 (Internal Server Error) if the viewPolicyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/view-policies")
    @Timed
    public ResponseEntity<ViewPolicyDTO> updateViewPolicy(@RequestBody ViewPolicyDTO viewPolicyDTO) throws URISyntaxException {
        log.debug("REST request to update ViewPolicy : {}", viewPolicyDTO);
        if (viewPolicyDTO.getId() == null) {
            return createViewPolicy(viewPolicyDTO);
        }
        ViewPolicyDTO result = viewPolicyService.save(viewPolicyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, viewPolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /view-policies : get all the viewPolicies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of viewPolicies in body
     */
    @GetMapping("/view-policies")
    @Timed
    public List<ViewPolicyDTO> getAllViewPolicies() {
        log.debug("REST request to get all ViewPolicies");
        return viewPolicyService.findAll();
    }

    /**
     * GET  /view-policies : get all the viewPolicies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of viewPolicies in body
     */
    @PostMapping("/view-policies/page")
    @Timed
    public ResponseEntity<ViewPolicyDT> getAllViewPolicies(@RequestBody DatatablesRequest datatablesRequest) {
        log.debug("REST request to get all ViewPolicies");
        ViewPolicyDT dt = new ViewPolicyDT();
        dt.setRecordsFiltered(viewPolicyService.getCountPoliciesWithFiltter(datatablesRequest.getSearchValue()));
        dt.setRecordsTotal(viewPolicyService.getCountPolicies());

        try {
            final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
            final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
            List<ViewPolicyDTO> dtos = viewPolicyService.findAll(datatablesRequest.getSearchValue(), pr).getContent();
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
    
    @GetMapping(value = "/view-policies/export/excel/{search}")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportPoliciesToExcel(@PathVariable String search, HttpServletResponse response) {
        log.debug("REST request to export all ViewPolicies into excel {}", search);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        if(search.equals("-1")) search = null;
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        final PageRequest pr = new PageRequest(0, Integer.MAX_VALUE);
        List<ViewPolicyDTO> dtos = viewPolicyService.findAll(search, pr).getContent();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "Contrats" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getPoliciesExcelExport(dtos);
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
     * GET  /view-policies/:id : get the "id" viewPolicy.
     *
     * @param id the id of the viewPolicyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the viewPolicyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/view-policies/{id}")
    @Timed
    public ResponseEntity<ViewPolicyDTO> getViewPolicy(@PathVariable Long id) {
        log.debug("REST request to get ViewPolicy : {}", id);
        ViewPolicyDTO viewPolicyDTO = viewPolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(viewPolicyDTO));
    }

    /**
     * DELETE  /view-policies/:id : delete the "id" viewPolicy.
     *
     * @param id the id of the viewPolicyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/view-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteViewPolicy(@PathVariable Long id) {
        log.debug("REST request to delete ViewPolicy : {}", id);
        viewPolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/view-policies?query=:query : search for the viewPolicy corresponding
     * to the query.
     *
     * @param query the query of the viewPolicy search
     * @return the result of the search
     */
    @GetMapping("/_search/view-policies")
    @Timed
    public List<ViewPolicyDTO> searchViewPolicies(@RequestParam String query) {
        log.debug("REST request to search ViewPolicies for query {}", query);
        return viewPolicyService.search(query);
    }

}
