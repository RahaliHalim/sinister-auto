package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Apec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Apec entity.
 */
public interface ApecSearchRepository extends ElasticsearchRepository<Apec, Long> {
}
