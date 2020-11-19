package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.ToDeleteBusinessEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;



@SuppressWarnings("unused")
@Repository
public interface EntitiRepository extends JpaRepository<ToDeleteBusinessEntity,Long> {


}