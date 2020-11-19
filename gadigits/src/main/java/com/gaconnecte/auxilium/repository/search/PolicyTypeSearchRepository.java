package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PolicyType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PolicyType entity.
 */
public interface PolicyTypeSearchRepository extends ElasticsearchRepository<PolicyType, Long> {
}
