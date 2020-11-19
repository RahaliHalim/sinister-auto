package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PolicyHolder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PolicyHolder entity.
 */
public interface PolicyHolderSearchRepository extends ElasticsearchRepository<PolicyHolder, Long> {
}
