package com.gaconnecte.auxilium.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.gaconnecte.auxilium.domain.NotifAlertUser;
import com.gaconnecte.auxilium.domain.RefNotifAlert;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.repository.NotifAlertUserRepository;

import com.gaconnecte.auxilium.service.NotifAlertUserService;
import com.gaconnecte.auxilium.service.dto.NotifAlertUserDTO;

import com.gaconnecte.auxilium.service.mapper.NotifAlertUserMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NotifAlertUserServiceImpl implements NotifAlertUserService {

	private final Logger log = LoggerFactory.getLogger(NotifAlertUserServiceImpl.class);

	private final NotifAlertUserRepository notifAlertUserRepository;

	private final NotifAlertUserMapper notifAlertUserMapper;

	public NotifAlertUserServiceImpl(NotifAlertUserRepository notifAlertUserRepository,
			NotifAlertUserMapper notifAlertUserMapper) {
		this.notifAlertUserRepository = notifAlertUserRepository;
		this.notifAlertUserMapper = notifAlertUserMapper;
	}

	@Override
	public NotifAlertUserDTO save(NotifAlertUserDTO[] notifAlertUserDTO) {
		log.debug("Request to save NotifAlertUser : {}", notifAlertUserDTO);
		NotifAlertUserDTO result = null;
		for (NotifAlertUserDTO notifAlerte : notifAlertUserDTO) {
			System.out.println("id source****" + notifAlerte.getSource());
			notifAlerte.setSendingDate(LocalDateTime.now());
			NotifAlertUser notifAlertUser = notifAlertUserMapper.toEntity(notifAlerte);
			notifAlertUser.setNotification(new RefNotifAlert(notifAlerte.getNotification().getId()));
			notifAlertUser.setSource(new UserExtra(notifAlerte.getSource()));
			notifAlertUser.setDestination(new UserExtra(notifAlerte.getDestination()));
			notifAlertUser = notifAlertUserRepository.save(notifAlertUser);
			result = notifAlertUserMapper.toDto(notifAlertUser);

		}
		return result;
	}

	@Override
	public NotifAlertUserDTO update(NotifAlertUserDTO notifAlertUserDTO) {
		log.debug("Request to save NotifAlertUser : {}", notifAlertUserDTO);
		NotifAlertUser notifAlertUser = notifAlertUserMapper.toEntity(notifAlertUserDTO);
		notifAlertUser = notifAlertUserRepository.save(notifAlertUser);
		NotifAlertUserDTO result = notifAlertUserMapper.toDto(notifAlertUser);
		return result;
	}

	@Override
	public List<NotifAlertUserDTO> findAll() {
		log.debug("Request to get all NotifAlertUser");
		List<NotifAlertUser> list = notifAlertUserRepository.findAllNotificationsNotRead();
		log.debug("Request to get all NotifAlertUserLength", list.size());
		return notifAlertUserMapper.toDto(list);
	}

	@Override
	public List<NotifAlertUserDTO> readAllPrestTrait(Long pecId) {
		log.debug("Request to get all NotifTRead {}, ", pecId);
		List<NotifAlertUser> list = notifAlertUserRepository.findAllNotificationsNotReadWithoutUser();
		List<NotifAlertUser> listToRead = new ArrayList<NotifAlertUser>();
		for (NotifAlertUser notif : list) {
			try {
				JSONObject notifSettings = new JSONObject(String.valueOf(notif.getSettings()));
				Long sinisterPecId = notifSettings.getLong("sinisterPecId");
				String typeNotification = (String) notifSettings.get("typenotification");
				String notifType = "notifSendDemandPec";
				if (pecId.equals(sinisterPecId) && notifType.equals(typeNotification)) {
					listToRead.add(notif);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		for (NotifAlertUser notifToRead : listToRead) {
			notifToRead.setRead(true);
			notifAlertUserRepository.save(notifToRead);
		}
		return notifAlertUserMapper.toDto(listToRead);
	}

	@Override
	public NotifAlertUserDTO readNotifForUser(Long pecId, Integer stepId, Long userId) {
		log.debug("Request to get all NotifTRead {}, ", pecId);
		List<NotifAlertUser> list = notifAlertUserRepository.findNotificationsForUser(userId);
		NotifAlertUser notifToRead = new NotifAlertUser();
		for (NotifAlertUser notif : list) {
			try {
				JSONObject notifSettings = new JSONObject(String.valueOf(notif.getSettings()));
				Long sinisterPecId = notifSettings.getLong("sinisterPecId");
				Integer step = notifSettings.getInt("stepPecId");
				if (pecId.equals(sinisterPecId) && stepId.equals(step)) {
					notifToRead = notif;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (notifToRead.getId() != null) {
			notifToRead.setRead(true);
			notifAlertUserRepository.save(notifToRead);
		}
		return notifAlertUserMapper.toDto(notifToRead);
	}

	@Override
	public NotifAlertUserDTO readNotifCancelReparateur(Long pecId, Integer stepId, Long userId) {
		log.debug("Request to get all NotifTRead {}, ", pecId);
		List<NotifAlertUser> list = notifAlertUserRepository.findNotificationsForUser(userId);
		NotifAlertUser notifToRead = new NotifAlertUser();
		for (NotifAlertUser notif : list) {
			try {
				JSONObject notifSettings = new JSONObject(String.valueOf(notif.getSettings()));
				Long sinisterPecId = notifSettings.getLong("sinisterPecId");
				Integer step = notifSettings.getInt("stepPecId");
				if (pecId.equals(sinisterPecId) && stepId.equals(step)) {
					notifToRead = notif;
					if (notifToRead.getId() != null) {
						notifToRead.setRead(true);
						notifAlertUserRepository.save(notifToRead);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return notifAlertUserMapper.toDto(notifToRead);
	}

	@Override
	public NotifAlertUserDTO findOne(Long id) {
		log.debug("Request to get NotifAlertUser : {}", id);
		NotifAlertUser refNotifAlert = notifAlertUserRepository.findOne(id);
		return notifAlertUserMapper.toDto(refNotifAlert);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete NotifAlertUser : {}", id);
		notifAlertUserRepository.delete(id);

	}

}
