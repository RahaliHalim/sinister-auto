package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.SinisterPec;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SinisterPec entity.
 */
public interface SinisterPecSearchRepository extends ElasticsearchRepository<SinisterPec, Long> {
}
