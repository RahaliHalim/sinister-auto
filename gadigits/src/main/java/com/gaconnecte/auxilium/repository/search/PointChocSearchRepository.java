package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PointChoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PointChoc entity.
 */
public interface PointChocSearchRepository extends ElasticsearchRepository<PointChoc, Long> {
}
