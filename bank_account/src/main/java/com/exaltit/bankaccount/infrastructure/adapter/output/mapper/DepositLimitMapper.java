package com.exaltit.bankaccount.infrastructure.adapter.output.mapper;

import com.exaltit.bankaccount.domain.model.DepositLimit;
import com.exaltit.bankaccount.infrastructure.adapter.output.entities.DepositLimitEntity;
import org.springframework.stereotype.Component;

@Component
public class DepositLimitMapper {
  DepositLimitEntity toEntity(DepositLimit depositLimit) {
    return new DepositLimitEntity(depositLimit.id(), depositLimit.amount());
  }

  DepositLimit toDomain(DepositLimitEntity depositLimit) {
    return new DepositLimit(depositLimit.getId(), depositLimit.getAmount());
  }
}