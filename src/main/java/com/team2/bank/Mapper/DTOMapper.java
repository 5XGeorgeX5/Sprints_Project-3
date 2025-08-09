package com.team2.bank.Mapper;

import com.team2.bank.Models.CustomerModel;
import com.team2.bank.Models.BankAccountModel;
import com.team2.bank.Models.TransactionModel;
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

    public CustomerDTO toCustomerDTO(CustomerModel customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerModel toCustomerEntity(CustomerDTO dto) {
        return modelMapper.map(dto, CustomerModel.class);
    }

    public BankAccountDTO toBankAccountDTO(BankAccountModel bankAccount) {
        return modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    public BankAccountModel toBankAccountEntity(BankAccountDTO dto) {
        return modelMapper.map(dto, BankAccountModel.class);
    }

    public TransactionDTO toTransactionDTO(TransactionModel transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public TransactionModel toTransactionEntity(TransactionDTO dto) {
        return modelMapper.map(dto, TransactionModel.class);
    }
}
