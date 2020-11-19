package com.gaconnecte.auxilium.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.RefNotifAlert;
import com.gaconnecte.auxilium.repository.RefNotifAlertRepository;

import com.gaconnecte.auxilium.service.RefNotifAlertService;

import com.gaconnecte.auxilium.service.dto.RefNotifAlertDTO;

import com.gaconnecte.auxilium.service.mapper.RefNotifAlertMapper;
import java.util.List;

@Service
@Transactional
public class RefNotifAlertServiceImpl implements RefNotifAlertService {
	
	
	private final Logger log = LoggerFactory.getLogger(RefNotifAlertServiceImpl.class);
	
	private final RefNotifAlertRepository refNotifAlertRepository;
	
	private final RefNotifAlertMapper refNotifAlertMapper;
	
	
	public RefNotifAlertServiceImpl(RefNotifAlertRepository refNotifAlertRepository, RefNotifAlertMapper refNotifAlertMapper) {
        this.refNotifAlertRepository = refNotifAlertRepository;
        this.refNotifAlertMapper = refNotifAlertMapper;
    }

	@Override
	public RefNotifAlertDTO save(RefNotifAlertDTO refNotifAlertDTO) {
		log.debug("Request to save RefNotifAlert : {}", refNotifAlertDTO);
        RefNotifAlert refNotifAlert = refNotifAlertMapper.toEntity(refNotifAlertDTO);
        refNotifAlert = refNotifAlertRepository.save(refNotifAlert);
        RefNotifAlertDTO result = refNotifAlertMapper.toDto(refNotifAlert);
        return result;
	}

	@Override
	public List<RefNotifAlertDTO> findAll() {
		log.debug("Request to get all RefNotifAlert");
        return refNotifAlertMapper.toDto(refNotifAlertRepository.findAll());
	}

	@Override
	public RefNotifAlertDTO findOne(Long id) {
		log.debug("Request to get RefNotifAlert : {}", id);
		RefNotifAlert refNotifAlert = refNotifAlertRepository.findOne(id);
        return refNotifAlertMapper.toDto(refNotifAlert);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete RefNotifAlert : {}", id);
		refNotifAlertRepository.delete(id);
		
	}

}
