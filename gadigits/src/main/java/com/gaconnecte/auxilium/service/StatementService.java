package com.gaconnecte.auxilium.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.domain.Global;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.Statement;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.enumeration.NatureIncident;
import com.gaconnecte.auxilium.repository.GlobalRepository;
import com.gaconnecte.auxilium.repository.RefRemorqueurRepository;
import com.gaconnecte.auxilium.repository.SinisterPrestationRepository;
import com.gaconnecte.auxilium.repository.StatementRepository;
import com.gaconnecte.auxilium.repository.search.StatementSearchRepository;
import com.gaconnecte.auxilium.service.dto.CompanyFtlDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.StatementDTO;
import com.gaconnecte.auxilium.service.dto.StatementFtlDTO;
import com.gaconnecte.auxilium.service.mapper.StatementMapper;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Service Implementation for managing Statement.
 */
@Service
@Transactional
public class StatementService {

    private final Logger log = LoggerFactory.getLogger(StatementService.class);

    private final StatementRepository statementRepository;

    private final StatementMapper statementMapper;

    private final StatementSearchRepository statementSearchRepository;

    private final SinisterPrestationRepository sinisterPrestationRepository;

    private final GlobalRepository globalRepository;

    private final RefRemorqueurRepository remorqueurRepository;

    private final ApplicationProperties properties;

    private final Configuration freemarkerConfig;

    private final PartnerService partnerService;

    public StatementService(StatementRepository statementRepository, StatementMapper statementMapper,
            StatementSearchRepository statementSearchRepository, SinisterPrestationRepository sinisterPrestationRepository,
             GlobalRepository globalRepository, RefRemorqueurRepository remorqueurRepository, ApplicationProperties properties, Configuration freemarkerConfig, PartnerService partnerService) {
        this.statementRepository = statementRepository;
        this.statementMapper = statementMapper;
        this.statementSearchRepository = statementSearchRepository;
        this.sinisterPrestationRepository = sinisterPrestationRepository;
        this.globalRepository = globalRepository;
        this.remorqueurRepository = remorqueurRepository;
        this.properties = properties;
        this.freemarkerConfig = freemarkerConfig;
        this.partnerService = partnerService;
    }

    /**
     * Save a statement.
     *
     * @param statementDTO the entity to save
     * @return the persisted entity
     */
    public StatementDTO save(StatementDTO statementDTO) {
        log.debug("Request to save Statement : {}", statementDTO);
        Statement statement = statementMapper.toEntity(statementDTO);
        statement = statementRepository.save(statement);
        StatementDTO result = statementMapper.toDto(statement);
        statementSearchRepository.save(statement);
        return result;
    }

