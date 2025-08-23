package com.redway.recallm.repositories;

import com.redway.recallm.models.elastic.MemoryItemDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryItemDocumentRepository
    extends ElasticsearchRepository<MemoryItemDocument, String> {

  List<MemoryItemDocument> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}
