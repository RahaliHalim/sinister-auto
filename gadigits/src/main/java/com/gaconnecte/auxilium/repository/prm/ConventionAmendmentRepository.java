package com.gaconnecte.auxilium.repository.prm;

import com.gaconnecte.auxilium.domain.prm.ConventionAmendment;
import com.gaconnecte.auxilium.domain.referential.RefPack;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConventionAmendment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConventionAmendmentRepository extends JpaRepository<ConventionAmendment, Long> {

	@Query("SELECT conventionAmendment from ConventionAmendment conventionAmendment order by conventionAmendment.id")
	Set<ConventionAmendment> findAllAmendment();
	@Query("SELECT conventionAmendment.refPack from ConventionAmendment conventionAmendment WHERE conventionAmendment.convention.client.id =:partnerId")
	Set<RefPack> findPackByPartner(@Param("partnerId") Long partnerId);

}
