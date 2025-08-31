package com.redway.recallm.repositories;

import com.redway.recallm.models.Memory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryItemDocumentRepository extends ElasticsearchRepository<Memory, String> {

  Page<Memory> findBySessionId(String sessionId, Pageable pageable);

  Optional<Memory> findTopByUserIdOrderByCreatedAtDesc(String userId);
}
