package com.gaconnecte.auxilium.service.referential.impl;

import com.gaconnecte.auxilium.domain.referential.RefGrounds;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import com.gaconnecte.auxilium.repository.referential.RefGroundsRepository;
import com.gaconnecte.auxilium.service.referential.dto.RefGroundsDTO;
import com.gaconnecte.auxilium.service.referential.RefGroundsService;
import com.gaconnecte.auxilium.service.referential.mapper.RefGroundsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;


/**
 * Service Implementation for managing RefGrounds.
 */
@Service
@Transactional
public class RefGroundsServiceImpl implements RefGroundsService {

    private final Logger log = LoggerFactory.getLogger(RefGroundsServiceImpl.class);

    private final RefGroundsRepository refGroundsRepository;

    private final RefGroundsMapper refGroundsMapper;

    public RefGroundsServiceImpl(RefGroundsRepository refGroundsRepository, RefGroundsMapper refGroundsMapper) {
        this.refGroundsRepository = refGroundsRepository;
        this.refGroundsMapper = refGroundsMapper;
    }

    /**
     * Save a refGrounds.
     *
     * @param refGroundsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefGroundsDTO save(RefGroundsDTO refGroundsDTO) {
        log.debug("Request to save RefGrounds : {}", refGroundsDTO);
        RefGrounds refGrounds = refGroundsMapper.toEntity(refGroundsDTO);
        refGrounds = refGroundsRepository.save(refGrounds);
        return refGroundsMapper.toDto(refGrounds);
    }

    /**
     *  Get all the refGroundss.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefGroundsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefGroundss");
        return refGroundsRepository.findAll(pageable)
            .map(refGroundsMapper::toDto);
    }

    /**
     *  Get all the refGrounds.
     *
     *  @param id
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefGroundsDTO> findByStatus(Long id) {
        log.debug("Request to get all RefGrounds");
        RefStatusSinister status = new RefStatusSinister();
        status.setId(id);
        Set<RefGrounds> grounds = refGroundsRepository.findAllByStatus(status);
        if(CollectionUtils.isNotEmpty(grounds)) {
        	return grounds.stream().map(refGroundsMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    
    /**
     *  Get one refGrounds by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefGroundsDTO findOne(Long id) {
        log.debug("Request to get RefGrounds : {}", id);
        RefGrounds refGrounds = refGroundsRepository.findOne(id);
        return refGroundsMapper.toDto(refGrounds);
    }

    /**
     *  Delete the  refGrounds by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefGrounds : {}", id);
        refGroundsRepository.delete(id);
    }
}
