package com.gaconnecte.auxilium.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.Notification;
import com.gaconnecte.auxilium.repository.NotificationRepository;

import com.gaconnecte.auxilium.service.NotificationService;

import com.gaconnecte.auxilium.service.dto.NotificationDTO;

import com.gaconnecte.auxilium.service.mapper.NotificationMapper;


@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	
	
	private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	private final NotificationRepository notificationRepository;
	
	private final NotificationMapper notificationMapper;
	
	
	public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

	@Override
	public NotificationDTO save(NotificationDTO notificationDTO) {
		log.debug("Request to save Notification : {}", notificationDTO);
		
        Notification notification = notificationMapper.toEntity(notificationDTO);
        notification = notificationRepository.save(notification);
        NotificationDTO result = notificationMapper.toDto(notification);
        return result;
	}

	@Override
	public Page<NotificationDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Notifications");
        return notificationRepository.findAll(pageable)
            .map(notificationMapper::toDto);
	}

	@Override
	public NotificationDTO findOne(Integer id) {
		log.debug("Request to get Notification : {}", id);
		Notification notification = notificationRepository.findOne(id);
        return notificationMapper.toDto(notification);
	}

	@Override
	public void delete(Integer id) {
		log.debug("Request to delete Notification : {}", id);
		notificationRepository.delete(id);
		
	}

}
