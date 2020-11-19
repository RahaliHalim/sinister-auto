package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.repository.DossierRepository;
import com.gaconnecte.auxilium.repository.search.ContratAssuranceSearchRepository;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.mapper.ContratAssuranceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ContratAssurance.
 */
@Service
@Transactional
public class ContratAssuranceServiceImpl implements ContratAssuranceService {

    private final Logger log = LoggerFactory.getLogger(ContratAssuranceServiceImpl.class);

    private final ContratAssuranceRepository contratAssuranceRepository;

    private final ContratAssuranceMapper contratAssuranceMapper;

    private final ContratAssuranceSearchRepository contratAssuranceSearchRepository;

    public ContratAssuranceServiceImpl(ContratAssuranceRepository contratAssuranceRepository, ContratAssuranceMapper contratAssuranceMapper, ContratAssuranceSearchRepository contratAssuranceSearchRepository) {
        this.contratAssuranceRepository = contratAssuranceRepository;
        this.contratAssuranceMapper = contratAssuranceMapper;
        this.contratAssuranceSearchRepository = contratAssuranceSearchRepository;
    }

    /**
     * Save a contratAssurance.
     *
     * @param contratAssuranceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContratAssuranceDTO save(ContratAssuranceDTO contratAssuranceDTO) {
        log.debug("Request to save ContratAssurance : {}", contratAssuranceDTO);
        ContratAssurance contratAssurance = contratAssuranceMapper.toEntity(contratAssuranceDTO);
        contratAssurance.setIsAssujettieTva((contratAssuranceDTO.isIsAssujettieTva() != null && contratAssuranceDTO.isIsAssujettieTva()) ? Boolean.TRUE : Boolean.FALSE);
        contratAssurance.setIsDelete(Boolean.FALSE);
        contratAssurance.getVehicules().forEach(v->{
        	System.out.println("id contrarararata"+v.getContrat().getId());
        });
        contratAssurance = contratAssuranceRepository.save(contratAssurance);
        ContratAssuranceDTO result = contratAssuranceMapper.toDto(contratAssurance);
        return result;
    }

    /**
     *  Get all the contratAssurances.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContratAssuranceDTO> findAll() {
        log.debug("Request to get all ContratAssurances");
        List<ContratAssurance> listContrat = contratAssuranceRepository.findAllContrat();
         List<ContratAssuranceDTO> listContratDTO = contratAssuranceMapper.toDto(listContrat);
        
        listContratDTO.forEach(item->{
        	ContratAssurance cont = contratAssuranceRepository.findOne(item.getId());
        	if( cont.getClient() != null){	
        	item.setNomCompagnie(cont.getClient().getCompanyName());
        	}
        });
          return listContratDTO;   
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ContratAssuranceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContratAssurances");
        return contratAssuranceRepository.findAll(pageable).map(contratAssuranceMapper::toDto);
    }

    /**
     *  Get one contratAssurance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContratAssuranceDTO findOne(Long id) {
        log.debug("Request to get ContratAssurance : {}", id);
        ContratAssurance contratAssurance = contratAssuranceRepository.findOne(id);
        ContratAssuranceDTO dto = contratAssuranceMapper.toDto(contratAssurance);
        return dto;
    }
    
    /**
     *  Get one contratAssurance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContratAssuranceDTO findOneWithVehicles(Long id) {
        log.debug("Request to get ContratAssurance : {}", id);
        ContratAssurance contratAssurance = contratAssuranceRepository.findOneWithVehicles(id);
        ContratAssuranceDTO dto = contratAssuranceMapper.toDto(contratAssurance);
        return dto;
    }

    /**
     *  Get one contact by vehiculeId.
     *
     *  @param vehiculeId the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContratAssuranceDTO> findByVehicule(Pageable pageable, Long vehiculeId) {
        log.debug("Request to get Prestaion : {}", vehiculeId);
        Page<ContratAssurance> contratAssurances = contratAssuranceRepository.findContratAssurancesByVehicule(pageable, vehiculeId);
        return contratAssurances.map(contratAssuranceMapper::toDto);
         
    }

    /**
     *  Delete the  contratAssurance by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public Boolean delete(Long id) {
        log.debug("Request to delete ContratAssurance : {}", id);
        ContratAssurance contratAssurance =  contratAssuranceRepository.findOne(id);
        Boolean delete = validerDeleteContrat(contratAssurance);
         if (delete) {
        	contratAssurance.setIsDelete(Boolean.TRUE);
        contratAssurance = contratAssuranceRepository.save(contratAssurance);
        //contratAssuranceSearchRepository.delete(id);
    }
     return delete;
    }

    /**
     * Search for the contratAssurance corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContratAssuranceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContratAssurances for query {}", query);
        Page<ContratAssurance> result = contratAssuranceSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(contratAssuranceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ContratAssuranceDTO findOneByImmatriculation(String immatriculationVehicule) {
        log.debug("Request to get ContratAssurance : {}", immatriculationVehicule);
        Set<VehiculeAssure> vehicles = contratAssuranceRepository.findByRegistrationNumber(immatriculationVehicule);
        if(CollectionUtils.isNotEmpty(vehicles)) {
            VehiculeAssure vehicle = vehicles.iterator().next();
            if (vehicle != null) {
                return contratAssuranceMapper.toDto(vehicle.getContrat());
            }            
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean findOneForTiersByImmatriculation(String immatriculationVehicule) {
        log.debug("Request to get ContratAssurance : {}", immatriculationVehicule);
        Set<VehiculeAssure> vehicles = contratAssuranceRepository.findByRegistrationNumber(immatriculationVehicule);
        if(CollectionUtils.isNotEmpty(vehicles)) {
            VehiculeAssure vehicle = vehicles.iterator().next();
            if (vehicle != null) {
                return true;
            }            
        }

        return false;
    }



     /**
     * valider suppression d'un contrat.
     * @param contrat
     * @return valider delete
     */
    public Boolean validerDeleteContrat(ContratAssurance contratAssurance) {
    	Boolean deleteValide = Boolean.TRUE;

    	/*Dossier dossier = dossierRepository.findByVehicule(contratAssurance.getVehicule().getId());
    	if (dossier != null) {
    		deleteValide = Boolean.FALSE;
		}*/
    	return deleteValide;
    }


