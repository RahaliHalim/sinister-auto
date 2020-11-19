package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehicleBrand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehicleBrand entity.
 */
public interface VehicleBrandSearchRepository extends ElasticsearchRepository<VehicleBrand, Long> {
}
