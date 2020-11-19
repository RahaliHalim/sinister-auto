package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehiclePiece;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehiclePiece entity.
 */
public interface VehiclePieceSearchRepository extends ElasticsearchRepository<VehiclePiece, Long> {
}
