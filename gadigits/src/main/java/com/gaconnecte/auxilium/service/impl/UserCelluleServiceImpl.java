package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.UserCelluleService;
import com.gaconnecte.auxilium.domain.UserCellule;
import com.gaconnecte.auxilium.repository.UserCelluleRepository;
import com.gaconnecte.auxilium.service.dto.UserCelluleDTO;
import com.gaconnecte.auxilium.service.mapper.UserCelluleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * Service Implementation for managing UserCellule.
 */
@Service
@Transactional
public class UserCelluleServiceImpl implements UserCelluleService{

    private final Logger log = LoggerFactory.getLogger(UserCelluleServiceImpl.class);

    private final UserCelluleRepository userCelluleRepository;

    private final UserCelluleMapper userCelluleMapper;

    public UserCelluleServiceImpl(UserCelluleRepository userCelluleRepository, UserCelluleMapper userCelluleMapper) {
        this.userCelluleRepository = userCelluleRepository;
        this.userCelluleMapper = userCelluleMapper;
    }

    /**
     * Save a userCellule.
     *
     * @param userCelluleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserCelluleDTO save(UserCelluleDTO userCelluleDTO) {
        log.debug("Request to save UserCellule : {}", userCelluleDTO);
        UserCellule userCellule = userCelluleMapper.toEntity(userCelluleDTO);
        userCellule = userCelluleRepository.save(userCellule);
        return userCelluleMapper.toDto(userCellule);
    }

    /**
     *  Get all the userCellules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserCelluleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserCellules");
        return userCelluleRepository.findAll(pageable)
            .map(userCelluleMapper::toDto);
    }

    /**
     *  Get one userCellule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserCelluleDTO findOne(Long id) {
        log.debug("Request to get UserCellule : {}", id);
        UserCellule userCellule = userCelluleRepository.findOne(id);
        return userCelluleMapper.toDto(userCellule);
    }

    /**
     *  Delete the  userCellule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCellule : {}", id);
        userCelluleRepository.delete(id);
    }

    /**
     *  Get one userCellule by user and cellule.
     *
     *  @param userId, celluleId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserCelluleDTO findByUserAndCellule(Long userId, Long celluleId) {
        log.debug("Request to get UserCellule : {}", userId,celluleId);
        UserCellule userCellule = userCelluleRepository.findByUserAndCellule(userId,celluleId);
        return userCelluleMapper.toDto(userCellule);
    }

    @Override
    public List<UserCelluleDTO> findByUser(Long userId) {
        log.debug("Request to get  authority: {}", userId);
        List<UserCellule> userCellules = userCelluleRepository.findByUser(userId);
        return userCelluleMapper.toDto(userCellules);
    }

	@Override
	public UserCelluleDTO update(UserCelluleDTO userCelluleDTO) {
	
		
		UserCellule userCellule = userCelluleMapper.toEntity(userCelluleDTO);
		userCellule = userCelluleRepository.save(userCellule);
		UserCelluleDTO result = userCelluleMapper.toDto(userCellule);
        
         return result;
		
	}


}
