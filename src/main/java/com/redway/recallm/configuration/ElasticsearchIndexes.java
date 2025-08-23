package com.redway.recallm.configuration;

import com.redway.recallm.models.Memory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

/** Prebuild elastic indexes so as to afford default mappings being created */
@Configuration
public class ElasticsearchIndexes {

  @Bean
  ApplicationRunner initIndex(ElasticsearchOperations ops) {
    return args -> {
      IndexOperations indexOps = ops.indexOps(Memory.class);
      if (!indexOps.exists()) {
        indexOps.create();
        indexOps.putMapping(indexOps.createMapping(Memory.class));
      }
    };
  }
}
