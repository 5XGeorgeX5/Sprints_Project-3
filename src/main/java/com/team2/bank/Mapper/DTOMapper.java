package com.team2.bank.Mapper;

import com.team2.bank.Models.Customer;
import com.team2.bank.Models.BankAccount;
import com.team2.bank.Models.Transaction;
import com.team2.bank.DTOs.CustomerDTO;
import com.team2.bank.DTOs.BankAccountDTO;
import com.team2.bank.DTOs.TransactionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper {

    private final ModelMapper modelMapper;

    public DTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer toCustomerEntity(CustomerDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }

    public BankAccountDTO toBankAccountDTO(BankAccount bankAccount) {
        return modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    public BankAccount toBankAccountEntity(BankAccountDTO dto) {
        return modelMapper.map(dto, BankAccount.class);
    }

    public TransactionDTO toTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public Transaction toTransactionEntity(TransactionDTO dto) {
        return modelMapper.map(dto, Transaction.class);
    }
}
