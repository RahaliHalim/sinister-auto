package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Policy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Policy entity.
 */
public interface PolicySearchRepository extends ElasticsearchRepository<Policy, Long> {
}
