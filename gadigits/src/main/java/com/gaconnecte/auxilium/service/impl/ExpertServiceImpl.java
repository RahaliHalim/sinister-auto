package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.domain.ExpertGarantieImplique;
import com.gaconnecte.auxilium.domain.GarantieImplique;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.Histories;
import com.gaconnecte.auxilium.domain.History;
import com.gaconnecte.auxilium.domain.Orientation;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.domain.Histories.Fields;
import com.gaconnecte.auxilium.domain.Histories.Operation;
import com.gaconnecte.auxilium.domain.Histories.Fields.Field;
import com.gaconnecte.auxilium.repository.ExpertRepository;
import com.gaconnecte.auxilium.repository.HistoryRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.search.ExpertSearchRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.ExpertGarantieImpliqueDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.mapper.ExpertMapper;
import com.gaconnecte.auxilium.service.mapper.HistoryMapper;
import com.gaconnecte.gadigits.gtestimate.utils.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ContainerChange;
import org.javers.core.diff.changetype.map.MapChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Service Implementation for managing Expert.
 */
@Service
@Transactional
public class ExpertServiceImpl implements ExpertService {

	private final Logger log = LoggerFactory.getLogger(ExpertServiceImpl.class);

	private final ExpertRepository expertRepository;

	private final ExpertMapper expertMapper;

	private final ExpertSearchRepository expertSearchRepository;
	private final HistoryRepository historyRepository;

	private final HistoryMapper historyMapper;
	private final UserRepository userRepository;
	@Autowired
	private UserService userService;

	public ExpertServiceImpl(ExpertRepository expertRepository, ExpertMapper expertMapper,
			ExpertSearchRepository expertSearchRepository, UserRepository userRepository,
			HistoryRepository historyRepository, HistoryMapper historyMapper) {
		this.expertRepository = expertRepository;
		this.expertMapper = expertMapper;
		this.expertSearchRepository = expertSearchRepository;
		this.userRepository = userRepository;
		this.historyRepository = historyRepository;
		this.historyMapper = historyMapper;
	}

	/**
	 * Save a expert.
	 *
	 * @param expertDTO the entity to save
	 * @return the persisted entity
	 */

	@Override
	public ExpertDTO save(ExpertDTO expertDTO) {
		log.debug("Request to save Expert DTO : {}", expertDTO);
		Expert expert = expertMapper.toEntity(expertDTO);
		if (expert.getGarantieImpliques() != null) {
			for (ExpertGarantieImplique expertGarantieImplique : expert.getGarantieImpliques()) {
				expertGarantieImplique.setExpert(expert);
			}
		}
		expert = expertRepository.save(expert);
		ExpertDTO result = expertMapper.toDto(expert);
		expertSearchRepository.save(expert);
		return result;
	}

