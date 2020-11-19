package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.AuthorityService;
import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.domain.Cellule;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserCellule;
import com.gaconnecte.auxilium.repository.AuthorityRepository;
import com.gaconnecte.auxilium.repository.CelluleRepository;
import com.gaconnecte.auxilium.service.dto.AuthorityDTO;
import com.gaconnecte.auxilium.service.dto.UserDTO;
import com.gaconnecte.auxilium.service.mapper.AuthorityMapper;
import com.gaconnecte.auxilium.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Apec.
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService{

    private final Logger log = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;
    
    @Autowired
    private CelluleRepository celluleRepository;

    @Autowired
    private UserMapper userMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    /**
     * Save a apec.
     *
     * @param AuthorityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AuthorityDTO save(AuthorityDTO authorityDTO) {
        log.debug("Request to save Authority : {}", authorityDTO);
        Authority authority = authorityMapper.toEntity(authorityDTO);
        authority = authorityRepository.save(authority);
        AuthorityDTO result = authorityMapper.toDto(authority);
        return result;
    }

    /**
     *  Get all the apecs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Authorities");
        return authorityRepository.findAll(pageable)
            .map(authorityMapper::toDto);
    }

    /**
     *  Get one authority by name.
     *
     *  @param name the name of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AuthorityDTO findByName(String nom) {
        log.debug("Request to get authority : {}", nom);
        Authority authority = authorityRepository.findByName(nom);
        return authorityMapper.toDto(authority);
    }

     @Override
    public List<AuthorityDTO> findAuthorityInterne() {
        log.debug("Request to get  authority");
        List<Authority> authorities = authorityRepository.findAuthorityInterne();
        return authorityMapper.toDto(authorities);
    }

    @Override
    public List<AuthorityDTO> findAuthorityExterne() {
        log.debug("Request to get  authority");
        List<Authority> authorities = authorityRepository.findAuthorityExterne();
        return authorityMapper.toDto(authorities);
    }

     @Override
    public List<String> findAuthorityCelluleCombinaison() {
        log.debug("Request to get  authority");
        List<String> authorityCellule = new ArrayList();
        List<AuthorityDTO> authoritiesInterne = this.findAuthorityInterne();
        List<AuthorityDTO> authoritiesExterne = this.findAuthorityExterne();
        List<Cellule> cellules = celluleRepository.findAllCellule();    
        for(int i=0; i<authoritiesExterne.size(); i++) {
            authorityCellule.add(authoritiesExterne.get(i).getName());
        }
        for(int i=0; i<authoritiesInterne.size(); i++) {
          for(int j=0; j<cellules.size(); j++) {
            authorityCellule.add(authoritiesInterne.get(i).getName() + "=>" + cellules.get(j).getName() );
          }
        }
        return authorityCellule;
    }

    
    @Override
    public List<UserDTO> findUsersForRoleGestionnaire() {
        log.debug("Request to get  user"); 
        List<User> users = new ArrayList();
        Authority authority = authorityRepository.findByName("ROLE_GESTIONNAIRE");
        List<UserCellule> usersCellules = new ArrayList(authority.getUsersCellules());
            for(int i=0; i<usersCellules.size(); i++) {
                users.add(usersCellules.get(i).getUser());
            }
            return userMapper.usersToUserDTOs(users);
}

	@Override
	public List<String> findAuthorityActive() {
		
		log.debug("Request to get  authority active ");
         List<String> authorityName = new ArrayList();
        List<Authority> authoritiesActive = authorityRepository.findAuthorityActive();
        for(int i=0; i<authoritiesActive.size(); i++) {
            authorityName.add(authoritiesActive.get(i).getName());
        }
        return authorityName;
	}

}
