package com.gaconnecte.auxilium.repository.search;

import com.gaconnecte.auxilium.domain.PolicyReceiptStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PolicyReceiptStatus entity.
 */
public interface PolicyReceiptStatusSearchRepository extends ElasticsearchRepository<PolicyReceiptStatus, Long> {
}
