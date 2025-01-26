package com.exaltit.bankaccount.infrastructure.adapter.output.repository;

import com.exaltit.bankaccount.infrastructure.adapter.output.entities.CurrentAccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAccountRepository extends JpaRepository<CurrentAccountEntity, Long> {

  Optional<CurrentAccountEntity> findByAccountNumber(Long accountNumber);
}