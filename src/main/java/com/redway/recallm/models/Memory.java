package com.redway.recallm.models;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Memory.INDEX_NAME)
public class Memory {
  public static final String INDEX_NAME = "recallm-memories";

  @Id private String id;

  @Field(type = FieldType.Keyword)
  private String userId;

  @Field(type = FieldType.Keyword)
  private String sessionId;

  @Field(type = FieldType.Keyword)
  private String role;

  @Field(type = FieldType.Text)
  private String content;

  @Field(type = FieldType.Date)
  private OffsetDateTime createdAt;
}
