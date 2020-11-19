package com.gaconnecte.auxilium.service.prm.impl;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gaconnecte.auxilium.domain.prm.PartnerRules;
import com.gaconnecte.auxilium.repository.prm.PartnerRulesRepository;
import com.gaconnecte.auxilium.service.prm.PartnerRulesService;
import com.gaconnecte.auxilium.service.prm.dto.PartnerRulesDTO;
import com.gaconnecte.auxilium.service.prm.mapper.PartnerRulesMapper;


@Service
@Transactional
public class PartnerRulesServiceImpl implements PartnerRulesService {

   private final Logger log = LoggerFactory.getLogger(PartnerRulesServiceImpl.class);

   private final PartnerRulesRepository partnerRulesRepository;
   private final PartnerRulesMapper partnerRulesMapper;
  
   public PartnerRulesServiceImpl(PartnerRulesRepository partnerRulesRepository, PartnerRulesMapper partnerRulesMapper){
     this.partnerRulesRepository = partnerRulesRepository;
     this.partnerRulesMapper = partnerRulesMapper;
   }

  @Override
  public PartnerRulesDTO save(PartnerRulesDTO partnerRulesDTO) {
    
      PartnerRules partnerRules = partnerRulesMapper.toEntity(partnerRulesDTO);
      partnerRules = partnerRulesRepository.save(partnerRules);
      PartnerRulesDTO result = partnerRulesMapper.toDto(partnerRules);
          
      return result;
  }
  @Override
  public Set<PartnerRulesDTO> findAll() {
      log.debug("Request to get all Partner Rules");
      Set<PartnerRules> partnerRules = partnerRulesRepository.findAllPartnerRules();
      if(CollectionUtils.isNotEmpty(partnerRules)) {
        return partnerRules.stream().map(partnerRulesMapper::toDto).collect(Collectors.toSet());
      }
    return new HashSet<>();
  }
  @Override
  public PartnerRulesDTO findOne(Long id) {

      log.debug("Request to get Partner Rules : {}", id);
      PartnerRules partnerRules = partnerRulesRepository.findOne(id);
      return partnerRulesMapper.toDto(partnerRules);
  }
  @Override
  public PartnerRulesDTO findPartnerRulesByPartnerAndMode(Long partnerId, Long modeId){

      log.debug("Request to get Partner Rules By Partner and ModeId : {}", partnerId, modeId);
      PartnerRules partnerRules = partnerRulesRepository.findPartnerRulesByPartnerAndMode(partnerId, modeId);
      return partnerRulesMapper.toDto(partnerRules);
  }

}