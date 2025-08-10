package com.team2.bank.Repositories;

import com.team2.bank.Enums.TransactionType;
import com.team2.bank.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByCustomer_BankAccount_Id(Long accountId);
    @Modifying
    void deleteByType(TransactionType type);

}
