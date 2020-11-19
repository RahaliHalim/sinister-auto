package com.gaconnecte.auxilium.service.prm.impl;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gaconnecte.auxilium.domain.HistoryPartnerRules;
import com.gaconnecte.auxilium.repository.HistoryPartnerRulesRepository;
import com.gaconnecte.auxilium.service.prm.HistoryPartnerRulesService;
import com.gaconnecte.auxilium.service.prm.dto.HistoryPartnerRulesDTO;
import com.gaconnecte.auxilium.service.prm.mapper.HistoryPartnerRulesMapper;


@Service
@Transactional
public class HistoryPartnerRulesServiceImpl implements HistoryPartnerRulesService {

   private final Logger log = LoggerFactory.getLogger(HistoryPartnerRulesServiceImpl.class);

   private final HistoryPartnerRulesRepository historyPartnerRulesRepository;
   private final HistoryPartnerRulesMapper historyPartnerRulesMapper;
  
   public HistoryPartnerRulesServiceImpl(HistoryPartnerRulesRepository historyPartnerRulesRepository, HistoryPartnerRulesMapper historyPartnerRulesMapper){
     this.historyPartnerRulesRepository = historyPartnerRulesRepository;
     this.historyPartnerRulesMapper = historyPartnerRulesMapper;
   }

  @Override
  public HistoryPartnerRulesDTO save(HistoryPartnerRulesDTO historyPartnerRulesDTO) {
    
      HistoryPartnerRules historyPartnerRules = historyPartnerRulesMapper.toEntity(historyPartnerRulesDTO);
      historyPartnerRules = historyPartnerRulesRepository.save(historyPartnerRules);
      HistoryPartnerRulesDTO result = historyPartnerRulesMapper.toDto(historyPartnerRules);
          
      return result;
  }

}