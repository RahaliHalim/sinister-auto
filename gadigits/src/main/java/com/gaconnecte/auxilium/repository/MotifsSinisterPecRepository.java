package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.MotifsSinisterPec;


@Repository
public interface MotifsSinisterPecRepository extends JpaRepository<MotifsSinisterPec,Long> {

}
