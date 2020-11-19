package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.Grille;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Grille entity.
 */
public interface GrilleSearchRepository extends ElasticsearchRepository<Grille, Long> {
}