    /**
     * Get all the statements.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StatementDTO> findAll() {
        log.debug("Request to get all Statements");
        return statementRepository.findAllByStepNotOrderByCreationDateDesc(2).stream()
                .map(statementMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the companies statements.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PartnerDTO> findAllCompaniesForStatement(String month) {
        log.debug("Request to get all companies for statements");
        // Calculate period 
    	YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("MMyyyy"));
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atDay(1);
        endDate = endDate.plusMonths(1).minusDays(1);
        LocalDateTime startDate1 = startDate.atStartOfDay();
        LocalDateTime endDate1 = endDate.atStartOfDay();
        String pdfName = month.substring(0, 2) + "-" + month.substring(2) + ".pdf"; 
        log.debug("Getting statement from {} to {}", startDate, endDate);
        
        List<PartnerDTO> dtos = new LinkedList<>();
        List<PartnerDTO> partners = partnerService.findAll();
        for (PartnerDTO partner : partners) {
            // Test if we will print or no
            boolean toBeAdded = false;
            Map<Long, Long> tugs = new HashMap<>();
            Set<SinisterPrestation> sps = sinisterPrestationRepository.findAllSinisterPrestationClosedOrCanceledGroupByPartner(partner.getId(), startDate1, endDate1);
            if (CollectionUtils.isNotEmpty(sps)) {
                toBeAdded = true;
                for (SinisterPrestation sp : sps) {
                    tugs.put(sp.getAffectedTugId(), sp.getAffectedTugId());
                    Integer validatedSt = statementRepository.findCountValidStatement(sp.getAffectedTugId(), pdfName);
                    if (validatedSt == 0) { // the statement is invalid
                        toBeAdded = false;
                        break;
                    }
                }
            }
            if (toBeAdded) {
                dtos.add(partner);
            }
        }
        return dtos;
    }

    /**
     * Get one statement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StatementDTO findOne(Long id) {
        log.debug("Request to get Statement : {}", id);
        Statement statement = statementRepository.findOne(id);
        return statementMapper.toDto(statement);
    }

    /**
     * Delete the statement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Statement : {}", id);
        statementRepository.delete(id);
        statementSearchRepository.delete(id);
    }

    /**
     * Search for the statement corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StatementDTO> search(String query) {
        log.debug("Request to search Statements for query {}", query);
        return StreamSupport
                .stream(statementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .map(statementMapper::toDto)
                .collect(Collectors.toList());
    }

    public Set<Long> findAllTugWithClosedOrCanceledPrestation() {
        return sinisterPrestationRepository.findAllTugWithClosedOrCanceledPrestation();
    }

    public Set<SinisterPrestation> findAllSinisterPrestationClosedOrCanceledGroupByTug(Long tugId) {
        return sinisterPrestationRepository.findAllSinisterPrestationClosedOrCanceledGroupByTug(tugId);
    }

    public Set<SinisterPrestation> findAllSinisterPrestationClosedOrCanceledGroupByPartner(Long partnerId) {
        return sinisterPrestationRepository.findAllSinisterPrestationClosedOrCanceledGroupByPartner(partnerId);
    }

    public XSSFWorkbook getStatementExcelExport(Long id, String month) throws IOException, InvalidFormatException {
    	log.debug("Request to generate statement excel for company {} and month {}", id, month);
    	// Calculate period 
    	YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("MMyyyy"));
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atDay(1);
        endDate = endDate.plusMonths(1).minusDays(1);
        LocalDateTime startDate1 = startDate.atStartOfDay();
        LocalDateTime endDate1 = endDate.atStartOfDay();
        log.debug("Getting statement from {} to {}", startDate, endDate);

    	XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("bordereau");
        PartnerDTO partner = partnerService.findOne(id);
        Set<SinisterPrestation> prestations = sinisterPrestationRepository.findAllSinisterPrestationClosedOrCanceledGroupByPartner(id, startDate1, endDate1);
        int rownum = 0;
        int rowIndex = 0;
        long duration = 0l;
        String durationLabel = "";
        double ttc = 0d;
        Cell cell;
        Row row;
        //
        XSSFCellStyle dataStyle = ExcelUtil.createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForMerge = ExcelUtil.createStyleForDataBlack(workbook);
        dataStyleForMerge.setAlignment(HorizontalAlignment.CENTER);
        dataStyleForMerge.setVerticalAlignment(VerticalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;

        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Logo GA");
        cell.setCellStyle(dataStyleForMerge);
        sheet.addMergedRegion(new CellRangeAddress(1, 5, 1, 4));

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("IMPRIMES Bordereau " + partner.getCompanyName());
        cell.setCellStyle(dataStyleForMerge);
        sheet.addMergedRegion(new CellRangeAddress(1, 5, 5, 15));

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Date d'application : 13/11/2016");
        cell.setCellStyle(dataStyleForMerge);
        sheet.addMergedRegion(new CellRangeAddress(1, 5, 16, 19));

        rownum = rownum + 5;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Logo partenaire");
        cell.setCellStyle(dataStyleForMerge);
        sheet.addMergedRegion(new CellRangeAddress(6, 10, 1, 4));

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Bordereau " + partner.getCompanyName() + LocalDate.now().format(DateTimeFormatter.ofPattern("MM - yyyy")));
        cell.setCellStyle(dataStyleForMerge);
        sheet.addMergedRegion(new CellRangeAddress(6, 10, 5, 19));

        rownum = rownum + 5;
        row = sheet.createRow(rownum);
        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("N°");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date de survenance");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("J/N/F");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Délai d'intervention");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Référence Prestation");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Nom & Prenom");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("N° Tel Assuré");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Numéro du contrat");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Pack");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Nature de l'événement");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Immatriculation");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Gouvernorat du Sinistre");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Ville du sinistre");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Gouvernorat de destination");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Ville de destination");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("KLM");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Périm");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Type de service");
        cell.setCellStyle(dataStyle);

        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Montant TND");
        cell.setCellStyle(dataStyle);

        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(prestations)) {
            // Policies          
            for (SinisterPrestation prestation : prestations) {
                rownum++;
                rowIndex++;
				durationLabel = "";
                ttc += prestation.getPriceTtc() != null ? prestation.getPriceTtc().doubleValue() : 0d;
                if (prestation.getTugAssignmentDate() != null && prestation.getTugArrivalDate() != null) {
                    LocalDateTime tempDateTime = LocalDateTime.from(prestation.getTugAssignmentDate());
                    duration = tempDateTime.until(prestation.getTugArrivalDate(), ChronoUnit.SECONDS);
                    long hours = duration / 3600;
                    long mins = (duration % 3600) / 60;
                    durationLabel += hours != 0 ? hours + "h" : "";
                    durationLabel += mins + "min";
                }
                Sinister sinister = prestation.getSinister();

                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.NUMERIC);
                cell.setCellValue(rowIndex);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(sinister.getIncidentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cell.setCellStyle(dataStyle);

                String jnf = "J";
                if (prestation.isNight()) {
                    jnf += "/N";
                }
                if (prestation.isHolidays()) {
                    jnf += "/F";
                }
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(jnf);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(durationLabel);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(sinister.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(sinister.getVehicle() != null ? sinister.getVehicle().getInsured().getFullName() : "--");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(sinister.getPhone());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(sinister.getContract().getNumeroContrat());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(sinister.getVehicle() != null && sinister.getVehicle().getPack() != null ? sinister.getVehicle().getPack().getLabel() : "");
                cell.setCellStyle(dataStyle);

                String typea = "A";
                if (sinister.getNature().equals(NatureIncident.PANNE.name())) {
                    typea = "P";
                }
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(typea);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(sinister.getVehicle() != null ? sinister.getVehicle().getImmatriculationVehicule() : "--");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(prestation.getIncidentGovernorate().getLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(prestation.getIncidentLocation().getLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(prestation.getDestinationGovernorate().getLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(prestation.getDestinationLocation().getLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(prestation.getMileage() != null ? prestation.getMileage() : 0D);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(prestation.getServiceType().getNom());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(19, CellType.STRING);
                cell.setCellValue(prestation.getPriceTtc());
                cell.setCellStyle(dataStyle);
            }

            rownum++;
            row = sheet.createRow(rownum);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Total des Prestations Fournies Du mois/cumulées");
            cell.setCellStyle(dataStyleForMerge);
            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 17));

            cell = row.createCell(18, CellType.NUMERIC);
            cell.setCellValue(rowIndex);
            cell.setCellStyle(dataStyle);

            cell = row.createCell(19, CellType.NUMERIC);
            cell.setCellValue(rowIndex);
            cell.setCellStyle(dataStyle);

            rownum++;
            row = sheet.createRow(rownum);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Total du Coût des Prestations Fournies Du mois/ Cumulées");
            cell.setCellStyle(dataStyleForMerge);
            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 17));

            cell = row.createCell(18, CellType.NUMERIC);
            cell.setCellValue(ttc);
            cell.setCellStyle(dataStyle);

            cell = row.createCell(19, CellType.NUMERIC);
            cell.setCellValue(ttc);
            cell.setCellStyle(dataStyle);

        }

        return workbook;

    }

    /**
     * Regenerate statement pdf for tug
     * @param tugId
     * @param month
     */
    public void regenerateStatement(Long tugId, String month, String statementName) {
        log.info("Traitement du remorqueur {}", tugId);
        Double stamped = 0.600d;
        
        // Calculate period 
    	YearMonth ym = YearMonth.parse(month, DateTimeFormatter.ofPattern("MM-yyyy"));
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atDay(1);
        endDate = endDate.plusMonths(1).minusDays(1);
        LocalDateTime startDate1 = startDate.atStartOfDay();
        LocalDateTime endDate1 = endDate.atStartOfDay();
        log.debug("Getting statement from {} to {}", startDate, endDate);
        
        StatementFtlDTO statement = new StatementFtlDTO();
        Global global = globalRepository.findOne(1L);

        RefRemorqueur remorqueur = remorqueurRepository.getOne(tugId);

        if (remorqueur != null) {
            statement.setIdTug(remorqueur.getId());
            statement.setSocialReason(StringUtils.defaultIfBlank(remorqueur.getSociete().getRaisonSociale(), ""));
            statement.setTaxRegistration(StringUtils.defaultIfBlank(remorqueur.getSociete().getMatriculeFiscale(), ""));
            statement.setAdress(StringUtils.defaultIfBlank(remorqueur.getSociete().getAdresse(), ""));
            statement.setTel(StringUtils.defaultIfBlank(remorqueur.getTelephone(), ""));
            statement.setVat(remorqueur.getSociete() != null && remorqueur.getSociete().getTva() != null && remorqueur.getSociete().getTva().getVatRate() != null ? remorqueur.getSociete().getTva().getVatRate().doubleValue() : 0D);
            statement.setStamped(stamped);
            statement.setCode(remorqueur.getSociete().getCodeCategorie());
            Integer serial = statementRepository.findCountStatement(LocalDate.now().getYear());
            if (serial < 10) {
                statement.setInvoiceReference(LocalDateTime.now().getYear() + "/" + statement.getCode() + "/00" + serial);
            } else if (serial < 100) {
                statement.setInvoiceReference(LocalDateTime.now().getYear() + "/" + statement.getCode() + "/0" + serial);
            } else {
                statement.setInvoiceReference(LocalDateTime.now().getYear() + "/" + statement.getCode() + "/" + serial);
            }
        }

        statement.setAdressGA(StringUtils.defaultIfBlank(global.getAddress(), ""));
        statement.setTaxRegistrationGA(StringUtils.defaultIfBlank(global.getTaxIdentificationNumber(), ""));
        statement.setTelGA(global.getPhone() != null ? global.getPhone().toString() : "");
        Set<SinisterPrestationDTO> dtos = new HashSet<>();
        Map<Long, CompanyFtlDTO> companies = new HashMap<>();
        statement.setTht(0D);
        statement.setTtc(statement.getStamped());
        //statement.setStamped(0.600);
        Set<SinisterPrestation> listPrestation = sinisterPrestationRepository.findAllSinisterPrestationClosedGroupByTug(tugId, startDate1, endDate1);
        if (CollectionUtils.isNotEmpty(listPrestation)) {
            for (SinisterPrestation prestation : listPrestation) {
                Long companyId = prestation.getSinister().getPartner().getId();
                if (prestation.getPriceHt() != null && !prestation.getPriceHt().equals(0D)) {
                    if (!companies.containsKey(companyId)) {
                        CompanyFtlDTO cmp = new CompanyFtlDTO();
                        cmp.setCompanyName(prestation.getSinister().getPartner().getCompanyName());
                        cmp.setTotalHt(0D);
                        companies.put(companyId, cmp);
                    }

                    statement.addTht(prestation.getPriceHt());
                    statement.addTtc(prestation.getPriceTtc());
                    companies.get(companyId).addTotalHt(prestation.getPriceHt());

                    SinisterPrestationDTO dto = new SinisterPrestationDTO();
                    dto.setId(prestation.getId());
                    dto.setContractNumber(prestation.getSinister().getContract().getNumeroContrat());
                    dto.setReference(prestation.getSinister().getReference());
                    dto.setInsuredName(prestation.getSinister().getVehicle().getInsured().getFullName());
                    dto.setVehicleRegistration(prestation.getSinister().getVehicle().getImmatriculationVehicule());
                    dto.setIncidentLocationLabel(prestation.getIncidentLocation() != null ? prestation.getIncidentLocation().getLabel() : "--");
                    dto.setDestinationLocationLabel(prestation.getDestinationLocation() != null ? prestation.getDestinationLocation().getLabel() : "--");
                    dto.setMileage(prestation.getMileage() != null ? prestation.getMileage() : 0D);
                    dto.setServiceTypeLabel(prestation.getServiceType().getNom());
                    dto.setInsuredFirstTel(prestation.getSinister().getVehicle().getInsured().getPremTelephone());
                    dto.setPriceTtc(prestation.getPriceTtc() != null ? prestation.getPriceTtc() : 0D);
                    dto.setPriceHt(prestation.getPriceHt() != null ? prestation.getPriceHt() : 0D);
                    dto.setStringIncidentDate(prestation.getSinister().getIncidentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    dto.setPartnerName(prestation.getSinister().getPartner().getCompanyName());
                    companies.get(companyId).addPrestation(dto);
                    dtos.add(dto);
                }
            }
        }
        statement.getCompanies().addAll(companies.values());
        statement.setListSinisterPrestation(dtos);
        try {
            statement.setLogo64(properties.getLogo());
            statement.setSlogon64(properties.getSlogon());
            this.generateStatementPdf(statement, "statement-batch-template.ftl", properties.getStatementRoot(), statementName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generateStatementPdf(StatementFtlDTO statementDto, String template, String path, String statementName) throws Exception {
        // Using a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        Map<String, Object> model = new HashMap<>();
        model.put("statement", statementDto);
        Template t = freemarkerConfig.getTemplate(template);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        Document document = new Document(PageSize.A4, 10, 10, 60, 60);
        //Document document = new Document(PageSize.A4, 110, 110, 120, 140);
        try {
            path += File.separator + "T" + statementDto.getIdTug();
            //String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-yyyy")) + ".pdf";
            String fileName = statementName;
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(path + File.separator + fileName);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(html));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

    }

}
