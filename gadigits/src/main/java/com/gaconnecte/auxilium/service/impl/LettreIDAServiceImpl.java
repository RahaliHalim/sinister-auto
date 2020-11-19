package com.gaconnecte.auxilium.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.enumeration.NaturePiece;
import com.gaconnecte.auxilium.domain.enumeration.ObservationRepairer;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.GovernorateService;
import com.gaconnecte.auxilium.service.LettreIDAService;
import com.gaconnecte.auxilium.service.LettreOuvertureService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.dto.AssureDTO;
import com.gaconnecte.auxilium.service.dto.ContratAssuranceDTO;
import com.gaconnecte.auxilium.service.dto.DetailsPiecesDTO;
import com.gaconnecte.auxilium.service.dto.DossierDTO;
import com.gaconnecte.auxilium.service.dto.ExpertDTO;
import com.gaconnecte.auxilium.service.dto.GovernorateDTO;
import com.gaconnecte.auxilium.service.dto.LettreIDADTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import com.gaconnecte.auxilium.service.dto.QuoteDTO;
import com.gaconnecte.auxilium.service.dto.ReparateurDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.TiersDTO;
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
public class LettreIDAServiceImpl implements LettreIDAService {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private final String lettre_ouverture_template = "/report/lettreOuverture.jrxml";

    private final String lettre_ida_template = "/report/lettreIDA.jrxml";

    private static final String logo_path = "/report/LogoSlogon.png";
    private static final String logo_path1 = "/report/LogoSlogon1.png";
    private static final String logo_path2 = "/report/LogoSlogon2.png";
    private static final String logo_path3 = "/report/LogoSlogon3.png";
    private static final String logo_path4 = "/report/LogoSlogon4.png";

    private final String quote_main_template = "/report/quotation.jrxml";
    private final String quote_ingredient_template = "/report/quotationIngredient.jrxml";
    private final String quote_fourniture_template = "/report/quotationFourniture.jrxml";
    private final String quote_piece_template = "/report/quotationPieceRechange.jrxml";
    private final String quote_avis_technique_template = "/report/avisTechnique.jrxml";

    // private static final String alliance_path = "/report/alliance.png";
    private static final String amana_path = "/report/amana.png";
    private static final String ami_path = "/report/ami.png";
    private static final String bh_path = "/report/bhAssurance.png";
    private static final String biat_path = "/report/biat.png";
    private static final String carte_path = "/report/carte.png";
    private static final String gat_path = "/report/gat.png";
    private static final String lloyd_path = "/report/lloyd.png";
    // private static final String peugeot_path = "/report/peugeot.png";

    private final ApplicationProperties properties;

    @Autowired
    ContratAssuranceService contratAssuranceService;

    @Autowired
    AssureService assureService;
    @Autowired
    TiersService tiersService;
    @Autowired
    PartnerService partnerService;
    @Autowired
    SinisterService sinisterService;
    @Autowired
    VehiculeAssureService vehiculeAssureService;
    @Autowired
    LettreOuvertureService lettreOuvertureService;
    @Autowired
    GovernorateService governorateService;
    @Autowired
    SinisterPecService sinisterPecService;
    @Autowired
    ExpertService expertService;
    @Autowired
    ReparateurService reparateurService;
    @Autowired
	AttachmentRepository attachmentRepository;

    @Autowired
    public LettreIDAServiceImpl(ApplicationProperties properties) {
        this.properties = properties;

    }

    /**
     * infos to generate letter IDA
     */
    @Override
    public Map<String, Object> infosToSendLetterIDAReport(SinisterPecDTO sinisterPecDTO) {

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Map<String, Object> lettreIDAMap = new HashMap<String, Object>();
        LettreIDADTO lettreIDADTO = new LettreIDADTO();
        if (sinisterPecDTO != null) {
            lettreIDADTO.setCasDeBareme(sinisterPecDTO.getCodeBareme());
            SinisterDTO sinisterDTO = new SinisterDTO();
            sinisterDTO = sinisterService.findOne(sinisterPecDTO.getSinisterId());

            if (sinisterDTO != null) {
                lettreIDADTO.setDateAccident(dt.format(sinisterDTO.getIncidentDate()));
                lettreIDADTO.setAdresseSinistre(sinisterDTO.getGovernorateLabel());
                lettreIDADTO.setReferenceSinister(sinisterPecDTO.getCompanyReference() + "/GA");
                lettreIDADTO.setImmatriculation(sinisterDTO.getVehicleRegistration());

                GovernorateDTO governorateDTO = governorateService.findOne(sinisterPecDTO.getGovernorateId());
                if (governorateDTO != null) {
                    lettreIDADTO.setAdresseSinistre(governorateDTO.getLabel());
                }

                Set<TiersDTO> tiersDTO = new HashSet<>();
                TiersDTO tierDTO = new TiersDTO();
                tiersDTO = sinisterPecDTO.getTiers();
                for (TiersDTO tr : tiersDTO) {
                    if (tr.getResponsible() != null) {
                        if (tr.getResponsible()) {
                            tierDTO = tr;
                            if (tierDTO != null) {
                                lettreIDADTO.setTiersName(tierDTO.getFullName());
                                lettreIDADTO.setTiersImmatriculation(tierDTO.getImmatriculation());
                                lettreIDADTO.setContratAssuranceNum(tierDTO.getNumeroContrat());
                                PartnerDTO partnerDTO = partnerService.findOne(tierDTO.getCompagnieId());
                                if (partnerDTO != null) {
                                    lettreIDADTO.setAssuranceTiersName(partnerDTO.getCompanyName());
                                    lettreIDADTO.setAssuranceTiersAdresse(partnerDTO.getAddress());
                                }
                            }
                        }
                    }
                }

                ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOne(sinisterDTO.getContractId());
                if (contratAssuranceDTO != null) {
                    // lettreIDADTO.setContratAssuranceNum(contratAssuranceDTO.getNumeroContrat());
                    PartnerDTO partnerDTO = partnerService.findOne(contratAssuranceDTO.getClientId());
                    if (partnerDTO != null) {
                        lettreIDADTO.setAssuranceAssureName(partnerDTO.getCompanyName());
                        if (partnerDTO.getId().equals(1L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(bh_path));
                        }
                        if (partnerDTO.getId().equals(2L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(gat_path));
                        }
                        if (partnerDTO.getId().equals(3L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(carte_path));
                        }
                        if (partnerDTO.getId().equals(4L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(ami_path));
                        }
                        if (partnerDTO.getId().equals(5L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(biat_path));
                        }
                        if (partnerDTO.getId().equals(6L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(lloyd_path));
                        }
                        if (partnerDTO.getId().equals(7L)) {
                            lettreIDAMap.put("logoCampanyPath", getClass().getResourceAsStream(amana_path));
                        }
                    }
                    VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOne(sinisterDTO.getVehicleId());
                    AssureDTO assureDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
                    if (assureDTO != null) {
                        if (assureDTO.getCompany()) {
                            lettreIDADTO.setAssureFullName(assureDTO.getRaisonSociale());
                        } else {
                            lettreIDADTO.setAssureFullName(assureDTO.getNom() + " " + assureDTO.getPrenom());
                        }
                    }
                    // lettreIDAMap.put("logoCampanyPath",
                    // getClass().getResourceAsStream(logo_path));
                    lettreIDAMap.put("lettreIDA", lettreIDADTO);

                }
            }

        }
        return lettreIDAMap;
    }

