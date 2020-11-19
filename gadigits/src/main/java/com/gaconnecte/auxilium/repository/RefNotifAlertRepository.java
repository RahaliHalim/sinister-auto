package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.RefNotifAlert;


@Repository
public interface RefNotifAlertRepository extends JpaRepository<RefNotifAlert,Long> {

}
