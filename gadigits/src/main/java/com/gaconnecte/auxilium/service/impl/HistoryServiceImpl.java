package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.domain.History;
import com.gaconnecte.auxilium.domain.Journal;
import com.gaconnecte.auxilium.domain.PriseEnCharge;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.domain.Histories;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.service.*;
import com.gaconnecte.auxilium.repository.HistoryRepository;
import com.gaconnecte.auxilium.repository.QuotationRepository;
import com.gaconnecte.auxilium.repository.SinisterPecRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.*;
import com.gaconnecte.auxilium.service.mapper.HistoryMapper;
import com.gaconnecte.auxilium.domain.Histories.Operation;
import com.gaconnecte.auxilium.domain.Histories.Fields;
import com.gaconnecte.auxilium.domain.Histories.Fields.Field;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import com.gaconnecte.gadigits.gtestimate.utils.Constants;
import com.gaconnecte.gadigits.gtestimate.utils.XmlUtils;
import com.gaconnecte.gadigits.gtestimate.xml.XmlBean;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.javers.common.collections.Lists;

/**
 * Service Implementation for managing Journal.
 */
@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

	private final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

	private final HistoryRepository historyRepository;

	private final HistoryMapper historyMapper;

	private final UserService userService;


	public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper,
			 UserService userService) {
		this.historyRepository = historyRepository;
		this.historyMapper = historyMapper;
		this.userService = userService;
	}

	/**
	 * Save a journal.
	 *
	 * @param journalDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public HistoryDTO save(HistoryDTO historyDTO) {
		log.debug("Request to save history : {}", historyDTO);
		History history = historyMapper.toEntity(historyDTO);
		history = historyRepository.save(history);
		return historyMapper.toDto(history);

	}

	public String getActionObject(int oldStatus, int NewStatus, String action) {

		String operation = action;

		switch (oldStatus) {
		case 0:
			if (NewStatus == 1) {
				operation = "Création";
				break;
			}
		case 1:
			if (NewStatus == 1) {
				operation = "Modification";
				break;
			}
		}

		return operation;
	}

	public String getActionAssistance(int oldStatus, int NewStatus, String action) {

		String operation = action;

		switch (oldStatus) {

		case 0:
			operation = "Création Prestation";
			break;
		case 1:

			if (NewStatus == 2) {
				operation = "annuler Prestation";
				break;
			} else if (NewStatus == 3) {
				operation = "cloturé Prestation";
				break;
			} else {
				operation = "modification Prestation";
				break;
			}
		case 2:

			if (NewStatus == 1) {
				operation = "Réouvert Prestation";
				break;
			} else {
				operation = "modification Prestation";
				break;
			}
		case 3:

			if (NewStatus == 1) {
				operation = "Réouvert Prestation";
				break;
			} else {
				operation = "modification Prestation";
				break;
			}
		case 5:

			if (NewStatus == 1) {
				operation = "Réouvert Prestation";
				break;
			} else {
				operation = "modification Prestation";
				break;
			}
		}

		return operation;
	}

	public String getActionSinister(int oldStatus, int NewStatus, String action) {

		String operation = action;

		switch (oldStatus) {

		case 1:

			if (NewStatus == 2) {
				operation = "Dossier annulé";
				break;
			} else if (NewStatus == 3) {
				operation = "Dossier cloturé";
				break;
			} else {
				operation = "Traitement dossier";
				break;
			}
		case 2:

			if (NewStatus == 1) {
				operation = "Dossier Réouvert";
				break;
			} else {
				operation = "Traitement dossier";
				break;
			}
		case 3:

			if (NewStatus == 1) {
				operation = "Dossier Réouvert";
				break;
			} else {
				operation = "Traitement dossier";
				break;
			}
		case 5:
			if (NewStatus == 1) {
				operation = "Réouvert dossier";
				break;
			} else {
				operation = "Traitement dossier";
				break;
			}
		}

		return operation;
	}

	public Long getAction(int oldStatus, int NewStatus, String action) {

		Long operation = null;

		switch (oldStatus) {

		case 0:
			if (NewStatus == 1) {
				operation = 1L;
				break;
			}
		case 1:
			if (NewStatus == 2) {
				operation = 2L;
				break;
			}

			else if (NewStatus == 1) {
				operation = 52L;
				break;
			}
		case 2:
			if (NewStatus == 3) {
				operation = 3L;
				break;
			}

		case 100:
			if (NewStatus == 22) {
				operation = 67L;
				break;
			} else if (NewStatus == 104) {
				operation = 68L;
				break;
			} else if (NewStatus == 106) {
				operation = 60L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 167L;
				break;
			}

		case 12:
			if (NewStatus == 3) {
				operation = 53L;
				break;
			} else if (NewStatus == 12) {
				operation = 64L;
				break;
			}
		case 3:
			if (NewStatus == 30) {
				operation = 4L;
				break;
			} else if (NewStatus == 11) {
				operation = 62L;
				break;
			} else if (NewStatus == 3) {
				operation = 63L;
				break;
			}

		case 30:
			if (NewStatus == 12) {
				operation = 5L;
				break;
			} else if (NewStatus == 11) {
				operation = 6L;
				break;
			} else if (NewStatus == 13) {
				operation = 7L;
				break;
			} else if (NewStatus == 31) {
				operation = 8L;
				break;
			} else if (NewStatus == 32) {
				operation = 9L;
				break;
			} else if (NewStatus == 30) {
				operation = 10L;
				break;
			} else if (NewStatus == 9) {
				operation = 142L;
				break;
			} else if (NewStatus == 10) {
				operation = 143L;
				break;
			}
		case 11:
			if (NewStatus == 25) {
				operation = 12L;
				break;
			} else if (NewStatus == 22) {
				operation = 98L;
				break;
			} else if (NewStatus == 33) {
				operation = 121L;
				break;
			}

		case 13:
			if (NewStatus == 11) {
				operation = 54L;
				break;
			} else if (NewStatus == 3) {
				operation = 55L;
				break;
			}
		case 17:
			if (NewStatus == 17) {
				operation = 13L;
				break;
			} else if (NewStatus == 24) {
				operation = 14L;
				break;
			} else if (NewStatus == 22) {
				operation = 105L;
				break;
			} else if (NewStatus == 33) {
				operation = 125L;
				break;
			}

		case 25:
			if (NewStatus == 25) {
				operation = 15L;
				break;
			} else if (NewStatus == 15) {
				operation = 43L;
				break;
			} else if (NewStatus == 26) {
				operation = 16L;
				break;
			} else if (NewStatus == 8) {
				operation = 17L;
				break;
			} else if (NewStatus == 22) {
				operation = 96L;
				break;
			} else if (NewStatus == 33) {
				operation = 135L;
				break;
			}

		case 26:
			if (NewStatus == 8) {
				operation = 18L;
				break;
			} else if (NewStatus == 23) {
				operation = 19L;
				break;
			} else if (NewStatus == 17) {
				operation = 46L;
				break;
			} else if (NewStatus == 22) {
				operation = 97L;
				break;
			} else if (NewStatus == 33) {
				operation = 120L;
				break;
			} else if (NewStatus == 20) {
				operation = 152L;
				break;
			}else if (NewStatus == 106) {
				operation = 178L;
				break;
			}
		case 24:
			if (NewStatus == 26 || NewStatus == 16) {
				operation = 20L;
				break;
			} else if (NewStatus == 34) {
				operation = 22L;
				break;
			} else if (NewStatus == 24) {
				operation = 57L;
				break;
			} else if (NewStatus == 22) {
				operation = 103L;
				break;
			} else if (NewStatus == 33) {
				operation = 124L;
				break;
			}
		case 8:
			if (NewStatus == 20) {
				operation = 23L;
				break;
			} else if (NewStatus == 22) {
				operation = 100L;
				break;
			} else if (NewStatus == 33) {
				operation = 122L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 164L;
				break;
			}

		case 300:
			if (NewStatus == 300) {
				operation = 51L;
				break;
			}
		case 400:
			if (NewStatus == 400) {
				operation = 150L;
				break;
			}
		case 500:
			if (NewStatus == 500) {
				operation = 151L;
				break;
			}
		case 15:
			if (NewStatus == 26) {
				operation = 45L;
				break;

			} else if (NewStatus == 15) {
				operation = 56L;
				break;
			} else if (NewStatus == 8) {
				operation = 89L;
				break;
			} else if (NewStatus == 22) {
				operation = 115L;
				break;
			} else if (NewStatus == 33) {
				operation = 133L;
				break;
			}
		case 20:
			if (NewStatus == 15) {
				operation = 43L;
				break;
			} else if (NewStatus == 16) {
				operation = 90L;
				break;
			} else if (NewStatus == 17) {
				operation = 26L;
				break;
			} else if (NewStatus == 18) {
				operation = 27L;
				break;
			} else if (NewStatus == 21) {
				operation = 29L;
				break;
			} else if (NewStatus == 106) {
				operation = 44L;
				break;
			} else if (NewStatus == 30) {
				operation = 76L;
				break;
			} else if (NewStatus == 100) {
				operation = 25L;
				break;
			} else if (NewStatus == 22) {
				operation = 30L;
				break;
			} else if (NewStatus == 33) {
				operation = 139L;
				break;
			}
			 else if (NewStatus == 24) {
					operation = 163L;
					break;
				}

		case 110:
			if (NewStatus == 37) {
				operation = 47L;
				break;
			} else if (NewStatus == 33) {
				operation = 127L;
				break;
			}

			else if (NewStatus == 26) {
				operation = 70L;
				break;
			}

			else if (NewStatus == 24) {
				operation = 71L;
				break;
			} else if (NewStatus == 22) {
				operation = 108L;
				break;
			}

		case 34:
			if (NewStatus == 8) {
				operation = 50L;
				break;
			}if (NewStatus == 34) {
				operation = null;
				break;
			} else if (NewStatus == 24) {
				operation = 58L;
				break;
			} else if (NewStatus == 22) {
				operation = 106L;
				break;
			} else if (NewStatus == 33) {
				operation = 126L;
				break;
			} else if (NewStatus == 100) {
				operation = 153L;
				break;
			} else if (NewStatus == 106) {
				operation = 154L;
				break;
			} else if (NewStatus == 20) {
				operation = 155L;
				break;
			} else if (NewStatus == 16) {
				operation = 156L;
				break;
			}

		case 106:
			if (NewStatus == 107) {
				operation = 37L;
				break;

			} else if (NewStatus == 22) {
				operation = 38L;
				break;
			} else if (NewStatus == 200) {
				operation = 91L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 170L;
				break;
			}
			else if (NewStatus == 106) {
				operation = 178L;
				break;
			}

		case 107:
			if (NewStatus == 100) {
				operation = 33L;
				break;
			} else if (NewStatus == 103) {
				operation = 39L;
				break;
			} else if (NewStatus == 24) {
				operation = 40L;
				break;
			} else if (NewStatus == 33) {
				operation = 41L;
				break;
			}
			
		case 10:
			if (NewStatus == 3) {
				operation = 92L;
				break;
			}
		case 103:
			if (NewStatus == 109) {
				operation = 72L;
				break;
			} else if (NewStatus == 22) {
				operation = 113L;
				break;
			} else if (NewStatus == 33) {
				operation = 137L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 168L;
				break;
			}

		case 35:
			if (NewStatus == 36) {
				operation = 73L;
				break;
			} else if (NewStatus == 22) {
				operation = 117L;
				break;
			} else if (NewStatus == 33) {
				operation = 136L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 173L;
				break;
			}
		case 36:
			if (NewStatus == 40) {
				operation = 74L;
				break;
			} else if (NewStatus == 22) {
				operation = 118L;
				break;
			} else if (NewStatus == 33) {
				operation = 138L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 174L;
				break;
			}

		case 104:
			if (NewStatus == 106) {
				operation = 69L;
				break;
			}

			else if (NewStatus == 22) {
				operation = 112L;
				break;
			} else if (NewStatus == 33) {
				operation = 130L;
				break;
			}
			 else if (NewStatus == 24) {
					operation = 169L;
					break;
				}

		case 18:
			if (NewStatus == 19) {
				operation = 28L;
				break;
			} else if (NewStatus == 22) {
				operation = 66L;
				break;
			} else if (NewStatus == 30) {
				operation = 77L;
				break;
			} else if (NewStatus == 21) {

				operation = 80L;
				break;
			} else if (NewStatus == 100) {
				operation = 85L;
				break;
			} else if (NewStatus == 106) {
				operation = 86L;
				break;
			} else if (NewStatus == 17) {

				operation = 87L;
				break;
			} else if (NewStatus == 33) {
				operation = 140L;
				break;
			}
			else if (NewStatus == 24) {

				operation = 161L;
				break;
			}

		case 19:
			if (NewStatus == 22) {
				operation = 65L;
				break;
			} else if (NewStatus == 30) {
				operation = 78L;
				break;
			} else if (NewStatus == 21) {

				operation = 79L;
				break;
			} else if (NewStatus == 100) {
				operation = 82L;
				break;
			} else if (NewStatus == 106) {

				operation = 83L;
				break;
			} else if (NewStatus == 17) {
				operation = 84L;
				break;
			} else if (NewStatus == 33) {
				operation = 141L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 162L;
				break;
			}

		case 109:
			if (NewStatus == 110) {
				operation = 49L;
				break;
			} else if (NewStatus == 22) {
				operation = 114L;
				break;
			} else if (NewStatus == 33) {
				operation = 132L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 172L;
				break;
			}
		case 21:
			if (NewStatus == 26) {
				operation = 59L;
				break;
			} else if (NewStatus == 22) {
				operation = 102L;
				break;
			} else if (NewStatus == 21) {
				operation = 75L;
				break;
			} else if (NewStatus == 33) {
				operation = 123L;
				break;
			}

		case 37:
			if (NewStatus == 35) {
				operation = 48L;
				break;
			} else if (NewStatus == 22) {
				operation = 116L;
				break;
			} else if (NewStatus == 33) {
				operation = 134L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 134L;
				break;
			}

		case 32:

			if (NewStatus == 33) {
				operation = 175L;
				break;
			}
		case 22:
			if (NewStatus == 3) {
				operation = 81L;
				break;
			} else if (NewStatus == 9) {
				operation = 104L;
				break;
			}

		case 16:
			if (NewStatus == 22) {
				operation = 109L;
				break;
			} else if (NewStatus == 33) {
				operation = 128L;
				break;
			} else if (NewStatus == 100) {
				operation = 157L;
				break;
			} else if (NewStatus == 106) {
				operation = 158L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 165L;
				break;
			}
		case 27:
			if (NewStatus == 22) {
				operation = 111L;
				break;
			} else if (NewStatus == 33) {
				operation = 129L;
				break;
			} else if (NewStatus == 106) {
				operation = 159L;
				break;
			}
			else if (NewStatus == 24) {
				operation = 177L;
				break;
			}

		case 33:

			if (NewStatus == 10) {
				operation = 94L;
				break;
			} else if (NewStatus == 3) {
				operation = 95L;
				break;
			}

		case 9:
			if (NewStatus == 3) {
				operation = 93L;
				break;
			}
			
		case 40:
			if (NewStatus == 24) {
				operation = 176L;
				break;
			}
			
		default:

			if (NewStatus == 8) {
				operation = 32L;
				break;
			}

			break;
		}

		return operation;
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

	@Override
	public void historyApecSave(String entityName, Long entityId, ApecDTO apecDTO, Long actionId, SinisterPecDTO sinPec,
			String typeAccord) {

		// Create JAXB Context
		JAXBContext jaxbContext = null;
		if (typeAccord.equals("AccordModifie") || typeAccord.equals("AccordModifieDerogation")) {
			apecDTO.setApprouvDate(null);
			apecDTO.setAssureValidationDate(null);
			apecDTO.setDateGeneration(null);
			apecDTO.setImprimDate(null);
			apecDTO.setModifDate(null);
			apecDTO.setToApprouvDate(null);
			apecDTO.setValidationDate(null);
			apecDTO.setSignatureDate(null);
			apecDTO.setSinisterPec(null);
		}

		try {
			jaxbContext = JAXBContext.newInstance(ApecDTO.class);
			// Create Marshaller
			Marshaller jaxbMarshaller;
			jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console;
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(apecDTO, sw);
			// Verify XML Content
			String xmlContent = sw.toString();
			HistoryDTO historyDTO = new HistoryDTO();
			String login = SecurityUtils.getCurrentUserLogin();
			User user = userService.findOneUserByLogin(login);
			historyDTO.setDescriptionOfHistorization(xmlContent);
			historyDTO.setEntityName(entityName);
			historyDTO.setEntityId(entityId);
			historyDTO.setOperationDate(LocalDateTime.now());
			historyDTO.setActionId(actionId);
			historyDTO.setUserId(user.getId());
			historyDTO.setTypeAccord(selectTypeApec(sinPec, typeAccord));
			History history = historyMapper.toEntity(historyDTO);
			historyRepository.save(history);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void historyDevisSave(String entityName, Long entityId, QuotationDTO quotation, Long actionId,
			SinisterPecDTO sinPec, String historyAvisExpert) {

		quotation.setCreationDate(null);
		quotation.setAvisExpertDate(null);
		quotation.setRevueDate(null);
		quotation.setConfirmationDecisionQuoteDate(null);

		log.debug("entrer to save devis 1history");
		// Create JAXB Context
		JAXBContext jaxbContext = null;

		try {
			jaxbContext = JAXBContext.newInstance(QuotationDTO.class);
			// Create Marshaller
			Marshaller jaxbMarshaller;
			jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Print XML String to Console;
			StringWriter sw = new StringWriter();
			// Write XML to StringWriter
			jaxbMarshaller.marshal(quotation, sw);
			// Verify XML Content
			String xmlContent = sw.toString();
			HistoryDTO historyDTO = new HistoryDTO();
			String login = SecurityUtils.getCurrentUserLogin();
			User user = userService.findOneUserByLogin(login);
			historyDTO.setDescriptionOfHistorization(xmlContent);
			historyDTO.setEntityName(entityName);
			historyDTO.setEntityId(entityId);
			historyDTO.setOperationDate(LocalDateTime.now());
			historyDTO.setActionId(actionId);
			historyDTO.setUserId(user.getId());
			historyDTO.setTypeDevis(selectTypeDevis(sinPec, historyAvisExpert));
			History history = historyMapper.toEntity(historyDTO);
			historyRepository.save(history);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public String selectTypeDevis(SinisterPecDTO sinPec, String historyAvisExpert) {
		Integer size = sinPec.getListComplementaryQuotation().size();
		String typeDevis = null;
		String avExQuote = "AvisExpertQuotation";
		String iQuote = "InitialeQuotation";
		String derogationQuote = "DerogationQuotation";
		String updateQuote = "UpdateQuote";
		String complementaireQuote = "ComplementaireQuotation";
		String revueQuote = "RevueQuotation";
		String rectifyQuote = "RectifyQuotation";
		String modificationPrixQuote = "ModificationPrixQuotation";
		if (sinPec.getOldStepNw() == null) {
			sinPec.setOldStepNw(0L);
		}
		if (sinPec.getOldStepNw() != null) {

			if (size.equals(0)) {
				if (iQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Initial";
				}
				if (rectifyQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Rectifié";
				}
				if (revueQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Revue";
				}
				if (avExQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Avis Expert";
				}
				if (modificationPrixQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Modification Prix";
				}
				if (derogationQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis par dérogation";
				}
				if (updateQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis mis à jour";
				}

			} else {
				
				if (complementaireQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Complémentaire Initial N : " + size;
				}
				if (rectifyQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Complémentaire Rectifié N : " + size;
				}
				
				if (revueQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Complémentaire Revue N :" + size;
				}
				if (avExQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Complémentaire Avis Expert N :" + size;
				}
				
				if (modificationPrixQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Modification Prix N :" + size;
				}
				if (derogationQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis par dérogation";
				}
				
				if (updateQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis mis à jour";
				}
				if (iQuote.equals(historyAvisExpert)) {
					typeDevis = "Devis Initial";
				}

			}
		}

		return typeDevis;

	}

	public String selectTypeApec(SinisterPecDTO sinPec, String typeAForHistory) {
		Integer size = sinPec.getListComplementaryQuotation().size();
		String typeAccord = null;

		if (typeAForHistory.equals("ConstatPreliminaire")) {
			typeAccord = "Accord fusionné (Constat Preliminaire)";
		} else if (typeAForHistory.equals("Debloquage")) {
			typeAccord = "Accord fusionné (Debloquage)";
		} else if (typeAForHistory.equals("ModificationPrix")) {
			typeAccord = "Accord fusionné (ModificationPrix)";
		} else if (typeAForHistory.equals("AccordAnnule")) {
			typeAccord = "Accord fusionné (Accord Annulé)";
		} else if (typeAForHistory.equals("AccordModifie")) {
			typeAccord = "Accord Modifié";
		} else if (typeAForHistory.equals("AccordModifieDerogation")) {
			typeAccord = "Accord Modifié (Dérogation)";
		} else if (typeAForHistory.equals("AccordDerogation")) {
			typeAccord = "Accord dérogation";
		} else {
			if (size.equals(0)) {
				List<History> history = historyRepository.findHistoryByEntity(sinPec.getId(), "Appp");
				if (history.size() == 0) {
					typeAccord = "Accord Initial";
				}
			} else {
				List<History> history = historyRepository.findListAccordComplByHistory(sinPec.getId());
				if (history.size() == 0) {
					if (sinPec.getOldStepNw().equals(16L)) {
						typeAccord = "Accord complémentaire N : " + size;
					}
				}
			}
		}

		return typeAccord;
	}

	@Transactional(readOnly = true)
	public List<HistoryDTO> findListDevis(Long sinisterPecId) {

		return historyRepository.findListDevisByHistory(sinisterPecId).stream().map(historyMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

	}

	@Transactional(readOnly = true)
	public List<HistoryDTO> findListAccord(Long sinisterPecId) {

		return historyRepository.findListAccordByHistory(sinisterPecId).stream().map(historyMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));

	}

	@Override
	public void historysave(String entityName, Long entityId, Object oldValue, Object newValue, int lastStep,
			int newStep, String operationName) {
		log.debug("log to service history save impl ");
		// start javers
		Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
				.build();
		Histories histories = new Histories();
		Operation operation = new Operation();
		Fields fieldsObj = new Fields();
		// comparaison de deux objet
		Diff diff = javers.compare(oldValue, newValue);
		//System.out.println("test-------------------------------");
		//System.out.println(diff.toString());

		List<ValueChange> objets = diff.getChangesByType(ValueChange.class);

		/*log.debug("log to service history  1  testtt unit helkmi  test for expert " + diff.changesSummary());

		log.debug("log to service history 2 testtt unit helkmi  test for expert " + diff.toString());

		log.debug("log to service history 3testtt unit helkmi  test for expert " + diff.countByType());

		log.debug("log to service history 4 testtt unit helkmi  test for expert " + diff.getChanges());*/

		/*
		 * if(oldList != null && newList != null){ Diff diff2 =
		 * javers.compareCollections(oldList, oldList, Object.class);
		 * 
		 * log.debug("log to service history    testtt unit helkmi  test for expert "
		 * +diff2);
		 * 
		 * }
		 */
		for (int i = 0; i < objets.size(); i++) {
			Field field = new Field();
			field.setName(objets.get(i).getPropertyName());
			field.setAncienVal(String.valueOf(objets.get(i).getLeft()));
			field.setNouvVal(String.valueOf(objets.get(i).getRight()));
			fieldsObj.getField().add(field);
		}
		// intial history
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userService.findOneUserByLogin(login);
		operation.setDateOperation(LocalDateTime.now().toString());
		operation.setEntityName(entityName);
		if (operationName.equals("Prestations") && entityName.equals("Sinister_Prestation")) {
			operation.setName(this.getActionAssistance(lastStep, newStep, operationName));
		} else if (operationName.equals("sinister") && entityName.equals("sinister")) {
			operation.setName(this.getActionSinister(lastStep, newStep, operationName));
		} else if (entityName.equals("Expert") || entityName.equals("Reparateur") || entityName.equals("Partner")
				|| entityName.equals("Agency") || entityName.equals("DroitTimbre") || entityName.equals("Marque")
				|| entityName.equals("TauxTVA") || entityName.equals("VehicleEnergie") || entityName.equals("RaisonPec")
				|| entityName.equals("Modele") || entityName.equals("RaisonAssistance")
				|| entityName.equals("RefBareme") || entityName.equals("User") || entityName.equals("UsageVehicule")
				|| entityName.equals("Contrat") || entityName.equals("NaturePanne")) {
			operation.setName(this.getActionObject(lastStep, newStep, operationName));
		}
		if (user != null) {
			operation.setUserId(user.getId());
		}
		histories.setOperation(operation);
		histories.setFields(fieldsObj);
		// conversion de objet history to Xml
		String xml = "";
		try {
			xml = getXmlFromBean(histories, Constants.DEFAULT_ENCODING, false);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		System.out.println(xml);
		HistoryDTO historyDTO = new HistoryDTO();
		historyDTO.setDescriptionOfHistorization(xml);
		historyDTO.setEntityName(entityName);
		historyDTO.setEntityId(entityId);
		historyDTO.setOperationDate(LocalDateTime.now());
		if (operationName.equals("Prestations") && entityName.equals("Sinister_Prestation")) {
			historyDTO.setOperationName(this.getActionAssistance(lastStep, newStep, operationName));
		} else if (operationName.equals("sinister") && entityName.equals("sinister")) {
			historyDTO.setOperationName(this.getActionSinister(lastStep, newStep, operationName));
		} else if (entityName.equals("Expert") || entityName.equals("Reparateur") || entityName.equals("Partner")
				|| entityName.equals("Agency") || entityName.equals("DroitTimbre") || entityName.equals("Marque")
				|| entityName.equals("TauxTVA") || entityName.equals("VehicleEnergie") || entityName.equals("RaisonPec")
				|| entityName.equals("Modele") || entityName.equals("RaisonAssistance")
				|| entityName.equals("RefBareme") || entityName.equals("User") || entityName.equals("UsageVehicule")
				|| entityName.equals("Contrat")|| entityName.equals("NaturePanne")) {
			historyDTO.setOperationName(this.getActionObject(lastStep, newStep, operationName));
		} else if (operationName.equals("modification charge")) {
			historyDTO.setActionId(this.getAction(300, 300, operationName));
		} else if (operationName.equals("annulation_affectation_reparateur")) {
			historyDTO.setActionId(this.getAction(400, 400, operationName));
		} else if (operationName.equals("annulation_missionnement_expert")) {
			historyDTO.setActionId(this.getAction(500, 500, operationName));
		} else {
			historyDTO.setActionId(this.getAction(lastStep, newStep, operationName));
		}

		historyDTO.setUserId(user.getId());
		History history = historyMapper.toEntity(historyDTO);
		historyRepository.save(history);
	}

	@Override
	public QuotationDTO findHistoryQuotationByAction(Long actionId, Long entityId, String entityName) {
		HistoryDTO histories = new HistoryDTO();
		QuotationDTO quotationDTO = new QuotationDTO();
		histories = historyMapper.toDto(historyRepository.findHistoryQuotationByAction(actionId, entityId, entityName));
		if (histories != null) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(QuotationDTO.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(histories.getDescriptionOfHistorization());
				quotationDTO = (QuotationDTO) unmarshaller.unmarshal(reader);
			} catch (JAXBException e) {
				e.printStackTrace();

			}
			if (quotationDTO.getId() != null) {
				return quotationDTO;
			} else {
				return new QuotationDTO();
			}
		} else {
			return new QuotationDTO();
		}

	}

	@Override
	public void historysaveQuote(String entityName, Long entityId, Object oldValue, Object newValue, int lastStep,
			int newStep, String operationName, Long quotationId) {
		log.debug("log to service history save impl ");

		// start javers
		Javers javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
				.build();
		Histories histories = new Histories();
		Operation operation = new Operation();
		Fields fieldsObj = new Fields();
		// comparaison de deux objet
		Diff diff = javers.compare(oldValue, newValue);
		//System.out.println("test-------------------------------");
		//System.out.println(diff.toString());

		List<ValueChange> objets = diff.getChangesByType(ValueChange.class);

		/*log.debug("log to service history  1  testtt unit helkmi  test for expert " + diff.changesSummary());

		log.debug("log to service history 2 testtt unit helkmi  test for expert " + diff.toString());

		log.debug("log to service history 3testtt unit helkmi  test for expert " + diff.countByType());

		log.debug("log to service history 4 testtt unit helkmi  test for expert " + diff.getChanges());*/

		/*
		 * if(oldList != null && newList != null){ Diff diff2 =
		 * javers.compareCollections(oldList, oldList, Object.class);
		 * 
		 * log.debug("log to service history    testtt unit helkmi  test for expert "
		 * +diff2);
		 * 
		 * }
		 */
		for (int i = 0; i < objets.size(); i++) {
			Field field = new Field();
			field.setName(objets.get(i).getPropertyName());
			field.setAncienVal(String.valueOf(objets.get(i).getLeft()));
			field.setNouvVal(String.valueOf(objets.get(i).getRight()));
			fieldsObj.getField().add(field);
		}
		// intial history
		String login = SecurityUtils.getCurrentUserLogin();
		User user = userService.findOneUserByLogin(login);
		operation.setDateOperation(LocalDateTime.now().toString());
		operation.setEntityName(entityName);
		if (operationName.equals("Prestations") && entityName.equals("Sinister_Prestation")) {
			operation.setName(this.getActionAssistance(lastStep, newStep, operationName));
		} else if (operationName.equals("sinister") && entityName.equals("sinister")) {
			operation.setName(this.getActionSinister(lastStep, newStep, operationName));
		} else if (entityName.equals("Expert") || entityName.equals("Reparateur") || entityName.equals("Partner")
				|| entityName.equals("Agency") || entityName.equals("DroitTimbre") || entityName.equals("Marque")
				|| entityName.equals("TauxTVA") || entityName.equals("VehicleEnergie") || entityName.equals("RaisonPec")
				|| entityName.equals("Modele") || entityName.equals("RaisonAssistance")
				|| entityName.equals("RefBareme") || entityName.equals("User") || entityName.equals("UsageVehicule")) {
			operation.setName(this.getActionObject(lastStep, newStep, operationName));
		}
		if (user != null) {
			operation.setUserId(user.getId());
		}
		histories.setOperation(operation);
		histories.setFields(fieldsObj);
		// conversion de objet history to Xml
		String xml = "";
		try {
			xml = getXmlFromBean(histories, Constants.DEFAULT_ENCODING, false);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		System.out.println(xml);
		HistoryDTO historyDTO = new HistoryDTO();
		historyDTO.setDescriptionOfHistorization(xml);
		historyDTO.setEntityName(entityName);
		historyDTO.setEntityId(entityId);
		historyDTO.setOperationDate(LocalDateTime.now());
		if (operationName.equals("Prestations") && entityName.equals("Sinister_Prestation")) {
			historyDTO.setOperationName(this.getActionAssistance(lastStep, newStep, operationName));
		} else if (operationName.equals("sinister") && entityName.equals("sinister")) {
			historyDTO.setOperationName(this.getActionSinister(lastStep, newStep, operationName));
		} else if (entityName.equals("Expert") || entityName.equals("Reparateur") || entityName.equals("Partner")
				|| entityName.equals("Agency") || entityName.equals("DroitTimbre") || entityName.equals("Marque")
				|| entityName.equals("TauxTVA") || entityName.equals("VehicleEnergie") || entityName.equals("RaisonPec")
				|| entityName.equals("Modele") || entityName.equals("RaisonAssistance")
				|| entityName.equals("RefBareme") || entityName.equals("User") || entityName.equals("UsageVehicule")) {
			historyDTO.setOperationName(this.getActionObject(lastStep, newStep, operationName));
		} else if (operationName.equals("modification charge")) {
			historyDTO.setActionId(this.getAction(300, 300, operationName));
		} else if (operationName.equals("annulation_affectation_reparateur")) {
			historyDTO.setActionId(this.getAction(400, 400, operationName));
		} else if (operationName.equals("annulation_missionnement_expert")) {
			historyDTO.setActionId(this.getAction(500, 500, operationName));
		} else {
			historyDTO.setActionId(this.getAction(lastStep, newStep, operationName));

		}
		historyDTO.setQuotationId(quotationId);
		historyDTO.setUserId(user.getId());
		History history = historyMapper.toEntity(historyDTO);
		historyRepository.save(history);
	}

	@Override
	public QuotationDTO findHistoryQuotationById(Long historyId) {

		//System.out.println("test3------------------ " + historyId);
		HistoryDTO histories = new HistoryDTO();
		QuotationDTO quotationDTO = new QuotationDTO();
		histories = historyMapper.toDto(historyRepository.findOne(historyId));
		if (histories != null) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(QuotationDTO.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(histories.getDescriptionOfHistorization());
				quotationDTO = (QuotationDTO) unmarshaller.unmarshal(reader);
			} catch (JAXBException e) {
				e.printStackTrace();

			}
			if (quotationDTO.getId() != null) {
				return quotationDTO;
			} else {
				return new QuotationDTO();
			}
		} else {
			return new QuotationDTO();
		}

	}

	@Override
	public ApecDTO findHistoryApecById(Long historyId) {

		//System.out.println("test3------------------ " + historyId);
		HistoryDTO histories = new HistoryDTO();
		ApecDTO apecDTO = new ApecDTO();
		histories = historyMapper.toDto(historyRepository.findOne(historyId));
		if (histories != null) {
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(ApecDTO.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(histories.getDescriptionOfHistorization());
				apecDTO = (ApecDTO) unmarshaller.unmarshal(reader);
			} catch (JAXBException e) {
				e.printStackTrace();

			}
			if (apecDTO.getId() != null) {
				return apecDTO;
			} else {
				return new ApecDTO();
			}
		} else {
			return new ApecDTO();
		}

	}

	@Override
	public Page<HistoryDTO> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public HistoryDTO findOne(Long id) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}

	public static Histories getBeanFromXml(Class<Histories> class1, String descriptionOfHistorization)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(class1);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(descriptionOfHistorization);
		return (Histories) unmarshaller.unmarshal(reader);
	}

	@Override
	public List<HistoryDTO> findHistoryByEntity(Long entityId, String entityName) {
		log.debug("request to get all Histories By Entity");
		List<HistoryDTO> histories = new ArrayList<HistoryDTO>();
		histories = historyMapper.toDto(historyRepository.findHistoryByEntity(entityId, entityName));
		for (int i = 0; i < histories.size(); i++) {
			Histories historie = new Histories();
			List<ChangeValueDTO> changeValueDTOs = new ArrayList<ChangeValueDTO>();
			Fields fieldsObj = new Fields();
			try {
				historie = getBeanFromXml(Histories.class, histories.get(i).getDescriptionOfHistorization());
				fieldsObj = historie.getFields();
				for (int j = 0; j < fieldsObj.getField().size(); j++) {
					ChangeValueDTO changeValueDTO = new ChangeValueDTO();
					changeValueDTO.setNameAttribute(fieldsObj.getField().get(j).getName());
					changeValueDTO.setLastValue(fieldsObj.getField().get(j).getAncienVal());
					changeValueDTO.setNewValue(fieldsObj.getField().get(j).getNouvVal());
					changeValueDTOs.add(changeValueDTO);
				}
				histories.get(i).setChangeValues(changeValueDTOs);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return histories;
	}

	@Override
	public HistoryDTO findHistoryByEntityAndAssistance(Long entityId, String entityName, String operation) {
		log.debug("request to get all Histories By Entity");
		HistoryDTO histories = new HistoryDTO();
		histories = historyMapper
				.toDto(historyRepository.findHistoryByEntityAndAssistance(entityId, entityName, operation));
		return histories;

	}

	@Override
	public HistoryDTO findHistoryByEntity(Long id) {
		return null;
	}

	@Override
	public void historysaveUser(String entityName, Long entityId, UserExtraDTO oldValue, UserExtraDTO newValue,
			String operationName) {
		log.debug("log to service history save Reparateur impl ");

		Javers javersList = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
				.build();
		Histories histories = new Histories();
		Operation operation = new Operation();
		Fields fieldsObj = new Fields();

		// comparaison de deux objet
		Diff diff = javersList.compare(oldValue, newValue);
		Diff difflistgetPartnerModes = javersList.compare(oldValue.getUserPartnerModes(),
				newValue.getUserPartnerModes());
		Diff difflistUserAccess = javersList.compare(oldValue.getAccesses(), newValue.getAccesses());
		Diff difflistPartnerModesMapping = javersList.compare(oldValue.getPartnerModes(), newValue.getPartnerModes());
		Diff diffUserAccessWork = javersList.compare(oldValue.getUserAccessWork(), newValue.getUserAccessWork());
		System.out.println("testHistoriesUsers");
		System.out.println(diff.toString());

		List<ValueChange> objetsDiff = diff.getChangesByType(ValueChange.class);
		Field field = new Field();

		field.setName("Liste Partner Modes");
		field.setNouvVal(difflistgetPartnerModes.getChanges().toString());
		fieldsObj.getField().add(field);
		System.out.println("Liste Partner Modes");
		System.out.println(difflistgetPartnerModes.toString());

		for (int i = 0; i < difflistgetPartnerModes.getChanges().size(); i++) {
			System.out.println("test -----------------------------");
			System.out.println(difflistgetPartnerModes.getChanges().get(i).toString());
		}

		field = new Field();
		field.setName("Liste Users Access");
		field.setNouvVal(difflistUserAccess.getChanges().toString());
		fieldsObj.getField().add(field);
		field = new Field();
		field.setName("Liste Partner Modes Mappers");
		field.setNouvVal(difflistPartnerModesMapping.getChanges().toString());
		fieldsObj.getField().add(field);
		field = new Field();
		field.setName("Liste User Access Work");
		field.setNouvVal(diffUserAccessWork.getChanges().toString());
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
}