package com.gaconnecte.auxilium.service.prm.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.prm.Rules;
import com.gaconnecte.auxilium.repository.prm.RulesRepository;
import com.gaconnecte.auxilium.service.prm.RulesService;
import com.gaconnecte.auxilium.service.prm.dto.RulesDTO;
import com.gaconnecte.auxilium.service.prm.mapper.RulesMapper;

@Service
@Transactional
public class RulesServiceImpl implements RulesService {

   private final Logger log = LoggerFactory.getLogger(RulesServiceImpl.class);	
   private final RulesRepository rulesRepository;
   private final RulesMapper rulesMapper;

   public RulesServiceImpl(RulesRepository rulesRepository, RulesMapper rulesMapper){
     this.rulesRepository = rulesRepository;
     this.rulesMapper = rulesMapper;
   }

@Override
public Set<RulesDTO> findAll() {
	
    Set<Rules> rules = rulesRepository.findAllRules();
    if(CollectionUtils.isNotEmpty(rules)) {
    return rules.stream().map(rulesMapper::toDto).collect(Collectors.toSet());
    }
    return new HashSet<>();
}
@Override
public Set<RulesDTO> findByCode(String code) {

    Set<Rules> rules = rulesRepository.findByCode(code);
    if(CollectionUtils.isNotEmpty(rules)) {
    return rules.stream().map(rulesMapper::toDto).collect(Collectors.toSet());
    }
    return new HashSet<>();
}

}