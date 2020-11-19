package com.gaconnecte.auxilium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gaconnecte.auxilium.domain.ToDeleteFeatures;

/**
 * Spring Data JPA repository for the Feature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeatureRepository extends JpaRepository<ToDeleteFeatures,Long>{


  @Query("select feature from ToDeleteFeatures feature where feature.entitie.id =:id")
    List<ToDeleteFeatures> getListFeaturesByEntiti(@Param("id") Long id);
  @Query("select feature from ToDeleteFeatures feature where feature.parent = null")
    List<ToDeleteFeatures> findFeaturesWithoutParent();
}
