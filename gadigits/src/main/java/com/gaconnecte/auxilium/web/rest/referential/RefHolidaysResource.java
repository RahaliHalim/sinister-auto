package com.gaconnecte.auxilium.web.rest.referential;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.referential.RefHolidaysService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.referential.dto.RefHolidaysDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing RefHolidays.
 */
@RestController
@RequestMapping("/api")
public class RefHolidaysResource {

    private final Logger log = LoggerFactory.getLogger(RefHolidaysResource.class);

    private static final String ENTITY_NAME = "refHolidays";

    private final RefHolidaysService refHolidaysService;
    
    private final HistoryService historyService;
    public RefHolidaysResource(RefHolidaysService refHolidaysService, HistoryService historyService) {
        this.refHolidaysService = refHolidaysService;
        this.historyService = historyService;
    }

    /**
     * POST  /ref-holidays : Create a new refHolidays.
     *
     * @param refHolidaysDTO the refHolidaysDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refHolidaysDTO, or with status 400 (Bad Request) if the refHolidays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ref-holidays")
    @Timed
    public ResponseEntity<RefHolidaysDTO> createRefHolidays(@Valid @RequestBody RefHolidaysDTO refHolidaysDTO) throws URISyntaxException {
        log.debug("REST request to save RefHolidays : {}", refHolidaysDTO);
        if (refHolidaysDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refHolidays cannot already have an ID")).body(null);
        }
        RefHolidaysDTO result = refHolidaysService.save(refHolidaysDTO);
       // historyService.historysave("Jour Ferier",  result.getId(), "CREATION");
        return ResponseEntity.created(new URI("/api/ref-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-holidays : Updates an existing refHolidays.
     *
     * @param refHolidaysDTO the refHolidaysDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refHolidaysDTO,
     * or with status 400 (Bad Request) if the refHolidaysDTO is not valid,
     * or with status 500 (Internal Server Error) if the refHolidaysDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ref-holidays")
    @Timed
    public ResponseEntity<RefHolidaysDTO> updateRefHolidays(@Valid @RequestBody RefHolidaysDTO refHolidaysDTO) throws URISyntaxException {
        log.debug("REST request to update RefHolidays : {}", refHolidaysDTO);
        if (refHolidaysDTO.getId() == null) {
            return createRefHolidays(refHolidaysDTO);
        }
        RefHolidaysDTO result = refHolidaysService.save(refHolidaysDTO);
       // historyService.historysave("Jour Ferier", result.getId(), "UPDATE");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refHolidaysDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ref-holidays : get all the refHolidayss.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refHolidayss in body
     */
    @GetMapping("/ref-holidays")
    @Timed
    public List<RefHolidaysDTO> getAllRefHolidays() {
        log.debug("REST request to get a page of RefHolidays");
        return refHolidaysService.findAll();
    }

    /**
     * GET  /ref-holidays : get all the refHolidayss.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refHolidayss in body
     */
    @PostMapping("/ref-holidays/search")
    @Timed
    public ResponseEntity<Set<RefHolidaysDTO>> getAllRefHolidaysByLabelOrDate(@Valid @RequestBody RefHolidaysDTO refHolidaysDTO) {
        log.debug("REST request to get a page of RefHolidays");
        Set<RefHolidaysDTO> holidays = refHolidaysService.findByLabelOrDate(refHolidaysDTO);
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }
    
    /**
     * GET  /ref-holidays/:id : get the "id" refHolidays.
     *
     * @param id the id of the refHolidaysDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refHolidaysDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-holidays/{id}")
    @Timed
    public ResponseEntity<RefHolidaysDTO> getRefHolidays(@PathVariable Long id) {
        log.debug("REST request to get RefHolidays : {}", id);
        RefHolidaysDTO refHolidaysDTO = refHolidaysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refHolidaysDTO));
    }

     /**
     * GET  ferier/ref-holidays/:date : get the "date" refHolidays.
     *
     * @param date the date of the refHolidaysDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refHolidaysDTO, or with status 404 (Not Found)
     */
    @GetMapping("ferier/ref-holidays/{date}")
    @Timed
    public ResponseEntity<RefHolidaysDTO> getRefHolidaysByDate(@PathVariable LocalDate date) {
        log.debug("REST request to get RefHolidays : {}", date);
        RefHolidaysDTO refHolidaysDTO = refHolidaysService.findByDate(date);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refHolidaysDTO));
    }

    /**
     * DELETE  /ref-holidays/:id : delete the "id" refHolidays.
     *
     * @param id the id of the refHolidaysDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-holidays/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefHolidays(@PathVariable Long id) {
        log.debug("REST request to delete RefHolidays : {}", id);
        refHolidaysService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
