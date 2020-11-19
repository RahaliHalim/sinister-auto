/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.repository.ReportTugPerformanceRepository;
import com.gaconnecte.auxilium.service.dto.ReportTugPerformanceDTO;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewPolicyDTO;
import com.gaconnecte.auxilium.service.mapper.ReportTugPerformanceMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hannibaal
 */
/**
 * Service Implementation for managing report.
 */
@Service
@Transactional
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportTugPerformanceRepository reportTugPerformanceRepository;
    private final ReportTugPerformanceMapper reportTugPerformanceMapper;

    public ReportService(ReportTugPerformanceRepository reportTugPerformanceRepository, ReportTugPerformanceMapper reportTugPerformanceMapper) {
        this.reportTugPerformanceRepository = reportTugPerformanceRepository;
        this.reportTugPerformanceMapper = reportTugPerformanceMapper;
    }
    
    /**
     *  Get all the ReportTugPerformance lines.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ReportTugPerformanceDTO> findAll(SearchDTO searchDTO) {
                log.debug("Request to get all sinister prestation tug performance {}", searchDTO);
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        return reportTugPerformanceRepository.findAllReportTugPerformanceByDates(startDate, endDate).stream()
            .map(reportTugPerformanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    @Transactional(readOnly = true)
    public List<ReportTugPerformanceDTO> findAll() {
       
        return reportTugPerformanceRepository.findAll().stream()
            .map(reportTugPerformanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
   
    
    @Transactional(readOnly = true)
    public Long getCountReportTug(SearchDTO searchDTO) {
        log.debug("Request to get all ReportTug");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        return reportTugPerformanceRepository.countAllS(startDate, endDate);
    }
    
    /*@Transactional(readOnly = true)
    public Page<ReportTugPerformanceDTO> findAllReportSE(String filter, Pageable pageable) {
        log.debug("Request to get a ReportTug page");
        LocalDate startDate = LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(9000, Month.JANUARY, 1);
        if (StringUtils.isNotBlank(filter)) {
            return reportTugPerformanceRepository.findAllWithFilter2(filter.toLowerCase(), pageable, startDate, endDate).map(reportTugPerformanceMapper::toDto);
        } else {
            return reportTugPerformanceRepository.findAllS(pageable, startDate, endDate).map(reportTugPerformanceMapper::toDto);
        }
    }*/
    @Transactional(readOnly = true)
    public Long getCountReportTugWithFiltter(String filter, SearchDTO searchDTO) {
        log.debug("Request to get all ReportTug");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        Integer partnerId= searchDTO.getCompagnieId()  != null ? searchDTO.getCompagnieId().intValue() : 0;
        Integer tugId= searchDTO.getTugId()  != null ? searchDTO.getTugId().intValue() : 0;
        Integer zoneId= searchDTO.getZoneId()  != null ? searchDTO.getZoneId().intValue() : 0;

        if (StringUtils.isNotBlank(filter)) {
            return reportTugPerformanceRepository.countAllWithFilter(filter.toLowerCase(), startDate.toString(), endDate.toString(), partnerId  ,tugId,zoneId);
        } else {
            return reportTugPerformanceRepository.countAllwithoutfilterdt(startDate.toString(), endDate.toString(), partnerId  ,tugId,zoneId);
        }
    }
    
    @Transactional(readOnly = true)
    public Page<ReportTugPerformanceDTO> findAllReportS(String filter, Pageable pageable, SearchDTO searchDTO) {
        log.debug("Request to get a ReportTug page");
        LocalDate startDate = searchDTO.getStartDate() != null ? searchDTO.getStartDate() : LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = searchDTO.getEndDate() != null ? searchDTO.getEndDate() : LocalDate.of(9000, Month.JANUARY, 1);
        Integer partnerId= searchDTO.getCompagnieId()  != null ? searchDTO.getCompagnieId().intValue() : 0;
        Integer tugId= searchDTO.getTugId()  != null ? searchDTO.getTugId().intValue() : 0;
        Integer zoneId= searchDTO.getZoneId()  != null ? searchDTO.getZoneId().intValue() : 0;

        if (StringUtils.isNotBlank(filter)) {
            return reportTugPerformanceRepository.findAllWithFilter(filter.toLowerCase(), pageable, startDate.toString(), endDate.toString(), partnerId  ,tugId,zoneId).map(reportTugPerformanceMapper::toDto);
        } else {
            return reportTugPerformanceRepository.findallwithoutfilterdt(pageable, startDate.toString(), endDate.toString(), partnerId  ,tugId,zoneId).map(reportTugPerformanceMapper::toDto);
        }
    }
}
