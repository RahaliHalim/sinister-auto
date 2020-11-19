package com.gaconnecte.auxilium.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.repository.referential.RefStatusSinisterRepository;

import com.gaconnecte.auxilium.service.referential.dto.RefStatusSinisterDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefStatusSinisterMapper;

@Service
@Transactional
public class RefStatusSinisterService {

	private final Logger log = LoggerFactory.getLogger(RefStatusSinisterService.class);
private final RefStatusSinisterRepository refStatusSinisterRepository;
private final RefStatusSinisterMapper refStatusSinisterMapper;
	
	
public RefStatusSinisterService ( RefStatusSinisterRepository refStatusSinisterRepository, RefStatusSinisterMapper refStatusSinisterMapper) {
	
	this.refStatusSinisterRepository = refStatusSinisterRepository;
	this.refStatusSinisterMapper = refStatusSinisterMapper;


		}


@Transactional(readOnly = true)
public List<RefStatusSinisterDTO> findAll() {
	return refStatusSinisterRepository.findAll().stream()
    .map(refStatusSinisterMapper::toDto)
    .collect(Collectors.toCollection(LinkedList::new));
			}


}
