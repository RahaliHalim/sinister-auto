package com.gaconnecte.auxilium.web.rest;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.dto.ViewNotificationDTO;
import com.gaconnecte.auxilium.service.dto.ViewNotificationService;

@RestController
@RequestMapping("/api")
public class ViewNotificationResource {
	private final Logger log = LoggerFactory.getLogger(ViewNotificationResource.class);
	private static final String ENTITY_NAME = "viewNotification";
	private final ViewNotificationService viewNotificationService;
	public ViewNotificationResource(ViewNotificationService viewNotificationService) {
		this.viewNotificationService = viewNotificationService;
	}
	@GetMapping("/view-notification/{type}")
    @Timed
    public Set<ViewNotificationDTO> getAllNotifications(@PathVariable String type) {
        log.debug("REST request to get of Notifications");
        Set<ViewNotificationDTO> page = viewNotificationService.findAllNotificationByUser(type);
        return page;
    }
}
