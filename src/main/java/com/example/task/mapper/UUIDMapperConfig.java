package com.example.task.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.Named;

import java.util.UUID;

@MapperConfig(componentModel = "spring")
public interface UUIDMapperConfig {
  default UUID stringToUUID(String value) {
    return value != null ? UUID.fromString(value) : null;
  }

  default String uuidToString(UUID uuid) {
    return uuid != null ? uuid.toString() : null;
  }

  @Named("intToUUID")
  default UUID intToUUID(int value) {
    return UUID.fromString(String.valueOf(value));
  }
}
