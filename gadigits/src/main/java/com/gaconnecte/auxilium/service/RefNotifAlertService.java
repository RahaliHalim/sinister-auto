package com.gaconnecte.auxilium.service;


import com.gaconnecte.auxilium.service.dto.RefNotifAlertDTO;
import java.util.List;

public interface RefNotifAlertService {
	
	RefNotifAlertDTO save(RefNotifAlertDTO refNotifAlertDTO);
	
	List<RefNotifAlertDTO> findAll();
	
	RefNotifAlertDTO findOne(Long id);
	
	void delete(Long id);

}
