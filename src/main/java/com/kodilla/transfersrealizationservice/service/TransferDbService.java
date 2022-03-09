package com.kodilla.transfersrealizationservice.service;

import com.kodilla.transfersrealizationservice.db.TransferRepository;
import com.kodilla.transfersrealizationservice.domain.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferDbService {
    private final TransferRepository transferRepository;

    public void save(Transfer transfer){
        transferRepository.save(transfer);
    }
}
