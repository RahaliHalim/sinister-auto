package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Tiers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tiers entity.
 */
public interface TiersSearchRepository extends ElasticsearchRepository<Tiers, Long> {
}
