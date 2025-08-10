package com.team2.bank.Services;

import com.team2.bank.DTOs.CustomerDTO;
import com.team2.bank.Mapper.DTOMapper;
import com.team2.bank.Models.Customer;
import com.team2.bank.Repositories.CustomerRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final DTOMapper dtoMapper;

    public CustomerService(CustomerRepo customerRepo, DTOMapper dtoMapper) {
        this.customerRepo = customerRepo;
        this.dtoMapper = dtoMapper;
    }

    // create
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customerEntity = dtoMapper.toCustomerEntity(customerDTO);
        Customer savedEntity = customerRepo.save(customerEntity);
        return dtoMapper.toCustomerDTO(savedEntity);
    }

    // read
    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(dtoMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return dtoMapper.toCustomerDTO(customer);
    }

    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email)
                .map(dtoMapper::toCustomerDTO);
    }

    // update
    public CustomerDTO updateCustomer(Long id, CustomerDTO updatedDTO) {
        Customer existing = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        existing.setName(updatedDTO.getName());
        existing.setEmail(updatedDTO.getEmail());

        Customer updatedEntity = customerRepo.save(existing);
        return dtoMapper.toCustomerDTO(updatedEntity);
    }

    // delete
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }
}
