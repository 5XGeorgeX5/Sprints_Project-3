package com.team2.bank.DTOs;

import com.team2.bank.Enums.BankAccountType;
import lombok.Data;

@Data
public class CreateBankAccountDTO {
    private Double balance;
    private BankAccountType accountType;
    private Long customerId;
}
