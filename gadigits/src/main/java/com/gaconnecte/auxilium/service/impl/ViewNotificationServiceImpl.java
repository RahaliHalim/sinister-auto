package com.gaconnecte.auxilium.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.ViewNotificationRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.ViewNotificationDTO;
import com.gaconnecte.auxilium.service.dto.ViewNotificationService;
import com.gaconnecte.auxilium.service.mapper.ViewNotifcationMapper;

@Service
@Transactional
public class ViewNotificationServiceImpl implements ViewNotificationService{

	private final Logger log = LoggerFactory.getLogger(RefNotifAlertServiceImpl.class);
	
	private final ViewNotificationRepository viewNotificationRepository;
	
	private final UserRepository userRepository;
	
	private final ViewNotifcationMapper viewNotifcationMapper;

	

	public ViewNotificationServiceImpl(ViewNotificationRepository viewNotificationRepository,
			UserRepository userRepository, ViewNotifcationMapper viewNotifcationMapper) {
		super();
		this.viewNotificationRepository = viewNotificationRepository;
		this.userRepository = userRepository;
		this.viewNotifcationMapper = viewNotifcationMapper;
	}



	@Override
	public Set<ViewNotificationDTO> findAllNotificationByUser(String type) {
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);

		log.debug("Request to get all ViewNotificationDTO");
		return viewNotificationRepository.findAllNotificationByUser(user.getId(),type).stream().map(viewNotifcationMapper::toDto)
				.collect(Collectors.toCollection(HashSet::new));
       // return viewNotifcationMapper.toDto((viewNotificationRepository.findAllNotificationByUser(user.getId(),type)));
	}
}
