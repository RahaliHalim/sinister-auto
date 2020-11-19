package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.VatRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VatRate entity.
 */
public interface VatRateSearchRepository extends ElasticsearchRepository<VatRate, Long> {
}
