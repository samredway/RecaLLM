package com.redway.recallm.models;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@Document(indexName = Memory.INDEX_NAME)
public class Memory {
  public static final String INDEX_NAME = "recallm-memories";

  public enum Role {
    USER,
    ASSISTENT,
  }

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

  public Memory(String userId, String sessionId, Role role, String content) {
    this.id = null;
    this.userId = userId;
    this.sessionId = sessionId;
    this.role = role.name();
    this.content = content;
    this.createdAt = OffsetDateTime.now();
  }
}
