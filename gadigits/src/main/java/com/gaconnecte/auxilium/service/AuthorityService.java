package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.AuthorityDTO;
import com.gaconnecte.auxilium.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


/**
 * Service Interface for managing Apec.
 */
public interface AuthorityService {

    /**
     * Save a apec.
     *
     * @param apecDTO the entity to save
     * @return the persisted entity
     */
    AuthorityDTO save(AuthorityDTO authorityDTO);

    /**
     *  Get all the apecs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AuthorityDTO> findAll(Pageable pageable);

    AuthorityDTO findByName(String name);

    List<AuthorityDTO> findAuthorityInterne();

    List<AuthorityDTO> findAuthorityExterne();

    List<String> findAuthorityCelluleCombinaison();

    List<UserDTO> findUsersForRoleGestionnaire();

    List<String> findAuthorityActive();

    


 
}
