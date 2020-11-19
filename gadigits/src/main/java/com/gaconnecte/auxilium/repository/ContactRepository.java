package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Contact;
import com.gaconnecte.auxilium.domain.Produit;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

     @Query("select distinct contact from Contact contact where contact.personneMorale.id =:personneMoraleId")
     Page<Contact> findContactsByPersonneMorale(Pageable pageable,@Param("personneMoraleId") Long personneMoraleId);
     
     @Query(value ="select * from Contact c where c.personne_morale_id =?1 and c.is_gerant = true Limit 1", nativeQuery = true)
     Contact findMainContactGerantByPersonneMoraleId(Long personneMoraleId);
     
     @Query(value ="select * from Contact c where c.personne_morale_id =?1 Limit 1", nativeQuery = true)
     Contact findMainContactNonGerantByPersonneMoraleId(Long personneMoraleId);
     
    
}