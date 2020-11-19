package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Reparateur;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Reparateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReparateurRepository extends JpaRepository<Reparateur, Long> {

	@Query("select r from Reparateur r, Delegation v where  r.ville = v.id  and v.governorate.id = :gouvernoratId")
	   List<Reparateur> findReparateurByGouvernorat(@Param("gouvernoratId") Long gouvernoratId);
	@Query("SELECT reparateur from Reparateur reparateur where reparateur.registreCommerce = :registreCommerce and reparateur.raisonSociale = :name")
  	Reparateur findReparateurByRegistreCommerce(@Param("registreCommerce") String registreCommerce,@Param("name") String name);
}
