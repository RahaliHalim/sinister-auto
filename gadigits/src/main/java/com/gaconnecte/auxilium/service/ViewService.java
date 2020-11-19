/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.repository.ViewExpertRepository;
import com.gaconnecte.auxilium.repository.ViewReparatorRepository;
import com.gaconnecte.auxilium.repository.ViewUserRepository;
import com.gaconnecte.auxilium.service.dto.ViewExpertDTO;
import com.gaconnecte.auxilium.service.dto.ViewReparatorDTO;
import com.gaconnecte.auxilium.service.dto.ViewUserDTO;
import com.gaconnecte.auxilium.service.mapper.ViewExpertMapper;
import com.gaconnecte.auxilium.service.mapper.ViewReparatorMapper;
import com.gaconnecte.auxilium.service.mapper.ViewUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hannibaal
 */
/**
 * Service Implementation for managing view.
 */
@Service
@Transactional
public class ViewService {

    private final Logger log = LoggerFactory.getLogger(ViewService.class);

    private final ViewUserRepository viewUserRepository;
    private final ViewUserMapper viewUserMapper;

    private final ViewExpertRepository viewExpertRepository;
    private final ViewExpertMapper viewExpertMapper;

    private final ViewReparatorRepository viewReparatorRepository;
    private final ViewReparatorMapper viewReparatorMapper;
    
    public ViewService(ViewUserRepository viewUserRepository, ViewUserMapper viewUserMapper,
            ViewExpertRepository viewExpertRepository, ViewExpertMapper viewExpertMapper,
            ViewReparatorRepository viewReparatorRepository, ViewReparatorMapper viewReparatorMapper) {
        this.viewUserRepository = viewUserRepository;
        this.viewUserMapper = viewUserMapper;       
        this.viewExpertRepository = viewExpertRepository;
        this.viewExpertMapper = viewExpertMapper;       
        this.viewReparatorRepository = viewReparatorRepository;
        this.viewReparatorMapper = viewReparatorMapper;       
    }

    /**
     * Get all user lines.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewUserDTO> findAllUsers() {
        log.debug("Request to get all view Users.");
        return viewUserRepository.findAll().stream()
                .map(viewUserMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all expert lines.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewExpertDTO> findAllExperts() {
        log.debug("Request to get all view Experts.");
        return viewExpertRepository.findAll().stream()
                .map(viewExpertMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all reparator lines.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewReparatorDTO> findAllReparators() {
        log.debug("Request to get all view reparators.");
        return viewReparatorRepository.findAll().stream()
                .map(viewReparatorMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
}