	/**
	 * Get all the experts.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ExpertDTO> findAll() {
		log.debug("Request to get all Experts");
		return expertMapper.toDto(expertRepository.findAll());
	}

	/**
	 * Get all the experts.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ExpertDTO> findAllExpert(Pageable pageable) {
		log.debug("Request to get all Experts");
		return expertRepository.findAllExpertnonbloque(pageable).map(expertMapper::toDto);
	}

	/**
	 * Get one expert by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public ExpertDTO findOne(Long id) {
		log.debug("Request to get Expert : {}", id);
		Expert expert = expertRepository.findOne(id);
		return expertMapper.toDto(expert);
	}

	@Override
	@Transactional(readOnly = true)
	public ExpertDTO getExpCompanyByFTUSA(String numeroFTUSA, String pname) {
		log.debug("Request to get expert : {}", pname);
		Expert expert = expertRepository.findExpertByFTUSA(numeroFTUSA, pname);
		if (expert == null) {
			return null;
		}
		return expertMapper.toDto(expert);

	}

	public List<ExpertGarantieImpliqueDTO> ConvertirGarantieDTO(
			List<ExpertGarantieImpliqueDTO> expertGarantieImpliqueDTOs) {
		for (ExpertGarantieImpliqueDTO expertGarantieImpliqueDTO : expertGarantieImpliqueDTOs) {
			for (RefModeGestionDTO refModeGestionDTO : expertGarantieImpliqueDTO.getRefModeGestions()) {
				if(expertGarantieImpliqueDTO.getRefModeGestionsString()!= null) 
				expertGarantieImpliqueDTO.setRefModeGestionsString(
						expertGarantieImpliqueDTO.getRefModeGestionsString() +" "+refModeGestionDTO.getLibelle());
				else 	expertGarantieImpliqueDTO.setRefModeGestionsString(refModeGestionDTO.getLibelle());
			}
		}
		return expertGarantieImpliqueDTOs;
	}

	@Override
	public void historysaveExpert(String entityName, Long entityId, ExpertDTO oldValue, ExpertDTO newValue,
			String operationName) {
		log.debug("log to service history save Expert impl");
	
		Javers javersList = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
				.build();

		Javers javers = JaversBuilder.javers().build();

		Histories histories = new Histories();
		Operation operation = new Operation();
		Fields fieldsObj = new Fields();
		// comparaison de deux objet
		ConvertirGarantieDTO(oldValue.getGarantieImpliques());
		ConvertirGarantieDTO(newValue.getGarantieImpliques());
		Diff diff = javersList.compare(oldValue, newValue);
		Diff difflistDeligation = javersList.compare(oldValue.getListeVilles(), newValue.getListeVilles());
		Diff difflistGarantique = javers.compare(oldValue.getGarantieImpliques(), newValue.getGarantieImpliques());
		Diff difflistGovernorat = javersList.compare(oldValue.getGouvernorats(), newValue.getGouvernorats());
		List<ValueChange> objetsDiff = diff.getChangesByType(ValueChange.class);
		
		Field field = new Field();
		field.setName("Liste Villes");
		field.setNouvVal(difflistDeligation.getChanges().toString());
		fieldsObj.getField().add(field);

		field = new Field();
		field.setName("Liste gouvernorats");
		field.setNouvVal(difflistGovernorat.getChanges().toString());
		fieldsObj.getField().add(field);

		field = new Field();
		field.setName("Liste Garanties Impliques");
		field.setNouvVal(difflistGarantique.getChanges().toString());
		fieldsObj.getField().add(field);
		
		for (int i = 0; i < objetsDiff.size(); i++) {
			field = new Field();
			field.setName(objetsDiff.get(i).getPropertyName());
			field.setAncienVal(String.valueOf(objetsDiff.get(i).getLeft()));
			field.setNouvVal(String.valueOf(objetsDiff.get(i).getRight()));
			fieldsObj.getField().add(field);
		}
		// intial history
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userService.findOneUserByLogin(login);
		operation.setDateOperation(LocalDateTime.now().toString());
		operation.setEntityName(entityName);

		operation.setUserId(user.getId());
		histories.setOperation(operation);
		histories.setFields(fieldsObj);
		// conversion de objet history to Xml
		String xml = "";
		try {
			xml = getXmlFromBean(histories, Constants.DEFAULT_ENCODING, false);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(xml);
		HistoryDTO historyDTO = new HistoryDTO();
		historyDTO.setDescriptionOfHistorization(xml);
		historyDTO.setEntityName(entityName);
		historyDTO.setEntityId(entityId);
		historyDTO.setOperationName(operationName);
		historyDTO.setOperationDate(LocalDateTime.now());
		historyDTO.setUserId(user.getId());
		History history = historyMapper.toEntity(historyDTO);
		historyRepository.save(history);
	}

	public String getXmlFromBean(Histories bean, String encoding, boolean isFormat) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(bean.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.encoding", encoding);
		marshaller.setProperty("jaxb.formatted.output", isFormat);
		StringWriter sw = new StringWriter();
		marshaller.marshal(bean, sw);
		return sw.toString();
	}
	/**
	 * Delete the expert by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Expert : {}", id);
		expertRepository.delete(id);
		expertSearchRepository.delete(id);
	}

	/**
	 * Search for the expert corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ExpertDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Experts for query {}", query);
		Page<Expert> result = expertSearchRepository.search(queryStringQuery(query), pageable);
		return result.map(expertMapper::toDto);
	}

	/**
	 * Get one expert by ville.
	 *
	 * @param ville the id of the entity
	 * @return the entity
	 */

	@Override
	@Transactional(readOnly = true)
	public Page<ExpertDTO> findExpertByVille(Pageable pageable, Long villeId) {
		log.debug("Request to get Expert : {}", villeId);
		return expertRepository.findByVille(pageable, villeId).map(expertMapper::toDto);
	}

	@Override
	public Page<ExpertDTO> findByGouvernorat(Pageable pageable, Long gouvernoratId) {
		log.debug("Request to get Experts : {}", gouvernoratId);
		return expertRepository.findByGouvernorat(pageable, gouvernoratId).map(expertMapper::toDto);

	}
	
	@Override
	@Transactional(readOnly = true)
	public Set<ExpertDTO> findByGovernorate(Long governorateId, Long partnerId, Long modeId) {
		log.debug("Request to get Experts : {}", governorateId);
		List<Expert> experts = expertRepository.findAll();
		Set<Expert> expertsMission = new HashSet<>();
		Set<Expert> expertsAffectation = new HashSet<>();
		for(Expert expertDTO : experts) {
			for(Governorate gov : expertDTO.getGouvernorats()) {
				if(gov.getId().equals(governorateId)) {
					expertsMission.add(expertDTO);
					break;
				}
			}
		}
		for(Expert expertDTO : expertsMission) {
			if(!expertDTO.getBlocage() && expertDTO.getEng() && expertDTO.getIsActive()) {
				for(ExpertGarantieImplique expertGarantieImpliqueDTO : expertDTO.getGarantieImpliques()) {
					if(partnerId.equals(expertGarantieImpliqueDTO.getPartner().getId())) {
						for(RefModeGestion refModeGestionDTO : expertGarantieImpliqueDTO.getRefModeGestions()) {
							if(modeId.equals(refModeGestionDTO.getId())) {
								expertsAffectation.add(expertDTO);
								break;
							}
						}
					}
				}
				
			}
		}
		
		if (CollectionUtils.isNotEmpty(expertsAffectation)) {
			return expertsAffectation.stream().map(expertMapper::toDto).collect(Collectors.toSet());
		}
		return new HashSet<>();

	}

}
