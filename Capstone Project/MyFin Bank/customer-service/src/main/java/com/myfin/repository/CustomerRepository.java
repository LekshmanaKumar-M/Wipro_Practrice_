package com.myfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myfin.model.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	

    Customer findByAccountNumber(String accountNumber); 
}
