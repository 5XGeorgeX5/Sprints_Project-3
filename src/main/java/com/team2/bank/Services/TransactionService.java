package com.team2.bank.Services;

import com.team2.bank.DTOs.TransactionDTO;
import com.team2.bank.Enums.TransactionType;
import com.team2.bank.Mapper.DTOMapper;
import com.team2.bank.Models.CustomerModel;
import com.team2.bank.Models.TransactionModel;
import com.team2.bank.Repositories.CustomerRepo;
import com.team2.bank.Repositories.TransactionRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final CustomerRepo customerRepo;
    private final DTOMapper dtoMapper;

    public TransactionService(TransactionRepo transactionRepo, CustomerRepo customerRepo, DTOMapper dtoMapper) {
        this.transactionRepo = transactionRepo;
        this.customerRepo = customerRepo;
        this.dtoMapper = dtoMapper;
    }

    // create
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        TransactionModel transaction = dtoMapper.toTransactionEntity(transactionDTO);
        TransactionModel saved = transactionRepo.save(transaction);
        return dtoMapper.toTransactionDTO(saved);
    }

    // read all
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepo.findAll().stream()
                .map(dtoMapper::toTransactionDTO)
                .collect(Collectors.toList());
    }

    // read by ID
    public TransactionDTO getTransactionById(Long id) {
        TransactionModel transaction = transactionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id " + id));
        return dtoMapper.toTransactionDTO(transaction);
    }

    // read by Type
    public List<TransactionDTO> getTransactionsByType(TransactionType type) {
        return transactionRepo.findByType(type).stream()
                .map(dtoMapper::toTransactionDTO)
                .collect(Collectors.toList());
    }

    // update
    public TransactionDTO updateTransaction(Long id, TransactionDTO updatedDTO) {
        TransactionModel existing = transactionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id " + id));

        CustomerModel customer = customerRepo.findById(updatedDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + updatedDTO.getCustomerId()));

        existing.setCustomerModel(customer);
        existing.setAmount(updatedDTO.getAmount());
        existing.setType(updatedDTO.getType());

        TransactionModel saved = transactionRepo.save(existing);
        return dtoMapper.toTransactionDTO(saved);
    }
    // delete
    public void deleteTransaction(Long id) {
        transactionRepo.deleteById(id);
    }
    @Transactional
    public void deleteTransactionsByType(TransactionType type) {
        transactionRepo.deleteByType(type);
    }

    public List<TransactionDTO> getTransactionsByCustomerId(Long customerId) {
        return transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getCustomerModel().getId().equals(customerId))
                .map(dtoMapper::toTransactionDTO)
                .collect(Collectors.toList());
    }
    public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {
        return transactionRepo.findByCustomerModel_BankAccountModel_Id(accountId).stream()
                .map(dtoMapper::toTransactionDTO)
                .collect(Collectors.toList());
    }
}
