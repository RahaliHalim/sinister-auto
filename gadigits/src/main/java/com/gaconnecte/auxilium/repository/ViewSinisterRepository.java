/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewSinister;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ViewSinister entity.
 * @author hannibaal
 */
@SuppressWarnings("unused")
@Repository
public interface ViewSinisterRepository extends JpaRepository<ViewSinister, Long> {
	
	
	@Query("SELECT sin FROM ViewSinister sin WHERE lower(sin.reference) like %:filter% OR  lower(sin.registrationNumber)  like %:filter%"
            + " OR  lower(sin.fullName)  like %:filter% OR  lower(sin.nature)  like %:filter% OR  lower(sin.serviceTypes)  like %:filter%")
    Page<ViewSinister> findAllWithFilter(@Param("filter") String filter, Pageable pageable);
	
	@Query("SELECT count(sin) FROM ViewSinister sin WHERE lower(sin.reference) like %:filter% OR  lower(sin.registrationNumber)  like %:filter%"
            + " OR  lower(sin.fullName)  like %:filter% OR  lower(sin.nature)  like %:filter% OR  lower(sin.serviceTypes)  like %:filter%")
    Long countAllWithFilter(@Param("filter") String filter);

    
}