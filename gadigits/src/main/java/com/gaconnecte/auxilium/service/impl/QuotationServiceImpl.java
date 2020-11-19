package com.gaconnecte.auxilium.service.impl;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;
import com.gaconnecte.auxilium.domain.Estimation;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.domain.Quotation;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.repository.QuotationRepository;
import com.gaconnecte.auxilium.repository.SinisterPecRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.QuotationService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.dto.EstimationDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.mapper.AttachmentMapper;
import com.gaconnecte.auxilium.service.mapper.EstimationMapper;
import com.gaconnecte.auxilium.service.mapper.QuotationMapper;
import com.gaconnecte.auxilium.service.mapper.SinisterPecMapper;
import com.gaconnecte.gadigits.gtestimate.services.EstimateService;
import com.gaconnecte.gadigits.gtestimate.xml.request.EstimateList;

@Service
@Transactional
public class QuotationServiceImpl implements QuotationService {

	private final Logger log = LoggerFactory.getLogger(QuotationServiceImpl.class);
	private final QuotationRepository quotationRepository;
	private EstimateService estimateService;
	private final QuotationMapper quotationMapper;
	private final AttachmentMapper attachmentMapper;
	private final EstimationMapper estimationMapper;
	private final FileStorageService fileStorageService;
	private final AttachmentRepository attachmentRepository;
	private final SinisterPecMapper sinisterPecMapper;

	@Autowired
	private UserService userService;
	@Autowired
	private SinisterPecRepository sinisterPecRepository;
	@Autowired
	private SinisterPecService sinisterPecService;
	@Autowired
	private UserExtraService userExtraService;
	@Autowired
	private ReparateurService reparateurService;

	public QuotationServiceImpl(AttachmentRepository attachmentRepository, FileStorageService fileStorageService,
			SinisterPecMapper sinisterPecMapper, QuotationRepository quotationRepository,
			QuotationMapper quotationMapper, EstimationMapper estimationMapper, AttachmentMapper attachmentMapper,
			EstimateService estimateService) {
		this.quotationRepository = quotationRepository;
		this.quotationMapper = quotationMapper;
		this.estimateService = estimateService;
		this.estimationMapper = estimationMapper;
		this.fileStorageService = fileStorageService;
		this.attachmentRepository = attachmentRepository;
		this.attachmentMapper = attachmentMapper;
		this.sinisterPecMapper = sinisterPecMapper;
	}

	/**
	 * Save a Qutation Attachments.
	 *
	 * @param conventionDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public QuotationDTO save(MultipartFile[] quotationFiles, QuotationDTO quotationDTO) {
		log.debug("Request to save Quotation with attachments : {}" + quotationDTO.getId());
		// cas de saisie manuelle du devis
		if (quotationDTO.getId() == null) {
			quotationDTO.setId(quotationRepository.getNextSeriesId());
		}
		// Treatment of attachment file
		Quotation quotation = quotationMapper.toEntity(quotationDTO);
		if (quotationFiles != null) {
			String name = "";
			for (int i = 0; i < quotationFiles.length; i++) {
				try {
					name = fileStorageService.storeFile(quotationFiles[i], Constants.ENTITY_QUOTATION);
				} catch (Exception ex) {
					// TODO: treat the exception
					log.error("Error when saving file", ex);
				}
				Attachment signedQuotationAttchment = new Attachment();
				signedQuotationAttchment.setCreationDate(LocalDateTime.now());
				signedQuotationAttchment.setEntityName(Constants.ENTITY_QUOTATION);
				signedQuotationAttchment.setOriginal(Boolean.FALSE);
				signedQuotationAttchment.setOriginalName(quotationFiles[i].getOriginalFilename());
				signedQuotationAttchment.setName(name);
				signedQuotationAttchment.setPath(Constants.ENTITY_QUOTATION);
				signedQuotationAttchment.setLabel(quotationFiles[i].getOriginalFilename());
				signedQuotationAttchment.setEntityId(quotationDTO.getId());
				attachmentRepository.save(signedQuotationAttchment);
			}
		}
		quotation = quotationRepository.save(quotation);
		QuotationDTO result = quotationMapper.toDto(quotation);
		return result;
	}

	/**
	 * update a Qutation Attachments.
	 *
	 * @param conventionDTO the entity to save
	 * @return the persisted entity
	 */

