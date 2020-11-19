/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.view.ViewReparator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ViewReparator entity.
 * @author hannibaal
 */
@Repository
public interface ViewReparatorRepository extends JpaRepository<ViewReparator, Long> {
    
}