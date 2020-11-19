package com.gaconnecte.auxilium.service.prm;

import java.util.Set;

import com.gaconnecte.auxilium.service.prm.dto.PartnerRulesDTO;

public interface PartnerRulesService {

   PartnerRulesDTO save(PartnerRulesDTO partnerRulesDTO);
   Set<PartnerRulesDTO> findAll();
   PartnerRulesDTO findOne(Long id);
   PartnerRulesDTO findPartnerRulesByPartnerAndMode(Long partnerId, Long modeId);
}