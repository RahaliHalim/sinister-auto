package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTugTruck;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTugTruck entity.
 */
public interface CamionSearchRepository extends ElasticsearchRepository<RefTugTruck, Long> {
}
