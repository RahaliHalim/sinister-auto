package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.ReportFrequencyRate;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.VehiculeAssureRepository;
import com.gaconnecte.auxilium.repository.search.VehiculeAssureSearchRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeContratDTO;
import com.gaconnecte.auxilium.service.mapper.PartnerMapper;
import com.gaconnecte.auxilium.service.mapper.VehiculeAssureMapper;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VehiculeAssure.
 */
@Service
@Transactional
public class VehiculeAssureServiceImpl implements VehiculeAssureService {

    private final Logger log = LoggerFactory.getLogger(VehiculeAssureServiceImpl.class);

    private final VehiculeAssureRepository vehiculeAssureRepository;

    private final VehiculeAssureMapper vehiculeAssureMapper;

    private final VehiculeAssureSearchRepository vehiculeAssureSearchRepository;
    private final UserRepository userRepository;

	@Autowired
	private UserExtraRepository userExtraRepository;
    
    @Autowired
    PartnerMapper partnerMapper;

    public VehiculeAssureServiceImpl(VehiculeAssureRepository vehiculeAssureRepository, VehiculeAssureMapper vehiculeAssureMapper, VehiculeAssureSearchRepository vehiculeAssureSearchRepository,UserRepository userRepository) {
		this.vehiculeAssureRepository = vehiculeAssureRepository;
        this.vehiculeAssureMapper = vehiculeAssureMapper;
        this.vehiculeAssureSearchRepository = vehiculeAssureSearchRepository;
        this.userRepository = userRepository;

    }

