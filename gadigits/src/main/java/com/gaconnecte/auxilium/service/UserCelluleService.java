package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.service.dto.UserCelluleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


/**
 * Service Interface for managing UserCellule.
 */
public interface UserCelluleService {

    /**
     * Save a userCellule.
     *
     * @param userCelluleDTO the entity to save
     * @return the persisted entity
     */
    UserCelluleDTO save(UserCelluleDTO userCelluleDTO);
    
    
    UserCelluleDTO update(UserCelluleDTO userCelluleDTO);

    /**
     *  Get all the userCellules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserCelluleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" userCellule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserCelluleDTO findOne(Long id);

    /**
     *  Delete the "id" userCellule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    UserCelluleDTO findByUserAndCellule(Long userId, Long celluleId);

    List<UserCelluleDTO> findByUser(Long userId);
}
