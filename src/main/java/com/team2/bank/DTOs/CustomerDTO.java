package com.team2.bank.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private BankAccountDTO bankAccount;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String name, String email, BankAccountDTO bankAccount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bankAccount = bankAccount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public BankAccountDTO getBankAccount() { return bankAccount; }
    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }
    
}