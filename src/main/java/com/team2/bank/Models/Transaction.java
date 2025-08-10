package com.team2.bank.Models;

import com.team2.bank.Enums.TransactionType;

import jakarta.persistence.*;

@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne()
    @JoinColumn(name = "account_owner_id", nullable = false)
    private Customer accountOwner;

    @ManyToOne()
    @JoinColumn(name = "receiver_account_id")
    private BankAccount receiverAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Customer getCustomerModel() {
        return accountOwner;
    }

    public void setCustomerModel(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }
}

