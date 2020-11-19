package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehiculeLoueur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehiculeLoueur entity.
 */
public interface VehiculeLoueurSearchRepository extends ElasticsearchRepository<VehiculeLoueur, Long> {
}
