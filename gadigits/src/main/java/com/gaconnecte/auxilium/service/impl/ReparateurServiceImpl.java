package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.domain.GarantieImplique;
import com.gaconnecte.auxilium.domain.Histories;
import com.gaconnecte.auxilium.domain.History;
import com.gaconnecte.auxilium.domain.Loueur;
import com.gaconnecte.auxilium.domain.Orientation;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.domain.Histories.Fields;
import com.gaconnecte.auxilium.domain.Histories.Operation;
import com.gaconnecte.auxilium.domain.Histories.Fields.Field;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.repository.ReparateurRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.HistoryRepository;
import com.gaconnecte.auxilium.repository.OrientationRepository;
import com.gaconnecte.auxilium.repository.search.ReparateurSearchRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.ExpertGarantieImpliqueDTO;
import com.gaconnecte.auxilium.service.dto.GarantieImpliqueDTO;
import com.gaconnecte.auxilium.service.dto.HistoryDTO;
import com.gaconnecte.auxilium.service.dto.OrientationDTO;
import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.VehicleBrandDTO;
import com.gaconnecte.auxilium.service.dto.VisAVisDTO;
import com.gaconnecte.auxilium.service.mapper.HistoryMapper;
import com.gaconnecte.auxilium.service.mapper.ReparateurMapper;
import com.gaconnecte.gadigits.gtestimate.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.ValueChange;

import com.gaconnecte.auxilium.security.AuthoritiesConstants;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.service.UserService;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Service Implementation for managing Reparateur.
 */
@Service
@Transactional
public class ReparateurServiceImpl implements ReparateurService {

	private final Logger log = LoggerFactory.getLogger(ReparateurServiceImpl.class);

	private final ReparateurRepository reparateurRepository;

	private final UserRepository userRepository;

	private final UserExtraRepository userExtraRepository;

	private final ReparateurMapper reparateurMapper;

	private final HistoryRepository historyRepository;

	private final ReparateurSearchRepository reparateurSearchRepository;

	private final OrientationRepository orientationRepository;

	private final UserService userService;

	private final HistoryMapper historyMapper;

	public ReparateurServiceImpl(ReparateurRepository reparateurRepository, ReparateurMapper reparateurMapper,
			ReparateurSearchRepository reparateurSearchRepository, UserRepository userRepository,
			OrientationRepository orientationRepository, UserService userService,
			UserExtraRepository userExtraRepository, HistoryRepository historyRepository, HistoryMapper historyMapper) {
		this.reparateurRepository = reparateurRepository;
		this.reparateurMapper = reparateurMapper;
		this.reparateurSearchRepository = reparateurSearchRepository;
		this.userRepository = userRepository;
		this.orientationRepository = orientationRepository;
		this.userService = userService;
		this.userExtraRepository = userExtraRepository;
		this.historyRepository = historyRepository;
		this.historyMapper = historyMapper;
	}

