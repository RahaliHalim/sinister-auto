package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.Referentiel;


@SuppressWarnings("unused")
@Repository
public interface ReferentielRepository extends JpaRepository<Referentiel,Long> {
    
}