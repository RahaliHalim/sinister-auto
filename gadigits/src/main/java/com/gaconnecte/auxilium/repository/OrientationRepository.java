package com.gaconnecte.auxilium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.gaconnecte.auxilium.domain.Orientation;

@SuppressWarnings("unused")
@Repository
public interface OrientationRepository extends JpaRepository<Orientation,Long> {


}
