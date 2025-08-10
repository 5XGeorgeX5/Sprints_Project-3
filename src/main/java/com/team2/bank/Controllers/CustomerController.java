package com.team2.bank.Controllers;

import com.team2.bank.DTOs.CustomerDTO;
import com.team2.bank.Services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //create - create customer
    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDto) {
        CustomerDTO createdCustomer = customerService.createCustomer((customerDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    //read - get all customers
    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    //read - get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    //read - get customer by email
    @GetMapping("/search")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam String email) {
        return customerService.getCustomerByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //update - update customer
    @PutMapping("update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id,customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    //delete - delete customer
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?>deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
