package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.BusinessEntity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BusinessEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessEntityRepository extends JpaRepository<BusinessEntity,Long> {
    
}
