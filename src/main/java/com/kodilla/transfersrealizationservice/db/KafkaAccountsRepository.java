package com.kodilla.transfersrealizationservice.db;

import com.kodilla.transfersrealizationservice.domain.KafkaAccounts;
import com.kodilla.transfersrealizationservice.domain.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface KafkaAccountsRepository extends CrudRepository<KafkaAccounts, Long> {
    Optional<KafkaAccounts> findByAccountNumber(String accountNumber);
}
