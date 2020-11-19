package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RaisonPec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RaisonPec entity.
 */
public interface RaisonPecSearchRepository extends ElasticsearchRepository<RaisonPec, Long> {
}
