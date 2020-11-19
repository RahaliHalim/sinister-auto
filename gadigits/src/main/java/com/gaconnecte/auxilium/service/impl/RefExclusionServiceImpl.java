package com.gaconnecte.auxilium.service.impl;


import com.gaconnecte.auxilium.service.RefExclusionService;
import com.gaconnecte.auxilium.domain.RefExclusion;
import com.gaconnecte.auxilium.repository.RefExclusionRepository;
import com.gaconnecte.auxilium.service.dto.RefExclusionDTO;

import com.gaconnecte.auxilium.service.mapper.RefExclusionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefModeGestion.
 */
@Service
@Transactional
public class RefExclusionServiceImpl implements RefExclusionService{

    private final Logger log = LoggerFactory.getLogger(RefExclusionServiceImpl.class);

    private final RefExclusionRepository refExclusionRepository;

    private final RefExclusionMapper refExclusionMapper;

 

  

    public RefExclusionServiceImpl(RefExclusionRepository refExclusionRepository, RefExclusionMapper refExclusionMapper) {
        this.refExclusionRepository = refExclusionRepository;
        this.refExclusionMapper = refExclusionMapper;
       
    }

    /**
     * Save a refModeGestion.
     *
     * @param refModeGestionDTO the entity to save
     * @return the persisted entity
     */
   

    /**
     *  Get all the refModeGestions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefExclusionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefExculsions");
        return refExclusionRepository.findAll(pageable)
            .map(refExclusionMapper::toDto);
    }
   

    /**
     *  Get one refModeGestion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefExclusionDTO findOne(Long id) {
        log.debug("Request to get RefExlution : {}", id);
        RefExclusion refExclusion = refExclusionRepository.findOne(id);
        return refExclusionMapper.toDto(refExclusion);
    }

	@Override
	public RefExclusionDTO save(RefExclusionDTO refRefExclusionDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<RefExclusionDTO> search(String query, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


 

}
