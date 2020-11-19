/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.repository.ViewDetailQuotationRepository;
import com.gaconnecte.auxilium.service.dto.ViewDetailQuotationDTO;
import com.gaconnecte.auxilium.service.mapper.ViewDetailQuotationMapper;
import com.gaconnecte.auxilium.domain.view.ViewDetailQuotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing view ViewDetailQuotation.
 */
@Service
@Transactional
public class ViewDetailQuotationService {

    private final Logger log = LoggerFactory.getLogger(ViewDetailQuotationService.class);

    private final ViewDetailQuotationRepository viewDetailQuotationRepository;
    private final ViewDetailQuotationMapper viewDetailQuotationMapper;

    public ViewDetailQuotationService(ViewDetailQuotationRepository viewDetailQuotationRepository, ViewDetailQuotationMapper viewDetailQuotationMapper) {
        this.viewDetailQuotationRepository = viewDetailQuotationRepository;
        this.viewDetailQuotationMapper = viewDetailQuotationMapper;
        
    }

    @Transactional(readOnly = true)
	public ViewDetailQuotationDTO findViewDetailQuotationByPec(Long id) {
		log.debug("Request to get  all view detail devis By TYpe", id);
		ViewDetailQuotation detail = viewDetailQuotationRepository.findViewDetailQuotationByPec(id);
		return viewDetailQuotationMapper.toDto(detail);
    }

}
