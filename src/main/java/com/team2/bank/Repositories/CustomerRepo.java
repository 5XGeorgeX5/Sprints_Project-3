package com.team2.bank.Repositories;

import com.team2.bank.Models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerModel,Long> {
    Optional<CustomerModel> findByEmail(String email);

}
