/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.util;
import com.gaconnecte.auxilium.Utils.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.gaconnecte.auxilium.service.dto.AssitancesDTO;
import com.gaconnecte.auxilium.service.dto.PriseEnChargeDTO;
import com.gaconnecte.auxilium.service.dto.ReportFollowUpAssistanceDTO;
import com.gaconnecte.auxilium.service.dto.ReportFrequencyRateDTO;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gaconnecte.auxilium.Utils.Constants;
import com.gaconnecte.auxilium.domain.ViewExtractPiece;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.domain.enumeration.NatureIncident;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.UserAccessDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.VehiclePieceDTO;
import com.gaconnecte.auxilium.service.dto.ViewContratDTO;
import com.gaconnecte.auxilium.service.dto.ViewExtractPieceDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterDTO;
import com.gaconnecte.auxilium.service.dto.ViewSinisterPrestationDTO;
import com.gaconnecte.auxilium.service.dto.ViewSouscriptionGaDTO;
import com.gaconnecte.auxilium.service.dto.ViewVehiclePiecesDTO;

/**
 *
 * @author hannibaal
 */
public class ExcelUtil {

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.LIGHT_ORANGE.getIndex());

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.LIGHT_ORANGE.getIndex());

        return style;
    }

    private static HSSFCellStyle createStyleForData(HSSFWorkbook workbook) {

        HSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        return style;
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.LIGHT_ORANGE.getIndex());

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.LIGHT_ORANGE.getIndex());

        return style;
    }

    private static XSSFCellStyle createStyleForSubTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.LIGHT_ORANGE.getIndex());

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.LIGHT_ORANGE.getIndex());

        return style;
    }

    private static XSSFCellStyle createStyleForData(XSSFWorkbook workbook) {

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        return style;
    }
    public static XSSFCellStyle createStyleForDataBlack(XSSFWorkbook workbook) {

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        //font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);

        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

        return style;
    }

    public static HSSFWorkbook getExcelExport(Set<SinisterPrestationDTO> prestations, Date date) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Suivi");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle headerStyle = createStyleForTitle(workbook);
        HSSFCellStyle headerStyleForHour = createStyleForTitle(workbook);
        headerStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        HSSFCellStyle dataStyle = createStyleForData(workbook);
        HSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Date : " + sdf.format(date));
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 25));

        rownum++;
        row = sheet.createRow(rownum);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Libellé");
        cell.setCellStyle(headerStyle);

        for (int i = 0; i < 15; i++) {
            cell = row.createCell(i + 2, CellType.STRING);
            cell.setCellValue((i < 10 ? "H0" : "H") + i);
            cell.setCellStyle(headerStyleForHour);
        }

        // Data
        for (SinisterPrestationDTO prestation : prestations) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(prestation.getReference());
            cell.setCellStyle(dataStyle);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(prestation.getVehicleRegistration());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(prestation.getInsuredName());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(prestation.getPartnerName());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(prestation.getPack());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(prestation.getUsage());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(prestation.getServiceTypeLabel());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(sdf.format(prestation.getIncidentDate()));
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(prestation.getIncidentNature());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(prestation.getIncidentMonth());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue(prestation.getAffectedTugLabel());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(prestation.getDmiStr());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(prestation.getIncidentLocationLabel());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(prestation.getDestinationLocationLabel());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue("--");
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(prestation.getPriceTtc());
            cell.setCellStyle(dataStyleForHour);

            cell = row.createCell(17, CellType.STRING);
            cell.setCellValue(prestation.getStatusLabel());
            cell.setCellStyle(dataStyleForHour);
        }

