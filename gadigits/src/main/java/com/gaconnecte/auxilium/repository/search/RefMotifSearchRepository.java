package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefMotif;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefMotif entity.
 */
public interface RefMotifSearchRepository extends ElasticsearchRepository<RefMotif, Long> {
}
