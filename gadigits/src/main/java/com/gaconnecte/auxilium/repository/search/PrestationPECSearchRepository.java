package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PrestationPEC;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PrestationPEC entity.
 */


public interface PrestationPECSearchRepository extends ElasticsearchRepository<PrestationPEC, Long> {
}
