package com.kodilla.transfersrealizationservice.service;

import com.kodilla.transfersrealizationservice.db.KafkaAccountsRepository;
import com.kodilla.transfersrealizationservice.domain.KafkaAccounts;
import com.kodilla.transfersrealizationservice.domain.Transfer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaAccountsService {
    private final KafkaAccountsRepository kafkaAccountsRepository;

    public KafkaAccounts getRecordByAccount(String account) {
        return kafkaAccountsRepository.findByAccountNumber(account).orElse(null);
    }

    public BigDecimal getAmount(String account){
        KafkaAccounts kafkaAccounts = kafkaAccountsRepository.findByAccountNumber(account).orElse(null);
        return kafkaAccounts!=null ? kafkaAccounts.getAmount() : null;
    }

    public void save(KafkaAccounts account) {
        kafkaAccountsRepository.save(account);
    }

    public void calculateNewAmountsOrSaveRecord(Transfer account){
        log.info("calculating amount for {}", account);
        calculateNewAmountsOrSaveRecordForSender(account);
        calculateNewAmountsOrSaveRecordForRecipient(account);
    }

    private void calculateNewAmountsOrSaveRecordForSender(Transfer senderAccount) {
        KafkaAccounts account = getRecordByAccount(senderAccount.getSenderAccount());
        BigDecimal currentAmount = account != null ? account.getAmount() : BigDecimal.ZERO;
        BigDecimal newAmount = currentAmount.subtract(senderAccount.getAmount());
        String accountNumber = senderAccount.getRecipientAccount();
        KafkaAccounts resultedAccount = KafkaAccounts.builder()
                .amount(newAmount)
                .accountNumber(accountNumber)
                .build();
        log.info("updating amount old one {}, new one {} for sender {}", currentAmount, newAmount, accountNumber);
        save(resultedAccount);
    }

    private void calculateNewAmountsOrSaveRecordForRecipient(Transfer recipientAccount) {
        KafkaAccounts account = getRecordByAccount(recipientAccount.getRecipientAccount());
        BigDecimal currentAmount = account != null ? account.getAmount() : BigDecimal.ZERO;
        BigDecimal newAmount = currentAmount.add(recipientAccount.getAmount());
        String accountNumber = recipientAccount.getRecipientAccount();
        KafkaAccounts resultedAccount = KafkaAccounts.builder()
                .amount(newAmount)
                .accountNumber(accountNumber)
                .build();
        log.info("updating amount old one {}, new one {} for sender {}", currentAmount, newAmount, accountNumber);
        save(resultedAccount);
    }
}