    /**
     * generate letter IDA
     */

    public void generateLetterIDA(HttpServletResponse response, SinisterPecDTO sinisterPecDTO) {

        Map<String, Object> parameterMap = infosToSendLetterIDAReport(sinisterPecDTO);

        try {
            InputStream jasperStream = this.getClass().getResourceAsStream(lettre_ida_template);
            JasperDesign design = JRXmlLoader.load(jasperStream);
            JasperReport report = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=report1.pdf");

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String uniqueFileName = "lettreIDA_" + dtf.format(localDateTime) + ".pdf";
            response.addHeader("pdfname", properties.getCheminPdfReport() + "/" + uniqueFileName);
            
            Attachment lettreIdaAttchment = new Attachment();
            lettreIdaAttchment.setCreationDate(LocalDateTime.now());
            lettreIdaAttchment.setEntityName("LettreIda");
            lettreIdaAttchment.setOriginal(false);
            lettreIdaAttchment.setNote("LettreIda");
            lettreIdaAttchment.setOriginalName(uniqueFileName);
            lettreIdaAttchment.setName(uniqueFileName);
            lettreIdaAttchment.setEntityId(sinisterPecDTO.getId());
            lettreIdaAttchment.setPath("LettreIda");
            lettreIdaAttchment.setLabel("LettreIda");
            lettreIdaAttchment = attachmentRepository.save(lettreIdaAttchment);

            final OutputStream outputStream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    properties.getCheminFichier() + "/" + uniqueFileName);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void generateQuotationPdf(HttpServletResponse response, QuotationDTO quotationPdf, Long pecId) {

        // Map<String, Object> parameterMap =
        // infosToSendLetterIDAReport(sinisterPecDTO);
        SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(pecId);
        List<DetailsPiecesDTO> listesMainsO = quotationPdf.getListMainO();

        List<QuoteDTO> listItemsMain = new ArrayList<QuoteDTO>();

        // traitement Main d'oeuvre
        System.out.println("testLengthMainO " + listesMainsO.size());
        for (DetailsPiecesDTO dp : listesMainsO) {
            QuoteDTO quote = new QuoteDTO();

            if (dp.getObservationExpert() != null) {
                if (dp.getObservationExpert().equals(1L)) {
                    quote.setObservationExpert("Accordé");
                } else if (dp.getObservationExpert().equals(2L)) {
                    quote.setObservationExpert("Non Accordé");
                } else if (dp.getObservationExpert().equals(3L)) {
                    quote.setObservationExpert("A Réparer");
                } else if (dp.getObservationExpert().equals(4L)) {
                    quote.setObservationExpert("A Remplacer");
                } else if (dp.getObservationExpert().equals(5L)) {
                    quote.setObservationExpert("Accordé avec Modification");
                } else {
                    quote.setObservationExpert(" ");
                }
            } else {
                quote.setObservationExpert(" ");
            }

            if (dp.getNombreMOEstime() != null) {
                quote.setNombreMOEstime(dp.getNombreMOEstime());
            } else {
                quote.setNombreMOEstime(0F);
            }

            if (dp.getDesignation() != null) {
                quote.setDesignation(dp.getDesignation());
            } else {
                quote.setDesignation(" ");
            }

            if (dp.getTypeInterventionLibelle() != null) {
                quote.setTypeIntervention(dp.getTypeInterventionLibelle());
            } else {
                quote.setTypeIntervention(" ");
            }

            if (dp.getPrixUnit() != null) {
                quote.setPrixUnit(dp.getPrixUnit());
            } else {
                quote.setPrixUnit(0D);
            }

            if (dp.getNombreHeures() != null) {
                quote.setNombreHeures(dp.getNombreHeures());
            } else {
                quote.setNombreHeures(0F);
            }

            /*if (dp.getTotalHtF() != null) {
                quote.setTotalHt(dp.getTotalHtF());
            } else {
                quote.setTotalHt(0F);
            }*/

            /*if (dp.getDiscount() != null) {
                quote.setDiscount(dp.getDiscount());
            } else {
                quote.setDiscount(0F);
            }*/

            /*if (dp.getAmountDiscount() != null) {
                quote.setAmountDiscount(dp.getAmountDiscount());
            } else {
                quote.setAmountDiscount(0F);
            }*/

            /*if (dp.getAmountAfterDiscount() != null) {
                quote.setAmountAfterDiscount(dp.getAmountAfterDiscount());
            } else {
                quote.setAmountAfterDiscount(0F);
            }*/

            if (dp.getTva() != null) {
                quote.setTva(dp.getTva());
            } else {
                quote.setTva(0F);
            }

            /*if (dp.getAmountVat() != null) {
                quote.setAmountVat(dp.getAmountVat());
            } else {
                quote.setAmountVat(0F);
            }*/

            if (dp.getTotalTtcF() != null) {
                quote.setTotalTtc(dp.getTotalTtcF());
            } else {
                quote.setTotalTtc(0F);
            }

            listItemsMain.add(quote);
        }

        // traitement Ingrédient de peinture

        List<DetailsPiecesDTO> ingredients = quotationPdf.getListIngredients();

        List<QuoteDTO> listItemsIngr = new ArrayList<QuoteDTO>();

        for (DetailsPiecesDTO dp : ingredients) {
            QuoteDTO quote = new QuoteDTO();

            if (dp.getObservationExpert() != null) {
                if (dp.getObservationExpert().equals(1L)) {
                    quote.setObservationExpert("Accordé");
                } else if (dp.getObservationExpert().equals(2L)) {
                    quote.setObservationExpert("Non Accordé");
                } else if (dp.getObservationExpert().equals(3L)) {
                    quote.setObservationExpert("A Réparer");
                } else if (dp.getObservationExpert().equals(4L)) {
                    quote.setObservationExpert("A Remplacer");
                } else if (dp.getObservationExpert().equals(5L)) {
                    quote.setObservationExpert("Accordé avec Modification");
                } else {
                    quote.setObservationExpert(" ");
                }
            } else {
                quote.setObservationExpert(" ");
            }

            if (dp.getDesignation() != null) {
                quote.setDesignation(dp.getDesignation());
            } else {
                quote.setDesignation(" ");
            }

            if (dp.getTypeInterventionLibelle() != null) {
                quote.setTypeIntervention(dp.getTypeInterventionLibelle());
            } else {
                quote.setTypeIntervention(" ");
            }

            if (dp.getPrixUnit() != null) {
                quote.setPrixUnit(dp.getPrixUnit());
            } else {
                quote.setPrixUnit(0D);
            }

            if (dp.getQuantite() != null) {
                quote.setNombreHeures(dp.getQuantite());
            } else {
                quote.setNombreHeures(0F);
            }

            /*if (dp.getTotalHtF() != null) {
                quote.setTotalHt(dp.getTotalHtF());
            } else {
                quote.setTotalHt(0F);
            }*/

            /*if (dp.getDiscount() != null) {
                quote.setDiscount(dp.getDiscount());
            } else {
                quote.setDiscount(0F);
            }*/

            /*if (dp.getAmountDiscount() != null) {
                quote.setAmountDiscount(dp.getAmountDiscount());
            } else {
                quote.setAmountDiscount(0F);
            }*/

            /*if (dp.getAmountAfterDiscount() != null) {
                quote.setAmountAfterDiscount(dp.getAmountAfterDiscount());
            } else {
                quote.setAmountAfterDiscount(0F);
            }*/

            if (dp.getTva() != null) {
                quote.setTva(dp.getTva());
            } else {
                quote.setTva(0F);
            }

            /*if (dp.getAmountVat() != null) {
                quote.setAmountVat(dp.getAmountVat());
            } else {
                quote.setAmountVat(0F);
            }*/

            if (dp.getTotalTtcF() != null) {
                quote.setTotalTtc(dp.getTotalTtcF());
            } else {
                quote.setTotalTtc(0F);
            }

            listItemsIngr.add(quote);
        }

        // traitement Ingrédient de fourniture

        List<DetailsPiecesDTO> fourniture = quotationPdf.getListFourniture();

        List<QuoteDTO> listItemsFourniture = new ArrayList<QuoteDTO>();

        for (DetailsPiecesDTO dp : fourniture) {
            QuoteDTO quote = new QuoteDTO();

            if (dp.getObservationExpert() != null) {
                if (dp.getObservationExpert().equals(1L)) {
                    quote.setObservationExpert("Accordé");
                } else if (dp.getObservationExpert().equals(2L)) {
                    quote.setObservationExpert("Non Accordé");
                } else if (dp.getObservationExpert().equals(3L)) {
                    quote.setObservationExpert("A Réparer");
                } else if (dp.getObservationExpert().equals(4L)) {
                    quote.setObservationExpert("A Remplacer");
                } else if (dp.getObservationExpert().equals(5L)) {
                    quote.setObservationExpert("Accordé avec Modification");
                } else {
                    quote.setObservationExpert(" ");
                }
            } else {
                quote.setObservationExpert(" ");
            }

            if (dp.getDesignation() != null) {
                quote.setDesignation(dp.getDesignation());
            } else {
                quote.setDesignation(" ");
            }

            if (dp.getTypeInterventionLibelle() != null) {
                quote.setTypeIntervention(dp.getTypeInterventionLibelle());
            } else {
                quote.setTypeIntervention(" ");
            }

            if (dp.getPrixUnit() != null) {
                quote.setPrixUnit(dp.getPrixUnit());
            } else {
                quote.setPrixUnit(0D);
            }

            if (dp.getQuantite() != null) {
                quote.setNombreHeures(dp.getQuantite());
            } else {
                quote.setNombreHeures(0F);
            }

            /*if (dp.getTotalHtF() != null) {
                quote.setTotalHt(dp.getTotalHtF());
            } else {
                quote.setTotalHt(0F);
            }*/

            /*if (dp.getDiscount() != null) {
                quote.setDiscount(dp.getDiscount());
            } else {
                quote.setDiscount(0F);
            }*/

            /*if (dp.getAmountDiscount() != null) {
                quote.setAmountDiscount(dp.getAmountDiscount());
            } else {
                quote.setAmountDiscount(0F);
            }*/

            /*if (dp.getAmountAfterDiscount() != null) {
                quote.setAmountAfterDiscount(dp.getAmountAfterDiscount());
            } else {
                quote.setAmountAfterDiscount(0F);
            }*/

            if (dp.getTva() != null) {
                quote.setTva(dp.getTva());
            } else {
                quote.setTva(0F);
            }

            /*if (dp.getAmountVat() != null) {
                quote.setAmountVat(dp.getAmountVat());
            } else {
                quote.setAmountVat(0F);
            }*/

            if (dp.getTotalTtcF() != null) {
                quote.setTotalTtc(dp.getTotalTtcF());
            } else {
                quote.setTotalTtc(0F);
            }

            listItemsFourniture.add(quote);
        }

        // traitement Pièces

        List<DetailsPiecesDTO> pieces = quotationPdf.getListItemsPieces();

        List<QuoteDTO> listItemsPieces = new ArrayList<QuoteDTO>();

        for (DetailsPiecesDTO dp : pieces) {
            QuoteDTO quote = new QuoteDTO();

            if (dp.getObservationExpert() != null) {
                if (dp.getObservationExpert().equals(1L)) {
                    quote.setObservationExpert("Accordé");
                } else if (dp.getObservationExpert().equals(2L)) {
                    quote.setObservationExpert("Non Accordé");
                } else if (dp.getObservationExpert().equals(3L)) {
                    quote.setObservationExpert("A Réparer");
                } else if (dp.getObservationExpert().equals(4L)) {
                    quote.setObservationExpert("A Remplacer");
                } else if (dp.getObservationExpert().equals(5L)) {
                    quote.setObservationExpert("Accordé avec Modification");
                } else {
                    quote.setObservationExpert(" ");
                }
            } else {
                quote.setObservationExpert(" ");
            }

            if (dp.getVetuste() != null) {
                quote.setVetuste(dp.getVetuste());
            } else {
                quote.setVetuste(0F);
            }

            /*if (dp.getTTCVetuste() != null) {
                quote.setTTCVetuste(String.valueOf(dp.getTTCVetuste()));
            } else {
                quote.setTTCVetuste(" ");
            }*/

            if (dp.getObservationRepairer() != null) {
                if (dp.getObservationRepairer().equals(ObservationRepairer.PIECE_DISPONIBLE)) {
                    quote.setObservationRepairer("Pièce disponible");
                } else if (dp.getObservationRepairer().equals(ObservationRepairer.COMMANDE_FERME)) {
                    quote.setObservationRepairer("Commande ferme");
                } else if (dp.getObservationRepairer().equals(ObservationRepairer.PIECE_NON_DISPONIBLE)) {
                    quote.setObservationRepairer("Pièce non disponible");
                } else {
                    quote.setObservationRepairer(" ");
                }

            } else {
                quote.setObservationRepairer(" ");
            }

            if (dp.getDesignationReference() != null) {
                quote.setDesignationReference(dp.getDesignationReference());
            } else {
                quote.setDesignationReference(" ");
            }

            if (dp.getDesignation() != null) {
                quote.setDesignation(dp.getDesignation());
            } else {
                quote.setDesignation(" ");
            }

            if (dp.getPrixUnit() != null) {
                quote.setPrixUnit(dp.getPrixUnit());
            } else {
                quote.setPrixUnit(0D);
            }

            if (dp.getNaturePiece() != null) {
                if (dp.getNaturePiece().equals(NaturePiece.CASSE)) {
                    quote.setNaturePiece("Casse");
                } else if (dp.getNaturePiece().equals(NaturePiece.ORIGINE)) {
                    quote.setNaturePiece("Origine");
                } else if (dp.getNaturePiece().equals(NaturePiece.GENERIQUE)) {
                    quote.setNaturePiece("Générique");
                } else {
                    quote.setNaturePiece(" ");
                }
            } else {
                quote.setNaturePiece(" ");
            }

            if (dp.getQuantite() != null) {
                quote.setQuantite(dp.getQuantite());
            } else {
                quote.setQuantite(0F);
            }

            /*if (dp.getTotalHtF() != null) {
                quote.setTotalHt(dp.getTotalHtF());
            } else {
                quote.setTotalHt(0F);
            }*/

            /*if (dp.getDiscount() != null) {
                quote.setDiscount(dp.getDiscount());
            } else {
                quote.setDiscount(0F);
            }*/

            /*if (dp.getAmountDiscount() != null) {
                quote.setAmountDiscount(dp.getAmountDiscount());
            } else {
                quote.setAmountDiscount(0F);
            }*/

            /*if (dp.getAmountAfterDiscount() != null) {
                quote.setAmountAfterDiscount(dp.getAmountAfterDiscount());
            } else {
                quote.setAmountAfterDiscount(0F);
            }*/

            if (dp.getTva() != null) {
                quote.setTva(dp.getTva());
            } else {
                quote.setTva(0F);
            }

            /*if (dp.getAmountVat() != null) {
                quote.setAmountVat(dp.getAmountVat());
            } else {
                quote.setAmountVat(0F);
            }*/

            if (dp.getTotalTtcF() != null) {
                quote.setTotalTtc(dp.getTotalTtcF());
            } else {
                quote.setTotalTtc(0F);
            }

            listItemsPieces.add(quote);
        }

        // traitement Main d'oeuvre
        JRBeanCollectionDataSource itemsJRBeanMain = new JRBeanCollectionDataSource(listItemsMain);

        Map<String, Object> parametersMain = new HashMap<String, Object>();
        parametersMain.put("quotePiece", itemsJRBeanMain);

        // traitement Ingrédient de peinture
        JRBeanCollectionDataSource itemsJRBeanIng = new JRBeanCollectionDataSource(listItemsIngr);

        Map<String, Object> parametersIng = new HashMap<String, Object>();
        parametersIng.put("quotePiece", itemsJRBeanIng);

        // traitement Ingrédient de fourniture

        JRBeanCollectionDataSource itemsJRBeanFourniture = new JRBeanCollectionDataSource(listItemsFourniture);

        Map<String, Object> parametersFourniture = new HashMap<String, Object>();
        parametersFourniture.put("quotePiece", itemsJRBeanFourniture);

        // traitement Pièces

        JRBeanCollectionDataSource itemsJRBeanPieces = new JRBeanCollectionDataSource(listItemsPieces);

        Map<String, Object> parametersPieces = new HashMap<String, Object>();
        parametersPieces.put("quotePiece", itemsJRBeanPieces);

        // traitement Avis Technique

        Map<String, Object> parametersAvisTechnique = new HashMap<String, Object>();

        // traitement Data Header

        if (sinisterPecDTO.getSinisterId() != null) {
            SinisterDTO sinisterDTO = sinisterService.findOne(sinisterPecDTO.getSinisterId());
            if (sinisterDTO.getVehicleRegistration() != null) {
                parametersMain.put("insuredImmatriculation", sinisterDTO.getVehicleRegistration());
                parametersIng.put("insuredImmatriculation", sinisterDTO.getVehicleRegistration());
                parametersFourniture.put("insuredImmatriculation", sinisterDTO.getVehicleRegistration());
                parametersPieces.put("insuredImmatriculation", sinisterDTO.getVehicleRegistration());
                parametersAvisTechnique.put("insuredImmatriculation", sinisterDTO.getVehicleRegistration());
            } else {
                parametersMain.put("insuredImmatriculation", " ");
                parametersIng.put("insuredImmatriculation", " ");
                parametersFourniture.put("insuredImmatriculation", " ");
                parametersPieces.put("insuredImmatriculation", " ");
                parametersAvisTechnique.put("insuredImmatriculation", " ");
            }

            if (sinisterDTO.getVehicleId() != null) {
                VehiculeAssureDTO vehiculeAssureDTO = vehiculeAssureService.findOne(sinisterDTO.getVehicleId());
                if (vehiculeAssureDTO.getInsuredId() != null) {
                    AssureDTO assureDTO = assureService.findOne(vehiculeAssureDTO.getInsuredId());
                    if (assureDTO.getCompany()) {
                        if (assureDTO.getRaisonSociale() != null) {
                            parametersMain.put("insuredName", assureDTO.getRaisonSociale());
                            parametersIng.put("insuredName", assureDTO.getRaisonSociale());
                            parametersFourniture.put("insuredName", assureDTO.getRaisonSociale());
                            parametersPieces.put("insuredName", assureDTO.getRaisonSociale());
                            parametersAvisTechnique.put("insuredName", assureDTO.getRaisonSociale());
                        } else {
                            parametersMain.put("insuredName", " ");
                            parametersIng.put("insuredName", " ");
                            parametersFourniture.put("insuredName", " ");
                            parametersPieces.put("insuredName", " ");
                            parametersAvisTechnique.put("insuredName", " ");
                        }
                    } else {

                        parametersMain.put("insuredName",
                                String.valueOf(assureDTO.getNom() + " " + assureDTO.getPrenom()));
                        parametersIng.put("insuredName",
                                String.valueOf(assureDTO.getNom() + " " + assureDTO.getPrenom()));
                        parametersFourniture.put("insuredName",
                                String.valueOf(assureDTO.getNom() + " " + assureDTO.getPrenom()));
                        parametersPieces.put("insuredName",
                                String.valueOf(assureDTO.getNom() + " " + assureDTO.getPrenom()));
                        parametersAvisTechnique.put("insuredName",
                                String.valueOf(assureDTO.getNom() + " " + assureDTO.getPrenom()));

                    }
                }
            }

            if (sinisterDTO.getReference() != null) {
                parametersMain.put("referenceSinister", sinisterDTO.getReference());
                parametersIng.put("referenceSinister", sinisterDTO.getReference());
                parametersFourniture.put("referenceSinister", sinisterDTO.getReference());
                parametersPieces.put("referenceSinister", sinisterDTO.getReference());
                parametersAvisTechnique.put("referenceSinister", sinisterDTO.getReference());
            } else {
                parametersMain.put("referenceSinister", " ");
                parametersIng.put("referenceSinister", " ");
                parametersFourniture.put("referenceSinister", " ");
                parametersPieces.put("referenceSinister", " ");
                parametersAvisTechnique.put("referenceSinister", " ");
            }

            if (sinisterPecDTO.getCompanyReference() != null) {
                parametersMain.put("numSinister", sinisterPecDTO.getCompanyReference());
                parametersIng.put("numSinister", sinisterPecDTO.getCompanyReference());
                parametersFourniture.put("numSinister", sinisterPecDTO.getCompanyReference());
                parametersPieces.put("numSinister", sinisterPecDTO.getCompanyReference());
                parametersAvisTechnique.put("numSinister", sinisterPecDTO.getCompanyReference());
            } else {
                parametersMain.put("numSinister", " ");
                parametersIng.put("numSinister", " ");
                parametersFourniture.put("numSinister", " ");
                parametersPieces.put("numSinister", " ");
                parametersAvisTechnique.put("numSinister", " ");
            }

            if (sinisterPecDTO.getReparateurId() != null) {
                ReparateurDTO reparateurDTO = reparateurService.findOne(sinisterPecDTO.getReparateurId());
                if (reparateurDTO.getRaisonSociale() != null) {
                    parametersMain.put("reparateur", reparateurDTO.getRaisonSociale());
                    parametersIng.put("reparateur", reparateurDTO.getRaisonSociale());
                    parametersFourniture.put("reparateur", reparateurDTO.getRaisonSociale());
                    parametersPieces.put("reparateur", reparateurDTO.getRaisonSociale());
                    parametersAvisTechnique.put("reparateur", reparateurDTO.getRaisonSociale());
                } else {
                    parametersMain.put("reparateur", " ");
                    parametersIng.put("reparateur", " ");
                    parametersFourniture.put("reparateur", " ");
                    parametersPieces.put("reparateur", " ");
                    parametersAvisTechnique.put("reparateur", " ");
                }

            } else {
                parametersMain.put("reparateur", " ");
                parametersIng.put("reparateur", " ");
                parametersFourniture.put("reparateur", " ");
                parametersPieces.put("reparateur", " ");
                parametersAvisTechnique.put("reparateur", " ");
            }

            if (sinisterPecDTO.getExpertId() != null) {
                ExpertDTO expertDTO = expertService.findOne(sinisterPecDTO.getExpertId());
                if (expertDTO.getRaisonSociale() != null) {
                    parametersMain.put("expert", expertDTO.getRaisonSociale());
                    parametersIng.put("expert", expertDTO.getRaisonSociale());
                    parametersFourniture.put("expert", expertDTO.getRaisonSociale());
                    parametersPieces.put("expert", expertDTO.getRaisonSociale());
                    parametersAvisTechnique.put("expert", expertDTO.getRaisonSociale());

                } else {
                    parametersMain.put("expert", " ");
                    parametersIng.put("expert", " ");
                    parametersFourniture.put("expert", " ");
                    parametersPieces.put("expert", " ");
                    parametersAvisTechnique.put("expert", " ");
                }

            } else {
                parametersMain.put("expert", " ");
                parametersIng.put("expert", " ");
                parametersFourniture.put("expert", " ");
                parametersPieces.put("expert", " ");
                parametersAvisTechnique.put("expert", " ");
            }

            if (sinisterDTO.getContractId() != null) {
                ContratAssuranceDTO contratAssuranceDTO = contratAssuranceService.findOne(sinisterDTO.getContractId());

                if (contratAssuranceDTO.getAgenceNom() != null) {
                    parametersMain.put("agenceName", contratAssuranceDTO.getAgenceNom());
                    parametersIng.put("agenceName", contratAssuranceDTO.getAgenceNom());
                    parametersFourniture.put("agenceName", contratAssuranceDTO.getAgenceNom());
                    parametersPieces.put("agenceName", contratAssuranceDTO.getAgenceNom());
                    parametersAvisTechnique.put("agenceName", contratAssuranceDTO.getAgenceNom());

                } else {
                    parametersMain.put("agenceName", " ");
                    parametersIng.put("agenceName", " ");
                    parametersFourniture.put("agenceName", " ");
                    parametersPieces.put("agenceName", " ");
                    parametersAvisTechnique.put("agenceName", " ");
                }

                if (contratAssuranceDTO.getNomCompagnie() != null) {
                    parametersMain.put("companyInsurance", contratAssuranceDTO.getNomCompagnie());
                    parametersIng.put("companyInsurance", contratAssuranceDTO.getNomCompagnie());
                    parametersFourniture.put("companyInsurance", contratAssuranceDTO.getNomCompagnie());
                    parametersPieces.put("companyInsurance", contratAssuranceDTO.getNomCompagnie());
                    parametersAvisTechnique.put("companyInsurance", contratAssuranceDTO.getNomCompagnie());

                } else {
                    parametersMain.put("companyInsurance", " ");
                    parametersIng.put("companyInsurance", " ");
                    parametersFourniture.put("companyInsurance", " ");
                    parametersPieces.put("companyInsurance", " ");
                    parametersAvisTechnique.put("companyInsurance", " ");
                }

            }
        }

        if (quotationPdf.getTtcAmount() != null) {
            parametersMain.put("totalDevis", String.valueOf(quotationPdf.getTtcAmount()));
            parametersIng.put("totalDevis", String.valueOf(quotationPdf.getTtcAmount()));
            parametersFourniture.put("totalDevis", String.valueOf(quotationPdf.getTtcAmount()));
            parametersPieces.put("totalDevis", String.valueOf(quotationPdf.getTtcAmount()));

        } else {
            parametersMain.put("totalDevis", " ");
            parametersIng.put("totalDevis", " ");
            parametersFourniture.put("totalDevis", " ");
            parametersPieces.put("totalDevis", " ");
        }

        if (quotationPdf.getPriceNewVehicle() != null) {
            parametersAvisTechnique.put("valeurANeuf", String.valueOf(quotationPdf.getPriceNewVehicle()));
        } else {
            parametersAvisTechnique.put("valeurANeuf", " ");
        }

        if (quotationPdf.getMarketValue() != null) {
            parametersAvisTechnique.put("valeurVenale", String.valueOf(quotationPdf.getMarketValue()));
        } else {
            parametersAvisTechnique.put("valeurVenale", " ");
        }

        if (quotationPdf.getKilometer() != null) {
            parametersAvisTechnique.put("kilometer", String.valueOf(quotationPdf.getKilometer()));
        } else {
            parametersAvisTechnique.put("kilometer", " ");
        }

        if (quotationPdf.getHeureMO() != null) {
            parametersAvisTechnique.put("heureMo", String.valueOf(quotationPdf.getHeureMO()));
        } else {
            parametersAvisTechnique.put("heureMo", " ");
        }

        if (quotationPdf.getEstimateJour() != null) {
            parametersAvisTechnique.put("delaiEstimatif", String.valueOf(quotationPdf.getEstimateJour()));
        } else {
            parametersAvisTechnique.put("delaiEstimatif", " ");
        }

        if (quotationPdf.getTotalMo() != null) {
            parametersAvisTechnique.put("totalMo", String.valueOf(quotationPdf.getTotalMo()));
        } else {
            parametersAvisTechnique.put("totalMo", " ");
        }

        if (quotationPdf.getConditionVehicle() != null) {
            parametersAvisTechnique.put("etatVehicule", String.valueOf(quotationPdf.getConditionVehicle()));
        } else {
            parametersAvisTechnique.put("etatVehicule", " ");
        }

        if (quotationPdf.getRepairableVehicle() != null) {
            if ((new Boolean(true)).equals(quotationPdf.getRepairableVehicle())) {
                parametersAvisTechnique.put("vehiculeReparable", "Oui");
            } else {
                parametersAvisTechnique.put("vehiculeReparable", "Non");
            }
        } else {
            parametersAvisTechnique.put("vehiculeReparable", " ");
        }

        if (quotationPdf.getPreliminaryReport() != null) {
            if ((new Boolean(true)).equals(quotationPdf.getPreliminaryReport())) {
                parametersAvisTechnique.put("constatPreliminaire", "Oui");
            } else {
                parametersAvisTechnique.put("constatPreliminaire", "Non");
            }
        } else {
            parametersAvisTechnique.put("constatPreliminaire", " ");
        }

        if (quotationPdf.getImmobilizedVehicle() != null) {
            if ((new Boolean(true)).equals(quotationPdf.getImmobilizedVehicle())) {
                parametersAvisTechnique.put("vehiculeImmobilise", "Oui");
            } else {
                parametersAvisTechnique.put("vehiculeImmobilise", "Non");
            }
        } else {
            parametersAvisTechnique.put("vehiculeImmobilise", " ");
        }

        if (quotationPdf.getConcordanceReport() != null) {
            if ((new Boolean(true)).equals(quotationPdf.getConcordanceReport())) {
                parametersAvisTechnique.put("concordance", "Oui");
            } else {
                parametersAvisTechnique.put("concordance", "Non");
            }
        } else {
            parametersAvisTechnique.put("concordance", " ");
        }

        parametersMain.put("logoSlogon", getClass().getResourceAsStream(logo_path));
        parametersIng.put("logoSlogon", getClass().getResourceAsStream(logo_path1));
        parametersFourniture.put("logoSlogon", getClass().getResourceAsStream(logo_path2));
        parametersPieces.put("logoSlogon", getClass().getResourceAsStream(logo_path3));
        parametersAvisTechnique.put("logoSlogon", getClass().getResourceAsStream(logo_path4));

        try {

            // traitement MainO

            InputStream jasperStreamMain = null;
            JasperDesign designMain = null;
            JasperReport reportMain = null;
            JasperPrint jasperPrintMain = null;

            if (quotationPdf.getListMainO().size() != 0) {
                jasperStreamMain = this.getClass().getResourceAsStream(quote_main_template);
                designMain = JRXmlLoader.load(jasperStreamMain);
                reportMain = JasperCompileManager.compileReport(designMain);

                jasperPrintMain = JasperFillManager.fillReport(reportMain, parametersMain, new JREmptyDataSource());
            }

            // traitement Ingredient

            InputStream jasperStreamIng = null;
            JasperDesign designIng = null;
            JasperReport reportIng = null;
            JasperPrint jasperPrintIng = null;

            if (quotationPdf.getListIngredients().size() != 0) {
                jasperStreamIng = this.getClass().getResourceAsStream(quote_ingredient_template);
                designIng = JRXmlLoader.load(jasperStreamIng);
                reportIng = JasperCompileManager.compileReport(designIng);

                jasperPrintIng = JasperFillManager.fillReport(reportIng, parametersIng, new JREmptyDataSource());
            }

            // traitement Fourniture

            InputStream jasperStreamFourniture = null;
            JasperDesign designFourniture = null;
            JasperReport reportFourniture = null;
            JasperPrint jasperPrintFourniture = null;

            if (quotationPdf.getListFourniture().size() != 0) {
                jasperStreamFourniture = this.getClass().getResourceAsStream(quote_fourniture_template);
                designFourniture = JRXmlLoader.load(jasperStreamFourniture);
                reportFourniture = JasperCompileManager.compileReport(designFourniture);

                jasperPrintFourniture = JasperFillManager.fillReport(reportFourniture, parametersFourniture,
                        new JREmptyDataSource());
            }

            // traitement Pieces

            InputStream jasperStreamPieces = null;
            JasperDesign designPieces = null;
            JasperReport reportPieces = null;
            JasperPrint jasperPrintPieces = null;

            if (quotationPdf.getListItemsPieces().size() != 0) {
                jasperStreamPieces = this.getClass().getResourceAsStream(quote_piece_template);
                designPieces = JRXmlLoader.load(jasperStreamPieces);
                reportPieces = JasperCompileManager.compileReport(designPieces);

                jasperPrintPieces = JasperFillManager.fillReport(reportPieces, parametersPieces,
                        new JREmptyDataSource());
            }

            // traitement Avis Technique
            InputStream jasperStreamAvisTechnique = null;
            JasperDesign designAvisTechnique = null;
            JasperReport reportAvisTechnique = null;
            JasperPrint jasperPrintAvisTechnique = null;

            if ((new Boolean(true)).equals(quotationPdf.getPdfGenerationAvisTechnique())) {
                jasperStreamAvisTechnique = this.getClass().getResourceAsStream(quote_avis_technique_template);
                designAvisTechnique = JRXmlLoader.load(jasperStreamAvisTechnique);
                reportAvisTechnique = JasperCompileManager.compileReport(designAvisTechnique);

                jasperPrintAvisTechnique = JasperFillManager.fillReport(reportAvisTechnique, parametersAvisTechnique,
                        new JREmptyDataSource());
            }

            // traitement multipage Linking

            JasperPrint jasperPrintMainIngr = null;
            JasperPrint jasperPrintFourniturePieces = null;
            JasperPrint jasperPrintTotale = null;

            if (jasperPrintMain != null && jasperPrintIng != null && jasperPrintFourniture != null
                    && jasperPrintPieces != null) {
                jasperPrintMainIngr = multipageLinking(jasperPrintMain, jasperPrintIng);

                jasperPrintFourniturePieces = multipageLinking(jasperPrintFourniture, jasperPrintPieces);

                jasperPrintTotale = multipageLinking(jasperPrintMainIngr, jasperPrintFourniturePieces);
            } else

            if (jasperPrintMain == null && jasperPrintIng != null && jasperPrintFourniture != null
                    && jasperPrintPieces != null) {
                jasperPrintMainIngr = multipageLinking(jasperPrintIng, jasperPrintFourniture);

                jasperPrintTotale = multipageLinking(jasperPrintMainIngr, jasperPrintPieces);
            } else

            if (jasperPrintMain != null && jasperPrintIng == null && jasperPrintFourniture != null
                    && jasperPrintPieces != null) {
                jasperPrintMainIngr = multipageLinking(jasperPrintMain, jasperPrintFourniture);

                jasperPrintTotale = multipageLinking(jasperPrintMainIngr, jasperPrintPieces);
            } else

            if (jasperPrintMain != null && jasperPrintIng != null && jasperPrintFourniture == null
                    && jasperPrintPieces != null) {
                jasperPrintMainIngr = multipageLinking(jasperPrintMain, jasperPrintIng);

                jasperPrintTotale = multipageLinking(jasperPrintMainIngr, jasperPrintPieces);
            } else

            if (jasperPrintMain != null && jasperPrintIng != null && jasperPrintFourniture != null
                    && jasperPrintPieces == null) {
                jasperPrintMainIngr = multipageLinking(jasperPrintMain, jasperPrintIng);

                jasperPrintTotale = multipageLinking(jasperPrintMainIngr, jasperPrintFourniture);
            } else

            if (jasperPrintMain == null && jasperPrintIng == null && jasperPrintFourniture != null
                    && jasperPrintPieces != null) {
                jasperPrintTotale = multipageLinking(jasperPrintFourniture, jasperPrintPieces);
            } else

            if (jasperPrintMain == null && jasperPrintIng != null && jasperPrintFourniture == null
                    && jasperPrintPieces != null) {
                jasperPrintTotale = multipageLinking(jasperPrintIng, jasperPrintPieces);
            } else if (jasperPrintMain == null && jasperPrintIng != null && jasperPrintFourniture != null
                    && jasperPrintPieces == null) {
                jasperPrintTotale = multipageLinking(jasperPrintIng, jasperPrintFourniture);
            } else

            if (jasperPrintMain != null && jasperPrintIng == null && jasperPrintFourniture == null
                    && jasperPrintPieces != null) {
                jasperPrintTotale = multipageLinking(jasperPrintMain, jasperPrintPieces);
            } else

            if (jasperPrintMain != null && jasperPrintIng == null && jasperPrintFourniture != null
                    && jasperPrintPieces == null) {
                jasperPrintTotale = multipageLinking(jasperPrintMain, jasperPrintFourniture);
            } else

            if (jasperPrintMain != null && jasperPrintIng != null && jasperPrintFourniture == null
                    && jasperPrintPieces == null) {
                jasperPrintTotale = multipageLinking(jasperPrintMain, jasperPrintIng);
            } else

            if (jasperPrintMain == null && jasperPrintIng == null && jasperPrintFourniture == null
                    && jasperPrintPieces != null) {
                jasperPrintTotale = jasperPrintPieces;
            } else

            if (jasperPrintMain == null && jasperPrintIng == null && jasperPrintFourniture != null
                    && jasperPrintPieces == null) {
                jasperPrintTotale = jasperPrintFourniture;
            } else

            if (jasperPrintMain == null && jasperPrintIng != null && jasperPrintFourniture == null
                    && jasperPrintPieces == null) {
                jasperPrintTotale = jasperPrintIng;
            } else

            if (jasperPrintMain != null && jasperPrintIng == null && jasperPrintFourniture == null
                    && jasperPrintPieces == null) {
                jasperPrintTotale = jasperPrintMain;
            }

            JasperPrint jasperPrint = null;

            if ((new Boolean(true)).equals(quotationPdf.getPdfGenerationAvisTechnique())) {
                jasperPrint = multipageLinking(jasperPrintTotale, jasperPrintAvisTechnique);
            } else {
                jasperPrint = jasperPrintTotale;
            }

            // traitement de generation

            response.setContentType("application/x-pdf");
            response.setHeader("Content-Disposition", "inline; filename=report1.pdf");

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            String uniqueFileName = "devis_" + dtf.format(localDateTime) + ".pdf";
            response.addHeader("pdfname", properties.getCheminPdfReport() + "/" + uniqueFileName);

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
