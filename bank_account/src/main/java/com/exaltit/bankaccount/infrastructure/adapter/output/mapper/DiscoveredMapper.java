package com.exaltit.bankaccount.infrastructure.adapter.output.mapper;

import com.exaltit.bankaccount.domain.model.Discovered;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.DiscoveredEntity;
import org.springframework.stereotype.Component;

@Component
public class DiscoveredMapper {
  DiscoveredEntity toEntity(Discovered discovered) {
    return new DiscoveredEntity(discovered.id(), discovered.authorizedDiscoveryAmount());
  }

  Discovered toDomain(DiscoveredEntity discoveredEntity) {
    return new Discovered(discoveredEntity.getId(),
        discoveredEntity.getAuthorizedDiscoveryAmount());
  }
}