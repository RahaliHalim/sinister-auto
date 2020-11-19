package com.gaconnecte.auxilium.service.referential.impl;

import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.RefTypeService;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.prm.ConventionAmendment;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.referential.RefPack;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.referential.RefPackRepository;
import com.gaconnecte.auxilium.repository.search.RefPackSearchRepository;
import com.gaconnecte.auxilium.service.referential.RefPackService;
import com.gaconnecte.auxilium.service.referential.dto.RefPackDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefPackMapper;

/**
 * Service Implementation for managing RefPack.
 */
@Service
@Transactional
public class RefPackServiceImpl implements RefPackService{

    private final Logger log = LoggerFactory.getLogger(RefPackServiceImpl.class);

    private final RefPackRepository refPackRepository;

    private final RefPackMapper refPackMapper;

    private final RefPackSearchRepository refPackSearchRepository;
    
    private final UserExtraRepository userExtraRepository;

    public RefPackServiceImpl(RefPackRepository refPackRepository, RefPackMapper refPackMapper, RefPackSearchRepository refPackSearchRepository, UserExtraRepository userExtraRepository) {
        this.refPackRepository = refPackRepository;
        this.refPackMapper = refPackMapper;
        this.refPackSearchRepository = refPackSearchRepository;
        this.userExtraRepository = userExtraRepository;
    }

    /**
     * Save a refPack.
     *
     * @param refPackDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefPackDTO save(RefPackDTO refPackDTO) {
        log.debug("Request to save RefPack : {}", refPackDTO);
        RefPack refPack = refPackMapper.toEntity(refPackDTO);
        refPack = refPackRepository.save(refPack);
        RefPackDTO result = refPackMapper.toDto(refPack);
        refPackSearchRepository.save(refPack);
        return result;
    }

    /**
     *  Get all the refPacks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefPackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RefPacks");
        return refPackRepository.findAll(pageable)
            .map(refPackMapper::toDto);
    }

    /**
     *  Get all the refPacks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefPackDTO> findAll() {
        log.debug("Request to get all RefPacks");
        Set<RefPack> packs = refPackRepository.findAllPacks();
        if(CollectionUtils.isNotEmpty(packs)) {
        	return packs.stream().map(refPackMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     *  Get all the refPacks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefPackDTO> findAllByServiceType(Long id) {
        log.debug("Request to get all RefPacks");
        /*Set<RefPack> packs = refPackRepository.findByServiceType(new RefTypeService(id));
        if(CollectionUtils.isNotEmpty(packs)) {
        	return packs.stream().map(refPackMapper::toDto).collect(Collectors.toSet());
        }*/
        return new HashSet<>();
    }

    /**
     *  Get all the packs by company.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<RefPackDTO> findAllPacksByCompany(Long id) {
        log.debug("Request to get all RefPacks");
        Set<RefPack> packs1 = refPackRepository.findAllByClient(new Partner(id));
        Set<RefPack> packs2 = refPackRepository.findAllByPartnerIdAndAmendment(id);
        Set<RefPack> packs = new HashSet<>();
        if(CollectionUtils.isNotEmpty(packs1)) {
            packs.addAll(packs1);
        }
        if(CollectionUtils.isNotEmpty(packs2)) {
            packs.addAll(packs2);
        }
        if(CollectionUtils.isNotEmpty(packs)) {
            return packs.stream().map(refPackMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<RefPackDTO> findAllPacksActifByCompany(Long id) {
        log.debug("Request to get all RefPacks");
        LocalDate dateNow = LocalDate.now();
        Set<RefPack> packs1 = refPackRepository.findAllByClient(new Partner(id));
        Set<RefPack> packs2 = refPackRepository.findAllActifByPartnerIdAndAmendment(id, dateNow);
        Set<ConventionAmendment> conventionAmendment = refPackRepository.findAllAmendmentsActifByPartnerIdAndAmendment(id, dateNow);
        Set<RefPack> packs = new HashSet<>();
        
        if(CollectionUtils.isNotEmpty(packs1)) {
            packs.addAll(packs1);
        }
        if(CollectionUtils.isNotEmpty(packs2)) {
            packs.addAll(packs2);
        }
        
        for(ConventionAmendment amendment : conventionAmendment) {
        	for(RefPack pack : packs) {
        		if(pack.getId().equals(amendment.getOldRefPackId())) {
        			packs.remove(pack);
        			break;
        		}
        	}
        	
        }
        
        if(CollectionUtils.isNotEmpty(packs)) {
            return packs.stream().map(refPackMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
    
    /**
     *  Get one refPack by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefPackDTO findOne(Long id) {
        log.debug("Request to get RefPack : {}", id);
        RefPack refPack = refPackRepository.findOne(id);
        return refPackMapper.toDto(refPack);
    }
    
    @Override
    @Transactional(readOnly = true)
    public RefPackDTO findOneModesByUser(Long id, Long idUser) {
        log.debug("Request to get RefPack : {}", id);
        RefPack refPack = refPackRepository.findOne(id);
        Set<RefModeGestion> modesGestionsByUser = new HashSet<>();
        UserExtra userExtra = userExtraRepository.findByUser(idUser);
		Set<UserPartnerMode> usersPartnerModes = userExtra.getUserPartnerModes();
        Set<RefModeGestion> modesGestions = refPack.getModeGestions();
        for(UserPartnerMode userPartnerMode : usersPartnerModes) {
			for(RefModeGestion refModeGestion : modesGestions) {
				if(userPartnerMode.getModeGestion().getId() == refModeGestion.getId() ) {
					modesGestionsByUser.add(refModeGestion);
				}
			}
		}
        refPack.setModeGestions(modesGestionsByUser);
        return refPackMapper.toDto(refPack);
    }

    /**
     *  Delete the  refPack by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RefPack : {}", id);
        refPackRepository.delete(id);
        refPackSearchRepository.delete(id);
    }

    /**
     *  Block the  refPack by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void block(Long id) {
        log.debug("Request to delete RefPack : {}", id);
        refPackRepository.delete(id);
        refPackSearchRepository.delete(id);
    }
    
    @Override
    public boolean isServiceTypeAuthorized(Long packId, Long serviceTypeId) {
        RefPack pack = refPackRepository.findPackIfServiceTypeAuthorized(packId, new RefTypeService(serviceTypeId));
        return pack != null;
    }

    /**
     * Search for the refPack corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefPackDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefPacks for query {}", query);
        Page<RefPack> result = refPackSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refPackMapper::toDto);
    }

}
