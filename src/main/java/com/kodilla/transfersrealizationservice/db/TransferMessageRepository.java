package com.kodilla.transfersrealizationservice.db;

import com.kodilla.transfersrealizationservice.domain.TransferMessageDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransferMessageRepository extends CrudRepository<TransferMessageDomain, UUID> {
}
