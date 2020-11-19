package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Journal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Journal entity.
 */
public interface JournalSearchRepository extends ElasticsearchRepository<Journal, Long> {
}
