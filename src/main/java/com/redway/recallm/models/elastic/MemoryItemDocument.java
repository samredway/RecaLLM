package com.redway.recallm.models.elastic;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@Document(indexName = "recallm-memories")
public class MemoryItemDocument {

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

  public MemoryItemDocument(
      String userId, String sessionId, String role, String content, OffsetDateTime createdAt) {
    this.id = null; // Elastic will generate id
    this.userId = userId;
    this.sessionId = sessionId;
    this.role = role;
    this.content = content;
    this.createdAt = createdAt;
  }
}
