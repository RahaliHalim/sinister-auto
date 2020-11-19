package com.gaconnecte.auxilium.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.RefAction;
import com.gaconnecte.auxilium.repository.RefActionRepository;

import com.gaconnecte.auxilium.service.RefActionService;

import com.gaconnecte.auxilium.service.dto.RefActionDTO;

import com.gaconnecte.auxilium.service.mapper.RefActionMapper;
import java.util.List;

@Service
@Transactional
public class RefActionServiceImpl implements RefActionService {
	
	
	private final Logger log = LoggerFactory.getLogger(RefActionServiceImpl.class);
	
	private final RefActionRepository refActionRepository;
	
	private final RefActionMapper refActionMapper;
	
	
	public RefActionServiceImpl(RefActionRepository refActionRepository, RefActionMapper refActionMapper) {
        this.refActionRepository = refActionRepository;
        this.refActionMapper = refActionMapper;
    }

	@Override
	public RefActionDTO save(RefActionDTO refActionDTO) {
		log.debug("Request to save RefNotifAlert : {}", refActionDTO);
        RefAction refAction = refActionMapper.toEntity(refActionDTO);
        refAction = refActionRepository.save(refAction);
        RefActionDTO result = refActionMapper.toDto(refAction);
        return result;
	}

	@Override
	public List<RefActionDTO> findAll() {
		log.debug("Request to get all RefNotifAlert");
        return refActionMapper.toDto(refActionRepository.findAll());
	}

	@Override
	public RefActionDTO findOne(Long id) {
		log.debug("Request to get RefNotifAlert : {}", id);
		RefAction refAction = refActionRepository.findOne(id);
        return refActionMapper.toDto(refAction);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete RefNotifAlert : {}", id);
		refActionRepository.delete(id);
		
	}

}
