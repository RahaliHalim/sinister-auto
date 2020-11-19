package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Statement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Statement entity.
 */
public interface StatementSearchRepository extends ElasticsearchRepository<Statement, Long> {
}
