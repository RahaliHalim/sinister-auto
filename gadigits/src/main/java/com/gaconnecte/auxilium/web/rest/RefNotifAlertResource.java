package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefNotifAlertService;
import com.gaconnecte.auxilium.service.dto.RefNotifAlertDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class RefNotifAlertResource {

	private final Logger log = LoggerFactory.getLogger(RefNotifAlertResource.class);
	private static final String ENTITY_NAME = "refNotifAlert";
	private final RefNotifAlertService refNotifAlertService;
	
	public RefNotifAlertResource(RefNotifAlertService refNotifAlertService) {
        this.refNotifAlertService = refNotifAlertService;
    }

    @PostMapping("/ref-notif-alert")
    @Timed
    public ResponseEntity<RefNotifAlertDTO> createNotification(@Valid @RequestBody RefNotifAlertDTO refNotifAlertDTO) throws URISyntaxException {
		log.debug("save notification");
        refNotifAlertDTO.setSendingDate(ZonedDateTime.now());
        RefNotifAlertDTO result = refNotifAlertService.save(refNotifAlertDTO);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
	
	
	@GetMapping("/ref-notif-alert")
    @Timed
    public List<RefNotifAlertDTO> getAllNotifications() {
        log.debug("REST request to get a page of Notifications");
        List<RefNotifAlertDTO> page = refNotifAlertService.findAll();
        return page;
    }
	
	
	@GetMapping("/ref-notif-alert/{id}")
    @Timed
    public ResponseEntity<RefNotifAlertDTO> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        RefNotifAlertDTO refNotifAlertDTO = refNotifAlertService.findOne(id);
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refNotifAlertDTO));
    }
	
	@DeleteMapping("/ref-notif-alert/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);
        refNotifAlertService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
	
	
}