package com.example.spring_security_otp_email.Repo;

import com.example.spring_security_otp_email.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findCustomerByEmail(String email);

}
