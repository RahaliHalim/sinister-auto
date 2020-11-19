package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Delegation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Delegation entity.
 */
public interface DelegationSearchRepository extends ElasticsearchRepository<Delegation, Long> {
}
