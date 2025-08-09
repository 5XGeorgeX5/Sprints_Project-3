package com.team2.bank.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class BankAccountDTO {

    private Long id;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance cannot be negative")
    private Double balance;

    @NotBlank(message = "Account type is required")
    private String accountType;

    private Long customerId;

    public BankAccountDTO() {}

    public BankAccountDTO(Long id, Double balance, String accountType, Long customerId) {
        this.id = id;
        this.balance = balance;
        this.accountType = accountType;
        this.customerId = customerId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
}
