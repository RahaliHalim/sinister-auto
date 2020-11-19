package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefZoneGeo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefZoneGeo entity.
 */
public interface RefZoneGeoSearchRepository extends ElasticsearchRepository<RefZoneGeo, Long> {
}
