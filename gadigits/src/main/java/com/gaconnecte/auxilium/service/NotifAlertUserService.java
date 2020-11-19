package com.gaconnecte.auxilium.service;

import java.util.List;

import com.gaconnecte.auxilium.service.dto.NotifAlertUserDTO;

public interface NotifAlertUserService {

	NotifAlertUserDTO save(NotifAlertUserDTO[] notifAlertUserDTO);

	NotifAlertUserDTO update(NotifAlertUserDTO notifAlertUserDTO);

	List<NotifAlertUserDTO> findAll();

	NotifAlertUserDTO findOne(Long id);

	void delete(Long id);

	public List<NotifAlertUserDTO> readAllPrestTrait(Long pecId);

	public NotifAlertUserDTO readNotifForUser(Long pecId, Integer stepId, Long userId);

	NotifAlertUserDTO readNotifCancelReparateur(Long pecId, Integer stepId, Long userId);

}
