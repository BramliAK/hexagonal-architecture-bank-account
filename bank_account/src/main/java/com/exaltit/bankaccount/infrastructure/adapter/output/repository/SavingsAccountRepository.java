package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import com.exaltit.bankaccount.infrastructure.adapter.output.entities.SavingsAccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccountEntity, Long> {

  Optional<SavingsAccountEntity> findByAccountNumber(Long accountNumber);
}