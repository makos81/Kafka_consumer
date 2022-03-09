package com.kodilla.transfersrealizationservice.service;

import com.kodilla.commons.TransferMessage;
import com.kodilla.transfersrealizationservice.domain.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferListener {
    private final TransferDbService transferDbService;
    private final KafkaAccountsService kafkaAccountsService;

    @KafkaListener(topics = "transfers")
    public void consume(@Payload TransferMessage transferMessage) {
        log.info("consumed transfer message: {}", transferMessage);
        Transfer transfer = Transfer.builder()
                .amount(transferMessage.getTransfer().getAmount())
                .recipientAccount(transferMessage.getTransfer().getRecipientAccount())
                .senderAccount(transferMessage.getTransfer().getSenderAccount())
                .title(transferMessage.getTransfer().getTitle())
                .build();
        transferDbService.save(transfer);
        kafkaAccountsService.calculateNewAmountsOrSaveRecord(transfer);
    }
}
