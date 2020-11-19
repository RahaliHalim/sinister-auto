package com.gaconnecte.auxilium.repository.prm;

import com.gaconnecte.auxilium.domain.prm.SinisterTypeSetting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SinisterTypeSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinisterTypeSettingRepository extends JpaRepository<SinisterTypeSetting, Long> {

}
