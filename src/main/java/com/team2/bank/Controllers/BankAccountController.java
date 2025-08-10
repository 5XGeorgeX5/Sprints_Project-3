package com.team2.bank.Controllers;

import com.team2.bank.DTOs.BankAccountDTO;
import com.team2.bank.Enums.TransactionType;
import com.team2.bank.Services.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    public final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    //create - create bank account
    @PostMapping
    public ResponseEntity<BankAccountDTO> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountDto) {
        BankAccountDTO createdBankAccount = bankAccountService.createBankAccount(bankAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
    }

    //read - get all accounts
    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        List<BankAccountDTO> bankAccount = bankAccountService.getAllBankAccounts();
        return ResponseEntity.ok(bankAccount);
    }

    //read - get account by id
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccountById(@PathVariable Long id) {
        BankAccountDTO bankAccount = bankAccountService.getBankAccountById(id);
        return ResponseEntity.ok(bankAccount);
    }

    //read - get bank accounts by type
    @GetMapping("/types/{type}")
    public ResponseEntity<List<BankAccountDTO>> getAccountByType(@PathVariable TransactionType type) {
        List<BankAccountDTO> bankAccounts = bankAccountService.getAccountsByType(type);
        return ResponseEntity.ok(bankAccounts);
    }

    //read - find balance greater that
    @GetMapping("/balance/greater-than/{amount}")
    public ResponseEntity<List<BankAccountDTO>> findByBalanceGreaterThan(@PathVariable Double amount) {
        List<BankAccountDTO> bankAccounts = bankAccountService.findByBalanceGreaterThan(amount);
        return ResponseEntity.ok(bankAccounts);
    }

    //read - find account in range
    @GetMapping("/balance/in-range")
    public ResponseEntity<List<BankAccountDTO>> findAccountsInRange(@RequestParam Double min, @RequestParam Double max) {
        List<BankAccountDTO> bankAccounts = bankAccountService.findAccountsInRange(min, max);
        return ResponseEntity.ok(bankAccounts);
    }

    //update - update account (by id)
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO>updateBankAccount(@PathVariable Long id, @Valid @RequestBody BankAccountDTO bankAccountDto){
        BankAccountDTO updatedBankAccount = bankAccountService.updateBankAccount(id, bankAccountDto);
        return ResponseEntity.ok(updatedBankAccount);
    }

    //delete - delete account (by id)
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteBankAccount(@PathVariable Long id){
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }

    //transfer money
    @PostMapping("/{id}/transfer")
    public ResponseEntity<?> transferMoney(@PathVariable Long id, @Valid @RequestBody Map<String, Object> transferDetails) {
        Long receiverAccountId = Long.valueOf(transferDetails.get("receiverAccountId").toString());
        Double amount = Double.valueOf(transferDetails.get("amount").toString());
        bankAccountService.transferMoney(id, receiverAccountId, amount);
        return ResponseEntity.ok().build();
    }

    //deposit
    @PostMapping("/{id}/deposit")
    public ResponseEntity<BankAccountDTO> deposit(@PathVariable Long id, @Valid @RequestBody Map<String, Object> payload) {
        Double amount = Double.valueOf(payload.get("amount").toString());
        BankAccountDTO updatedAccount = bankAccountService.deposit(id, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    //withdraw
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<BankAccountDTO> withdraw(@PathVariable Long id, @Valid @RequestBody Map<String, Object> payload) {
        Double amount = Double.valueOf(payload.get("amount").toString());
        BankAccountDTO updatedAccount = bankAccountService.withdraw(id, amount);
        return ResponseEntity.ok(updatedAccount);
    }

}
