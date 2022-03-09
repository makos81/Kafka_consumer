package com.kodilla.transfersrealizationservice.db;

import com.kodilla.transfersrealizationservice.domain.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
