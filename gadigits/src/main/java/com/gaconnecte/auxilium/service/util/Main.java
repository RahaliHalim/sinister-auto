/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author hannibaal
 */
public class Main {
    public static void main1(String[] args) throws Exception {
        System.out.println("____________________________________________________________");
        /*UserExtraDTO dto = new UserExtraDTO();
        dto.setFirstName("Issam");
        dto.setLastName("BEN ALI");
        dto.setEmail("issam.benali@gaconnecte.com");
        dto.setActivated(Boolean.TRUE);
        dto.setProfileName("Administrateur");
        Set<UserAccessDTO> accesses = new HashSet<>();
        accesses.add(new UserAccessDTO(1L, "Lister Expert"));
        accesses.add(new UserAccessDTO(2L, "Ajouter Expert"));
        accesses.add(new UserAccessDTO(3L, "Modifier Expert"));
        accesses.add(new UserAccessDTO(4L, "Lister  Réparateur"));
        accesses.add(new UserAccessDTO(5L, "Ajouter Rémorquer"));
        accesses.add(new UserAccessDTO(6L, "Ajouter hattachau"));
        dto.setAccesses(accesses);
        XSSFWorkbook xw = ExcelUtil.getUserExcelExport(dto);*/
        String[] aa = "03-2020.pdf".split("[.]");
        for (int i = 0; i < aa.length; i++) {
			System.out.println(aa[i]);
		}
    	YearMonth ym = YearMonth.parse("03-2020", DateTimeFormatter.ofPattern("MM-yyyy"));
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atDay(1);
        endDate = endDate.plusMonths(1).minusDays(1);
        System.out.println("____" + startDate + ", " + endDate);

    }
}
