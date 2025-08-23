package com.redway.recallm.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.redway.recallm.exceptions.StorageException;
import com.redway.recallm.mappers.MemoryItemMapper;
import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.models.elastic.MemoryItemDocument;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ElasticMemoryService implements MemoryService {

  private final ElasticsearchClient client;
  private final MemoryItemMapper mapper;

  /** Save a memory to elastic */
  @Override
  public void memorise(MemoryItem item) {
    try {
      client.index(i -> i.index(MemoryItemDocument.INDEX_NAME).document(mapper.toDocument(item)));
    } catch (IOException e) {
      throw new StorageException("Failed to write memory", e);
    }
  }

  /** Retrieve all memories from a given session */
  @Override
  public List<MemoryItem> recallSession(String sessionId) {
    try {
      var response =
          client.search(
              s ->
                  s.index(MemoryItemDocument.INDEX_NAME)
                      .query(q -> q.term(t -> t.field("sessionId").value(sessionId)))
                      .size(10000), // Max but should always be enough for single session
              MemoryItemDocument.class);

      return response.hits().hits().stream()
          .map(hit -> hit.source())
          .filter(doc -> doc != null)
          .map(doc -> mapper.toDomain(doc))
          .toList();

    } catch (IOException e) {
      throw new StorageException("Failed to query memories for sessionId: " + sessionId, e);
    }
  }

  public void forceRefresh() {
    try {
      client.indices().refresh(r -> r.index(MemoryItemDocument.INDEX_NAME));
    } catch (IOException e) {
      throw new StorageException("Unable to refresh indices", e);
    }
  }
}
