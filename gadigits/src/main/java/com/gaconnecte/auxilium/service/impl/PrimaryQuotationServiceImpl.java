package com.gaconnecte.auxilium.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.DetailsPieces;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.domain.Quotation;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.enumeration.NaturePiece;
import com.gaconnecte.auxilium.repository.PrimaryQuotationRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.PrimaryQuotationService;
import com.gaconnecte.auxilium.service.QuotationStatusService;
import com.gaconnecte.auxilium.service.RefTypeInterventionService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.VehiclePieceService;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.mapper.PrimaryQuotationMapper;
import com.gaconnecte.auxilium.service.mapper.QuotationStatusMapper;
import com.gaconnecte.auxilium.service.mapper.RefTypeInterventionMapper;
import com.gaconnecte.auxilium.service.mapper.VehiclePieceMapper;
import com.gaconnecte.gadigits.gtestimate.services.EstimateService;
import com.gaconnecte.gadigits.gtestimate.xml.response.Estimate;
import com.gaconnecte.gadigits.gtestimate.xml.response.Operation;

@Service
@Transactional
public class PrimaryQuotationServiceImpl implements PrimaryQuotationService {

	private final Logger log = LoggerFactory.getLogger(PrimaryQuotationServiceImpl.class);

	private final PrimaryQuotationRepository primaryQuotationRepository;

	private final PrimaryQuotationMapper primaryQuotationMapper;

	private final EstimateService estimateService;

	private final VehiclePieceService vehiclePieceService;
	private final VehiclePieceMapper vehiclePieceMapper;
	private final QuotationStatusMapper quotationStatusMapper;
	private final RefTypeInterventionMapper refTypeInterventionMapper;
	@Autowired
	private QuotationStatusService quotationStatusService;
	@Autowired
	private RefTypeInterventionService refTypeInterventionService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserExtraService userExtraService;
	@Autowired
	private ReparateurService reparateurService;
	
	public PrimaryQuotationServiceImpl(VehiclePieceService vehiclePieceService, VehiclePieceMapper VehiclePieceMapper,
			RefTypeInterventionMapper refTypeInterventionMapper, PrimaryQuotationRepository primaryQuotationRepository,
			PrimaryQuotationMapper primaryQuotationMapper, EstimateService estimateService,
			QuotationStatusMapper quotationStatusMapper) {
		this.primaryQuotationRepository = primaryQuotationRepository;
		this.primaryQuotationMapper = primaryQuotationMapper;
		this.estimateService = estimateService;
		this.quotationStatusMapper = quotationStatusMapper;
		this.refTypeInterventionMapper = refTypeInterventionMapper;
		this.vehiclePieceMapper = VehiclePieceMapper;
		this.vehiclePieceService = vehiclePieceService;

	}

	@Override
	public PrimaryQuotationDTO save(PrimaryQuotationDTO primaryQuotationDTO) {

		log.debug("Request to save PrimaryQuotation : {}", primaryQuotationDTO);
		PrimaryQuotation primaryQuotation = primaryQuotationMapper.toEntity(primaryQuotationDTO);
		primaryQuotation = primaryQuotationRepository.save(primaryQuotation);
		PrimaryQuotationDTO result = primaryQuotationMapper.toDto(primaryQuotation);

		return result;
	}

	@Override
	public PrimaryQuotationDTO update(PrimaryQuotationDTO primaryQuotationDTO) {
		log.debug("Request to update PrimaryQuotation : {}", primaryQuotationDTO);
		PrimaryQuotation primaryQuotation = primaryQuotationMapper.toEntity(primaryQuotationDTO);
		primaryQuotation = primaryQuotationRepository.save(primaryQuotation);
		PrimaryQuotationDTO result = primaryQuotationMapper.toDto(primaryQuotation);
		return result;
	}

	@Override
	public Set<PrimaryQuotationDTO> findAll() {

		log.debug("Request to get all PrimaryQuotation");
		List<PrimaryQuotationDTO> list = primaryQuotationMapper.toDto(primaryQuotationRepository.findAll());
		return (new HashSet<PrimaryQuotationDTO>(list));

	}

	@Override
	public PrimaryQuotationDTO findOne(Long id) {

		log.debug("Request to get PrimaryQuotation : {}", id);
		PrimaryQuotation primaryQuotation = primaryQuotationRepository.findOne(id);
		return primaryQuotationMapper.toDto(primaryQuotation);
	}

	@Override
	public PrimaryQuotationDTO findPrimaryQuotationById(Long id) {
		log.debug("Request to get PrimaryQuotation : {}", id);
		return primaryQuotationMapper.toDto(primaryQuotationRepository.findPrimaryQuotationById(id));

	}