    @Override
    @Transactional(readOnly = true)
    public ContratAssuranceDTO findContratAssuranceByVehicule (Long id){
       log.debug("Request to get ContratAssurance : {}", id);
        ContratAssurance ContratAssurance = contratAssuranceRepository.findOneByIdVehicule(id);
        if (ContratAssurance == null) {return null;}
        return contratAssuranceMapper.toDto(ContratAssurance);

    }
    @Override
    @Transactional(readOnly = true)
    public ContratAssuranceDTO findPolicyByNumber (String pnumber){
       log.debug("Request to get ContratAssurance : {}", pnumber);
        ContratAssurance ContratAssurance = contratAssuranceRepository.findByNumeroContrat(pnumber);
        if (ContratAssurance == null) {
            return null;
        }
        return contratAssuranceMapper.toDto(ContratAssurance);

    }

	@Override
	public ContratAssuranceDTO findByImmatriculationIgnoreFinValidite(String immatriculationVehicule) {
		log.debug("Request to get ContratAssurance : {}", immatriculationVehicule);
        VehiculeAssure vehicle = contratAssuranceRepository.findByImmatriculationAndIgnoreContractDate(immatriculationVehicule);
        if (vehicle != null) {
            return contratAssuranceMapper.toDto(vehicle.getContrat());
        }

        return null;
	}

	@Override
	public ContratAssuranceDTO findByContractNum(String numeroContrat) {
		log.debug("Request to get ContratAssurance : {}", numeroContrat);
        ContratAssurance ContratAssurance = contratAssuranceRepository.findByContractNum(numeroContrat);
        if (ContratAssurance == null) {
            return null;
        }
        return contratAssuranceMapper.toDto(ContratAssurance);
	}
}
