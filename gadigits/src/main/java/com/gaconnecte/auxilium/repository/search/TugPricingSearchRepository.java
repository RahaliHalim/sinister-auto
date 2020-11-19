package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.TugPricing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TugPricing entity.
 */
public interface TugPricingSearchRepository extends ElasticsearchRepository<TugPricing, Long> {
}
