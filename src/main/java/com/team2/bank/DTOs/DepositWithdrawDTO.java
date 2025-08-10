package com.team2.bank.DTOs;

import lombok.Data;

@Data
public class DepositWithdrawDTO {
    private long accountId;
    private double amount;
}
