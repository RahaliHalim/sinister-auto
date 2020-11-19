package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefRemorqueur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefRemorqueur entity.
 */
public interface RefRemorqueurSearchRepository extends ElasticsearchRepository<RefRemorqueur, Long> {
}
