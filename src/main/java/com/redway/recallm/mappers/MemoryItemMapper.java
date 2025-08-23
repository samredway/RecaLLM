package com.redway.recallm.mappers;

import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.models.elastic.MemoryItemDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemoryItemMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", expression = "java(item.role().name())")
  MemoryItemDocument toDocument(MemoryItem item);

  @Mapping(target = "role", expression = "java(MemoryItem.Role.valueOf(document.getRole()))")
  MemoryItem toDomain(MemoryItemDocument document);
}
