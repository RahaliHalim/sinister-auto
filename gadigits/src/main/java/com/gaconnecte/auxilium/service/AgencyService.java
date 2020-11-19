package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.Agency;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.repository.AgencyRepository;
import com.gaconnecte.auxilium.repository.search.AgencySearchRepository;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.mapper.AgencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Agency.
 */
@Service
@Transactional
public class AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyService.class);
    
    private static final String Agence_Concessionnaire = "agence_concessionnaire";
    
    private static final String Agent_Assurance = "agent_assurance";

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    private final AgencySearchRepository agencySearchRepository;
    
    
    private final HistoryService historyService;
    
    private final PartnerService partnerService;
    
    private final GovernorateService governorateService ;
     private final DelegationService delegationService ; 

    public AgencyService(AgencyRepository agencyRepository, AgencyMapper agencyMapper, AgencySearchRepository agencySearchRepository
    		, HistoryService historyService, PartnerService partnerService, GovernorateService governorateService,
    		DelegationService delegationService) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
        this.agencySearchRepository = agencySearchRepository;
        this.historyService = historyService;
        this.partnerService = partnerService;
        this.governorateService = governorateService;
        this.delegationService = delegationService;
    }

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save
     * @return the persisted entity
     */
    public AgencyDTO save(AgencyDTO agencyDTO) {
        log.debug("Request to save Agency : {}", agencyDTO);
        Agency agency = agencyMapper.toEntity(agencyDTO);
        LocalDate dateCreation = LocalDate.now();
        agency.setDateCreation(dateCreation);
        agency = agencyRepository.save(agency);
        AgencyDTO result = agencyMapper.toDto(agency);
        agencySearchRepository.save(agency);
        return result;
    }

    /**
     *  Get all the agencies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAll() {
        log.debug("Request to get all Agencies");
        return agencyRepository.findAll().stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the agencies by company.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllAgenciesByPartner(Long partnerId) {
        log.debug("Request to get all Agencies by company");
        return agencyRepository.findAllByPartner(new Partner(partnerId)).stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    
    
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllAgenciesByPartnerAndZone(Long partnerId,Long zoneId) {
        return agencyRepository.findAgencyByPartnerAndZone(new Governorate (zoneId),new Partner(partnerId)).stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllAgenciesByZone(Long zoneId) {
        return agencyRepository.findAgencyByZone(new Governorate (zoneId)).stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllAgenciesByPartnerAndType(Long partnerId, String type) {
        log.debug("Request to get all Agent by company");
        return agencyRepository.findAgencyByPartnerAndNoCourtier(new Partner(partnerId)).stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Find compagnies courtiers
     * @param partnersId
     * @param type
     * @return courtier list
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findCourtierByPartnerAndType(Long[] partnersId, String type){
        List<Agency> listCourtier = new LinkedList<>();
        for (Long partnerId : partnersId) {
            List<Agency> tmpList = agencyRepository.findAgencyByPartnerAndType(new Partner(partnerId), type);
            if (CollectionUtils.isNotEmpty(tmpList)) {
                listCourtier.addAll(tmpList);
            }
        }
        return listCourtier.stream()
                .map(agencyMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get all Agencies Concess.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllAgenceConcessionnares() {
        log.debug("Request to get all Agencies Concess");
        return agencyRepository.findAllByGenre(2).stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     *  Get all Agent Assurance.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllAgentAssurance() {
        log.debug("Request to get all Agent Assurance");
        return agencyRepository.findAllByGenre(1).stream()
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    public AgencyDTO getAgencyByNameCode(Long partnerId, String code ){
	      log.debug("Request to get AgencyDTO : {}", partnerId);
	      Agency agency = agencyRepository.findAgencyByNameCode(partnerId, code);
	       if (agency == null) {
	           return null;
	       }
	       return agencyMapper.toDto(agency);

	   }
    
    
    public AgencyDTO getAgenceConcessByNameConcessName(String pname, Long id){
	      log.debug("Request to get AgencyDTO : {}", pname);
	      Agency agency = agencyRepository.findAgenceConcessByNameConcessName(pname, id);
	       if (agency == null) {
	           return null;
	       }
	       return agencyMapper.toDto(agency);

	   }
    
    /**
     *  Get one agency by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AgencyDTO findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        Agency agency = agencyRepository.findOne(id);
        return agencyMapper.toDto(agency);
    }

    /**
     *  Delete the  agency by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.delete(id);
        agencySearchRepository.delete(id);
    }

    /**
     * Search for the agency corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> search(String query) {
        log.debug("Request to search Agencies for query {}", query);
        return StreamSupport
            .stream(agencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(agencyMapper::toDto)
            .collect(Collectors.toList());
    }
    
    public void findDiff(AgencyDTO agencyDTO) {
        AgencyDTO agencyDTOA = findOne(agencyDTO.getId());
        if(!agencyDTOA.getPartnerId().equals(agencyDTO.getPartnerId())) {
        	PartnerDTO partnerDTOA = partnerService.findOne(agencyDTOA.getPartnerId());
        	PartnerDTO partnerDTO = partnerService.findOne(agencyDTO.getPartnerId());
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Compagnie d'assurance de "+partnerDTOA.getCompanyName()+" à "+partnerDTO.getCompanyName()  );
        }
        if(!agencyDTOA.getNom().equals(agencyDTO.getNom())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Nom de "+agencyDTOA.getNom()+" à "+agencyDTO.getNom()  );
        }
        if(!agencyDTOA.getPrenom().equals(agencyDTO.getPrenom())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Prenom de "+agencyDTOA.getPrenom()+" à "+agencyDTO.getPrenom()  );
        }
        if(!agencyDTOA.getName().equals(agencyDTO.getName())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Nom agence de "+agencyDTOA.getName()+" à "+agencyDTO.getName()  );
        }
        if(!agencyDTOA.getPhone().equals(agencyDTO.getPhone())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Téléphone de "+agencyDTOA.getPhone()+" à "+agencyDTO.getPhone()  );
        }
        if(!agencyDTOA.getMobile().equals(agencyDTO.getMobile())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Mobile de "+agencyDTOA.getMobile()+" à "+agencyDTO.getMobile()  );
        }
        if(!agencyDTOA.getEmail().equals(agencyDTO.getEmail())) {
        	//.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Premier Email de "+agencyDTOA.getEmail()+" à "+agencyDTO.getEmail()  );
        }
        if(!agencyDTOA.getDeuxiemeMail().equals(agencyDTO.getDeuxiemeMail())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Deuxième Email de "+agencyDTOA.getDeuxiemeMail()+" à "+agencyDTO.getDeuxiemeMail()  );
        }
        if(!agencyDTOA.getGovernorateId().equals(agencyDTO.getGovernorateId())) {
        	GovernorateDTO governorateDTOA = governorateService.findOne(agencyDTOA.getGovernorateId());
        	GovernorateDTO governorateDTO = governorateService.findOne(agencyDTO.getGovernorateId());
        //	historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Gouvernorat de "+governorateDTOA.getLabel()+" à "+governorateDTO.getLabel()  );
        }
        if(!agencyDTOA.getDelegationId().equals(agencyDTO.getDelegationId())) {
        	DelegationDTO delegationDTOA = delegationService.findOne(agencyDTOA.getDelegationId());
        	DelegationDTO delegationDTO = delegationService.findOne(agencyDTO.getDelegationId());
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Ville de "+delegationDTOA.getLabel()+" à "+delegationDTO.getLabel()  );
        }
        if(!agencyDTOA.getTypeAgence().equals(agencyDTO.getTypeAgence())) {
        	//historyService.historysave(Agence_Concessionnaire, agencyDTO.getId(), "Modification Type Agence de "+agencyDTOA.getTypeAgence()+" à "+agencyDTO.getTypeAgence()  );
        }
        
       
    }
    
    /*public Histories saveHistory(String entityName,String operationName ) {
    	String login = SecurityUtils.getCurrentUserLogin();
    	User user = userService.findOneUserByLogin(login);
    	Histories histories = new Histories();
    	Operation operation = new Operation();
    	operation.setUserId(user.getId());
    	operation.setName(operationName);
    	operation.setDateOperation(LocalDateTime.now());
    	operation.setEntityName(entityName);
    	Fields fields = new Fields();
    	if(operationName == "Create") {
    		fields = null;
    	}
    	histories.setOperation(operation);
    	histories.setFields(fields);
    	return histories ;
    }*/
}
