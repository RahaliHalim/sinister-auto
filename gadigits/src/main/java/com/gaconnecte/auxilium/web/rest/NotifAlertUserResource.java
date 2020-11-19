package com.gaconnecte.auxilium.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.NotifAlertUserService;
import com.gaconnecte.auxilium.service.dto.NotifAlertUserDTO;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class NotifAlertUserResource {

	private final Logger log = LoggerFactory.getLogger(NotifAlertUserResource.class);
	private static final String ENTITY_NAME = "notifAlertUser";
	private final NotifAlertUserService notifAlertUserServiceService;
	private final UserRepository userRepository;
	
	public NotifAlertUserResource(NotifAlertUserService notifAlertUserServiceService, UserRepository userRepository) {
        this.notifAlertUserServiceService = notifAlertUserServiceService;
        this.userRepository = userRepository;
    }

    @PostMapping("/notif-alert-user")
    @Timed
    public ResponseEntity<NotifAlertUserDTO> createNotification(@Valid @RequestBody NotifAlertUserDTO[] notifAlertUserDTO) throws URISyntaxException {
		log.debug("save NotifAlertUser");
		log.debug("lenght NotifAlertUser ,{}", notifAlertUserDTO.length);
        NotifAlertUserDTO result = notifAlertUserServiceService.save(notifAlertUserDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    
    @PutMapping("/notif-alert-user")
    @Timed
    public ResponseEntity<NotifAlertUserDTO> updateNotification(@Valid @RequestBody NotifAlertUserDTO notifAlertUserDTO) throws URISyntaxException {
		log.debug("save NotifAlertUser");
		log.debug("lenght NotifAlertUser ,{}", notifAlertUserDTO);
        NotifAlertUserDTO result = notifAlertUserServiceService.update(notifAlertUserDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
	
	
	@GetMapping("/notif-alert-user")
    @Timed
    public List<NotifAlertUserDTO> getAllNotifications() {
        log.debug("REST request to get a page of NotifAlertUser");
        //String login = SecurityUtils.getCurrentUserLogin();
		//User user = userRepository.findOneUserByLogin(login);
        List<NotifAlertUserDTO> page = notifAlertUserServiceService.findAll();
        return page;
    }
	
	
	@GetMapping("/notif-alert-user/{id}")
    @Timed
    public ResponseEntity<NotifAlertUserDTO> getNotification(@PathVariable Long id) {
        log.debug("REST request to get NotifAlertUser : {}", id);
        NotifAlertUserDTO notifAlertUserDTO = notifAlertUserServiceService.findOne(id);
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notifAlertUserDTO));
    }
	
	@GetMapping("/notif-alert-user-to-read/{pecId}")
    @Timed
    public List<NotifAlertUserDTO> readNotification(@PathVariable Long pecId) {
		List<NotifAlertUserDTO> page = notifAlertUserServiceService.readAllPrestTrait(pecId);
		return page;
    }
	@GetMapping("/notif-alert-user-to-read-for-user/{pecId}/{stepId}/{userId}")
    @Timed
    public NotifAlertUserDTO readNotificationForUser(@PathVariable Long pecId, @PathVariable Integer stepId, @PathVariable Long userId) {
		NotifAlertUserDTO page = notifAlertUserServiceService.readNotifForUser(pecId, stepId, userId);
		return page;
    }

    @GetMapping("/notif-alert-user-to-read-for-user-reparateur/{pecId}/{stepId}/{userId}")
    @Timed
    public NotifAlertUserDTO readNotificationForUserReparateur(@PathVariable Long pecId, @PathVariable Integer stepId, @PathVariable Long userId) {
		NotifAlertUserDTO page = notifAlertUserServiceService.readNotifCancelReparateur(pecId, stepId, userId);
		return page;
    }
	
	@DeleteMapping("/notif-alert-user/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete NotifAlertUser : {}", id);
        notifAlertUserServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
	
	
}