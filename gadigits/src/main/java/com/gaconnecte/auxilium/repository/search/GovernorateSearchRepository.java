package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Governorate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Governorate entity.
 */
public interface GovernorateSearchRepository extends ElasticsearchRepository<Governorate, Long> {
}
