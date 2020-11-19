package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Concessionnaire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Concessionnaire entity.
 */
public interface ConcessionnaireSearchRepository extends ElasticsearchRepository<Concessionnaire, Long> {
}
