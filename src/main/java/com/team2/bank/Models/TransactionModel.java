package com.team2.bank.Models;

import com.team2.bank.Enums.TransactionType;

import jakarta.persistence.*;

@Entity
@Table(name = "Transaction")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private CustomerModel customerModel;
}