//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//            byte[] barray = bos.toByteArray();
//            return new ByteArrayInputStream(barray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } 
        return workbook;
    }

    public static XSSFWorkbook getUserExcelExport(UserExtraDTO user) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Suivi");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForData(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Rapport Utilisateur");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

        rownum++;
        row = sheet.createRow(rownum);

        // Date
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Date du rapport : ");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(sdf.format(new Date()));
        cell.setCellStyle(dataStyle);

        // Lest and first name
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Nom & Prénom : ");
        cell.setCellStyle(subHeaderStyle);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(user.getLastName() + " " + user.getFirstName());
        cell.setCellStyle(dataStyle);

        // Email
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Email : ");
        cell.setCellStyle(subHeaderStyle);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(user.getEmail());
        cell.setCellStyle(dataStyle);

        // Profil
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Profil : ");
        cell.setCellStyle(subHeaderStyle);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(user.getProfileName());
        cell.setCellStyle(dataStyle);

        // Actif
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Actif : ");
        cell.setCellStyle(subHeaderStyle);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(user.getActivated() ? "Oui" : "Non");
        cell.setCellStyle(dataStyle);

        if (CollectionUtils.isNotEmpty(user.getAccesses())) {
            // Access
            rownum++;
            row = sheet.createRow(rownum);
            //row.setHeightInPoints(2f);
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Liste des accès");
            cell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 2));

            for (UserAccessDTO access : user.getAccesses()) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(access.getElementMenuLabel());
                cell.setCellStyle(subHeaderStyle);
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(access.getLabel());
                cell.setCellStyle(dataStyle);
            }
        }

        /*FileOutputStream fileOut = new FileOutputStream("D:\\issam.xlsx");
	workbook.write(fileOut);
	workbook.close();
	fileOut.flush();
	fileOut.close();*/
        // Data
        /*for (SinisterPrestationDTO prestation : prestations) {
            rownum++;
            row = sheet.createRow(rownum);
 
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(prestation.getReference());
            cell.setCellStyle(dataStyle);
            

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(prestation.getIncidentNature() );
            cell.setCellStyle(dataStyleForHour);

        }*/
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            workbook.write(bos);
//            byte[] barray = bos.toByteArray();
//            return new ByteArrayInputStream(barray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } 
        return workbook;
    }

    public static XSSFWorkbook getPoliciesExcelExport(List<ViewPolicyDTO> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("policies");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des contrats");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Numéro Contrat");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Compagnie");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Agence");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Véhicule");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Assuré");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Début Validité");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Fin Validité");
        cell.setCellStyle(subHeaderStyle);

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewPolicyDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getPolicyNumber());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getCompanyName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getAgencyName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getRegistrationNumber());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getPolicyHolderName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getStartDate() != null ? policy.getStartDate().format(formatter) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getEndDate() != null ? policy.getEndDate().format(formatter) : "");
                cell.setCellStyle(dataStyle);

            }
        }

        return workbook;
    }
    
    public static XSSFWorkbook getSouscriptionsExcelExport(List<ViewContratDTO> policies) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("souscriptions");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Indicateur souscription");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Agent");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Usage");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Nature pack filtré");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Zone filtrée");
        cell.setCellStyle(subHeaderStyle);

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewContratDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getNomAgentAssurance());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getUsageLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getNbrePack());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getZone());
                cell.setCellStyle(dataStyle);
            }
        }

        return workbook;
    }

    public static XSSFWorkbook getSouscriptionsGaExcelExport(List<ViewSouscriptionGaDTO> policies) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Souscriptions");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Chiffre d'affaire souscription GA");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

        rownum++;
        row = sheet.createRow(rownum);
 
        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Compagnie");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Numéro du contrat");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Zone agent");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Pack souscrit");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Prime HT GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Prime TTC GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Part réassureur");
        cell.setCellStyle(subHeaderStyle);

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewSouscriptionGaDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getCompagnie());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getNumeroContrat());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getZone() != null ? policy.getZone() : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getPack() != null ? policy.getPack() : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getPrimeHt() != null ? policy.getPrimeHt() : 0L);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getPrimeTtc() != null ? policy.getPrimeTtc() : 0L);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getPartRea() != null ? policy.getPartRea() : 0L);
                cell.setCellStyle(dataStyle);

            }
        }

        return workbook;
    }

    public static XSSFWorkbook getStatementExcelExport(Set<SinisterPrestation> prestations) throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("policies");
        int rownum = 0;
        int rowIndex = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des contrats");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

        rownum++;
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

        if (CollectionUtils.isNotEmpty(prestations)) {
            // Policies          
            for (SinisterPrestation prestation : prestations) {
                rownum++;
                rowIndex++;
                Sinister sinister = prestation.getSinister();
                
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.NUMERIC);
                cell.setCellValue(rowIndex);
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(sinister.getIncidentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cell.setCellStyle(dataStyle);
                
                String jnf = "J";
                if(prestation.isNight()) jnf += "/N";
                if(prestation.isHolidays()) jnf += "/F";
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(jnf);
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue("DI_");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(sinister.getReference());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue("Nom et prenom");
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
                if(sinister.getNature().equals(NatureIncident.PANNE.name())) typea = "P";
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(typea);
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(sinister.getVehicle().getImmatriculationVehicule());
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
                cell.setCellValue(prestation.getMileage());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue("");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(prestation.getServiceType().getNom());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(19, CellType.STRING);
                cell.setCellValue(prestation.getPriceHt());
                cell.setCellStyle(dataStyle);
            }
        }

        return workbook;
     
    }
    
    public static XSSFWorkbook getAssitancesExcelExport(List<AssitancesDTO> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("assistances");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des assistances");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 19));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date de Création");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Date de survenance");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Nature de Survenance");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Ville de l'incident");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Ville de destination");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Nom de la Compagnie");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Nom agent assurance");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Numéro du contrat");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Marque");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Immatriculation assuré");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Nom et Prénom-Raison Sociale");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Type de service");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Date Affectation Remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Date Arrivé Remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Date Arrivé Assuré");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Kilométrage ");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Montant TTC ");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(20, CellType.STRING);
        cell.setCellValue("Etat prestation");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(21, CellType.STRING);
        cell.setCellValue("Chargé");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(22, CellType.STRING);
        cell.setCellValue("Motifs");
        cell.setCellStyle(subHeaderStyle); 
       

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (AssitancesDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getCreationDate() != null ? policy.getCreationDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getIncidentDate() != null ? policy.getIncidentDate().format(formatter) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getIncidentNature());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getVilleSinister());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getVilleDestination());
                cell.setCellStyle(dataStyle);
                
                
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getCompanyName());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getNomAgentAssurance());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(policy.getNumeroContrat());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(policy.getMarque());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(policy.getImmatriculationVehicule());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(policy.getNomPrenomRaison());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(policy.getTypeService());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(policy.getRemorqueur());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(policy.getTugAssignmentDate() != null ? policy.getTugAssignmentDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(policy.getTugArrivalDate() != null ? policy.getTugArrivalDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue(policy.getInsuredArrivalDate() != null ? policy.getInsuredArrivalDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(policy.getKilometrage() != null ? String.valueOf(policy.getKilometrage()) : "");
                cell.setCellStyle(dataStyle); 
                
                cell = row.createCell(19, CellType.STRING);
                cell.setCellValue(policy.getPrice_ttc() != null ? String.valueOf(policy.getPrice_ttc()) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(20, CellType.STRING);
                cell.setCellValue(policy.getEtatPrestation());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(21, CellType.STRING);
                cell.setCellValue(policy.getCharge());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(22, CellType.STRING);
                cell.setCellValue(policy.getMotif());
                cell.setCellStyle(dataStyle);

              
            }
        }

        return workbook;
    }
    
    
    
    
    public static XSSFWorkbook getPrestationExcelExport(List<ViewSinisterPrestationDTO> policies, Long status) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("assistances");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);
        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des prestations");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 16));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date de Création");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Date de survenance");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Immatriculation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Assuré");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Type de prestation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Lieu du sinister");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Destination");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Date d'affectation remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Date d'arrivé du remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Date d'arrivé de l'assuré");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Montant TND");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Nom du chargé");
        cell.setCellStyle(subHeaderStyle); 
        
        
        if(status == Constants.STATUS_CANCELED) {

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Motif d'annulation");
        cell.setCellStyle(subHeaderStyle); 
        }
        
        if(status == Constants.STATUS_NOTELIGIBLE) {

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Motif de non éligibilité");
        cell.setCellStyle(subHeaderStyle); 
        }
      
        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewSinisterPrestationDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getCreationDate() != null ? policy.getCreationDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getIncidentDate() != null ? policy.getIncidentDate().format(formatter) : "");
                cell.setCellStyle(dataStyle);
                
      
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getRegistrationNumber());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getFullName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getServiceType());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getIncidentLocationLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getDestinationLocationLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(policy.getAffectedTugLabel());
                cell.setCellStyle(dataStyle);
                            
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(policy.getTugAssignmentDate() != null ? policy.getTugAssignmentDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(policy.getTugArrivalDate() != null ? policy.getTugArrivalDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(policy.getInsuredArrivalDate() != null ? policy.getInsuredArrivalDate().format(formatter1) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(policy.getPriceTtc() != null ? String.valueOf(policy.getPriceTtc()) : "");
                cell.setCellStyle(dataStyle);
               
            	cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(policy.getCharge());
                cell.setCellStyle(dataStyle);
                
                if(status == Constants.STATUS_CANCELED) {
                	cell = row.createCell(15, CellType.STRING);
                    cell.setCellValue(policy.getCancelGroundsDescription());
                    cell.setCellStyle(dataStyle);
                }
                
                
                if(status == Constants.STATUS_NOTELIGIBLE) {
                	cell = row.createCell(16, CellType.STRING);
                    cell.setCellValue(policy.getNotEligibleGroundsDescription());
                    cell.setCellStyle(dataStyle);
                }

                
            }
        }

        return workbook;
    }
    
    
    public static XSSFWorkbook getReportTugPerformanceExcelExport(List<ReportTugPerformanceDTO> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("PerformanceRemorqueur");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des assistances");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 20));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Remorqueur affecté");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Date de création");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Immatriculation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Partenaire");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Type de service");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Usage");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Date de l'affectation du remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Date d'arrivée du remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Date d'arrivée de l'assuré");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Délai d'intervention");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Délai opération");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Habillage");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Lieu du sinistre");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Coût de la Prestation");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Motif d'annulation ");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Date d'annulation");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Date de cloture");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Chargé de création");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(20, CellType.STRING);
        cell.setCellValue("Chargé de cloture");
        cell.setCellStyle(subHeaderStyle); 
      

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ReportTugPerformanceDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getAffectedTugLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getReference());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getCreationDate()!= null ? String.valueOf(policy.getCreationDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getRegistrationNumber());
                cell.setCellStyle(dataStyle);

                
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getPartnerName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getServiceTypeLabel());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getUsageLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getTugAssignmentDate()!= null ? String.valueOf(policy.getTugAssignmentDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(policy.getTugArrivalDate()!= null ? String.valueOf(policy.getTugArrivalDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(policy.getInsuredArrivalDate()!= null ? String.valueOf(policy.getInsuredArrivalDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(policy.getInterventionTimeAvg()!= null ? String.valueOf(policy.getInterventionTimeAvg()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(policy.getDelaiOperation());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(policy.getHasHabillage() == false ? "Non" : "Oui");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(policy.getIncidentGovernorateLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(policy.getPriceTtc() != null ? String.valueOf(policy.getPriceTtc()) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(policy.getCancelGroundsDescription());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue(policy.getCancelDate()!= null ? String.valueOf(policy.getCancelDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(policy.getClosureDate()!= null ? String.valueOf(policy.getClosureDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(19, CellType.STRING);
                cell.setCellValue(policy.getCreationUser());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(20, CellType.STRING);
                cell.setCellValue(policy.getClosureUser());
                cell.setCellStyle(dataStyle);
                

              
            }
        }

        return workbook;
    }
    
    public static XSSFWorkbook getSinisterExcelExport(List<ViewSinisterDTO> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("dossier Sinister");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des assistances");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

        rownum++;
        row = sheet.createRow(rownum);

        // Header

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date de création");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Immatriculation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Conducteur/Assuré");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Nature de l'événement");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Type de service");
        cell.setCellStyle(subHeaderStyle);

       
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Date de création");
        cell.setCellStyle(subHeaderStyle); 
 
      

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewSinisterDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getIncidentDate()!= null ? String.valueOf(policy.getIncidentDate()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getRegistrationNumber());
                cell.setCellStyle(dataStyle);
                
             
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getFullName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getNature());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getServiceTypes());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getCreationDate()!= null ? String.valueOf(policy.getCreationDate()) : "");
                cell.setCellStyle(dataStyle);
               
              

              
            }
        }

        return workbook;
    }
    
    public static XSSFWorkbook getReport1ServicesExcelExport(List<ReportFollowUpAssistanceDTO> policies) throws IOException {
      
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("dossier Sinister");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Rapport Suivi du service assistance");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 32));

        rownum++;
        row = sheet.createRow(rownum);

        // Header

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date de création");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Immatriculation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Conducteur/Assuré");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Partnaire");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Pack");
        cell.setCellStyle(subHeaderStyle);

       
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Usage");
        cell.setCellStyle(subHeaderStyle); 
 
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Type de service");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Date de l'incident");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Nature de l'incident");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Mois de l'incident");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Remorqueur affecté");
        cell.setCellStyle(subHeaderStyle); 
        
        
        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Date de l'affectation du remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Date d'arrivée du remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Date d'arrivée de l'assuré");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Délai d'intervention");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Lieu du sinistre");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Destination");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Kilomètrage");
        cell.setCellStyle(subHeaderStyle); 
        
        
        cell = row.createCell(20, CellType.STRING);
        cell.setCellValue("Nombre de Services");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(21, CellType.STRING);
        cell.setCellValue("Coût de la Prestation");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(22, CellType.STRING);
        cell.setCellValue("Délai opération");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(23, CellType.STRING);
        cell.setCellValue("Etat de la prestation");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(24, CellType.STRING);
        cell.setCellValue("Chargé de création");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(25, CellType.STRING);
        cell.setCellValue("Chargé de cloture");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(26, CellType.STRING);
        cell.setCellValue("PL (Poids Lourd)");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(27, CellType.STRING);
        cell.setCellValue("Férié");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(28, CellType.STRING);
        cell.setCellValue("Nuit");
        cell.setCellStyle(subHeaderStyle); 
       
        cell = row.createCell(29, CellType.STRING);
        cell.setCellValue("Demi majoré");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(30, CellType.STRING);
        cell.setCellValue("4 Portes F");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(31, CellType.STRING);
        cell.setCellValue("4 Portes K");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(32, CellType.STRING);
        cell.setCellValue("Zone remorqueur");
        cell.setCellStyle(subHeaderStyle); 
        
        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ReportFollowUpAssistanceDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getReference()!= null ? policy.getReference() : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getCreationDate() != null ? policy.getCreationDate().format(formatter) : "");

                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getRegistrationNumber());
                cell.setCellStyle(dataStyle);
                
             
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getFullName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getPartnerName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getPackLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getUsageLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getServiceType());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(policy.getIncidentDate() != null ? String.valueOf(policy.getIncidentDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(policy.getIncidentNature());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(policy.getIncidentMonth());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(policy.getAffectedTugLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(policy.getTugAssignmentDate() != null ? policy.getTugAssignmentDate().format(formatter1) : "");

                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(policy.getTugArrivalDate() != null ? policy.getTugArrivalDate().format(formatter1) : "");

                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(policy.getInsuredArrivalDate() != null ? policy.getInsuredArrivalDate().format(formatter1) : "");

                cell.setCellStyle(dataStyle);
                
              
                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(policy.getInterventionTimeAvg());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue(policy.getIncidentLocationLabel());
                cell.setCellStyle(dataStyle);
                
               
                
                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(policy.getDestinationLocationLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(19, CellType.STRING);
                cell.setCellValue(policy.getMileage()!= null ? String.valueOf(policy.getMileage()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(20, CellType.STRING);
                cell.setCellValue(policy.getPrestationCount()!= null ? String.valueOf(policy.getPrestationCount()) : "");
                cell.setCellStyle(dataStyle);
                
                
                cell = row.createCell(21, CellType.STRING);
                cell.setCellValue(policy.getPriceTtc()!= null ? String.valueOf(policy.getPriceTtc()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(22, CellType.STRING);
                cell.setCellValue(policy.getInterventionTimeAvgMin());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(23, CellType.STRING);
                cell.setCellValue(policy.getStatusLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(24, CellType.STRING);
                cell.setCellValue(policy.getCreationUser());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(25, CellType.STRING);
                cell.setCellValue(policy.getClosureUser());
                cell.setCellStyle(dataStyle);
            
                cell = row.createCell(26, CellType.STRING);
                cell.setCellValue(policy.getHeavyWeights());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(27, CellType.STRING);
                cell.setCellValue(policy.getHolidays());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(28, CellType.STRING);
                cell.setCellValue(policy.getNight());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(29, CellType.STRING);
                cell.setCellValue(policy.getHalfPremium());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(30, CellType.STRING);
                cell.setCellValue(policy.getFourPorteF());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(31, CellType.STRING);
                cell.setCellValue(policy.getFourPorteK());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(32, CellType.STRING);
                cell.setCellValue(policy.getTugGovernorateLabel());
                cell.setCellStyle(dataStyle);
            
            }
        }

        return workbook;
    }
    
    public static XSSFWorkbook getReport2ServiceExcelExport(List<ReportFrequencyRateDTO> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("PerformanceRemorqueur");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des assistances");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Partenaire");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Usage");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Nature de l'incident");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Type de service");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Taux de fréquence");
        cell.setCellStyle(subHeaderStyle);
        
        

        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ReportFrequencyRateDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getPartnerName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getUsageLabel());
                cell.setCellStyle(dataStyle);
         
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getIncidentNature());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getTypeService());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getFrequencyRate());
                cell.setCellStyle(dataStyle);

 
              
            }
        }

        return workbook;
    }
    
    public static XSSFWorkbook getVehiculePiece(List<ViewVehiclePiecesDTO> vehiculesPieces) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("VehiculePiece");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des pièces");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Réference");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Désignation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Source");
        cell.setCellStyle(subHeaderStyle);
        

        if (CollectionUtils.isNotEmpty(vehiculesPieces)) {
            // Policies          
            for (ViewVehiclePiecesDTO vehiculePiece : vehiculesPieces) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(vehiculePiece.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(vehiculePiece.getLabel());
                cell.setCellStyle(dataStyle);
         
                cell = row.createCell(3, CellType.STRING);
                if((new String("REPARATOR")).equals(vehiculePiece.getSource()) || vehiculePiece.getSource() == null) {
                	cell.setCellValue("Réparateur");
                }else {
                	cell.setCellValue(vehiculePiece.getSource());
                }
                cell.setCellStyle(dataStyle);
 
              
            }
        }

        return workbook;
    }
    

    public static XSSFWorkbook getPrestationVrExcelExport(List<ViewSinisterPrestationDTO> policies, Long status) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("assistances");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);
        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des prestations");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 12));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date de Création");
        cell.setCellStyle(subHeaderStyle);


        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Immatriculation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Assuré");
        cell.setCellStyle(subHeaderStyle);


        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Lieu du sinister");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Lieu de livraison");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Nombre de jour");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Prix TTC");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Loueur");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Date acquisition");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Nom du chargé");
        cell.setCellStyle(subHeaderStyle); 
        
        if(status == Constants.STATUS_CLOSED) {
        	  cell = row.createCell(12, CellType.STRING);
              cell.setCellValue("Date retour");
              cell.setCellStyle(subHeaderStyle);            
            
      }
      if(status == Constants.STATUS_CANCELED) {
    	  cell = row.createCell(12, CellType.STRING);
          cell.setCellValue("Motif d'annulation");
          cell.setCellStyle(subHeaderStyle);                   
      }
             
      if(status == Constants.STATUS_REFUSED) {

    	  cell = row.createCell(12, CellType.STRING);
          cell.setCellValue("Motif Refus");
          cell.setCellStyle(subHeaderStyle); 
          
          
      }
     
      
        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewSinisterPrestationDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getCreationDate() != null ? String.valueOf(policy.getCreationDate().format(formatter))  : "");
                cell.setCellStyle(dataStyle);
                
               
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getRegistrationNumber());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getFullName());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
               // cell.setCellValue(policy.getIncidentGovernorateLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(6, CellType.STRING);
               // cell.setCellValue(policy.getDeliveryGovernorateLabel());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(7, CellType.STRING);
               // cell.setCellValue(policy.getDays()!= null ? String.valueOf(policy.getDays()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getPriceTtc() != null ? String.valueOf(policy.getPriceTtc()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(9, CellType.STRING);
               // cell.setCellValue(policy.getLoueurLabel());
                cell.setCellStyle(dataStyle);
                            
                cell = row.createCell(10, CellType.STRING);
               // cell.setCellValue(policy.getAcquisitionDate() != null ? String.valueOf(policy.getAcquisitionDate()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(policy.getCharge());
                cell.setCellStyle(dataStyle);
                
                if(status == Constants.STATUS_CLOSED) {
                	  cell = row.createCell(12, CellType.STRING);
                      //cell.setCellValue(policy.getConstantsReturnDate() != null ? String.valueOf(policy.getReturnDate()) : "");
                      cell.setCellStyle(dataStyle);
                      
                }
                if(status == Constants.STATUS_CANCELED) {
                	cell = row.createCell(12, CellType.STRING);
                    cell.setCellValue(policy.getCancelGroundsDescription());
                    cell.setCellStyle(dataStyle);
                }
                       
                if(status == Constants.STATUS_REFUSED) {

                	cell = row.createCell(12, CellType.STRING);
                    //cell.setCellValue(policy.getMotifRefusLabel());
                    cell.setCellStyle(dataStyle);
                   
                }
                
            
                
            }
        }

        return workbook;
    }
    public static XSSFWorkbook getExtractPieceExcelExport(List<ViewExtractPiece> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ExtractPiece");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);
        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Extract du piece");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 14));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Date Accident");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Date réparation ");
        cell.setCellStyle(subHeaderStyle);


        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Point de choc");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Reparateur");
        cell.setCellStyle(subHeaderStyle);


        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Zone");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("N° Chassis");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Immat");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Date de 1ère mise en circulation");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Marque ");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Type ");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Désignation pièce");
        cell.setCellStyle(subHeaderStyle); 
        
        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Réf pièce");
        cell.setCellStyle(subHeaderStyle);            
            
      
    	cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Nature pièce : Original/Adaptable");
        cell.setCellStyle(subHeaderStyle);                   
      
             

    	cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Prix HT  ");
        cell.setCellStyle(subHeaderStyle); 
          
          
      
     
      
        if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (ViewExtractPiece policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getDateAccident()!= null ? String.valueOf(policy.getDateAccident())  : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getOperationDate() != null ? String.valueOf(policy.getOperationDate())  : "");
                cell.setCellStyle(dataStyle);
                
               
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getPointChoc() != null ? policy.getPointChoc() : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getReparateur() != null ? policy.getReparateur() : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue( policy.getZone() != null ? policy.getZone() : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue( policy.getnChassis() != null ? policy.getnChassis() : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue( policy.getImmatriculation() != null ? policy.getImmatriculation() : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getDatePremCirculation() != null ? String.valueOf(policy.getDatePremCirculation()) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(policy.getMarque() != null ? policy.getMarque() : "");
                cell.setCellStyle(dataStyle);
                            
                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue( policy.getModele() != null ? policy.getModele() : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue( policy.getDesignationPiece() != null ? policy.getDesignationPiece() : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue( policy.getReferencePiece() != null ? policy.getReferencePiece() : "");
                cell.setCellStyle(dataStyle);
                      
                
                 cell = row.createCell(13, CellType.STRING);
                 cell.setCellValue(policy.getNaturePiece() != null ? policy.getNaturePiece() : "");
                 cell.setCellStyle(dataStyle);
                
                       

                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(policy.getTotalHt()!= null ? String.valueOf(policy.getTotalHt()) : "");
                cell.setCellStyle(dataStyle);
                   
                
                
            
                
            }
        }

        return workbook;
    }
    public static XSSFWorkbook getViewPriseEnChargeExcelExport(List<PriseEnChargeDTO> policies) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("PriseEnCharge");
        int rownum = 0;
        Cell cell;
        Row row;
        //
        XSSFCellStyle headerStyle = createStyleForTitle(workbook);
        XSSFCellStyle subHeaderStyle = createStyleForSubTitle(workbook);

        XSSFCellStyle dataStyle = createStyleForDataBlack(workbook);
        XSSFCellStyle dataStyleForHour = createStyleForData(workbook);
        dataStyleForHour.setAlignment(HorizontalAlignment.CENTER);

        row = sheet.createRow(rownum);
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Liste des Prise En Charge");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 39));

        rownum++;
        row = sheet.createRow(rownum);

        // Header
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Référence GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Référence du sinistre");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Date de survenance");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Date de déclaration");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Nom de la compagnie");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Nom de l'agent assurance");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Code agence");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Numéro du contrat");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Immatriculation assurée");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Nom et Prénom-Raison Sociale");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Téléphone");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Marque");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Nom de la compagnie adverse");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Immatriculation tiers");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Nom et Prénom-Raison Sociale (Tiers)");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Date de création");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Mode de gestion");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Cas de barème");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Position GA");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(20, CellType.STRING);
        cell.setCellValue("Points chocs");
        cell.setCellStyle(subHeaderStyle);
        
        
        cell = row.createCell(21, CellType.STRING);
        cell.setCellValue("Date du RDV");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(22, CellType.STRING);
        cell.setCellValue("Nature du choc");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(23, CellType.STRING);
        cell.setCellValue("Date de réception du véhicule");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(24, CellType.STRING);
        cell.setCellValue("Montant total devis");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(25, CellType.STRING);
        cell.setCellValue("Date génération APEC");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(26, CellType.STRING);
        cell.setCellValue("Date bon sortie");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(27, CellType.STRING);
        cell.setCellValue("Chargé client");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(28, CellType.STRING);
        cell.setCellValue("Réparateur");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(29, CellType.STRING);
        cell.setCellValue("Expert");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(30, CellType.STRING);
        cell.setCellValue("Responsable Pec");
        cell.setCellStyle(subHeaderStyle);
        
        
        cell = row.createCell(31, CellType.STRING);
        cell.setCellValue("Responsable contrôle technique");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(32, CellType.STRING);
        cell.setCellValue("État de la prestation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(33, CellType.STRING);
        cell.setCellValue("Motifs");
        cell.setCellStyle(subHeaderStyle);
       
        cell = row.createCell(34, CellType.STRING);
        cell.setCellValue("État d'approbation");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(35, CellType.STRING);
        cell.setCellValue("Étape de la prestation");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(36, CellType.STRING);
        cell.setCellValue("Date de la reprise");
        cell.setCellStyle(subHeaderStyle);

        cell = row.createCell(37, CellType.STRING);
        cell.setCellValue("Date prévue sortie");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(38, CellType.STRING);
        cell.setCellValue("Date de l'envoi de la demande");
        cell.setCellStyle(subHeaderStyle);
        
        cell = row.createCell(39, CellType.STRING);
        cell.setCellValue("Date d'acceptation du forme");
        cell.setCellStyle(subHeaderStyle);

               if (CollectionUtils.isNotEmpty(policies)) {
            // Policies          
            for (PriseEnChargeDTO policy : policies) {
                rownum++;
                row = sheet.createRow(rownum);            

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(policy.getReference());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(policy.getRefSinister());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(policy.getIncidentDate()!= null ? String.valueOf(policy.getIncidentDate().format(formatter1)) : "");
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(policy.getDeclarationDate()!= null ? String.valueOf(policy.getDeclarationDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(policy.getCompanyName());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(policy.getNomAgentAssurance());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(policy.getCodeAgentAssurance());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(policy.getNumeroContrat());
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(policy.getImmatriculationVehicule());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(policy.getRaisonSocialeAssure());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(policy.getNumberTeleInsured());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(policy.getMarque());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(13, CellType.STRING);
                cell.setCellValue(policy.getCompagnieAdverse());
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(14, CellType.STRING);
                cell.setCellValue(policy.getImmatriculationTiers());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(15, CellType.STRING);
                cell.setCellValue(policy.getRaisonSocialTiers());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(16, CellType.STRING);
                cell.setCellValue(policy.getCreationDate()!= null ? String.valueOf(policy.getCreationDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(17, CellType.STRING);
                cell.setCellValue(policy.getModeGestion());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(18, CellType.STRING);
                cell.setCellValue(policy.getCasbareme()!= null ? String.valueOf(policy.getCasbareme()) : "");
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(19, CellType.STRING);
                cell.setCellValue(policy.getPositionGa());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(20, CellType.STRING);
                cell.setCellValue(policy.getPointChock());
                cell.setCellStyle(dataStyle);
                
                
                cell = row.createCell(21, CellType.STRING);
                cell.setCellValue(policy.getDateRDVReparation()!= null ? String.valueOf(policy.getDateRDVReparation().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(22, CellType.STRING);
                cell.setCellValue(policy.getLightShock());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(23, CellType.STRING);
                cell.setCellValue(policy.getVehicleReceiptDate()!= null ? String.valueOf(policy.getVehicleReceiptDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(24, CellType.STRING);
                cell.setCellValue(policy.getMontantTotalDevis()!= null ? String.valueOf(policy.getMontantTotalDevis()) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(25, CellType.STRING);
                cell.setCellValue(policy.getImprimDate()!= null ? String.valueOf(policy.getImprimDate().format(formatter1)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(26, CellType.STRING);
                cell.setCellValue(policy.getBonDeSortie()!= null ? String.valueOf(policy.getBonDeSortie().format(formatter1)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(27, CellType.STRING);
                cell.setCellValue(policy.getChargeNom()+" "+policy.getChargePrenom());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(28, CellType.STRING);
                cell.setCellValue(policy.getReparateur());
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(29, CellType.STRING);
                cell.setCellValue(policy.getExpert());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(30, CellType.STRING);
                cell.setCellValue(policy.getBossNom()+" "+policy.getBossPrenom());
                cell.setCellStyle(dataStyle);
                
                
                cell = row.createCell(31, CellType.STRING);
                cell.setCellValue(policy.getResponsableNom()+" "+policy.getResponsablePrenom());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(32, CellType.STRING);
                cell.setCellValue(policy.getEtatPrestation());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(33, CellType.STRING);
                cell.setCellValue(policy.getMotifGeneral());
                cell.setCellStyle(dataStyle);
               
                cell = row.createCell(34, CellType.STRING);
                cell.setCellValue(policy.getApprovPec());
                cell.setCellStyle(dataStyle);

                cell = row.createCell(35, CellType.STRING);
                cell.setCellValue(policy.getEtapePrestation());
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(36, CellType.STRING);
                cell.setCellValue(policy.getDateReprise()!= null ? String.valueOf(policy.getDateReprise().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(37, CellType.STRING);
                cell.setCellValue(policy.getSignatureDate()!= null ? String.valueOf(policy.getSignatureDate().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

                cell = row.createCell(38, CellType.STRING);
                cell.setCellValue(policy.getDateEnvoiDemandePec()!= null ? String.valueOf(policy.getDateEnvoiDemandePec().format(formatter)) : "");
                cell.setCellStyle(dataStyle);
                
                cell = row.createCell(39, CellType.STRING);
                cell.setCellValue(policy.getDateAcceptationDeForme()!= null ? String.valueOf(policy.getDateAcceptationDeForme().format(formatter)) : "");
                cell.setCellStyle(dataStyle);

              
            }
        }

        return workbook;
    }
    
}
