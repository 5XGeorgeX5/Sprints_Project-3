package com.team2.bank.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Customer")
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(mappedBy = "customerModel", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private BankAccountModel bankAccountModel;


    @OneToMany(mappedBy = "customerModel" , cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<TransactionModel> transactionModels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BankAccountModel getBankAccountModel() {
        return bankAccountModel;
    }

    public void setBankAccountModel(BankAccountModel bankAccountModel) {
        this.bankAccountModel = bankAccountModel;
    }

    public List<TransactionModel> getTransactionModels() {
        return transactionModels;
    }

    public void setTransactionModels(List<TransactionModel> transactionModels) {
        this.transactionModels = transactionModels;
    }
}

