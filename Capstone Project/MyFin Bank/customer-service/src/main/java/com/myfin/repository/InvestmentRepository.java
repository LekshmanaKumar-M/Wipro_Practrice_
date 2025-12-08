package com.myfin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myfin.model.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    Investment findByAccountNumber(String accountNumber);
}
