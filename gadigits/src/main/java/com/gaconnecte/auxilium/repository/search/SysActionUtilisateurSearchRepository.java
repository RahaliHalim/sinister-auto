package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.SysActionUtilisateur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SysActionUtilisateur entity.
 */
public interface SysActionUtilisateurSearchRepository extends ElasticsearchRepository<SysActionUtilisateur, Long> {
}
