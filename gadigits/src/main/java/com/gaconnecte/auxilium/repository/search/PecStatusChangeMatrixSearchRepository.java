package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PecStatusChangeMatrix;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PecStatusChangeMatrix entity.
 */
public interface PecStatusChangeMatrixSearchRepository extends ElasticsearchRepository<PecStatusChangeMatrix, Long> {
}
