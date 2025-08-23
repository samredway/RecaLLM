package com.redway.recallm.configuration;

import com.redway.recallm.models.elastic.MemoryItemDocument;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

@Configuration
public class ElasticsearchIndexes {

  @Bean
  ApplicationRunner initIndex(ElasticsearchOperations ops) {
    return args -> {
      IndexOperations indexOps = ops.indexOps(MemoryItemDocument.class);
      if (!indexOps.exists()) {
        indexOps.create();
        indexOps.putMapping(indexOps.createMapping(MemoryItemDocument.class));
      }
    };
  }
}
