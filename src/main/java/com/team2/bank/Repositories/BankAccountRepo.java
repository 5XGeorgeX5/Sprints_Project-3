package com.team2.bank.Repositories;

import com.team2.bank.Models.BankAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccountModel, Long> {
    List<BankAccountModel> findByAccountType(String type);
    List<BankAccountModel> findByBalanceGreaterThan(Double amount);

    @Query("SELECT b FROM BankAccountModel b WHERE b.balance BETWEEN :min AND :max")
    List<BankAccountModel> findAccountsInRange(Double min, Double max);

    @Modifying
    @Query("UPDATE BankAccountModel b SET b.balance = :balance WHERE b.id = :id")
    void updateAccountBalance(Long id, Double balance);
}
