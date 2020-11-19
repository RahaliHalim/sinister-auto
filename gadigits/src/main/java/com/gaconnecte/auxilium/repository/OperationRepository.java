package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Operation;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation,Long> {
    
    List<Operation> findAllByCode(String code);
}
