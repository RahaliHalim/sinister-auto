package com.gaconnecte.auxilium.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.drools.FactDevis;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.ApecService;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.BonSortieService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.ReceiptService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.BonSortieDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.OrdreMissionExpertDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
import com.gaconnecte.auxilium.service.dto.ReceiptDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.VehicleUsageDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.web.rest.ReportResource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

	private final Logger log = LoggerFactory.getLogger(ReportResource.class);

	private final String bon_sortie_template = "/report/bonSortie.jrxml";

	private final String enquete_page1_template = "/report/enquetePage1.jrxml";

	// private final String enquete_page2_template = "/report/enquetePage2.jrxml";
	private final String ordre_mission_expert = "/report/ordreDeMissionsExpert.jrxml";
	private static final String logo_path = "/report/LogoSlogon.png";
	// private static final String slogon_path = "/report/slogon.png";
	private static final String footer_path = "/report/footer.png";
	private static final String enquete_path = "/report/enquete.png";

	private final ApplicationProperties properties;

	@Autowired
	SinisterService sinisterService;
	@Autowired
	ContratAssuranceService contratAssuranceService;
	@Autowired
	PartnerService partnerService;
	@Autowired
	VehiculeAssureService vehicleAssureService;
	@Autowired
	ReparateurService reparateurService;
	@Autowired
	ExpertService expertService;
	@Autowired
	BonSortieService bonSortieService;
	@Autowired
	ApecService apecService;
	@Autowired
	AssureService assureService;
	@Autowired
	SinisterPecService sinisterPecService;
	@Autowired
	private UserService userService;

	private final AttachmentRepository attachmentRepository;

	@Autowired
	public ReceiptServiceImpl(ApplicationProperties properties, AttachmentRepository attachmentRepository) {
		this.properties = properties;
		this.attachmentRepository = attachmentRepository;
	}

	@Override
	public Map<String, Object> infosToSendBonSortieReport(SinisterPecDTO sinisterPecDTO) {

		Map<String, Object> bonSortieMap = new HashMap<String, Object>();
		ReceiptDTO receiptDTO = new ReceiptDTO();
		if (sinisterPecDTO.getGenerationBonSortieDate() != null) {
			receiptDTO.setDateGenerationBonSortie(
					String.valueOf(sinisterPecDTO.getGenerationBonSortieDate().toLocalDate()));
		}
		if (sinisterPecDTO.getVehicleReceiptDate() != null) {
			receiptDTO.setDateReceiptionVehicule(String.valueOf(sinisterPecDTO.getVehicleReceiptDate().toLocalDate()));
		}
		receiptDTO.setBonSortieNumber(String.valueOf(sinisterPecService.countSinisterPec(sinisterPecDTO.getId())));
		Set<ApecDTO> apecs = apecService.findValidAccordBySinPec(sinisterPecDTO.getId());
		if (apecs.size() > 0) {
			Double droitDeTimbre = 0D;
			Double tva = 0D;
			Double reglepropor = 0D;
			Double franchise = 0D;
			Double depassplafond = 0D;
			Double vetuste = 0D;
			Double partResponsabilite = 0D;
			Double fraisdossier = 0D;
			Double engagementAssure = 0D;
			Double engagementga = 0D;
			Double ttcDetailsMo = 0D;
			Double ttcPieceR = 0D;
			Double ttcPieceIP = 0D;
			Double surplusEncaisse = 0D;
			Double soldeReparateur = 0D;
			Double soldefournisseur = 0D;
			Double droitDeTimbreReparateur = 0D;
			Double autre = 0D;
			Double totalPartAssure = 0D;
			Double totalFactureRepTtc = 0D;
			Double ttc = 0D;
			for (ApecDTO apecDTO : apecs) {
				if (apecDTO.getDroitDeTimbre() == null) {
					apecDTO.setDroitDeTimbre(0D);
				}
				droitDeTimbre = droitDeTimbre + apecDTO.getDroitDeTimbre();
				if (apecDTO.getParticipationTva() == null) {
					apecDTO.setParticipationTva(0D);
				}
				tva = tva + apecDTO.getParticipationTva();
				if (apecDTO.getRegleProportionnel() == null) {
					apecDTO.setRegleProportionnel(0D);
				}
				reglepropor = reglepropor + apecDTO.getRegleProportionnel();
				if (apecDTO.getEstimationFranchise() == null) {
					apecDTO.setEstimationFranchise(0D);
				}
				franchise = franchise + apecDTO.getEstimationFranchise();
				if (apecDTO.getDepacementPlafond() == null) {
					apecDTO.setDepacementPlafond(0D);
				}
				depassplafond = depassplafond + apecDTO.getDepacementPlafond();
				if (apecDTO.getParticipationVetuste() == null) {
					apecDTO.setParticipationVetuste(0D);
				}
				vetuste = vetuste + apecDTO.getParticipationVetuste();
				if (apecDTO.getParticipationRpc() == null) {
					apecDTO.setParticipationRpc(0D);
				}
				partResponsabilite = partResponsabilite + apecDTO.getParticipationRpc();
				if (apecDTO.getFraisDossier() == null) {
					apecDTO.setFraisDossier(0D);
				}
				fraisdossier = fraisdossier + apecDTO.getFraisDossier();
				if (apecDTO.getParticipationAssure() == null) {
					apecDTO.setParticipationAssure(0D);
				}
				engagementAssure = engagementAssure + apecDTO.getParticipationAssure();
				if (apecDTO.getParticipationGa() == null) {
					apecDTO.setParticipationGa(0D);
				}
				engagementga = engagementga + apecDTO.getParticipationGa();
				if (apecDTO.getMO() == null) {
					apecDTO.setMO(0D);
				}
				ttcDetailsMo = ttcDetailsMo + apecDTO.getMO();
				if (apecDTO.getPR() == null) {
					apecDTO.setPR(0D);
				}
				ttcPieceR = ttcPieceR + apecDTO.getPR();
				if (apecDTO.getIPPF() == null) {
					apecDTO.setIPPF(0D);
				}
				ttcPieceIP = ttcPieceIP + apecDTO.getIPPF();
				if (apecDTO.getSoldeReparateur() == null) {
					apecDTO.setSoldeReparateur(0D);
				}
				soldeReparateur = soldeReparateur + apecDTO.getSoldeReparateur();
				if (apecDTO.getSurplusEncaisse() == null) {
					apecDTO.setSurplusEncaisse(0D);
				}
				surplusEncaisse = surplusEncaisse + apecDTO.getSurplusEncaisse();
				if (apecDTO.getAvanceFacture() == null) {
					apecDTO.setAvanceFacture(0D);
				}
				autre = autre + apecDTO.getAvanceFacture();
				droitDeTimbreReparateur = droitDeTimbreReparateur + apecDTO.getDroitDeTimbre();
			}
			totalPartAssure = droitDeTimbre + tva + reglepropor + franchise + depassplafond + vetuste
					+ partResponsabilite + fraisdossier;
			totalFactureRepTtc = ttcDetailsMo + ttcPieceR + ttcPieceIP + droitDeTimbreReparateur;
			ttc = totalFactureRepTtc;
			receiptDTO.setDroitTimbre(String.valueOf(droitDeTimbre));
			receiptDTO.setTva(String.valueOf(tva));
			receiptDTO.setReglepropor(String.valueOf(reglepropor));
			receiptDTO.setFranchise(String.valueOf(franchise));
			receiptDTO.setDepassplafond(String.valueOf(depassplafond));
			receiptDTO.setVetuste(String.valueOf(vetuste));
			receiptDTO.setPartResponsabilite(String.valueOf(partResponsabilite));
			receiptDTO.setFraisdossier(String.valueOf(fraisdossier));
			receiptDTO.setEngagementAssure(String.valueOf(engagementAssure));
			receiptDTO.setEngagementga(String.valueOf(engagementga));
			receiptDTO.setTtcDetailsMo(String.valueOf(ttcDetailsMo));
			receiptDTO.setTtcPieceR(String.valueOf(ttcPieceR));
			receiptDTO.setTtcPieceIP(String.valueOf(ttcPieceIP));
			receiptDTO.setSurplusEncaisse(String.valueOf(surplusEncaisse));
			receiptDTO.setSoldeReparateur(String.valueOf(soldeReparateur));
			receiptDTO.setSoldefournisseur(String.valueOf(soldefournisseur));
			receiptDTO.setDroitDeTimbreReparateur(String.valueOf(droitDeTimbreReparateur));
			receiptDTO.setAutre(String.valueOf(autre));
			receiptDTO.setTotalPartAssure(String.valueOf(engagementAssure));
			receiptDTO.setTotalFactureRepTtc(String.valueOf(totalFactureRepTtc));
			receiptDTO.setTtc(String.valueOf(ttc));
		}
		SinisterDTO sinisterDTO = sinisterService.findOne(sinisterPecDTO.getSinisterId());
		if (sinisterDTO != null) {
			receiptDTO.setTelephoneAssure(sinisterDTO.getTel());
			receiptDTO.setNumSinister(sinisterPecDTO.getCompanyReference());
			receiptDTO.setIncidentDate(String.valueOf(sinisterDTO.getIncidentDate()));
			receiptDTO.setInsuredImmatriculation(sinisterDTO.getVehicleRegistration());
			receiptDTO.setReferenceSinister(sinisterDTO.getReference());

			ContratAssuranceDTO contratAssuranceDTO = new ContratAssuranceDTO();
			contratAssuranceDTO = contratAssuranceService.findOne(sinisterDTO.getContractId());
			if (contratAssuranceDTO != null) {
				receiptDTO.setNumContract(contratAssuranceDTO.getNumeroContrat());
				receiptDTO.setCompanyInsurance(contratAssuranceDTO.getNomCompagnie());
				receiptDTO.setAgenceName(contratAssuranceDTO.getAgenceNom());

				VehiculeAssureDTO vehicleAssureDTO = vehicleAssureService
						.findOne(sinisterDTO.getVehicleId());
				if (vehicleAssureDTO != null) {
					receiptDTO.setUsage(vehicleAssureDTO.getUsageLibelle());
					receiptDTO.setMarque(vehicleAssureDTO.getMarqueLibelle());
					AssureDTO assureDTO = assureService.findOne(vehicleAssureDTO.getInsuredId());
					if (assureDTO != null) {
						receiptDTO.setMail(assureDTO.getPremMail());
						receiptDTO.setInsuredName(assureDTO.getFullName());
					}
				}

			}
		}
		if (sinisterPecDTO.getReparateurId() != null) {
			ReparateurDTO reparateurDTO = reparateurService.findOne(sinisterPecDTO.getReparateurId());
			if (reparateurDTO != null) {
				receiptDTO.setRepairerName(reparateurDTO.getRaisonSociale());
			}
		}
		if (sinisterPecDTO.getExpertId() != null) {
			ExpertDTO expertDTO = expertService.findOne(sinisterPecDTO.getExpertId());
			if (expertDTO != null) {
				receiptDTO.setExpertName(expertDTO.getRaisonSociale());
			}
		}
		receiptDTO.setUnite("(DT)");
		BonSortieDTO bonSortieDTO = new BonSortieDTO();
		bonSortieDTO.setNumero(Double.valueOf(bonSortieService.findNewNumBonSortie()));
		receiptDTO.setNumBS(String.valueOf(bonSortieService.findNewNumBonSortie()));
		bonSortieService.save(bonSortieDTO);
		bonSortieMap.put("bonSortie", receiptDTO);
		bonSortieMap.put("logoSlogon", getClass().getResourceAsStream(logo_path));
		bonSortieMap.put("footer", getClass().getResourceAsStream(footer_path));
		bonSortieMap.put("logoSlogonEnquete", getClass().getResourceAsStream(logo_path));
		bonSortieMap.put("footerEnquete", getClass().getResourceAsStream(footer_path));
		bonSortieMap.put("enquete", getClass().getResourceAsStream(enquete_path));
		return bonSortieMap;
	}

	@Override
	public Map<String, Object> infosToOrdreMissionExpertReport(SinisterPecDTO sinisterPecDTO) {
		Map<String, Object> ordreMissionMap = new HashMap<String, Object>();
		OrdreMissionExpertDTO ordreMissionExpertDTO = new OrdreMissionExpertDTO();
		if (sinisterPecDTO.getGenerationBonSortieDate() != null) {
			ordreMissionExpertDTO.setDateOrdreMissionExpertSortie(String.valueOf(LocalDate.now()));
		}

		String login = SecurityUtils.getCurrentUserLogin();
		User user = userService.findOneUserByLogin(login);
		ordreMissionExpertDTO
				.setChargName(String.valueOf(user.getFirstName()).concat(" " + String.valueOf(user.getLastName())));
		ordreMissionExpertDTO.setModegestion(String.valueOf(sinisterPecDTO.getLabelModeGestion()));
		SinisterDTO sinisterDTO = sinisterService.findOne(sinisterPecDTO.getSinisterId());
		if (sinisterDTO != null) {
			ordreMissionExpertDTO.setTelephoneAssure(sinisterDTO.getTel());
			ordreMissionExpertDTO.setNumSinister(sinisterPecDTO.getCompanyReference());
			ordreMissionExpertDTO.setIncidentDate(String.valueOf(sinisterDTO.getIncidentDate()));
			ordreMissionExpertDTO.setInsuredImmatriculation(sinisterDTO.getVehicleRegistration());
			ordreMissionExpertDTO.setReferenceSinister(sinisterDTO.getReference());
			ContratAssuranceDTO contratAssuranceDTO = new ContratAssuranceDTO();
			contratAssuranceDTO = contratAssuranceService.findOne(sinisterDTO.getContractId());
			if (contratAssuranceDTO != null) {
				ordreMissionExpertDTO.setCompanyInsurance(contratAssuranceDTO.getNomCompagnie());

				VehiculeAssureDTO vehicleAssureDTO = vehicleAssureService
						.findOne(sinisterDTO.getVehicleId());
				if (vehicleAssureDTO != null) {
					if (sinisterPecDTO.getModeId().equals(6L) || sinisterPecDTO.getModeId().equals(10L)) {
						if (vehicleAssureDTO.getNewValueVehicle() != null) {
							ordreMissionExpertDTO
									.setCapitaleAssure(String.valueOf(vehicleAssureDTO.getNewValueVehicle()));
						} else {
							ordreMissionExpertDTO.setCapitaleAssure(" ");
						}
						if (vehicleAssureDTO.getNewValueVehicleFarnchise() != null) {
							ordreMissionExpertDTO
									.setFranchise(String.valueOf(vehicleAssureDTO.getNewValueVehicleFarnchise()));
						} else {
							ordreMissionExpertDTO.setFranchise(" ");
						}
					} else if (sinisterPecDTO.getModeId().equals(5L) || sinisterPecDTO.getModeId().equals(11L)) {
						if (vehicleAssureDTO.getDcCapital() != null) {
							ordreMissionExpertDTO.setCapitaleAssure(String.valueOf(vehicleAssureDTO.getDcCapital()));
						} else {
							ordreMissionExpertDTO.setCapitaleAssure(" ");
						}
						if (vehicleAssureDTO.getDcCapitalFranchise() != null) {
							ordreMissionExpertDTO
									.setFranchise(String.valueOf(vehicleAssureDTO.getDcCapitalFranchise()));
						} else {
							ordreMissionExpertDTO.setFranchise(" ");
						}
					} else if (sinisterPecDTO.getModeId().equals(7L)) {
						if (vehicleAssureDTO.getBgCapital() != null) {
							ordreMissionExpertDTO.setCapitaleAssure(String.valueOf(vehicleAssureDTO.getBgCapital()));
						} else {
							ordreMissionExpertDTO.setCapitaleAssure(" ");
						}
						if (vehicleAssureDTO.getBgCapitalFranchise() != null) {
							ordreMissionExpertDTO
									.setFranchise(String.valueOf(vehicleAssureDTO.getBgCapitalFranchise()));
						} else {
							ordreMissionExpertDTO.setFranchise(" ");
						}
					} else if (sinisterPecDTO.getModeId().equals(8L) || sinisterPecDTO.getModeId().equals(9L)) {
						if (vehicleAssureDTO.getMarketValue() != null) {
							ordreMissionExpertDTO.setCapitaleAssure(String.valueOf(vehicleAssureDTO.getMarketValue()));
						} else {
							ordreMissionExpertDTO.setCapitaleAssure(" ");
						}
						if (vehicleAssureDTO.getMarketValueFranchise() != null) {
							ordreMissionExpertDTO
									.setFranchise(String.valueOf(vehicleAssureDTO.getMarketValueFranchise()));
						} else {
							ordreMissionExpertDTO.setFranchise(" ");
						}
					} else {
						ordreMissionExpertDTO.setCapitaleAssure(" ");
						ordreMissionExpertDTO.setFranchise(" ");
					}

					ordreMissionExpertDTO.setnChassis(vehicleAssureDTO.getNumeroChassis());
					ordreMissionExpertDTO.setType(vehicleAssureDTO.getMarqueLibelle());
					ordreMissionExpertDTO
							.setDatepremierCirculation(String.valueOf(vehicleAssureDTO.getDatePCirculation()));
					if (vehicleAssureDTO.getPuissance() != null) {
						ordreMissionExpertDTO.setPuissance(String.valueOf(vehicleAssureDTO.getPuissance()));
					} else {
						ordreMissionExpertDTO.setPuissance(" ");
					}
					AssureDTO assureDTO = assureService.findOne(vehicleAssureDTO.getInsuredId());
					if (assureDTO != null) {
						ordreMissionExpertDTO.setAssureAdress(String.valueOf(assureDTO.getAdresse()));
						ordreMissionExpertDTO.setInsuredName(assureDTO.getFullName());
					}
				}
			}
		}
		if (sinisterPecDTO.getReparateurId() != null) {
			ReparateurDTO reparateurDTO = reparateurService.findOne(sinisterPecDTO.getReparateurId());
			if (reparateurDTO != null) {
				ordreMissionExpertDTO.setRepairerName(reparateurDTO.getRaisonSociale());
				ordreMissionExpertDTO.setRepairAdress(reparateurDTO.getAdresse());
				ordreMissionExpertDTO.setRepairTel(reparateurDTO.getTelPerVisVis());
				ordreMissionExpertDTO.setRepairFax(reparateurDTO.getFaxPerVisVis());
				ordreMissionExpertDTO.setRepairMail(reparateurDTO.getEmailPerVisVis());
			}
		}
		if (sinisterPecDTO.getExpertId() != null) {
			ExpertDTO expertDTO = expertService.findOne(sinisterPecDTO.getExpertId());
			if (expertDTO != null) {
				ordreMissionExpertDTO.setExpertName(expertDTO.getRaisonSociale());
				ordreMissionExpertDTO.setExpertAdresse(expertDTO.getAdresse());
				ordreMissionExpertDTO.setExpertTel(expertDTO.getTelephone());

			}
		}

		ordreMissionMap.put("ordreMissionExpertDTO", ordreMissionExpertDTO);
		ordreMissionMap.put("logoSlogon", getClass().getResourceAsStream(logo_path));
		ordreMissionMap.put("footer", getClass().getResourceAsStream(footer_path));
		return ordreMissionMap;
	}

	public void generateOrdreMissionExpert(HttpServletResponse response, SinisterPecDTO sinisterPecDTO) {
		log.debug("icii logg too save  generateOrdreMissionExpert  ");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		Map<String, Object> parameterMap = infosToOrdreMissionExpertReport(sinisterPecDTO);
		try {
			InputStream jasperStream = this.getClass().getResourceAsStream(ordre_mission_expert);
			JasperDesign design = JRXmlLoader.load(jasperStream);
			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint jasperPrintOrdreMission = JasperFillManager.fillReport(report, parameterMap,
					new JREmptyDataSource());
			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition", "inline; filename=report1.pdf");
			LocalDateTime localDateTime = LocalDateTime.now();
			String uniqueFileName = "ordreMissionExpert_" + dtf.format(localDateTime) + ".pdf";
			response.addHeader("pdfname", properties.getCheminPdfReport() + "/" + uniqueFileName);
			Attachment ordreMissionExpert = new Attachment();
			ordreMissionExpert.setCreationDate(LocalDateTime.now());
			ordreMissionExpert.setEntityName(Constants.ENTITY_ORDRE_MISSION);
			ordreMissionExpert.setOriginal(false);
			ordreMissionExpert.setNote("ordre de missuion Expert");
			ordreMissionExpert.setOriginalName(uniqueFileName);
			ordreMissionExpert.setName(uniqueFileName);
			ordreMissionExpert.setEntityId(sinisterPecDTO.getId());
			ordreMissionExpert.setPath(Constants.ENTITY_ORDRE_MISSION);
			ordreMissionExpert.setLabel("ordre de mission");
			ordreMissionExpert = attachmentRepository.save(ordreMissionExpert);
			final OutputStream outputStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrintOrdreMission, outputStream);
			JasperExportManager.exportReportToPdfFile(jasperPrintOrdreMission,
					properties.getCheminFichier() + "/" + uniqueFileName);

		} catch (Exception e) {
			e.getStackTrace();
			log.debug("icii cas d'exception" + e.getMessage());
		}

	}

	public void generateBonSortie(HttpServletResponse response, SinisterPecDTO sinisterPecDTO) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		SinisterPecDTO sinisterPecDTOF = sinisterPecService.save(sinisterPecDTO);

		Map<String, Object> parameterMap = infosToSendBonSortieReport(sinisterPecDTOF);

		try {
			InputStream jasperStream = this.getClass().getResourceAsStream(bon_sortie_template);
			JasperDesign design = JRXmlLoader.load(jasperStream);
			JasperReport report = JasperCompileManager.compileReport(design);
			JasperPrint jasperPrintBonSortie = JasperFillManager.fillReport(report, parameterMap,
					new JREmptyDataSource());

			InputStream jasperStreamEnquete1 = this.getClass().getResourceAsStream(enquete_page1_template);
			JasperDesign designEnquete1 = JRXmlLoader.load(jasperStreamEnquete1);
			JasperReport reportEnquete1 = JasperCompileManager.compileReport(designEnquete1);
			JasperPrint jasperPrintEnquete1 = JasperFillManager.fillReport(reportEnquete1, parameterMap,
					new JREmptyDataSource());

			JasperPrint jasperPrint = multipageLinking(jasperPrintBonSortie, jasperPrintEnquete1);

			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition", "inline; filename=report1.pdf");
			LocalDateTime localDateTime = LocalDateTime.now();
			String uniqueFileName = "bonSortie_" + dtf.format(localDateTime) + ".pdf";
			response.addHeader("pdfname", properties.getCheminPdfReport() + "/" + uniqueFileName);

			Attachment bonSortieAttchment = new Attachment();
			bonSortieAttchment.setCreationDate(LocalDateTime.now());
			bonSortieAttchment.setEntityName(Constants.ENTITY_BON_SORTIE);
			bonSortieAttchment.setOriginal(false);
			bonSortieAttchment.setNote("genérer bon Sortie");
			bonSortieAttchment.setOriginalName(uniqueFileName);
			bonSortieAttchment.setName(uniqueFileName);
			bonSortieAttchment.setEntityId(sinisterPecDTOF.getId());
			bonSortieAttchment.setPath(Constants.ENTITY_BON_SORTIE);
			bonSortieAttchment.setLabel("bon Sortie");
			bonSortieAttchment = attachmentRepository.save(bonSortieAttchment);

			final OutputStream outputStream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					properties.getCheminFichier() + "/" + uniqueFileName);

		} catch (Exception e) {
			e.getStackTrace();
			e.printStackTrace();
		}

	}

	/**
	 * Multi page Linking
	 */
	public JasperPrint multipageLinking(JasperPrint page1, JasperPrint page2) {
		List<JRPrintPage> pages = page2.getPages();
		for (int count = 0; count < pages.size(); count++) {
			page1.addPage((JRPrintPage) pages.get(count));
		}

		return page1;
	}

}
