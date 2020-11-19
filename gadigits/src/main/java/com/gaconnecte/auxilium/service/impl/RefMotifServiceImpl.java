package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.RefMotifService;
import com.gaconnecte.auxilium.domain.RefMotif;
import com.gaconnecte.auxilium.repository.RefMotifRepository;
import com.gaconnecte.auxilium.repository.search.RefMotifSearchRepository;
import com.gaconnecte.auxilium.service.dto.RefMotifDTO;
import com.gaconnecte.auxilium.service.mapper.RefMotifMapper;
import com.gaconnecte.auxilium.domain.enumeration.Motifs;
import com.gaconnecte.auxilium.domain.enumeration.EtatMotifs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RefMotif.
 */
@Service
@Transactional
public class RefMotifServiceImpl implements RefMotifService{

    private final Logger log = LoggerFactory.getLogger(RefMotifServiceImpl.class);

    private final RefMotifRepository refMotifRepository;

    private final RefMotifMapper refMotifMapper;

    private final RefMotifSearchRepository refMotifSearchRepository;

    public RefMotifServiceImpl(RefMotifRepository refMotifRepository, RefMotifMapper refMotifMapper, RefMotifSearchRepository refMotifSearchRepository) {
        this.refMotifRepository = refMotifRepository;
        this.refMotifMapper = refMotifMapper;
        this.refMotifSearchRepository = refMotifSearchRepository;
    }

    /**
     * Save a refMotif.
     *
     * @param refMotifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefMotifDTO save(RefMotifDTO refMotifDTO) {
        log.debug("Request to save RefMotif : {}", refMotifDTO);
        RefMotif refMotif = refMotifMapper.toEntity(refMotifDTO);
        refMotif = refMotifRepository.save(refMotif);
        RefMotifDTO result = refMotifMapper.toDto(refMotif);
        refMotifSearchRepository.save(refMotif);
        return result;
    }

    /**
     *  Get all the refMotifs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefMotifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefMotifs");
        return refMotifRepository.findAll(pageable)
            .map(refMotifMapper::toDto);
    }

    /**
     *  Get one refMotif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefMotifDTO findOne(Long id) {
        log.debug("Request to get RefMotif : {}", id);
        RefMotif refMotif = refMotifRepository.findOne(id);
        return refMotifMapper.toDto(refMotif);
    }

    /**
     *  Delete the  refMotif by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefMotif : {}", id);
        refMotifRepository.delete(id);
        refMotifSearchRepository.delete(id);
    }

    /**
     * Search for the refMotif corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefMotifDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefMotifs for query {}", query);
        Page<RefMotif> result = refMotifSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refMotifMapper::toDto);
    }
    @Override
    @Transactional(readOnly = true)
    public Set<RefMotifDTO> findAllMotifsByTypeAndEtat(String type, String etat){

        log.debug("Request to get all motifs by type and etat");
        Set<RefMotif> motifs = refMotifRepository.findAllMotifsByTypeAndEtat();
        return new HashSet<>();
    }
}
