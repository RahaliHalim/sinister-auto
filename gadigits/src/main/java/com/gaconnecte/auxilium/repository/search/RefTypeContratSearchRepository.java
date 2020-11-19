package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefTypeContrat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefTypeContrat entity.
 */
public interface RefTypeContratSearchRepository extends ElasticsearchRepository<RefTypeContrat, Long> {
}
