package com.team2.bank.Repositories;

import com.team2.bank.Enums.TransactionType;
import com.team2.bank.Models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionModel,Long> {
    List<TransactionModel> findByType(TransactionType type);
    List<TransactionModel> findByCustomerModel_BankAccountModel_Id(Long accountId);
    @Modifying
    void deleteByType(TransactionType type);

}
