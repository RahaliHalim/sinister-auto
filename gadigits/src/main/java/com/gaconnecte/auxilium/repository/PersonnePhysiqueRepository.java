package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.PersonnePhysique;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonnePhysique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonnePhysiqueRepository extends JpaRepository<PersonnePhysique,Long> {
	Optional<PersonnePhysique> findOneByPremMail(String mail);
}