	/**
	 * Save a reparateur.
	 *
	 * @param reparateurDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public ReparateurDTO save(ReparateurDTO reparateurDTO) {
		log.debug("Request to save Reparateur : {}", reparateurDTO);
		Reparateur reparateur = reparateurMapper.toEntity(reparateurDTO);
		Partner partner = null;
		RefRemorqueur refRemorqueur = null;
		Loueur loueur = null;

		if (reparateur.getOrientations() != null) {
			for (Orientation orientation : reparateur.getOrientations()) {
				orientation.setReparateur(reparateur);
			}
		}
		if (reparateur.getGarantieImpliques() != null) {
			for (GarantieImplique garantieImplique : reparateur.getGarantieImpliques()) {
				garantieImplique.setReparateur(reparateur);
			}
		}
		if (reparateur.getVisAViss() != null) {
			for (VisAVis visAVis : reparateur.getVisAViss()) {
				visAVis.setReparateur(reparateur);
				visAVis.setPartner(partner);
				visAVis.setRemorqueur(refRemorqueur);
				visAVis.setLoueur(loueur);

			}
		}
		reparateur = reparateurRepository.save(reparateur);
		ReparateurDTO result = reparateurMapper.toDto(reparateur);
		return result;
	}

	/**
	 * Get all the reparateurs.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ReparateurDTO> findAll() {
		log.debug("Request to get all Reparateurs");
		return reparateurMapper.toDto(reparateurRepository.findAll());
	}

	/**
	 * Get one reparateur by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public ReparateurDTO findOne(Long id) {
		log.debug("Request to get Reparateur : {}", id);
		Reparateur reparateur = reparateurRepository.findOne(id);
		return reparateurMapper.toDto(reparateur);
	}

	/**
	 * Delete the reparateur by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Reparateur : {}", id);
		reparateurRepository.delete(id);
		reparateurSearchRepository.delete(id);
	}

	public List<OrientationDTO> ConvertirOrientationDTO(List<OrientationDTO> orientationDTOs) {
		for (OrientationDTO orientationDTO : orientationDTOs) {
			for (VehicleBrandDTO vehicleBrandDTO : orientationDTO.getRefMarques()) {
				if (orientationDTO.getRefMarquesString() != null)
					orientationDTO.setRefMarquesString(
							orientationDTO.getRefMarquesString() + " " + vehicleBrandDTO.getLabel());
				else
					orientationDTO.setRefMarquesString(vehicleBrandDTO.getLabel());
			}
		}
		return orientationDTOs;
	}

	public List<GarantieImpliqueDTO> ConvertirGarantieDTO(List<GarantieImpliqueDTO> expertGarantieImpliqueDTOs) {
		for (GarantieImpliqueDTO expertGarantieImpliqueDTO : expertGarantieImpliqueDTOs) {
			for (RefModeGestionDTO refModeGestionDTO : expertGarantieImpliqueDTO.getRefModeGestions()) {
				if (expertGarantieImpliqueDTO.getListModeGestions() != null)
					expertGarantieImpliqueDTO.setListModeGestions(
							expertGarantieImpliqueDTO.getListModeGestions() + " " + refModeGestionDTO.getLibelle());
				else
					expertGarantieImpliqueDTO.setListModeGestions(refModeGestionDTO.getLibelle());
			}
		}
		return expertGarantieImpliqueDTOs;
	}

	public List<VisAVisDTO> ConvertirVisAVisDTO(List<VisAVisDTO> visAVisDTOs) {
		for (VisAVisDTO visAVisDTO : visAVisDTOs) {
			if (visAVisDTO == null && visAVisDTO.getNom() == null ) {
				visAVisDTOs.remove(visAVisDTO);
			}
		}
		return visAVisDTOs;
	}

	@Override
	public void historysaveReparateur(String entityName, Long entityId, ReparateurDTO oldValue, ReparateurDTO newValue,
			String operationName) {
		log.debug("log to service history save Reparateur impl ");
		// convertion de list de deux objet
		ConvertirGarantieDTO(oldValue.getGarantieImpliques());
		ConvertirGarantieDTO(newValue.getGarantieImpliques());
		ConvertirOrientationDTO(oldValue.getOrientations());
		ConvertirOrientationDTO(newValue.getOrientations());
		//ConvertirVisAVisDTO(oldValue.getVisAViss());
	//	ConvertirVisAVisDTO(newValue.getVisAViss());
		Javers javersList = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
				.build();
		Histories histories = new Histories();
		Operation operation = new Operation();
		Fields fieldsObj = new Fields();

		// comparaison de deux objet
		Diff diff = javersList.compare(oldValue, newValue);
		Diff difflistgetGarantieImpliques = javersList.compare(oldValue.getGarantieImpliques(),
				newValue.getGarantieImpliques());
		Diff difflistOrientations = javersList.compare(oldValue.getOrientations(), newValue.getOrientations());
		Diff difflistSpecialitePrincipales = javersList.compare(oldValue.getSpecialitePrincipales(),
				newValue.getSpecialitePrincipales());
		Diff difflistSpecialiteSecondaires = javersList.compare(oldValue.getSpecialiteSecondaires(),
				newValue.getSpecialiteSecondaires());
		Diff difflistVisAViss = javersList.compare(oldValue.getVisAViss(), newValue.getVisAViss());
		log.debug("log to service history save Reparateur impl  difflistgetGarantieImpliques"
				+ difflistgetGarantieImpliques.toString());
		log.debug("log to service history save Reparateur impl difflistOrientations" + difflistOrientations.toString());
		log.debug("log to service history save Reparateur impl  difflistSpecialitePrincipales"
				+ difflistSpecialitePrincipales.toString());
		log.debug("log to service history save Reparateur impl difflistSpecialiteSecondaires"
				+ difflistSpecialiteSecondaires.toString());
		log.debug("log to service history save Reparateur impl  difflistVisAViss" + difflistVisAViss.toString());
		log.debug("log to service history save Reparateur impl  difflistVisAViss change type"
				+ difflistVisAViss.getChangesByType(ValueChange.class).toString());
		log.debug("log to service history save Reparateur impl diff " + diff.toString());
		List<ValueChange> objetsDiff = diff.getChangesByType(ValueChange.class);
		Field field = new Field();

		field.setName("Liste Garanties Impliques");
		field.setNouvVal(difflistgetGarantieImpliques.getChanges().toString());
		fieldsObj.getField().add(field);

		field = new Field();
		field.setName("Liste orientations");
		field.setNouvVal(difflistOrientations.getChanges().toString());
		fieldsObj.getField().add(field);
		field = new Field();
		field.setName("Liste Specialite Principales");
		field.setNouvVal(difflistSpecialitePrincipales.getChanges().toString());
		fieldsObj.getField().add(field);
		field = new Field();
		field.setName("Liste Specialite Secondaires");
		field.setNouvVal(difflistSpecialiteSecondaires.getChanges().toString());
		fieldsObj.getField().add(field);

		field = new Field();
		field.setName("Liste VisAViss");
		field.setNouvVal(difflistVisAViss.getChanges().toString());
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
	 * Search for the reparateur corresponding to the query.
	 *
	 * @param query    the query of the search
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<ReparateurDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Reparateurs for query {}", query);
		Page<Reparateur> result = reparateurSearchRepository.search(queryStringQuery(query), pageable);
		return result.map(reparateurMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReparateurDTO> findByGouvernorat(Long gouvernoratId) {
		log.debug("Request to get reparateurs : {}", gouvernoratId);
		List<Reparateur> reparateurs = reparateurRepository.findReparateurByGouvernorat(gouvernoratId);
		return reparateurMapper.toDto(reparateurs);

	}

	@Override
	@Transactional(readOnly = true)
	public ReparateurDTO getReparateurByRegistreCommerce(String registreCommerce, String pname) {
		log.debug("Request to get RefRemorqueurDTO : {}", pname);
		Reparateur reparateur = reparateurRepository.findReparateurByRegistreCommerce(registreCommerce, pname);
		if (reparateur == null) {
			return null;
		}
		return reparateurMapper.toDto(reparateur);
	}

}
