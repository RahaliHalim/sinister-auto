package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Produit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query(value = "SELECT * FROM Produit p , Client c WHERE p.code = ?1 AND c.id = ?2 ", nativeQuery = true)
    Produit findProductClientCode(Integer code, Long clientId);

}
