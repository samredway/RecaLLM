package com.redway.recallm.repositories;

import com.redway.recallm.models.elastic.MemoryItemDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryItemDocumentRepository
    extends ElasticsearchRepository<MemoryItemDocument, String> {

  Page<MemoryItemDocument> findBySessionId(String sessionId, Pageable pageable);
}
