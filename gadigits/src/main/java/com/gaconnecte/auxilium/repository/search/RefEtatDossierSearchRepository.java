package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.RefEtatDossier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RefEtatDossier entity.
 */
public interface RefEtatDossierSearchRepository extends ElasticsearchRepository<RefEtatDossier, Long> {
}
