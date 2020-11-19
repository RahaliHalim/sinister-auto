package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Reparateur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reparateur entity.
 */
public interface ReparateurSearchRepository extends ElasticsearchRepository<Reparateur, Long> {
}
