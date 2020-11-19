package com.gaconnecte.auxilium.service.prm;

import java.util.Set;

import com.gaconnecte.auxilium.service.prm.dto.RulesDTO;

public interface RulesService {

   Set<RulesDTO> findAll();
   Set<RulesDTO> findByCode(String code);
}