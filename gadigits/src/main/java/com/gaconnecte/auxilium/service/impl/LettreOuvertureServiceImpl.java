package com.gaconnecte.auxilium.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.GovernorateService;
import com.gaconnecte.auxilium.service.LettreOuvertureService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.LettreOuvertureDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.TiersDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
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
public class LettreOuvertureServiceImpl implements LettreOuvertureService {

	private final ApplicationProperties properties;

	private final String lettre_ouverture_template = "/report/lettreOuverture.jrxml";

	// private static final String alliance_path = "/report/alliance.png";
	private static final String amana_path = "/report/amana.png";
	private static final String ami_path = "/report/ami.png";
	private static final String bh_path = "/report/bhAssurance.png";
	private static final String biat_path = "/report/biat.png";
	private static final String carte_path = "/report/carte.png";
	private static final String gat_path = "/report/gat.png";
	private static final String lloyd_path = "/report/lloyd.png";
	// private static final String peugeot_path = "/report/peugeot.png";

	@Autowired
	ContratAssuranceService contratAssuranceService;
	@Autowired
	AssureService assureService;
	@Autowired
	TiersService tiersService;
	@Autowired
	SinisterService sinisterService;
	@Autowired
	VehiculeAssureService vehiculeAssureService;
	@Autowired
	PartnerService partnerService;
	@Autowired
	GovernorateService governorateService;
	@Autowired
	AttachmentRepository attachmentRepository;

	@Autowired
	public LettreOuvertureServiceImpl(ApplicationProperties properties) {
		this.properties = properties;

	}

	/**
	 * infos to generate letter Ouverture
	 */

	@Override
	public Map<String, Object> infosToSendLetterOuvertureReport(SinisterPecDTO sinisterPecDTO) {
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		Map<String, Object> lettreOuvertureMap = new HashMap<String, Object>();
		LettreOuvertureDTO lettreOuvertureDTO = new LettreOuvertureDTO();
		if (sinisterPecDTO != null) {
			SinisterDTO sinisterDTO = new SinisterDTO();
			sinisterDTO = sinisterService.findOne(sinisterPecDTO.getSinisterId());
			if (sinisterDTO != null) {
				lettreOuvertureDTO.setDateAccident(dt.format(sinisterDTO.getIncidentDate()));
				lettreOuvertureDTO.setImmatriculation(sinisterDTO.getVehicleRegistration());
				lettreOuvertureDTO.setAdresseSinistre(sinisterDTO.getGovernorateLabel());
				lettreOuvertureDTO.setReferenceSinister(sinisterPecDTO.getCompanyReference() + "/GA");
				GovernorateDTO governorateDTO = governorateService.findOne(sinisterPecDTO.getGovernorateId());
				if (governorateDTO != null) {
					lettreOuvertureDTO.setAdresseSinistre(governorateDTO.getLabel());
				}
				Set<TiersDTO> tiersDTO = new HashSet<>();
				tiersDTO = sinisterPecDTO.getTiers();
				TiersDTO tierDTO = new TiersDTO();
				for (TiersDTO tr : tiersDTO) {
					if (tr.getResponsible() != null) {
						if (tr.getResponsible()) {
							tierDTO = tr;
							if (tierDTO != null) {
								lettreOuvertureDTO.setTiersName(tierDTO.getFullName());
								lettreOuvertureDTO.setTiersImmatriculation(tierDTO.getImmatriculation());
								lettreOuvertureDTO.setContratAssuranceNum(tierDTO.getNumeroContrat());
								PartnerDTO partnerDTO = partnerService.findOne(tierDTO.getCompagnieId());
								if (partnerDTO != null) {
									lettreOuvertureDTO.setAssuranceName(partnerDTO.getCompanyName());
									lettreOuvertureDTO.setAssuranceAdresse(partnerDTO.getAddress());
								}
							}
						}
					}
				}

				ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOne(sinisterDTO.getContractId());
				if (contratAssuranceDTO != null) {

					PartnerDTO partnerDTO = partnerService.findOne(contratAssuranceDTO.getClientId());
					if (partnerDTO != null) {
						lettreOuvertureDTO.setAssuranceAssureName(partnerDTO.getCompanyName());
						if (partnerDTO.getId().equals(1L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(bh_path));
						}
						if (partnerDTO.getId().equals(2L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(gat_path));
						}
						if (partnerDTO.getId().equals(3L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(carte_path));
						}
						if (partnerDTO.getId().equals(4L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(ami_path));
						}
						if (partnerDTO.getId().equals(5L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(biat_path));
						}
						if (partnerDTO.getId().equals(6L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(lloyd_path));
						}
						if (partnerDTO.getId().equals(7L)) {
							lettreOuvertureMap.put("logoCampanyPath", getClass().getResourceAsStream(amana_path));
						}
					}
					VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOne(sinisterDTO.getVehicleId());
					AssureDTO assureDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
					if (assureDTO != null) {
						if (assureDTO.getCompany()) {
							lettreOuvertureDTO.setAssureFullName(assureDTO.getRaisonSociale());
						} else {
							lettreOuvertureDTO.setAssureFullName(assureDTO.getNom() + " " + assureDTO.getPrenom());
						}
					}
					// lettreOuvertureMap.put("logoCampanyPath",
					// getClass().getResourceAsStream(logo_path));
					lettreOuvertureMap.put("lettreOuverture", lettreOuvertureDTO);
				}
			}

		}
		return lettreOuvertureMap;
	}

	/**
	 * generate letter Ouverture
	 */

	public void generateLetterOuverture(HttpServletResponse response, SinisterPecDTO sinisterPecDTO) {

		Map<String, Object> parameterMap = infosToSendLetterOuvertureReport(sinisterPecDTO);

		try {
			InputStream jasperStream = this.getClass().getResourceAsStream(lettre_ouverture_template);
			JasperDesign design = JRXmlLoader.load(jasperStream);
			JasperReport report = JasperCompileManager.compileReport(design);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());

			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition", "inline; filename=report1.pdf");

			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
			String uniqueFileName = "lettreOuverture_" + dtf.format(localDateTime) + ".pdf";
			response.addHeader("pdfname", properties.getCheminPdfReport() + "/" + uniqueFileName);
			
			Attachment lettreOuvertureAttchment = new Attachment();
			lettreOuvertureAttchment.setCreationDate(LocalDateTime.now());
			lettreOuvertureAttchment.setEntityName("LettreOuverture");
			lettreOuvertureAttchment.setOriginal(false);
			lettreOuvertureAttchment.setNote("LettreOuverture");
			lettreOuvertureAttchment.setOriginalName(uniqueFileName);
			lettreOuvertureAttchment.setName(uniqueFileName);
			lettreOuvertureAttchment.setEntityId(sinisterPecDTO.getId());
			lettreOuvertureAttchment.setPath("LettreOuverture");
			lettreOuvertureAttchment.setLabel("LettreOuverture");
			lettreOuvertureAttchment = attachmentRepository.save(lettreOuvertureAttchment);

			final OutputStream outputStream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					properties.getCheminFichier() + "/" + uniqueFileName);

		} catch (Exception e) {
			e.getStackTrace();
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
