package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.StatusPec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StatusPec entity.
 */
public interface StatusPecSearchRepository extends ElasticsearchRepository<StatusPec, Long> {
}
