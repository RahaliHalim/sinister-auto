package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



/**
 * Spring Data JPA repository for the RefRemorqueur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefRemorqueurRepository extends JpaRepository<RefRemorqueur,Long> {
	@Query("SELECT distinct ref_remorqueur from RefRemorqueur ref_remorqueur where ref_remorqueur.isDelete= false")
	List<RefRemorqueur> findAllRemorqueur();

	@Query("SELECT distinct ref_remorqueur from RefRemorqueur ref_remorqueur where ref_remorqueur.isDelete= false and ref_remorqueur.isBloque= false")
	Page<RefRemorqueur> findAllRemorqueurnonbloque(Pageable pageable);
	 
	@Query("select refRemorqueur from RefRemorqueur refRemorqueur where refRemorqueur.mail=:mail and refRemorqueur.mdp=:mdp")
	RefRemorqueur findRemorqueurBymailAndMdp(@Param("mail") String mail, @Param("mdp") String mdp);
	
	@Query("SELECT refRemorqueur from RefRemorqueur refRemorqueur where refRemorqueur.mail=:mail")
	RefRemorqueur findRefRemorqueurByMail(@Param("mail") String mail);
	
	@Query("SELECT refRemorqueur from RefRemorqueur refRemorqueur where refRemorqueur.deviceId=:deviceId")
	RefRemorqueur findRefRemorqueurByDeviceId(@Param("deviceId") String deviceId);

      	@Query("SELECT refRemorqueur from RefRemorqueur refRemorqueur where refRemorqueur.societe.raisonSociale = :name and refRemorqueur.societe.matriculeFiscale = :matriculeFiscale and refRemorqueur.societe.registreCommerce = :registreCommerce")
	RefRemorqueur findRefRemorqueurByName(@Param("name") String name,@Param("matriculeFiscale") String matriculeFiscale,@Param("registreCommerce") String registreCommerce);

	@Query("SELECT distinct ref_remorqueur from RefRemorqueur ref_remorqueur where ref_remorqueur.isDelete= false and ref_remorqueur.isBloque= false")
	List<RefRemorqueur> findtoutRemorqueurnonbloque();
	 
	@Query("SELECT distinct ref_remorqueur from RefRemorqueur ref_remorqueur where ref_remorqueur.isDelete= false")
		Page<RefRemorqueur> findAllRemorqueurCloture(Pageable pageable);
	@Query("update RefRemorqueur refRemorqueur set refRemorqueur.isFree =:status where refRemorqueur.id =:id")
    @Modifying
	void updateRmqStatus(@Param("status")Boolean status, @Param("id")Long id);	
}
