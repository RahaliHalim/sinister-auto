package com.gaconnecte.auxilium.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
import com.gaconnecte.auxilium.service.mapper.UserPartnerModeMapper;
import com.gaconnecte.auxilium.service.mapper.AgencyMapper;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.Agency;
import com.gaconnecte.auxilium.repository.UserPartnerModeRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

/**
 * Service Implementation for managing UserExtra.
 */
@Service
@Transactional
public class UserPartnerModeService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private final UserPartnerModeRepository userPartnerModeRepository;
    private final PartnerMapper partnerMapper;
    private final AgencyMapper agencyMapper;
    private final UserPartnerModeMapper userPartnerModeMapper;

	public UserPartnerModeService(UserPartnerModeRepository userPartnerModeRepository, PartnerMapper partnerMapper, AgencyMapper agencyMapper, UserPartnerModeMapper userPartnerModeMapper) {
		this.userPartnerModeRepository = userPartnerModeRepository;
        this.partnerMapper = partnerMapper;
        this.agencyMapper = agencyMapper;
        this.userPartnerModeMapper = userPartnerModeMapper;
	}

	@Transactional(readOnly = true)
	public Set<PartnerDTO> findPartnerByUser(Long idUser) {
		
		Set<Partner> usersPartner = userPartnerModeRepository.findPartnerByUser(idUser);
      if(CollectionUtils.isNotEmpty(usersPartner)) {
           return usersPartner.stream().map(partnerMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
	}
    @Transactional(readOnly = true)
	public Set<AgencyDTO> findAgencyByUser(Long idUser) {
		
		Set<Agency> usersAgency = userPartnerModeRepository.findAgencyByUser(idUser);
      if(CollectionUtils.isNotEmpty(usersAgency)) {
           return usersAgency.stream().map(agencyMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
	}
    
    @Transactional(readOnly = true)
	public Set<UserPartnerModeDTO> findUserPartnerModeByPartnerAndAgencyAndMode(Long idPartner, Long modeId) {
    	logger.info("Listes users partner modes by Partner Mode Agence Profile : {},{},{},{} ",idPartner,modeId);
		Set<UserPartnerMode> usersPartnerModes = userPartnerModeRepository.findUserPartnerModeByPartnerAndAgencyAndMode(idPartner, modeId);
		logger.info("Listes users partner modes by Partner Mode Agence Profile : {}", usersPartnerModes.size());
		if(CollectionUtils.isNotEmpty(usersPartnerModes)) {
           return usersPartnerModes.stream().map(userPartnerModeMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
	}
    
    @Transactional(readOnly = true)
	public Set<UserPartnerModeDTO> findUserPartnerModeByPartner(Long idPartner) {
    	logger.info("Listes users partner modes by Partner Mode Agence Profile : {},{},{},{} ",idPartner);
		Set<UserPartnerMode> usersPartnerModes = userPartnerModeRepository.findUserPartnerModeByPartner(idPartner);
		if(CollectionUtils.isNotEmpty(usersPartnerModes)) {
           return usersPartnerModes.stream().map(userPartnerModeMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
	}
    
    @Transactional(readOnly = true)
	public Set<UserPartnerModeDTO> findAllUsersByPartnerAndMode(Long idPartner, Long modeId) {
    	logger.info("Listes users partner modes by Partner Mode Agence Profile : {},{},{},{} ",idPartner,modeId);
		Set<UserPartnerMode> usersPartnerModes = userPartnerModeRepository.findAllUsersByPartnerAndMode(idPartner, modeId);
		logger.info("Listes users partner modes by Partner Mode Agence Profile : {}", usersPartnerModes.size());
		if(CollectionUtils.isNotEmpty(usersPartnerModes)) {
           return usersPartnerModes.stream().map(userPartnerModeMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
	}

}