package com.gaconnecte.auxilium.web.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.repository.AssureRepository;
import com.gaconnecte.auxilium.repository.ContratAssuranceRepository;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.drools.FactDevis;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.ContactService;
import com.gaconnecte.auxilium.service.ContratAssuranceService;
import com.gaconnecte.auxilium.service.DesignationUsageContratService;
import com.gaconnecte.auxilium.service.DroolsService;
import com.gaconnecte.auxilium.service.ExpertService;
import com.gaconnecte.auxilium.service.HistoryPecService;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.PieceJointeService;
import com.gaconnecte.auxilium.service.ReparateurService;
import com.gaconnecte.auxilium.service.TiersService;
import com.gaconnecte.auxilium.service.VehiculeAssureService;
import com.gaconnecte.auxilium.service.ApecService;
import com.gaconnecte.auxilium.service.QuotationService;
import com.gaconnecte.auxilium.service.SinisterService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.SinisterPecService;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.VehiculeAssureDTO;
import com.gaconnecte.auxilium.service.dto.QuotationDTO;
import java.time.LocalDateTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class AccordReports {

    private final Logger log = LoggerFactory.getLogger(AccordReports.class);
    private static final String logo_path = "/report/LogoSlogon.png";
    // private static final String slogon_path = "/report/slogon.png";
    private static final String footer_path = "/report/footer.png";

    @Autowired
    PieceJointeService pieceJointeService;
    @Autowired
    ContratAssuranceService contratAssuranceService;
    @Autowired
    DesignationUsageContratService designationUsageContratService;
    @Autowired
    LoggerService loggerService;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    AssureService assureService;
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
    private DroolsService droolsService;
    @Autowired
    private ApecService apecService;
    @Autowired
    private SinisterService sinisterService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private HistoryPecService historyPecService;
    @Autowired
    private SinisterPecService sinisterPecService;
    @Autowired
    private QuotationService quotationService;
    @Autowired
    private ContratAssuranceRepository contratAssuranceRepository;
    private final AttachmentRepository attachmentRepository;
    @Autowired
    private AssureRepository assureRepository;

    @Autowired
    public AccordReports(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    private FactDevis startDrools(SinisterPecDTO accordPriseChargeDTO, Long id) {

        return droolsService.getDataFromDevis(accordPriseChargeDTO, id);

    }

    @PostMapping("/accordPriseChargePdf/{id}/{confirmation}/{etat}/{typeAccord}")
    public @ResponseBody void accordPriseChargePdf(HttpServletResponse response,
            @RequestBody SinisterPecDTO accordPriseChargeDTO, @PathVariable Long id, @PathVariable Boolean confirmation,
            @PathVariable Integer etat, @PathVariable String typeAccord) {

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        if (confirmation == true) { // Sauvegarde de l'accord en Background

            FactDevis factDevis = startDrools(accordPriseChargeDTO, id);
            System.out.println("1---------- save when avis expert accord pour reparation");
            ApecDTO apecDTO = new ApecDTO();
            // apecDTO.setDateGeneration(new Date());
            if (factDevis.getEngagementGa() == null) {
                apecDTO.setParticipationGa(0D);
            } else {
                apecDTO.setParticipationGa(Double.parseDouble(factDevis.getEngagementGa().toString()));
            }
            if (factDevis.getTotalPartAssuree() == null) {
                apecDTO.setParticipationAssure(0D);
            } else {
                apecDTO.setParticipationAssure(Double.parseDouble(factDevis.getTotalPartAssuree().toString()));
            }
            System.out.println("etat apec save background" + etat);
            apecDTO.setSinisterPecId(accordPriseChargeDTO.getId());
            apecDTO.setParticipationVetuste(Double.parseDouble(factDevis.getVetuste().toString()));
            /*if (accordPriseChargeDTO.getPartnerId() == 1L) {
                if (accordPriseChargeDTO.getModeId() == 6L) {
                    etat = 0;
                }
            }
            if (accordPriseChargeDTO.getModeId() == 10L && quotationService.findOne(id).getTtcAmount() > 5000D) {
                etat = 0;
            }*/

            apecDTO.setEtat(6);

            apecDTO.setApecEdit(false);
            if (apecService.findLastApecNumber() == null) {
                apecDTO.setAccordNumber(1);
            } else {
                apecDTO.setAccordNumber(apecService.findLastApecNumber() + 1);
            }
            if (factDevis.getTtcDetailsMo() != null) {
                apecDTO.setMO(Double.parseDouble(factDevis.getTtcDetailsMo().toString()));
            } else {
                apecDTO.setMO(0D);
            }
            if (factDevis.getTtcPieceR() != null) {
                apecDTO.setPR(Double.parseDouble(factDevis.getTtcPieceR().toString()));
            } else {
                apecDTO.setPR(0D);
            }
            if (factDevis.getTtcPieceIP() != null) {
                apecDTO.setIPPF(Double.parseDouble(factDevis.getTtcPieceIP().toString()));
            } else {
                apecDTO.setIPPF(0D);
            }
            if (factDevis.getTva() != null) {
                apecDTO.setParticipationTva(factDevis.getTva());
            } else {
                apecDTO.setParticipationTva(0D);
            }
            if (factDevis.getDepassplafond() != null) {
                apecDTO.setDepacementPlafond(Double.parseDouble(factDevis.getDepassplafond().toString()));
            } else {
                apecDTO.setDepacementPlafond(0D);
            }
            if (factDevis.getSurplusEncaisse() != null) {
                apecDTO.setSurplusEncaisse(Double.parseDouble(factDevis.getSurplusEncaisse().toString()));
            } else {
                apecDTO.setSurplusEncaisse(0D);
            }
            apecDTO.setDroitDeTimbre(Double.parseDouble(factDevis.getDroitTimbre().toString()));
            if (factDevis.getReglepropor() != null) {
                apecDTO.setRegleProportionnel(Double.parseDouble(factDevis.getReglepropor().toString()));
            } else {
                apecDTO.setRegleProportionnel(0D);
            }
            if (factDevis.getSoldeReparateur() != null) {
                apecDTO.setSoldeReparateur(Double.parseDouble(factDevis.getSoldeReparateur().toString()));
            } else {
                apecDTO.setSoldeReparateur(0D);
            }
            if (factDevis.getAvance() != null) {
                apecDTO.setAvanceFacture(Double.parseDouble(factDevis.getAvance().toString()));
            } else {
                apecDTO.setAvanceFacture(0D);
            }
            if (factDevis.getFraisDossier() != null) {
                apecDTO.setFraisDossier(Double.parseDouble(factDevis.getFraisDossier().toString()));
            } else {
                apecDTO.setFraisDossier(0D);
            }
            if (factDevis.getFranchise() != null) {
                apecDTO.setEstimationFranchise(Double.parseDouble(factDevis.getFranchise().toString()));
            } else {
                apecDTO.setEstimationFranchise(0D);
            }
            if (factDevis.getPartResponsabilite() != null) {
                System.out.println("part responsabilité--*-*--" + factDevis.getPartResponsabilite().toString());
                apecDTO.setParticipationRpc(Double.parseDouble(factDevis.getPartResponsabilite().toString()));
            } else {
                apecDTO.setParticipationRpc(0D);
            }
            apecDTO.setQuotationId(id);
            try {
                apecDTO.setToApprouvDate(format.parse(format.format(date)));
            } catch (Exception e) {
                System.out.println("exception when save toApprouveDate");
            }
            if (id != null) {
                apecDTO.setTtc(quotationService.findOne(id).getTtcAmount());
                if (quotationService.getQuotationType(id) == 1) {
                    apecDTO.setIsComplementaire(false);
                } else if (quotationService.getQuotationType(id) == 2) {
                    apecDTO.setIsComplementaire(true);
                }
            }
            ApecDTO apecSaving = apecService.save(apecDTO);
            accordPriseChargeDTO
                    .setStepId(106L);
            sinisterPecService.save(accordPriseChargeDTO);

            /*
             * if (apecSaving != null && accordPriseChargeDTO.getStepId() != null) {
             * historyService.historysave("APEC", apecSaving.getId(), (Object) apecSaving,
             * (Object) apecSaving, apecSaving.getEtat().intValue() + 100,
             * apecSaving.getEtat().intValue() + 100, "APEC"); }
             */
            historyPecService.historyPecApecSave("Appp", accordPriseChargeDTO.getId(), apecSaving, 1L, accordPriseChargeDTO,
                    typeAccord);

        }

        if (confirmation == false) {

            ApecDTO apecImprim = null;
            System.out.println("id devissssss-*--*" + id);
            if (id != null) {
                apecImprim = apecService.findAccordByQuotation(id); // Get Accord By Quotation
            }

            System.out.println("1----------------------------");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

            //DecimalFormat df = new DecimalFormat();
            //df.setMaximumFractionDigits(3);

            /**
             * ********************* Partie En tete *****************
             */
            try {
                System.out.println("2----------------------------");
                LocalDateTime dateGener = LocalDateTime.now();
                Map<String, Object> parameterMap = new HashMap();
                parameterMap.put("logoGacPath", this.getClass().getResource("/report/Logo-GA.png"));
                parameterMap.put("logoSlogon", getClass().getResourceAsStream(logo_path));
                parameterMap.put("footer", getClass().getResourceAsStream(footer_path));
                parameterMap.put("dateGeneration", String
                        .valueOf(dateGener != null ? dateGener.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""));
                if (apecImprim.getValidationDate() == null) {
                    parameterMap.put("dateValidAccord", "");
                } else {
                    parameterMap.put("dateValidAccord",
                            apecImprim.getValidationDate() != null
                                    ? apecImprim.getValidationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                    : "");
                }

                if (accordPriseChargeDTO.getReparateurName() == null) {
                    parameterMap.put("reparateur", "");
                } else {
                    parameterMap.put("reparateur", accordPriseChargeDTO.getReparateurName());
                }
                if (accordPriseChargeDTO.getExpertName() == null) {
                    parameterMap.put("expert", "");
                } else {
                    parameterMap.put("expert", accordPriseChargeDTO.getExpertName());
                }

                System.out.println("3----------------------------");

                if (accordPriseChargeDTO != null) {
                    System.out.println("4----------------------------");

                    if (accordPriseChargeDTO.getIncidentDate() == null) {
                        parameterMap.put("dateAccident", "");
                    } else {
                        parameterMap
                                .put("dateAccident",
                                        accordPriseChargeDTO.getIncidentDate() != null ? accordPriseChargeDTO
                                                .getIncidentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                                : "");
                    }
                    if (accordPriseChargeDTO.getCompanyName() == null) {
                        parameterMap.put("cieAssurance", "");
                    } else {
                        parameterMap.put("cieAssurance", accordPriseChargeDTO.getCompanyName());
                    }
                    if (accordPriseChargeDTO.getAgenceName() == null) {
                        parameterMap.put("agence", "");
                    } else {
                        parameterMap.put("agence", accordPriseChargeDTO.getAgenceName());
                    }
                    if (accordPriseChargeDTO.getImmatriculationVehicle() == null) {
                        parameterMap.put("immat", "");
                    } else {
                        parameterMap.put("immat", accordPriseChargeDTO.getImmatriculationVehicle());
                    }
                    if (accordPriseChargeDTO.getContractNumber() == null) {
                        parameterMap.put("numContart", "");
                    } else {
                        parameterMap.put("numContart", accordPriseChargeDTO.getContractNumber());
                    }
                    if (accordPriseChargeDTO.getSinisterId() != null) {
                        SinisterDTO sinister = sinisterService.findOne(accordPriseChargeDTO.getSinisterId());
                        // ContratAssurance contrat =
                        // contratAssuranceRepository.findOne(sinister.getContractId());
                        VehiculeAssureDTO v = vehiculeAssureService.findOne(sinister.getVehicleId());
                        parameterMap.put("marque", v.getMarqueLibelle());
                        Assure assure = assureRepository.findOne(v.getInsuredId());
                        parameterMap.put("usage", sinister.getUsageLabel());

                        if (assure.getFullName() == null) {
                            parameterMap.put("assure", "");
                        }
                        if (assure.getFullName() != null) {
                            parameterMap.put("assure", assure.getFullName());
                        }
                        if (sinister.getTel() != null) {
                            parameterMap.put("gsm", sinister.getTel());
                        } else {
                            parameterMap.put("gsm", "");
                        }
                        if (sinister.getReference() == null) {
                            parameterMap.put("numDossierGa", "");
                        } else {
                            parameterMap.put("numDossierGa", sinister.getReference());
                        }
                    }
                    if (accordPriseChargeDTO.getCompanyReference() != null) {
                        System.out.println("numero sinister presation" + accordPriseChargeDTO.getCompanyReference());
                        parameterMap.put("numSinistre", accordPriseChargeDTO.getCompanyReference());
                    } else {
                        parameterMap.put("numSinistre", "");
                    }
                }

                System.out.println("5----------------------------");

                /**
                 * ********************* End Partie En tete *****************
                 */
                // if (apecImprim.getApecEdit()) { // Génération de l'accord aprés Modification
                System.out.println("apec editer" + apecImprim.getId());
                DecimalFormat df = new DecimalFormat("###.###");
                //String output = myFormatter.format(value);

                if (apecImprim.getDroitDeTimbre() != null) {
                    parameterMap.put("droitTimbre", df.format(apecImprim.getDroitDeTimbre().doubleValue()));
                } else {
                    parameterMap.put("droitTimbre", "0");
                }
                if (apecImprim.getParticipationRpc() != null) {
                    parameterMap.put("partResponsabilite", df.format(apecImprim.getParticipationRpc()));
                } else {
                    parameterMap.put("partResponsabilite", "0");
                }
                if (apecImprim.getParticipationVetuste() != null) {
                    parameterMap.put("vetuste", df.format(apecImprim.getParticipationVetuste()));
                } else {
                    parameterMap.put("vetuste", "0");
                }
                if (apecImprim.getParticipationAssure() != null) {
                    parameterMap.put("partassuree", df.format(apecImprim.getParticipationAssure()));
                } else {
                    parameterMap.put("partassuree", "0");
                }
                if (apecImprim.getMO() != null && apecImprim.getPR() != null && apecImprim.getIPPF() != null) {
                    parameterMap.put("ttc", df.format(apecImprim.getMO() + apecImprim.getPR() + apecImprim.getIPPF()));
                } else {
                    parameterMap.put("ttc", "0");
                }
                if (apecImprim.getParticipationGa() != null) {
                    parameterMap.put("engagementga", df.format(apecImprim.getParticipationGa()));
                } else {
                    parameterMap.put("engagementga", "0");
                }
                if (apecImprim.getSoldeReparateur() != null) {
                    parameterMap.put("soldereparateur", df.format(apecImprim.getSoldeReparateur()));
                } else {
                    parameterMap.put("soldereparateur", "0");
                }
                if (apecImprim.getMO() != null) {
                    parameterMap.put("ttcdetailsmo", df.format(apecImprim.getMO()));
                } else {
                    parameterMap.put("ttcdetailsmo", "0");
                }
                if (apecImprim.getPR() != null) {
                    parameterMap.put("ttcpiecerechange", df.format(apecImprim.getPR()));
                } else {
                    parameterMap.put("ttcpiecerechange", "0");
                }
                if (apecImprim.getIPPF() != null) {
                    parameterMap.put("ttcpieceingfour", df.format(apecImprim.getIPPF()));
                } else {
                    parameterMap.put("ttcpieceingfour", "0");
                }
                if (apecImprim.getSurplusEncaisse() != null) {
                    parameterMap.put("surplusencaisse", df.format(apecImprim.getSurplusEncaisse()));
                } else {
                    parameterMap.put("surplusencaisse", "0");
                }
                if (apecImprim.getAvanceFacture() != null) {
                    parameterMap.put("avance", df.format(apecImprim.getAvanceFacture()));
                } else {
                    parameterMap.put("avance", "0");
                }
                if (apecImprim.getEstimationFranchise() != null) {
                    parameterMap.put("franchise", df.format(apecImprim.getEstimationFranchise()));
                } else {
                    parameterMap.put("franchise", "0");
                }
                if (apecImprim.getDepacementPlafond() != null) {
                    parameterMap.put("depassplafond", df.format(apecImprim.getDepacementPlafond()));
                } else {
                    parameterMap.put("depassplafond", "0");
                }
                if (apecImprim.getRegleProportionnel() != null) {
                    parameterMap.put("regleprop", df.format(apecImprim.getRegleProportionnel()));
                } else {
                    parameterMap.put("regleprop", "0");
                }
                if (apecImprim.getFraisDossier() != null) {
                    parameterMap.put("fraisdossier", df.format(apecImprim.getFraisDossier()));
                } else {
                    parameterMap.put("fraisdossier", "0");
                }
                if (apecImprim.getParticipationTva() != null) {
                    parameterMap.put("tva", df.format(apecImprim.getParticipationTva()));
                } else {
                    parameterMap.put("tva", "0");
                }

                /*
                 * } else {// Génération de l'accord sans modification
                 * 
                 * if (factDevis.getDroitTimbre() != null) { parameterMap.put("droitTimbre",
                 * df.format(factDevis.getDroitTimbre().doubleValue())); } if
                 * (factDevis.getPartResponsabilite() != null) {
                 * 
                 * parameterMap.put("partResponsabilite",
                 * df.format(factDevis.getPartResponsabilite())); } else {
                 * parameterMap.put("partResponsabilite", "0"); } if (factDevis.getTva() !=
                 * null) { parameterMap.put("tva", df.format(factDevis.getTva())); } else {
                 * parameterMap.put("tva", "0"); }
                 * 
                 * if (factDevis.getVetuste() != null) { parameterMap.put("vetuste",
                 * df.format(factDevis.getVetuste())); } if (factDevis.getTotalPartAssuree() !=
                 * null) { System.out.println("part assurée" + factDevis.getTotalPartAssuree());
                 * parameterMap.put("partassuree", df.format(factDevis.getTotalPartAssuree()));
                 * }
                 * 
                 * if (factDevis.getTotalTtc() != null) { parameterMap.put("ttc",
                 * df.format(factDevis.getTotalTtc())); }
                 * System.out.println("6----------------------------"); if
                 * (factDevis.getEngagementGa() != null) {
                 * 
                 * parameterMap.put("engagementga", df.format(factDevis.getEngagementGa())); }
                 * else { parameterMap.put("engagementga", "0"); } if
                 * (factDevis.getSoldeReparateur() != null) {
                 * parameterMap.put("soldereparateur",
                 * df.format(factDevis.getSoldeReparateur())); } else {
                 * parameterMap.put("soldereparateur", "0"); } if (factDevis.getTtcDetailsMo()
                 * != null) { parameterMap.put("ttcdetailsmo",
                 * df.format(factDevis.getTtcDetailsMo())); } else {
                 * parameterMap.put("ttcdetailsmo", "0"); } if (factDevis.getTtcPieceR() !=
                 * null) { parameterMap.put("ttcpiecerechange",
                 * df.format(factDevis.getTtcPieceR())); } else {
                 * parameterMap.put("ttcpiecerechange", "0"); } if (factDevis.getTtcPieceIP() !=
                 * null) { parameterMap.put("ttcpieceingfour",
                 * df.format(factDevis.getTtcPieceIP())); } else {
                 * parameterMap.put("ttcpieceingfour", "0"); } if
                 * (factDevis.getSurplusEncaisse() != null) {
                 * parameterMap.put("surplusencaisse",
                 * df.format(factDevis.getSurplusEncaisse())); } else {
                 * parameterMap.put("surplusencaisse", "0"); } if (factDevis.getAvance() !=
                 * null) { parameterMap.put("avance", df.format(factDevis.getAvance())); } else
                 * { parameterMap.put("avance", "0"); } if (factDevis.getFranchise() != null) {
                 * 
                 * parameterMap.put("franchise", df.format(factDevis.getFranchise()));
                 * 
                 * } if (factDevis.getDepassplafond() != null) {
                 * 
                 * parameterMap.put("depassplafond", df.format(factDevis.getDepassplafond())); }
                 * else { parameterMap.put("depassplafond", "0"); } if
                 * (factDevis.getReglepropor() != null) { parameterMap.put("regleprop",
                 * df.format(factDevis.getReglepropor())); } else {
                 * parameterMap.put("regleprop", "0"); }
                 * 
                 * if (factDevis.getFraisDossier() != null) {
                 * System.out.println("frais dossier when generate reports-*-*-*" +
                 * factDevis.getFraisDossier()); parameterMap.put("fraisdossier",
                 * df.format(factDevis.getFraisDossier())); } else {
                 * parameterMap.put("fraisdossier", "0.0"); }
                 * System.out.println("7----------------------------");
                 * 
                 * }
                 */
                if (apecImprim != null) {
                    parameterMap.put("numAccord", String.valueOf(apecImprim.getAccordNumber()));
                } else {
                    parameterMap.put("numAccord", "0");
                }

                parameterMap.put("unite", "(DT)");

                InputStream jasperStream = this.getClass().getResourceAsStream("/report/accordPEC.jrxml");
                JasperDesign design = JRXmlLoader.load(jasperStream);
                JasperReport report = JasperCompileManager.compileReport(design);
                System.out.println("8----------------------------");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameterMap, new JREmptyDataSource());
                response.setContentType("application/x-pdf");
                response.setHeader("Content-Disposition", "inline; filename=report1.pdf");

                LocalDateTime localDateTime = LocalDateTime.now();
                String uniqueFileName = "accordPriseEnCharge_" + dtf.format(localDateTime) + ".pdf";

                response.addHeader("pdfname",
                        pieceJointeService.affectCheminPdfReport().getcontent() + "/" + uniqueFileName);

                final OutputStream outputStream = response.getOutputStream();
                System.out.println("9----------------------------");
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                JasperExportManager.exportReportToPdfFile(jasperPrint,
                        pieceJointeService.affectChemin().getcontent() + "/" + uniqueFileName);
                // JasperExportManager.exportReportToPdfFile(jasperPrint,
                // "/home/local/GA/ridha.bouazizi/accord/"+uniqueFileName);

                System.out.println("10----------------------------");

                /**
                 * ******** Save history and attachemenet de l'accord ****
                 */
                if (apecImprim != null) {

                    apecImprim.setImprimDate(format.parse(format.format(date)));
                    if (apecImprim != null) {
                        historyPecService.historyPecsave("APEC", apecImprim.getId(), (Object) apecImprim, (Object) apecImprim,
                                103, apecImprim.getEtat().intValue() + 100, "APEC");
                    }
                    if (!apecImprim.getIsComplementaire()) {
                        System.out.println("is not complementaire-*-*-*");
                        apecImprim.setEtat(9);
                        accordPriseChargeDTO.setStepId(109L);
                    } else if (apecImprim.getIsComplementaire()) {
                        System.out.println("is complementaire-*-*-*");
                        apecImprim.setEtat(9);
                        accordPriseChargeDTO.setStepId(109L);// Modified by MED
                    }
                    apecService.save(apecImprim);

                    sinisterPecService.save(accordPriseChargeDTO);
                    System.out.println("11-----------------");
                    Attachment accordAttchment = new Attachment();

                    accordAttchment.setCreationDate(LocalDateTime.now());
                    accordAttchment.setEntityName(Constants.ENTITY_APEC);
                    accordAttchment.setOriginal(false);
                    accordAttchment.setNote("genérer Accord");
                    accordAttchment.setOriginalName(uniqueFileName);
                    accordAttchment.setName(uniqueFileName);
                    accordAttchment.setEntityId(apecImprim.getId());
                    accordAttchment.setPath(Constants.ENTITY_APEC);
                    accordAttchment.setLabel("Accord");
                    System.out.println("12-----------------");
                    accordAttchment = attachmentRepository.save(accordAttchment);
                    System.out.println("13-----------------" + accordAttchment.getId());
                    historyPecService.historyPecsave("sinister pec", accordPriseChargeDTO.getId(),
                            (Object) accordPriseChargeDTO, (Object) accordPriseChargeDTO,
                            accordPriseChargeDTO.getOldStepNw().intValue(), accordPriseChargeDTO.getStepId().intValue(),
                            "PECS");
                }

                System.out.println("14-----------------");
                // type.setNumAccord(numAccord);
                // typeChoixDroolsService.update(type);

            } catch (Exception e) {
                loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
                e.printStackTrace();
            }
        }
    }

    private Long getStepRulesManagement(SinisterPecDTO accordPriseChargeDTO, Long id) {
        // log.debug("value of tt
        // amout"+accordPriseChargeDTO.getQuotation().getTtcAmount());
        // log.debug("value of mode gestion"+accordPriseChargeDTO.getModeId());

        //QuotationDTO q = quotationService.findOne(id);
        Long stepAccord = 106L;
        /*System.out.println("partner id getstep rules-*-*-*-*" + accordPriseChargeDTO.getPartnerId());
        System.out.println("partner id getstep rules intger-*-*-*-*"
                + Integer.parseInt(accordPriseChargeDTO.getPartnerId().toString()));
        if (accordPriseChargeDTO.getPartnerId() == 1L) {
            if (accordPriseChargeDTO.getModeId() == 6L || accordPriseChargeDTO.getModeId() == 2L) {
                System.out.println("SALIM-*-*-*-*" + stepAccord);
                stepAccord = 100L;

            }
        }
        if (accordPriseChargeDTO.getPartnerId() == 4L) {
            if ((accordPriseChargeDTO.getModeId() == 6L && q.getTtcAmount() > 5000D)
                    || (accordPriseChargeDTO.getModeId() == 2L)) {
                System.out.println("AMI-*-*-*-*" + stepAccord);
                stepAccord = 100L;

            }
        }
        if (accordPriseChargeDTO.getPartnerId() == 7L) {
            if ((accordPriseChargeDTO.getModeId() == 6L && q.getTtcAmount() > 5000D)
                    || (accordPriseChargeDTO.getModeId() == 2L)) {
                System.out.println("AMANA-*-*-*-*" + stepAccord);
                stepAccord = 100L;

            }
        }
        if (accordPriseChargeDTO.getPartnerId() == 6L) {
            if (accordPriseChargeDTO.getModeId() == 6L && q.getTtcAmount() > 5000D) {
                System.out.println("LLOYED-*-*-*-*" + stepAccord);
                stepAccord = 100L;

            }
        }
        if (accordPriseChargeDTO.getPartnerId() == 5L) {
            if ((accordPriseChargeDTO.getModeId() == 6L && q.getTtcAmount() > 3000D)
                    || (accordPriseChargeDTO.getModeId() == 2L)) {
                System.out.println("BIAT-*-*-*-*" + stepAccord);
                stepAccord = 100L;

            }
        }
        if (accordPriseChargeDTO.getPartnerId() == 8L) {
            if ((accordPriseChargeDTO.getModeId() == 6L && q.getTtcAmount() > 5000D)
                    || (accordPriseChargeDTO.getModeId() == 2L)) {
                System.out.println("ATTAKAFULIA-*-*-*-*" + stepAccord);
                stepAccord = 100L;

            }
        }
        if (accordPriseChargeDTO.getModeId() == 10L && q.getTtcAmount() > 5000D) {
            System.out.println("MODE DE GESTION TIERCE-*-*-*-*" + stepAccord);
            stepAccord = 100L;

        }

        System.out.println("step accord amana takaful-*-*-*-*" + stepAccord);*/
        return stepAccord;
    }

}
