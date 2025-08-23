package com.redway.recallm.configuration;

import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

  @Override
  public ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder().connectedTo("localhost:9200").build();
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
