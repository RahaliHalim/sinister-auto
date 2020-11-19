package com.gaconnecte.auxilium.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.GovernorateService;
import com.gaconnecte.auxilium.service.LettreOuvertureService;
import com.gaconnecte.auxilium.service.LoueurService;
import com.gaconnecte.auxilium.service.OrdrePrestationVrService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.SinisterPrestationService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.LoueurDTO;
import com.gaconnecte.auxilium.service.dto.OrdrePrestationVrDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.TiersDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.web.rest.ReportResource;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
@Transactional
public class OrdrePrestationVrServiceImpl implements OrdrePrestationVrService{

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);


    private final String ordre_prestation_template = "/report/ordre_de_prestation_vr.jrxml";

    private static final String logo_path = "/report/LogoSlogon.png";
 
    
 
    private final ApplicationProperties properties;  

    @Autowired
    AssureService assureService;
    @Autowired
    SinisterService sinisterService;
    @Autowired
    VehiculeAssureService vehiculeAssureService;
    @Autowired
    SinisterPrestationService sinisterPrestationService;
    @Autowired
	AttachmentRepository attachmentRepository;
    @Autowired
     LoueurService loueurService;
    @Autowired
    public OrdrePrestationVrServiceImpl(ApplicationProperties properties) {
        this.properties = properties;

    }
    
    /**
     * infos to generate letter IDA
     */
    @Override
    public Map<String, Object> infosToSendOrdrePrestationVr(SinisterPrestationDTO sinisterPrestationDTO) {

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Map<String, Object> ordrePrestationVrMap = new HashMap<String, Object>();
        OrdrePrestationVrDTO ordrePrestationVrDTO = new OrdrePrestationVrDTO();
        if (sinisterPrestationDTO != null) {
        	
        	ordrePrestationVrDTO.setNumPrestation(String.valueOf(sinisterPrestationService.countPrestationVr()));
            ordrePrestationVrDTO.setReferenceDossier(sinisterPrestationDTO.getReference());
            //ordrePrestationVrDTO.setNomRaison(sinisterPrestationDTO.getLoueurLabel());
           // LoueurDTO loueurDTO = loueurService.findOne(sinisterPrestationDTO.getLoueurId());
           // if (loueurDTO != null) {
               // ordrePrestationVrDTO.setContact(loueurDTO.getTelephone());
           // }
            ordrePrestationVrDTO.setDeuxiemeConducteur("");
            ordrePrestationVrDTO.setNomPrenom(sinisterPrestationDTO.getInsuredName());
            ordrePrestationVrDTO.setTelephone(sinisterPrestationDTO.getInsuredFirstTel());
            ordrePrestationVrDTO.setImmatriculation(sinisterPrestationDTO.getVehicleRegistration());

            SinisterDTO sinisterDTO = new SinisterDTO();
             sinisterDTO = sinisterService.findOne(sinisterPrestationDTO.getSinisterId());
            if (sinisterDTO != null) {
            	VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOne(sinisterDTO.getVehicleId());
                ordrePrestationVrDTO.setMarque(vehiculeAssureDTO.getMarqueLibelle());
                ordrePrestationVrDTO.setCie(vehiculeAssureDTO.getCompagnyName());

            	AssureDTO assureDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());           	
            	if (assureDTO != null) {
                    ordrePrestationVrDTO.setAdresse(assureDTO.getAdresse());

            	}

            }
            
            //ordrePrestationVrDTO.setJours(sinisterPrestationDTO.getDays());
            //ordrePrestationVrDTO.setDate1(sinisterPrestationDTO.getDeliveryDate());
           // ordrePrestationVrDTO.setDate2(sinisterPrestationDTO.getExpectedReturnDate());

           // SinisterDTO sinisterDTO = new SinisterDTO();
           // sinisterDTO = sinisterService.findOne(sinisterPrestationDTO.getSinisterId());
            
            ordrePrestationVrMap.put("logo_ga", getClass().getResourceAsStream(logo_path));
	        ordrePrestationVrMap.put("ordrePrestationVrDTO", ordrePrestationVrDTO);
        }
	        return ordrePrestationVrMap;
    }

    /**
     * generate letter IDA
     */

    public void generateOrdrePrestationVr(HttpServletResponse response, Long sinisterPrestationId) {

    	SinisterPrestationDTO sinisterPrestationDTO = sinisterPrestationService.findOne(sinisterPrestationId);
        Map<String, Object> parameterMap = infosToSendOrdrePrestationVr(sinisterPrestationDTO);

        try {
            InputStream jasperStream = this.getClass().getResourceAsStream(ordre_prestation_template);
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport report = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=report1.pdf");

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String uniqueFileName = "ordrePrestationVr_" + dtf.format(localDateTime) + ".pdf";
            response.addHeader("pdfname", properties.getCheminPdfReport() + "/" + uniqueFileName);
            
            Attachment ordrePrestationVrAttchment = new Attachment();
            ordrePrestationVrAttchment.setCreationDate(LocalDateTime.now());
            ordrePrestationVrAttchment.setEntityName("OrdrePrestationVr");
            ordrePrestationVrAttchment.setOriginal(false);
            ordrePrestationVrAttchment.setNote("OrdrePrestationVr");
            ordrePrestationVrAttchment.setOriginalName(uniqueFileName);
            ordrePrestationVrAttchment.setName(uniqueFileName);
            ordrePrestationVrAttchment.setEntityId(sinisterPrestationDTO.getId());
            ordrePrestationVrAttchment.setPath("OrdrePrestationVr");
            ordrePrestationVrAttchment.setLabel("OrdrePrestationVr");
            ordrePrestationVrAttchment = attachmentRepository.save(ordrePrestationVrAttchment);

            final OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    properties.getCheminFichier() + "/" + uniqueFileName);
            //sinisterPrestationDTO.setIsGenerated(true);
            sinisterPrestationService.save(sinisterPrestationDTO);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
