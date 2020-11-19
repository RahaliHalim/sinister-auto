package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.Tiers;
import com.gaconnecte.auxilium.repository.TiersRepository;
import com.gaconnecte.auxilium.repository.search.TiersSearchRepository;
import com.gaconnecte.auxilium.service.dto.TiersDTO;
import com.gaconnecte.auxilium.service.mapper.TiersMapper;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tiers.
 */
@Service
@Transactional
public class TiersServiceImpl implements TiersService{

    private final Logger log = LoggerFactory.getLogger(TiersServiceImpl.class);

    private final TiersRepository tiersRepository;

    private final TiersMapper tiersMapper;

    private final TiersSearchRepository tiersSearchRepository;

    public TiersServiceImpl(TiersRepository tiersRepository, TiersMapper tiersMapper, TiersSearchRepository tiersSearchRepository) {
        this.tiersRepository = tiersRepository;
        this.tiersMapper = tiersMapper;
        this.tiersSearchRepository = tiersSearchRepository;
    }

    /**
     * Save a tiers.
     *
     * @param tiersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TiersDTO save(TiersDTO tiersDTO) {
        log.debug("Request to save Tiers : {}", tiersDTO);
        Tiers tiers = tiersMapper.toEntity(tiersDTO);
        tiers = tiersRepository.save(tiers);
        TiersDTO result = tiersMapper.toDto(tiers);
        tiersSearchRepository.save(tiers);
        return result;
    }

    /**
     *  Get all the tiers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TiersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tiers");
        return tiersRepository.findAll(pageable)
            .map(tiersMapper::toDto);
    }

    /**
     *  Get one tiers by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TiersDTO findOne(Long id) {
        log.debug("Request to get Tiers : {}", id);
        Tiers tiers = tiersRepository.findOne(id);
        return tiersMapper.toDto(tiers);
    }

    /**
     *  Delete the  tiers by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tiers : {}", id);
        tiersRepository.delete(id);
        tiersSearchRepository.delete(id);
    }

    /**
     * Search for the tiers corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TiersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tiers for query {}", query);
        Page<Tiers> result = tiersSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tiersMapper::toDto);
    }

    /**
     *  Get one contact by dossierId.
     *
     *  @param dossierId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TiersDTO> findByPrestationPEC(Pageable pageable, Long prestationPecId) {
        log.debug("Request to get Tiers : {}", prestationPecId);
        Page<Tiers> tiers = tiersRepository.findTiersByPrestationPec(pageable, prestationPecId);
        return tiers.map(tiersMapper::toDto);
         
    }
    
	@Override
	@Transactional(readOnly = true)
	public TiersDTO findByImmatriculation(String immatriculationTier) {
		log.debug("Request to get tiers : {}", immatriculationTier);
		Tiers tier= tiersRepository.findTiersByImmatriculation(immatriculationTier);
		return tiersMapper.toDto(tier);
	}

	@Override
	@Transactional(readOnly = true)
	public Set<TiersDTO> findTierssByImmatriculation(String immatriculationTier, Long clientId) {
		log.debug("Request to get all sinister pec ");
		Set<Tiers> listesTiers = tiersRepository.findTierssByImmatriculation(immatriculationTier, clientId);
		if (CollectionUtils.isNotEmpty(listesTiers)) {
			return listesTiers.stream().map(tiersMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}
}
