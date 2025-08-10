package com.team2.bank.Repositories;

import com.team2.bank.Models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByAccountType(String type);
    List<BankAccount> findByBalanceGreaterThan(Double amount);

    @Query("SELECT b FROM BankAccount b WHERE b.balance BETWEEN :min AND :max")
    List<BankAccount> findAccountsInRange(Double min, Double max);

    @Modifying
    @Query("UPDATE BankAccount b SET b.balance = :balance WHERE b.id = :id")
    void updateAccountBalance(Long id, Double balance);
}
