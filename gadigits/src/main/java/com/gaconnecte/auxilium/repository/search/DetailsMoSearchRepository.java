package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.DetailsMo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DetailsMo entity.
 */
public interface DetailsMoSearchRepository extends ElasticsearchRepository<DetailsMo, Long> {
}
