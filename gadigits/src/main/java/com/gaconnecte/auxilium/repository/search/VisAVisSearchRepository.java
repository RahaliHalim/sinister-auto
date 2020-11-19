package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VisAVis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Contact entity.
 */
public interface VisAVisSearchRepository extends ElasticsearchRepository<VisAVis, Long> {
}
