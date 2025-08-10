package com.team2.bank.DTOs;

import com.team2.bank.Enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionDTO {

    private Long id;
    private CustomerDTO accountOwner;
    private BankAccountDTO receiverAccount;
    private Double amount;
    private TransactionType type;


    public TransactionDTO() {
    }

    public TransactionDTO(Long id, CustomerDTO accountOwner, Double amount, TransactionType type) {
        this.id = id;
        this.accountOwner = accountOwner;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(CustomerDTO accountOwner) {
        this.accountOwner = accountOwner;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BankAccountDTO getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(BankAccountDTO receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
}
