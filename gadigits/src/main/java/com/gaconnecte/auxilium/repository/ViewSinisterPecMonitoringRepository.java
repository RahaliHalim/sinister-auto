/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewPolicy;
import com.gaconnecte.auxilium.domain.view.ViewSinisterPecMonitoring;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hannibaal
 */
@Repository
public interface ViewSinisterPecMonitoringRepository extends JpaRepository<ViewSinisterPecMonitoring, Long> {
    @Query("SELECT sp from ViewSinisterPecMonitoring sp WHERE sp.incidentDate <= :endDate AND sp.incidentDate >= :startDate")
    List<ViewSinisterPecMonitoring> findAllByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
}