package com.team2.bank.DTOs;

import lombok.Data;

@Data
public class TransferDTO {
    private long senderId;
    private long receiverAccountId;
    private double amount;
}
