package com.team2.bank.Services;

import com.team2.bank.DTOs.BankAccountDTO;
import com.team2.bank.Enums.TransactionType;
import com.team2.bank.Mapper.DTOMapper;
import com.team2.bank.Models.BankAccountModel;
import com.team2.bank.Models.CustomerModel;
import com.team2.bank.Models.TransactionModel;
import com.team2.bank.Repositories.BankAccountRepo;
import com.team2.bank.Repositories.CustomerRepo;
import com.team2.bank.Repositories.TransactionRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private final BankAccountRepo bankAccountRepo;
    private final CustomerRepo customerRepo;
    private final TransactionRepo transactionRepo;
    private final DTOMapper dtoMapper;

    public BankAccountService(BankAccountRepo bankAccountRepo, CustomerRepo customerRepo, TransactionRepo transactionRepo, DTOMapper dtoMapper) {
        this.bankAccountRepo = bankAccountRepo;
        this.customerRepo = customerRepo;
        this.transactionRepo = transactionRepo;
        this.dtoMapper = dtoMapper;
    }

    // create
    public BankAccountDTO createBankAccount(BankAccountDTO dto) {
        BankAccountModel accountEntity = dtoMapper.toBankAccountEntity(dto);

        CustomerModel customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        accountEntity.setCustomerModel(customer);

        return dtoMapper.toBankAccountDTO(bankAccountRepo.save(accountEntity));
    }

    // read
    public List<BankAccountDTO> getAllBankAccounts() {
        return bankAccountRepo.findAll()
                .stream()
                .map(dtoMapper::toBankAccountDTO)
                .collect(Collectors.toList());
    }

    public BankAccountDTO getBankAccountById(Long id) {
        return bankAccountRepo.findById(id)
                .map(dtoMapper::toBankAccountDTO)
                .orElseThrow(() -> new EntityNotFoundException("Bank account not found"));
    }

    public List<BankAccountDTO> getAccountsByType(TransactionType type) {
        return bankAccountRepo.findByAccountType(type).stream()
                .map(dtoMapper::toBankAccountDTO)
                .collect(Collectors.toList());
    }

    public List<BankAccountDTO> findByBalanceGreaterThan(Double amount) {
        return bankAccountRepo.findByBalanceGreaterThan(amount)
                .stream()
                .map(dtoMapper::toBankAccountDTO)
                .collect(Collectors.toList());
    }

    public List<BankAccountDTO> findAccountsInRange(Double min, Double max) {
        return bankAccountRepo.findAccountsInRange(min, max)
                .stream()
                .map(dtoMapper::toBankAccountDTO)
                .collect(Collectors.toList());
    }

    // update
    public BankAccountDTO updateBankAccount(Long id, BankAccountDTO dto) {
        BankAccountModel existing = bankAccountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bank account not found"));

        existing.setBalance(dto.getBalance());
        existing.setAccountType(dto.getAccountType());

        if (dto.getCustomerId() != null) {
            CustomerModel customer = customerRepo.findById(dto.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            existing.setCustomerModel(customer);
        }

        return dtoMapper.toBankAccountDTO(bankAccountRepo.save(existing));
    }

    // delete
    public void deleteBankAccount(Long id) {
        bankAccountRepo.deleteById(id);
    }

    // transfer money between accounts
    @Transactional
    public void transferMoney(Long senderAccountId, Long receiverAccountId, Double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be positive");

        BankAccountModel sender = bankAccountRepo.findById(senderAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));

        BankAccountModel receiver = bankAccountRepo.findById(receiverAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        if (sender.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        bankAccountRepo.save(sender);
        bankAccountRepo.save(receiver);
    }

    @Transactional
    public BankAccountDTO deposit(Long accountId, Double amount) {
        BankAccountModel account = bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        bankAccountRepo.save(account);

        // Record transaction
        TransactionModel transaction = new TransactionModel();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setCustomerModel(account.getCustomerModel());
        transactionRepo.save(transaction);

        return dtoMapper.toBankAccountDTO(account);
    }

    @Transactional
    public BankAccountDTO withdraw(Long accountId, Double amount) {
        BankAccountModel account = bankAccountRepo.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);
        bankAccountRepo.save(account);

        // Record transaction
        TransactionModel transaction = new TransactionModel();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setCustomerModel(account.getCustomerModel());
        transactionRepo.save(transaction);

        return dtoMapper.toBankAccountDTO(account);
    }
}
