package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehicleBrandModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehicleBrandModel entity.
 */
public interface VehicleBrandModelSearchRepository extends ElasticsearchRepository<VehicleBrandModel, Long> {
}
