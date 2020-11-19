package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Devis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Devis entity.
 */
public interface DevisSearchRepository extends ElasticsearchRepository<Devis, Long> {
}