	@Override
	public QuotationDTO update(MultipartFile[] quotationFiles, QuotationDTO quotationDTO) {
		log.debug("Request to update Quotation with attachments : {}" + quotationDTO.getId());
		Quotation quotation = quotationMapper.toEntity(quotationDTO);
		Pageable pageable = null;
		@SuppressWarnings("unchecked")
		// list of attachments file of quotation
		List<Attachment> attachments = (List<Attachment>) attachmentRepository.findAttachments(pageable,
				quotation.getId());
		if (quotationFiles != null) {
			String name = "";
			Boolean etat;
			for (int i = 0; i < quotationFiles.length; i++) {
				etat = false;
				if (attachments != null) {
					for (int j = 0; j < attachments.size(); j++) {
						// update of attachments already exists (case 1)
						if (quotationFiles[i].getOriginalFilename() == attachments.get(j).getLabel()) {
							try {
								fileStorageService.updateFile(quotationFiles[i], Constants.ENTITY_QUOTATION,
										attachments.get(j).getName());
								etat = true;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
				if (etat == false) {
					try {
						// add new attachment (case 2)
						name = fileStorageService.storeFile(quotationFiles[i], Constants.ENTITY_QUOTATION);
						Attachment signedQuotationAttchment = new Attachment();
						signedQuotationAttchment.setCreationDate(LocalDateTime.now());
						signedQuotationAttchment.setEntityName(Constants.ENTITY_QUOTATION);
						signedQuotationAttchment.setOriginal(Boolean.FALSE);
						signedQuotationAttchment.setOriginalName(quotationFiles[i].getOriginalFilename());
						signedQuotationAttchment.setName(name);
						signedQuotationAttchment.setPath(Constants.ENTITY_QUOTATION);
						signedQuotationAttchment.setLabel(quotationFiles[i].getOriginalFilename());
						signedQuotationAttchment.setEntityId(quotationDTO.getId());
						attachmentRepository.save(signedQuotationAttchment);
					} catch (Exception ex) {
						// TODO: treat the exception
						log.error("Error when saving file", ex);
					}
				}
			}
		}
		// save Quotation
		quotation = quotationRepository.save(quotation);
		QuotationDTO result = quotationMapper.toDto(quotation);
		return result;
	}

	@Override
	public QuotationDTO update(QuotationDTO quotationDTO) {
		log.debug("Request to update Quotation : {}", quotationDTO);
		/*
		 * if (quotationDTO.getId() == null) {
		 * quotationDTO.setId(quotationRepository.getNextSeriesId()); }
		 */
		Quotation quotation = quotationMapper.toEntity(quotationDTO);
		quotation = quotationRepository.save(quotation);
		QuotationDTO result = quotationMapper.toDto(quotation);
		return result;
	}

	@Override
	public Set<QuotationDTO> findAll() {
		log.debug("Request to get all Quotation");
		List<QuotationDTO> list = quotationMapper.toDto(quotationRepository.findAll());
		return (new HashSet<QuotationDTO>(list));
	}

	@Override
	public QuotationDTO findOne(Long id) {
		log.debug("Request to get Quotation : {}", id);
		Quotation quotation = quotationRepository.findOne(id);
		// QuotationDTO qdto = quotationMapper.toDto(quotation);
		// for (DetailsPiecesDTO dp : qdto.getListPieces()) {
		// System.out.println("details total ttc**/*/" + dp.getTotalTtc());
		// }
		return quotationMapper.toDto(quotation);
	}

	@Override
	public Long getQuotationType(Long id) {
		log.debug("Request to get Quotation type : {}", id);
		return quotationRepository.getQuotationType(id);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Delegation : {}", id);
		quotationRepository.delete(id);
	}

	@Override
	public Float findTtcComplementary(Long idPec, Long idQuot) {
		log.debug("Request to get sum ttc complementary Quotation type : {}", idPec);
		return quotationRepository.findTtcComplementary(idPec, idQuot);
	}

	@Override
	@Transactional
	public EstimationDTO getCreationEstimateUrl(Long sinisterPecId) {
		//getEstimateListUrl();
		//System.out.println("testttttttttttttttttttttttt---gtMotive");
		Estimation estimation = new Estimation();
		log.debug("Request to get Url for create estimate");
		Long key = null;
		SinisterPecDTO sinPec = sinisterPecService.findOne(sinisterPecId);
		if (sinPec.getPrimaryQuotationId() == null) {
			PrimaryQuotation primaryQuotation = new PrimaryQuotation();
			primaryQuotation.setCreationDate(LocalDateTime.now());
			primaryQuotation = quotationRepository.save(primaryQuotation);
			key = primaryQuotation.getId();
			sinPec.setQuoteId(key);
			sinisterPecService.save(sinPec);
			/*SinisterPec sinisterPec = sinisterPecMapper.toEntity(sinPec);
			sinisterPec.setPrimaryQuotation(primaryQuotation);
			
			//sinPec.setPrimaryQuotationId(key);
			if (sinPec.getLightShock() == null || !(new Boolean(false)).equals(sinPec.getLightShock())) {
				sinisterPec.setLightShock(true);
			}
			sinisterPecRepository.save(sinisterPec);*/
		} else {
			if ((new Boolean(true)).equals(sinPec.getQuoteViaGt())) {
				key = sinPec.getPrimaryQuotationId();
				sinPec.setQuoteViaGt(null);
				sinisterPecService.save(sinPec);
			} else {
				ComplementaryQuotation complementaryQuotation = new ComplementaryQuotation();
				complementaryQuotation.setSinisterPec(sinisterPecMapper.toEntity(sinPec));
				complementaryQuotation.setCreationDate(LocalDateTime.now());
				complementaryQuotation = quotationRepository.save(complementaryQuotation);
				key = complementaryQuotation.getId();
			}
		}

		log.debug("Request to get Url for create estimate" + key);
		try {

			String login = SecurityUtils.getCurrentUserLogin();
			User user = userService.findOneUserByLogin(login);
			UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
			if ((new Long(28)).equals(userExtraDTO.getProfileId())) {
				ReparateurDTO reparateurDTO = reparateurService.findOne(userExtraDTO.getPersonId());
				estimation.setId(key);
				estimation.setUrl(estimateService.createEstimate("SGS036200C", "F83506D31B", "GT_TUNISIE",
						reparateurDTO.getLogin(), String.valueOf(key) ,String.valueOf(sinisterPecId)  ));// reparateurDTO.getLogin(),String.valueOf(key)));
			} else {
				estimation.setId(key);
				/*
				 * estimation.setUrl(estimateService.createEstimate("SGS036200C", "F83506D31B",
				 * "GT_TUNISIE", "testintegration", String.valueOf(key)));
				 */
				estimation.setUrl(estimateService.createEstimate("SGS036200C", "F83506D31B", "GT_TUNISIE",
						"testintegration", String.valueOf(key), String.valueOf(sinisterPecId)));
			}
			// AutenticationData(gtId, gtPWd, custumId, userId)
			// Numéro de client : GT_TUNISIE
			// Code d’utilisateur : Bassemtaktak
			// Mot de passe : Expert86
			// "SGS034000J", "FB3307D73U"
			// nticationData>
			// <gsId>"SGS036200C"</gsId>
			// <gsPwd>"F83506D31B"</gsPwd>

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return estimationMapper.toDto(estimation);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AttachmentDTO> findAttachments(Pageable pageable, Long id) {
		log.debug("Request to find Attachments  {}", id);
		Page<Attachment> result = attachmentRepository.findAttachments(pageable, id);
		return result.map(attachmentMapper::toDto);
	}

	@Override
	@Transactional
	public EstimationDTO getUpdateEstimateUrl(Long id, Long sinisterPecId) {
		Estimation estimation = new Estimation();
		log.debug("Request to get Url for create estimate");
		try {
			String login = SecurityUtils.getCurrentUserLogin();
			User user = userService.findOneUserByLogin(login);
			UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
			if ((new Long(28)).equals(userExtraDTO.getProfileId())) {
				ReparateurDTO reparateurDTO = reparateurService.findOne(userExtraDTO.getPersonId());
				estimation.setId(id);
				estimation.setUrl(estimateService.updateEstimate("SGS036200C", "F83506D31B", "GT_TUNISIE",
						/* "testintegration" */reparateurDTO.getLogin(), String.valueOf(id), String.valueOf(sinisterPecId)));// reparateurDTO.getLogin(),String.valueOf(key)));
			} else {
				estimation.setId(id);
				estimation.setUrl(estimateService.updateEstimate("SGS036200C", "F83506D31B", "GT_TUNISIE",
						"testintegration", String.valueOf(id), String.valueOf(sinisterPecId)));
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return estimationMapper.toDto(estimation);
	}
	
	@Override
	@Transactional
	public void getEstimateListUrl() {
		log.debug("Request to get Url for create estimate");
		try {
			String login = SecurityUtils.getCurrentUserLogin();
			User user = userService.findOneUserByLogin(login);
			UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
			
			//ReparateurDTO reparateurDTO = reparateurService.findOne(userExtraDTO.getPersonId());
			EstimateList listesEstimation = estimateService.getEstimates(new Date(), new Date());
			System.out.println("test");	
			System.out.println(listesEstimation);	
			

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		//return estimationMapper.toDto(estimation);
	}
	
	@Override
	@Transactional
	public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
    	synchronized (stompSession) {
	    	stompSession.subscribe("/topics/event", new StompFrameHandler() {
	
	            public Type getPayloadType(StompHeaders stompHeaders) {
	                return byte[].class;
	            }
	
	            public void handleFrame(StompHeaders stompHeaders, Object o) {
	                System.out.println("Received greeting " + new String((byte[]) o));
	            }
	        });
    	}
    }
	
	@Override
	@Transactional
    public void sendEventGtEstimate(StompSession stompSession, Long sinisterPecId, Long quotationId) {
		String jsonHello = "{\"typenotification\":\"gtEstimateSendEvent\",\"sinisterPecId\":"+ sinisterPecId +",\"quotationId\":" + quotationId + "}";
        stompSession.send("/app/hello", jsonHello.getBytes());
        stompSession.disconnect();
    }
	
	@Override
	@Transactional
    public void sendEventFirstConnexionGtEstimate(StompSession stompSession, Long sinisterPecId, Long quotationId) {
		String jsonHello = "{\"typenotification\":\"fakeEventGtEstimateSendEvent\",\"sinisterPecId\":"+ sinisterPecId +",\"quotationId\":" + quotationId + "}";
        stompSession.send("/app/hello", jsonHello.getBytes());
        stompSession.disconnect();
    }
}