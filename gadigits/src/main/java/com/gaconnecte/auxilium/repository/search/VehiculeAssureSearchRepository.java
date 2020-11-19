package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehiculeAssure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehiculeAssure entity.
 */
public interface VehiculeAssureSearchRepository extends ElasticsearchRepository<VehiculeAssure, Long> {
}