	/**
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @methode who goes through the list of operations of an estimate and saves in
	 *          details pieces
	 */
	void detailsPiece(List<Operation> Results, List<DetailsPieces> detailsPiecesList, Quotation quotation)
			throws IllegalAccessException, InvocationTargetException {
		log.debug("REST request to add   operation list : " + Results.size());
		DetailsPieces detailsPieces = new DetailsPieces();
		for (int i = 0; i < Results.size(); i++) {
			detailsPieces = new DetailsPieces();
			// auto create a novel parts
			log.debug("REST request to see ref action description" + Results.get(i).getActionDescription().getValue());
			log.debug("REST request to see ref action description resultats" + Results.get(i).toString());
			if (Results.get(i).getActionDescription().getValue().equals("Remplacer")) {
				detailsPieces.setIsMo(false);
				detailsPieces.setTypeIntervention(
						refTypeInterventionMapper.toEntity(refTypeInterventionService.findOne((long) 2)));
				detailsPieces.setQuantite(1F);
				detailsPieces.setDesignation(vehiclePieceMapper.toEntity(
						vehiclePieceService.findVehiclePieceByReferenceAndType(Results.get(i).getShortNumber(), 1L)));
				detailsPieces.setNaturePiece(NaturePiece.GENERIQUE);
				detailsPieces.setReference(Results.get(i).getPartNumber());
			}
			// auto create a novel labor repair
			else if (Results.get(i).getActionDescription().getValue().equals("Réparer")) {
				detailsPieces.setIsMo(true);
				detailsPieces.setTypeIntervention(
						refTypeInterventionMapper.toEntity(refTypeInterventionService.findOne((long) 1)));
				detailsPieces.setDesignation(vehiclePieceMapper.toEntity(
						vehiclePieceService.findVehiclePieceByReferenceAndType(Results.get(i).getShortNumber(), 1L)));

				detailsPieces.setNombreHeures(Results.get(i).getPrecalculation().getLabourTime().floatValue());
			}
			else if (Results.get(i).getActionDescription().getValue().equals("Peindre")) {
				detailsPieces.setIsMo(true);
				detailsPieces.setTypeIntervention(
						refTypeInterventionMapper.toEntity(refTypeInterventionService.findOne((long) 3)));
				detailsPieces.setDesignation(vehiclePieceMapper.toEntity(
						vehiclePieceService.findVehiclePieceByReferenceAndType(Results.get(i).getShortNumber(), 1L)));

				detailsPieces.setNombreHeures(Results.get(i).getPrecalculation().getLabourTime().floatValue());
			}

			// auto create a novel labor paint
			else {
				detailsPieces.setIsMo(true);
				detailsPieces.setTypeIntervention(
						refTypeInterventionMapper.toEntity(refTypeInterventionService.findOne((long) 2)));
				detailsPieces.setDesignation(vehiclePieceMapper.toEntity(
						vehiclePieceService.findVehiclePieceByReferenceAndType(Results.get(i).getShortNumber(), 1L)));
				detailsPieces.setNombreHeures(Results.get(i).getPrecalculation().getLabourTime().floatValue());
			}
			detailsPieces.setQuotation(quotation);
			detailsPieces.setTotalTtc(Results.get(i).getPrecalculation().getTotal().floatValue());
			detailsPieces.setTva((float) 19);
			detailsPieces.setDiscount((float) 0);
			//detailsPieces.set((Double) Results.get(i).getPrecalculation().getPriceMaterial().doubleValue());
			detailsPieces.setPrixUnit((Double) Results.get(i).getPrecalculation().getPriceMaterial().doubleValue());
			detailsPieces.setTotalHt(Results.get(i).getPrecalculation().getTotal().floatValue());
			detailsPiecesList.add(detailsPieces);
		}
	}
	
	
	@Override
	public PrimaryQuotation getEstimation(Long id, Long prestationId)
			throws IllegalAccessException, InvocationTargetException {
		
		log.debug("REST request to create Devis Gtestimation  on BD}");
		Estimate estimateDevis = null;
		PrimaryQuotation quotation = new PrimaryQuotation();
		try {
			String login = SecurityUtils.getCurrentUserLogin();
			User user = userService.findOneUserByLogin(login);
			UserExtraDTO userExtraDTO = userExtraService.finPersonneIdByUser(user.getId());
	        ReparateurDTO reparateurDTO = reparateurService.findOne(userExtraDTO.getPersonId());
			estimateDevis = estimateService.getEstimate(reparateurDTO.getLogin(), reparateurDTO.getPwd(), "GT_TUNISIE",reparateurDTO.getLogin(),String.valueOf(id));
			/* quotation fill from estimation */
			log.debug("REST tto test taillle de la list" + estimateDevis.getOperationList().getOperation().size());
			ZoneId timeZone = ZoneId.systemDefault();
			quotation.setCreationDate(LocalDateTime
					.parse(estimateDevis.getDateCreation(), DateTimeFormatter.ISO_DATE_TIME));
			quotation.setId(id);
			quotation.setTtcAmount(estimateDevis.getResult().getTotal().doubleValue());
			quotation.setHtAmount(estimateDevis.getResult().getTaxBase().doubleValue());
			quotation.setStatus(quotationStatusMapper.toEntity(quotationStatusService.findOne((long) 1)));
			List<DetailsPieces> listDetailsPieces = new ArrayList<>();
			List<Operation> operations = estimateDevis.getOperationList().getOperation();
			/* here the supplies ingredients */
			DetailsPieces detailsPieces = new DetailsPieces();
		
			this.detailsPiece(operations, listDetailsPieces, quotation);
			quotation.setListPieces(listDetailsPieces);

		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		return quotation;

	}

}