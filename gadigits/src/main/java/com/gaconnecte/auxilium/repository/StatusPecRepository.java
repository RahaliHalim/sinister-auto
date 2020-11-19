package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.StatusPec;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StatusPec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusPecRepository extends JpaRepository<StatusPec,Long> {
    List<StatusPec> findAllByCode(String code);
    List<StatusPec> findAllByHasReason(Boolean hasReason);
}
