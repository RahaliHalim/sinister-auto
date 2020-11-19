package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VehicleEnergy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehicleEnergy entity.
 */
public interface VehicleEnergySearchRepository extends ElasticsearchRepository<VehicleEnergy, Long> {
}
