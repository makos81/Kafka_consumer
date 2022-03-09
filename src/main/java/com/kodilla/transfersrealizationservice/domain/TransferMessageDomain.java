package com.kodilla.transfersrealizationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "transfer_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferMessageDomain {
    @Id
    private UUID id;
    private String originalMessage;
}
