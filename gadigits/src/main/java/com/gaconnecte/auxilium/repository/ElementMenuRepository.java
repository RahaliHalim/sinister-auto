package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ElementMenu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ElementMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementMenuRepository extends JpaRepository<ElementMenu,Long> {
    
}
