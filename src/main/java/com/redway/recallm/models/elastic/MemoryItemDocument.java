package com.redway.recallm.models.elastic;

import java.time.OffsetDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

  public MemoryItemDocument() {}

  public MemoryItemDocument(
      String id,
      String userId,
      String sessionId,
      String role,
      String content,
      OffsetDateTime createdAt) {
    this.id = id;
    this.userId = userId;
    this.sessionId = sessionId;
    this.role = role;
    this.content = content;
    this.createdAt = createdAt;
  }

  // TODO consider using Lombok to reduce the boilerplate below
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
