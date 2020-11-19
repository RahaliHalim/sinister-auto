package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Reinsurer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reinsurer entity.
 */
public interface ReinsurerSearchRepository extends ElasticsearchRepository<Reinsurer, Long> {
}
