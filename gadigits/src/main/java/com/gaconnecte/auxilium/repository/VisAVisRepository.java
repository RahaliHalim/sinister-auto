package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.domain.Produit;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisAVisRepository extends JpaRepository<VisAVis,Long> {

    
     
    
}