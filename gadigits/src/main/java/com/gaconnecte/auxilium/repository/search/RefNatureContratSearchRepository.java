package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefNatureContrat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefNatureContrat entity.
 */
public interface RefNatureContratSearchRepository extends ElasticsearchRepository<RefNatureContrat, Long> {
}
