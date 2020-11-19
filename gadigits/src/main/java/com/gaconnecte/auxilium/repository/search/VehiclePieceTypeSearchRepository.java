package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehiclePieceType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehiclePieceType entity.
 */
public interface VehiclePieceTypeSearchRepository extends ElasticsearchRepository<VehiclePieceType, Long> {
}