    /**
     * Save a vehiculeAssure.
     *
     * @param vehiculeAssureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehiculeAssureDTO save(VehiculeAssureDTO vehiculeAssureDTO) {
        log.debug("Request to save VehiculeAssure : {}", vehiculeAssureDTO);
        VehiculeAssure vehiculeAssure = vehiculeAssureMapper.toEntity(vehiculeAssureDTO);
        vehiculeAssure = vehiculeAssureRepository.save(vehiculeAssure);
        VehiculeAssureDTO result = vehiculeAssureMapper.toDto(vehiculeAssure);
        //vehiculeAssureSearchRepository.save(vehiculeAssure);
        return result;
    }

    /**
     * Get all the vehiculeAssures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehiculeAssureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehiculeAssures");
        return vehiculeAssureRepository.findAll(pageable)
                .map(vehiculeAssureMapper::toDto);
    }

    /**
     * Get one vehiculeAssure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VehiculeAssureDTO findOne(Long id) {
        log.debug("Request to get VehiculeAssure : {}", id);
        VehiculeAssure vehiculeAssure = vehiculeAssureRepository.findOne(id);
        return vehiculeAssureMapper.toDto(vehiculeAssure);
    }

    /**
     * Delete the vehiculeAssure by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehiculeAssure : {}", id);
        vehiculeAssureRepository.delete(id);
        vehiculeAssureSearchRepository.delete(id);
    }

    /**
     * Search for the vehiculeAssure corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehiculeAssureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VehiculeAssures for query {}", query);
        Page<VehiculeAssure> result = vehiculeAssureSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(vehiculeAssureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeAssureDTO findOneByImmatriculation(String immatriculationVehicule) {
        log.debug("Request to get VehiculeAssure : {}", immatriculationVehicule);
        VehiculeAssure vehiculeAssure = vehiculeAssureRepository.findByImmatriculationVehicule(immatriculationVehicule);
        //log.debug("Request to get VehiculeAssure rrrrrrrrrahaliiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii: {}", vehiculeAssure.getDatePCirculation());
        return vehiculeAssureMapper.toDto(vehiculeAssure);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehiculeAssureDTO> findOneByContrat(Pageable pageable, Long contratId) {
        log.debug("Request to get VehiculeAssure : {}", contratId);
        Page<VehiculeAssure> vehiculeAssures = vehiculeAssureRepository.findByContrat(pageable, contratId);
        return vehiculeAssures.map(vehiculeAssureMapper::toDto);
    }

    @Override
    public String findMarqueByVehicule(Long vehiculeId) {
        // TODO Auto-generated method stub
        return vehiculeAssureRepository.findMarqueByVehicule(vehiculeId);
    }

    @Override
    public List<VehiculeAssureDTO> findAllWithoutPagination() {

        return vehiculeAssureMapper.toDto(vehiculeAssureRepository.findAll());
    }

    @Override
    public List<VehiculeContratDTO> findContratsForEachVehicle() { 

        List<VehiculeAssure> listVehicule = vehiculeAssureRepository.findAll();
        List<VehiculeContratDTO> vcdtos = new ArrayList<>();

        for(VehiculeAssure vehicule : listVehicule) {

            VehiculeContratDTO vcdto = new VehiculeContratDTO();

            vcdto.setId(vehicule.getId());
            vcdto.setImmatriculation(vehicule.getImmatriculationVehicule());
            if (vehicule.getContrat() != null) {
                if (vehicule.getInsured() != null && vehicule.getInsured().getId() != null) {
                    vcdto.setAssureId(vehicule.getInsured().getId());
                }
                if (vehicule.getContrat().getId() != null) {
                    vcdto.setContratId(vehicule.getContrat().getId());
                }
                if (vehicule.getInsured() != null && vehicule.getInsured().getPrenom() != null) {
                    vcdto.setAssurePrenom(vehicule.getInsured().getPrenom());
                }
                if (vehicule.getInsured() != null && vehicule.getInsured().getRaisonSociale() != null) {
                    vcdto.setAssureRaison(vehicule.getInsured().getRaisonSociale());
                }
                if (vehicule.getInsured() != null) {
                    vcdto.setAssureFullName(vehicule.getInsured().getFullName());
                }
                if (vehicule.getContrat().getNumeroContrat() != null) {
                    vcdto.setNumeroContrat(vehicule.getContrat().getNumeroContrat());
                }
                if (vehicule.getContrat().getClient() != null && vehicule.getContrat().getClient().getCompanyName() != null) {
                    vcdto.setNomCompagnie(vehicule.getContrat().getClient().getCompanyName());
                }
                if (vehicule.getContrat().getDebutValidite() != null) {
                    vcdto.setDebutValidite(vehicule.getContrat().getDebutValidite());
                }
                if (vehicule.getContrat().getFinValidite() != null) {
                    vcdto.setFinValidite(vehicule.getContrat().getFinValidite());
                }
                if (vehicule.getContrat().getAgence() != null && vehicule.getContrat().getAgence().getName() != null) {
                    vcdto.setNomAgence(vehicule.getContrat().getAgence().getName());
                }
            }

            vcdtos.add(vcdto);

        }
        return vcdtos;
    }

    @Override
    public void deleteByContrat(Long id) {

        vehiculeAssureRepository.deleteByContrat(id);
    }

    @Override
    public VehiculeContratDTO findContratByImmatriculation(String immatriculation) {
        VehiculeAssure vehiculeAssure = vehiculeAssureRepository.findByImmatriculationVehicule(immatriculation);
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<PartnerDTO> findListByImmatriculation(String immatriculationVehicule) {
        log.debug("Request to get VehiculeAssure : {}", immatriculationVehicule);
        
        String login = SecurityUtils.getCurrentUserLogin();
		User user = userRepository.findOneUserByLogin(login);
		UserExtra userExtra = userExtraRepository.findByUser(user.getId());
		
		 Set<VehiculeAssure> vehiculeAssure = vehiculeAssureRepository.findListByImmatriculation(immatriculationVehicule);
	        Set<Partner> listeCompagnies = new HashSet<>();
		
		
		if ((userExtra.getProfile().getId()).equals(25L) || (userExtra.getProfile().getId()).equals(26L)) {
			for(VehiculeAssure v : vehiculeAssure) {
				if(v.getContrat().getClient().getId().equals(userExtra.getPersonId())) {
					listeCompagnies.add(v.getContrat().getClient());
				}
	        	
	        }
        
		
		}else if ((userExtra.getProfile().getId()).equals(23L) || (userExtra.getProfile().getId()).equals(24L)) {

			for(VehiculeAssure v : vehiculeAssure) {
				if(v.getContrat().getAgence().getId().equals(userExtra.getPersonId())) {
					listeCompagnies.add(v.getContrat().getClient());
				}
	        	
	        }
			
        
		
		}else {
			for(VehiculeAssure v : vehiculeAssure) {
					listeCompagnies.add(v.getContrat().getClient());
				
	        	
	        }
			 
		}
       
       
        if (CollectionUtils.isNotEmpty(vehiculeAssure)) {
			return listeCompagnies.stream().map(partnerMapper::toDto).collect(Collectors.toSet());
		}
        return new HashSet<>();
    }
    
    @Override
    @Transactional(readOnly = true)
    public VehiculeAssureDTO findOneByCompagnyAndImmatriculation(String company, String immatriculation) {
        log.debug("Request to get VehiculeAssure : {}", immatriculation);
        VehiculeAssure vehiculeAssure = vehiculeAssureRepository.findByCompagnyNameAndImmatriculation(company, immatriculation);
        return vehiculeAssureMapper.toDto(vehiculeAssure);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VehiculeAssureDTO findOneByClientIdAndImmatriculation(Long clientId, String immatriculation) {
        log.debug("Request to get VehiculeAssure : {}", immatriculation);
        VehiculeAssure vehiculeAssure = vehiculeAssureRepository.findByClientIdAndImmatriculation(clientId, immatriculation);
        return vehiculeAssureMapper.toDto(vehiculeAssure);
    }
}
