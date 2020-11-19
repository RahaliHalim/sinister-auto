package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Reason;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reason entity.
 */
public interface ReasonSearchRepository extends ElasticsearchRepository<Reason, Long> {
}
