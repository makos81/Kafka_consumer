package com.kodilla.transfersrealizationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.commons.TransferMessage;
import com.kodilla.transfersrealizationservice.db.TransferMessageRepository;
import com.kodilla.transfersrealizationservice.domain.Transfer;
import com.kodilla.transfersrealizationservice.domain.TransferMessageDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferListener {
    private final TransferDbService transferDbService;
    private final KafkaAccountsService kafkaAccountsService;
    private final TransferMessageRepository transferMessageRepository;

    @KafkaListener(topics = "transfers")
    public void consume(@Payload TransferMessage transferMessage) throws JsonProcessingException {
        log.info("consumed transfer message: {}", transferMessage);
        UUID id = transferMessage.getId();
        if(transferMessageRepository.findById(id).isPresent()){
            Transfer transfer = buildTransfer(transferMessage);
            transferDbService.save(transfer);
            kafkaAccountsService.calculateNewAmountsOrSaveRecord(transfer);
        }
        TransferMessageDomain transferMessageDomain = TransferMessageDomain.builder()
                .originalMessage(mapToJson(transferMessage.getTransfer()))
                .id(transferMessage.getId())
                .build();
        transferMessageRepository.save(transferMessageDomain);
    }

    private String mapToJson(Object transferMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(transferMessage);
    }

    private Transfer buildTransfer(TransferMessage transferMessage){
        return Transfer.builder()
                .amount(transferMessage.getTransfer().getAmount())
                .recipientAccount(transferMessage.getTransfer().getRecipientAccount())
                .senderAccount(transferMessage.getTransfer().getSenderAccount())
                .title(transferMessage.getTransfer().getTitle())
                .build();
    }
}
