package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.LienService;
import com.gaconnecte.auxilium.domain.Lien;
import com.gaconnecte.auxilium.domain.Cellule;
import com.gaconnecte.auxilium.domain.UserCellule;
import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.repository.LienRepository;

import com.gaconnecte.auxilium.service.dto.LienDTO;
import com.gaconnecte.auxilium.service.mapper.LienMapper;

import java.util.Set;
import java.util.HashSet;

import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.UserCelluleRepository;
import com.gaconnecte.auxilium.repository.CelluleRepository;
import com.gaconnecte.auxilium.repository.AuthorityRepository;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;


/**
 * Service Implementation for managing Lien.
 */
@Service
@Transactional
public class LienServiceImpl implements LienService {

    private final Logger log = LoggerFactory.getLogger(LienServiceImpl.class);

    private final LienRepository lienRepository;

    private final LienMapper lienMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserCelluleRepository userCelluleRepository;

    @Autowired
    private CelluleRepository celluleRepository;

    public LienServiceImpl(LienRepository lienRepository, LienMapper lienMapper) {
        this.lienRepository = lienRepository;
        this.lienMapper = lienMapper;
    }

    /**
     * Save a lien.
     *
     * @param lienDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LienDTO save(LienDTO lienDTO) {
        log.debug("Request to save Lien : {}", lienDTO);
        Lien lien = lienMapper.toEntity(lienDTO);
        lien = lienRepository.save(lien);
        return lienMapper.toDto(lien);
    }

    /**
     * Get all the liens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LienDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Liens");
        return lienRepository.findAll(pageable)
            .map(lienMapper::toDto);
    }

    /**
     * Get one lien by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LienDTO findOne(Long id) {
        log.debug("Request to get Lien : {}", id);
        Lien lien = lienRepository.findOneWithEagerRelationships(id);
        return lienMapper.toDto(lien);
    }

    /**
     * Delete the  lien by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lien : {}", id);
        lienRepository.delete(id);
    }

    
    @Override
    public List<LienDTO> findLienWithoutParent() {
        log.debug("Request to get  all liens without parent");
        List<Lien> lien = lienRepository.findLienWithoutParent();
        return lienMapper.toDto(lien);
    }

    /**
     *  Get all the liens by user.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
    */
    @Override
    public List<LienDTO> findLienByUser(Long parentId) {
        log.debug("Request to get Liens", parentId);
        String login = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userRepository.findOneByLoginIgnoreCase(login);
        List<UserCellule> userCellules = new ArrayList();
        List<Cellule> listeCellule = new ArrayList();
        List<Lien> listeLienForCellules = new ArrayList();
        List<Lien> listeLienForAuthorities = new ArrayList();
        List<Lien> listeLien = new ArrayList();
        List<Lien> listeLienPermis = new ArrayList();
        List<Lien> lienTotale = new ArrayList();
        List<Authority> authoritiesInternes= new ArrayList();
        if (user.isPresent()) {
            User utilisateur = user.get();
            List<Authority> authoritiesExterne = authorityRepository.findAuthorityExterneForUser(utilisateur.getId());
            for (int i = 0; i < authoritiesExterne.size(); i++) {
                listeLien.addAll(new ArrayList(authoritiesExterne.get(i).getLiens()));
            }
            userCellules = userCelluleRepository.findByUser(utilisateur.getId());
            if (userCellules.size() > 0) {
                for (int i = 0; i < userCellules.size(); i++) {
                    listeCellule.add(userCellules.get(i).getCellule());
                    authoritiesInternes.addAll(userCellules.get(i).getAuthorities());
                }
                for (int i = 0; i < listeCellule.size(); i++) {
                    listeLienForCellules.addAll(listeCellule.get(i).getLiens());
                }
                for (int i = 0; i < authoritiesInternes.size(); i++) {
                    listeLienForAuthorities.addAll(authoritiesInternes.get(i).getLiens());
                }


                listeLienForCellules.retainAll(listeLienForAuthorities);
            }
            listeLien.addAll(listeLienForCellules);
            Set<Lien> listeLienForUser = new HashSet<Lien>(listeLien);
            listeLienPermis = new ArrayList(listeLienForUser);
            for (int i = 0; i < listeLienPermis.size(); i++) {
                if (listeLienPermis.get(i).getParent().getId().equals(parentId)) {
                    lienTotale.add(listeLienPermis.get(i));
                }
            }
        }
        return lienMapper.toDto(lienTotale);
    }
}
