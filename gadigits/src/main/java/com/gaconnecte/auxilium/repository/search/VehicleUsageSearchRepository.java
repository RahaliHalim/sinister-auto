package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehicleUsage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehicleUsage entity.
 */
public interface VehicleUsageSearchRepository extends ElasticsearchRepository<VehicleUsage, Long> {
}
