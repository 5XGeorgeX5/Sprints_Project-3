package com.team2.bank.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Transaction")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private CustomerModel customerModel;
}

