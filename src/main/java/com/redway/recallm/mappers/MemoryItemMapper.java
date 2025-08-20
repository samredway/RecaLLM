package com.redway.recallm.mappers;

import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.models.elastic.MemoryItemDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemoryItemMapper {
    // Domain object to elastic document model
    @Mapping(target = "id", ignore = true)
    MemoryItemDocument toDocument(MemoryItem item);

    // Elastic document model to domain model
    @Mapping(target = "role", expression = "java(MemoryItem.Role.valueOf(document.getRole()))")
    MemoryItem toDomain(MemoryItemDocument document);
}
