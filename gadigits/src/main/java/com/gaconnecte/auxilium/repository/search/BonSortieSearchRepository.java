package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.BonSortie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BonSortie entity.
 */
public interface BonSortieSearchRepository extends ElasticsearchRepository<BonSortie, Long> {
}
