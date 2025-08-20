package com.redway.recallm.mappers;

import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.models.elastic.MemoryItemDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoryItemMapper {

  MemoryItemMapper INSTANCE = Mappers.getMapper(MemoryItemMapper.class);

  // Domain → Document
  MemoryItemDocument toDocument(MemoryItem item);

  // Document → Domain
  MemoryItem toDomain(MemoryItemDocument document);
}
