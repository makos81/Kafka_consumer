package com.kodilla.transfersrealizationservice.controller;

import com.kodilla.transfersrealizationservice.service.KafkaAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountsController {
    private final KafkaAccountsService kafkaAccountsService;

    @GetMapping
    public BigDecimal getAmount(String accountNumber){
        BigDecimal amount = kafkaAccountsService.getAmount(accountNumber);
        if(amount==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return amount;
    }
}
