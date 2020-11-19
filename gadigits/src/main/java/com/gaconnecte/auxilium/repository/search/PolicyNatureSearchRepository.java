package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PolicyNature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PolicyNature entity.
 */
public interface PolicyNatureSearchRepository extends ElasticsearchRepository<PolicyNature, Long> {
}
