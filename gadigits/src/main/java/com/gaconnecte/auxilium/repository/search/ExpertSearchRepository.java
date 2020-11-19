package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Expert;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Expert entity.
 */
public interface ExpertSearchRepository extends ElasticsearchRepository<Expert, Long> {
}
