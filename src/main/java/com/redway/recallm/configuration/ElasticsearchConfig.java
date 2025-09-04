package com.redway.recallm.configuration;

import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * Sets up the low level ElasticsearchClient
 *
 * <p>NOTE not currently being used but will be later for long term memory query
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

  @Value("${spring.elasticsearch.uris}")
  private String elasticsearchUri;

  @Override
  public ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder()
        .connectedTo(elasticsearchUri.replace("http://", "").replace("https://", ""))
        .build();
  }

  @Override
  public JsonpMapper jsonpMapper() {
    ObjectMapper om =
        new ObjectMapper()
            .findAndRegisterModules() // picks up jackson-datatype-jsr310
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return new JacksonJsonpMapper(om);
  }
}
