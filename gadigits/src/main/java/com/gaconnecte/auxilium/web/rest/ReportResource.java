package com.gaconnecte.auxilium.web.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.BonSortieService;
import com.gaconnecte.auxilium.service.ContactService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.DesignationUsageContratService;
import com.gaconnecte.auxilium.service.DevisService;
import com.gaconnecte.auxilium.service.DroolsService;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.LettreIDAService;
import com.gaconnecte.auxilium.service.LettreOuvertureService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.OrdrePrestationVrService;
import com.gaconnecte.auxilium.service.PersonneMoraleService;
import com.gaconnecte.auxilium.service.PersonnePhysiqueService;
import com.gaconnecte.auxilium.service.PieceJointeService;
import com.gaconnecte.auxilium.service.PrestationPECService;
import com.gaconnecte.auxilium.service.PrestationService;
import com.gaconnecte.auxilium.service.ReceiptService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.ContactDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DossierDTO;

import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.PersonneMoraleDTO;
import com.gaconnecte.auxilium.service.dto.PersonnePhysiqueDTO;
import com.gaconnecte.auxilium.service.dto.PieceJointeDTO;
import com.gaconnecte.auxilium.service.dto.PrestationDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of
 * authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no View Model and DTO, a lot less code, and an outer-join which
 * would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);
    private static final String ENTITY_NAME = "pieceJointe";

    @Autowired
    PieceJointeService pieceJointeService;
    @Autowired
    ContratAssuranceService contratAssuranceService;
    @Autowired
    DesignationUsageContratService designationUsageContratService;
    @Autowired
    PersonneMoraleService personneMoraleService;
    @Autowired
    PersonnePhysiqueService personnePhysiqueService;
    @Autowired
    LoggerService loggerService;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    AssureService assureService;
    // @Autowired
    // PrestationService prestationService;
    @Autowired
    PrestationPECService prestationPECService;
    @Autowired
    ReparateurService reparateurService;
    @Autowired
    ExpertService expertService;
    @Autowired
    VehiculeAssureService vehiculeAssureService;
    @Autowired
    ContactService contactService;
    @Autowired
    TiersService tiersService;
    @Autowired
    private DevisService devisService;
    @Autowired
    private DroolsService droolsService;
    @Autowired
    private BonSortieService bonDeSortieService;
    @Autowired
    private LettreIDAService lettreIDAService;
    @Autowired
    private LettreOuvertureService lettreOuvertureService;
    @Autowired
    SinisterService sinisterService;
    @Autowired
    private OrdrePrestationVrService ordrePrestationVrService;
    @Autowired
    ReceiptService receiptService;

    @PostMapping("/affectRepPDF")
    public @ResponseBody void affectRepPDF(HttpServletResponse response,
            @RequestBody PrestationPECDTO prestationPECDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        try {
            log.debug(" ReportResource----------------------------------->  affectRepPDF ");

            InputStream jasperStream = this.getClass().getResourceAsStream("/report/bonAffectation.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport report = JasperCompileManager.compileReport(design);
            Map<String, Object> parameterMap = new HashMap<>();

            parameterMap.put("logoGacPath", this.getClass().getResource("/report/Logo-GA.png"));

            ContratAssuranceDTO contratAssuranceDTO = contratAssuraceByPrestationPec(prestationPECDTO);
            /*
             * if (contratAssuranceDTO != null) { log.debug("  ------1111------>    ");
             * AssureDTO assureDTO =
             * assureService.findOne(contratAssuranceDTO.getAssureId());
             * 
             * if (assureDTO != null) { log.debug("  ------1122------>    ");
             * PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueService
             * .findOne(assureDTO.getPersonnePhyisqueId()); // info AssureVehiculeAssureDTO
             * if (personnePhysiqueDTO != null) { log.debug("  ------1123------>    ");
             * parameterMap.put("assureName", personnePhysiqueDTO.getNom() == null ? " " :
             * personnePhysiqueDTO.getNom() + " " + personnePhysiqueDTO.getPrenom() == null
             * ? " " : personnePhysiqueDTO.getPrenom()); // parameterMap.put("assureGsm",
             * personnePhysiqueDTO.getPremTelephone()); // } } if (contratAssuranceDTO !=
             * null) { VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService
             * .findOne(contratAssuranceDTO.getVehiculeId()); // info vehicule if
             * (vehiculeAssureDTO != null) { parameterMap.put("immatrVehicule",
             * vehiculeAssureDTO.getImmatriculationVehicule()); //
             * parameterMap.put("dateCirculation", vehiculeAssureDTO.getDatePCirculation());
             * // parameterMap.put("chassisVehicule", vehiculeAssureDTO.getNumeroChassis());
             * // parameterMap.put("marqueVehicule",
             * vehiculeAssureService.findMarqueByVehicule(vehiculeAssureDTO.getId())); // }
             * } }
             */

            parameterMap.put("pointChoc", "");

            if (prestationPECDTO.getReparateurId() != null) {
                ReparateurDTO reparateurDTO = reparateurService.findOne(prestationPECDTO.getReparateurId());
                if (reparateurDTO != null) {
                    PersonneMoraleDTO personneMoraleDTO = personneMoraleService.findOne(null);
                    if (personneMoraleDTO != null) {
                        parameterMap.put("raisonSociale", personneMoraleDTO.getRaisonSociale());
                        parameterMap.put("adresseRep", personneMoraleDTO.getAdresse());
                        PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueByPersonneMorale(personneMoraleDTO);
                        // info Reparateur
                        if (personnePhysiqueDTO != null) {

                            parameterMap.put("contact", personnePhysiqueDTO.getNom() == null ? " "
                                    : personnePhysiqueDTO.getNom() + " " + personnePhysiqueDTO.getPrenom() == null ? " "
                                            : personnePhysiqueDTO.getPrenom());
                            parameterMap.put("gsmRep", personnePhysiqueDTO.getPremTelephone());
                        }
                    }
                }
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=report1.pdf");
            LocalDateTime localDateTime = LocalDateTime.now();
            String uniqueFileName = "bonAffectation_" + dtf.format(localDateTime) + ".pdf";
            response.addHeader("pdfname",
                    pieceJointeService.affectCheminPdfReport().getcontent() + "/" + uniqueFileName);

            final OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    pieceJointeService.affectChemin().getcontent() + "/" + uniqueFileName);

        } catch (Exception e) {
            log.error("    Exception catched .................affectReparateurPDF");
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
        }

    }

    @PostMapping("/affectExpertPDF")
    public @ResponseBody void affectExpertPDF(HttpServletResponse response,
            @RequestBody PrestationPECDTO prestationPECDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        PersonnePhysiqueDTO personnePhysiqueDTO = new PersonnePhysiqueDTO();
        String personnePhysiqueNom;
        try {
            Map<String, Object> parameterMap = new HashMap<>();
            ContratAssuranceDTO contratAssuranceDTO = contratAssuraceByPrestationPec(prestationPECDTO);

            PrestationDTO prestationDto = null;
            /*
             * if (prestationDto != null) { DossierDTO dossierDTO =
             * dossierService.findOne(prestationDto.getDossierId()); if (dossierDTO != null)
             * { parameterMap.put("headerRefGA", dossierDTO.getReference()); } }
             */
            if (contratAssuranceDTO != null) {
                parameterMap.put("immatrVehicule", contratAssuranceDTO.getVehiculeImmatriculationVehicule());
                parameterMap.put("capitalAssure", contratAssuranceDTO.getDcCapital());
                parameterMap.put("franchisse", contratAssuranceDTO.getFranchiseTypeDcCapital());
                parameterMap.put("headerCieAssurance", contratAssuranceDTO.getAgenceNom());

                // AssureDTO assureDTO =
                // assureService.findOne(contratAssuranceDTO.getAssureId());

                /*
                 * if (assureDTO != null) { parameterMap.put("assureName",
                 * assureDTO.getPersonnePhyisqueNom()); personnePhysiqueDTO =
                 * personnePhysiqueService .findOne(assureDTO.getPersonnePhyisqueId()); // info
                 * AssureVehiculeAssureDTO if (personnePhysiqueDTO != null) {
                 * personnePhysiqueNom = personnePhysiqueDTO.getNom() != null ?
                 * personnePhysiqueDTO.getNom() : " " + " " + personnePhysiqueDTO.getPrenom() !=
                 * null ? personnePhysiqueDTO.getPrenom() : " "; parameterMap.put("assureName",
                 * personnePhysiqueNom); parameterMap.put("assureAdr",
                 * personnePhysiqueDTO.getAdresse()); parameterMap.put("assureGsm",
                 * personnePhysiqueDTO.getPremTelephone()); } }
                 */
                if (contratAssuranceDTO != null) {
                    VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService
                            .findOne(contratAssuranceDTO.getVehiculeId());
                    // info vehicule log.debug(" affectExpertPDF------------> ");
                    if (vehiculeAssureDTO != null) {
                        parameterMap.put("immatrVehicule", vehiculeAssureDTO.getImmatriculationVehicule());
                        parameterMap.put("dateCirculation", vehiculeAssureDTO.getDatePCirculation());
                        parameterMap.put("chassisVehicule", vehiculeAssureDTO.getNumeroChassis());
                        parameterMap.put("energie", vehiculeAssureDTO.getEnergieLibelle());
                    }
                }
            }
            parameterMap.put("modeGestion", prestationPECDTO.getModeLibelle());
            parameterMap.put("natureGarentie", prestationPECDTO.getGarantieLibelle());
            parameterMap.put("headerRefCie", prestationPECDTO.getReferenceSinistre());

            if (prestationPECDTO.getReparateurId() != null) {
                ReparateurDTO reparateurDTO = reparateurService.findOne(prestationPECDTO.getReparateurId());
                if (reparateurDTO != null) {
                    PersonneMoraleDTO personneMoraleDTO = personneMoraleService.findOne(null);
                    if (personneMoraleDTO != null) {
                        parameterMap.put("garage", personneMoraleDTO.getRaisonSociale());

                        personnePhysiqueDTO = personnePhysiqueByPersonneMorale(personneMoraleDTO);
                        // info Reparateur
                        if (personnePhysiqueDTO != null) {
                            personnePhysiqueNom = personnePhysiqueDTO.getNom() != null ? personnePhysiqueDTO.getNom()
                                    : " " + " " + personnePhysiqueDTO.getPrenom() != null
                                            ? personnePhysiqueDTO.getPrenom()
                                            : " ";
                            parameterMap.put("reparateurName", personnePhysiqueNom);
                            parameterMap.put("adrgarage", personnePhysiqueDTO.getAdresse());
                            parameterMap.put("tel", personnePhysiqueDTO.getDeuxTelephone());
                            parameterMap.put("fax", personnePhysiqueDTO.getFax());
                            /*-*/
                            parameterMap.put("gsm", personnePhysiqueDTO.getPremTelephone());
                            parameterMap.put("email", personnePhysiqueDTO.getPremMail());
                        }

                    }
                }
            }

            // info expert
            if (prestationPECDTO.getExpertId() != null) {

                ExpertDTO expertDTO = expertService.findOne(prestationPECDTO.getExpertId());

                if (expertDTO != null) {

                    personnePhysiqueDTO = personnePhysiqueByPersonneMorale(null);
                    if (personnePhysiqueDTO != null) {
                        personnePhysiqueNom = personnePhysiqueDTO.getNom() != null ? personnePhysiqueDTO.getNom()
                                : " " + " " + personnePhysiqueDTO.getPrenom() != null ? personnePhysiqueDTO.getPrenom()
                                        : " ";
                        parameterMap.put("headerMr", personnePhysiqueNom);
                        parameterMap.put("headerAdresse", personnePhysiqueDTO.getAdresse());
                        parameterMap.put("headerTel", personnePhysiqueDTO.getPremTelephone());
                    }
                }
            }
            parameterMap.put("logoGacPath", this.getClass().getResource("/report/Logo-GA.png"));
            InputStream jasperStream = this.getClass().getResourceAsStream("/report/ordreDeMissions.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=report1.pdf");
            LocalDateTime localDateTime = LocalDateTime.now();
            String uniqueFileName = "ordreDeMission_" + dtf.format(localDateTime) + ".pdf";
            response.addHeader("pdfname",
                    pieceJointeService.affectCheminPdfReport().getcontent() + "/" + uniqueFileName);

            final OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    pieceJointeService.affectChemin().getcontent() + "/" + uniqueFileName);

        } catch (Exception e) {
            log.error("    Exception catched .................affectExpertPDF");
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
        }

    }

    public ContratAssuranceDTO contratAssuraceByPrestationPec(PrestationPECDTO prestationPECDTO) {
        ContratAssuranceDTO contratAssuranceDTO = new ContratAssuranceDTO();
        if (prestationPECDTO != null) {
            PrestationDTO prestationDto = null;
            if (prestationDto != null) {
                // DossierDTO dossierDTO = dossierService.findOne(prestationDto.getDossierId());
                /*
                 * if (dossierDTO != null) { contratAssuranceDTO =
                 * contratAssuranceService.findOneByImmatriculation(dossierDTO.
                 * getVehiculeImmatriculationVehicule()); if (contratAssuranceDTO != null) {
                 * return contratAssuranceDTO; } }
                 */
            }
        }
        return null;
    }

    public PersonnePhysiqueDTO personnePhysiqueByPersonneMorale(PersonneMoraleDTO personneMoraleDTO) {
        PersonnePhysiqueDTO personnePhysiqueDTO = new PersonnePhysiqueDTO();
        if (personneMoraleDTO != null) {
            ContactDTO contactDTO = contactService.findMainContact(personneMoraleDTO.getId());
            if (contactDTO != null) {
                personnePhysiqueDTO = personnePhysiqueService.findOne(contactDTO.getPersonnePhysiqueId());
                if (personnePhysiqueDTO != null) {
                    return personnePhysiqueDTO;
                }
            }
        }
        return null;
    }

    /**
     *
     * generateLettreIDA
     *
     * @param prestationPECDTO
     */
    @PostMapping("/generatelettreIDA")
    public @ResponseBody void lettreIDARessourcePdf(HttpServletResponse response,
            @RequestBody SinisterPecDTO sinisterPecDTO) {

        lettreIDAService.generateLetterIDA(response, sinisterPecDTO);

    }

    /**
     *
     * generateLettreOuverture
     *
     * @param prestationPECDTO
     */
    @PostMapping("/generatelettreReouverture")
    public @ResponseBody void lettreReouvertureRessourcePdf(HttpServletResponse response,
            @RequestBody SinisterPecDTO sinisterPecDTO) {

        lettreOuvertureService.generateLetterOuverture(response, sinisterPecDTO);

    }
    
    /**
    *
    * generateLettreIDA
    *
    * @param prestationPECDTO
    */
   @PostMapping("/generateOrdrePrestationVr/{id}")
   public @ResponseBody void ordrePrestationVrRessourcePdf(HttpServletResponse response,
		   @PathVariable  Long id) {
	   

	   ordrePrestationVrService.generateOrdrePrestationVr(response, id);

   }


    /**
     *
     * generateLettreIDA
     *
     * @param prestationPECDTO
     */
    @PostMapping("/generatequotation")
    public @ResponseBody void quotationRessourcePdf(HttpServletResponse response,
            @RequestBody QuotationDTO quotationDTO) {

        lettreIDAService.generateQuotationPdf(response, quotationDTO, quotationDTO.getSinPecId());

    }

    /**
     * PEC Global Devis By Drools Service
     */
    /*
     * private FactDevis getGlobalDevisByDroolsService(AccordPriseChargeDTO
     * accordPriseChargeDTO) { Long idPrestation =
     * accordPriseChargeDTO.getPrestationPEC().getId(); Long modeGestion =
     * accordPriseChargeDTO.getPrestationPEC().getModeId(); Integer responsabilite;
     * if ((null == accordPriseChargeDTO.getPrestationPEC().getBareme())) {
     * responsabilite = 0; } else { responsabilite =
     * accordPriseChargeDTO.getPrestationPEC().getBareme().getResponsabiliteX(); }
     * return droolsService.getDataFromDevis(idPrestation, responsabilite,
     * modeGestion); }
     */

    @PostMapping("/bonSortiePdf")
    public @ResponseBody void bonSortiePdf(HttpServletResponse response, @RequestBody SinisterPecDTO sinisterPecDTO) {

        receiptService.generateBonSortie(response, sinisterPecDTO);

    }

    @PostMapping("/ordreMissionExpertPdf")
    public @ResponseBody void ordreMissionExpertPdf(HttpServletResponse response,
            @RequestBody SinisterPecDTO sinisterPecDTO) {
        log.debug("ReportResource-----------------------------------> ordreMissionExpertPdf");
        receiptService.generateOrdreMissionExpert(response, sinisterPecDTO);
    }

    @PostMapping("/pieceJointePdf")
    public @ResponseBody void pieceJointePdf(HttpServletResponse response, @RequestBody PieceJointeDTO pieceJointeDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline; filename=piece1");
        LocalDateTime localDateTime = LocalDateTime.now();

        String uniqueFileName = "pieceJointe_" + dtf.format(localDateTime);
        // response.addHeader("pdfname",
        // "C:/Users/salwa.chaabi/Desktop/PDFTest/bonSortie_19072018143244");
        // response.addHeader("pdfname",pieceJointeService.affectCheminPdfReport().getcontent()+"/"+uniqueFileName);
        if (pieceJointeDTO.getId() != null) {

            // response.addHeader("pdfname","http://localhost:9090/examples/"+pieceJointeDTO.getLibelle());
            response.addHeader("pdfname",
                    pieceJointeService.affectCheminPdfReport().getcontent() + "/" + pieceJointeDTO.getLibelle());
        }

    }

    Map<String, Object> infosToSendIDAgenerateRemorqueurBordereauReport(RefRemorqueurDTO refRemorqueurDTO) {
        Map<String, Object> parameterMap = new HashMap();
        String assurePrenomNom;

        if (refRemorqueurDTO != null) {
            parameterMap.put("raison sociale", refRemorqueurDTO.getSocieteRaisonSociale());
            parameterMap.put("destination", refRemorqueurDTO.getLibelleGouvernorat());
            parameterMap.put("adresse", refRemorqueurDTO.getAdresse());
            parameterMap.put("matricule Fiscale", refRemorqueurDTO.getMatriculeFiscale());

        }
        return parameterMap;

    }

    @PostMapping("/generateRemorqueurBordereau")
    public @ResponseBody void remorqueurBordereauRessourcePdf(HttpServletResponse response,
            @RequestBody RefRemorqueurDTO refRemorqueurDTO) {

        Map<String, Object> parameterMap = infosToSendIDAgenerateRemorqueurBordereauReport(refRemorqueurDTO);
        PieceJointeDTO pieceJointeDTO = new PieceJointeDTO();

        try {
            InputStream jasperStream = this.getClass().getResourceAsStream("/report/remorqueurBordereau.jrxml");
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport report = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=Bordereau1.pdf");

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String uniqueFileName = "remorqueurBordereau_" + dtf.format(localDateTime) + ".pdf";
            response.addHeader("pdfname",
                    pieceJointeService.affectCheminPdfReport().getcontent() + "/" + uniqueFileName);

            final OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    pieceJointeService.affectChemin().getcontent() + "/" + uniqueFileName);

            pieceJointeDTO.setPrestationId(Long.valueOf(refRemorqueurDTO.getId()));
            pieceJointeDTO.setLibelle("Lettre Ouverture");
            pieceJointeDTO.setChemin(pieceJointeService.affectChemin().getcontent() + "/" + uniqueFileName);
            pieceJointeDTO.setDateImport(LocalDate.now());
            pieceJointeDTO.setIsOriginale(false);
            pieceJointeDTO.setTypeId(Long.valueOf(999));

        } catch (Exception e) {
            loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
        }
        pieceJointeService.save(pieceJointeDTO);
    }

}
