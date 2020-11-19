package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PolicyStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PolicyStatus entity.
 */
public interface PolicyStatusSearchRepository extends ElasticsearchRepository<PolicyStatus, Long> {
}
