package com.team2.bank.Controllers;

import com.team2.bank.DTOs.TransactionDTO;
import com.team2.bank.Enums.TransactionType;
import com.team2.bank.Services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //create - create transaction
    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDto) {
        TransactionDTO created = transactionService.createTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //read - get all transactions
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    //read - get by id
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    //read - type
    @GetMapping("types/{type}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(@PathVariable TransactionType type) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    //read - get transaction by customer id
    @GetMapping("customers/{customerId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCustomerId(@PathVariable Long customerId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }

    //read - get transaction by account id
    @GetMapping("accounts/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    //update - update transaction
    @PutMapping("update/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDto) {
        TransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDto);
        return ResponseEntity.ok(updatedTransaction);
    }

    //delete - delete transactions by id
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    //delete - delete transactions by type
    @DeleteMapping("delete/types/{type}")
    public ResponseEntity<?> deleteTransactionsByType(@PathVariable TransactionType type) {
        transactionService.deleteTransactionsByType(type);
        return ResponseEntity.noContent().build();
    }

}
