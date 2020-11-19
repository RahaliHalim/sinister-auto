package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.Menu;
import com.gaconnecte.auxilium.domain.RefStepPec;

@Repository
public interface RefStepPecRepository extends JpaRepository<RefStepPec, Long>{

}
