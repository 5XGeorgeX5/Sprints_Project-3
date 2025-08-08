package com.team2.bank.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "BankAccount")
public class BankAccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance",nullable = false)
    private double balance;

    @Column(name = "accountType",nullable = false)
    private String accountType;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerModel customerModel;



}

