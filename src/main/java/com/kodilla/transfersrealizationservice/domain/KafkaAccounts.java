package com.kodilla.transfersrealizationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "kafka_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaAccounts {
    @Id
    private String accountNumber;
    private BigDecimal amount;
}
