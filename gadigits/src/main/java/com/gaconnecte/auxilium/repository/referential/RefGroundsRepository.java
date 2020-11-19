package com.gaconnecte.auxilium.repository.referential;

import com.gaconnecte.auxilium.domain.referential.RefGrounds;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import org.springframework.stereotype.Repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the RefGrounds entity.
 */
@Repository
public interface RefGroundsRepository extends JpaRepository<RefGrounds, Long> {

    Set<RefGrounds> findAllByStatus(RefStatusSinister status);
    
}
