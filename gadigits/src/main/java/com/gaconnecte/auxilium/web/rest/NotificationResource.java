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
import com.gaconnecte.auxilium.service.NotificationService;
import com.gaconnecte.auxilium.service.dto.NotificationDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class NotificationResource {

	private final Logger log = LoggerFactory.getLogger(NotificationResource.class);
	private static final String ENTITY_NAME = "notification";
	private final NotificationService notificationService;
	
	public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
	
	
	/*@GetMapping("/notifications/{commentaire}/{isClosed}")
    @Timed
    public ResponseEntity<NotificationDTO> createNotification(@PathVariable String commentaire, @PathVariable Boolean isClosed ) throws URISyntaxException {
		log.debug("Test");
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setCommentaire(commentaire);
		notificationDTO.setIsClosed(isClosed);
		notificationDTO.setJhiDate(ZonedDateTime.now());
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }*/

    @PostMapping("/notifications")
    @Timed
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) throws URISyntaxException {
		log.debug("save notification");
        notificationDTO.setJhiDate(ZonedDateTime.now());
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
	
	
	@GetMapping("/notifications")
    @Timed
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Notifications");
        Page<NotificationDTO> page = notificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
	
	
	@GetMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Integer id) {
        log.debug("REST request to get Notification : {}", id);
        NotificationDTO notificationDTO = notificationService.findOne(id);
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notificationDTO));
    }
	
	@DeleteMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        log.debug("REST request to delete Notification : {}", id);
        notificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
	
	
}